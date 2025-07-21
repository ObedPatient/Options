/**
 * Repository interface for ExecutionPeriod option data access
 */
package rw.evolve.eprocurement.execution_period_options.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.execution_period_options.model.ExecutionPeriodOptionModel;

import java.util.List;

@Repository
public interface ExecutionPeriodOptionRepository extends JpaRepository<ExecutionPeriodOptionModel, Long> {


    /**
     * @param name of the ExecutionPeriodOption to check if exists
     * @return True if ExecutionPeriodOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ExecutionPeriodOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {ExecutionPeriodOptionModel} entities.
     */
    List<ExecutionPeriodOptionModel> findByDeletedAtIsNull();
}
