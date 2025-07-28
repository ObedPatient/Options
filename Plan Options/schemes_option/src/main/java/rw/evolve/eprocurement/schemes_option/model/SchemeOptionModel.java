/**
 * Model representing a Scheme option in the 'plan' table 'scheme' field  in the database.
 */
package rw.evolve.eprocurement.schemes_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.schemes_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "scheme_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class SchemeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
