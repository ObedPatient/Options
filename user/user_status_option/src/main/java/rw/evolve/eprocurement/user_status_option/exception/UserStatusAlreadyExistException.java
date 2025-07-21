package rw.evolve.eprocurement.user_status_option.exception;

public class UserStatusAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if user status not found
     */
    public UserStatusAlreadyExistException(String message){
        super(message);
    }
}
