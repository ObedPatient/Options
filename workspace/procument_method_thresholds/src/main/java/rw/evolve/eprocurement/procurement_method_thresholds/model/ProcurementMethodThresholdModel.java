/**
 * Model representing a Procurement method threshold option in the 'workspace' table 'procurement method threshold' field  in the database.
 */
package rw.evolve.eprocurement.procurement_method_thresholds.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.procurement_method_thresholds.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "procurement_method_threshold_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ProcurementMethodThresholdModel extends GeneralTimestampBaseModel{

        @Id
        @Column(name = "id")
        private String id;

        @EqualsAndHashCode.Include
        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "description")
        private String description;
}
