package com.mriganka.taskmanager.taskService.service;

import com.mriganka.taskmanager.taskService.dao.TaskRepository;
import com.mriganka.taskmanager.taskService.model.Task;
import com.mriganka.taskmanager.taskService.model.TaskProgress;
import com.mriganka.taskmanager.taskService.model.TaskProgressStatus;
import com.mriganka.taskmanager.taskService.model.TaskStatusEnum;
import com.mriganka.taskmanager.taskService.model.web.TaskRequest;
import com.mriganka.taskmanager.taskService.util.TaskHelper;
import com.mriganka.taskmanager.taskService.util.TaskProgressSortByValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private TaskProgressService taskProgressService;

    public Task createTask(int goal, int step) throws Exception {
        String taskGroupId = TaskHelper.randomGroupIdGenerator();
        return createTask(goal, step, taskGroupId);
    }

    /**
     * Create task
     *
     * @param goal
     * @param step
     * @param taskGroupId
     * @return
     * @throws Exception
     */
    public Task createTask(int goal, int step, String taskGroupId) throws Exception {
        Task task = new Task();
        task.setTaskGroupId(taskGroupId);
        task.setGoal(goal);
        task.setStep(step);
        task.setInterval(TaskHelper.randomInterval());
        task.setTaskStatus(taskStatusService.getTaskStatus(TaskStatusEnum.IN_PROGRESS));
        task.setStartDate(new Date());
        task = taskRepository.save(task);

        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setTask(task);
        taskProgress.setValue(goal);
        taskProgress.setProcessedTime(new Date());
        taskProgressService.createTaskProgress(taskProgress);

        return task;
    }

    /**
     * Create bul task
     *
     * @param taskRequests
     * @return
     * @throws Exception
     */
    public String createBulkTask(List<TaskRequest> taskRequests) throws Exception {
        String taskGroupId = TaskHelper.randomGroupIdGenerator();
        for (TaskRequest task : taskRequests) {
            createTask(Integer.parseInt(task.getGoal()), Integer.parseInt(task.getStep()), taskGroupId);
        }
        return taskGroupId;
    }

    /**
     * Get task by task group id
     *
     * @param taskGroupId
     * @return
     * @throws Exception
     */
    public List<Task> getTasksByTaskGroupId(String taskGroupId) throws Exception {
        Optional<List<Task>> taskStatus = taskRepository.findByTaskGroupId(taskGroupId);
        taskStatus.orElseThrow(() -> new Exception("No Task found with UUID: " + taskGroupId));
        return taskStatus.get();
    }

    /**
     * Get task progress report
     *
     * @param tasks
     * @return
     */
    public List<String> getTaskProgressReport(List<Task> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }
        List<String> results = new ArrayList<>();
        tasks.forEach(
                task -> {
                    List<TaskProgress> taskProgresses = task.getTaskProgresses();
                    Collections.sort(taskProgresses, new TaskProgressSortByValue(TaskProgressSortByValue.SortOrder.DESCENDING));
                    StringBuffer resultBuffer = new StringBuffer();
                    for (int i = 0; i < taskProgresses.size(); i++) {
                        resultBuffer.append(taskProgresses.get(i).getValue());
                        if (i < (taskProgresses.size() - 1)) {
                            resultBuffer.append(",");
                        }
                    }
                    results.add(resultBuffer.toString());
                }
        );
        return results;
    }

    /**
     * Get status by task group id
     *
     * @param taskProgressId
     * @return
     * @throws Exception
     */
    public String getTaskStatusByTaskGroupId(String taskProgressId) throws Exception {
        List<Task> taskList = getTasksByTaskGroupId(taskProgressId);
        TaskStatusEnum statusEnum = TaskStatusEnum.SUCCESS;
        for (Task task : taskList) {
            if (TaskStatusEnum.ERROR.getStatus().equalsIgnoreCase(task.getTaskStatus().getTaskStatus())) {
                return TaskStatusEnum.ERROR.getStatus();
            } else if (TaskStatusEnum.IN_PROGRESS.getStatus().equalsIgnoreCase(task.getTaskStatus().getTaskStatus())) {
                statusEnum = TaskStatusEnum.IN_PROGRESS;
            }
        }
        return statusEnum.getStatus();
    }

    /**
     * Update current task progress
     *
     * @param progressStatus
     * @throws Exception
     */
    @Transactional
    public void updateTaskProgress(TaskProgressStatus progressStatus) throws Exception {
        Task task = taskRepository.getOne(progressStatus.getTaskId());

        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setTask(task);
        taskProgress.setValue(progressStatus.getCurrentValue());
        taskProgress.setProcessedTime(progressStatus.getCurrentTime());
        taskProgressService.createTaskProgress(taskProgress);

        if (!task.getTaskStatus().getTaskStatus().equalsIgnoreCase(progressStatus.getTaskStatus().getStatus())) {
            task.setTaskStatus(taskStatusService.getTaskStatus(progressStatus.getTaskStatus()));
            taskRepository.save(task);
        }

    }
}
