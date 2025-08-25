package rw.evolve.eprocurement.procurement_method_thresholds.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_method_thresholds.exception.ProcurementMethodThresholdAlreadyExistException;
import rw.evolve.eprocurement.procurement_method_thresholds.exception.ProcurementMethodThresholdNotFoundException;
import rw.evolve.eprocurement.procurement_method_thresholds.model.ProcurementMethodThresholdModel;
import rw.evolve.eprocurement.procurement_method_thresholds.repository.ProcurementMethodThresholdOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing ProcurementMethodThreshold model.
 * Provides functionality to create, read, update, and delete ProcurementMethodThreshold data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
@AllArgsConstructor
public class ProcurementMethodThresholdService {

    private ProcurementMethodThresholdOptionRepository procurementMethodThresholdOptionRepository;

    /**
     * Creates a single ProcurementMethodThreshold model with a generated ID.
     *
     * @param procurementMethodThresholdModel                  - the ProcurementMethodThresholdModel to be created
     * @return                                                 - the saved ProcurementMethodThreshold model
     * @throws ProcurementMethodThresholdAlreadyExistException - if a ProcurementMethodThreshold with the same name exists
     */
    @Transactional
    public ProcurementMethodThresholdModel save(ProcurementMethodThresholdModel procurementMethodThresholdModel) {
        if (procurementMethodThresholdModel == null) {
            throw new NullPointerException("Procurement method threshold cannot be null");
        }
        if (procurementMethodThresholdOptionRepository.existsByName(procurementMethodThresholdModel.getName())) {
            throw new ProcurementMethodThresholdAlreadyExistException("Procurement method threshold already exists: " + procurementMethodThresholdModel.getName());
        }
        return procurementMethodThresholdOptionRepository.save(procurementMethodThresholdModel);
    }

