package rw.evolve.eprocurement.evaluation_criteria_phase_option.exception;

public class EvaluationCriteriaPhaseNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if EvaluationCriteriaPhase not found
     */

    public EvaluationCriteriaPhaseNotFoundException(String message){
        super(message);
    }
}
