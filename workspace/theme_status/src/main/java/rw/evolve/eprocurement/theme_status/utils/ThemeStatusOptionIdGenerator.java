/**
 * Utility class for generating unique identifier for ThemeStatus options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.theme_status.utils;

import java.util.Random;

public class ThemeStatusOptionIdGenerator {
    /**
     * Generates a unique identifier for a ThemeStatus option.
     * The ID         - is formatted as "THEME_STATUS_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique ThemeStatus option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "THEME_STATUS_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}