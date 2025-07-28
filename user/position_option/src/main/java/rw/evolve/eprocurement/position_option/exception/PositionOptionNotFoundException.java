package rw.evolve.eprocurement.position_option.exception;

public class PositionOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if Position not found
     */

    public PositionOptionNotFoundException(String message){
        super(message);
    }
}
