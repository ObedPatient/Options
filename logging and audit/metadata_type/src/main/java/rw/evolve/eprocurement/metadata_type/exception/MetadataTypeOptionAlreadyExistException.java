package rw.evolve.eprocurement.metadata_type.exception;

public class MetadataTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if MetadataType already exist
     */
    public MetadataTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
