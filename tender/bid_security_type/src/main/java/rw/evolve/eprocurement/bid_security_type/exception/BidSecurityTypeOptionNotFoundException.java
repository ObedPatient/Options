package rw.evolve.eprocurement.bid_security_type.exception;

public class BidSecurityTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if BidSecurityTypeOption not found
     */

    public BidSecurityTypeOptionNotFoundException(String message){
        super(message);
    }
}
