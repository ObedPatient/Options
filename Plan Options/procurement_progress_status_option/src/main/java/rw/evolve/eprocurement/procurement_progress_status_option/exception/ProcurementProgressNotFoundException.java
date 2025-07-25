package rw.evolve.eprocurement.procurement_progress_status_option.exception;

public class ProcurementProgressNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if ProcurementProgress not found
     */
    public ProcurementProgressNotFoundException(String message){
        super(message);
    }
}
