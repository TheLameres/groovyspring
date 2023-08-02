package thelameres.groovyspring.infrastructure.dsls


import groovy.util.logging.Slf4j

import java.time.Duration

@Slf4j
abstract class TaskScript extends Script {

    abstract void scriptBody();

    private Set<? extends TaskDsl> taskDsls = [];

    Set<? extends TaskDsl> taskDsls() {
        return taskDsls
    }

    @Override
    Object run() {
        scriptBody();
        return null;
    }

    def methodMissing(String name, def args) {
        log.info("{}: {}", name, args)
    }

    def fixedRateTask(Duration interval, Duration initialDelay, Closure<Void> closure) {
        taskDsls.add(new FixedRateTaskDsl(interval, initialDelay, closure))
    }

    def fixedRateTask(LinkedHashMap<String, Duration> args, Closure<Void> closure) {
        fixedRateTask(args["interval"], args["initialDelay"], closure)
    }

    def fixedDelayTask(Duration interval, Duration initialDelay, Closure<Void> closure) {
        taskDsls.add(new FixedDelayTaskDsl(interval, initialDelay, closure))
    }

    def fixedDelayTask(LinkedHashMap<String, Duration> args, Closure<Void> closure) {
        fixedDelayTask(args["interval"], args["initialDelay"], closure)
    }

    def cronTask(String cron, Closure<Void> closure) {
        taskDsls.add(new CronTaskDsl(cron, closure))
    }

    def cronTask(LinkedHashMap<String, String> args, Closure<Void> closure) {
        cronTask(args["cron"], closure)
    }

}
