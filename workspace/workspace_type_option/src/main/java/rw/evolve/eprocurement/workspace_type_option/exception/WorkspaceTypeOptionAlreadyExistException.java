package rw.evolve.eprocurement.workspace_type_option.exception;

public class WorkspaceTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if scheme already exist
     */
    public WorkspaceTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
