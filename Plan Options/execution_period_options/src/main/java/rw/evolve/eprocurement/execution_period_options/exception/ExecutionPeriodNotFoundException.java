package rw.evolve.eprocurement.execution_period_options.exception;

public class ExecutionPeriodNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if Execution period already exist
     */
    public ExecutionPeriodNotFoundException(String message){
        super(message);
    }
}
