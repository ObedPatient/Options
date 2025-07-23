/**
 * Repository interface for Scheme option data access
 */
package rw.evolve.eprocurement.schemes_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.schemes_option.model.SchemeOptionModel;

import java.util.List;

@Repository
public interface SchemeOptionRepository extends JpaRepository<SchemeOptionModel, String> {

    /**
     * @param name of the SchemeOption to check if exists
     * @return True if SchemeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {SchemeOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {SchemeOptionModel} entities.
     */
    List<SchemeOptionModel> findByDeletedAtIsNull();
}
