package rw.evolve.eprocurement.authority_type_option.exception;

public class AuthorityTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if scheme already exist
     */
    public AuthorityTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
