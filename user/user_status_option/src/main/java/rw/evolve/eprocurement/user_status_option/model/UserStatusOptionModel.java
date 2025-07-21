/**
 * Entity representing a User status option in the database.
 */
package rw.evolve.eprocurement.user_status_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.user_status_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "user_status_option_model")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class UserStatusOptionModel extends GeneralTimestampBaseModel {

    /** Unique identifier for the User status option. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_status_option_id")
    @EqualsAndHashCode.Include
    private Long id;

    /** name of the User status option */
    @Column(name = "name", nullable = false)
    private String name;

    /** Brief description of the User status option. */
    @Column(name = "description", nullable = false)
    private String description;

}
