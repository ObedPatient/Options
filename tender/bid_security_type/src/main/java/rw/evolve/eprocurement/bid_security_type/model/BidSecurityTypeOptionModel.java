/**
 * Model representing a BidSecurityType option in the 'tender' table 'bid security type' field  in the database.
 */
package rw.evolve.eprocurement.bid_security_type.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.bid_security_type.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "bid_security_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class BidSecurityTypeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
