package rw.evolve.eprocurement.unit_of_measure_options.exception;

public class UnitOfMeasureAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if UnitOfMeasure not found
     */
    public UnitOfMeasureAlreadyExistException(String message){
        super(message);
    }
}
