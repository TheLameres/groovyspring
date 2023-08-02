package thelameres.groovyspring.infrastructure.dsls


import thelameres.groovyspring.model.TaskType

abstract class TaskDsl {
    private Closure run;
    private TaskType taskType;

    TaskDsl(TaskType taskType, Closure run) {
        this.run = run
        this.taskType = taskType
    }

    Closure getRun() {
        return run
    }

    TaskType getTaskType() {
        return taskType
    }

    def taskType(TaskType taskType) {
        this.taskType = taskType
    }

    def run(Closure<Void> run) {
        this.run = run;
    }
}



