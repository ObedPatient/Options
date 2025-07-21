package rw.evolve.eprocurement.unit_of_measure_options.exception;

public class UnitOfMeasureNotFoundException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if UnitOfMeasure already exist
     */
    public UnitOfMeasureNotFoundException(String message){
        super(message);
    }
}
