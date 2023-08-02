package thelameres.groovyspring.infrastructure.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.*;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ApplicationSchedulingConfigurer implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(ApplicationSchedulingConfigurer.class);

    private final TaskScheduler taskScheduler;
    private final Set<? extends Task> tasks;


    public ApplicationSchedulingConfigurer(TaskScheduler taskScheduler,
                                           Set<? extends Task> tasks) {
        this.taskScheduler = taskScheduler;
        this.tasks = tasks;
        log.info("tasks: {}", tasks.stream().map(Object::hashCode).collect(Collectors.toSet()));
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler);
        tasks.forEach(task -> registerTask(taskRegistrar, task));
    }

    private static void registerTask(ScheduledTaskRegistrar taskRegistrar, Task task) {

        if (task instanceof CronTask cronTask) {
            log.info("Task is CronTask");
            taskRegistrar.addCronTask(cronTask);
        } else if (task instanceof FixedDelayTask fixedDelayTask) {
            log.info("Task is FixedDelayTask");
            taskRegistrar.addFixedDelayTask(fixedDelayTask);
        } else if (task instanceof FixedRateTask fixedRateTask) {
            log.info("Task is FixedRateTask");
            taskRegistrar.addFixedRateTask(fixedRateTask);
        }
    }
}
