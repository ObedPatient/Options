/**
 * Utility class for generating unique identifier for procurement options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.procurement_method_option.utils;

import java.util.Random;

public class ProcurementMethodOptionIdGenerator {

    /**
     * Generates a unique identifier for a procurement option.
     * The ID         - is formatted as "PROCURE_METHOD_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique procurement option ID as a String.
     */

    public static String generateId() {
        return String.format(
                "PROCURE_METHOD_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}
