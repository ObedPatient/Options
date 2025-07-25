package rw.evolve.eprocurement.source_of_fund_option.exception;

public class SourceOfFundAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if SourceOfFund already exist
     */
    public SourceOfFundAlreadyExistException(String message){
        super(message);
    }
}
