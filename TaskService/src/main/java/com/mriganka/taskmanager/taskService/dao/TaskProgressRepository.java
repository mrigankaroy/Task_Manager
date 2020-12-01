package com.mriganka.taskmanager.taskService.dao;

import com.mriganka.taskmanager.taskService.model.Task;
import com.mriganka.taskmanager.taskService.model.TaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Integer> {

}
