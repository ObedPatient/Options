package rw.evolve.eprocurement.workflow_stage_status.exception;

public class WorkflowStageStatusOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if WorkflowStageStatus already exist
     */
    public WorkflowStageStatusOptionAlreadyExistException(String message){
        super(message);
    }
}
