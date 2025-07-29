/**
 * Utility class for generating unique identifier for TenderRequiredDocumentType options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.tender_required_document_type.utils;

import java.util.Random;

public class TenderRequiredDocumentTypeIdGenerator {
    /**
     * Generates a unique identifier for a TenderRequiredDocumentType option.
     * The ID         - is formatted as "TENDER_REQUIRED_DOC_TYPE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique TenderRequiredDocumentType option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "TENDER_REQUIRED_DOC_TYPE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}