package com.mriganka.taskmanager.TaskEngine.conf.process;

import com.mriganka.taskmanager.TaskEngine.service.TaskService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

//@Configuration

//@EnableKafkaStreams
public class TaskStreamProcessor {

    /*Logger LOG = LoggerFactory.getLogger(TaskStreamProcessor.class);

    @Value("${task-stream.kafka.bootstrap-server}")
    private String bootstrapServers;

    @Value("${task-stream.kafka.application-id}")
    private String applicationId;

    @Value("${task-stream.kafka.stream.input-stream}")
    private String taskStream;*/

   /* @Value("${task-stream.kafka.stream.output-stream}")
    private String testOutputStream;*/

  /*  @Autowired
    private TaskService taskService;


    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public KStream<String, String> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> stream = kStreamBuilder.stream(taskStream);
        stream.mapValues(v -> {
            taskService.processTaskData(v);
            return v;
        });
        return stream;
    }*/
}
