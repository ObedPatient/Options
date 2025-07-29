package rw.evolve.eprocurement.tender_stage.exception;

public class TenderStageOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if Tender stage already exist
     */
    public TenderStageOptionAlreadyExistException(String message){
        super(message);
    }
}
