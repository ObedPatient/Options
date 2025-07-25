package rw.evolve.eprocurement.source_of_fund_option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.evolve.eprocurement.source_of_fund_option.model.SourceOfFundOptionModel;

import java.util.List;

public interface SourceOfFundOptionRepository extends JpaRepository<SourceOfFundOptionModel, String> {

    /**
     * @param name of the SourceOfFundOption to check if exists
     * @return True if SourceOfFundOption exist else false
     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {SourceOfFundOptionModel} entities that have not been soft-deleted.
     * Only Scheme Options with a null {deletedAt} field are returned.
     *
     * @return A list of non-deleted {SourceOfFundOptionModel} entities.
     */
    List<SourceOfFundOptionModel> findByDeletedAtIsNull();
}
