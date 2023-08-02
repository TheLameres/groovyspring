package thelameres.groovyspring.infrastructure.extenstions

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationExtension {
    public static Duration getMillis(Integer self) {
        return Duration.of(self, ChronoUnit.MILLIS);
    }

    public static Duration getSeconds(Integer self) {
        return Duration.of(self, ChronoUnit.SECONDS);
    }

    public static Duration getMinutes(Integer self) {
        return Duration.of(self, ChronoUnit.MINUTES);
    }

    public static Duration getHours(Integer self) {
        return Duration.of(self, ChronoUnit.HOURS);
    }

    public static Duration getDays(Integer self) {
        return Duration.of(self, ChronoUnit.DAYS);
    }
}
