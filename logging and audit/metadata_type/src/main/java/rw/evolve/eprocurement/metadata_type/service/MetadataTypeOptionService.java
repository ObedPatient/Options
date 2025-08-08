/**
 * Service for managing MetadataTypeOption model.
 * Provides functionality to create, read, update, and delete MetadataTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.metadata_type.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.metadata_type.exception.MetadataTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.metadata_type.exception.MetadataTypeOptionNotFoundException;
import rw.evolve.eprocurement.metadata_type.model.MetadataTypeOptionModel;
import rw.evolve.eprocurement.metadata_type.repository.MetadataTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class MetadataTypeOptionService {

    @Autowired
    private MetadataTypeOptionRepository metadataTypeOptionRepository;

    /**
     * Creates a single MetadataTypeOption model with a generated ID.
     *
     * @param metadataTypeOptionModel                  - the MetadataTypeOptionModel to be created
     * @return                                         - the saved MetadataTypeOption model
     * @throws MetadataTypeOptionAlreadyExistException - if a MetadataTypeOption with the same name exists
     */
    @Transactional
    public MetadataTypeOptionModel save(MetadataTypeOptionModel metadataTypeOptionModel) {
        if (metadataTypeOptionModel == null) {
            throw new NullPointerException("Metadata type option cannot be null");
        }
        if (metadataTypeOptionRepository.existsByName(metadataTypeOptionModel.getName())) {
            throw new MetadataTypeOptionAlreadyExistException("Metadata type option already exists: " + metadataTypeOptionModel.getName());
        }
        return metadataTypeOptionRepository.save(metadataTypeOptionModel);
    }

    /**
     * Creates multiple MetadataTypeOption models, each with a unique generated ID.
     *
     * @param metadataTypeOptionModelList - the list of MetadataTypeOption models to be created
     * @return                            - a list of saved MetadataTypeOption models
     * @throws NullPointerException       - if the input list is null
     */
    @Transactional
    public List<MetadataTypeOptionModel> saveMany(List<MetadataTypeOptionModel> metadataTypeOptionModelList) {
        if (metadataTypeOptionModelList == null || metadataTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Metadata type option model list cannot be null or empty");
        }
        for (MetadataTypeOptionModel metadataTypeOptionModel : metadataTypeOptionModelList) {
            if (metadataTypeOptionRepository.existsByName(metadataTypeOptionModel.getName())) {
                throw new MetadataTypeOptionAlreadyExistException("Metadata type option already exists: " + metadataTypeOptionModel.getName());
            }
        }
        return metadataTypeOptionRepository.saveAll(metadataTypeOptionModelList);
    }

    /**
     * Retrieves a single MetadataTypeOption model by its ID.
     * Throws a MetadataTypeOptionNotFoundException if the MetadataTypeOption is not found or has been deleted.
     *
     * @param id                                   - the ID of the MetadataTypeOption to retrieve
     * @return                                     - the MetadataTypeOption model if found and not deleted
     * @throws MetadataTypeOptionNotFoundException - if the MetadataTypeOption is not found
     * @throws NullPointerException                - if MetadataTypeOption ID is null
     */
    @Transactional
    public MetadataTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Metadata type option ID cannot be null");
        }
        MetadataTypeOptionModel metadataTypeOptionModel = metadataTypeOptionRepository.findById(id)
                .orElseThrow(() -> new MetadataTypeOptionNotFoundException("Metadata type option not found with ID: " + id));
        if (metadataTypeOptionModel.getDeletedAt() != null) {
            throw new MetadataTypeOptionNotFoundException("Metadata type option not found with ID: " + id);
        }
        return metadataTypeOptionModel;
    }

    /**
     * Retrieves a list of MetadataTypeOption models based on the provided MetadataTypeOption IDs.
     *
     * @param metadataTypeOptionIdList - A list of MetadataTypeOption IDs to retrieve
     * @return                         - A list of MetadataTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException    - if a MetadataTypeOption ID list is null
     */
    @Transactional
    public List<MetadataTypeOptionModel> readMany(List<String> metadataTypeOptionIdList) {
        if (metadataTypeOptionIdList == null || metadataTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Metadata type option ID list cannot be null");
        }
        List<MetadataTypeOptionModel> modelList = new ArrayList<>();
        for (String id : metadataTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Metadata type option ID cannot be null");
            }
            MetadataTypeOptionModel metadataTypeOptionModel = metadataTypeOptionRepository.findById(id)
                    .orElse(null);
            if (metadataTypeOptionModel == null)
                continue;
            if (metadataTypeOptionModel.getDeletedAt() == null) {
                modelList.add(metadataTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all MetadataTypeOption models that are not marked as deleted.
     *
     * @return - a List of MetadataTypeOption models where deletedAt is null
     */
    @Transactional
    public List<MetadataTypeOptionModel> readAll() {
        return metadataTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all MetadataTypeOption models, including those marked as deleted.
     *
     * @return - A list of all MetadataTypeOptionModel objects
     */
    @Transactional
    public List<MetadataTypeOptionModel> hardReadAll() {
        return metadataTypeOptionRepository.findAll();
    }

    /**
     * Updates a single MetadataTypeOption model identified by the provided ID.
     *
     * @param model                                - The MetadataTypeOptionModel containing updated data
     * @return                                     - The updated MetadataTypeOptionModel
     * @throws MetadataTypeOptionNotFoundException - if MetadataTypeOption is not found or marked as deleted
     */
    @Transactional
    public MetadataTypeOptionModel updateOne(MetadataTypeOptionModel model) {
        MetadataTypeOptionModel existing = metadataTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new MetadataTypeOptionNotFoundException("Metadata type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new MetadataTypeOptionNotFoundException("Metadata type option with ID: " + model.getId() + " is not found");
        }
        return metadataTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple MetadataTypeOption models.
     *
     * @param modelList                            - List of MetadataTypeOptionModel objects containing updated data
     * @return                                     - List of updated MetadataTypeOptionModel objects
     * @throws MetadataTypeOptionNotFoundException - if a MetadataTypeOption is not found or marked as deleted
     */
    @Transactional
    public List<MetadataTypeOptionModel> updateMany(List<MetadataTypeOptionModel> modelList) {
        for (MetadataTypeOptionModel model : modelList) {
            MetadataTypeOptionModel existing = metadataTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new MetadataTypeOptionNotFoundException("Metadata type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new MetadataTypeOptionNotFoundException("Metadata type option with ID: " + model.getId() + " is not found");
            }
        }
        return metadataTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single MetadataTypeOption model by ID, including deleted ones.
     *
     * @param model                                - The MetadataTypeOptionModel containing updated data
     * @return                                     - The updated MetadataTypeOptionModel
     * @throws MetadataTypeOptionNotFoundException - if MetadataTypeOption is not found
     */
    @Transactional
    public MetadataTypeOptionModel hardUpdate(MetadataTypeOptionModel model) {
        return metadataTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple MetadataTypeOption models by their ID, including deleted ones.
     *
     * @param metadataTypeOptionModelList - List of MetadataTypeOptionModel objects containing updated data
     * @return                            - List of updated MetadataTypeOptionModel objects
     */
    @Transactional
    public List<MetadataTypeOptionModel> hardUpdateAll(List<MetadataTypeOptionModel> metadataTypeOptionModelList) {
        return metadataTypeOptionRepository.saveAll(metadataTypeOptionModelList);
    }

    /**
     * Soft deletes a MetadataTypeOption by ID.
     *
     * @param id                                   - The ID of the MetadataTypeOption to soft delete
     * @return                                     - The soft-deleted MetadataTypeOptionModel
     * @throws MetadataTypeOptionNotFoundException - if MetadataTypeOption ID is not found
     */
    @Transactional
    public MetadataTypeOptionModel softDelete(String id) {
        MetadataTypeOptionModel metadataTypeOptionModel = metadataTypeOptionRepository.findById(id)
                .orElseThrow(() -> new MetadataTypeOptionNotFoundException("Metadata type option not found with id: " + id));
        metadataTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return metadataTypeOptionRepository.save(metadataTypeOptionModel);
    }

    /**
     * Hard deletes a MetadataTypeOption by ID.
     *
     * @param id                                   - ID of the MetadataTypeOption to hard delete
     * @throws NullPointerException                - if the MetadataTypeOption ID is null
     * @throws MetadataTypeOptionNotFoundException - if the MetadataTypeOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Metadata type option ID cannot be null");
        }
        if (!metadataTypeOptionRepository.existsById(id)) {
            throw new MetadataTypeOptionNotFoundException("Metadata type option not found with id: " + id);
        }
        metadataTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple MetadataTypeOptions by their IDs.
     *
     * @param idList                               - List of MetadataTypeOption IDs to be soft deleted
     * @return                                     - List of soft-deleted MetadataTypeOption objects
     * @throws MetadataTypeOptionNotFoundException - if any MetadataTypeOption IDs are not found
     */
    @Transactional
    public List<MetadataTypeOptionModel> softDeleteMany(List<String> idList) {
        List<MetadataTypeOptionModel> metadataTypeOptionModelList = metadataTypeOptionRepository.findAllById(idList);
        if (metadataTypeOptionModelList.isEmpty()) {
            throw new MetadataTypeOptionNotFoundException("No metadata type options found with provided IDList: " + idList);
        }
        for (MetadataTypeOptionModel model : metadataTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return metadataTypeOptionRepository.saveAll(metadataTypeOptionModelList);
    }

    /**
     * Hard deletes multiple MetadataTypeOptions by IDs.
     *
     * @param idList - List of MetadataTypeOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        metadataTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all MetadataTypeOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        metadataTypeOptionRepository.deleteAll();
    }
}