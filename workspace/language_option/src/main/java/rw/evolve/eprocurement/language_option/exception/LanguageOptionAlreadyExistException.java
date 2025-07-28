package rw.evolve.eprocurement.language_option.exception;

public class LanguageOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if scheme already exist
     */
    public LanguageOptionAlreadyExistException(String message){
        super(message);
    }
}
