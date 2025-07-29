package rw.evolve.eprocurement.evaluation_criteria_phase_option.exception;

public class EvaluationCriteriaPhaseAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if EvaluationCriteriaPhase already exist
     */
    public EvaluationCriteriaPhaseAlreadyExistException(String message){
        super(message);
    }
}
