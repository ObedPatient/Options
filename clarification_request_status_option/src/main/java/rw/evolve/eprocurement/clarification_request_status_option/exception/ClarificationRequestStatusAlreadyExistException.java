package rw.evolve.eprocurement.clarification_request_status_option.exception;

public class ClarificationRequestStatusAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if ClarificationRequestStatus already exist
     */
    public ClarificationRequestStatusAlreadyExistException(String message){
        super(message);
    }
}
