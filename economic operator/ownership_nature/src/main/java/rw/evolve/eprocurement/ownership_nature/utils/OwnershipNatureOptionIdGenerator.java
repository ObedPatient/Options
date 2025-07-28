/**
 * Utility class for generating unique identifier for OwnershipNature options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.ownership_nature.utils;

import java.util.Random;

public class OwnershipNatureOptionIdGenerator {
    /**
     * Generates a unique identifier for a OwnershipNature option.
     * The ID         - is formatted as "OWNERSHIP_NATURE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique OwnershipNature option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "OWNERSHIP_NATURE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}