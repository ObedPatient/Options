/**
 * Entity representing a Procurement Progress option in the "plan" table "procurement progress" field in the database.
 */
package rw.evolve.eprocurement.procurement_progress_status_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.procurement_progress_status_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "procurement_progress_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ProcurementProgressOptionModel extends GeneralTimestampBaseModel {


    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;
}
