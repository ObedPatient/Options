/**
 * Utility class for generating unique identifier for scheme options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.organization_role_option.utils;

import java.util.Random;

public class OrganizationRoleOptionIdGenerator {
    /**
     * Generates a unique identifier for an organization role option.
     * The ID         - is formatted as "ORGANIZATION_ROLE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique organization role option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "ORGANIZATION_ROLE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}