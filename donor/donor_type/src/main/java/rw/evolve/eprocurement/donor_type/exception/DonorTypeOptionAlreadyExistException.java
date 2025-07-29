package rw.evolve.eprocurement.donor_type.exception;

public class DonorTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if DonorTypeOption already exist
     */
    public DonorTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
