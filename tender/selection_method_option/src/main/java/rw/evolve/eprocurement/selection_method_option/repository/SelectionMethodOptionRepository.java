/**
 * Repository interface for SelectionMethod option data access
 */
package rw.evolve.eprocurement.selection_method_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.selection_method_option.model.SelectionMethodOptionModel;

import java.util.List;

@Repository
public interface SelectionMethodOptionRepository extends JpaRepository<SelectionMethodOptionModel, String> {

    /**
     * @param name of the SelectionMethodOption to check if exists
     * @return True   - if SelectionMethodOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {SelectionMethodOptionModel} model that have not been soft-deleted.
     * Only SelectionMethod options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {SelectionMethodOptionModel} entities.
     */
    List<SelectionMethodOptionModel> findByDeletedAtIsNull();
}
