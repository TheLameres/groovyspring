package thelameres.groovyspring.infrastructure.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.TaskScheduler;

@Configuration()
public class TaskSchedulerConfiguration {
    @Bean()
    public TaskSchedulerCustomizer taskSchedulerCustomizer1() {
        return it -> it.setErrorHandler((t) -> {
            Logger logger = LoggerFactory.getLogger(t.getClass());
            logger.error(t.getMessage(), t);
        });
    }

    @Bean
    @Primary
    public TaskScheduler taskScheduler(TaskSchedulerBuilder taskSchedulerBuilder) {
        return taskSchedulerBuilder.build();
    }
}