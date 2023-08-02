package thelameres.groovyspring.infrastructure.dsls

import thelameres.groovyspring.model.TaskType

class CronTaskDsl extends TaskDsl {

    private String cron;

    CronTaskDsl(String cron, Closure run) {
        super(TaskType.CRON, run)
        this.cron = cron;
    }

    String getCron() {
        return cron
    }

    def cron(String cron) {
        this.cron = cron;
    }
}