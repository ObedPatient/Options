/**
 * Entity representing a ArchiveStrategy option in the 'audit log' table 'Archive strategy' field  in the database.
 */
package rw.evolve.eprocurement.archive_strategy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.archive_strategy.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "archive_strategy_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class ArchiveStrategyOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
