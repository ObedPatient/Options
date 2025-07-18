/**
 * Entity representing a account type option in the database.
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

    /** Unique identifier for the Procurement method option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procurement_type_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the method option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the method option. */
    @Column(name = "description", nullable = false)
    private String description;
}
