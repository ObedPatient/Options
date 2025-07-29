package rw.evolve.eprocurement.civil_society_type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.civil_society_type.model.CivilSocietyTypeOptionModel;

import java.util.List;

@Repository
public interface CivilSocietyTypeOptionRepository extends JpaRepository<CivilSocietyTypeOptionModel, String> {


    /**
     * @param name of the CivilSocietyTypeOption to check if exists
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {CivilSocietyTypeOptionModel} model that have not been soft-deleted.
     * Only CivilSocietyType options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {CivilSocietyTypeOptionModel} entities.
     */
    List<CivilSocietyTypeOptionModel> findByDeletedAtIsNull();
}
