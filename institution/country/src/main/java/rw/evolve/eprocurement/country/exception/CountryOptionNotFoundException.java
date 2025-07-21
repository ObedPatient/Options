package rw.evolve.eprocurement.country.exception;

public class CountryOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if country not found
     */
    public CountryOptionNotFoundException(String message){
        super(message);
    }
}
