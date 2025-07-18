package rw.evolve.eprocurement.plan_status_option.exception;

public class PlanStatusAlreadExistException extends RuntimeException{
    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if scheme not found
     */

    public PlanStatusAlreadExistException(String message){
        super(message);
    }
}
