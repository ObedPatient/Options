package rw.evolve.eprocurement.archive_strategy.exception;

public class ArchiveStrategyOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if ArchiveStrategy already exist
     */
    public ArchiveStrategyOptionAlreadyExistException(String message){
        super(message);
    }
}
