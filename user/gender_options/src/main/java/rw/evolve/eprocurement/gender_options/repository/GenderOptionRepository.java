/**
 * Repository interface for Gender option data access
 */
package rw.evolve.eprocurement.gender_options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.gender_options.model.GenderOptionModel;

import java.util.List;

@Repository
public interface GenderOptionRepository extends JpaRepository<GenderOptionModel, String> {

    /**
     * @param name of the GenderOption to check if exists
     * @return True if GenderOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {GenderOptionModel} model that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {GenderOptionModel} model.
     */
    List<GenderOptionModel> findByDeletedAtIsNull();

}
