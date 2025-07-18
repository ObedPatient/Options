/**
 * Custom Exception for account type that already exists
 */
package rw.evolve.eprocurement.account_type_option.exception;

public class AccountTypeExistException extends RuntimeException{
    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the detail message
     */
    public AccountTypeExistException(String message){
        super(message);
    }
}
