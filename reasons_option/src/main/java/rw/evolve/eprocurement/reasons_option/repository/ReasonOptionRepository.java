/**
 * Repository interface for Reason option data access
 */
package rw.evolve.eprocurement.reasons_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.reasons_option.model.ReasonOptionModel;

import java.util.List;

@Repository
public interface ReasonOptionRepository extends JpaRepository<ReasonOptionModel, String> {

    /**
     * @param name of the ReasonOption to check if exists
     * @return True   - if ReasonOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ReasonOptionModel} model that have not been soft-deleted.
     * Only Reason options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {ReasonOptionModel} entities.
     */
    List<ReasonOptionModel> findByDeletedAtIsNull();
}
