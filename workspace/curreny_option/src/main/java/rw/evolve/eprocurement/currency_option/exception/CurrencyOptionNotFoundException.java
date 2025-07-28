package rw.evolve.eprocurement.currency_option.exception;

public class CurrencyOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if scheme not found
     */

    public CurrencyOptionNotFoundException(String message){
        super(message);
    }
}
