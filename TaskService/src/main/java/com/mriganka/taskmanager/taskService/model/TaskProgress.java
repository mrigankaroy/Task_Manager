package com.mriganka.taskmanager.taskService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "TASK_PROGRESS")
public class TaskProgress implements Serializable {

    private static final long serialVersionUID = 113L;

    @Id
    @Column(name = "TASK_PROGRESS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskProgressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TASK_ID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Task task;

    @Column(name = "VALUE")
    private int value;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "PROCESS_TIME")
    private Date processedTime;
}
