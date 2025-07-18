package rw.evolve.eprocurement.selection_method_option.exception;

public class SelectionMethodAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if method already exist
     */

    public SelectionMethodAlreadyExistException(String message){
        super(message);
    }
}
