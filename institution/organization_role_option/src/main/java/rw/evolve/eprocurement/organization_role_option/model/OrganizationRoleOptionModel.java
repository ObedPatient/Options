/**
 * Entity representing a Organization Role in "institution" table "organization role" field option in the database.
 */
package rw.evolve.eprocurement.organization_role_option.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.organization_role_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity
@Table(name = "organization_role_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class OrganizationRoleOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "description")
    private String description;
}
