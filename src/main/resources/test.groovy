import thelameres.groovyspring.infrastructure.events.FromScriptEvent

fixedRateTask(initialDelay: 10.seconds, interval: 20.seconds) {
    log.info("Hello from task1")
    log.info("Application name: ${applicationContext.id}")
    def random = new Random()
    applicationContext.publishEvent(new FromScriptEvent("String: ${random.nextInt(0, 10)}"))
}

fixedRateTask(initialDelay: 20.seconds, interval: 30.seconds) {
    log.info("Hello from task2")
}


fixedRateTask(initialDelay: 30.seconds, interval: 30.seconds) {
    log.info("Hello from task3")
}

fixedDelayTask(initialDelay: 10.seconds, interval: 30.seconds) {
    log.info("Hello from task4")
}

fixedDelayTask(initialDelay: 10.seconds, interval: 30.seconds) {
    log.info("Hello from task5")
}

cronTask(cron: "0 * * * * *") {
    log.info("Hello from task6")
}