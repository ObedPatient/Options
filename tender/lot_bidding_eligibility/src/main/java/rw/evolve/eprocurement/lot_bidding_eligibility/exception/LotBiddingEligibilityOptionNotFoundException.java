package rw.evolve.eprocurement.lot_bidding_eligibility.exception;

public class LotBiddingEligibilityOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if MarketScope not found
     */

    public LotBiddingEligibilityOptionNotFoundException(String message){
        super(message);
    }
}
