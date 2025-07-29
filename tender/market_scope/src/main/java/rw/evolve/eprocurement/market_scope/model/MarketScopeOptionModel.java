/**
 * Model representing a MarketScope option in the 'tender' table 'market scope' field  in the database.
 */
package rw.evolve.eprocurement.market_scope.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.market_scope.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "market_scope_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class MarketScopeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
