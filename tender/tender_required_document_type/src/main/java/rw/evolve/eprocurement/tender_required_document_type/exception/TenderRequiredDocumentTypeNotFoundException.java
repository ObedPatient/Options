package rw.evolve.eprocurement.tender_required_document_type.exception;

public class TenderRequiredDocumentTypeNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if TenderRequiredDocumentType not found
     */

    public TenderRequiredDocumentTypeNotFoundException(String message){
        super(message);
    }
}
