package rw.evolve.eprocurement.reasons_option.exception;

public class ReasonAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if Reason already exist
     */
    public ReasonAlreadyExistException(String message){
        super(message);
    }
}
