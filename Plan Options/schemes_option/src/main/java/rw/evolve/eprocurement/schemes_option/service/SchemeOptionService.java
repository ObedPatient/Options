/**
 * Service for managing SchemeOption model.
 * Provides functionality to create, read, update, and delete SchemeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.schemes_option.service;

import jakarta.transaction.Transactional;
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

    /**
     * Creates a single Scheme option model with a generated ID.
     *
     * @param schemeOptionModel            - the SchemeOptionModel to be created
     * @return                             - the saved SchemeOption model
     * @throws SchemeAlreadyExistException - if a SchemeOption with the same name exists
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
     * Creates multiple Scheme Option model, each with a unique generated ID.
     *
     * @param schemeOptionModelList - the list of Scheme option model to be created
     * @return                      - a list of saved Scheme Option model
     * @throws NullPointerException - if the input list is null
     */
    @Transactional
    public List<SchemeOptionModel> saveMany(List<SchemeOptionModel> schemeOptionModelList) {
        if (schemeOptionModelList == null || schemeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Scheme option model list cannot be null or empty");
        }
        for (SchemeOptionModel schemeOptionModel : schemeOptionModelList) {
            if (schemeOptionRepository.existsByName(schemeOptionModel.getName())) {
                throw new SchemeAlreadyExistException("Scheme option already exists: " + schemeOptionModel.getName());
            }
        }
        return schemeOptionRepository.saveAll(schemeOptionModelList);
    }

    /**
     * Retrieves a single Scheme option model by its ID.
     * Throws a SchemeNotFoundException if the Scheme option is not found or has been deleted.
     *
     * @param id                       - the ID of the Scheme option to retrieve
     * @return                         - the Scheme option model if found and not deleted
     * @throws SchemeNotFoundException - if the Scheme option is not found
     * @throws NullPointerException    - if Scheme option ID is null
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
     * Retrieves a list of SchemeOption model list based on the provided SchemeOption ID.
     *
     * @param schemeOptionIdList       - A list of SchemeOption ID to retrieve
     * @return                         - A list of SchemeOptionModel objects that are not marked as deleted
     * @throws NullPointerException    - if a SchemeOption ID list is null
     */
    @Transactional
    public List<SchemeOptionModel> readMany(List<String> schemeOptionIdList) {
        if (schemeOptionIdList == null || schemeOptionIdList.isEmpty()) {
            throw new NullPointerException("Scheme option ID list cannot be null");
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
     * Retrieve all Scheme options that are not marked as deleted
     *
     * @return         - a List of Scheme option model where deletedAt is null
     */
    @Transactional
    public List<SchemeOptionModel> readAll() {
        return schemeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all Scheme Option model, including those marked as deleted.
     *
     * @return            - A list of all SchemeOptionModel objects
     */
    @Transactional
    public List<SchemeOptionModel> hardReadAll() {
        return schemeOptionRepository.findAll();
    }

    /**
     * Updates a single Scheme Option model identified by the provided ID.
     *
     * @param model                          - The SchemeOptionModel containing updated data
     * @return                               - The updated SchemeOptionModel
     * @throws SchemeAlreadyExistException   - if scheme option already exist
     */
    @Transactional
    public SchemeOptionModel updateOne(SchemeOptionModel model) {
        SchemeOptionModel existing = schemeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new SchemeNotFoundException("Scheme option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new SchemeNotFoundException("Scheme option with ID: " + model.getId() + " is not found");
        }
        return schemeOptionRepository.save(model);
    }


    /**
     * Updates multiple scheme option model in a transactional manner.
     *
     * @param modelList                      - List of SchemeOptionModel objects containing updated data
     * @return                               - List of updated SchemeOptionModel objects
     * @throws SchemeAlreadyExistException   - if scheme option already exist
     */
    @Transactional
    public List<SchemeOptionModel> updateMany(List<SchemeOptionModel> modelList) {
        for (SchemeOptionModel model: modelList){
            SchemeOptionModel existing = schemeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new SchemeNotFoundException("Scheme option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new SchemeNotFoundException("Scheme option with ID: " + model.getId() + " is not found");
            }
        }
        return schemeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Scheme option model by ID, including deleted ones.
     *
     * @param model                          - The SchemeOptionModel containing updated data
     * @return                               - The updated SchemeOptionModel
     * @throws SchemeAlreadyExistException   - if scheme option already exist
     */
    @Transactional
    public SchemeOptionModel hardUpdate(SchemeOptionModel model) {
        return schemeOptionRepository.save(model);
    }

    /**
     * Updates multiple SchemeOption modelList by their IDs, including deleted ones.
     *
     * @param schemeOptionModelList        - List of SchemeOptionModel objects containing updated data
     * @return                             - List of updated SchemeOptionModel objects
     */
    @Transactional
    public List<SchemeOptionModel> hardUpdateAll(List<SchemeOptionModel> schemeOptionModelList) {
        return schemeOptionRepository.saveAll(schemeOptionModelList);
    }

    /**
     * Soft deletes a Scheme option by ID.
     *
     * @return                         - The soft-deleted SchemeOptionModel
     * @throws SchemeNotFoundException - if scheme option id is not found
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
     * @throws SchemeNotFoundException - if any Scheme option ID are not found
     */
    @Transactional
    public List<SchemeOptionModel> softDeleteMany(List<String> idList) {
        List<SchemeOptionModel> schemeOptionModelList = schemeOptionRepository.findAllById(idList);
        if (schemeOptionModelList.isEmpty()) {
            throw new SchemeNotFoundException("No scheme options found with provided IDList: " + idList);
        }
        for (SchemeOptionModel model : schemeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return schemeOptionRepository.saveAll(schemeOptionModelList);
    }

    /**
     * Hard deletes multiple Scheme options by IDs.
     *
     * @param  idList     - List of Scheme option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
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