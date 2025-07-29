/**
 * Repository interface for BidSecurityType option data access
 */
package rw.evolve.eprocurement.bid_security_type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.bid_security_type.model.BidSecurityTypeOptionModel;

import java.util.List;

@Repository
public interface BidSecurityTypeOptionRepository extends JpaRepository<BidSecurityTypeOptionModel, String> {

    /**
     * @param name of the BidSecurityTypeOption to check if exists
     * @return True   - if BidSecurityTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {BidSecurityTypeOptionModel} model that have not been soft-deleted.
     * Only BidSecurityType options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {BidSecurityTypeOptionModel} entities.
     */
    List<BidSecurityTypeOptionModel> findByDeletedAtIsNull();
}
