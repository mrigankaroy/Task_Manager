package com.mriganka.taskmanager.TaskEngine.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
