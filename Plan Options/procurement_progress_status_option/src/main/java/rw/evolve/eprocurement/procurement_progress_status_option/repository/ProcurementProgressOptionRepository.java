package rw.evolve.eprocurement.procurement_progress_status_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.evolve.eprocurement.procurement_progress_status_option.model.ProcurementProgressOptionModel;

import java.util.List;

public interface ProcurementProgressOptionRepository extends JpaRepository<ProcurementProgressOptionModel, String> {

    /**
     * @param name of the ProcurementProgressOption to check if exists
     * @return True if ProcurementProgressOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ProcurementProgressOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {ProcurementProgressOptionModel} entities.
     */
    List<ProcurementProgressOptionModel> findByDeletedAtIsNull();
}
