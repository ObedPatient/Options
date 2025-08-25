/**
 * Entity representing a Country option in "institution" table "country" field in the database.
 */
package rw.evolve.eprocurement.country.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.country.model.base.GeneralTimestampBaseModel;


@Data
@Entity
@Table(name = "country_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class CountryOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "dial_code", nullable = false)
    private String dialCode;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;
}
