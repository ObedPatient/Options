/**
 * Model representing a Currency option in the 'workspace' table 'currency' field  in the database.
 */
package rw.evolve.eprocurement.currency_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.currency_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "currency_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class CurrencyOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
