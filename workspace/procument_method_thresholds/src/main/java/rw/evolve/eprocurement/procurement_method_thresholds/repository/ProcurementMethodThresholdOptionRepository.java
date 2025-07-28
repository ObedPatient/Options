/**
 * Repository interface for Procurement method threshold option data access
 */
package rw.evolve.eprocurement.procurement_method_thresholds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.procurement_method_thresholds.model.ProcurementMethodThresholdModel;

import java.util.List;

@Repository
public interface ProcurementMethodThresholdOptionRepository extends JpaRepository<ProcurementMethodThresholdModel, String> {

    /**
     * @param name of the ProcurementMethodThresholdOption to check if exists
     * @return True   - if ProcurementMethodThresholdOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ProcurementMethodThresholdOptionModel} model that have not been soft-deleted.
     * Only ProcurementMethodThreshold options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {ProcurementMethodThresholdOptionModel} entities.
     */
    List<ProcurementMethodThresholdModel> findByDeletedAtIsNull();
}
