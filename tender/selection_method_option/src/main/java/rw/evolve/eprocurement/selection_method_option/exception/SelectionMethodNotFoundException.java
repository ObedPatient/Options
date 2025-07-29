package rw.evolve.eprocurement.selection_method_option.exception;

public class SelectionMethodNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if scheme not found
     */

    public SelectionMethodNotFoundException(String message){
        super(message);
    }
}
