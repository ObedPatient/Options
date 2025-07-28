package rw.evolve.eprocurement.organization_role_option.exception;

public class OrganizationRoleOptionNotFoundException extends RuntimeException{

    /**
     * Constructs a new exception with the specified message.
     * @param message the detail message
     * @return error message if OrganizationRoleOption not found
     */
    public OrganizationRoleOptionNotFoundException(String message){
        super(message);
    }
}
