package rw.evolve.eprocurement.tender_status.exception;

public class TenderStatusOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if TenderStatus not found
     */

    public TenderStatusOptionNotFoundException(String message){
        super(message);
    }
}
