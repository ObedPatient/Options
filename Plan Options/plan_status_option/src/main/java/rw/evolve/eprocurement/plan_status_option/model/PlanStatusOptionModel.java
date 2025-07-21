/**
 * Entity representing a Plan status method option in the database.
 */
package rw.evolve.eprocurement.plan_status_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.plan_status_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "plan_status_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class PlanStatusOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the Plan status option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_status_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the Plan status option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the Plan status option. */
    @Column(name = "description", nullable = false)
    private String description;
}
