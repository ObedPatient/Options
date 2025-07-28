/**
 * Service for managing ProcurementMethodOption entities.
 * Provides functionality to create, read, update, and delete ProcurementMethodOption data, supporting both
 * soft and hard deletion operations.
 */
package rw.evolve.eprocurement.procurement_method_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_method_option.exception.ProcurementMethodNotFoundException;
import rw.evolve.eprocurement.procurement_method_option.exception.ProcurementMethodOptionAlreadyExistException;
import rw.evolve.eprocurement.procurement_method_option.model.ProcurementMethodOptionModel;
import rw.evolve.eprocurement.procurement_method_option.repository.ProcurementMethodOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementMethodOptionService {

    @Autowired
    private ProcurementMethodOptionRepository procurementMethodOptionRepository;

    /**
     * Creates a single Procurement Method option model with a generated ID.
     *
     * @param procurementMethodOptionModel                  - the ProcurementMethodOptionModel to be created
     * @return                                              - the saved ProcurementMethodOption model
     * @throws ProcurementMethodOptionAlreadyExistException - if a ProcurementMethodOption with the same name exists
     */
    @Transactional
    public ProcurementMethodOptionModel save(ProcurementMethodOptionModel procurementMethodOptionModel) {
        if (procurementMethodOptionModel == null) {
            throw new NullPointerException("Procurement Method option cannot be null");
        }
        if (procurementMethodOptionRepository.existsByName(procurementMethodOptionModel.getName())) {
            throw new ProcurementMethodOptionAlreadyExistException("Procurement Method option already exists: " + procurementMethodOptionModel.getName());
        }
        return procurementMethodOptionRepository.save(procurementMethodOptionModel);
    }

    /**
     * Creates multiple Procurement Method option models, each with a unique generated ID.
     *
     * @param procurementMethodOptionModelList - the list of Procurement Method option models to be created
     * @return                                 - a list of saved Procurement Method option models
     * @throws IllegalArgumentException        - if the input list is null or empty
     */
    @Transactional
    public List<ProcurementMethodOptionModel> saveMany(List<ProcurementMethodOptionModel> procurementMethodOptionModelList) {
        if (procurementMethodOptionModelList == null || procurementMethodOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement Method option model list cannot be null or empty");
        }
        for (ProcurementMethodOptionModel procurementMethodOptionModel : procurementMethodOptionModelList) {
            if (procurementMethodOptionRepository.existsByName(procurementMethodOptionModel.getName())) {
                throw new ProcurementMethodOptionAlreadyExistException("Procurement Method option already exists: " + procurementMethodOptionModel.getName());
            }
        }
        return procurementMethodOptionRepository.saveAll(procurementMethodOptionModelList);
    }

    /**
     * Retrieves a single Procurement Method option model by its ID.
     * Throws a ProcurementMethodNotFoundException if the Procurement Method option is not found or has been deleted.
     *
     * @param id                                  - the ID of the Procurement Method option to retrieve
     * @return                                    - the Procurement Method option model if found and not deleted
     * @throws ProcurementMethodNotFoundException - if the Procurement Method option is not found
     * @throws NullPointerException               - if Procurement Method option ID is null
     */
    @Transactional
    public ProcurementMethodOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement Method option ID cannot be null");
        }
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementMethodNotFoundException("Procurement Method option not found with ID: " + id));
        if (procurementMethodOptionModel.getDeletedAt() != null) {
            throw new ProcurementMethodNotFoundException("Procurement Method option not found with ID: " + id);
        }
        return procurementMethodOptionModel;
    }

    /**
     * Retrieves a list of ProcurementMethodOption models based on the provided ProcurementMethodOption IDs.
     *
     * @param procurementMethodOptionIdList - A list of ProcurementMethodOption IDs to retrieve
     * @return                              - A list of ProcurementMethodOptionModel objects that are not marked as deleted
     * @throws NullPointerException         - if a ProcurementMethodOption ID list is null
     */
    @Transactional
    public List<ProcurementMethodOptionModel> readMany(List<String> procurementMethodOptionIdList) {
        if (procurementMethodOptionIdList == null || procurementMethodOptionIdList.isEmpty()) {
            throw new NullPointerException("Procurement Method option ID list cannot be null");
        }
        List<ProcurementMethodOptionModel> modelList = new ArrayList<>();
        for (String id : procurementMethodOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Procurement Method option ID cannot be null");
            }
            ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                    .orElse(null);
            if (procurementMethodOptionModel == null) {
                continue;
            }
            if (procurementMethodOptionModel.getDeletedAt() == null) {
                modelList.add(procurementMethodOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Procurement Method options that are not marked as deleted.
     *
     * @return - a List of Procurement Method option models where deletedAt is null
     */
    @Transactional
    public List<ProcurementMethodOptionModel> readAll() {
        return procurementMethodOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ProcurementMethodOption models, including those marked as deleted.
     *
     * @return - A list of all ProcurementMethodOptionModel objects
     */
    @Transactional
    public List<ProcurementMethodOptionModel> hardReadAll() {
        return procurementMethodOptionRepository.findAll();
    }

    /**
     * Updates a single Procurement Method option model identified by the provided ID.
     *
     * @param model                                         - The ProcurementMethodOptionModel containing updated data
     * @return                                              - The updated ProcurementMethodOptionModel
     * @throws ProcurementMethodOptionAlreadyExistException - if Procurement Method option already exists
     */
    @Transactional
    public ProcurementMethodOptionModel updateOne(ProcurementMethodOptionModel model) {
        if (model.getName() != null && procurementMethodOptionRepository.existsByNameAndIdNot(model.getName(), model.getId())) {
            throw new ProcurementMethodOptionAlreadyExistException("Procurement Method option already exists with name: " + model.getName());
        }
        ProcurementMethodOptionModel existing = procurementMethodOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ProcurementMethodNotFoundException("Procurement Method option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ProcurementMethodNotFoundException("Procurement Method option with ID: " + model.getId() + " is not found");
        }
        return procurementMethodOptionRepository.save(model);
    }

    /**
     * Updates multiple Procurement Method option models in a transactional manner.
     *
     * @param modelList                                     - List of ProcurementMethodOptionModel objects containing updated data
     * @return                                              - List of updated ProcurementMethodOptionModel objects
     * @throws ProcurementMethodOptionAlreadyExistException - if Procurement Method option already exists
     */
    @Transactional
    public List<ProcurementMethodOptionModel> updateMany(List<ProcurementMethodOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement Method option model list cannot be null or empty");
        }
        for (ProcurementMethodOptionModel model : modelList) {
            if (model.getName() != null && procurementMethodOptionRepository.existsByNameAndIdNot(model.getName(), model.getId())) {
                throw new ProcurementMethodOptionAlreadyExistException("Procurement Method option already exists with name: " + model.getName());
            }
            ProcurementMethodOptionModel existing = procurementMethodOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ProcurementMethodNotFoundException("Procurement Method option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ProcurementMethodNotFoundException("Procurement Method option with ID: " + model.getId() + " is not found");
            }
        }
        return procurementMethodOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Procurement Method option model by ID, including deleted ones.
     *
     * @param model                                         - The ProcurementMethodOptionModel containing updated data
     * @return                                              - The updated ProcurementMethodOptionModel
     * @throws ProcurementMethodOptionAlreadyExistException - if Procurement Method option already exists
     */
    @Transactional
    public ProcurementMethodOptionModel hardUpdate(ProcurementMethodOptionModel model) {
        if (model.getName() != null && procurementMethodOptionRepository.existsByNameAndIdNot(model.getName(), model.getId())) {
            throw new ProcurementMethodOptionAlreadyExistException("Procurement Method option already exists with name: " + model.getName());
        }
        return procurementMethodOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementMethodOption models by their IDs, including deleted ones.
     *
     * @param procurementMethodOptionModelList              - List of ProcurementMethodOptionModel objects containing updated data
     * @return                                              - List of updated ProcurementMethodOptionModel objects
     * @throws ProcurementMethodOptionAlreadyExistException - if any Procurement Method option already exists
     */
    @Transactional
    public List<ProcurementMethodOptionModel> hardUpdateAll(List<ProcurementMethodOptionModel> procurementMethodOptionModelList) {
        if (procurementMethodOptionModelList == null || procurementMethodOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement Method option model list cannot be null or empty");
        }
        for (ProcurementMethodOptionModel model : procurementMethodOptionModelList) {
            if (model.getName() != null && procurementMethodOptionRepository.existsByNameAndIdNot(model.getName(), model.getId())) {
                throw new ProcurementMethodOptionAlreadyExistException("Procurement Method option already exists with name: " + model.getName());
            }
        }
        return procurementMethodOptionRepository.saveAll(procurementMethodOptionModelList);
    }

    /**
     * Soft deletes a Procurement Method option by ID.
     *
     * @param id                                  - The ID of the Procurement Method option to soft delete
     * @return                                    - The soft-deleted ProcurementMethodOptionModel
     * @throws ProcurementMethodNotFoundException - if Procurement Method option ID is not found
     */
    @Transactional
    public ProcurementMethodOptionModel softDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement Method option ID cannot be null");
        }
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementMethodNotFoundException("Procurement Method option not found with id: " + id));
        if (procurementMethodOptionModel.getDeletedAt() != null) {
            throw new ProcurementMethodNotFoundException("Procurement Method option not found with id: " + id);
        }
        procurementMethodOptionModel.setDeletedAt(LocalDateTime.now());
        return procurementMethodOptionRepository.save(procurementMethodOptionModel);
    }

    /**
     * Hard deletes a Procurement Method option by ID.
     *
     * @param id                                  - ID of the Procurement Method option to hard delete
     * @throws NullPointerException               - if the Procurement Method option ID is null
     * @throws ProcurementMethodNotFoundException - if the Procurement Method option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement Method option ID cannot be null");
        }
        if (!procurementMethodOptionRepository.existsById(id)) {
            throw new ProcurementMethodNotFoundException("Procurement Method option not found with id: " + id);
        }
        procurementMethodOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Procurement Method options by their IDs.
     *
     * @param idList                              - List of Procurement Method option IDs to be soft deleted
     * @return                                    - List of soft-deleted ProcurementMethodOption objects
     * @throws ProcurementMethodNotFoundException - if any Procurement Method option IDs are not found
     */
    @Transactional
    public List<ProcurementMethodOptionModel> softDeleteMany(List<String> idList) {
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionRepository.findAllById(idList);
        if (procurementMethodOptionModels.isEmpty()) {
            throw new ProcurementMethodNotFoundException("No Procurement Method options found with provided ID list: " + idList);
        }
        for (ProcurementMethodOptionModel model : procurementMethodOptionModels) {
            if (model.getDeletedAt() != null) {
                throw new ProcurementMethodNotFoundException("Procurement Method option not found with id: " + model.getId());
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        return procurementMethodOptionRepository.saveAll(procurementMethodOptionModels);
    }

    /**
     * Hard deletes multiple Procurement Method options by IDs.
     *
     * @param idList - List of Procurement Method option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Procurement Method option ID list cannot be null or empty");
        }
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionRepository.findAllById(idList);
        if (procurementMethodOptionModels.isEmpty()) {
            throw new ProcurementMethodNotFoundException("No Procurement Method options found with provided ID list: " + idList);
        }
        procurementMethodOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Procurement Method options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        procurementMethodOptionRepository.deleteAll();
    }
}