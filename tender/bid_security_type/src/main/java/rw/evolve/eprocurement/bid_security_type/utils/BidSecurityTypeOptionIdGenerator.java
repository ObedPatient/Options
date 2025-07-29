/**
 * Utility class for generating unique identifier for BidSecurityType options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.bid_security_type.utils;

import java.util.Random;

public class BidSecurityTypeOptionIdGenerator {
    /**
     * Generates a unique identifier for a BidSecurityType option.
     * The ID         - is formatted as "BID_SECURITY_TYPE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique BidSecurityType option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "BID_SECURITY_TYPE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}