package rw.evolve.eprocurement.tender_required_document_type.exception;

public class TenderRequiredDocumentTypeAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if TenderRequiredDocumentType already exist
     */
    public TenderRequiredDocumentTypeAlreadyExistException(String message){
        super(message);
    }
}
