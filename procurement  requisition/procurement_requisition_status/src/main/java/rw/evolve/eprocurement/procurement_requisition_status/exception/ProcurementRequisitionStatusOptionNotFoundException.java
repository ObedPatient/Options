package rw.evolve.eprocurement.procurement_requisition_status.exception;

public class ProcurementRequisitionStatusOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if ProcurementRequisitionStatus not found
     */

    public ProcurementRequisitionStatusOptionNotFoundException(String message){
        super(message);
    }
}
