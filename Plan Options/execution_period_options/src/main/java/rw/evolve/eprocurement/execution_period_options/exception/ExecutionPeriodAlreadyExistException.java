package rw.evolve.eprocurement.execution_period_options.exception;

public class ExecutionPeriodAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if Execution period not found
     */

    public ExecutionPeriodAlreadyExistException(String message){
        super(message);
    }
}
