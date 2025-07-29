package rw.evolve.eprocurement.civil_society_type.exception;

public class CivilSocietyTypeOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param    - message the detail message
     * @return   - error message if CivilSocietyTypeOption not found
     */

    public CivilSocietyTypeOptionNotFoundException(String message){
        super(message);
    }
}
