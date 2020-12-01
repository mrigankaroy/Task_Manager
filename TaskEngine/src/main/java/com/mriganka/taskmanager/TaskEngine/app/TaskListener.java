package com.mriganka.taskmanager.TaskEngine.app;

import com.mriganka.taskmanager.TaskEngine.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TaskListener {

    Logger LOG = LoggerFactory.getLogger(TaskListener.class);

    @Autowired
    private TaskService taskService;

    /**
     * Kafka listener for topic task_manager.public.task
     * @param message
     * @param partition
     */
    @KafkaListener(topics = "task_manager.public.task", groupId = "task_consumer_group_1")
    public void listenTaskMessage(@Payload String message,
                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOG.info("Consumer raw message: " + message);
        LOG.info("Consumer partition id: " + partition);
        taskService.processTaskData(message);
    }
}
