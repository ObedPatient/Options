/**
 * Repository interface for ThemeStatus option data access
 */
package rw.evolve.eprocurement.theme_status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.theme_status.model.ThemeStatusOptionModel;

import java.util.List;

@Repository
public interface ThemeStatusOptionRepository extends JpaRepository<ThemeStatusOptionModel, String> {

    /**
     * @param name of the ThemeStatusOption to check if exists
     * @return True   - if ThemeStatusOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {ThemeStatusOptionModel} model that have not been soft-deleted.
     * Only ThemeStatus Option with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {ThemeStatusOptionModel} entities.
     */
    List<ThemeStatusOptionModel> findByDeletedAtIsNull();
}
