/**
 * Repository interface for Authority type option data access
 */
package rw.evolve.eprocurement.authority_type_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.authority_type_option.model.AuthorityTypeOptionModel;

import java.util.List;

@Repository
public interface AuthorityTypeOptionRepository extends JpaRepository<AuthorityTypeOptionModel, String> {

    /**
     * @param name of the AuthorityTypeOption to check if exists
     * @return True   - if AuthorityTypeOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {AuthorityTypeOptionModel} model that have not been soft-deleted.
     * Only Authority type options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {AuthorityTypeOptionModel} model.
     */
    List<AuthorityTypeOptionModel> findByDeletedAtIsNull();
}
