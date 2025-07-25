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
@Table(name = "execution_period_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ExecutionPeriodOptionModel extends GeneralTimestampBaseModel {


    @Id
    @Column(name = "id")
    private String id;


    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;


}
