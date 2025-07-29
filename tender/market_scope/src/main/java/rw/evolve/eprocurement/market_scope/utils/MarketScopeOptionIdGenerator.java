/**
 * Utility class for generating unique identifier for MarketScope options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.market_scope.utils;

import java.util.Random;

public class MarketScopeOptionIdGenerator {
    /**
     * Generates a unique identifier for a MarketScope option.
     * The ID         - is formatted as "MARKET_SCOPE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique MarketScope option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "MARKET_SCOPE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}