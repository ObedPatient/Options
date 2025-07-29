package rw.evolve.eprocurement.tender_stage.exception;

public class TenderStageOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if tender stage not found
     */

    public TenderStageOptionNotFoundException(String message){
        super(message);
    }
}
