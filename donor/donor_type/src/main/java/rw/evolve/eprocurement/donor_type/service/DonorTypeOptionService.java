/**
 * Service for managing DonorTypeOption model.
 * Provides functionality to create, read, update, and delete DonorTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.donor_type.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.donor_type.exception.DonorTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.donor_type.exception.DonorTypeOptionNotFoundException;
import rw.evolve.eprocurement.donor_type.model.DonorTypeOptionModel;
import rw.evolve.eprocurement.donor_type.repository.DonorTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class DonorTypeOptionService {

    private DonorTypeOptionRepository donorTypeOptionRepository;

    /**
     * Creates a single DonorType option model with a generated ID.
     *
     * @param donorTypeOptionModel                  - the DonorTypeOptionModel to be created
     * @return                                      - the saved DonorTypeOption model
     * @throws DonorTypeOptionAlreadyExistException - if a DonorTypeOption with the same name exists
     */
    @Transactional
    public DonorTypeOptionModel save(DonorTypeOptionModel donorTypeOptionModel) {
        if (donorTypeOptionModel == null) {
            throw new NullPointerException("Donor type option cannot be null");
        }
        if (donorTypeOptionRepository.existsByName(donorTypeOptionModel.getName())) {
            throw new DonorTypeOptionAlreadyExistException("Donor type option already exists: " + donorTypeOptionModel.getName());
        }
        return donorTypeOptionRepository.save(donorTypeOptionModel);
    }

    /**
     * Creates multiple DonorType Option model, each with a unique generated ID.
     *
     * @param donorTypeOptionModelList - the list of DonorType option model to be created
     * @return                         - a list of saved DonorType Option model
     * @throws NullPointerException    - if the input list is null
     */
    @Transactional
    public List<DonorTypeOptionModel> saveMany(List<DonorTypeOptionModel> donorTypeOptionModelList) {
        if (donorTypeOptionModelList == null || donorTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Donor type option model list cannot be null or empty");
        }
        for (DonorTypeOptionModel donorTypeOptionModel : donorTypeOptionModelList) {
            if (donorTypeOptionRepository.existsByName(donorTypeOptionModel.getName())) {
                throw new DonorTypeOptionAlreadyExistException("Donor type option already exists: " + donorTypeOptionModel.getName());
            }
        }
        return donorTypeOptionRepository.saveAll(donorTypeOptionModelList);
    }

    /**
     * Retrieves a single DonorType option model by its ID.
     * Throws a DonorTypeOptionNotFoundException if the DonorType option is not found or has been deleted.
     *
     * @param id                                - the ID of the DonorType option to retrieve
     * @return                                  - the DonorType option model if found and not deleted
     * @throws DonorTypeOptionNotFoundException - if the DonorType option is not found
     * @throws NullPointerException             - if DonorType option ID is null
     */
    @Transactional
    public DonorTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Donor type option ID cannot be null");
        }
        DonorTypeOptionModel donorTypeOptionModel = donorTypeOptionRepository.findById(id)
                .orElseThrow(() -> new DonorTypeOptionNotFoundException("Donor type option not found with ID: " + id));
        if (donorTypeOptionModel.getDeletedAt() != null) {
            throw new DonorTypeOptionNotFoundException("Donor type option not found with ID: " + id);
        }
        return donorTypeOptionModel;
    }

    /**
     * Retrieves a list of DonorTypeOption model list based on the provided DonorTypeOption ID.
     *
     * @param donorTypeOptionIdList   - A list of DonorTypeOption ID to retrieve
     * @return                        - A list of DonorTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException   - if a DonorTypeOption ID list is null
     */
    @Transactional
    public List<DonorTypeOptionModel> readMany(List<String> donorTypeOptionIdList) {
        if (donorTypeOptionIdList == null || donorTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Donor type option ID list cannot be null");
        }
        List<DonorTypeOptionModel> modelList = new ArrayList<>();
        for (String id : donorTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Donor type option ID cannot be null");
            }
            DonorTypeOptionModel donorTypeOptionModel = donorTypeOptionRepository.findById(id)
                    .orElse(null);
            if (donorTypeOptionModel == null)
                continue;
            if (donorTypeOptionModel.getDeletedAt() == null) {
                modelList.add(donorTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all DonorType options that are not marked as deleted
     *
     * @return         - a List of DonorType option model where deletedAt is null
     */
    @Transactional
    public List<DonorTypeOptionModel> readAll() {
        return donorTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all DonorType Option model, including those marked as deleted.
     *
     * @return            - A list of all DonorTypeOptionModel objects
     */
    @Transactional
    public List<DonorTypeOptionModel> hardReadAll() {
        return donorTypeOptionRepository.findAll();
    }

    /**
     * Updates a single DonorType Option model identified by the provided ID.
     *
     * @param model                             - The DonorTypeOptionModel containing updated data
     * @return                                  - The updated DonorTypeOptionModel
     * @throws DonorTypeOptionNotFoundException - if donor type option is not found
     */
    @Transactional
    public DonorTypeOptionModel updateOne(DonorTypeOptionModel model) {
        DonorTypeOptionModel existing = donorTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new DonorTypeOptionNotFoundException("Donor type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new DonorTypeOptionNotFoundException("Donor type option with ID: " + model.getId() + " is not found");
        }
        return donorTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple donor type option model in a transactional manner.
     *
     * @param modelList                         - List of DonorTypeOptionModel objects containing updated data
     * @return                                  - List of updated DonorTypeOptionModel objects
     * @throws DonorTypeOptionNotFoundException - if donor type option is not found
     */
    @Transactional
    public List<DonorTypeOptionModel> updateMany(List<DonorTypeOptionModel> modelList) {
        for (DonorTypeOptionModel model : modelList) {
            DonorTypeOptionModel existing = donorTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new DonorTypeOptionNotFoundException("Donor type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new DonorTypeOptionNotFoundException("Donor type option with ID: " + model.getId() + " is not found");
            }
        }
        return donorTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single DonorType option model by ID, including deleted ones.
     *
     * @param model                           - The DonorTypeOptionModel containing updated data
     * @return                                - The updated DonorTypeOptionModel
     */
    @Transactional
    public DonorTypeOptionModel hardUpdate(DonorTypeOptionModel model) {
        return donorTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple DonorTypeOption modelList by their IDs, including deleted ones.
     *
     * @param donorTypeOptionModelList      - List of DonorTypeOptionModel objects containing updated data
     * @return                              - List of updated DonorTypeOptionModel objects
     */
    @Transactional
    public List<DonorTypeOptionModel> hardUpdateAll(List<DonorTypeOptionModel> donorTypeOptionModelList) {
        return donorTypeOptionRepository.saveAll(donorTypeOptionModelList);
    }

    /**
     * Soft deletes a DonorType option by ID.
     *
     * @return                                  - The soft-deleted DonorTypeOptionModel
     * @throws DonorTypeOptionNotFoundException - if donor type option id is not found
     */
    @Transactional
    public DonorTypeOptionModel softDelete(String id) {
        DonorTypeOptionModel donorTypeOptionModel = donorTypeOptionRepository.findById(id)
                .orElseThrow(() -> new DonorTypeOptionNotFoundException("Donor type option not found with id: " + id));
        donorTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return donorTypeOptionRepository.save(donorTypeOptionModel);
    }

    /**
     * Hard deletes a DonorType option by ID.
     *
     * @param id                                - ID of the DonorType option to hard delete
     * @throws NullPointerException             - if the DonorType option ID is null
     * @throws DonorTypeOptionNotFoundException - if the DonorType option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Donor type option ID cannot be null");
        }
        if (!donorTypeOptionRepository.existsById(id)) {
            throw new DonorTypeOptionNotFoundException("Donor type option not found with id: " + id);
        }
        donorTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple DonorType options by their ID.
     *
     * @param idList                            - List of DonorType option IDs to be soft deleted
     * @return                                  - List of soft-deleted DonorTypeOption objects
     * @throws DonorTypeOptionNotFoundException - if any DonorType option ID are not found
     */
    @Transactional
    public List<DonorTypeOptionModel> softDeleteMany(List<String> idList) {
        List<DonorTypeOptionModel> donorTypeOptionModelList = donorTypeOptionRepository.findAllById(idList);
        if (donorTypeOptionModelList.isEmpty()) {
            throw new DonorTypeOptionNotFoundException("No donor type options found with provided IDList: " + idList);
        }
        for (DonorTypeOptionModel model : donorTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return donorTypeOptionRepository.saveAll(donorTypeOptionModelList);
    }

    /**
     * Hard deletes multiple DonorType options by ID.
     *
     * @param idList     - List of DonorType option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        donorTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all DonorType options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        donorTypeOptionRepository.deleteAll();
    }
}