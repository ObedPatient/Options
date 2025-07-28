/**
 * Model representing a Country code option in the 'workspace' table 'country code' field  in the database.
 */
package rw.evolve.eprocurement.country_code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.country_code.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "country_code_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class CountryCodeOptionModel extends GeneralTimestampBaseModel {

        @Id
        @Column(name = "id")
        private String id;

        @EqualsAndHashCode.Include
        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "description")
        private String description;
}
