/**
 * Utility class for generating unique identifier for scheme options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.country.utils;

import java.util.Random;

public class CountryOptionIdGenerator {
    /**
     * Generates a unique identifier for a COUNTRY option.
     * The ID         - is formatted as "COUNTRY_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique COUNTRY option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "COUNTRY_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}