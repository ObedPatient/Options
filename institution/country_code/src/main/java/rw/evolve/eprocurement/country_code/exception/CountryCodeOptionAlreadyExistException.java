package rw.evolve.eprocurement.country_code.exception;

public class CountryCodeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if scheme already exist
     */
    public CountryCodeOptionAlreadyExistException(String message){
        super(message);
    }
}
