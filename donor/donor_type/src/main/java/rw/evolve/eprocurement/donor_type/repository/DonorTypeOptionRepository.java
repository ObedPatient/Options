package rw.evolve.eprocurement.donor_type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.evolve.eprocurement.donor_type.model.DonorTypeOptionModel;

import java.util.List;

@Repository
public interface DonorTypeOptionRepository extends JpaRepository<DonorTypeOptionModel, String> {

    /**
     * @param name of the DonorTypeOption to check if exists
    DonorTypeOption     */
    boolean existsByName(String name);


    /**
     * Retrieves a list of {DonorTypeOptionModel} model that have not been soft-deleted.
     * Only DonorType options with a null {deletedAt} field are returned.
     *
     * @return    - A list of non-deleted {DonorTypeOptionModel} entities.
     */
    List<DonorTypeOptionModel> findByDeletedAtIsNull();
}
