package rw.evolve.eprocurement.procurement_type_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_type_option.exception.ProcurementTypeExistException;
import rw.evolve.eprocurement.procurement_type_option.exception.ProcurementTypeOptionNotFoundException;
import rw.evolve.eprocurement.procurement_type_option.model.ProcurementTypeOptionModel;
import rw.evolve.eprocurement.procurement_type_option.repository.ProcurementTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing ProcurementTypeOption entities.
 * Provides functionality to create, read, update, and delete ProcurementTypeOption data, supporting both
 * soft and hard deletion operations.
 */
@Service
public class ProcurementTypeOptionService {

    @Autowired
    private ProcurementTypeOptionRepository procurementTypeOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Procurement type option model with a generated ID.
     *
     * @param procurementTypeOptionModel     - the ProcurementTypeOptionModel to be created
     * @return                               - the saved ProcurementTypeOption model
     * @throws ProcurementTypeExistException - if a ProcurementTypeOption with the same name exists
     */
    @Transactional
    public ProcurementTypeOptionModel save(ProcurementTypeOptionModel procurementTypeOptionModel) {
        if (procurementTypeOptionModel == null) {
            throw new NullPointerException("Procurement type option cannot be null");
        }
        if (procurementTypeOptionRepository.existsByName(procurementTypeOptionModel.getName())) {
            throw new ProcurementTypeExistException("Procurement type option already exists: " + procurementTypeOptionModel.getName());
        }
        return procurementTypeOptionRepository.save(procurementTypeOptionModel);
    }

    /**
     * Creates multiple Procurement type option models, each with a unique generated ID.
     *
     * @param procurementTypeOptionModelList - the list of Procurement type option models to be created
     * @return                               - a list of saved Procurement type option models
     * @throws IllegalArgumentException      - if the input list is null or empty
     */
    @Transactional
    public List<ProcurementTypeOptionModel> saveMany(List<ProcurementTypeOptionModel> procurementTypeOptionModelList) {
        if (procurementTypeOptionModelList == null || procurementTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Procurement type option model list cannot be null or empty");
        }
        for (ProcurementTypeOptionModel procurementTypeOptionModel : procurementTypeOptionModelList) {
            if (procurementTypeOptionRepository.existsByName(procurementTypeOptionModel.getName())) {
                throw new ProcurementTypeExistException("Procurement type option already exists: " + procurementTypeOptionModel.getName());
            }
        }
        return procurementTypeOptionRepository.saveAll(procurementTypeOptionModelList);
    }

