package rw.evolve.eprocurement.organization_role_option.exception;

public class OrganizationRoleOptionAlreadyExistException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if OrganizationRoleOption already exist
     */
    public OrganizationRoleOptionAlreadyExistException(String message){
        super(message);
    }
}
