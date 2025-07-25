package rw.evolve.eprocurement.source_of_fund_option.exception;

public class SourceOfFundNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if SourceOfFund not found
     */
    public SourceOfFundNotFoundException(String message){
        super(message);
    }
}
