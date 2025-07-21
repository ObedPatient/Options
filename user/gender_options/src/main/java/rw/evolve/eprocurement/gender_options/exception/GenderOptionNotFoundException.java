package rw.evolve.eprocurement.gender_options.exception;

public class GenderOptionNotFoundException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if gender not found
     */
    public GenderOptionNotFoundException(String message){
        super(message);
    }
}
