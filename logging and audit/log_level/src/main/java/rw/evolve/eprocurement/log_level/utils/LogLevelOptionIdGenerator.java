/**
 * Utility class for generating unique identifier for LogLevel options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.log_level.utils;

import java.util.Random;

public class LogLevelOptionIdGenerator {
    /**
     * Generates a unique identifier for a LogLevel option.
     * The ID         - is formatted as "LOG_LEVEL_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique LogLevel option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "LOG_LEVEL_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}