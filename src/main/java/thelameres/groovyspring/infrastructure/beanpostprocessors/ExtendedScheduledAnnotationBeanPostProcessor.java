package thelameres.groovyspring.infrastructure.beanpostprocessors;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExtendedScheduledAnnotationBeanPostProcessor extends ScheduledAnnotationBeanPostProcessor {
}
