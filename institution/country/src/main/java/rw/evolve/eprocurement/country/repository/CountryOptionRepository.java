/**
 * Repository interface for Country option data access
 */
package rw.evolve.eprocurement.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.country.model.CountryOptionModel;

import java.util.List;

@Repository
public interface CountryOptionRepository extends JpaRepository<CountryOptionModel, String> {


    /**
     * @param name of the CountryOption to check if exists
     * @return True if CountryOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {CountryOptionModel} model that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {CountryOptionModel} entities.
     */
    List<CountryOptionModel> findByDeletedAtIsNull();
}
