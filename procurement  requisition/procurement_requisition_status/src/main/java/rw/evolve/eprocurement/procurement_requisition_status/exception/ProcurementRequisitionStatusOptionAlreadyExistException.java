package rw.evolve.eprocurement.procurement_requisition_status.exception;

public class ProcurementRequisitionStatusOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if ProcurementRequisitionStatus already exist
     */
    public ProcurementRequisitionStatusOptionAlreadyExistException(String message){
        super(message);
    }
}
