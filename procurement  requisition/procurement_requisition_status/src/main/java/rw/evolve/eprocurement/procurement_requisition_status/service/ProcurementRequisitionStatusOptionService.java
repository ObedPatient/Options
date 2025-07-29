package rw.evolve.eprocurement.procurement_requisition_status.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_requisition_status.exception.ProcurementRequisitionStatusOptionAlreadyExistException;
import rw.evolve.eprocurement.procurement_requisition_status.exception.ProcurementRequisitionStatusOptionNotFoundException;
import rw.evolve.eprocurement.procurement_requisition_status.model.ProcurementRequisitionStatusOptionModel;
import rw.evolve.eprocurement.procurement_requisition_status.repository.ProcurementRequisitionStatusOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing ProcurementRequisitionStatusOption model.
 * Provides functionality to create, read, update, and delete ProcurementRequisitionStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
public class ProcurementRequisitionStatusOptionService {

    @Autowired
    private ProcurementRequisitionStatusOptionRepository procurementRequisitionStatusOptionRepository;

    /**
     * Creates a single ProcurementRequisitionStatus option model with a generated ID.
     *
     * @param procurementRequisitionStatusOptionModel                  - the ProcurementRequisitionStatusOptionModel to be created
     * @return                                                         - the saved ProcurementRequisitionStatusOption model
     * @throws ProcurementRequisitionStatusOptionAlreadyExistException - if a ProcurementRequisitionStatusOption with the same name exists
     */
    @Transactional
    public ProcurementRequisitionStatusOptionModel save(ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel) {
        if (procurementRequisitionStatusOptionModel == null) {
            throw new NullPointerException("Procurement requisition status option cannot be null");
        }
        if (procurementRequisitionStatusOptionRepository.existsByName(procurementRequisitionStatusOptionModel.getName())) {
            throw new ProcurementRequisitionStatusOptionAlreadyExistException("Procurement requisition status option already exists: " + procurementRequisitionStatusOptionModel.getName());
        }
        return procurementRequisitionStatusOptionRepository.save(procurementRequisitionStatusOptionModel);
    }

