/**
 * Model representing a Tender stage option in the 'Tender' table 'tender stage' field  in the database.
 */
package rw.evolve.eprocurement.tender_stage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.tender_stage.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "tender_stage_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class TenderStageOptionModel extends GeneralTimestampBaseModel {

        @Id
        @Column(name = "id")
        private String id;

        @EqualsAndHashCode.Include
        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "description")
        private String description;
}


