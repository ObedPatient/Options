package rw.evolve.eprocurement.business_type_option.exception;

public class BusinessTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if BusinessType already exist
     */
    public BusinessTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
