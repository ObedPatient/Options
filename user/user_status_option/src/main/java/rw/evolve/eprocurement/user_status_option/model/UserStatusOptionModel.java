/**
 * Entity representing a User status option in "User" table "status" field in the database.
 */
package rw.evolve.eprocurement.user_status_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.user_status_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "user_status_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class UserStatusOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;

}
