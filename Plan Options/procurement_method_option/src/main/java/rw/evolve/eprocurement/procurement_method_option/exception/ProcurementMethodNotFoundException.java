package rw.evolve.eprocurement.procurement_method_option.exception;

public class ProcurementMethodNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if method not found
     */
    public ProcurementMethodNotFoundException(String message){
        super(message);
    }
}
