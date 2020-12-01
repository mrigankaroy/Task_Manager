package com.mriganka.taskmanager.TaskEngine.conf;

import org.redisson.Redisson;
import org.redisson.api.ExecutorOptions;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.api.executor.TaskFailureListener;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedissonConfiguration {

    Logger LOG = LoggerFactory.getLogger(RedissonConfiguration.class);

    @Value("${task-redisson.conf-file}")
    private String configurationFile;

    @Value("${task-redisson.executor-name}")
    private String executorName;

    /**
     * Redisson configuration from configuration file
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Config config() throws IOException {
        ClassPathResource path = new ClassPathResource(configurationFile);
        return Config.fromYAML(path.getInputStream());
    }

    /**
     * Redisson client to access redisson server
     *
     * @param config
     * @return
     */
    @Bean
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

    /**
     * Redisson distributed scheduler based on name configure in {task-redisson.executor-name}
     *
     * @param redissonClient
     * @param beanFactory
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RScheduledExecutorService rScheduledExecutorService(
            final RedissonClient redissonClient,
            final BeanFactory beanFactory
    ) {
        final WorkerOptions workerOptions = WorkerOptions.defaults()
                .workers(2)
                .beanFactory(beanFactory)
                .taskTimeout(10, TimeUnit.SECONDS)
                // add listener which is invoked on task failed event
                .addListener(new TaskFailureListener() {
                    public void onFailed(String taskId, Throwable exception) {
                        LOG.error("Fail to execute task " + taskId, exception);
                    }
                });

        final ExecutorOptions executorOptions = ExecutorOptions.defaults()
                .taskRetryInterval(2, TimeUnit.SECONDS);
        final RScheduledExecutorService executorService = redissonClient
                .getExecutorService(executorName, executorOptions);
        executorService.registerWorkers(workerOptions);
        return executorService;
    }


}


