package rw.evolve.eprocurement.procurement_progress_status_option.exception;

public class ProcurementProgressAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if ProcurementProgress already exist
     */

    public ProcurementProgressAlreadyExistException(String message){
        super(message);
    }
}
