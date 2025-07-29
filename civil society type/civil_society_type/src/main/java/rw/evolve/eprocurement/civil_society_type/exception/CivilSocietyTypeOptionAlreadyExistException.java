package rw.evolve.eprocurement.civil_society_type.exception;

public class CivilSocietyTypeOptionAlreadyExistException extends RuntimeException{


    /**
     * Constructs a new exception with the specified message.
     * @param   - message the detail message
     * @return  - error message if CivilSocietyTypeOption already exist
     */
    public CivilSocietyTypeOptionAlreadyExistException(String message){
        super(message);
    }
}
