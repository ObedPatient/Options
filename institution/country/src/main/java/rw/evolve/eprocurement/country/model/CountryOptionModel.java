/**
 * Entity representing a Country option in the database.
 */
package rw.evolve.eprocurement.country.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.country.model.base.GeneralTimestampBaseModel;


@Data
@Entity
@Table(name = "country_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class CountryOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the Country option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the Country option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the Country option. */
    @Column(name = "description", nullable = false)
    private String description;
}
