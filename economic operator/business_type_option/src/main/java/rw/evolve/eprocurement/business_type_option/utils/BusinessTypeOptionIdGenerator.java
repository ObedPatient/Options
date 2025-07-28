/**
 * Utility class for generating unique identifier for Business type options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.business_type_option.utils;

import java.util.Random;

public class BusinessTypeOptionIdGenerator {
    /**
     * Generates a unique identifier for a Business type option.
     * The ID         - is formatted as "BUSINESS_TYPE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique Business type option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "BUSINESS_TYPE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}