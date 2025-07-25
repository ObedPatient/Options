/**
 * Entity representing a source of fund option in the "plan" table "source of fund" field in the database.
 */
package rw.evolve.eprocurement.source_of_fund_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.source_of_fund_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "source_of_fund_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class SourceOfFundOptionModel  extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;

}
