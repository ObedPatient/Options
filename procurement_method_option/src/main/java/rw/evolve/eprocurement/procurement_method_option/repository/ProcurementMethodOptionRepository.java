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
     * @param name of the AccountTypeOption to check if exists
     * @return True if AccountTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {AccountTypeOptionModel} entities that have not been soft-deleted.
     * Only Account Type Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {AccountTypeOptionModel} entities.
     */
    List<ProcurementMethodOptionModel> findByDeletedAtIsNull();
}
