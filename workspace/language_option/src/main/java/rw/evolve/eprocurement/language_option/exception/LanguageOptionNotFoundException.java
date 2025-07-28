package rw.evolve.eprocurement.language_option.exception;

public class LanguageOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if scheme not found
     */

    public LanguageOptionNotFoundException(String message){
        super(message);
    }
}
