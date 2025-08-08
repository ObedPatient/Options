/**
 * Repository interface for ArchiveStrategy option data access
 */
package rw.evolve.eprocurement.archive_strategy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.archive_strategy.model.ArchiveStrategyOptionModel;

import java.util.List;

@Repository
public interface ArchiveStrategyOptionRepository extends JpaRepository<ArchiveStrategyOptionModel, String> {

    /**
     * @param name of the ArchiveStrategyOption to check if exists
     * @return True   - if ArchiveStrategyOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ArchiveStrategyOptionModel} model that have not been soft-deleted.
     * Only ArchiveStrategy Option with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {ArchiveStrategyOptionModel} entities.
     */
    List<ArchiveStrategyOptionModel> findByDeletedAtIsNull();
}
