/**
 * Repository interface for performing CRUD and custom queries
 * on {@link rw.evolve.eprocurement.country.model.CountryOptionModel}.
 */
package rw.evolve.eprocurement.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.country.model.CountryOptionModel;

import java.util.List;

@Repository
public interface CountryOptionRepository extends JpaRepository<CountryOptionModel, String> {

    /**
     * Checks if a CountryOption entity exists by its name.
     *
     * @param name           - the name of the CountryOption to check.
     * @return {@code true}  - if a CountryOption with the given name exists, otherwise false.
     */
    boolean existsByName(String name);

    /**
     * Checks if a CountryOption entity exists by its abbreviation.
     *
     * @param dialCode  - the abbreviation of the CountryOption to check.
     * @return          - true if a CountryOption with the given abbreviation exists, otherwise false.
     */
    boolean existsByDialCode(String dialCode);

    /**
     * Checks if a CountryOption entity exists by its Abbreviation.
     *
     * @param code           - the code of the CountryOption to check.
     * @return               - true if a CountryOption with the given code exists, otherwise {@code false}.
     */
    boolean existsByCode(String code);

    /**
     * Retrieves a list of CountryOption entities that have not been soft-deleted.
     * Only options with a  null  deletedAt field are returned.
     *
     * @return    - a list of non-deleted  CountryOptionModel entities.
     */
    List<CountryOptionModel> findByDeletedAtIsNull();
}
