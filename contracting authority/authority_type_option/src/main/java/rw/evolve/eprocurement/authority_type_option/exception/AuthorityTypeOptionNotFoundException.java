package rw.evolve.eprocurement.authority_type_option.exception;

public class AuthorityTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if scheme not found
     */

    public AuthorityTypeOptionNotFoundException(String message){
        super(message);
    }
}