    /**
     * Creates multiple ProcurementMethodThreshold model, each with a unique generated ID.
     *
     * @param procurementMethodThresholdModelList - the list of ProcurementMethodThreshold models to be created
     * @return                                    - a list of saved ProcurementMethodThreshold models
     * @throws NullPointerException               - if the input list is null
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> saveMany(List<ProcurementMethodThresholdModel> procurementMethodThresholdModelList) {
        if (procurementMethodThresholdModelList == null || procurementMethodThresholdModelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement method threshold model list cannot be null or empty");
        }
        for (ProcurementMethodThresholdModel procurementMethodThresholdModel : procurementMethodThresholdModelList) {
            if (procurementMethodThresholdOptionRepository.existsByName(procurementMethodThresholdModel.getName())) {
                throw new ProcurementMethodThresholdAlreadyExistException("Procurement method threshold already exists: " + procurementMethodThresholdModel.getName());
            }
        }
        return procurementMethodThresholdOptionRepository.saveAll(procurementMethodThresholdModelList);
    }

    /**
     * Retrieves a single ProcurementMethodThreshold model by its ID.
     * Throws a ProcurementMethodThresholdNotFoundException if the ProcurementMethodThreshold is not found or has been deleted.
     *
     * @param id                                           - the ID of the ProcurementMethodThreshold to retrieve
     * @return                                             - the ProcurementMethodThreshold model if found and not deleted
     * @throws ProcurementMethodThresholdNotFoundException - if the ProcurementMethodThreshold is not found
     * @throws NullPointerException                        - if ProcurementMethodThreshold ID is null
     */
    @Transactional
    public ProcurementMethodThresholdModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement method threshold ID cannot be null");
        }
        ProcurementMethodThresholdModel procurementMethodThresholdModel = procurementMethodThresholdOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementMethodThresholdNotFoundException("Procurement method threshold not found with ID: " + id));
        if (procurementMethodThresholdModel.getDeletedAt() != null) {
            throw new ProcurementMethodThresholdNotFoundException("Procurement method threshold not found with ID: " + id);
        }
        return procurementMethodThresholdModel;
    }

    /**
     * Retrieves a list of ProcurementMethodThreshold model based on the provided ProcurementMethodThreshold ID.
     *
     * @param procurementMethodThresholdIdList    - A list of ProcurementMethodThreshold IDs to retrieve
     * @return                                    - A list of ProcurementMethodThresholdModel objects that are not marked as deleted
     * @throws NullPointerException               - if a ProcurementMethodThreshold ID list is null
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> readMany(List<String> procurementMethodThresholdIdList) {
        if (procurementMethodThresholdIdList == null || procurementMethodThresholdIdList.isEmpty()) {
            throw new NullPointerException("Procurement method threshold ID list cannot be null");
        }
        List<ProcurementMethodThresholdModel> modelList = new ArrayList<>();
        for (String id : procurementMethodThresholdIdList) {
            if (id == null) {
                throw new NullPointerException("Procurement method threshold ID cannot be null");
            }
            ProcurementMethodThresholdModel procurementMethodThresholdModel = procurementMethodThresholdOptionRepository.findById(id)
                    .orElse(null);
            if (procurementMethodThresholdModel == null)
                continue;
            if (procurementMethodThresholdModel.getDeletedAt() == null) {
                modelList.add(procurementMethodThresholdModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all ProcurementMethodThreshold models that are not marked as deleted.
     *
     * @return - a List of ProcurementMethodThreshold models where deletedAt is null
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> readAll() {
        return procurementMethodThresholdOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ProcurementMethodThreshold models, including those marked as deleted.
     *
     * @return - A list of all ProcurementMethodThresholdModel objects
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> hardReadAll() {
        return procurementMethodThresholdOptionRepository.findAll();
    }

    /**
     * Updates a single ProcurementMethodThreshold model identified by the provided ID.
     *
     * @param model                                        - The ProcurementMethodThresholdModel containing updated data
     * @return                                             - The updated ProcurementMethodThresholdModel
     * @throws ProcurementMethodThresholdNotFoundException - if ProcurementMethodThreshold is not found or marked as deleted
     */
    @Transactional
    public ProcurementMethodThresholdModel updateOne(ProcurementMethodThresholdModel model) {
        ProcurementMethodThresholdModel existing = procurementMethodThresholdOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ProcurementMethodThresholdNotFoundException("Procurement method threshold not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ProcurementMethodThresholdNotFoundException("Procurement method threshold with ID: " + model.getId() + " is not found");
        }
        return procurementMethodThresholdOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementMethodThreshold model.
     *
     * @param modelList                                    - List of ProcurementMethodThresholdModel objects containing updated data
     * @return                                             - List of updated ProcurementMethodThresholdModel objects
     * @throws ProcurementMethodThresholdNotFoundException - if a ProcurementMethodThreshold is not found or marked as deleted
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> updateMany(List<ProcurementMethodThresholdModel> modelList) {
        for (ProcurementMethodThresholdModel model : modelList) {
            ProcurementMethodThresholdModel existing = procurementMethodThresholdOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ProcurementMethodThresholdNotFoundException("Procurement method threshold not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ProcurementMethodThresholdNotFoundException("Procurement method threshold with ID: " + model.getId() + " is not found");
            }
        }
        return procurementMethodThresholdOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single ProcurementMethodThreshold model by ID, including deleted ones.
     *
     * @param model                                        - The ProcurementMethodThresholdModel containing updated data
     * @return                                             - The updated ProcurementMethodThresholdModel
     * @throws ProcurementMethodThresholdNotFoundException - if ProcurementMethodThreshold is not found
     */
    @Transactional
    public ProcurementMethodThresholdModel hardUpdate(ProcurementMethodThresholdModel model) {
        return procurementMethodThresholdOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementMethodThreshold model by their ID, including deleted ones.
     *
     * @param procurementMethodThresholdModelList - List of ProcurementMethodThresholdModel objects containing updated data
     * @return                                    - List of updated ProcurementMethodThresholdModel objects
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> hardUpdateAll(List<ProcurementMethodThresholdModel> procurementMethodThresholdModelList) {
        return procurementMethodThresholdOptionRepository.saveAll(procurementMethodThresholdModelList);
    }

    /**
     * Soft deletes a ProcurementMethodThreshold by ID.
     *
     * @param id                                           - The ID of the ProcurementMethodThreshold to soft delete
     * @return                                             - The soft-deleted ProcurementMethodThresholdModel
     * @throws ProcurementMethodThresholdNotFoundException - if ProcurementMethodThreshold ID is not found
     */
    @Transactional
    public ProcurementMethodThresholdModel softDelete(String id) {
        ProcurementMethodThresholdModel procurementMethodThresholdModel = procurementMethodThresholdOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementMethodThresholdNotFoundException("Procurement method threshold not found with id: " + id));
        procurementMethodThresholdModel.setDeletedAt(LocalDateTime.now());
        return procurementMethodThresholdOptionRepository.save(procurementMethodThresholdModel);
    }

    /**
     * Hard deletes a ProcurementMethodThreshold by ID.
     *
     * @param id                                           - ID of the ProcurementMethodThreshold to hard delete
     * @throws NullPointerException                        - if the ProcurementMethodThreshold ID is null
     * @throws ProcurementMethodThresholdNotFoundException - if the ProcurementMethodThreshold is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement method threshold ID cannot be null");
        }
        if (!procurementMethodThresholdOptionRepository.existsById(id)) {
            throw new ProcurementMethodThresholdNotFoundException("Procurement method threshold not found with id: " + id);
        }
        procurementMethodThresholdOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple ProcurementMethodThreshold by their ID.
     *
     * @param idList                                       - List of ProcurementMethodThreshold IDs to be soft deleted
     * @return                                             - List of soft-deleted ProcurementMethodThreshold objects
     * @throws ProcurementMethodThresholdNotFoundException - if any ProcurementMethodThreshold IDs are not found
     */
    @Transactional
    public List<ProcurementMethodThresholdModel> softDeleteMany(List<String> idList) {
        List<ProcurementMethodThresholdModel> procurementMethodThresholdModelList = procurementMethodThresholdOptionRepository.findAllById(idList);
        if (procurementMethodThresholdModelList.isEmpty()) {
            throw new ProcurementMethodThresholdNotFoundException("No procurement method thresholds found with provided IDList: " + idList);
        }
        for (ProcurementMethodThresholdModel model : procurementMethodThresholdModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return procurementMethodThresholdOptionRepository.saveAll(procurementMethodThresholdModelList);
    }

    /**
     * Hard deletes multiple ProcurementMethodThresholds by IDs.
     *
     * @param idList - List of ProcurementMethodThreshold IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        procurementMethodThresholdOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all ProcurementMethodThresholds, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        procurementMethodThresholdOptionRepository.deleteAll();
    }
}