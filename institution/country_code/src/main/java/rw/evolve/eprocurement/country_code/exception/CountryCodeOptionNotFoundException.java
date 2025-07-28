package rw.evolve.eprocurement.country_code.exception;

public class CountryCodeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if scheme not found
     */

    public CountryCodeOptionNotFoundException(String message){
        super(message);
    }
}
