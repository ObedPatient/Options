package rw.evolve.eprocurement.country.exception;

public class CountryOptionAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if country already exist
     */
    public CountryOptionAlreadyExistException(String message){
        super(message);
    }

}
