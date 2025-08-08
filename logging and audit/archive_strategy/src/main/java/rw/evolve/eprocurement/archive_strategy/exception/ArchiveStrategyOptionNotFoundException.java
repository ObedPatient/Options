package rw.evolve.eprocurement.archive_strategy.exception;

public class ArchiveStrategyOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if ArchiveStrategy not found
     */

    public ArchiveStrategyOptionNotFoundException(String message){
        super(message);
    }
}
