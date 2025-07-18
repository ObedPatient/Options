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
    List<ProcurementTypeOptionModel> findByDeletedAtIsNull();
}
