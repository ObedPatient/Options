package rw.evolve.eprocurement.ownership_nature.exception;

public class OwnershipNatureOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if OwnershipNature already exist
     */
    public OwnershipNatureOptionAlreadyExistException(String message){
        super(message);
    }
}
