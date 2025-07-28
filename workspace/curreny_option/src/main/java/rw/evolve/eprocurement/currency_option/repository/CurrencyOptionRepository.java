/**
 * Repository interface for currency option data access
 */
package rw.evolve.eprocurement.currency_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.currency_option.model.CurrencyOptionModel;

import java.util.List;

@Repository
public interface CurrencyOptionRepository extends JpaRepository<CurrencyOptionModel, String> {

    /**
     * @param name of the CurrencyOption to check if exists
     * @return True   - if CurrencyOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {CurrencyOptionModel} model that have not been soft-deleted.
     * Only Currency options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {CurrencyOptionModel} entities.
     */
    List<CurrencyOptionModel> findByDeletedAtIsNull();
}
