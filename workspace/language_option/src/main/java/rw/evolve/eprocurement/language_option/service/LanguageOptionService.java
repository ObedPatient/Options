/**
 * Service for managing LanguageOption model.
 * Provides functionality to create, read, update, and delete LanguageOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.language_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.language_option.exception.LanguageOptionAlreadyExistException;
import rw.evolve.eprocurement.language_option.exception.LanguageOptionNotFoundException;
import rw.evolve.eprocurement.language_option.model.LanguageOptionModel;
import rw.evolve.eprocurement.language_option.repository.LanguageOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class LanguageOptionService {

    private LanguageOptionRepository languageOptionRepository;

    /**
     * Creates a single LanguageOption model with a generated ID.
     *
     * @param languageOptionModel                  - the LanguageOptionModel to be created
     * @return                                     - the saved LanguageOption model
     * @throws LanguageOptionAlreadyExistException - if a LanguageOption with the same name exists
     */
    @Transactional
    public LanguageOptionModel save(LanguageOptionModel languageOptionModel) {
        if (languageOptionModel == null) {
            throw new NullPointerException("Language option cannot be null");
        }
        if (languageOptionRepository.existsByName(languageOptionModel.getName())) {
            throw new LanguageOptionAlreadyExistException("Language option already exists: " + languageOptionModel.getName());
        }
        return languageOptionRepository.save(languageOptionModel);
    }

    /**
     * Creates multiple LanguageOption model, each with a unique generated ID.
     *
     * @param languageOptionModelList - the list of LanguageOption models to be created
     * @return                        - a list of saved LanguageOption models
     * @throws NullPointerException   - if the input list is null
     */
    @Transactional
    public List<LanguageOptionModel> saveMany(List<LanguageOptionModel> languageOptionModelList) {
        if (languageOptionModelList == null || languageOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Language option model list cannot be null or empty");
        }
        for (LanguageOptionModel languageOptionModel : languageOptionModelList) {
            if (languageOptionRepository.existsByName(languageOptionModel.getName())) {
                throw new LanguageOptionAlreadyExistException("Language option already exists: " + languageOptionModel.getName());
            }
        }
        return languageOptionRepository.saveAll(languageOptionModelList);
    }

    /**
     * Retrieves a single LanguageOption model by its ID.
     * Throws a LanguageOptionNotFoundException if the LanguageOption is not found or has been deleted.
     *
     * @param id                               - the ID of the LanguageOption to retrieve
     * @return                                 - the LanguageOption model if found and not deleted
     * @throws LanguageOptionNotFoundException - if the LanguageOption is not found
     * @throws NullPointerException            - if LanguageOption ID is null
     */
    @Transactional
    public LanguageOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Language option ID cannot be null");
        }
        LanguageOptionModel languageOptionModel = languageOptionRepository.findById(id)
                .orElseThrow(() -> new LanguageOptionNotFoundException("Language option not found with ID: " + id));
        if (languageOptionModel.getDeletedAt() != null) {
            throw new LanguageOptionNotFoundException("Language option not found with ID: " + id);
        }
        return languageOptionModel;
    }

    /**
     * Retrieves a list of LanguageOption model based on the provided LanguageOption IDs.
     *
     * @param languageOptionIdList    - A list of LanguageOption IDs to retrieve
     * @return                        - A list of LanguageOptionModel objects that are not marked as deleted
     * @throws NullPointerException   - if a LanguageOption ID list is null
     */
    @Transactional
    public List<LanguageOptionModel> readMany(List<String> languageOptionIdList) {
        if (languageOptionIdList == null || languageOptionIdList.isEmpty()) {
            throw new NullPointerException("Language option ID list cannot be null");
        }
        List<LanguageOptionModel> modelList = new ArrayList<>();
        for (String id : languageOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Language option ID cannot be null");
            }
            LanguageOptionModel languageOptionModel = languageOptionRepository.findById(id)
                    .orElse(null);
            if (languageOptionModel == null)
                continue;
            if (languageOptionModel.getDeletedAt() == null) {
                modelList.add(languageOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all LanguageOption model that are not marked as deleted.
     *
     * @return - a List of LanguageOption model where deletedAt is null
     */
    @Transactional
    public List<LanguageOptionModel> readAll() {
        return languageOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all LanguageOption model, including those marked as deleted.
     *
     * @return - A list of all LanguageOptionModel objects
     */
    @Transactional
    public List<LanguageOptionModel> hardReadAll() {
        return languageOptionRepository.findAll();
    }

    /**
     * Updates a single LanguageOption model identified by the provided ID.
     *
     * @param model                            - The LanguageOptionModel containing updated data
     * @return                                 - The updated LanguageOptionModel
     * @throws LanguageOptionNotFoundException - if LanguageOption is not found or marked as deleted
     */
    @Transactional
    public LanguageOptionModel updateOne(LanguageOptionModel model) {
        LanguageOptionModel existing = languageOptionRepository.findById(model.getId())
                .orElseThrow(() -> new LanguageOptionNotFoundException("Language option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new LanguageOptionNotFoundException("Language option with ID: " + model.getId() + " is not found");
        }
        return languageOptionRepository.save(model);
    }

    /**
     * Updates multiple LanguageOption model in a transactional manner.
     *
     * @param modelList                        - List of LanguageOptionModel objects containing updated data
     * @return                                 - List of updated LanguageOptionModel objects
     * @throws LanguageOptionNotFoundException - if a LanguageOption is not found or marked as deleted
     */
    @Transactional
    public List<LanguageOptionModel> updateMany(List<LanguageOptionModel> modelList) {
        for (LanguageOptionModel model : modelList) {
            LanguageOptionModel existing = languageOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new LanguageOptionNotFoundException("Language option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new LanguageOptionNotFoundException("Language option with ID: " + model.getId() + " is not found");
            }
        }
        return languageOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single LanguageOption model by ID, including deleted ones.
     *
     * @param model                            - The LanguageOptionModel containing updated data
     * @return                                 - The updated LanguageOptionModel
     * @throws LanguageOptionNotFoundException - if LanguageOption is not found
     */
    @Transactional
    public LanguageOptionModel hardUpdate(LanguageOptionModel model) {
        return languageOptionRepository.save(model);
    }

    /**
     * Updates multiple LanguageOption models by their IDs, including deleted ones.
     *
     * @param languageOptionModelList - List of LanguageOptionModel objects containing updated data
     * @return                        - List of updated LanguageOptionModel objects
     */
    @Transactional
    public List<LanguageOptionModel> hardUpdateAll(List<LanguageOptionModel> languageOptionModelList) {
        return languageOptionRepository.saveAll(languageOptionModelList);
    }

    /**
     * Soft deletes a LanguageOption by ID.
     *
     * @param id                               - The ID of the LanguageOption to soft delete
     * @return                                 - The soft-deleted LanguageOptionModel
     * @throws LanguageOptionNotFoundException - if LanguageOption ID is not found
     */
    @Transactional
    public LanguageOptionModel softDelete(String id) {
        LanguageOptionModel languageOptionModel = languageOptionRepository.findById(id)
                .orElseThrow(() -> new LanguageOptionNotFoundException("Language option not found with id: " + id));
        languageOptionModel.setDeletedAt(LocalDateTime.now());
        return languageOptionRepository.save(languageOptionModel);
    }

    /**
     * Hard deletes a LanguageOption by ID.
     *
     * @param id                               - ID of the LanguageOption to hard delete
     * @throws NullPointerException            - if the LanguageOption ID is null
     * @throws LanguageOptionNotFoundException - if the LanguageOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Language option ID cannot be null");
        }
        if (!languageOptionRepository.existsById(id)) {
            throw new LanguageOptionNotFoundException("Language option not found with id: " + id);
        }
        languageOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple LanguageOptions by their IDs.
     *
     * @param idList                           - List of LanguageOption IDs to be soft deleted
     * @return                                 - List of soft-deleted LanguageOption objects
     * @throws LanguageOptionNotFoundException - if any LanguageOption IDs are not found
     */
    @Transactional
    public List<LanguageOptionModel> softDeleteMany(List<String> idList) {
        List<LanguageOptionModel> languageOptionModelList = languageOptionRepository.findAllById(idList);
        if (languageOptionModelList.isEmpty()) {
            throw new LanguageOptionNotFoundException("No language options found with provided IDList: " + idList);
        }
        for (LanguageOptionModel model : languageOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return languageOptionRepository.saveAll(languageOptionModelList);
    }

    /**
     * Hard deletes multiple LanguageOptions by IDs.
     *
     * @param idList - List of LanguageOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        languageOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all LanguageOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        languageOptionRepository.deleteAll();
    }
}