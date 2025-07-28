package rw.evolve.eprocurement.business_type_option.exception;

public class BusinessTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if BusinessType not found
     */

    public BusinessTypeOptionNotFoundException(String message){
        super(message);
    }
}
