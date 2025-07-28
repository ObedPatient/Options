/**
 * Utility class for generating unique identifier for plan status options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.plan_status_option.utils;

import java.util.Random;

public class PlanStatusOptionIdGenerator {

    /**
     * Generates a unique identifier for a Plan status option.
     * The ID         - is formatted as "PLAN_STATUS_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique Plan status option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "PLAN_STATUS_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}
