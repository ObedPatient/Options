/**
 * Entity representing a Position option in the 'user' table 'position' field  in the database.
 */
package rw.evolve.eprocurement.position_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.position_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "position_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class PositionOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
