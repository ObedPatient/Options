package rw.evolve.eprocurement.workflow_stage_status.exception;

public class WorkflowStageStatusOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if WorkflowStageStatus not found
     */

    public WorkflowStageStatusOptionNotFoundException(String message){
        super(message);
    }
}
