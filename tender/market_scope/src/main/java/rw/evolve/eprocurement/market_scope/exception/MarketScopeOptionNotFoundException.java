package rw.evolve.eprocurement.market_scope.exception;

public class MarketScopeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if MarketScope not found
     */

    public MarketScopeOptionNotFoundException(String message){
        super(message);
    }
}
