package rw.evolve.eprocurement.bid_security_type.exception;

public class BidSecurityTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if BidSecurityType already exist
     */
    public BidSecurityTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
