/**
 * Model representing a civil society type option in the 'civil society' table 'civil society type' field  in the database.
 */
package rw.evolve.eprocurement.civil_society_type.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.civil_society_type.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "civil_society_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class CivilSocietyTypeOptionModel extends GeneralTimestampBaseModel {
    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}

