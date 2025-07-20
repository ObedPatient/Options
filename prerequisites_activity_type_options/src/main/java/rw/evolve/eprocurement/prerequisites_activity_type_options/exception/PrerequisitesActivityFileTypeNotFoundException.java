package rw.evolve.eprocurement.prerequisites_activity_type_options.exception;

public class PrerequisitesActivityFileTypeNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if file type already exist
     */

    public PrerequisitesActivityFileTypeNotFoundException(String message){
        super(message);
    }

}
