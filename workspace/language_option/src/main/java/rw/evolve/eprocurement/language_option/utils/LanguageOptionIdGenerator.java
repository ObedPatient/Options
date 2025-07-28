/**
 * Utility class for generating unique identifier for scheme options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.language_option.utils;

import java.util.Random;

public class LanguageOptionIdGenerator {
    /**
     * Generates a unique identifier for a Language option.
     * The ID         - is formatted as "LANGUAGE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique Language option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "LANGUAGE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}