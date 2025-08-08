/**
 * Utility class for generating unique identifier for MetadataType options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.metadata_type.utils;

import java.util.Random;

public class MetadataTypeOptionIdGenerator {
    /**
     * Generates a unique identifier for a MetadataType option.
     * The ID         - is formatted as "METADATA_TYPE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique MetadataType option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "METADATA_TYPE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}