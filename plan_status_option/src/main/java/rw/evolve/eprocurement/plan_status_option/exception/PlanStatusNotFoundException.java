package rw.evolve.eprocurement.plan_status_option.exception;

public class PlanStatusNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if scheme already exist
     */

    public PlanStatusNotFoundException(String message){
        super(message);
    }
}
