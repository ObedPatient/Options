/**
 * Repository interface for TenderStage option data access
 */
package rw.evolve.eprocurement.tender_stage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.tender_stage.model.TenderStageOptionModel;

import java.util.List;

@Repository
public interface TenderStageOptionRepository extends JpaRepository<TenderStageOptionModel, String> {

    /**
     * @param name of the TenderStageOption to check if exists
     * @return True   - if TenderStageOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {TenderStageOptionModel} model that have not been soft-deleted.
     * Only TenderStage options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {TenderStageOptionModel} entities.
     */
    List<TenderStageOptionModel> findByDeletedAtIsNull();
}
