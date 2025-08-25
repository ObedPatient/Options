/**
 * Service for managing WorkspaceTypeOption model.
 * Provides functionality to create, read, update, and delete WorkspaceTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.workspace_type_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.workspace_type_option.exception.WorkspaceTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.workspace_type_option.exception.WorkspaceTypeOptionNotFoundException;
import rw.evolve.eprocurement.workspace_type_option.model.WorkspaceTypeOptionModel;
import rw.evolve.eprocurement.workspace_type_option.repository.WorkspaceTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class WorkspaceTypeOptionService {

    private WorkspaceTypeOptionRepository workspaceTypeOptionRepository;

    /**
     * Creates a single WorkspaceTypeOption model with a generated ID.
     *
     * @param workspaceTypeOptionModel                  - the WorkspaceTypeOptionModel to be created
     * @return                                          - the saved WorkspaceTypeOption model
     * @throws WorkspaceTypeOptionAlreadyExistException - if a WorkspaceTypeOption with the same name exists
     */
    @Transactional
    public WorkspaceTypeOptionModel save(WorkspaceTypeOptionModel workspaceTypeOptionModel) {
        if (workspaceTypeOptionModel == null) {
            throw new NullPointerException("WorkspaceType option cannot be null");
        }
        if (workspaceTypeOptionRepository.existsByName(workspaceTypeOptionModel.getName())) {
            throw new WorkspaceTypeOptionAlreadyExistException("WorkspaceType option already exists: " + workspaceTypeOptionModel.getName());
        }
        return workspaceTypeOptionRepository.save(workspaceTypeOptionModel);
    }

    /**
     * Creates multiple WorkspaceTypeOption model, each with a unique generated ID.
     *
     * @param workspaceTypeOptionModelList - the list of WorkspaceTypeOption models to be created
     * @return                             - a list of saved WorkspaceTypeOption models
     * @throws NullPointerException        - if the input list is null
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> saveMany(List<WorkspaceTypeOptionModel> workspaceTypeOptionModelList) {
        if (workspaceTypeOptionModelList == null || workspaceTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("WorkspaceType option model list cannot be null or empty");
        }
        for (WorkspaceTypeOptionModel workspaceTypeOptionModel : workspaceTypeOptionModelList) {
            if (workspaceTypeOptionRepository.existsByName(workspaceTypeOptionModel.getName())) {
                throw new WorkspaceTypeOptionAlreadyExistException("WorkspaceType option already exists: " + workspaceTypeOptionModel.getName());
            }
        }
        return workspaceTypeOptionRepository.saveAll(workspaceTypeOptionModelList);
    }

    /**
     * Retrieves a single WorkspaceTypeOption model by its ID.
     * Throws a WorkspaceTypeOptionNotFoundException if the WorkspaceTypeOption is not found or has been deleted.
     *
     * @param id                                    - the ID of the WorkspaceTypeOption to retrieve
     * @return                                      - the WorkspaceTypeOption model if found and not deleted
     * @throws WorkspaceTypeOptionNotFoundException - if the WorkspaceTypeOption is not found
     * @throws NullPointerException                 - if WorkspaceTypeOption ID is null
     */
    @Transactional
    public WorkspaceTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("WorkspaceType option ID cannot be null");
        }
        WorkspaceTypeOptionModel workspaceTypeOptionModel = workspaceTypeOptionRepository.findById(id)
                .orElseThrow(() -> new WorkspaceTypeOptionNotFoundException("WorkspaceType option not found with ID: " + id));
        if (workspaceTypeOptionModel.getDeletedAt() != null) {
            throw new WorkspaceTypeOptionNotFoundException("WorkspaceType option not found with ID: " + id);
        }
        return workspaceTypeOptionModel;
    }

    /**
     * Retrieves a list of WorkspaceTypeOption model based on the provided WorkspaceTypeOption IDs.
     *
     * @param workspaceTypeOptionIdList - A list of WorkspaceTypeOption IDs to retrieve
     * @return                          - A list of WorkspaceTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException     - if a WorkspaceTypeOption ID list is null
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> readMany(List<String> workspaceTypeOptionIdList) {
        if (workspaceTypeOptionIdList == null || workspaceTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("WorkspaceType option ID list cannot be null");
        }
        List<WorkspaceTypeOptionModel> modelList = new ArrayList<>();
        for (String id : workspaceTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("WorkspaceType option ID cannot be null");
            }
            WorkspaceTypeOptionModel workspaceTypeOptionModel = workspaceTypeOptionRepository.findById(id)
                    .orElse(null);
            if (workspaceTypeOptionModel == null)
                continue;
            if (workspaceTypeOptionModel.getDeletedAt() == null) {
                modelList.add(workspaceTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all WorkspaceTypeOption model that are not marked as deleted.
     *
     * @return - a List of WorkspaceTypeOption model where deletedAt is null
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> readAll() {
        return workspaceTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all WorkspaceTypeOption model, including those marked as deleted.
     *
     * @return - A list of all WorkspaceTypeOptionModel objects
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> hardReadAll() {
        return workspaceTypeOptionRepository.findAll();
    }

    /**
     * Updates a single WorkspaceTypeOption model identified by the provided ID.
     *
     * @param model                                 - The WorkspaceTypeOptionModel containing updated data
     * @return                                      - The updated WorkspaceTypeOptionModel
     * @throws WorkspaceTypeOptionNotFoundException - if WorkspaceTypeOption is not found or marked as deleted
     */
    @Transactional
    public WorkspaceTypeOptionModel updateOne(WorkspaceTypeOptionModel model) {
        WorkspaceTypeOptionModel existing = workspaceTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new WorkspaceTypeOptionNotFoundException("WorkspaceType option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new WorkspaceTypeOptionNotFoundException("WorkspaceType option with ID: " + model.getId() + " is not found");
        }
        return workspaceTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple WorkspaceTypeOption model in a transactional manner.
     *
     * @param modelList                             - List of WorkspaceTypeOptionModel objects containing updated data
     * @return                                      - List of updated WorkspaceTypeOptionModel objects
     * @throws WorkspaceTypeOptionNotFoundException - if a WorkspaceTypeOption is not found or marked as deleted
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> updateMany(List<WorkspaceTypeOptionModel> modelList) {
        for (WorkspaceTypeOptionModel model : modelList) {
            WorkspaceTypeOptionModel existing = workspaceTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new WorkspaceTypeOptionNotFoundException("WorkspaceType option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new WorkspaceTypeOptionNotFoundException("WorkspaceType option with ID: " + model.getId() + " is not found");
            }
        }
        return workspaceTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single WorkspaceTypeOption model by ID, including deleted ones.
     *
     * @param model                                 - The WorkspaceTypeOptionModel containing updated data
     * @return                                      - The updated WorkspaceTypeOptionModel
     * @throws WorkspaceTypeOptionNotFoundException - if WorkspaceTypeOption is not found
     */
    @Transactional
    public WorkspaceTypeOptionModel hardUpdate(WorkspaceTypeOptionModel model) {
        return workspaceTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple WorkspaceTypeOption models by their IDs, including deleted ones.
     *
     * @param workspaceTypeOptionModelList - List of WorkspaceTypeOptionModel objects containing updated data
     * @return                             - List of updated WorkspaceTypeOptionModel objects
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> hardUpdateAll(List<WorkspaceTypeOptionModel> workspaceTypeOptionModelList) {
        return workspaceTypeOptionRepository.saveAll(workspaceTypeOptionModelList);
    }

    /**
     * Soft deletes a WorkspaceTypeOption by ID.
     *
     * @param id                                    - The ID of the WorkspaceTypeOption to soft delete
     * @return                                      - The soft-deleted WorkspaceTypeOptionModel
     * @throws WorkspaceTypeOptionNotFoundException - if WorkspaceTypeOption ID is not found
     */
    @Transactional
    public WorkspaceTypeOptionModel softDelete(String id) {
        WorkspaceTypeOptionModel workspaceTypeOptionModel = workspaceTypeOptionRepository.findById(id)
                .orElseThrow(() -> new WorkspaceTypeOptionNotFoundException("WorkspaceType option not found with id: " + id));
        workspaceTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return workspaceTypeOptionRepository.save(workspaceTypeOptionModel);
    }

    /**
     * Hard deletes a WorkspaceTypeOption by ID.
     *
     * @param id                                    - ID of the WorkspaceTypeOption to hard delete
     * @throws NullPointerException                 - if the WorkspaceTypeOption ID is null
     * @throws WorkspaceTypeOptionNotFoundException - if the WorkspaceTypeOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("WorkspaceType option ID cannot be null");
        }
        if (!workspaceTypeOptionRepository.existsById(id)) {
            throw new WorkspaceTypeOptionNotFoundException("WorkspaceType option not found with id: " + id);
        }
        workspaceTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple WorkspaceTypeOption by their ID.
     *
     * @param idList                                - List of WorkspaceTypeOption IDs to be soft deleted
     * @return                                      - List of soft-deleted WorkspaceTypeOption objects
     * @throws WorkspaceTypeOptionNotFoundException - if any WorkspaceTypeOption IDs are not found
     */
    @Transactional
    public List<WorkspaceTypeOptionModel> softDeleteMany(List<String> idList) {
        List<WorkspaceTypeOptionModel> workspaceTypeOptionModelList = workspaceTypeOptionRepository.findAllById(idList);
        if (workspaceTypeOptionModelList.isEmpty()) {
            throw new WorkspaceTypeOptionNotFoundException("No WorkspaceType options found with provided IDList: " + idList);
        }
        for (WorkspaceTypeOptionModel model : workspaceTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return workspaceTypeOptionRepository.saveAll(workspaceTypeOptionModelList);
    }

    /**
     * Hard deletes multiple WorkspaceTypeOption by ID.
     *
     * @param idList - List of WorkspaceTypeOption ID to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        workspaceTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all WorkspaceTypeOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        workspaceTypeOptionRepository.deleteAll();
    }
}