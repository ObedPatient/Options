/**
 * Model representing a SelectionMethod option in the 'tender' table 'selection method' field  in the database.
 */
package rw.evolve.eprocurement.selection_method_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.selection_method_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "selection_method_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class SelectionMethodOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
