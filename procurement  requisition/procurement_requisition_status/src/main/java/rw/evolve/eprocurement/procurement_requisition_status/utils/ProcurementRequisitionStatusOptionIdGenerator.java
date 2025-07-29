/**
 * Utility class for generating unique identifier for ProcurementRequisitionStatus options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.procurement_requisition_status.utils;

import java.util.Random;

public class ProcurementRequisitionStatusOptionIdGenerator {
    /**
     * Generates a unique identifier for a ProcurementRequisitionStatus option.
     * The ID         - is formatted as "PROCURE_REQUISITION_STATUS_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique ProcurementRequisitionStatus option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "PROCURE_REQUISITION_STATUS_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}