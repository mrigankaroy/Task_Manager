package com.mriganka.taskmanager.taskService.dao;

import com.mriganka.taskmanager.taskService.model.TaskProgress;
import com.mriganka.taskmanager.taskService.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    Optional<TaskStatus> findByTaskStatus(String taskStatus);
}
