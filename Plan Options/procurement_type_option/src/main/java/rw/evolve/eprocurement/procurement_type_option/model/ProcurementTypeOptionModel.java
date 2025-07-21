/**
 * Entity representing a account type option in the database.
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
    /** Unique identifier for the Procurement type option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procurement_type_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the type option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the type option. */
    @Column(name = "description", nullable = false)
    private String description;
}
