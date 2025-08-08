/**
 * Entity representing a LogLevel option in the 'audit log' table 'log level' field  in the database.
 */
package rw.evolve.eprocurement.log_level.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.log_level.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "log_level_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class LogLevelOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
