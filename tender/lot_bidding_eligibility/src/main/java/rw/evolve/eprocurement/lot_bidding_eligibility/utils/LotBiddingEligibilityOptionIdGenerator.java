/**
 * Utility class for generating unique identifier for LotBiddingEligibility options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.lot_bidding_eligibility.utils;

import java.util.Random;

public class LotBiddingEligibilityOptionIdGenerator {
    /**
     * Generates a unique identifier for a LotBiddingEligibility option.
     * The ID         - is formatted as "LOT_BID_ELIGIBILITY_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique LotBiddingEligibility option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "LOT_BID_ELIGIBILITY_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}