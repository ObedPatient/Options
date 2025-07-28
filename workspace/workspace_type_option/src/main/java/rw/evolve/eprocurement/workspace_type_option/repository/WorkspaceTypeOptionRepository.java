package rw.evolve.eprocurement.workspace_type_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.evolve.eprocurement.workspace_type_option.model.WorkspaceTypeOptionModel;

import java.util.List;

public interface WorkspaceTypeOptionRepository extends JpaRepository<WorkspaceTypeOptionModel, String> {

    /**
     * @param name of the SchemeOption to check if exists
     * @return True   - if SchemeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {WorkspaceTypeOptionModel} model that have not been soft-deleted.
     * Only Workspace type option with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {WorkspaceTypeOptionModel} entities.
     */
    List<WorkspaceTypeOptionModel> findByDeletedAtIsNull();
}
