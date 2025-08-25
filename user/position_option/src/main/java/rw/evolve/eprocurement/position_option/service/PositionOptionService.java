package rw.evolve.eprocurement.position_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.position_option.exception.PositionOptionAlreadyExistException;
import rw.evolve.eprocurement.position_option.exception.PositionOptionNotFoundException;
import rw.evolve.eprocurement.position_option.model.PositionOptionModel;
import rw.evolve.eprocurement.position_option.repository.PositionOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing PositionOption model.
 * Provides functionality to create, read, update, and delete PositionOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
@AllArgsConstructor
public class PositionOptionService {


    private PositionOptionRepository positionOptionRepository;

    /**
     * Creates a single PositionOption model with a generated ID.
     *
     * @param positionOptionModel                  - the PositionOptionModel to be created
     * @return                                     - the saved PositionOption model
     * @throws PositionOptionAlreadyExistException - if a PositionOption with the same name exists
     */
    @Transactional
    public PositionOptionModel save(PositionOptionModel positionOptionModel) {
        if (positionOptionModel == null) {
            throw new NullPointerException("Position option cannot be null");
        }
        if (positionOptionRepository.existsByName(positionOptionModel.getName())) {
            throw new PositionOptionAlreadyExistException("Position option already exists: " + positionOptionModel.getName());
        }
        return positionOptionRepository.save(positionOptionModel);
    }

    /**
     * Creates multiple PositionOption models, each with a unique generated ID.
     *
     * @param positionOptionModelList - the list of PositionOption models to be created
     * @return                        - a list of saved PositionOption models
     * @throws NullPointerException   - if the input list is null
     */
    @Transactional
    public List<PositionOptionModel> saveMany(List<PositionOptionModel> positionOptionModelList) {
        if (positionOptionModelList == null || positionOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Position option model list cannot be null or empty");
        }
        for (PositionOptionModel positionOptionModel : positionOptionModelList) {
            if (positionOptionRepository.existsByName(positionOptionModel.getName())) {
                throw new PositionOptionAlreadyExistException("Position option already exists: " + positionOptionModel.getName());
            }
        }
        return positionOptionRepository.saveAll(positionOptionModelList);
    }

