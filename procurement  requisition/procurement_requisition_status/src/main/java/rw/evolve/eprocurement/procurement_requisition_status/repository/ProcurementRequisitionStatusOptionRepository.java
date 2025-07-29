/**
 * Repository interface for ProcurementRequisitionStatus option data access
 */
package rw.evolve.eprocurement.procurement_requisition_status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.procurement_requisition_status.model.ProcurementRequisitionStatusOptionModel;

import java.util.List;

@Repository
public interface ProcurementRequisitionStatusOptionRepository extends JpaRepository<ProcurementRequisitionStatusOptionModel, String> {

    /**
     * @param name of the ProcurementRequisitionStatusOption to check if exists
     * @return True   - if ProcurementRequisitionStatusOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ProcurementRequisitionStatusOptionModel} model that have not been soft-deleted.
     * Only ProcurementRequisitionStatus options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {ProcurementRequisitionStatusOptionModel} entities.
     */
    List<ProcurementRequisitionStatusOptionModel> findByDeletedAtIsNull();


}
