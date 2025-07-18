/**
 * Repository interface for Account type option data access
 */
package rw.evolve.eprocurement.procurement_type_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.procurement_type_option.model.ProcurementTypeOptionModel;

import java.util.List;

@Repository
public interface ProcurementTypeOptionRepository extends JpaRepository<ProcurementTypeOptionModel, Long> {
    /**
     * @param name of the ProcurementTypeOption to check if exists
     * @return True if ProcurementTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ProcurementTypeOptionModel} entities that have not been soft-deleted.
     * Only Procurement Type Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {ProcurementTypeOptionModel} entities.
     */
    List<ProcurementTypeOptionModel> findByDeletedAtIsNull();
}
