package rw.evolve.eprocurement.user_status_option.exception;

public class UserStatusNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if user status already exist
     */
    public UserStatusNotFoundException(String message){
        super(message);
    }
}
