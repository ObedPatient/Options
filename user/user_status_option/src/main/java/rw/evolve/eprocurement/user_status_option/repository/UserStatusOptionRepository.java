/**
 * Repository interface for user status option data access
 */
package rw.evolve.eprocurement.user_status_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.user_status_option.model.UserStatusOptionModel;

import java.util.List;

@Repository
public interface UserStatusOptionRepository extends JpaRepository<UserStatusOptionModel, Long> {

    /**
     * @param name of the UserStatusOption to check if exists
     * @return True if UserStatusOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {UserStatusOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {UserStatusOptionModel} entities.
     */
    List<UserStatusOptionModel> findByDeletedAtIsNull();
}
