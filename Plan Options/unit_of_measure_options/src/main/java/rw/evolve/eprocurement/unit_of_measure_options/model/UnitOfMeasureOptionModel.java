/**
 * Entity representing a UnitOfMeasure option in "item" table "unit of measure" field in the database.
 */
package rw.evolve.eprocurement.unit_of_measure_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.unit_of_measure_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "unit_of_measure_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class UnitOfMeasureOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;
}
