/**
 * Repository interface for OrganizationRoleOption option data access
 */
package rw.evolve.eprocurement.organization_role_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.organization_role_option.model.OrganizationRoleOptionModel;

import java.util.List;

@Repository
public interface OrganizationRoleOptionRepository extends JpaRepository<OrganizationRoleOptionModel, String> {


    /**
     * @param name of the OrganizationRoleOption to check if exists
     * @return True if OrganizationRoleOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {OrganizationRoleOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {OrganizationRoleOptionModel} entities.
     */
    List<OrganizationRoleOptionModel> findByDeletedAtIsNull();
}
