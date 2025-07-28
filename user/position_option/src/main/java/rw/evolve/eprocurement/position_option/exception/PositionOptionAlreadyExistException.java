package rw.evolve.eprocurement.position_option.exception;

public class PositionOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if Position already exist
     */
    public PositionOptionAlreadyExistException(String message){
        super(message);
    }
}
