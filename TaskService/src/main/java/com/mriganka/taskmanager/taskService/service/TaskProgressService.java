package com.mriganka.taskmanager.taskService.service;

import com.mriganka.taskmanager.taskService.dao.TaskProgressRepository;
import com.mriganka.taskmanager.taskService.model.TaskProgress;
import com.mriganka.taskmanager.taskService.model.TaskProgressStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskProgressService {

    @Autowired
    private TaskProgressRepository taskProgressRepository;

    public TaskProgress createTaskProgress(TaskProgress taskProgress){
        return taskProgressRepository.save(taskProgress);
    }


}
