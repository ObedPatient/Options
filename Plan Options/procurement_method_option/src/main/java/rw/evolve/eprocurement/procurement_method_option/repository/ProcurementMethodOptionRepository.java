/**
 * Repository interface for Account type option data access
 */
package rw.evolve.eprocurement.procurement_method_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.procurement_method_option.model.ProcurementMethodOptionModel;

import java.util.List;

@Repository
public interface ProcurementMethodOptionRepository extends JpaRepository<ProcurementMethodOptionModel, Long> {

    /**
     * @param name of the ProcurementMethodOption to check if exists
     * @return True if ProcurementMethodOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ProcurementMethodOptionModel} entities that have not been soft-deleted.
     * Only Procurement method Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {ProcurementMethodOptionModel} entities.
     */
    List<ProcurementMethodOptionModel> findByDeletedAtIsNull();
}
