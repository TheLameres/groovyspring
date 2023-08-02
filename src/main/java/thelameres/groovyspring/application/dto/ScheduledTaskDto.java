package thelameres.groovyspring.application.dto;

import thelameres.groovyspring.model.TaskType;
import org.springframework.scheduling.config.*;

public record ScheduledTaskDto(String name,
                        String cron,
                        String interval,
                        String initialDelay,
                        TaskType taskType) {

    private ScheduledTaskDto() {
        this(null);
    }

    private ScheduledTaskDto(String name) {
        this(name, null);
    }

    private ScheduledTaskDto(String name, String cron) {
        this(name, cron, null, null, TaskType.CRON);
    }

    private ScheduledTaskDto(String name, String interval, String initialDelay, TaskType taskType) {
        this(name, null, interval, initialDelay, taskType);
    }


    public static ScheduledTaskDto create(Object object) {
        if (object instanceof ScheduledTask scheduledTask) {
            Task task = scheduledTask.getTask();
            String name = task.getRunnable().toString();
            if (task instanceof CronTask cronTask) {
                return new ScheduledTaskDto(name, cronTask.getExpression());
            } else if (task instanceof FixedDelayTask fixedDelayTask) {
                return new ScheduledTaskDto(name, fixedDelayTask.getIntervalDuration().toString(), fixedDelayTask.getInitialDelayDuration().toString(), TaskType.FIXED_DELAY);
            } else if (task instanceof FixedRateTask fixedRateTask) {
                return new ScheduledTaskDto(name, fixedRateTask.getIntervalDuration().toString(), fixedRateTask.getInitialDelayDuration().toString(), TaskType.FIXED_RATE);
            }
        }
        throw new RuntimeException();
    }
}