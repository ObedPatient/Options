package rw.evolve.eprocurement.archive_strategy.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.archive_strategy.exception.ArchiveStrategyOptionAlreadyExistException;
import rw.evolve.eprocurement.archive_strategy.exception.ArchiveStrategyOptionNotFoundException;
import rw.evolve.eprocurement.archive_strategy.model.ArchiveStrategyOptionModel;
import rw.evolve.eprocurement.archive_strategy.repository.ArchiveStrategyOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing ArchiveStrategyOption model.
 * Provides functionality to create, read, update, and delete ArchiveStrategyOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
public class ArchiveStrategyOptionService {

    @Autowired
    private ArchiveStrategyOptionRepository archiveStrategyOptionRepository;

    /**
     * Creates a single ArchiveStrategyOption model with a generated ID.
     *
     * @param archiveStrategyOptionModel                  - the ArchiveStrategyOptionModel to be created
     * @return                                            - the saved ArchiveStrategyOption model
     * @throws ArchiveStrategyOptionAlreadyExistException - if an ArchiveStrategyOption with the same name exists
     */
    @Transactional
    public ArchiveStrategyOptionModel save(ArchiveStrategyOptionModel archiveStrategyOptionModel) {
        if (archiveStrategyOptionModel == null) {
            throw new NullPointerException("Archive strategy option cannot be null");
        }
        if (archiveStrategyOptionRepository.existsByName(archiveStrategyOptionModel.getName())) {
            throw new ArchiveStrategyOptionAlreadyExistException("Archive strategy option already exists: " + archiveStrategyOptionModel.getName());
        }
        return archiveStrategyOptionRepository.save(archiveStrategyOptionModel);
    }

