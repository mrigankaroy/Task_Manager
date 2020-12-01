package com.mriganka.taskmanager.taskService.web;

import com.mriganka.taskmanager.taskService.model.web.BulkTaskRequest;
import com.mriganka.taskmanager.taskService.model.web.TaskRequest;
import com.mriganka.taskmanager.taskService.model.web.TaskResponse;
import com.mriganka.taskmanager.taskService.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bulkGenerate")
public class BulkTaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> createBulkTask(@RequestBody List<TaskRequest> taskRequestList) throws Exception {
        String groupId = taskService.createBulkTask(taskRequestList);
        HttpHeaders responseHeaders = new HttpHeaders();
        TaskResponse taskResponse = new TaskResponse(groupId);
        return new ResponseEntity<>(taskResponse, responseHeaders, HttpStatus.ACCEPTED);
    }
}
