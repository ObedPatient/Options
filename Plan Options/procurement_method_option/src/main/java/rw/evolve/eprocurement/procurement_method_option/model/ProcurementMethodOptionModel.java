/**
 * Entity representing a procurement method int the 'plan' table 'procurement_method' field option in the database.
 */
package rw.evolve.eprocurement.procurement_method_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.procurement_method_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "procurement_method_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ProcurementMethodOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "procurement_method_option_id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}
