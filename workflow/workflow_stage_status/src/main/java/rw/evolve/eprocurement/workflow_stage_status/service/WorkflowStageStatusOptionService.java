/**
 * Service for managing WorkflowStageStatusOption model.
 * Provides functionality to create, read, update, and delete WorkflowStageStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.workflow_stage_status.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.workflow_stage_status.exception.WorkflowStageStatusOptionAlreadyExistException;
import rw.evolve.eprocurement.workflow_stage_status.exception.WorkflowStageStatusOptionNotFoundException;
import rw.evolve.eprocurement.workflow_stage_status.model.WorkflowStageStatusOptionModel;
import rw.evolve.eprocurement.workflow_stage_status.repository.WorkflowStageStatusOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class WorkflowStageStatusOptionService {

    @Autowired
    private WorkflowStageStatusOptionRepository workflowStageStatusOptionRepository;

    /**
     * Creates a single WorkflowStageStatusOption model with a generated ID.
     *
     * @param workflowStageStatusOptionModel                  - the WorkflowStageStatusOptionModel to be created
     * @return                                                - the saved WorkflowStageStatusOption model
     * @throws WorkflowStageStatusOptionAlreadyExistException - if a WorkflowStageStatusOption with the same name exists
     */
    @Transactional
    public WorkflowStageStatusOptionModel save(WorkflowStageStatusOptionModel workflowStageStatusOptionModel) {
        if (workflowStageStatusOptionModel == null) {
            throw new NullPointerException("Workflow stage status option cannot be null");
        }
        if (workflowStageStatusOptionRepository.existsByName(workflowStageStatusOptionModel.getName())) {
            throw new WorkflowStageStatusOptionAlreadyExistException("Workflow stage status option already exists: " + workflowStageStatusOptionModel.getName());
        }
        return workflowStageStatusOptionRepository.save(workflowStageStatusOptionModel);
    }

    /**
     * Creates multiple WorkflowStageStatusOption model, each with a unique generated ID.
     *
     * @param workflowStageStatusOptionModelList - the list of WorkflowStageStatusOption model to be created
     * @return                                   - a list of saved WorkflowStageStatusOption model
     * @throws NullPointerException              - if the input list is null
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> saveMany(List<WorkflowStageStatusOptionModel> workflowStageStatusOptionModelList) {
        if (workflowStageStatusOptionModelList == null || workflowStageStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Workflow stage status option model list cannot be null or empty");
        }
        for (WorkflowStageStatusOptionModel workflowStageStatusOptionModel : workflowStageStatusOptionModelList) {
            if (workflowStageStatusOptionRepository.existsByName(workflowStageStatusOptionModel.getName())) {
                throw new WorkflowStageStatusOptionAlreadyExistException("Workflow stage status option already exists: " + workflowStageStatusOptionModel.getName());
            }
        }
        return workflowStageStatusOptionRepository.saveAll(workflowStageStatusOptionModelList);
    }

    /**
     * Retrieves a single WorkflowStageStatusOption model by its ID.
     * Throws a WorkflowStageStatusOptionNotFoundException if the WorkflowStageStatusOption is not found or has been deleted.
     *
     * @param id                                          - the ID of the WorkflowStageStatusOption to retrieve
     * @return                                            - the WorkflowStageStatusOption model if found and not deleted
     * @throws WorkflowStageStatusOptionNotFoundException - if the WorkflowStageStatusOption is not found
     * @throws NullPointerException                       - if WorkflowStageStatusOption ID is null
     */
    @Transactional
    public WorkflowStageStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Workflow stage status option ID cannot be null");
        }
        WorkflowStageStatusOptionModel workflowStageStatusOptionModel = workflowStageStatusOptionRepository.findById(id)
                .orElseThrow(() -> new WorkflowStageStatusOptionNotFoundException("Workflow stage status option not found with ID: " + id));
        if (workflowStageStatusOptionModel.getDeletedAt() != null) {
            throw new WorkflowStageStatusOptionNotFoundException("Workflow stage status option not found with ID: " + id);
        }
        return workflowStageStatusOptionModel;
    }

    /**
     * Retrieves a list of WorkflowStageStatusOption model list based on the provided WorkflowStageStatusOption ID.
     *
     * @param workflowStageStatusOptionIdList - A list of WorkflowStageStatusOption ID to retrieve
     * @return                                - A list of WorkflowStageStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException           - if a WorkflowStageStatusOption ID list is null
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> readMany(List<String> workflowStageStatusOptionIdList) {
        if (workflowStageStatusOptionIdList == null || workflowStageStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("Workflow stage status option ID list cannot be null");
        }
        List<WorkflowStageStatusOptionModel> modelList = new ArrayList<>();
        for (String id : workflowStageStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Workflow stage status option ID cannot be null");
            }
            WorkflowStageStatusOptionModel workflowStageStatusOptionModel = workflowStageStatusOptionRepository.findById(id)
                    .orElse(null);
            if (workflowStageStatusOptionModel == null)
                continue;
            if (workflowStageStatusOptionModel.getDeletedAt() == null) {
                modelList.add(workflowStageStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all WorkflowStageStatusOptions that are not marked as deleted
     *
     * @return         - a List of WorkflowStageStatusOption model where deletedAt is null
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> readAll() {
        return workflowStageStatusOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all WorkflowStageStatusOption model, including those marked as deleted.
     *
     * @return            - A list of all WorkflowStageStatusOptionModel objects
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> hardReadAll() {
        return workflowStageStatusOptionRepository.findAll();
    }

    /**
     * Updates a single WorkflowStageStatusOption model identified by the provided ID.
     *
     * @param model                                       - The WorkflowStageStatusOptionModel containing updated data
     * @return                                            - The updated WorkflowStageStatusOptionModel
     * @throws WorkflowStageStatusOptionNotFoundException - if workflow stage status option is not found
     */
    @Transactional
    public WorkflowStageStatusOptionModel updateOne(WorkflowStageStatusOptionModel model) {
        WorkflowStageStatusOptionModel existing = workflowStageStatusOptionRepository.findById(model.getId())
                .orElseThrow(() -> new WorkflowStageStatusOptionNotFoundException("Workflow stage status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new WorkflowStageStatusOptionNotFoundException("Workflow stage status option with ID: " + model.getId() + " is not found");
        }
        return workflowStageStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple workflow stage status option model in a transactional manner.
     *
     * @param modelList                                   - List of WorkflowStageStatusOptionModel objects containing updated data
     * @return                                            - List of updated WorkflowStageStatusOptionModel objects
     * @throws WorkflowStageStatusOptionNotFoundException - if workflow stage status option is not found
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> updateMany(List<WorkflowStageStatusOptionModel> modelList) {
        for (WorkflowStageStatusOptionModel model : modelList) {
            WorkflowStageStatusOptionModel existing = workflowStageStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new WorkflowStageStatusOptionNotFoundException("Workflow stage status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new WorkflowStageStatusOptionNotFoundException("Workflow stage status option with ID: " + model.getId() + " is not found");
            }
        }
        return workflowStageStatusOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single WorkflowStageStatusOption model by ID, including deleted ones.
     *
     * @param model                                  - The WorkflowStageStatusOptionModel containing updated data
     * @return                                       - The updated WorkflowStageStatusOptionModel
     */
    @Transactional
    public WorkflowStageStatusOptionModel hardUpdate(WorkflowStageStatusOptionModel model) {
        return workflowStageStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple WorkflowStageStatusOption modelList by their IDs, including deleted ones.
     *
     * @param workflowStageStatusOptionModelList   - List of WorkflowStageStatusOptionModel objects containing updated data
     * @return                                     - List of updated WorkflowStageStatusOptionModel objects
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> hardUpdateAll(List<WorkflowStageStatusOptionModel> workflowStageStatusOptionModelList) {
        return workflowStageStatusOptionRepository.saveAll(workflowStageStatusOptionModelList);
    }

    /**
     * Soft deletes a WorkflowStageStatusOption by ID.
     *
     * @return                                            - The soft-deleted WorkflowStageStatusOptionModel
     * @throws WorkflowStageStatusOptionNotFoundException - if workflow stage status option id is not found
     */
    @Transactional
    public WorkflowStageStatusOptionModel softDelete(String id) {
        WorkflowStageStatusOptionModel workflowStageStatusOptionModel = workflowStageStatusOptionRepository.findById(id)
                .orElseThrow(() -> new WorkflowStageStatusOptionNotFoundException("Workflow stage status option not found with id: " + id));
        workflowStageStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return workflowStageStatusOptionRepository.save(workflowStageStatusOptionModel);
    }

    /**
     * Hard deletes a WorkflowStageStatusOption by ID.
     *
     * @param id                                          - ID of the WorkflowStageStatusOption to hard delete
     * @throws NullPointerException                       - if the WorkflowStageStatusOption ID is null
     * @throws WorkflowStageStatusOptionNotFoundException - if the WorkflowStageStatusOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Workflow stage status option ID cannot be null");
        }
        if (!workflowStageStatusOptionRepository.existsById(id)) {
            throw new WorkflowStageStatusOptionNotFoundException("Workflow stage status option not found with id: " + id);
        }
        workflowStageStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple WorkflowStageStatusOptions by their IDs.
     *
     * @param idList                                      - List of WorkflowStageStatusOption IDs to be soft deleted
     * @return                                            - List of soft-deleted WorkflowStageStatusOption objects
     * @throws WorkflowStageStatusOptionNotFoundException - if any WorkflowStageStatusOption ID are not found
     */
    @Transactional
    public List<WorkflowStageStatusOptionModel> softDeleteMany(List<String> idList) {
        List<WorkflowStageStatusOptionModel> workflowStageStatusOptionModelList = workflowStageStatusOptionRepository.findAllById(idList);
        if (workflowStageStatusOptionModelList.isEmpty()) {
            throw new WorkflowStageStatusOptionNotFoundException("No workflow stage status options found with provided IDList: " + idList);
        }
        for (WorkflowStageStatusOptionModel model : workflowStageStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return workflowStageStatusOptionRepository.saveAll(workflowStageStatusOptionModelList);
    }

    /**
     * Hard deletes multiple WorkflowStageStatusOptions by IDs.
     *
     * @param idList     - List of WorkflowStageStatusOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        workflowStageStatusOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all WorkflowStageStatusOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        workflowStageStatusOptionRepository.deleteAll();
    }
}