    /**
     * Retrieves a single Procurement type option model by its ID.
     * Throws a ProcurementTypeOptionNotFoundException if the Procurement type option is not found or has been deleted.
     *
     * @param id                                      - the ID of the Procurement type option to retrieve
     * @return                                        - the Procurement type option model if found and not deleted
     * @throws ProcurementTypeOptionNotFoundException - if the Procurement type option is not found
     * @throws NullPointerException                   - if Procurement type option ID is null
     */
    @Transactional
    public ProcurementTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement type option ID cannot be null");
        }
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementTypeOptionNotFoundException("Procurement type option not found with ID: " + id));
        if (procurementTypeOptionModel.getDeletedAt() != null) {
            throw new ProcurementTypeOptionNotFoundException("Procurement type option not found with ID: " + id);
        }
        return procurementTypeOptionModel;
    }

    /**
     * Retrieves a list of ProcurementTypeOption models based on the provided ProcurementTypeOption IDs.
     *
     * @param procurementTypeOptionIdList - A list of ProcurementTypeOption IDs to retrieve
     * @return                            - A list of ProcurementTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException       - if a ProcurementTypeOption ID list is null
     */
    @Transactional
    public List<ProcurementTypeOptionModel> readMany(List<String> procurementTypeOptionIdList) {
        if (procurementTypeOptionIdList == null || procurementTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Procurement type option ID list cannot be null");
        }
        List<ProcurementTypeOptionModel> modelList = new ArrayList<>();
        for (String id : procurementTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Procurement type option ID cannot be null");
            }
            ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                    .orElse(null);
            if (procurementTypeOptionModel == null) {
                continue;
            }
            if (procurementTypeOptionModel.getDeletedAt() == null) {
                modelList.add(procurementTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Procurement type options that are not marked as deleted.
     *
     * @return - a List of Procurement type option models where deletedAt is null
     */
    @Transactional
    public List<ProcurementTypeOptionModel> readAll() {
        return procurementTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ProcurementTypeOption models, including those marked as deleted.
     *
     * @return - A list of all ProcurementTypeOptionModel objects
     */
    @Transactional
    public List<ProcurementTypeOptionModel> hardReadAll() {
        return procurementTypeOptionRepository.findAll();
    }

    /**
     * Updates a single ProcurementTypeOption model identified by the provided ID.
     *
     * @param model                          - The ProcurementTypeOptionModel containing updated data
     * @return                               - The updated ProcurementTypeOptionModel
     */
    @Transactional
    public ProcurementTypeOptionModel updateOne(ProcurementTypeOptionModel model) {
        ProcurementTypeOptionModel existing = procurementTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ProcurementTypeOptionNotFoundException("Procurement type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ProcurementTypeOptionNotFoundException("Procurement type option with ID: " + model.getId() + " is not found");
        }
        return procurementTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementTypeOption models in a transactional manner.
     *
     * @param modelList                      - List of ProcurementTypeOptionModel objects containing updated data
     * @return                               - List of updated ProcurementTypeOptionModel objects
     */
    @Transactional
    public List<ProcurementTypeOptionModel> updateMany(List<ProcurementTypeOptionModel> modelList) {
        for (ProcurementTypeOptionModel model : modelList) {
            ProcurementTypeOptionModel existing = procurementTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ProcurementTypeOptionNotFoundException("Procurement type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ProcurementTypeOptionNotFoundException("Procurement type option with ID: " + model.getId() + " is not found");
            }
        }
        return procurementTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Procurement type option model by ID, including deleted ones.
     *
     * @param model                          - The ProcurementTypeOptionModel containing updated data
     * @return                               - The updated ProcurementTypeOptionModel
     */
    @Transactional
    public ProcurementTypeOptionModel hardUpdate(ProcurementTypeOptionModel model) {
        return procurementTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple ProcurementTypeOption models by their IDs, including deleted ones.
     *
     * @param procurementTypeOptionModelList - List of ProcurementTypeOptionModel objects containing updated data
     * @return                               - List of updated ProcurementTypeOptionModel objects
     */
    @Transactional
    public List<ProcurementTypeOptionModel> hardUpdateAll(List<ProcurementTypeOptionModel> procurementTypeOptionModelList) {
        return procurementTypeOptionRepository.saveAll(procurementTypeOptionModelList);
    }

    /**
     * Soft deletes a Procurement type option by ID.
     *
     * @param id                                      - ID of the Procurement type option to soft delete
     * @return                                        - The soft-deleted ProcurementTypeOptionModel
     * @throws ProcurementTypeOptionNotFoundException - if procurement type option ID is not found
     */
    @Transactional
    public ProcurementTypeOptionModel softDelete(String id) {
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                .orElseThrow(() -> new ProcurementTypeOptionNotFoundException("Procurement type option not found with id: " + id));
        procurementTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return procurementTypeOptionRepository.save(procurementTypeOptionModel);
    }

    /**
     * Hard deletes a Procurement type option by ID.
     *
     * @param id                                      - ID of the Procurement type option to hard delete
     * @throws NullPointerException                   - if the Procurement type option ID is null
     * @throws ProcurementTypeOptionNotFoundException - if the Procurement type option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Procurement type option ID cannot be null");
        }
        if (!procurementTypeOptionRepository.existsById(id)) {
            throw new ProcurementTypeOptionNotFoundException("Procurement type option not found with id: " + id);
        }
        procurementTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Procurement type options by their IDs.
     *
     * @param idList                                  - List of Procurement type option IDs to be soft deleted
     * @return                                        - List of soft-deleted ProcurementTypeOption objects
     * @throws ProcurementTypeOptionNotFoundException - if any Procurement type option IDs are not found
     */
    @Transactional
    public List<ProcurementTypeOptionModel> softDeleteMany(List<String> idList) {
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionRepository.findAllById(idList);
        if (procurementTypeOptionModels.isEmpty()) {
            throw new ProcurementTypeOptionNotFoundException("No procurement type options found with provided IDList: " + idList);
        }
        for (ProcurementTypeOptionModel model : procurementTypeOptionModels) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return procurementTypeOptionRepository.saveAll(procurementTypeOptionModels);
    }

    /**
     * Hard deletes multiple Procurement type options by IDs.
     *
     * @param idList - List of Procurement type option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        procurementTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Procurement type options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        procurementTypeOptionRepository.deleteAll();
    }
}