/**
 * Service for managing SchemeOption entities.
 * Provides functionality to create, read, update, and delete SchemeOption data, supporting both
 * soft and hard deletion operations.
 */
package rw.evolve.eprocurement.schemes_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.schemes_option.exception.SchemeAlreadyExistException;
import rw.evolve.eprocurement.schemes_option.exception.SchemeNotFoundException;
import rw.evolve.eprocurement.schemes_option.model.SchemeOptionModel;
import rw.evolve.eprocurement.schemes_option.repository.SchemeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchemeOptionService {

    @Autowired
    private SchemeOptionRepository schemeOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Scheme option entity with a generated ID.
     *
     * @param schemeOptionModel the SchemeOptionModel to be created
     * @return the saved SchemeOption model
     * @throws SchemeAlreadyExistException if a SchemeOption with the same name exists
     */
    @Transactional
    public SchemeOptionModel save(SchemeOptionModel schemeOptionModel) {
        if (schemeOptionModel == null) {
            throw new NullPointerException("Scheme option cannot be null");
        }
        if (schemeOptionRepository.existsByName(schemeOptionModel.getName())) {
            throw new SchemeAlreadyExistException("Scheme option already exists: " + schemeOptionModel.getName());
        }
        return schemeOptionRepository.save(schemeOptionModel);
    }

    /**
     * Creates multiple Scheme Option entities, each with a unique generated ID.
     *
     * @param schemeOptionModelList - the list of Scheme option models to be created
     * @return                      - a list of saved Scheme Option models
     * @throws NullPointerException - if the input list is null
     */
    @Transactional
    public List<SchemeOptionModel> saveMany(List<SchemeOptionModel> schemeOptionModelList) {
        if (schemeOptionModelList == null) {
            throw new NullPointerException("Scheme option model list cannot be null");
        }
        List<SchemeOptionModel> savedSchemeModelList = new ArrayList<>();
        for (SchemeOptionModel schemeOptionModel : schemeOptionModelList) {
            if (schemeOptionModel == null) {
                throw new NullPointerException("Scheme option cannot be null");
            }
            if (schemeOptionRepository.existsByName(schemeOptionModel.getName())) {
                throw new SchemeAlreadyExistException("Scheme Option Already exists: " + schemeOptionModel.getName());
            }
        }
        return schemeOptionRepository.saveAll(savedSchemeModelList);
    }

    /**
     * Retrieves a single Scheme option model by its ID.
     * Throws a SchemeNotFoundException if the Scheme option is not found or has been deleted.
     *
     * @param id                       - the ID of the Scheme option to retrieve
     * @return                         - the Scheme option model if found and not deleted
     * @throws SchemeNotFoundException - if the Scheme option is not found
     */
    @Transactional
    public SchemeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Scheme option ID cannot be null");
        }
        SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                .orElseThrow(() -> new SchemeNotFoundException("Scheme option not found with ID: " + id));
        if (schemeOptionModel.getDeletedAt() != null) {
            throw new SchemeNotFoundException("Scheme option not found with ID: " + id);
        }
        return schemeOptionModel;
    }

    /**
     * Retrieves a list of SchemeOption objects based on the provided SchemeOption IDs.
     *
     * @param schemeOptionIdList       - A list of SchemeOption IDs to retrieve
     * @return                         - A list of SchemeOptionModel objects that are not marked as deleted
     * @throws SchemeNotFoundException - if a SchemeOption with the given ID is not found
     */
    @Transactional
    public List<SchemeOptionModel> readMany(List<String> schemeOptionIdList) {
        if (schemeOptionIdList == null || schemeOptionIdList.isEmpty()) {
            throw new NullPointerException("Scheme option IDs list cannot be null");
        }
        List<SchemeOptionModel> modelList = new ArrayList<>();
        for (String id : schemeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Scheme option ID cannot be null");
            }
            SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                    .orElse(null);
            if (schemeOptionModel == null)
                continue;
            if (schemeOptionModel.getDeletedAt() == null) {
                modelList.add(schemeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Scheme Options that are not marked as deleted
     *
     * @return         - a List of Scheme option objects where deletedAt is null
     */
    @Transactional
    public List<SchemeOptionModel> readAll() {
        return schemeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all Scheme Option models, including those marked as deleted.
     *
     * @return                         - A list of all SchemeOptionModel objects
     */
    @Transactional
    public List<SchemeOptionModel> hardReadAll() {
        return schemeOptionRepository.findAll();
    }

    /**
     * Updates a single Scheme Option model identified by the provided ID.
     *
     * @param model                    - The SchemeOptionModel containing updated data
     * @return                         - The updated SchemeOptionModel
     * @throws SchemeNotFoundException - if the SchemeOptionModel is not found or is marked as deleted
     */
    @Transactional
    public SchemeOptionModel updateOne(SchemeOptionModel model) {
        return schemeOptionRepository.save(model);
    }

    /**
     * Updates multiple SchemeOption models in a transactional manner.
     *
     * @param modelList - List of SchemeOptionModel objects containing updated data
     * @return          - List of updated SchemeOptionModel objects
     */
    @Transactional
    public List<SchemeOptionModel> updateMany(List<SchemeOptionModel> modelList) {
        return schemeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Scheme option model by ID, including deleted ones.
     *
     * @param model                    - The SchemeOptionModel containing updated data
     * @return                         - The updated SchemeOptionModel
     * @throws NullPointerException    - if the Scheme option ID or model is null
     * @throws SchemeNotFoundException - if the Scheme option is not found
     */
    @Transactional
    public SchemeOptionModel hardUpdate(SchemeOptionModel model) {
        return schemeOptionRepository.save(model);
    }

    /**
     * Updates multiple SchemeOption models by their IDs, including deleted ones.
     *
     * @param schemeOptionModelList    - List of SchemeOptionModel objects containing updated data
     * @return                         - List of updated SchemeOptionModel objects
     */
    @Transactional
    public List<SchemeOptionModel> hardUpdateAll(List<SchemeOptionModel> schemeOptionModelList) {
        return schemeOptionRepository.saveAll(schemeOptionModelList);
    }

    /**
     * Soft deletes a Scheme option by ID in a transactional manner.
     *
     * @return   - The soft-deleted SchemeOptionModel
     */
    @Transactional
    public SchemeOptionModel softDelete(String id) {
        SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                .orElseThrow(() -> new SchemeNotFoundException("Scheme option not found with id: " + id));
        schemeOptionModel.setDeletedAt(LocalDateTime.now());
        return schemeOptionRepository.save(schemeOptionModel);
    }

    /**
     * Hard deletes a Scheme option by ID.
     *
     * @param id                       - ID of the Scheme option to hard delete
     * @throws NullPointerException    - if the Scheme option ID is null
     * @throws SchemeNotFoundException - if the Scheme option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Scheme option ID cannot be null");
        }
        if (!schemeOptionRepository.existsById(id)) {
            throw new SchemeNotFoundException("Scheme option not found with id: " + id);
        }
        schemeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Scheme options by their IDs.
     *
     * @param idList                   - List of Scheme option IDs to be soft deleted
     * @return                         - List of soft-deleted SchemeOption objects
     * @throws NullPointerException    - if any Scheme option ID is null
     * @throws SchemeNotFoundException - if any Scheme option IDs are not found
     */
    @Transactional
    public List<SchemeOptionModel> softDeleteMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Scheme option IDs list cannot be null or empty");
        }
        if (idList.contains(null)) {
            throw new NullPointerException("Scheme option ID cannot be null");
        }
        List<SchemeOptionModel> schemeOptionModels = schemeOptionRepository.findAllById(idList);
        if (schemeOptionModels.isEmpty()) {
            throw new SchemeNotFoundException("No scheme options found with provided IDs: " + idList);
        }
        for (SchemeOptionModel model : schemeOptionModels) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return schemeOptionRepository.saveAll(schemeOptionModels);
    }

    /**
     * Hard deletes multiple Scheme options by IDs.
     *
     * @param  idList                  - List of Scheme option IDs to hard delete
     * @throws NullPointerException    - if the Scheme option IDs list is null or empty
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Scheme option IDs list cannot be null or empty");
        }
        schemeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Scheme options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        schemeOptionRepository.deleteAll();
    }
}