/**
 * Repository interface for Business category option data access
 */
package rw.evolve.eprocurement.business_category_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.business_category_option.model.BusinessCategoryOptionModel;

import java.util.List;

@Repository
public interface BusinessCategoryOptionRepository extends JpaRepository<BusinessCategoryOptionModel, String> {

    /**
     * @param name of the BusinessCategoryOption to check if exists
     * @return True   - if BusinessCategoryOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {BusinessCategoryOptionModel} model that have not been soft-deleted.
     * Only Business category options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {BusinessCategoryOptionModel} entities.
     */
    List<BusinessCategoryOptionModel> findByDeletedAtIsNull();
}
