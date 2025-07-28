/**
 * Model representing an authority type option in the 'contracting authority' table 'authority type' field  in the database.
 */
package rw.evolve.eprocurement.authority_type_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.authority_type_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "authority_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class AuthorityTypeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}

