package rw.evolve.eprocurement.tender_status.exception;

public class TenderStatusOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if TenderStatus already exist
     */
    public TenderStatusOptionAlreadyExistException(String message){
        super(message);
    }
}
