/**
 * Repository interface for Account type option data access
 */
package rw.evolve.eprocurement.plan_status_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.plan_status_option.model.PlanStatusOptionModel;

import java.util.List;

@Repository
public interface PlanStatusOptionRepositoy extends JpaRepository<PlanStatusOptionModel, String> {

    /**
     * @param name of the PlanStatusOption to check if exists
     * @return True if PlanStatusOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {PlanStatusOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {PlanStatusOptionModel} entities.
     */
    List<PlanStatusOptionModel> findByDeletedAtIsNull();
}
