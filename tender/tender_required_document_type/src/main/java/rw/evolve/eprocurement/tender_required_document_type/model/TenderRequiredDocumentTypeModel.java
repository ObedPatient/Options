/**
 * Model representing a tender required document type option in the 'tender' table 'tender required document type' field  in the database.
 */
package rw.evolve.eprocurement.tender_required_document_type.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.evolve.eprocurement.tender_required_document_type.model.base.GeneralTimestampBaseModel;

@Data
@Entity(name = "tender_required_document_type_option")
@EqualsAndHashCode(callSuper =false, onlyExplicitlyIncluded = true)
public class TenderRequiredDocumentTypeModel extends GeneralTimestampBaseModel {

    @Id
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