    /**
     * Creates multiple ArchiveStrategyOption models, each with a unique generated ID.
     *
     * @param archiveStrategyOptionModelList - the list of ArchiveStrategyOption models to be created
     * @return                               - a list of saved ArchiveStrategyOption models
     * @throws NullPointerException          - if the input list is null
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> saveMany(List<ArchiveStrategyOptionModel> archiveStrategyOptionModelList) {
        if (archiveStrategyOptionModelList == null || archiveStrategyOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Archive strategy option model list cannot be null or empty");
        }
        for (ArchiveStrategyOptionModel archiveStrategyOptionModel : archiveStrategyOptionModelList) {
            if (archiveStrategyOptionRepository.existsByName(archiveStrategyOptionModel.getName())) {
                throw new ArchiveStrategyOptionAlreadyExistException("Archive strategy option already exists: " + archiveStrategyOptionModel.getName());
            }
        }
        return archiveStrategyOptionRepository.saveAll(archiveStrategyOptionModelList);
    }

    /**
     * Retrieves a single ArchiveStrategyOption model by its ID.
     * Throws an ArchiveStrategyOptionNotFoundException if the ArchiveStrategyOption is not found or has been deleted.
     *
     * @param id                                       - the ID of the ArchiveStrategyOption to retrieve
     * @return                                         - the ArchiveStrategyOption model if found and not deleted
     * @throws ArchiveStrategyOptionNotFoundException  - if the ArchiveStrategyOption is not found
     * @throws NullPointerException                    - if ArchiveStrategyOption ID is null
     */
    @Transactional
    public ArchiveStrategyOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Archive strategy option ID cannot be null");
        }
        ArchiveStrategyOptionModel archiveStrategyOptionModel = archiveStrategyOptionRepository.findById(id)
                .orElseThrow(() -> new ArchiveStrategyOptionNotFoundException("Archive strategy option not found with ID: " + id));
        if (archiveStrategyOptionModel.getDeletedAt() != null) {
            throw new ArchiveStrategyOptionNotFoundException("Archive strategy option not found with ID: " + id);
        }
        return archiveStrategyOptionModel;
    }

    /**
     * Retrieves a list of ArchiveStrategyOption models based on the provided ArchiveStrategyOption IDs.
     *
     * @param archiveStrategyOptionIdList - A list of ArchiveStrategyOption IDs to retrieve
     * @return                            - A list of ArchiveStrategyOptionModel objects that are not marked as deleted
     * @throws NullPointerException       - if an ArchiveStrategyOption ID list is null
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> readMany(List<String> archiveStrategyOptionIdList) {
        if (archiveStrategyOptionIdList == null || archiveStrategyOptionIdList.isEmpty()) {
            throw new NullPointerException("Archive strategy option ID list cannot be null");
        }
        List<ArchiveStrategyOptionModel> modelList = new ArrayList<>();
        for (String id : archiveStrategyOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Archive strategy option ID cannot be null");
            }
            ArchiveStrategyOptionModel archiveStrategyOptionModel = archiveStrategyOptionRepository.findById(id)
                    .orElse(null);
            if (archiveStrategyOptionModel == null)
                continue;
            if (archiveStrategyOptionModel.getDeletedAt() == null) {
                modelList.add(archiveStrategyOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all ArchiveStrategyOption models that are not marked as deleted.
     *
     * @return - a List of ArchiveStrategyOption models where deletedAt is null
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> readAll() {
        return archiveStrategyOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ArchiveStrategyOption models, including those marked as deleted.
     *
     * @return - A list of all ArchiveStrategyOptionModel objects
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> hardReadAll() {
        return archiveStrategyOptionRepository.findAll();
    }

    /**
     * Updates a single ArchiveStrategyOption model identified by the provided ID.
     *
     * @param model                                    - The ArchiveStrategyOptionModel containing updated data
     * @return                                         - The updated ArchiveStrategyOptionModel
     * @throws ArchiveStrategyOptionNotFoundException  - if ArchiveStrategyOption is not found or marked as deleted
     */
    @Transactional
    public ArchiveStrategyOptionModel updateOne(ArchiveStrategyOptionModel model) {
        ArchiveStrategyOptionModel existing = archiveStrategyOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ArchiveStrategyOptionNotFoundException("Archive strategy option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ArchiveStrategyOptionNotFoundException("Archive strategy option with ID: " + model.getId() + " is not found");
        }
        return archiveStrategyOptionRepository.save(model);
    }

    /**
     * Updates multiple ArchiveStrategyOption models.
     *
     * @param modelList                                - List of ArchiveStrategyOptionModel objects containing updated data
     * @return                                         - List of updated ArchiveStrategyOptionModel objects
     * @throws ArchiveStrategyOptionNotFoundException  - if an ArchiveStrategyOption is not found or marked as deleted
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> updateMany(List<ArchiveStrategyOptionModel> modelList) {
        for (ArchiveStrategyOptionModel model : modelList) {
            ArchiveStrategyOptionModel existing = archiveStrategyOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ArchiveStrategyOptionNotFoundException("Archive strategy option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ArchiveStrategyOptionNotFoundException("Archive strategy option with ID: " + model.getId() + " is not found");
            }
        }
        return archiveStrategyOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single ArchiveStrategyOption model by ID, including deleted ones.
     *
     * @param model                                    - The ArchiveStrategyOptionModel containing updated data
     * @return                                         - The updated ArchiveStrategyOptionModel
     * @throws ArchiveStrategyOptionNotFoundException  - if ArchiveStrategyOption is not found
     */
    @Transactional
    public ArchiveStrategyOptionModel hardUpdate(ArchiveStrategyOptionModel model) {
        return archiveStrategyOptionRepository.save(model);
    }

    /**
     * Updates multiple ArchiveStrategyOption models by their ID, including deleted ones.
     *
     * @param archiveStrategyOptionModelList - List of ArchiveStrategyOptionModel objects containing updated data
     * @return                               - List of updated ArchiveStrategyOptionModel objects
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> hardUpdateAll(List<ArchiveStrategyOptionModel> archiveStrategyOptionModelList) {
        return archiveStrategyOptionRepository.saveAll(archiveStrategyOptionModelList);
    }

    /**
     * Soft deletes an ArchiveStrategyOption by ID.
     *
     * @param id                                       - The ID of the ArchiveStrategyOption to soft delete
     * @return                                         - The soft-deleted ArchiveStrategyOptionModel
     * @throws ArchiveStrategyOptionNotFoundException  - if ArchiveStrategyOption ID is not found
     */
    @Transactional
    public ArchiveStrategyOptionModel softDelete(String id) {
        ArchiveStrategyOptionModel archiveStrategyOptionModel = archiveStrategyOptionRepository.findById(id)
                .orElseThrow(() -> new ArchiveStrategyOptionNotFoundException("Archive strategy option not found with id: " + id));
        archiveStrategyOptionModel.setDeletedAt(LocalDateTime.now());
        return archiveStrategyOptionRepository.save(archiveStrategyOptionModel);
    }

    /**
     * Hard deletes an ArchiveStrategyOption by ID.
     *
     * @param id                                       - ID of the ArchiveStrategyOption to hard delete
     * @throws NullPointerException                    - if the ArchiveStrategyOption ID is null
     * @throws ArchiveStrategyOptionNotFoundException  - if the ArchiveStrategyOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Archive strategy option ID cannot be null");
        }
        if (!archiveStrategyOptionRepository.existsById(id)) {
            throw new ArchiveStrategyOptionNotFoundException("Archive strategy option not found with id: " + id);
        }
        archiveStrategyOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple ArchiveStrategyOptions by their IDs.
     *
     * @param idList                                  - List of ArchiveStrategyOption IDs to be soft deleted
     * @return                                        - List of soft-deleted ArchiveStrategyOption objects
     * @throws ArchiveStrategyOptionNotFoundException - if any ArchiveStrategyOption IDs are not found
     */
    @Transactional
    public List<ArchiveStrategyOptionModel> softDeleteMany(List<String> idList) {
        List<ArchiveStrategyOptionModel> archiveStrategyOptionModelList = archiveStrategyOptionRepository.findAllById(idList);
        if (archiveStrategyOptionModelList.isEmpty()) {
            throw new ArchiveStrategyOptionNotFoundException("No archive strategy options found with provided IDList: " + idList);
        }
        for (ArchiveStrategyOptionModel model : archiveStrategyOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return archiveStrategyOptionRepository.saveAll(archiveStrategyOptionModelList);
    }

    /**
     * Hard deletes multiple ArchiveStrategyOptions by IDs.
     *
     * @param idList - List of ArchiveStrategyOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        archiveStrategyOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all ArchiveStrategyOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        archiveStrategyOptionRepository.deleteAll();
    }
}