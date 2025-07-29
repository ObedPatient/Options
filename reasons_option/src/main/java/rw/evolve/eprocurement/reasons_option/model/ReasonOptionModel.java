/**
 * Model representing a Reason option in the ' ' table 'reason' field  in the database.
 */
package rw.evolve.eprocurement.reasons_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.reasons_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "reason_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ReasonOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
