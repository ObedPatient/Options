/**
 * Model representing a Business category option in the 'economic operator' table 'business category' field  in the database.
 */
package rw.evolve.eprocurement.business_category_option.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.business_category_option.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "business_category_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class BusinessCategoryOptionModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
