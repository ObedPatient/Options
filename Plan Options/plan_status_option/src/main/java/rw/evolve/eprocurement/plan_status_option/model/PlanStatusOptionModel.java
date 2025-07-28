/**
 * Entity representing a Plan status option in the 'plan' table 'status' field in the database.
 */
package rw.evolve.eprocurement.plan_status_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.plan_status_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "plan_status_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class PlanStatusOptionModel extends GeneralTimestampBaseModel {


    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
