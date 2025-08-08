/**
 * Utility class for generating unique identifier for ArchiveStrategy options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.archive_strategy.utils;

import java.util.Random;

public class ArchiveStrategyOptionIdGenerator {
    /**
     * Generates a unique identifier for a ArchiveStrategy option.
     * The ID         - is formatted as "ARCHIVE_STRATEGY_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique ArchiveStrategy option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "ARCHIVE_STRATEGY_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}