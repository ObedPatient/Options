/**
 * Repository interface for ClarificationRequestStatus option data access
 */
package rw.evolve.eprocurement.clarification_request_status_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.clarification_request_status_option.model.ClarificationRequestStatusOptionModel;

import java.util.List;

@Repository
public interface ClarificationRequestStatusOptionRepository extends JpaRepository<ClarificationRequestStatusOptionModel, String> {

    /**
     * @param name of the ClarificationRequestStatus to check if exists
     * @return True   - if ClarificationRequestStatus exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ClarificationRequestStatusOptionModel} model that have not been soft-deleted.
     * Only ClarificationRequestStatus options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {ClarificationRequestStatusOptionModel} entities.
     */
    List<ClarificationRequestStatusOptionModel> findByDeletedAtIsNull();
}
