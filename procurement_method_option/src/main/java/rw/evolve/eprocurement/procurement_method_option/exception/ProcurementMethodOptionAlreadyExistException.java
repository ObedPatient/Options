package rw.evolve.eprocurement.procurement_method_option.exception;

public class ProcurementMethodOptionAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if method already exist
     */
    public ProcurementMethodOptionAlreadyExistException(String message){
        super(message);
    }
}
