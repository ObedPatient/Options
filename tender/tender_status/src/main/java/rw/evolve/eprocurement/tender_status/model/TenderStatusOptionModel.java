/**
 * Model representing a TenderStatus option in the 'tender' table 'status' field  in the database.
 */
package rw.evolve.eprocurement.tender_status.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.tender_status.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "tender_status_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class TenderStatusOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
