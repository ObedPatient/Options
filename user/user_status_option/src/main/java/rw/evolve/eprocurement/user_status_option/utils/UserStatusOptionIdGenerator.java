/**
 * Utility class for generating unique identifier for scheme options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.user_status_option.utils;

import java.util.Random;

public class UserStatusOptionIdGenerator {
    /**
     * Generates a unique identifier for a User status option.
     * The ID         - is formatted as "USER_STATUS_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique User status option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "USER_STATUS_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}