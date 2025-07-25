/**
 * Utility class for generating unique identifier for scheme options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.source_of_fund_option.utils;

import java.util.Random;

public class SourceOfFundOptionIdGenerator {
    /**
     * Generates a unique identifier for a scheme option.
     * The ID         - is formatted as "SOURCE_OF_FUND_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique Source of fund option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "SOURCE_OF_FUND_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}