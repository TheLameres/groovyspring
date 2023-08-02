package thelameres.groovyspring.application.controller;

import thelameres.groovyspring.application.dto.ScheduledTaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scheduled")
public class ScheduledTaskController {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskController.class);

    private final ScheduledTaskHolder scheduledTaskRegistrar;

    ScheduledTaskController(ScheduledTaskHolder scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
    }


    @GetMapping
    public List<ScheduledTaskDto> getTasks() {
        return scheduledTaskRegistrar
                .getScheduledTasks()
                .stream()
                .peek((it) -> log.info("Task: {}", it))
                .map(ScheduledTaskDto::create)
                .toList();
    }
}
