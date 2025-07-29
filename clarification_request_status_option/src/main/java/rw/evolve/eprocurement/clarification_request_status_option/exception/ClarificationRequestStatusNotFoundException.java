package rw.evolve.eprocurement.clarification_request_status_option.exception;

public class ClarificationRequestStatusNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if ClarificationRequestStatus not found
     */

    public ClarificationRequestStatusNotFoundException(String message){
        super(message);
    }
}
