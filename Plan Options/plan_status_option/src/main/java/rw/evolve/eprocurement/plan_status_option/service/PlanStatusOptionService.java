/**
 * Service for managing PlanStatusOptionModel entities.
 * Provides functionality to create, read, update, and delete PlanStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.plan_status_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.plan_status_option.exception.PlanStatusAlreadExistException;
import rw.evolve.eprocurement.plan_status_option.exception.PlanStatusNotFoundException;
import rw.evolve.eprocurement.plan_status_option.model.PlanStatusOptionModel;
import rw.evolve.eprocurement.plan_status_option.repository.PlanStatusOptionRepositoy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanStatusOptionService {

    @Autowired
    private PlanStatusOptionRepositoy planStatusOptionRepositoy;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single PlanStatusOption entity.
     *
     * @param planStatusOptionModel           - the PlanStatusOptionModel to be created
     * @return                                - the saved PlanStatusOption model
     * @throws PlanStatusAlreadExistException - if a PlanStatusOption with the same name exists
     */
    @Transactional
    public PlanStatusOptionModel save(PlanStatusOptionModel planStatusOptionModel) {
        if (planStatusOptionModel == null) {
            throw new NullPointerException("Plan Status Option model cannot be null");
        }
        if (planStatusOptionRepositoy.existsByName(planStatusOptionModel.getName())) {
            throw new PlanStatusAlreadExistException("Plan Status Option already exists: " + planStatusOptionModel.getName());
        }
        return planStatusOptionRepositoy.save(planStatusOptionModel);
    }

    /**
     * Creates multiple PlanStatusOption entities, each with a unique generated ID.
     *
     * @param planStatusOptionModelList - the list of PlanStatusOption models to be created
     * @return                          - a list of saved PlanStatusOption models
     * @throws NullPointerException     - if the input list is null or empty
     */
    @Transactional
    public List<PlanStatusOptionModel> saveMany(List<PlanStatusOptionModel> planStatusOptionModelList) {
        if (planStatusOptionModelList == null || planStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Plan Status Option model list cannot be null or empty");
        }
        for (PlanStatusOptionModel planStatusOptionModel : planStatusOptionModelList) {
            if (planStatusOptionRepositoy.existsByName(planStatusOptionModel.getName())) {
                throw new PlanStatusAlreadExistException("Plan Status Option already exists: " + planStatusOptionModel.getName());
            }
        }
        return planStatusOptionRepositoy.saveAll(planStatusOptionModelList);
    }

    /**
     * Retrieves a single PlanStatusOption entity by its ID.
     * Throws a PlanStatusNotFoundException if the PlanStatusOption is not found or has been deleted.
     *
     * @param id                           - the ID of the PlanStatusOption to retrieve
     * @return                             - the PlanStatusOption model if found and not deleted
     * @throws PlanStatusNotFoundException - if the PlanStatusOption is not found
     * @throws NullPointerException        - if PlanStatusOption ID is null
     */
    @Transactional
    public PlanStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Plan status option ID cannot be null");
        }
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                .orElseThrow(() -> new PlanStatusNotFoundException("Plan Status Option not found with ID: " + id));
        if (planStatusOptionModel.getDeletedAt() != null) {
            throw new PlanStatusNotFoundException("Plan status option not found with ID: " + id);
        }
        return planStatusOptionModel;
    }

    /**
     * Retrieves a list of PlanStatusOption objects based on the provided PlanStatusOption ID.
     *
     * @param planStatusOptionIdList - A list of PlanStatusOption IDs to retrieve
     * @return                       - A list of PlanStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException  - if a PlanStatusOption ID list is null
     */
    @Transactional
    public List<PlanStatusOptionModel> readMany(List<String> planStatusOptionIdList) {
        if (planStatusOptionIdList == null || planStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("Plan status option ID list cannot be null");
        }
        List<PlanStatusOptionModel> modelList = new ArrayList<>();
        for (String id : planStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Plan status option ID cannot be null");
            }
            PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                    .orElse(null);
            if (planStatusOptionModel == null) {
                continue;
            }
            if (planStatusOptionModel.getDeletedAt() == null) {
                modelList.add(planStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all PlanStatusOption that are not marked as deleted.
     *
     * @return - a List of PlanStatusOption model where deletedAt is null
     */
    @Transactional
    public List<PlanStatusOptionModel> readAll() {
        return planStatusOptionRepositoy.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all PlanStatusOptionModels, including those marked as deleted.
     *
     * @return - A list of all PlanStatusOptionModel objects
     */
    @Transactional
    public List<PlanStatusOptionModel> hardReadAll() {
        return planStatusOptionRepositoy.findAll();
    }

    /**
     * Updates a single PlanStatusOption model identified by the provided ID.
     *
     * @param model                        - the PlanStatusOptionModel containing updated data
     * @return                             - the updated PlanStatusOptionModel
     * @throws PlanStatusNotFoundException - if the PlanStatusOption is not found or is marked as deleted
     */
    @Transactional
    public PlanStatusOptionModel updateOne(PlanStatusOptionModel model) {
        PlanStatusOptionModel existing = planStatusOptionRepositoy.findById(model.getId())
                .orElseThrow(() -> new PlanStatusNotFoundException("Plan status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new PlanStatusNotFoundException("Plan status option with ID: " + model.getId() + " is not found");
        }
        return planStatusOptionRepositoy.save(model);
    }

    /**
     * Updates multiple PlanStatusOption models in a transactional manner.
     *
     * @param modelList                     - List of PlanStatusOptionModel objects containing updated data
     * @return                              - List of updated PlanStatusOptionModel objects
     * @throws PlanStatusNotFoundException  - if a PlanStatusOption is not found or marked as deleted
     */
    @Transactional
    public List<PlanStatusOptionModel> updateMany(List<PlanStatusOptionModel> modelList) {
        for (PlanStatusOptionModel model : modelList) {
            PlanStatusOptionModel existing = planStatusOptionRepositoy.findById(model.getId())
                    .orElseThrow(() -> new PlanStatusNotFoundException("Plan status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new PlanStatusNotFoundException("Plan status option with ID: " + model.getId() + " is not found");
            }
        }
        return planStatusOptionRepositoy.saveAll(modelList);
    }

    /**
     * Updates a single PlanStatusOption model by ID, including deleted ones.
     *
     * @param model                        - the PlanStatusOptionModel containing updated data
     * @return                             - the updated PlanStatusOptionModel
     */
    @Transactional
    public PlanStatusOptionModel hardUpdate(PlanStatusOptionModel model) {
        return planStatusOptionRepositoy.save(model);
    }

    /**
     * Updates multiple PlanStatusOption models by their IDs, including deleted ones.
     *
     * @param planStatusOptionModelList    - List of PlanStatusOptionModel objects containing updated data
     * @return                             - List of updated PlanStatusOptionModel objects
     */
    @Transactional
    public List<PlanStatusOptionModel> hardUpdateAll(List<PlanStatusOptionModel> planStatusOptionModelList) {
            return planStatusOptionRepositoy.saveAll(planStatusOptionModelList);
    }

    /**
     * Soft deletes a PlanStatusOption by ID.
     *
     * @param id                           - the ID of the PlanStatusOption to soft delete
     * @return                             - the soft-deleted PlanStatusOptionModel
     * @throws PlanStatusNotFoundException - if the PlanStatusOption is not found
     */
    @Transactional
    public PlanStatusOptionModel softDelete(String id) {
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                .orElseThrow(() -> new PlanStatusNotFoundException("Plan Status Option not found with id: " + id));
        planStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return planStatusOptionRepositoy.save(planStatusOptionModel);
    }

    /**
     * Hard deletes a PlanStatusOption by ID.
     *
     * @param id                           - ID of the PlanStatusOption to hard delete
     * @throws NullPointerException        - if the PlanStatusOption ID is null
     * @throws PlanStatusNotFoundException - if the PlanStatusOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Plan Status Option ID cannot be null");
        }
        if (!planStatusOptionRepositoy.existsById(id)) {
            throw new PlanStatusNotFoundException("Plan Status Option not found with id: " + id);
        }
        planStatusOptionRepositoy.deleteById(id);
    }

    /**
     * Soft deletes multiple PlanStatusOption by their IDs.
     *
     * @param idList                        - List of PlanStatusOption IDs to be soft deleted
     * @return                              - List of soft-deleted PlanStatusOption objects
     * @throws PlanStatusNotFoundException  - if any PlanStatusOption IDs are not found
     */
    @Transactional
    public List<PlanStatusOptionModel> softDeleteMany(List<String> idList) {
        List<PlanStatusOptionModel> planStatusOptionModelList = planStatusOptionRepositoy.findAllById(idList);
        if (planStatusOptionModelList.isEmpty()) {
            throw new PlanStatusNotFoundException("No Plan Status Options found with provided IDList: " + idList);
        }
        for (PlanStatusOptionModel model : planStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return planStatusOptionRepositoy.saveAll(planStatusOptionModelList);
    }

    /**
     * Hard deletes multiple PlanStatusOption by IDs.
     *
     * @param idList  - List of PlanStatusOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        planStatusOptionRepositoy.deleteAllById(idList);
    }

    /**
     * Hard deletes all PlanStatusOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        planStatusOptionRepositoy.deleteAll();
    }
}