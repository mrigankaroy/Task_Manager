package com.mriganka.taskmanager.taskService.dao;

import com.mriganka.taskmanager.taskService.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<List<Task>> findByTaskGroupId(String category);
}
