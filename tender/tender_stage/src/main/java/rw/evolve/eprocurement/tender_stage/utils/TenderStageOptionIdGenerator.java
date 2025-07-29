/**
 * Utility class for generating unique identifier for tender stage options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.tender_stage.utils;

import java.util.Random;

public class TenderStageOptionIdGenerator {
    /**
     * Generates a unique identifier for a tender stage option.
     * The ID         - is formatted as "TENDER_STAGE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique tender stage option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "SCHEME_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}