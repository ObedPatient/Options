package rw.evolve.eprocurement.prebid_event_type.exception;

public class PrebidEventTypeNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if PrebidEventType not found
     */

    public PrebidEventTypeNotFoundException(String message){
        super(message);
    }
}
