package thelameres.groovyspring;

import thelameres.groovyspring.infrastructure.events.FromScriptEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class GroovySpringApplication {

    private static final Logger log = LoggerFactory.getLogger(GroovySpringApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GroovySpringApplication.class, args);
    }

    @EventListener(FromScriptEvent.class)
    public void fromScriptEvent(FromScriptEvent fromScriptEvent) {
        log.info("From Script: {}", fromScriptEvent.getString());
    }

}