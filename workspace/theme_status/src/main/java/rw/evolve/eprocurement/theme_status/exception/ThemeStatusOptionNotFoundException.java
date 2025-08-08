package rw.evolve.eprocurement.theme_status.exception;

public class ThemeStatusOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if ThemeStatus not found
     */

    public ThemeStatusOptionNotFoundException(String message){
        super(message);
    }
}
