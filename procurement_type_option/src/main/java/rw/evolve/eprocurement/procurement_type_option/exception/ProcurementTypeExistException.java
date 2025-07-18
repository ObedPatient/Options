package rw.evolve.eprocurement.procurement_type_option.exception;

public class ProcurementTypeExistException extends RuntimeException{
    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the detail message
     */
    public ProcurementTypeExistException(String message){
        super(message);
    }
}
