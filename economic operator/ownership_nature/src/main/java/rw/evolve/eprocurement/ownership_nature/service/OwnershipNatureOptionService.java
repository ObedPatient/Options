/**
 * Service for managing OwnershipNatureOption model.
 * Provides functionality to create, read, update, and delete OwnershipNatureOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.ownership_nature.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.ownership_nature.exception.OwnershipNatureOptionAlreadyExistException;
import rw.evolve.eprocurement.ownership_nature.exception.OwnershipNatureOptionNotFoundException;
import rw.evolve.eprocurement.ownership_nature.model.OwnershipNatureOptionModel;
import rw.evolve.eprocurement.ownership_nature.repository.OwnershipNatureOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OwnershipNatureOptionService {

    @Autowired
    private OwnershipNatureOptionRepository ownershipNatureOptionRepository;

    /**
     * Creates a single OwnershipNatureOption model with a generated ID.
     *
     * @param ownershipNatureOptionModel                  - the OwnershipNatureOptionModel to be created
     * @return                                            - the saved OwnershipNatureOption model
     * @throws OwnershipNatureOptionAlreadyExistException - if a OwnershipNatureOption with the same name exists
     */
    @Transactional
    public OwnershipNatureOptionModel save(OwnershipNatureOptionModel ownershipNatureOptionModel) {
        if (ownershipNatureOptionModel == null) {
            throw new NullPointerException("Ownership nature option cannot be null");
        }
        if (ownershipNatureOptionRepository.existsByName(ownershipNatureOptionModel.getName())) {
            throw new OwnershipNatureOptionAlreadyExistException("Ownership nature option already exists: " + ownershipNatureOptionModel.getName());
        }
        return ownershipNatureOptionRepository.save(ownershipNatureOptionModel);
    }

    /**
     * Creates multiple OwnershipNatureOption model, each with a unique generated ID.
     *
     * @param ownershipNatureOptionModelList - the list of OwnershipNatureOption model to be created
     * @return                               - a list of saved OwnershipNatureOption models
     * @throws NullPointerException          - if the input list is null
     */
    @Transactional
    public List<OwnershipNatureOptionModel> saveMany(List<OwnershipNatureOptionModel> ownershipNatureOptionModelList) {
        if (ownershipNatureOptionModelList == null || ownershipNatureOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Ownership nature option model list cannot be null or empty");
        }
        for (OwnershipNatureOptionModel ownershipNatureOptionModel : ownershipNatureOptionModelList) {
            if (ownershipNatureOptionRepository.existsByName(ownershipNatureOptionModel.getName())) {
                throw new OwnershipNatureOptionAlreadyExistException("Ownership nature option already exists: " + ownershipNatureOptionModel.getName());
            }
        }
        return ownershipNatureOptionRepository.saveAll(ownershipNatureOptionModelList);
    }

    /**
     * Retrieves a single OwnershipNatureOption model by its ID.
     * Throws a OwnershipNatureOptionNotFoundException if the OwnershipNatureOption is not found or has been deleted.
     *
     * @param id                                      - the ID of the OwnershipNatureOption to retrieve
     * @return                                        - the OwnershipNatureOption model if found and not deleted
     * @throws OwnershipNatureOptionNotFoundException - if the OwnershipNatureOption is not found
     * @throws NullPointerException                   - if OwnershipNatureOption ID is null
     */
    @Transactional
    public OwnershipNatureOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Ownership nature option ID cannot be null");
        }
        OwnershipNatureOptionModel ownershipNatureOptionModel = ownershipNatureOptionRepository.findById(id)
                .orElseThrow(() -> new OwnershipNatureOptionNotFoundException("Ownership nature option not found with ID: " + id));
        if (ownershipNatureOptionModel.getDeletedAt() != null) {
            throw new OwnershipNatureOptionNotFoundException("Ownership nature option not found with ID: " + id);
        }
        return ownershipNatureOptionModel;
    }

    /**
     * Retrieves a list of OwnershipNatureOption models based on the provided OwnershipNatureOption IDs.
     *
     * @param ownershipNatureOptionIdList      - A list of OwnershipNatureOption IDs to retrieve
     * @return                                 - A list of OwnershipNatureOptionModel objects that are not marked as deleted
     * @throws NullPointerException            - if a OwnershipNatureOption ID list is null
     */
    @Transactional
    public List<OwnershipNatureOptionModel> readMany(List<String> ownershipNatureOptionIdList) {
        if (ownershipNatureOptionIdList == null || ownershipNatureOptionIdList.isEmpty()) {
            throw new NullPointerException("Ownership nature option ID list cannot be null");
        }
        List<OwnershipNatureOptionModel> modelList = new ArrayList<>();
        for (String id : ownershipNatureOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Ownership nature option ID cannot be null");
            }
            OwnershipNatureOptionModel ownershipNatureOptionModel = ownershipNatureOptionRepository.findById(id)
                    .orElse(null);
            if (ownershipNatureOptionModel == null)
                continue;
            if (ownershipNatureOptionModel.getDeletedAt() == null) {
                modelList.add(ownershipNatureOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all OwnershipNatureOption model that are not marked as deleted.
     *
     * @return - a List of OwnershipNatureOption model where deletedAt is null
     */
    @Transactional
    public List<OwnershipNatureOptionModel> readAll() {
        return ownershipNatureOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all OwnershipNatureOption model, including those marked as deleted.
     *
     * @return - A list of all OwnershipNatureOptionModel objects
     */
    @Transactional
    public List<OwnershipNatureOptionModel> hardReadAll() {
        return ownershipNatureOptionRepository.findAll();
    }

    /**
     * Updates a single OwnershipNatureOption model identified by the provided ID.
     *
     * @param model                                     - The OwnershipNatureOptionModel containing updated data
     * @return                                          - The updated OwnershipNatureOptionModel
     * @throws OwnershipNatureOptionNotFoundException   - if OwnershipNatureOption is not found or marked as deleted
     */
    @Transactional
    public OwnershipNatureOptionModel updateOne(OwnershipNatureOptionModel model) {
        OwnershipNatureOptionModel existing = ownershipNatureOptionRepository.findById(model.getId())
                .orElseThrow(() -> new OwnershipNatureOptionNotFoundException("Ownership nature option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new OwnershipNatureOptionNotFoundException("Ownership nature option with ID: " + model.getId() + " is not found");
        }
        return ownershipNatureOptionRepository.save(model);
    }

    /**
     * Updates multiple OwnershipNatureOption models in a transactional manner.
     *
     * @param modelList                                 - List of OwnershipNatureOptionModel objects containing updated data
     * @return                                          - List of updated OwnershipNatureOptionModel objects
     * @throws OwnershipNatureOptionNotFoundException   - if a OwnershipNatureOption is not found or marked as deleted
     */
    @Transactional
    public List<OwnershipNatureOptionModel> updateMany(List<OwnershipNatureOptionModel> modelList) {
        for (OwnershipNatureOptionModel model : modelList) {
            OwnershipNatureOptionModel existing = ownershipNatureOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new OwnershipNatureOptionNotFoundException("Ownership nature option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new OwnershipNatureOptionNotFoundException("Ownership nature option with ID: " + model.getId() + " is not found");
            }
        }
        return ownershipNatureOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single OwnershipNatureOption model by ID, including deleted ones.
     *
     * @param model                                     - The OwnershipNatureOptionModel containing updated data
     * @return                                          - The updated OwnershipNatureOptionModel
     */
    @Transactional
    public OwnershipNatureOptionModel hardUpdate(OwnershipNatureOptionModel model) {
        return ownershipNatureOptionRepository.save(model);
    }

    /**
     * Updates multiple OwnershipNatureOption model by their ID, including deleted ones.
     *
     * @param ownershipNatureOptionModelList - List of OwnershipNatureOptionModel objects containing updated data
     * @return                               - List of updated OwnershipNatureOptionModel objects
     */
    @Transactional
    public List<OwnershipNatureOptionModel> hardUpdateAll(List<OwnershipNatureOptionModel> ownershipNatureOptionModelList) {
        return ownershipNatureOptionRepository.saveAll(ownershipNatureOptionModelList);
    }

    /**
     * Soft deletes a OwnershipNatureOption by ID.
     *
     * @param id                                      - The ID of the OwnershipNatureOption to soft delete
     * @return                                        - The soft-deleted OwnershipNatureOptionModel
     * @throws OwnershipNatureOptionNotFoundException - if OwnershipNatureOption ID is not found
     */
    @Transactional
    public OwnershipNatureOptionModel softDelete(String id) {
        OwnershipNatureOptionModel ownershipNatureOptionModel = ownershipNatureOptionRepository.findById(id)
                .orElseThrow(() -> new OwnershipNatureOptionNotFoundException("Ownership nature option not found with id: " + id));
        ownershipNatureOptionModel.setDeletedAt(LocalDateTime.now());
        return ownershipNatureOptionRepository.save(ownershipNatureOptionModel);
    }

    /**
     * Hard deletes a OwnershipNatureOption by ID.
     *
     * @param id                                      - ID of the OwnershipNatureOption to hard delete
     * @throws NullPointerException                   - if the OwnershipNatureOption ID is null
     * @throws OwnershipNatureOptionNotFoundException - if the OwnershipNatureOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Ownership nature option ID cannot be null");
        }
        if (!ownershipNatureOptionRepository.existsById(id)) {
            throw new OwnershipNatureOptionNotFoundException("Ownership nature option not found with id: " + id);
        }
        ownershipNatureOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple OwnershipNatureOption by their ID.
     *
     * @param idList                                  - List of OwnershipNatureOption IDs to be soft deleted
     * @return                                        - List of soft-deleted OwnershipNatureOption objects
     * @throws OwnershipNatureOptionNotFoundException - if any OwnershipNatureOption IDs are not found
     */
    @Transactional
    public List<OwnershipNatureOptionModel> softDeleteMany(List<String> idList) {
        List<OwnershipNatureOptionModel> ownershipNatureOptionModelList = ownershipNatureOptionRepository.findAllById(idList);
        if (ownershipNatureOptionModelList.isEmpty()) {
            throw new OwnershipNatureOptionNotFoundException("No ownership nature options found with provided IDList: " + idList);
        }
        for (OwnershipNatureOptionModel model : ownershipNatureOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return ownershipNatureOptionRepository.saveAll(ownershipNatureOptionModelList);
    }

    /**
     * Hard deletes multiple OwnershipNatureOption by ID.
     *
     * @param idList - List of OwnershipNatureOption ID to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        ownershipNatureOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all OwnershipNatureOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        ownershipNatureOptionRepository.deleteAll();
    }
}