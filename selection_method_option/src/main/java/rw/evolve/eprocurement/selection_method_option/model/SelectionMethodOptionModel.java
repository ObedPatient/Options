/**
 * Entity representing a Selection method option in the database.
 */
package rw.evolve.eprocurement.selection_method_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.selection_method_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "selection_method_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class SelectionMethodOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the Selection method option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selection_method_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the method option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the method option. */
    @Column(name = "description", nullable = false)
    private String description;
}
