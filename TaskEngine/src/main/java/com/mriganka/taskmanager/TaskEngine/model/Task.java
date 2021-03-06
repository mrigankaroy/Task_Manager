package com.mriganka.taskmanager.TaskEngine.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task implements Serializable {

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
