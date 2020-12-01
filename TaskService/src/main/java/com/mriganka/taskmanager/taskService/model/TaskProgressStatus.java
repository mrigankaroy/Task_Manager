package com.mriganka.taskmanager.taskService.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskProgressStatus {
    private static final long serialVersionUID = 1L;

    private int taskId;
    private String taskGroupId;
    private int goal;
    private int step;
    private int interval;
    private TaskStatusEnum taskStatus;
    private Date startDate;
    private int currentValue;
    private Date currentTime;
}
