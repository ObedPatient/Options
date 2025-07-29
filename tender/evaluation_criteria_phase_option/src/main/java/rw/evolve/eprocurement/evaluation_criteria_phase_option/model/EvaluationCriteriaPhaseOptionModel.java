/**
 * Model representing a Evaluation Criteria Phase option in the 'Evaluation criteria' table 'phase' field  in the database.
 */
package rw.evolve.eprocurement.evaluation_criteria_phase_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "evaluation_criteria_phase_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class EvaluationCriteriaPhaseOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
