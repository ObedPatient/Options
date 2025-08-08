/**
 * Service for managing ThemeStatusOption model.
 * Provides functionality to create, read, update, and delete ThemeStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.theme_status.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.theme_status.exception.ThemeStatusOptionAlreadyExistException;
import rw.evolve.eprocurement.theme_status.exception.ThemeStatusOptionNotFoundException;
import rw.evolve.eprocurement.theme_status.model.ThemeStatusOptionModel;
import rw.evolve.eprocurement.theme_status.repository.ThemeStatusOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ThemeStatusOptionService {

    @Autowired
    private ThemeStatusOptionRepository themeStatusOptionRepository;

    /**
     * Creates a single ThemeStatusOption model with a generated ID.
     *
     * @param themeStatusOptionModel                  - the ThemeStatusOptionModel to be created
     * @return                                        - the saved ThemeStatusOption model
     * @throws ThemeStatusOptionAlreadyExistException - if a ThemeStatusOption with the same name exists
     */
    @Transactional
    public ThemeStatusOptionModel save(ThemeStatusOptionModel themeStatusOptionModel) {
        if (themeStatusOptionModel == null) {
            throw new NullPointerException("Theme status option cannot be null");
        }
        if (themeStatusOptionRepository.existsByName(themeStatusOptionModel.getName())) {
            throw new ThemeStatusOptionAlreadyExistException("Theme status option already exists: " + themeStatusOptionModel.getName());
        }
        return themeStatusOptionRepository.save(themeStatusOptionModel);
    }

    /**
     * Creates multiple ThemeStatusOption models, each with a unique generated ID.
     *
     * @param themeStatusOptionModelList - the list of ThemeStatusOption models to be created
     * @return                           - a list of saved ThemeStatusOption models
     * @throws NullPointerException      - if the input list is null
     */
    @Transactional
    public List<ThemeStatusOptionModel> saveMany(List<ThemeStatusOptionModel> themeStatusOptionModelList) {
        if (themeStatusOptionModelList == null || themeStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Theme status option model list cannot be null or empty");
        }
        for (ThemeStatusOptionModel themeStatusOptionModel : themeStatusOptionModelList) {
            if (themeStatusOptionRepository.existsByName(themeStatusOptionModel.getName())) {
                throw new ThemeStatusOptionAlreadyExistException("Theme status option already exists: " + themeStatusOptionModel.getName());
            }
        }
        return themeStatusOptionRepository.saveAll(themeStatusOptionModelList);
    }

    /**
     * Retrieves a single ThemeStatusOption model by its ID.
     * Throws a ThemeStatusOptionNotFoundException if the ThemeStatusOption is not found or has been deleted.
     *
     * @param id                                  - the ID of the ThemeStatusOption to retrieve
     * @return                                    - the ThemeStatusOption model if found and not deleted
     * @throws ThemeStatusOptionNotFoundException - if the ThemeStatusOption is not found
     * @throws NullPointerException               - if ThemeStatusOption ID is null
     */
    @Transactional
    public ThemeStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Theme status option ID cannot be null");
        }
        ThemeStatusOptionModel themeStatusOptionModel = themeStatusOptionRepository.findById(id)
                .orElseThrow(() -> new ThemeStatusOptionNotFoundException("Theme status option not found with ID: " + id));
        if (themeStatusOptionModel.getDeletedAt() != null) {
            throw new ThemeStatusOptionNotFoundException("Theme status option not found with ID: " + id);
        }
        return themeStatusOptionModel;
    }

    /**
     * Retrieves a list of ThemeStatusOption models based on the provided ThemeStatusOption IDs.
     *
     * @param themeStatusOptionIdList - A list of ThemeStatusOption IDs to retrieve
     * @return                        - A list of ThemeStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException   - if a ThemeStatusOption ID list is null
     */
    @Transactional
    public List<ThemeStatusOptionModel> readMany(List<String> themeStatusOptionIdList) {
        if (themeStatusOptionIdList == null || themeStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("Theme status option ID list cannot be null");
        }
        List<ThemeStatusOptionModel> modelList = new ArrayList<>();
        for (String id : themeStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Theme status option ID cannot be null");
            }
            ThemeStatusOptionModel themeStatusOptionModel = themeStatusOptionRepository.findById(id)
                    .orElse(null);
            if (themeStatusOptionModel == null)
                continue;
            if (themeStatusOptionModel.getDeletedAt() == null) {
                modelList.add(themeStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all ThemeStatusOption models that are not marked as deleted.
     *
     * @return - a List of ThemeStatusOption models where deletedAt is null
     */
    @Transactional
    public List<ThemeStatusOptionModel> readAll() {
        return themeStatusOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ThemeStatusOption models, including those marked as deleted.
     *
     * @return - A list of all ThemeStatusOptionModel objects
     */
    @Transactional
    public List<ThemeStatusOptionModel> hardReadAll() {
        return themeStatusOptionRepository.findAll();
    }

    /**
     * Updates a single ThemeStatusOption model identified by the provided ID.
     *
     * @param model                               - The ThemeStatusOptionModel containing updated data
     * @return                                    - The updated ThemeStatusOptionModel
     * @throws ThemeStatusOptionNotFoundException - if ThemeStatusOption is not found or marked as deleted
     */
    @Transactional
    public ThemeStatusOptionModel updateOne(ThemeStatusOptionModel model) {
        ThemeStatusOptionModel existing = themeStatusOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ThemeStatusOptionNotFoundException("Theme status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ThemeStatusOptionNotFoundException("Theme status option with ID: " + model.getId() + " is not found");
        }
        return themeStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple ThemeStatusOption models.
     *
     * @param modelList                           - List of ThemeStatusOptionModel objects containing updated data
     * @return                                    - List of updated ThemeStatusOptionModel objects
     * @throws ThemeStatusOptionNotFoundException - if a ThemeStatusOption is not found or marked as deleted
     */
    @Transactional
    public List<ThemeStatusOptionModel> updateMany(List<ThemeStatusOptionModel> modelList) {
        for (ThemeStatusOptionModel model : modelList) {
            ThemeStatusOptionModel existing = themeStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ThemeStatusOptionNotFoundException("Theme status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ThemeStatusOptionNotFoundException("Theme status option with ID: " + model.getId() + " is not found");
            }
        }
        return themeStatusOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single ThemeStatusOption model by ID, including deleted ones.
     *
     * @param model                               - The ThemeStatusOptionModel containing updated data
     * @return                                    - The updated ThemeStatusOptionModel
     * @throws ThemeStatusOptionNotFoundException - if ThemeStatusOption is not found
     */
    @Transactional
    public ThemeStatusOptionModel hardUpdate(ThemeStatusOptionModel model) {
        return themeStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple ThemeStatusOption models by their ID, including deleted ones.
     *
     * @param themeStatusOptionModelList - List of ThemeStatusOptionModel objects containing updated data
     * @return                           - List of updated ThemeStatusOptionModel objects
     */
    @Transactional
    public List<ThemeStatusOptionModel> hardUpdateAll(List<ThemeStatusOptionModel> themeStatusOptionModelList) {
        return themeStatusOptionRepository.saveAll(themeStatusOptionModelList);
    }

    /**
     * Soft deletes a ThemeStatusOption by ID.
     *
     * @param id                                  - The ID of the ThemeStatusOption to soft delete
     * @return                                    - The soft-deleted ThemeStatusOptionModel
     * @throws ThemeStatusOptionNotFoundException - if ThemeStatusOption ID is not found
     */
    @Transactional
    public ThemeStatusOptionModel softDelete(String id) {
        ThemeStatusOptionModel themeStatusOptionModel = themeStatusOptionRepository.findById(id)
                .orElseThrow(() -> new ThemeStatusOptionNotFoundException("Theme status option not found with id: " + id));
        themeStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return themeStatusOptionRepository.save(themeStatusOptionModel);
    }

    /**
     * Hard deletes a ThemeStatusOption by ID.
     *
     * @param id                                  - ID of the ThemeStatusOption to hard delete
     * @throws NullPointerException               - if the ThemeStatusOption ID is null
     * @throws ThemeStatusOptionNotFoundException - if the ThemeStatusOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Theme status option ID cannot be null");
        }
        if (!themeStatusOptionRepository.existsById(id)) {
            throw new ThemeStatusOptionNotFoundException("Theme status option not found with id: " + id);
        }
        themeStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple ThemeStatusOptions by their IDs.
     *
     * @param idList                              - List of ThemeStatusOption IDs to be soft deleted
     * @return                                    - List of soft-deleted ThemeStatusOption objects
     * @throws ThemeStatusOptionNotFoundException - if any ThemeStatusOption IDs are not found
     */
    @Transactional
    public List<ThemeStatusOptionModel> softDeleteMany(List<String> idList) {
        List<ThemeStatusOptionModel> themeStatusOptionModelList = themeStatusOptionRepository.findAllById(idList);
        if (themeStatusOptionModelList.isEmpty()) {
            throw new ThemeStatusOptionNotFoundException("No theme status options found with provided IDList: " + idList);
        }
        for (ThemeStatusOptionModel model : themeStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return themeStatusOptionRepository.saveAll(themeStatusOptionModelList);
    }

    /**
     * Hard deletes multiple ThemeStatusOptions by IDs.
     *
     * @param idList - List of ThemeStatusOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        themeStatusOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all ThemeStatusOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        themeStatusOptionRepository.deleteAll();
    }
}