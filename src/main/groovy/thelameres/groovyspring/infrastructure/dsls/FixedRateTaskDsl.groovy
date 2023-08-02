package thelameres.groovyspring.infrastructure.dsls


import thelameres.groovyspring.model.TaskType

import java.time.Duration

class FixedRateTaskDsl extends TaskDsl {
    private Duration interval;
    private Duration initialDelay;

    FixedRateTaskDsl(Duration interval, Duration initialDelay, Closure run) {
        super(TaskType.FIXED_RATE, run)
        this.interval = interval
        this.initialDelay = initialDelay
    }

    def interval(Duration interval) {
        this.interval = interval
    }

    def initialDelay(Duration initialDelay) {
        this.initialDelay = initialDelay
    }

    Duration getInterval() {
        return interval
    }

    Duration getInitialDelay() {
        return initialDelay
    }
}