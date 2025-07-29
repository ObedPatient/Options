/**
 * Repository interface for LotBiddingEligibility option data access
 */
package rw.evolve.eprocurement.lot_bidding_eligibility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.lot_bidding_eligibility.model.LotBiddingEligibilityOptionModel;

import java.util.List;

@Repository
public interface LotBiddingEligibilityOptionRepository extends JpaRepository<LotBiddingEligibilityOptionModel, String> {

    /**
     * @param name of the LotBiddingEligibilityOption to check if exists
     * @return True   - if LotBiddingEligibilityOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {LotBiddingEligibilityOptionModel} model that have not been soft-deleted.
     * Only LotBiddingEligibility options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {LotBiddingEligibilityOptionModel} entities.
     */
    List<LotBiddingEligibilityOptionModel> findByDeletedAtIsNull();
}
