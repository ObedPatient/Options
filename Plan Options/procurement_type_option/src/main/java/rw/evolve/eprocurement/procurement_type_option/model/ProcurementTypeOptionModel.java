/**
 * Entity representing a procurement type in the 'plan' table 'procurement_type' field option in the database.
 */
package rw.evolve.eprocurement.procurement_type_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.procurement_type_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "procurement_type_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ProcurementTypeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
