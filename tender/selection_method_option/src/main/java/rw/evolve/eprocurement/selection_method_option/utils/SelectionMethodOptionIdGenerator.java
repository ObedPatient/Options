/**
 * Utility class for generating unique identifier for SelectionMethod options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.selection_method_option.utils;

import java.util.Random;

public class SelectionMethodOptionIdGenerator {
    /**
     * Generates a unique identifier for a SelectionMethod option.
     * The ID         - is formatted as "SELECTION_METHOD_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique SelectionMethod option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "SELECTION_METHOD_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}