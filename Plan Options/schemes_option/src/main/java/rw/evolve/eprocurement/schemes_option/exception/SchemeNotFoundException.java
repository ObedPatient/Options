package rw.evolve.eprocurement.schemes_option.exception;

public class SchemeNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if scheme not found
     */

    public SchemeNotFoundException(String message){
        super(message);
    }
}
