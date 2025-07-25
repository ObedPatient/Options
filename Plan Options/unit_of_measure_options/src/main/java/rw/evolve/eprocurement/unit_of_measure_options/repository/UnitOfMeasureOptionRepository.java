package rw.evolve.eprocurement.unit_of_measure_options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.evolve.eprocurement.unit_of_measure_options.model.UnitOfMeasureOptionModel;

import java.util.List;

public interface UnitOfMeasureOptionRepository extends JpaRepository<UnitOfMeasureOptionModel, String> {

    /**
     * @param name of the UnitOfMeasure to check if exists
     * @return True if UnitOfMeasure exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {UnitOfMeasureOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {UnitOfMeasureOptionModel} entities.
     */
    List<UnitOfMeasureOptionModel> findByDeletedAtIsNull();

}
