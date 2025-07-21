/**
 * Entity representing a Scheme option in the database.
 */
package rw.evolve.eprocurement.schemes_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.schemes_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "scheme_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class SchemeOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the Scheme option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheme_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the Scheme option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the Scheme option. */
    @Column(name = "description", nullable = false)
    private String description;
}
