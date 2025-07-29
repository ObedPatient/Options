/**
 * Utility class for generating unique identifier for PrebidEventType options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.prebid_event_type.utils;

import java.util.Random;

public class PrebidEventTypeIdGenerator {
    /**
     * Generates a unique identifier for a PrebidEventType option.
     * The ID         - is formatted as "PREBID_EVENT_TYPE_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique PrebidEventType option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "PREBID_EVENT_TYPE_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}