/**
 * Utility class for generating unique identifier for WorkflowStageStatus options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.workflow_stage_status.utils;

import java.util.Random;

public class WorkflowStageStatusOptionIdGenerator {
    /**
     * Generates a unique identifier for a WorkflowStageStatus option.
     * The ID         - is formatted as "WORKFLOW_STAGE_STATUS_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique WorkflowStageStatus option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "WORKFLOW_STAGE_STATUS_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}