package rw.evolve.eprocurement.schemes_option.exception;

public class SchemeAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if scheme already exist
     */
    public SchemeAlreadyExistException(String message){
        super(message);
    }
}
