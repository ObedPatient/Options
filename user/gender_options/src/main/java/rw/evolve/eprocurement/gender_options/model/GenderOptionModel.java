/**
 * Entity representing a Gender option in the "user" table "gender" field in the database.
 */
package rw.evolve.eprocurement.gender_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.gender_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "gender_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class GenderOptionModel extends GeneralTimestampBaseModel {


    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;
}
