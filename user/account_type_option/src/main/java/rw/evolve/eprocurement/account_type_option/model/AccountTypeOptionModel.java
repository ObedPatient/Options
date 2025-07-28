/**
 * Entity representing a account type in "user" table "account type" field option in the database.
 */
package rw.evolve.eprocurement.account_type_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.account_type_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "account_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class AccountTypeOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;


}
