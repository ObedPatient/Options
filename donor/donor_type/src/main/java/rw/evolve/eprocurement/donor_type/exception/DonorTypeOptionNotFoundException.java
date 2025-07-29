package rw.evolve.eprocurement.donor_type.exception;

public class DonorTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if DonorTypeOption not found
     */

    public DonorTypeOptionNotFoundException(String message){
        super(message);
    }
}
