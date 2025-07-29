/**
 * Model representing a Scheme option in the 'plan' table 'scheme' field  in the database.
 */
package rw.evolve.eprocurement.prebid_event_type.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.prebid_event_type.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "prebid_event_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class PrebidEventTypeModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
