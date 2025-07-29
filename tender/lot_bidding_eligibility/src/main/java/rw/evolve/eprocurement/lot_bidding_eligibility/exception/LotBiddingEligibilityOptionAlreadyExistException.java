package rw.evolve.eprocurement.lot_bidding_eligibility.exception;

public class LotBiddingEligibilityOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if MarketScope already exist
     */
    public LotBiddingEligibilityOptionAlreadyExistException(String message){
        super(message);
    }
}
