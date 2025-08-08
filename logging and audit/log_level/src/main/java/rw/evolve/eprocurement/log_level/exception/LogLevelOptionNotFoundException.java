package rw.evolve.eprocurement.log_level.exception;

public class LogLevelOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if Position not found
     */

    public LogLevelOptionNotFoundException(String message){
        super(message);
    }
}
