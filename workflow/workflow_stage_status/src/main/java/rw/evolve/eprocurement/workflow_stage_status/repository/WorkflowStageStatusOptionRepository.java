package rw.evolve.eprocurement.workflow_stage_status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.workflow_stage_status.model.WorkflowStageStatusOptionModel;

import java.util.List;

@Repository
public interface WorkflowStageStatusOptionRepository extends JpaRepository<WorkflowStageStatusOptionModel, String> {

    /**
     * @param name of the WorkflowStageStatus to check if exists
     * @return True   - if WorkflowStageStatus exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {WorkflowStageStatusOptionModel} model that have not been soft-deleted.
     * Only WorkflowStageStatus options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {WorkflowStageStatusOptionModel} entities.
     */
    List<WorkflowStageStatusOptionModel> findByDeletedAtIsNull();
}
