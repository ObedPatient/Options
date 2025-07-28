/**
 * Repository interface for Business type option data access
 */
package rw.evolve.eprocurement.business_type_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.business_type_option.model.BusinessTypeOptionModel;

import java.util.List;

@Repository
public interface BusinessTypeOptionRepository extends JpaRepository<BusinessTypeOptionModel, String> {

    /**
     * @param name of the BusinessTypeOption to check if exists
     * @return True   - if BusinessTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {BusinessTypeOptionModel} model that have not been soft-deleted.
     * Only BusinessType options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {BusinessTypeOptionModel} entities.
     */
    List<BusinessTypeOptionModel> findByDeletedAtIsNull();
}
