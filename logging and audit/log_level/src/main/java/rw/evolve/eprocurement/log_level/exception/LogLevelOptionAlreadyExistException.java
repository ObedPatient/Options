package rw.evolve.eprocurement.log_level.exception;

public class LogLevelOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if LogLevel already exist
     */
    public LogLevelOptionAlreadyExistException(String message){
        super(message);
    }
}
