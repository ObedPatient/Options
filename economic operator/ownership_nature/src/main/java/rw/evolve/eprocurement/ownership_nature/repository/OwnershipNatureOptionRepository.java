/**
 * Repository interface for OwnershipNature option data access
 */
package rw.evolve.eprocurement.ownership_nature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.evolve.eprocurement.ownership_nature.model.OwnershipNatureOptionModel;

import java.util.List;

public interface OwnershipNatureOptionRepository extends JpaRepository<OwnershipNatureOptionModel, String> {


    /**
     * @param name of the OwnershipNatureOption to check if exists
     * @return True   - if OwnershipNatureOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {OwnershipNatureOptionModel} model that have not been soft-deleted.
     * Only OwnershipNature options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {OwnershipNatureOptionModel} model.
     */
    List<OwnershipNatureOptionModel> findByDeletedAtIsNull();
}
