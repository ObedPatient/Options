/**
 * Utility class for generating unique identifier for scheme options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.procurement_method_thresholds.utils;

import java.util.Random;

public class ProcurementMethodThresholdIdGenerator {
    /**
     * Generates a unique identifier for a Procurement method threshold option.
     * The ID         - is formatted as "PROCURE_METHOD_THRESHOLD_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique Procurement method threshold option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "PROCURE_METHOD_THRESHOLD_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}