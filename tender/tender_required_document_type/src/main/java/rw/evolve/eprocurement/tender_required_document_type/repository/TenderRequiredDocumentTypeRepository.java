/**
 * Repository interface for TenderRequiredDocumentType option data access
 */
package rw.evolve.eprocurement.tender_required_document_type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.tender_required_document_type.model.TenderRequiredDocumentTypeModel;

import java.util.List;

@Repository
public interface TenderRequiredDocumentTypeRepository extends JpaRepository<TenderRequiredDocumentTypeModel, String> {

    /**
     * @param name of the TenderRequiredDocumentTypeOption to check if exists
     * @return True   - if TenderRequiredDocumentTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {TenderRequiredDocumentTypeModel} model that have not been soft-deleted.
     * Only TenderRequiredDocumentType options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {TenderRequiredDocumentTypeModel} entities.
     */
    List<TenderRequiredDocumentTypeModel> findByDeletedAtIsNull();
}
