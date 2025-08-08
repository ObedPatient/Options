package rw.evolve.eprocurement.theme_status.exception;

public class ThemeStatusOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if ThemeStatus already exist
     */
    public ThemeStatusOptionAlreadyExistException(String message){
        super(message);
    }
}
