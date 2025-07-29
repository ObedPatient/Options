package rw.evolve.eprocurement.prebid_event_type.exception;

public class PrebidEventTypeAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if PrebidEventType already exist
     */
    public PrebidEventTypeAlreadyExistException(String message){
        super(message);
    }
}
