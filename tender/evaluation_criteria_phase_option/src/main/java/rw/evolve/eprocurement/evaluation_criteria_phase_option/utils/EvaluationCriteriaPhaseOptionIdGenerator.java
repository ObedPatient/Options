/**
 * Utility class for generating unique identifier for EvaluationCriteriaPhase options in the e-procurement system.
 * The generated ID follow a specific format combining a timestamp and a random number.
 */
package rw.evolve.eprocurement.evaluation_criteria_phase_option.utils;

import java.util.Random;

public class EvaluationCriteriaPhaseOptionIdGenerator {
    /**
     * Generates a unique identifier for a EvaluationCriteriaPhase option.
     * The ID         - is formatted as "EVALUATION_CRITERIA_PHASE_OPT_{timestamp}_{randomNumber}", where the timestamp     *
     * @return        - A unique EvaluationCriteriaPhase option ID as a String.
     */
    public static String generateId() {
        return String.format(
                "EVALUATION_CRITERIA_PHASE_OPT_%s_%s",
                DateTimeUtil.getTimeStamp().replaceAll("[^a-zA-Z0-9]", ""),
                (new Random()).nextLong(1, 10_000_000)
        );
    }
}