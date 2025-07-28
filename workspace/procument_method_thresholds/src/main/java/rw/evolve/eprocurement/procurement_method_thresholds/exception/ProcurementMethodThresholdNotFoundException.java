package rw.evolve.eprocurement.procurement_method_thresholds.exception;

public class ProcurementMethodThresholdNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if procurement method threshold not found
     */

    public ProcurementMethodThresholdNotFoundException(String message){
        super(message);
    }
}
