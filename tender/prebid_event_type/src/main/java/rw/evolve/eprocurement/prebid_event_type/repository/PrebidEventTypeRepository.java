/**
 * Repository interface for PrebidEventType option data access
 */
package rw.evolve.eprocurement.prebid_event_type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.prebid_event_type.model.PrebidEventTypeModel;

import java.util.List;

@Repository
public interface PrebidEventTypeRepository extends JpaRepository<PrebidEventTypeModel, String> {

    /**
     * @param name of the PrebidEventType to check if exists
     * @return True   - if PrebidEventType exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {PrebidEventTypeModel} model that have not been soft-deleted.
     * Only PrebidEventType options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {PrebidEventTypeModel} entities.
     */
    List<PrebidEventTypeModel> findByDeletedAtIsNull();
}