    /**
     * Creates multiple ProcurementRequisitionStatus Option model, each with a unique generated ID.
     *
     * @param procurementRequisitionStatusOptionModelList - the list of ProcurementRequisitionStatus option model to be created
     * @return                                            - a list of saved ProcurementRequisitionStatus Option model
     * @throws NullPointerException                       - if the input list is null
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> saveMany(List<ProcurementRequisitionStatusOptionModel> procurementRequisitionStatusOptionModelList) {
        if (procurementRequisitionStatusOptionModelList == null || procurementRequisitionStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement requisition status option model list cannot be null or empty");
        }
        for (ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel : procurementRequisitionStatusOptionModelList) {
            if (procurementRequisitionStatusOptionRepository.existsByName(procurementRequisitionStatusOptionModel.getName())) {
                throw new ProcurementRequisitionStatusOptionAlreadyExistException("Procurement requisition status option already exists: " + procurementRequisitionStatusOptionModel.getName());
            }
        }
        return procurementRequisitionStatusOptionRepository.saveAll(procurementRequisitionStatusOptionModelList);
    }

    /**
     * Retrieves a single ProcurementRequisitionStatus option model by its ID.
     * Throws a ProcurementRequisitionStatusOptionNotFoundException if the ProcurementRequisitionStatus option is not found or has been deleted.
     *
     * @param id                                                   - the ID of the ProcurementRequisitionStatus option to retrieve
     * @return                                                     - the ProcurementRequisitionStatus option model if found and not deleted
     * @throws ProcurementRequisitionStatusOptionNotFoundException - if the ProcurementRequisitionStatus option is not found
     * @throws NullPointerException                                - if ProcurementRequisitionStatus option ID is null
     */
    @Transactional
    public ProcurementRequisitionStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement requisition status option ID cannot be null");
        }
        ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel = procurementRequisitionStatusOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option not found with ID: " + id));
        if (procurementRequisitionStatusOptionModel.getDeletedAt() != null) {
            throw new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option not found with ID: " + id);
        }
        return procurementRequisitionStatusOptionModel;
    }

    /**
     * Retrieves a list of ProcurementRequisitionStatusOption model list based on the provided ProcurementRequisitionStatusOption ID.
     *
     * @param procurementRequisitionStatusOptionIdList - A list of ProcurementRequisitionStatusOption ID to retrieve
     * @return                                         - A list of ProcurementRequisitionStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException                    - if a ProcurementRequisitionStatusOption ID list is null
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> readMany(List<String> procurementRequisitionStatusOptionIdList) {
        if (procurementRequisitionStatusOptionIdList == null || procurementRequisitionStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("Procurement requisition status option ID list cannot be null");
        }
        List<ProcurementRequisitionStatusOptionModel> modelList = new ArrayList<>();
        for (String id : procurementRequisitionStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Procurement requisition status option ID cannot be null");
            }
            ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel = procurementRequisitionStatusOptionRepository.findById(id)
                    .orElse(null);
            if (procurementRequisitionStatusOptionModel == null)
                continue;
            if (procurementRequisitionStatusOptionModel.getDeletedAt() == null) {
                modelList.add(procurementRequisitionStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all ProcurementRequisitionStatus options that are not marked as deleted
     *
     * @return         - a List of ProcurementRequisitionStatus option model where deletedAt is null
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> readAll() {
        return procurementRequisitionStatusOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ProcurementRequisitionStatus Option model, including those marked as deleted.
     *
     * @return            - A list of all ProcurementRequisitionStatusOptionModel objects
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> hardReadAll() {
        return procurementRequisitionStatusOptionRepository.findAll();
    }

    /**
     * Updates a single ProcurementRequisitionStatus Option model identified by the provided ID.
     *
     * @param model                                                - The ProcurementRequisitionStatusOptionModel containing updated data
     * @return                                                     - The updated ProcurementRequisitionStatusOptionModel
     * @throws ProcurementRequisitionStatusOptionNotFoundException - if procurement requisition status option is not found
     */
    @Transactional
    public ProcurementRequisitionStatusOptionModel updateOne(ProcurementRequisitionStatusOptionModel model) {
        ProcurementRequisitionStatusOptionModel existing = procurementRequisitionStatusOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option with ID: " + model.getId() + " is not found");
        }
        return procurementRequisitionStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple procurement requisition status option model in a transactional manner.
     *
     * @param modelList                                            - List of ProcurementRequisitionStatusOptionModel objects containing updated data
     * @return                                                     - List of updated ProcurementRequisitionStatusOptionModel objects
     * @throws ProcurementRequisitionStatusOptionNotFoundException - if procurement requisition status option is not found
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> updateMany(List<ProcurementRequisitionStatusOptionModel> modelList) {
        for (ProcurementRequisitionStatusOptionModel model : modelList) {
            ProcurementRequisitionStatusOptionModel existing = procurementRequisitionStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option with ID: " + model.getId() + " is not found");
            }
        }
        return procurementRequisitionStatusOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single ProcurementRequisitionStatus option model by ID, including deleted ones.
     *
     * @param model                                       - The ProcurementRequisitionStatusOptionModel containing updated data
     * @return                                            - The updated ProcurementRequisitionStatusOptionModel
     */
    @Transactional
    public ProcurementRequisitionStatusOptionModel hardUpdate(ProcurementRequisitionStatusOptionModel model) {
        return procurementRequisitionStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementRequisitionStatusOption modelList by their IDs, including deleted ones.
     *
     * @param procurementRequisitionStatusOptionModelList - List of ProcurementRequisitionStatusOptionModel objects containing updated data
     * @return                                            - List of updated ProcurementRequisitionStatusOptionModel objects
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> hardUpdateAll(List<ProcurementRequisitionStatusOptionModel> procurementRequisitionStatusOptionModelList) {
        return procurementRequisitionStatusOptionRepository.saveAll(procurementRequisitionStatusOptionModelList);
    }

    /**
     * Soft deletes a ProcurementRequisitionStatus option by ID.
     *
     * @return                                                     - The soft-deleted ProcurementRequisitionStatusOptionModel
     * @throws ProcurementRequisitionStatusOptionNotFoundException - if procurement requisition status option id is not found
     */
    @Transactional
    public ProcurementRequisitionStatusOptionModel softDelete(String id) {
        ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel = procurementRequisitionStatusOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option not found with id: " + id));
        procurementRequisitionStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return procurementRequisitionStatusOptionRepository.save(procurementRequisitionStatusOptionModel);
    }

    /**
     * Hard deletes a ProcurementRequisitionStatus option by ID.
     *
     * @param id                                                   - ID of the ProcurementRequisitionStatus option to hard delete
     * @throws NullPointerException                                - if the ProcurementRequisitionStatus option ID is null
     * @throws ProcurementRequisitionStatusOptionNotFoundException - if the ProcurementRequisitionStatus option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement requisition status option ID cannot be null");
        }
        if (!procurementRequisitionStatusOptionRepository.existsById(id)) {
            throw new ProcurementRequisitionStatusOptionNotFoundException("Procurement requisition status option not found with id: " + id);
        }
        procurementRequisitionStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple ProcurementRequisitionStatus options by their IDs.
     *
     * @param idList                                               - List of ProcurementRequisitionStatus option IDs to be soft deleted
     * @return                                                     - List of soft-deleted ProcurementRequisitionStatusOption objects
     * @throws ProcurementRequisitionStatusOptionNotFoundException - if any ProcurementRequisitionStatus option ID are not found
     */
    @Transactional
    public List<ProcurementRequisitionStatusOptionModel> softDeleteMany(List<String> idList) {
        List<ProcurementRequisitionStatusOptionModel> procurementRequisitionStatusOptionModelList = procurementRequisitionStatusOptionRepository.findAllById(idList);
        if (procurementRequisitionStatusOptionModelList.isEmpty()) {
            throw new ProcurementRequisitionStatusOptionNotFoundException("No procurement requisition status options found with provided IDList: " + idList);
        }
        for (ProcurementRequisitionStatusOptionModel model : procurementRequisitionStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return procurementRequisitionStatusOptionRepository.saveAll(procurementRequisitionStatusOptionModelList);
    }

    /**
     * Hard deletes multiple ProcurementRequisitionStatus options by IDs.
     *
     * @param idList     - List of ProcurementRequisitionStatus option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        procurementRequisitionStatusOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all ProcurementRequisitionStatus options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        procurementRequisitionStatusOptionRepository.deleteAll();
    }
}