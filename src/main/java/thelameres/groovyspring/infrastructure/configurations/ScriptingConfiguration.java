package thelameres.groovyspring.infrastructure.configurations;

import thelameres.groovyspring.infrastructure.dsls.CronTaskDsl;
import thelameres.groovyspring.infrastructure.dsls.FixedDelayTaskDsl;
import thelameres.groovyspring.infrastructure.dsls.FixedRateTaskDsl;
import thelameres.groovyspring.infrastructure.dsls.TaskScript;
import groovy.lang.GroovyShell;
import groovy.util.logging.Log;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.Task;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ScriptingConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ScriptingConfiguration.class);

    @Bean
    public CompilationCustomizer logCompilationCustomizer() {
        return new ASTTransformationCustomizer(Log.class);
    }

    @Bean
    public CompilerConfiguration compilerConfiguration(Set<CompilationCustomizer> compilationCustomizers) {
        var config = new CompilerConfiguration();
        compilationCustomizers.forEach(config::addCompilationCustomizers);
        return config;
    }

    @Bean
    public GroovyShell shell(CompilerConfiguration compilerConfiguration) {
        return new GroovyShell(this.getClass().getClassLoader(), compilerConfiguration);
    }

    @Bean
    public Resource script() {
        return new ClassPathResource("test.groovy");
    }

    @Bean
    public Set<? extends Task> tasks(GroovyShell shell,
                                     Resource script,
                                     ConfigurableApplicationContext applicationContext) throws Exception {
        TaskScript parse = (TaskScript) shell.parse(script.getContentAsString(StandardCharsets.UTF_8));
        parse.setProperty("applicationContext", applicationContext);
        parse.run();
        return parse
                .taskDsls()
                .stream()
                .peek(it -> log.info("Task: {}", it.hashCode()))
                //Replace if instanceof
                .map(it -> switch (it.getTaskType()) {
                    case CRON -> {
                        CronTaskDsl cronTaskDsl = (CronTaskDsl) it;
                        yield new CronTask(() -> cronTaskDsl.getRun().run(), cronTaskDsl.getCron());
                    }
                    case FIXED_DELAY -> {
                        FixedDelayTaskDsl fixedDelayTaskDsl = (FixedDelayTaskDsl) it;
                        yield new FixedDelayTask(() -> fixedDelayTaskDsl.getRun().run(), fixedDelayTaskDsl.getInterval(), fixedDelayTaskDsl.getInitialDelay());
                    }
                    case FIXED_RATE -> {
                        FixedRateTaskDsl fixedRateTaskDsl = (FixedRateTaskDsl) it;
                        yield new FixedRateTask(() -> fixedRateTaskDsl.getRun().run(), fixedRateTaskDsl.getInterval(), fixedRateTaskDsl.getInitialDelay());
                    }
                })
                .collect(Collectors.toSet());
    }
}
