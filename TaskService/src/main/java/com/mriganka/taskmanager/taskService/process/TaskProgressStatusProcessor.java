package com.mriganka.taskmanager.taskService.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mriganka.taskmanager.taskService.model.TaskProgressStatus;
import com.mriganka.taskmanager.taskService.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TaskProgressStatusProcessor {

    Logger LOG = LoggerFactory.getLogger(TaskProgressStatusProcessor.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TaskService taskService;

    /**
     * Kafka listener for topic updated_task_progress
     * @param message
     * @param partition
     * @throws Exception
     */
    @KafkaListener(topics = "updated_task_progress")
    public void listenTaskMessage(@Payload String message,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws Exception {
        LOG.info("Consumer raw message: " + message);
        LOG.info("Consumer partition id: " + partition);
        TaskProgressStatus progressStatus = mapper.readValue(message, TaskProgressStatus.class);
        LOG.info("Converted to TaskProgressStatus: " + progressStatus);
        taskService.updateTaskProgress(progressStatus);
    }
}
