package com.mriganka.taskmanager.taskService.service;

import com.mriganka.taskmanager.taskService.dao.TaskStatusRepository;
import com.mriganka.taskmanager.taskService.model.TaskStatus;
import com.mriganka.taskmanager.taskService.model.TaskStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public TaskStatus getTaskStatus(TaskStatusEnum statusEnum) throws Exception{
        Optional<TaskStatus> taskStatus = taskStatusRepository.findByTaskStatus(statusEnum.getStatus());
        taskStatus.orElseThrow(() -> new Exception("Task status not found with satus: " + statusEnum.getStatus()));
        return taskStatus.get();
    }
}
