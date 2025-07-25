/**
 * Service for managing SourceOfFundOption model.
 * Provides functionality to create, read, update, and delete SourceOfFundOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.source_of_fund_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.source_of_fund_option.exception.SourceOfFundAlreadyExistException;
import rw.evolve.eprocurement.source_of_fund_option.exception.SourceOfFundNotFoundException;
import rw.evolve.eprocurement.source_of_fund_option.model.SourceOfFundOptionModel;
import rw.evolve.eprocurement.source_of_fund_option.repository.SourceOfFundOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SourceOfFundOptionService {

    @Autowired
    private SourceOfFundOptionRepository sourceOfFundOptionRepository;

    /**
     * Creates a single Source of Fund option model.
     *
     * @param sourceOfFundOptionModel            - the SourceOfFundOptionModel to be created
     * @return                                   - the saved SourceOfFundOption model
     * @throws SourceOfFundAlreadyExistException - if a SourceOfFundOption with the same name exists
     * @throws NullPointerException              - if the input model is null
     */
    @Transactional
    public SourceOfFundOptionModel save(SourceOfFundOptionModel sourceOfFundOptionModel) {
        if (sourceOfFundOptionModel == null) {
            throw new NullPointerException("Source of fund option cannot be null");
        }
        if (sourceOfFundOptionRepository.existsByName(sourceOfFundOptionModel.getName())) {
            throw new SourceOfFundAlreadyExistException("Source of fund option already exists: " + sourceOfFundOptionModel.getName());
        }
        return sourceOfFundOptionRepository.save(sourceOfFundOptionModel);
    }

    /**
     * Creates multiple Source of Fund option model.
     *
     * @param sourceOfFundOptionModelList        - the list of SourceOfFundOption models to be created
     * @return                                   - a list of saved SourceOfFundOption models
     * @throws IllegalArgumentException          - if the input list is null or empty
     * @throws SourceOfFundAlreadyExistException - if a SourceOfFundOption with the same name exists
     */
    @Transactional
    public List<SourceOfFundOptionModel> saveMany(List<SourceOfFundOptionModel> sourceOfFundOptionModelList) {
        if (sourceOfFundOptionModelList == null || sourceOfFundOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Source of fund option model list cannot be null or empty");
        }
        for (SourceOfFundOptionModel sourceOfFundOptionModel : sourceOfFundOptionModelList) {
            if (sourceOfFundOptionRepository.existsByName(sourceOfFundOptionModel.getName())) {
                throw new SourceOfFundAlreadyExistException("Source of fund option already exists: " + sourceOfFundOptionModel.getName());
            }
        }
        return sourceOfFundOptionRepository.saveAll(sourceOfFundOptionModelList);
    }

    /**
     * Retrieves a single Source of Fund option model by its ID.
     * Throws a SourceOfFundNotFoundException if the option is not found or has been deleted.
     *
     * @param id                             - the ID of the SourceOfFundOption to retrieve
     * @return                               - the SourceOfFundOption model if found and not deleted
     * @throws SourceOfFundNotFoundException - if the option is not found
     * @throws NullPointerException          - if the ID is null
     */
    @Transactional
    public SourceOfFundOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Source of fund option ID cannot be null");
        }
        SourceOfFundOptionModel sourceOfFundOptionModel = sourceOfFundOptionRepository.findById(id)
                .orElseThrow(() -> new SourceOfFundNotFoundException("Source of fund option not found with ID: " + id));
        if (sourceOfFundOptionModel.getDeletedAt() != null) {
            throw new SourceOfFundNotFoundException("Source of fund option not found with ID: " + id);
        }
        return sourceOfFundOptionModel;
    }

    /**
     * Retrieves a list of SourceOfFundOption models based on the provided IDs.
     *
     * @param idList                        - A list of SourceOfFundOption IDs to retrieve
     * @return                              - A list of SourceOfFundOptionModel objects that are not marked as deleted
     * @throws NullPointerException         - if the ID list is null or contains null IDs
     */
    @Transactional
    public List<SourceOfFundOptionModel> readMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Source of fund option ID list cannot be null");
        }
        List<SourceOfFundOptionModel> modelList = new ArrayList<>();
        for (String id : idList) {
            if (id == null) {
                throw new NullPointerException("Source of fund option ID cannot be null");
            }
            SourceOfFundOptionModel sourceOfFundOptionModel = sourceOfFundOptionRepository.findById(id)
                    .orElse(null);
            if (sourceOfFundOptionModel == null) {
                continue;
            }
            if (sourceOfFundOptionModel.getDeletedAt() == null) {
                modelList.add(sourceOfFundOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all SourceOfFundOption model that are not marked as deleted.
     *
     * @return - a List of SourceOfFundOption models where deletedAt is null
     */
    @Transactional
    public List<SourceOfFundOptionModel> readAll() {
        return sourceOfFundOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all SourceOfFundOption model, including those marked as deleted.
     *
     * @return - A list of all SourceOfFundOptionModel objects
     */
    @Transactional
    public List<SourceOfFundOptionModel> hardReadAll() {
        return sourceOfFundOptionRepository.findAll();
    }

    /**
     * Updates a single SourceOfFundOption model identified by the provided ID.
     *
     * @param model                          - The SourceOfFundOptionModel containing updated data
     * @return                               - The updated SourceOfFundOptionModel
     * @throws SourceOfFundNotFoundException - if the option is not found or is marked as deleted
     */
    @Transactional
    public SourceOfFundOptionModel updateOne(SourceOfFundOptionModel model) {
        SourceOfFundOptionModel existing = sourceOfFundOptionRepository.findById(model.getId())
                .orElseThrow(() -> new SourceOfFundNotFoundException("Source of fund option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new SourceOfFundNotFoundException("Source of fund option with ID: " + model.getId() + " is not found");
        }
        return sourceOfFundOptionRepository.save(model);
    }

    /**
     * Updates multiple SourceOfFundOption model in a transactional manner.
     *
     * @param modelList                      - List of SourceOfFundOptionModel objects containing updated data
     * @return                               - List of updated SourceOfFundOptionModel objects
     * @throws SourceOfFundNotFoundException - if any option is not found or is marked as deleted
     */
    @Transactional
    public List<SourceOfFundOptionModel> updateMany(List<SourceOfFundOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Source of Fund option model list cannot be null or empty");
        }
        for (SourceOfFundOptionModel model : modelList) {
            SourceOfFundOptionModel existing = sourceOfFundOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new SourceOfFundNotFoundException("Source of Fund option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new SourceOfFundNotFoundException("Source of Fund option with ID: " + model.getId() + " is not found");
            }
        }
        return sourceOfFundOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single SourceOfFundOption model by ID, including soft-deleted ones.
     *
     * @param model - The SourceOfFundOptionModel containing updated data
     * @return      - The updated SourceOfFundOptionModel
     */
    @Transactional
    public SourceOfFundOptionModel hardUpdate(SourceOfFundOptionModel model) {
        return sourceOfFundOptionRepository.save(model);
    }

    /**
     * Updates all SourceOfFundOption models by their IDs, including soft-deleted ones.
     *
     * @param sourceOfFundOptionModelList - List of SourceOfFundOptionModel objects containing updated data
     * @return                            - List of updated SourceOfFundOptionModel objects
     */
    @Transactional
    public List<SourceOfFundOptionModel> hardUpdateAll(List<SourceOfFundOptionModel> sourceOfFundOptionModelList) {
        return sourceOfFundOptionRepository.saveAll(sourceOfFundOptionModelList);
    }

    /**
     * Soft deletes a Source of Fund option by ID.
     *
     * @param id                             - The ID of the SourceOfFundOption to soft delete
     * @return                               - The soft-deleted SourceOfFundOptionModel
     * @throws SourceOfFundNotFoundException - if the option is not found
     */
    @Transactional
    public SourceOfFundOptionModel softDelete(String id) {
        SourceOfFundOptionModel sourceOfFundOptionModel = sourceOfFundOptionRepository.findById(id)
                .orElseThrow(() -> new SourceOfFundNotFoundException("Source of Fund option not found with id: " + id));
        sourceOfFundOptionModel.setDeletedAt(LocalDateTime.now());
        return sourceOfFundOptionRepository.save(sourceOfFundOptionModel);
    }

    /**
     * Hard deletes a Source of Fund option by ID.
     *
     * @param id                             - ID of the SourceOfFundOption to hard delete
     * @throws NullPointerException          - if the ID is null
     * @throws SourceOfFundNotFoundException - if the option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Source of fund option ID cannot be null");
        }
        if (!sourceOfFundOptionRepository.existsById(id)) {
            throw new SourceOfFundNotFoundException("Source of fund option not found with id: " + id);
        }
        sourceOfFundOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Source of Fund options by their IDs.
     *
     * @param idList                         - List of SourceOfFundOption IDs to be soft deleted
     * @return                               - List of soft-deleted SourceOfFundOption objects
     * @throws SourceOfFundNotFoundException - if any IDs are not found
     */
    @Transactional
    public List<SourceOfFundOptionModel> softDeleteMany(List<String> idList) {
        List<SourceOfFundOptionModel> sourceOfFundOptionModelList = sourceOfFundOptionRepository.findAllById(idList);
        if (sourceOfFundOptionModelList.isEmpty()) {
            throw new SourceOfFundNotFoundException("No Source of fund options found with provided IDs: " + idList);
        }
        for (SourceOfFundOptionModel model : sourceOfFundOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return sourceOfFundOptionRepository.saveAll(sourceOfFundOptionModelList);
    }

    /**
     * Hard deletes multiple Source of Fund options by IDs.
     *
     * @param idList - List of SourceOfFundOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        sourceOfFundOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Source of Fund options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        sourceOfFundOptionRepository.deleteAll();
    }
}