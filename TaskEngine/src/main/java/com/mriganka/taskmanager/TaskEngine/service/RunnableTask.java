package com.mriganka.taskmanager.TaskEngine.service;

import com.mriganka.taskmanager.TaskEngine.model.Task;
import org.redisson.api.annotation.RInject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class RunnableTask implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient TaskService taskService;

    @RInject
    private String taskId;

    private Task task;

    public RunnableTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        taskService.processTask(task,taskId);
    }
}
