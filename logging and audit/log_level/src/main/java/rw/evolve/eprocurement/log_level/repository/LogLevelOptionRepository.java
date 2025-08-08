/**
 * Repository interface for LogLevel option data access
 */
package rw.evolve.eprocurement.log_level.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.log_level.model.LogLevelOptionModel;

import java.util.List;

@Repository
public interface LogLevelOptionRepository extends JpaRepository<LogLevelOptionModel, String> {

    /**
     * @param name of the LogLevelOption to check if exists
     * @return True   - if LogLevelOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {LogLevelOptionModel} model that have not been soft-deleted.
     * Only LogLevel Option with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {LogLevelOptionModel} entities.
     */
    List<LogLevelOptionModel> findByDeletedAtIsNull();
}
