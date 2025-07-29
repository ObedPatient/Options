/**
 * Model representing a Donor type option in the 'Donor' table 'donor type' field  in the database.
 */
package rw.evolve.eprocurement.donor_type.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.donor_type.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "donor_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class DonorTypeOptionModel extends GeneralTimestampBaseModel {
    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}

