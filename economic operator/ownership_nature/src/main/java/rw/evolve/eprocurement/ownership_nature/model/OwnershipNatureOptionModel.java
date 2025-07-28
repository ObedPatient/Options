/**
 * Model representing a OwnershipNature option in the 'Beneficial ownership information' table 'ownership nature' field  in the database.
 */
package rw.evolve.eprocurement.ownership_nature.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.ownership_nature.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "ownership_nature_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class OwnershipNatureOptionModel extends GeneralTimestampBaseModel {
    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
