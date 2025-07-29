/**
 * Model representing a ClarificationRequestStatus option in the 'ClarificationRequestStatus' table 'status' field  in the database.
 */
package rw.evolve.eprocurement.clarification_request_status_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.clarification_request_status_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "clarification_request_status_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ClarificationRequestStatusOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
