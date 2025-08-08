/**
 * Entity representing a MetadataType option in the 'audit log' table 'Metadata Type' field  in the database.
 */
package rw.evolve.eprocurement.metadata_type.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.metadata_type.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "metadata_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class MetadataTypeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
