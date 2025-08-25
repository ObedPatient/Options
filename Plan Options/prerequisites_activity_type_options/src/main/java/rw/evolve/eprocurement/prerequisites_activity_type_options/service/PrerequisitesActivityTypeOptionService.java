/**
 * Service for managing PrerequisitesActivityTypeOption model.
 * Provides functionality to create, read, update, and delete PrerequisitesActivityTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.prerequisites_activity_type_options.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.prerequisites_activity_type_options.exception.PrerequisitesActivictyTypeAlreadyExistException;
import rw.evolve.eprocurement.prerequisites_activity_type_options.exception.PrerequisitesActivityFileTypeNotFoundException;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.PrerequisitesActivityTypeOptionModel;
import rw.evolve.eprocurement.prerequisites_activity_type_options.repository.PrerequisitesActivityTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PrerequisitesActivityTypeOptionService {

    private PrerequisitesActivityTypeOptionRepository prerequisitesActivityTypeOptionRepository;

    /**
     * Creates a single PrerequisitesActivityTypeOption model.
     *
     * @param prerequisitesActivityTypeOptionModel             - the PrerequisitesActivityTypeOptionModel to be created
     * @return                                                 - the saved PrerequisitesActivityTypeOption model
     * @throws PrerequisitesActivictyTypeAlreadyExistException - if a PrerequisitesActivityTypeOption with the same name exists
     * @throws NullPointerException                            - if the input model is null
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel save(PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel) {
        if (prerequisitesActivityTypeOptionModel == null) {
            throw new NullPointerException("Prerequisites Activity Type Option cannot be null");
        }
        if (prerequisitesActivityTypeOptionRepository.existsByName(prerequisitesActivityTypeOptionModel.getName())) {
            throw new PrerequisitesActivictyTypeAlreadyExistException("Prerequisites Activity Type Option already exists: " + prerequisitesActivityTypeOptionModel.getName());
        }
        return prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel);
    }

    /**
     * Creates multiple PrerequisitesActivityTypeOption models.
     *
     * @param prerequisitesActivityTypeOptionModelList         - the list of PrerequisitesActivityTypeOption models to be created
     * @return                                                 - a list of saved PrerequisitesActivityTypeOption models
     * @throws IllegalArgumentException                        - if the input list is null or empty
     * @throws PrerequisitesActivictyTypeAlreadyExistException - if a PrerequisitesActivityTypeOption with the same name exists
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> saveMany(List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModelList) {
        if (prerequisitesActivityTypeOptionModelList == null || prerequisitesActivityTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Prerequisites Activity Type Option model list cannot be null or empty");
        }
        for (PrerequisitesActivityTypeOptionModel model : prerequisitesActivityTypeOptionModelList) {
            if (prerequisitesActivityTypeOptionRepository.existsByName(model.getName())) {
                throw new PrerequisitesActivictyTypeAlreadyExistException("Prerequisites Activity Type Option already exists: " + model.getName());
            }
        }
        return prerequisitesActivityTypeOptionRepository.saveAll(prerequisitesActivityTypeOptionModelList);
    }

    /**
     * Retrieves a single PrerequisitesActivityTypeOption model by its ID.
     * Throws a PrerequisitesActivityTypeNotFoundException if the option is not found or has been deleted.
     *
     * @param id                                              - the ID of the PrerequisitesActivityTypeOption to retrieve
     * @return                                                - the PrerequisitesActivityTypeOption model if found and not deleted
     * @throws PrerequisitesActivityFileTypeNotFoundException - if the option is not found
     * @throws NullPointerException                           - if the ID is null
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Prerequisites Activity Type Option ID cannot be null");
        }
        PrerequisitesActivityTypeOptionModel model = prerequisitesActivityTypeOptionRepository.findById(id)
                .orElseThrow(() -> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with ID: " + id));
        if (model.getDeletedAt() != null) {
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with ID: " + id);
        }
        return model;
    }

    /**
     * Retrieves a list of PrerequisitesActivityTypeOption models based on the provided IDs.
     *
     * @param idList                                   - A list of PrerequisitesActivityTypeOption IDs to retrieve
     * @return                                         - A list of PrerequisitesActivityTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException                    - if the ID list is null or contains null IDs
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> readMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Prerequisites Activity Type Option ID list cannot be null");
        }
        List<PrerequisitesActivityTypeOptionModel> modelList = new ArrayList<>();
        for (String id : idList) {
            if (id == null) {
                throw new NullPointerException("Prerequisites Activity Type Option ID cannot be null");
            }
            PrerequisitesActivityTypeOptionModel model = prerequisitesActivityTypeOptionRepository.findById(id)
                    .orElse(null);
            if (model == null) {
                continue;
            }
            if (model.getDeletedAt() == null) {
                modelList.add(model);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all PrerequisitesActivityTypeOption models that are not marked as deleted.
     *
     * @return - a List of PrerequisitesActivityTypeOption models where deletedAt is null
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> readAll() {
        return prerequisitesActivityTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all PrerequisitesActivityTypeOption model, including those marked as deleted.
     *
     * @return - A list of all PrerequisitesActivityTypeOptionModel objects
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> hardReadAll() {
        return prerequisitesActivityTypeOptionRepository.findAll();
    }

    /**
     * Updates a single PrerequisitesActivityTypeOption model identified by the provided ID.
     *
     * @param model                                           - The PrerequisitesActivityTypeOptionModel containing updated data
     * @return                                                - The updated PrerequisitesActivityTypeOptionModel
     * @throws PrerequisitesActivityFileTypeNotFoundException - if the option is not found or is marked as deleted
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel updateOne(PrerequisitesActivityTypeOptionModel model) {
        PrerequisitesActivityTypeOptionModel existing = prerequisitesActivityTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites activity type option not found with ID: " + model.getId());
        }
        return prerequisitesActivityTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple PrerequisitesActivityTypeOption model in a transactional manner.
     *
     * @param modelList                                       - List of PrerequisitesActivityTypeOptionModel objects containing updated data
     * @return                                                - List of updated PrerequisitesActivityTypeOptionModel objects
     * @throws PrerequisitesActivityFileTypeNotFoundException - if any option is not found or is marked as deleted
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> updateMany(List<PrerequisitesActivityTypeOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Prerequisites activity type option model list cannot be null or empty");
        }
        for (PrerequisitesActivityTypeOptionModel model : modelList) {
            PrerequisitesActivityTypeOptionModel existing = prerequisitesActivityTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites activity type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites activity type option not found with ID: " + model.getId());
            }
        }
        return prerequisitesActivityTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single PrerequisitesActivityTypeOption model by ID, including soft-deleted ones.
     *
     * @param model                                    - The PrerequisitesActivityTypeOptionModel containing updated data
     * @return                                         - The updated PrerequisitesActivityTypeOptionModel
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel hardUpdate(PrerequisitesActivityTypeOptionModel model) {
        return prerequisitesActivityTypeOptionRepository.save(model);
    }

    /**
     * Updates all PrerequisitesActivityTypeOption model by their ID, including soft-deleted ones.
     *
     * @param modelList                                - List of PrerequisitesActivityTypeOptionModel objects containing updated data
     * @return                                         - List of updated PrerequisitesActivityTypeOptionModel objects
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> hardUpdateAll(List<PrerequisitesActivityTypeOptionModel> modelList) {
        return prerequisitesActivityTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Soft deletes a PrerequisitesActivityTypeOption by ID.
     *
     * @param id                                              - The ID of the PrerequisitesActivityTypeOption to soft delete
     * @return                                                - The soft-deleted PrerequisitesActivityTypeOptionModel
     * @throws PrerequisitesActivityFileTypeNotFoundException - if the option is not found
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel softDelete(String id) {
        PrerequisitesActivityTypeOptionModel model = prerequisitesActivityTypeOptionRepository.findById(id)
                .orElseThrow(() -> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites activity type option not found with id: " + id));
        model.setDeletedAt(LocalDateTime.now());
        return prerequisitesActivityTypeOptionRepository.save(model);
    }

    /**
     * Hard deletes a PrerequisitesActivityTypeOption by ID.
     *
     * @param id                                              - ID of the PrerequisitesActivityTypeOption to hard delete
     * @throws NullPointerException                           - if the ID is null
     * @throws PrerequisitesActivityFileTypeNotFoundException - if the option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Prerequisites activity type option ID cannot be null");
        }
        if (!prerequisitesActivityTypeOptionRepository.existsById(id)) {
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites activity type option not found with id: " + id);
        }
        prerequisitesActivityTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple PrerequisitesActivityTypeOption by their ID.
     *
     * @param idList                                          - List of PrerequisitesActivityTypeOption IDs to be soft deleted
     * @return                                                - List of soft-deleted PrerequisitesActivityTypeOption objects
     * @throws PrerequisitesActivityFileTypeNotFoundException - if any IDs are not found
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> softDeleteMany(List<String> idList) {
        List<PrerequisitesActivityTypeOptionModel> modelList = prerequisitesActivityTypeOptionRepository.findAllById(idList);
        if (modelList.isEmpty()) {
            throw new PrerequisitesActivityFileTypeNotFoundException("No Prerequisites activity type option found with provided IDs: " + idList);
        }
        for (PrerequisitesActivityTypeOptionModel model : modelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return prerequisitesActivityTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Hard deletes multiple PrerequisitesActivityTypeOption by ID.
     *
     * @param idList - List of PrerequisitesActivityTypeOption ID to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        prerequisitesActivityTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all PrerequisitesActivityTypeOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        prerequisitesActivityTypeOptionRepository.deleteAll();
    }
}