    /**
     * Retrieves a single PositionOption model by its ID.
     * Throws a PositionOptionNotFoundException if the PositionOption is not found or has been deleted.
     *
     * @param id                               - the ID of the PositionOption to retrieve
     * @return                                 - the PositionOption model if found and not deleted
     * @throws PositionOptionNotFoundException - if the PositionOption is not found
     * @throws NullPointerException            - if PositionOption ID is null
     */
    @Transactional
    public PositionOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Position option ID cannot be null");
        }
        PositionOptionModel positionOptionModel = positionOptionRepository.findById(id)
                .orElseThrow(() -> new PositionOptionNotFoundException("Position option not found with ID: " + id));
        if (positionOptionModel.getDeletedAt() != null) {
            throw new PositionOptionNotFoundException("Position option not found with ID: " + id);
        }
        return positionOptionModel;
    }

    /**
     * Retrieves a list of PositionOption models based on the provided PositionOption IDs.
     *
     * @param positionOptionIdList    - A list of PositionOption IDs to retrieve
     * @return                        - A list of PositionOptionModel objects that are not marked as deleted
     * @throws NullPointerException   - if a PositionOption ID list is null
     */
    @Transactional
    public List<PositionOptionModel> readMany(List<String> positionOptionIdList) {
        if (positionOptionIdList == null || positionOptionIdList.isEmpty()) {
            throw new NullPointerException("Position option ID list cannot be null");
        }
        List<PositionOptionModel> modelList = new ArrayList<>();
        for (String id : positionOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Position option ID cannot be null");
            }
            PositionOptionModel positionOptionModel = positionOptionRepository.findById(id)
                    .orElse(null);
            if (positionOptionModel == null)
                continue;
            if (positionOptionModel.getDeletedAt() == null) {
                modelList.add(positionOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all PositionOption model that are not marked as deleted.
     *
     * @return - a List of PositionOption models where deletedAt is null
     */
    @Transactional
    public List<PositionOptionModel> readAll() {
        return positionOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all PositionOption models, including those marked as deleted.
     *
     * @return - A list of all PositionOptionModel objects
     */
    @Transactional
    public List<PositionOptionModel> hardReadAll() {
        return positionOptionRepository.findAll();
    }

    /**
     * Updates a single PositionOption model identified by the provided ID.
     *
     * @param model                            - The PositionOptionModel containing updated data
     * @return                                 - The updated PositionOptionModel
     * @throws PositionOptionNotFoundException - if PositionOption is not found or marked as deleted
     */
    @Transactional
    public PositionOptionModel updateOne(PositionOptionModel model) {
        PositionOptionModel existing = positionOptionRepository.findById(model.getId())
                .orElseThrow(() -> new PositionOptionNotFoundException("Position option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new PositionOptionNotFoundException("Position option with ID: " + model.getId() + " is not found");
        }
        return positionOptionRepository.save(model);
    }

    /**
     * Updates multiple PositionOption model.
     *
     * @param modelList                        - List of PositionOptionModel objects containing updated data
     * @return                                 - List of updated PositionOptionModel objects
     * @throws PositionOptionNotFoundException - if a PositionOption is not found or marked as deleted
     */
    @Transactional
    public List<PositionOptionModel> updateMany(List<PositionOptionModel> modelList) {
        for (PositionOptionModel model : modelList) {
            PositionOptionModel existing = positionOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new PositionOptionNotFoundException("Position option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new PositionOptionNotFoundException("Position option with ID: " + model.getId() + " is not found");
            }
        }
        return positionOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single PositionOption model by ID, including deleted ones.
     *
     * @param model                            - The PositionOptionModel containing updated data
     * @return                                 - The updated PositionOptionModel
     * @throws PositionOptionNotFoundException - if PositionOption is not found
     */
    @Transactional
    public PositionOptionModel hardUpdate(PositionOptionModel model) {
        return positionOptionRepository.save(model);
    }

    /**
     * Updates multiple PositionOption models by their ID, including deleted ones.
     *
     * @param positionOptionModelList - List of PositionOptionModel objects containing updated data
     * @return                        - List of updated PositionOptionModel objects
     */
    @Transactional
    public List<PositionOptionModel> hardUpdateAll(List<PositionOptionModel> positionOptionModelList) {
        return positionOptionRepository.saveAll(positionOptionModelList);
    }

    /**
     * Soft deletes a PositionOption by ID.
     *
     * @param id                               - The ID of the PositionOption to soft delete
     * @return                                 - The soft-deleted PositionOptionModel
     * @throws PositionOptionNotFoundException - if PositionOption ID is not found
     */
    @Transactional
    public PositionOptionModel softDelete(String id) {
        PositionOptionModel positionOptionModel = positionOptionRepository.findById(id)
                .orElseThrow(() -> new PositionOptionNotFoundException("Position option not found with id: " + id));
        positionOptionModel.setDeletedAt(LocalDateTime.now());
        return positionOptionRepository.save(positionOptionModel);
    }

    /**
     * Hard deletes a PositionOption by ID.
     *
     * @param id                               - ID of the PositionOption to hard delete
     * @throws NullPointerException            - if the PositionOption ID is null
     * @throws PositionOptionNotFoundException - if the PositionOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Position option ID cannot be null");
        }
        if (!positionOptionRepository.existsById(id)) {
            throw new PositionOptionNotFoundException("Position option not found with id: " + id);
        }
        positionOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple PositionOptions by their IDs.
     *
     * @param idList                           - List of PositionOption IDs to be soft deleted
     * @return                                 - List of soft-deleted PositionOption objects
     * @throws PositionOptionNotFoundException - if any PositionOption IDs are not found
     */
    @Transactional
    public List<PositionOptionModel> softDeleteMany(List<String> idList) {
        List<PositionOptionModel> positionOptionModelList = positionOptionRepository.findAllById(idList);
        if (positionOptionModelList.isEmpty()) {
            throw new PositionOptionNotFoundException("No position options found with provided IDList: " + idList);
        }
        for (PositionOptionModel model : positionOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return positionOptionRepository.saveAll(positionOptionModelList);
    }

    /**
     * Hard deletes multiple PositionOptions by IDs.
     *
     * @param idList - List of PositionOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        positionOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all PositionOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        positionOptionRepository.deleteAll();
    }
}