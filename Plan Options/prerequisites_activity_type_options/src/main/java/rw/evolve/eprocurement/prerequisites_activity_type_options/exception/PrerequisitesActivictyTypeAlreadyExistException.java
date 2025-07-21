package rw.evolve.eprocurement.prerequisites_activity_type_options.exception;

public class PrerequisitesActivictyTypeAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if file type not found
     */

    public PrerequisitesActivictyTypeAlreadyExistException(String message){
        super(message);
    }
}
