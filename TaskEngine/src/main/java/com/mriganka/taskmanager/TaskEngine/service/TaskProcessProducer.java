package com.mriganka.taskmanager.TaskEngine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mriganka.taskmanager.TaskEngine.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskProcessProducer {

    Logger LOG = LoggerFactory.getLogger(TaskProcessProducer.class);

    @Value("${task-progress-stream.stream.output-stream}")
    private String taskProgressProducer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void sentTaskProgressData(Task task) throws JsonProcessingException {
        LOG.info("Sending to kafka topic:{} message:{}", taskProgressProducer, task);
        kafkaTemplate.send(taskProgressProducer, mapper.writeValueAsString(task));
    }
}
