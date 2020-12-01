package com.mriganka.taskmanager.taskService.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkTaskRequest implements Serializable {
    private List<TaskRequest> taskRequestList;
}
