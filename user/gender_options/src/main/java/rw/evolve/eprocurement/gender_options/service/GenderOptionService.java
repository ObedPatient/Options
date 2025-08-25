/**
 * Service for managing GenderOption entities.
 * Provides functionality to create, read, update, and delete GenderOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.gender_options.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.gender_options.exception.GenderOptionAlreadyExistException;
import rw.evolve.eprocurement.gender_options.exception.GenderOptionNotFoundException;
import rw.evolve.eprocurement.gender_options.model.GenderOptionModel;
import rw.evolve.eprocurement.gender_options.repository.GenderOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class GenderOptionService {

    private GenderOptionRepository genderOptionRepository;

    /**
     * Creates a single Gender option model with a generated ID.
     *
     * @param genderOptionModel                  - the GenderOptionModel to be created
     * @return                                   - the saved GenderOption model
     * @throws GenderOptionAlreadyExistException - if a GenderOption with the same name exists
     */
    @Transactional
    public GenderOptionModel save(GenderOptionModel genderOptionModel) {
        if (genderOptionModel == null) {
            throw new NullPointerException("Gender option cannot be null");
        }
        if (genderOptionRepository.existsByName(genderOptionModel.getName())) {
            throw new GenderOptionAlreadyExistException("Gender option already exists: " + genderOptionModel.getName());
        }
        return genderOptionRepository.save(genderOptionModel);
    }

    /**
     * Creates multiple Gender Option model, each with a unique generated ID.
     *
     * @param genderOptionModelList     - the list of Gender option models to be created
     * @return                          - a list of saved Gender Option models
     * @throws IllegalArgumentException - if the input list is null or empty
     */
    @Transactional
    public List<GenderOptionModel> saveMany(List<GenderOptionModel> genderOptionModelList) {
        if (genderOptionModelList == null || genderOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Gender option model list cannot be null or empty");
        }
        for (GenderOptionModel genderOptionModel : genderOptionModelList) {
            if (genderOptionRepository.existsByName(genderOptionModel.getName())) {
                throw new GenderOptionAlreadyExistException("Gender option already exists: " + genderOptionModel.getName());
            }
        }
        return genderOptionRepository.saveAll(genderOptionModelList);
    }

    /**
     * Retrieves a single Gender option model by its ID.
     * Throws a GenderOptionNotFoundException if the Gender option is not found or has been deleted.
     *
     * @param id                             - the ID of the Gender option to retrieve
     * @return                               - the Gender option model if found and not deleted
     * @throws GenderOptionNotFoundException - if the Gender option is not found
     * @throws NullPointerException          - if Gender option ID is null
     */
    @Transactional
    public GenderOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Gender option ID cannot be null");
        }
        GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender option not found with ID: " + id));
        if (genderOptionModel.getDeletedAt() != null) {
            throw new GenderOptionNotFoundException("Gender option not found with ID: " + id);
        }
        return genderOptionModel;
    }

    /**
     * Retrieves a list of GenderOption models based on the provided GenderOption IDs.
     *
     * @param genderOptionIdList       - A list of GenderOption IDs to retrieve
     * @return                         - A list of GenderOptionModel objects that are not marked as deleted
     * @throws NullPointerException    - if GenderOption ID list is null
     */
    @Transactional
    public List<GenderOptionModel> readMany(List<String> genderOptionIdList) {
        if (genderOptionIdList == null || genderOptionIdList.isEmpty()) {
            throw new NullPointerException("Gender option ID list cannot be null");
        }
        List<GenderOptionModel> modelList = new ArrayList<>();
        for (String id : genderOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Gender option ID cannot be null");
            }
            GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                    .orElse(null);
            if (genderOptionModel == null)
                continue;
            if (genderOptionModel.getDeletedAt() == null) {
                modelList.add(genderOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Gender options that are not marked as deleted
     *
     * @return         - a List of Gender option models where deletedAt is null
     */
    @Transactional
    public List<GenderOptionModel> readAll() {
        return genderOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all Gender Option models, including those marked as deleted.
     *
     * @return         - A list of all GenderOptionModel objects
     */
    @Transactional
    public List<GenderOptionModel> hardReadAll() {
        return genderOptionRepository.findAll();
    }

    /**
     * Updates a single Gender Option model identified by the provided ID.
     *
     * @param model                          - The GenderOptionModel containing updated data
     * @return                               - The updated GenderOptionModel
     * @throws GenderOptionNotFoundException - if Gender option is not found or is marked as deleted
     */
    @Transactional
    public GenderOptionModel updateOne(GenderOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("Gender option or ID cannot be null");
        }
        GenderOptionModel existing = genderOptionRepository.findById(model.getId())
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new GenderOptionNotFoundException("Gender option with ID: " + model.getId() + " is not found");
        }
        return genderOptionRepository.save(model);
    }

    /**
     * Updates multiple Gender option models in a transactional manner.
     *
     * @param modelList                    - List of GenderOptionModel objects containing updated data
     *
     */
    @Transactional
    public List<GenderOptionModel> updateMany(List<GenderOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Gender option model list cannot be null or empty");
        }
        for (GenderOptionModel model : modelList) {
            if (model.getId() == null) {
                throw new NullPointerException("Gender option ID cannot be null");
            }
            GenderOptionModel existing = genderOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new GenderOptionNotFoundException("Gender option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new GenderOptionNotFoundException("Gender option with ID: " + model.getId() + " is not found");
            }
        }
        return genderOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Gender option model by ID, including deleted ones.
     *
     * @param model                        - The GenderOptionModel containing updated data
     *
     */
    @Transactional
    public GenderOptionModel hardUpdate(GenderOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("Gender option or ID cannot be null");
        }
        return genderOptionRepository.save(model);
    }

    /**
     * Updates multiple GenderOption models by their IDs, including deleted ones.
     *
     * @param genderOptionModelList    - List of GenderOptionModel objects containing data updated
     * @return                         - List of updated GenderOptionModel objects
     */
    @Transactional
    public List<GenderOptionModel> hardUpdateAll(List<GenderOptionModel> genderOptionModelList) {
        if (genderOptionModelList == null || genderOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Gender option model list cannot be null or empty");
        }
        return genderOptionRepository.saveAll(genderOptionModelList);
    }

    /**
     * Soft deletes a Gender option by ID.
     *
     * @param id                             - ID of the Gender option to soft delete
     * @return                               - soft-deleted GenderOptionModel
     * @throws GenderOptionNotFoundException - if Gender option is not found
     */
    @Transactional
    public GenderOptionModel softDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Gender option ID cannot be null");
        }
        GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender option not found with id: id: " + id));
        genderOptionModel.setDeletedAt(LocalDateTime.now());
        return genderOptionRepository.save(genderOptionModel);
    }

    /**
     * Hard deletes a Gender option by ID.
     *
     * @param id                               - ID of the ID Gender option to hard delete
     * @throws NullPointerException            - if the Gender option ID is null
     * @throws GenderOptionNotFoundException   - if the Gender option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Gender option ID cannot be null");
        }
        if (!genderOptionRepository.existsById(id)) {
            throw new GenderOptionNotFoundException("Gender option not found id with ID: " + id);
        }
        genderOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Gender options by their IDs.
     *
     * @param idList                         - List of Gender option IDs to be soft deleted
     * @return                               - List of soft-deleted GenderOption objects
     * @throws GenderOptionNotFoundException - if any Gender option IDs ID are not found
     */
    @Transactional
    public List<GenderOptionModel> softDeleteMany(List<String> idList) {
        List<GenderOptionModel> genderOptionModelList = genderOptionRepository.findAllById(idList);
        if (genderOptionModelList.isEmpty()) {
            throw new GenderOptionNotFoundException("No gender options found with provided IDList: ID list: " + idList);
        }
        for (GenderOptionModel model : genderOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return genderOptionRepository.saveAll(genderOptionModelList);
    }

    /**
     * Hard deletes multiple Gender options by IDs.
     *
     * @param idList     - List of Gender option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        genderOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Gender options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        genderOptionRepository.deleteAll();
    }
}