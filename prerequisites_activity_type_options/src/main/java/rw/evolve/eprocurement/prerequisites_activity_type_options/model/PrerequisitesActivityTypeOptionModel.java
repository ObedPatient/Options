/**
 * Entity representing a Prerequisites Activity File Type option in the database.
 */
package rw.evolve.eprocurement.prerequisites_activity_type_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "prerequisites_activity_file_type_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class PrerequisitesActivityTypeOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the Prerequisites Activity File Type option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prerequisites_activity_file_type_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the Prerequisites Activity File Type option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the Prerequisites Activity File Type option. */
    @Column(name = "description", nullable = false)
    private String description;
}
