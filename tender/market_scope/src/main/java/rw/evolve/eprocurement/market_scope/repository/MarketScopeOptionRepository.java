/**
 * Repository interface for MarketScope option data access
 */
package rw.evolve.eprocurement.market_scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.market_scope.model.MarketScopeOptionModel;

import java.util.List;

@Repository
public interface MarketScopeOptionRepository extends JpaRepository<MarketScopeOptionModel, String> {

    /**
     * @param name of the MarketScope to check if exists
     * @return True   - if MarketScope exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {MarketScopeOptionModel} model that have not been soft-deleted.
     * Only MarketScope options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {MarketScopeOptionModel} entities.
     */
    List<MarketScopeOptionModel> findByDeletedAtIsNull();
}
