/**
 * Repository interface for MetadataType option data access
 */
package rw.evolve.eprocurement.metadata_type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.metadata_type.model.MetadataTypeOptionModel;

import java.util.List;

@Repository
public interface MetadataTypeOptionRepository extends JpaRepository<MetadataTypeOptionModel, String> {

    /**
     * @param name of the MetadataTypeOption to check if exists
     * @return True   - if MetadataTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {MetadataTypeOptionModel} model that have not been soft-deleted.
     * Only MetadataType Option with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {MetadataTypeOptionModel} entities.
     */
    List<MetadataTypeOptionModel> findByDeletedAtIsNull();
}
