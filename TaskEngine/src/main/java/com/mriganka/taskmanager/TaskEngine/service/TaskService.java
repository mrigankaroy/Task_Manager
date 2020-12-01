package com.mriganka.taskmanager.TaskEngine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mriganka.taskmanager.TaskEngine.model.Task;
import com.mriganka.taskmanager.TaskEngine.model.TaskStatusEnum;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {

    Logger LOG = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RScheduledExecutorService rScheduledExecutorService;

    @Autowired
    private TaskProcessProducer taskProcessProducer;

    /**
     * Convert kafka message to Task object and process
     *
     * @param taskRawData
     */
    public void processTaskData(String taskRawData) {
        Task task = null;
        try {
            JsonNode actualObj = mapper.readTree(taskRawData);
            JsonNode payload = actualObj.get("payload");
            JsonNode data = payload.get("after");
            task = convertJSONToObject(data);
            LOG.info("Message converted to task :{}", task);
        } catch (JsonProcessingException e) {
            LOG.error("Parsing error:{}", e);
        }
        if (!Objects.isNull(task) && TaskStatusEnum.IN_PROGRESS.equals(task.getTaskStatus())) {
            checkAndScheduleTask(task);
        }
    }

    /**
     * Convert kafka message to Task object
     *
     * @param data
     * @return
     */
    private Task convertJSONToObject(JsonNode data) {
        Task task = new Task();
        task.setTaskId(Integer.parseInt(data.get("task_id").toString()));
        task.setTaskGroupId(data.get("task_group_id").toString());
        task.setGoal(Integer.parseInt(data.get("goal").toString()));
        task.setStep(Integer.parseInt(data.get("step").toString()));
        task.setInterval(Integer.parseInt(data.get("interval").toString()));
        task.setTaskStatus(TaskStatusEnum.getById(Integer.parseInt(data.get("status_id").toString())));
        task.setStartDate(new Date(Long.parseLong(data.get("start_date").toString())));
        task.setCurrentValue(task.getGoal());
        return task;
    }

    /**
     * Schedule task based on criteria
     *
     * @param task
     */
    public void checkAndScheduleTask(Task task) {
        if (task.getCurrentValue() > 0) {
            final RScheduledFuture<?> rScheduledFuture = this.rScheduledExecutorService.schedule(
                    new RunnableTask(task),
                    task.getInterval(),
                    TimeUnit.SECONDS
            );
            rScheduledFuture.whenComplete((result, error) -> {
                LOG.info("Completed execution:{}", task.toString());
                rScheduledFuture.cancel(true);
            });
        }
    }

    @Transactional
    public void processTask(Task task, String taskId) {
        LOG.info("Executing task id :{} with task details :{}", taskId, task);
        task.setCurrentValue(task.getCurrentValue() - task.getStep());
        task.setCurrentTime(new Date());
        if (task.getCurrentValue() <= 0) {
            task.setTaskStatus(TaskStatusEnum.SUCCESS);
        }
        boolean isSuccess = false;
        try {
            taskProcessProducer.sentTaskProgressData(task);
            checkAndScheduleTask(task);
            isSuccess = true;
        } catch (Exception e) {
            LOG.error("Error during message processing ", e);
        }

        if (!isSuccess) {
            task.setTaskStatus(TaskStatusEnum.ERROR);
            try {
                taskProcessProducer.sentTaskProgressData(task);
            } catch (JsonProcessingException e) {
                LOG.error("Error during sending error log ", e);
            }
        }

    }

}
