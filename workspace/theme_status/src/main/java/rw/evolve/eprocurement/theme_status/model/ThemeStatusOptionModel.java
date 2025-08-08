/**
 * Entity representing a ThemeStatus option in the 'theme' table 'status' field  in the database.
 */
package rw.evolve.eprocurement.theme_status.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.theme_status.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "theme_status_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ThemeStatusOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
