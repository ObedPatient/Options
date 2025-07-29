/**
 * Repository interface for TenderStatus option data access
 */
package rw.evolve.eprocurement.tender_status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.tender_status.model.TenderStatusOptionModel;

import java.util.List;

@Repository
public interface TenderStatusOptionRepository extends JpaRepository<TenderStatusOptionModel, String> {

    /**
     * @param name of the TenderStatus to check if exists
     * @return True   - if TenderStatus exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {TenderStatusOptionModel} model that have not been soft-deleted.
     * Only TenderStatus options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {TenderStatusOptionModel} entities.
     */
    List<TenderStatusOptionModel> findByDeletedAtIsNull();
}
