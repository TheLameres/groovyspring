package thelameres.groovyspring.infrastructure.events;

import org.springframework.context.ApplicationEvent;

public class FromScriptEvent extends ApplicationEvent {
    private final String string;

    public FromScriptEvent(String source) {
        super(source);
        this.string = source;
    }

    public String getString() {
        return string;
    }
}
