package com.mriganka.taskmanager.taskService.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "TASK_STATUS")
public class TaskStatus implements Serializable {

    private static final long serialVersionUID = 113L;

    @Id
    @Column(name = "STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskStatusId;

    @Column(name = "STATUS_NAME")
    private String taskStatus;
}
