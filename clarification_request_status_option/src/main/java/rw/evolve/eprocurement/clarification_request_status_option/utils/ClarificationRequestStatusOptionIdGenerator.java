/**
 * Utility class for generating unique identifier for ClarificationRequestStatus options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.clarification_request_status_option.utils;

import java.util.Random;

public class ClarificationRequestStatusOptionIdGenerator {
    /**
     * Generates a unique identifier for a ClarificationRequestStatus option.
     * The ID         - is formatted as "CLARIFICATION_REQUEST_STATUS_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique ClarificationRequestStatus option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "CLARIFICATION_REQUEST_STATUS_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}