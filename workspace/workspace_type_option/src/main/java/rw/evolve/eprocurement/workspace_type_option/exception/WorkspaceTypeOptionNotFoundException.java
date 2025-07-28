package rw.evolve.eprocurement.workspace_type_option.exception;

public class WorkspaceTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if scheme not found
     */

    public WorkspaceTypeOptionNotFoundException(String message){
        super(message);
    }
}
