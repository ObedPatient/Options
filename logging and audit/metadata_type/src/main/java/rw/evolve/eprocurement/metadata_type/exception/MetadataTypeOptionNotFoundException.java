package rw.evolve.eprocurement.metadata_type.exception;

public class MetadataTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if MetadataType not found
     */

    public MetadataTypeOptionNotFoundException(String message){
        super(message);
    }
}
