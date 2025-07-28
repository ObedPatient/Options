package rw.evolve.eprocurement.ownership_nature.exception;

public class OwnershipNatureOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if OwnershipNature not found
     */

    public OwnershipNatureOptionNotFoundException(String message){
        super(message);
    }
}
