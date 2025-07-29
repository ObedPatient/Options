/**
 * Repository interface for EvaluationCriteriaPhase option data access
 */
package rw.evolve.eprocurement.evaluation_criteria_phase_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.model.EvaluationCriteriaPhaseOptionModel;


import java.util.List;

@Repository
public interface EvaluationCriteriaPhaseOptionRepository extends JpaRepository<EvaluationCriteriaPhaseOptionModel, String> {

    /**
     * @param name of the EvaluationCriteriaPhaseOption to check if exists
     * @return True   - if EvaluationCriteriaPhaseOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {EvaluationCriteriaPhaseOptionModel} model that have not been soft-deleted.
     * Only Scheme options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {EvaluationCriteriaPhaseOptionModel} entities.
     */
    List<EvaluationCriteriaPhaseOptionModel> findByDeletedAtIsNull();
}
