/**
 * Entity representing a ExecutionPeriod option in the database.
 */
package rw.evolve.eprocurement.execution_period_options.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.execution_period_options.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "execution_period_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ExecutionPeriodOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the ExecutionPeriod option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "execution_period_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the ExecutionPeriod option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the ExecutionPeriod option. */
    @Column(name = "description", nullable = false)
    private String description;


}
