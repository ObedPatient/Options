package rw.evolve.eprocurement.reasons_option.exception;

public class ReasonNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if Reason not found
     */

    public ReasonNotFoundException(String message){
        super(message);
    }
}
