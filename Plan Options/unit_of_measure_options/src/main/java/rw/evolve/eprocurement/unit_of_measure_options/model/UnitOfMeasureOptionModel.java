/**
 * Entity representing a UnitOfMeasure option in the database.
 */
package rw.evolve.eprocurement.unit_of_measure_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.unit_of_measure_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "unit_of_measure_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class UnitOfMeasureOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the UnitOfMeasure option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_of_measure_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the UnitOfMeasure option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the UnitOfMeasure option. */
    @Column(name = "description", nullable = false)
    private String description;
}
