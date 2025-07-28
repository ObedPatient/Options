package rw.evolve.eprocurement.currency_option.exception;

public class CurrencyOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if scheme already exist
     */
    public CurrencyOptionAlreadyExistException(String message){
        super(message);
    }
}
