/**
 * Model representing a LotBiddingEligibility option in the 'tender' table 'Lot bidding eligibility' field  in the database.
 */
package rw.evolve.eprocurement.lot_bidding_eligibility.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.lot_bidding_eligibility.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "lot_bidding_eligibility_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class LotBiddingEligibilityOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
