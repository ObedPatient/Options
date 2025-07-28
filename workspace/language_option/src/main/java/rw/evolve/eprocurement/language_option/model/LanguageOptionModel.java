/**
 * Model representing a Language option in the 'workspace' table 'language' field  in the database.
 */
package rw.evolve.eprocurement.language_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.language_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "language_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class LanguageOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
