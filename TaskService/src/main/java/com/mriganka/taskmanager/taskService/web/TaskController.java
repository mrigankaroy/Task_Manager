package com.mriganka.taskmanager.taskService.web;

import com.mriganka.taskmanager.taskService.model.Task;
import com.mriganka.taskmanager.taskService.model.web.BulkTaskResultResponse;
import com.mriganka.taskmanager.taskService.model.web.TaskResultResponse;
import com.mriganka.taskmanager.taskService.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final String GET_NUMLIST = "get_numlist";

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "{taskGroupId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTaskStatus(@PathVariable(value = "taskGroupId") String taskGroupId) throws Exception {
        String status = taskService.getTaskStatusByTaskGroupId(taskGroupId);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(new TaskResultResponse(status), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "{taskGroupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTask(@PathVariable(value = "taskGroupId") String taskGroupId,
                                     @RequestParam("action") String action) throws Exception {
        List<Task> tasks = taskService.getTasksByTaskGroupId(taskGroupId);
        if (CollectionUtils.isEmpty(tasks)) {
            throw new Exception("No task found with task group id " + taskGroupId);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        if (GET_NUMLIST.equalsIgnoreCase(action)) {
            List<String> progressReport = taskService.getTaskProgressReport(tasks);
            if (progressReport.size() == 1) {
                return new ResponseEntity<>(new TaskResultResponse(progressReport.get(0)), responseHeaders, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BulkTaskResultResponse(progressReport), responseHeaders, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
