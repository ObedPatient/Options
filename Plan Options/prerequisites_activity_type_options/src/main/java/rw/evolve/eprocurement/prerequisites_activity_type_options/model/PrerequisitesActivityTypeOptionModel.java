/**
 * Entity representing a Prerequisites Activity File Type option in the "plan" table "prerequisite activity" field in the database.
 */
package rw.evolve.eprocurement.prerequisites_activity_type_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "prerequisites_activity_file_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class PrerequisitesActivityTypeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;


    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;
}
