/**
 * Entity representing a Gender option in the database.
 */
package rw.evolve.eprocurement.gender_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.gender_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "gender_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class GenderOptionModel extends GeneralTimestampBaseModel {


    /** Unique identifier for the Gender option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the Gender option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the Gender option. */
    @Column(name = "description", nullable = false)
    private String description;
}
