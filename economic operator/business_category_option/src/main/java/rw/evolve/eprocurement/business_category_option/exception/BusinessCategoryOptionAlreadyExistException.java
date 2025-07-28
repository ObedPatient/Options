package rw.evolve.eprocurement.business_category_option.exception;

public class BusinessCategoryOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if business category already exist
     */
    public BusinessCategoryOptionAlreadyExistException(String message){
        super(message);
    }
}
