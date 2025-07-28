/**
 * Repository interface for Scheme option data access
 */
package rw.evolve.eprocurement.country_code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.country_code.model.CountryCodeOptionModel;

import java.util.List;

@Repository
public interface CountryCodeOptionRepository extends JpaRepository<CountryCodeOptionModel, String> {

    /**
     * @param name of the CountryCodeOption to check if exists
     * @return True   - if CountryCodeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {CountryCodeOptionModel} model that have not been soft-deleted.
     * Only CountryCode options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {CountryCodeOptionModel} entities.
     */
    List<CountryCodeOptionModel> findByDeletedAtIsNull();
}
