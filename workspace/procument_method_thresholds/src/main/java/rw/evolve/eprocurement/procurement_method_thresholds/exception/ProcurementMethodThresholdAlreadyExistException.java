package rw.evolve.eprocurement.procurement_method_thresholds.exception;

public class ProcurementMethodThresholdAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if procurement method threshold already exist
     */
    public ProcurementMethodThresholdAlreadyExistException(String message){
        super(message);
    }
}
