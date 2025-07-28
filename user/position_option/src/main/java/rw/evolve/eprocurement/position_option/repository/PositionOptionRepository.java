/**
 * Repository interface for Position option data access
 */
package rw.evolve.eprocurement.position_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.position_option.model.PositionOptionModel;

import java.util.List;

@Repository
public interface PositionOptionRepository extends JpaRepository<PositionOptionModel, String> {

    /**
     * @param name of the PositionOption to check if exists
     * @return True   - if PositionOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {PositionOptionModel} model that have not been soft-deleted.
     * Only Position Option with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {PositionOptionModel} entities.
     */
    List<PositionOptionModel> findByDeletedAtIsNull();
}
