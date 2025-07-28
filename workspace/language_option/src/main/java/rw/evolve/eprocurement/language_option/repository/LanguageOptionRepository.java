/**
 * Repository interface for Language option data access
 */
package rw.evolve.eprocurement.language_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.language_option.model.LanguageOptionModel;

import java.util.List;

@Repository
public interface LanguageOptionRepository extends JpaRepository<LanguageOptionModel, String> {

    /**
     * @param name of the LanguageOption to check if exists
     * @return True   - if LanguageOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {LanguageOptionModel} model that have not been soft-deleted.
     * Only Language options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {LanguageOptionModel} entities.
     */
    List<LanguageOptionModel> findByDeletedAtIsNull();
}
