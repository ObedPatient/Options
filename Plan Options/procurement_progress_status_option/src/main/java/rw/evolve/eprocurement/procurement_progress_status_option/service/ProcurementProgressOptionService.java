/**
 * Service for managing ProcurementProgressOption model.
 * Provides functionality to create, read, update, and delete ProcurementProgressOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
        package rw.evolve.eprocurement.procurement_progress_status_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_progress_status_option.exception.ProcurementProgressAlreadyExistException;
import rw.evolve.eprocurement.procurement_progress_status_option.exception.ProcurementProgressNotFoundException;
import rw.evolve.eprocurement.procurement_progress_status_option.model.ProcurementProgressOptionModel;
import rw.evolve.eprocurement.procurement_progress_status_option.repository.ProcurementProgressOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementProgressOptionService {

    @Autowired
    private ProcurementProgressOptionRepository procurementProgressOptionRepository;

    /**
     * Creates a single Procurement Progress option model.
     *
     * @param procurementProgressOptionModel            - the ProcurementProgressOptionModel to be created
     * @return                                          - the saved ProcurementProgressOption model
     * @throws ProcurementProgressAlreadyExistException - if a ProcurementProgressOption with the same name exists
     * @throws NullPointerException                     - if the input model is null
     */
    @Transactional
    public ProcurementProgressOptionModel save(ProcurementProgressOptionModel procurementProgressOptionModel) {
        if (procurementProgressOptionModel == null) {
            throw new NullPointerException("Procurement progress option cannot be null");
        }
        if (procurementProgressOptionRepository.existsByName(procurementProgressOptionModel.getName())) {
            throw new ProcurementProgressAlreadyExistException("Procurement progress option already exists: " + procurementProgressOptionModel.getName());
        }
        return procurementProgressOptionRepository.save(procurementProgressOptionModel);
    }

    /**
     * Creates multiple Procurement Progress option model.
     *
     * @param procurementProgressOptionModelList        - the list of ProcurementProgressOption models to be created
     * @return                                          - a list of saved ProcurementProgressOption models
     * @throws IllegalArgumentException                 - if the input list is null or empty
     * @throws ProcurementProgressAlreadyExistException - if a ProcurementProgressOption with the same name exists
     */
    @Transactional
    public List<ProcurementProgressOptionModel> saveMany(List<ProcurementProgressOptionModel> procurementProgressOptionModelList) {
        if (procurementProgressOptionModelList == null || procurementProgressOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement progress option model list cannot be null or empty");
        }
        for (ProcurementProgressOptionModel model : procurementProgressOptionModelList) {
            if (procurementProgressOptionRepository.existsByName(model.getName())) {
                throw new ProcurementProgressAlreadyExistException("Procurement progress option already exists: " + model.getName());
            }
        }
        return procurementProgressOptionRepository.saveAll(procurementProgressOptionModelList);
    }

    /**
     * Retrieves a single Procurement Progress option model by its ID.
     * Throws a ProcurementProgressNotFoundException if the option is not found or has been deleted.
     *
     * @param id                                    - the ID of the ProcurementProgressOption to retrieve
     * @return                                      - the ProcurementProgressOption model if found and not deleted
     * @throws ProcurementProgressNotFoundException - if the option is not found
     * @throws NullPointerException                 - if the ID is null
     */
    @Transactional
    public ProcurementProgressOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement progress option ID cannot be null");
        }
        ProcurementProgressOptionModel model = procurementProgressOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementProgressNotFoundException("Procurement progress option not found with ID: " + id));
        if (model.getDeletedAt() != null) {
            throw new ProcurementProgressNotFoundException("Procurement progress option not found with ID: " + id);
        }
        return model;
    }

    /**
     * Retrieves a list of ProcurementProgressOption models based on the provided IDs.
     *
     * @param idList                              - A list of ProcurementProgressOption IDs to retrieve
     * @return                                    - A list of ProcurementProgressOptionModel objects that are not marked as deleted
     * @throws NullPointerException               - if the ID list is null or contains null IDs
     */
    @Transactional
    public List<ProcurementProgressOptionModel> readMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Procurement progress option ID list cannot be null");
        }
        List<ProcurementProgressOptionModel> modelList = new ArrayList<>();
        for (String id : idList) {
            if (id == null) {
                throw new NullPointerException("Procurement progress option ID cannot be null");
            }
            ProcurementProgressOptionModel model = procurementProgressOptionRepository.findById(id)
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
     * Retrieves all ProcurementProgressOption models that are not marked as deleted.
     *
     * @return - a List of ProcurementProgressOption models where deletedAt is null
     */
    @Transactional
    public List<ProcurementProgressOptionModel> readAll() {
        return procurementProgressOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ProcurementProgressOption models, including those marked as deleted.
     *
     * @return - A list of all ProcurementProgressOptionModel objects
     */
    @Transactional
    public List<ProcurementProgressOptionModel> hardReadAll() {
        return procurementProgressOptionRepository.findAll();
    }

    /**
     * Updates a single ProcurementProgressOption model identified by the provided ID.
     *
     * @param model                                 - The ProcurementProgressOptionModel containing updated data
     * @return                                      - The updated ProcurementProgressOptionModel
     * @throws ProcurementProgressNotFoundException - if the option is not found or is marked as deleted
     */
    @Transactional
    public ProcurementProgressOptionModel updateOne(ProcurementProgressOptionModel model) {
        ProcurementProgressOptionModel existing = procurementProgressOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ProcurementProgressNotFoundException("Procurement progress option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ProcurementProgressNotFoundException("Procurement progress option not found with ID: " + model.getId());
        }
        return procurementProgressOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementProgressOption models in a transactional manner.
     *
     * @param modelList                             - List of ProcurementProgressOptionModel objects containing updated data
     * @return                                      - List of updated ProcurementProgressOptionModel objects
     * @throws ProcurementProgressNotFoundException - if any option is not found or is marked as deleted
     */
    @Transactional
    public List<ProcurementProgressOptionModel> updateMany(List<ProcurementProgressOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement progress option model list cannot be null or empty");
        }
        for (ProcurementProgressOptionModel model : modelList) {
            ProcurementProgressOptionModel existing = procurementProgressOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ProcurementProgressNotFoundException("Procurement Progress option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ProcurementProgressNotFoundException("Procurement progress option not found with ID: " + model.getId());
            }
        }
        return procurementProgressOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single ProcurementProgressOption model by ID, including soft-deleted ones.
     *
     * @param model                               - The ProcurementProgressOptionModel containing updated data
     * @return                                    - The updated ProcurementProgressOptionModel
     */
    @Transactional
    public ProcurementProgressOptionModel hardUpdate(ProcurementProgressOptionModel model) {
        return procurementProgressOptionRepository.save(model);
    }

    /**
     * Updates all ProcurementProgressOption models by their IDs, including soft-deleted ones.
     *
     * @param modelList                           - List of ProcurementProgressOptionModel objects containing updated data
     * @return                                    - List of updated ProcurementProgressOptionModel objects
     */
    @Transactional
    public List<ProcurementProgressOptionModel> hardUpdateAll(List<ProcurementProgressOptionModel> modelList) {
        return procurementProgressOptionRepository.saveAll(modelList);
    }

    /**
     * Soft deletes a Procurement Progress option by ID.
     *
     * @param id                                    - The ID of the ProcurementProgressOption to soft delete
     * @return                                      - The soft-deleted ProcurementProgressOptionModel
     * @throws ProcurementProgressNotFoundException - if the option is not found
     */
    @Transactional
    public ProcurementProgressOptionModel softDelete(String id) {
        ProcurementProgressOptionModel model = procurementProgressOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementProgressNotFoundException("Procurement progress option not found with id: " + id));
        model.setDeletedAt(LocalDateTime.now());
        return procurementProgressOptionRepository.save(model);
    }

    /**
     * Hard deletes a Procurement Progress option by ID.
     *
     * @param id                                    - ID of the ProcurementProgressOption to hard delete
     * @throws NullPointerException                 - if the ID is null
     * @throws ProcurementProgressNotFoundException - if the option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement progress option ID cannot be null");
        }
        if (!procurementProgressOptionRepository.existsById(id)) {
            throw new ProcurementProgressNotFoundException("Procurement progress option not found with id: " + id);
        }
        procurementProgressOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Procurement Progress options by their IDs.
     *
     * @param idList                                - List of ProcurementProgressOption IDs to be soft deleted
     * @return                                      - List of soft-deleted ProcurementProgressOption objects
     * @throws ProcurementProgressNotFoundException - if any IDs are not found
     */
    @Transactional
    public List<ProcurementProgressOptionModel> softDeleteMany(List<String> idList) {
        List<ProcurementProgressOptionModel> modelList = procurementProgressOptionRepository.findAllById(idList);
        if (modelList.isEmpty()) {
            throw new ProcurementProgressNotFoundException("No Procurement progress options found with provided IDs: " + idList);
        }
        for (ProcurementProgressOptionModel model : modelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return procurementProgressOptionRepository.saveAll(modelList);
    }

    /**
     * Hard deletes multiple Procurement Progress options by IDs.
     *
     * @param idList - List of ProcurementProgressOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        procurementProgressOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Procurement Progress options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        procurementProgressOptionRepository.deleteAll();
    }
}