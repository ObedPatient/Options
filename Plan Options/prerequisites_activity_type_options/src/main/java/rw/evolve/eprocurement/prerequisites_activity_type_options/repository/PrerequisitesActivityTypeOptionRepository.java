/**
 * Repository interface for PrerequisitesActivityType option data access
 */
package rw.evolve.eprocurement.prerequisites_activity_type_options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.PrerequisitesActivityTypeOptionModel;

import java.util.List;

@Repository
public interface PrerequisitesActivityTypeOptionRepository extends JpaRepository<PrerequisitesActivityTypeOptionModel, String> {

    /**
     * @param name of the PrerequisitesActivityTypeOption to check if exists
     * @return True if PrerequisitesActivityTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {PrerequisitesActivityTypeOptionModel} entities that have not been soft-deleted.
     * Only PrerequisitesActivityType Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {PrerequisitesActivityTypeOptionModel} entities.
     */
    List<PrerequisitesActivityTypeOptionModel> findByDeletedAtIsNull();
}
