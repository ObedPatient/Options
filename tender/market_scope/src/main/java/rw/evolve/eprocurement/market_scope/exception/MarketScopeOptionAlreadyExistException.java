package rw.evolve.eprocurement.market_scope.exception;

public class MarketScopeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if MarketScope already exist
     */
    public MarketScopeOptionAlreadyExistException(String message){
        super(message);
    }
}
