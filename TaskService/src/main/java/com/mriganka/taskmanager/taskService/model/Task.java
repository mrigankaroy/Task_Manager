package com.mriganka.taskmanager.taskService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TASK")
public class Task implements Serializable {

    private static final long serialVersionUID = 113L;

    @Id
    @Column(name = "TASK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @Column(name = "TASK_GROUP_ID")
    private String taskGroupId;

    @Column(name = "GOAL")
    private int goal;

    @Column(name = "STEP")
    private int step;

    @Column(name = "INTERVAL")
    private int interval;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STATUS_ID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TaskStatus taskStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "START_DATE")
    private Date startDate;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<TaskProgress> taskProgresses;
}
