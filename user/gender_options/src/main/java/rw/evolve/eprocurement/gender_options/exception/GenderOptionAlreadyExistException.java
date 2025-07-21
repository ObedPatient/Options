package rw.evolve.eprocurement.gender_options.exception;

public class GenderOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if gender already exist
     */

    public GenderOptionAlreadyExistException(String message){
        super(message);
    }
}
