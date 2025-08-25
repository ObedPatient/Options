/**
 * Service for managing ExecutionPeriodOption entities.
 * Provides functionality to create, read, update, and delete ExecutionPeriodOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.execution_period_options.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.execution_period_options.exception.ExecutionPeriodAlreadyExistException;
import rw.evolve.eprocurement.execution_period_options.exception.ExecutionPeriodNotFoundException;
import rw.evolve.eprocurement.execution_period_options.model.ExecutionPeriodOptionModel;
import rw.evolve.eprocurement.execution_period_options.repository.ExecutionPeriodOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExecutionPeriodOptionService {

    private ExecutionPeriodOptionRepository executionPeriodOptionRepository;

    /**
     * Creates a single Execution Period Option model with a generated ID.
     *
     * @param executionPeriodOptionModel            - the ExecutionPeriodOptionModel to be created
     * @return                                      - the saved ExecutionPeriodOption model
     * @throws ExecutionPeriodAlreadyExistException - if an Execution Period Option with the same name exists
     */
    @Transactional
    public ExecutionPeriodOptionModel save(ExecutionPeriodOptionModel executionPeriodOptionModel) {
        if (executionPeriodOptionModel == null) {
            throw new NullPointerException("Execution period option cannot be null");
        }
        if (executionPeriodOptionRepository.existsByName(executionPeriodOptionModel.getName())) {
            throw new ExecutionPeriodAlreadyExistException("Execution period option already exists: " + executionPeriodOptionModel.getName());
        }
        return executionPeriodOptionRepository.save(executionPeriodOptionModel);
    }

    /**
     * Creates multiple Execution Period Option models, each with a unique generated ID.
     *
     * @param executionPeriodOptionModelList - the list of Execution Period Option models to be created
     * @return                               - a list of saved Execution Period Option models
     * @throws NullPointerException          - if the input list is null
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> saveMany(List<ExecutionPeriodOptionModel> executionPeriodOptionModelList) {
        if (executionPeriodOptionModelList == null || executionPeriodOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Execution period option model list cannot be null or empty");
        }
        for (ExecutionPeriodOptionModel executionPeriodOptionModel : executionPeriodOptionModelList) {
            if (executionPeriodOptionRepository.existsByName(executionPeriodOptionModel.getName())) {
                throw new ExecutionPeriodAlreadyExistException("Execution period option already exists: " + executionPeriodOptionModel.getName());
            }
        }
        return executionPeriodOptionRepository.saveAll(executionPeriodOptionModelList);
    }

    /**
     * Retrieves a single Execution Period Option model by its ID.
     * Throws an ExecutionPeriodNotFoundException if the Execution Period Option is not found or has been deleted.
     *
     * @param id                                - the ID of the Execution Period Option to retrieve
     * @return                                  - the Execution Period Option model if found and not deleted
     * @throws ExecutionPeriodNotFoundException - if the Execution Period Option is not found
     * @throws NullPointerException             - if Execution Period Option ID is null
     */
    @Transactional
    public ExecutionPeriodOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Execution Period Option ID cannot be null");
        }
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution period option not found with ID: " + id));
        if (executionPeriodOptionModel.getDeletedAt() != null) {
            throw new ExecutionPeriodNotFoundException("Execution period option not found with ID: " + id);
        }
        return executionPeriodOptionModel;
    }

    /**
     * Retrieves a list of Execution Period Option model based on the provided Execution Period Option IDs.
     *
     * @param executionPeriodOptionIdList - A list of Execution Period Option IDs to retrieve
     * @return                            - A list of ExecutionPeriodOptionModel objects that are not marked as deleted
     * @throws NullPointerException       - if an Execution Period Option ID list is null
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> readMany(List<String> executionPeriodOptionIdList) {
        if (executionPeriodOptionIdList == null || executionPeriodOptionIdList.isEmpty()) {
            throw new NullPointerException("Execution period option ID list cannot be null");
        }
        List<ExecutionPeriodOptionModel> modelList = new ArrayList<>();
        for (String id : executionPeriodOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Execution period option ID cannot be null");
            }
            ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                    .orElse(null);
            if (executionPeriodOptionModel == null)
                continue;
            if (executionPeriodOptionModel.getDeletedAt() == null) {
                modelList.add(executionPeriodOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Execution Period Options that are not marked as deleted
     *
     * @return - a List of Execution Period Option models where deletedAt is null
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> readAll() {
        return executionPeriodOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ExecutionPeriodOptionModels, including those marked as deleted.
     *
     * @return - A list of all ExecutionPeriodOptionModel objects
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> hardReadAll() {
        return executionPeriodOptionRepository.findAll();
    }

    /**
     * Updates a single Execution Period Option model identified by the provided ID.
     *
     * @param model                             - The ExecutionPeriodOptionModel containing updated data
     * @return                                  - The updated ExecutionPeriodOptionModel
     * @throws ExecutionPeriodNotFoundException - if Execution Period Option is not found
     */
    @Transactional
    public ExecutionPeriodOptionModel updateOne(ExecutionPeriodOptionModel model) {
        ExecutionPeriodOptionModel existing = executionPeriodOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution period option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ExecutionPeriodNotFoundException("Execution period option with ID: " + model.getId() + " is not found");
        }
        return executionPeriodOptionRepository.save(model);
    }

    /**
     * Updates multiple Execution Period Option model in a transactional manner.
     *
     * @param modelList                         - List of ExecutionPeriodOptionModel objects containing updated data
     * @return                                  - List of updated ExecutionPeriodOptionModel objects
     * @throws ExecutionPeriodNotFoundException - if Execution Period Option is not found
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> updateMany(List<ExecutionPeriodOptionModel> modelList) {
        for (ExecutionPeriodOptionModel model : modelList) {
            ExecutionPeriodOptionModel existing = executionPeriodOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution period option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ExecutionPeriodNotFoundException("Execution period option with ID: " + model.getId() + " is not found");
            }
        }
        return executionPeriodOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Execution Period Option model by ID, including deleted ones.
     *
     * @param model                          - The ExecutionPeriodOptionModel containing updated data
     * @return                               - The updated ExecutionPeriodOptionModel
     */
    @Transactional
    public ExecutionPeriodOptionModel hardUpdate(ExecutionPeriodOptionModel model) {
        return executionPeriodOptionRepository.save(model);
    }

    /**
     * Updates multiple Execution Period Option model by their ID, including deleted ones.
     *
     * @param executionPeriodOptionModelList - List of ExecutionPeriodOptionModel objects containing updated data
     * @return                               - List of updated ExecutionPeriodOptionModel objects
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> hardUpdateAll(List<ExecutionPeriodOptionModel> executionPeriodOptionModelList) {
        return executionPeriodOptionRepository.saveAll(executionPeriodOptionModelList);
    }

    /**
     * Soft deletes an Execution Period Option by ID.
     *
     * @param id                                - The ID of the Execution Period Option to soft delete
     * @return                                  - The soft-deleted ExecutionPeriodOptionModel
     * @throws ExecutionPeriodNotFoundException - if Execution Period Option ID is not found
     */
    @Transactional
    public ExecutionPeriodOptionModel softDelete(String id) {
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution period option not found with id: " + id));
        executionPeriodOptionModel.setDeletedAt(LocalDateTime.now());
        return executionPeriodOptionRepository.save(executionPeriodOptionModel);
    }

    /**
     * Hard deletes an Execution Period Option by ID.
     *
     * @param id                                - ID of the Execution Period Option to hard delete
     * @throws NullPointerException             - if the Execution Period Option ID is null
     * @throws ExecutionPeriodNotFoundException - if the Execution Period Option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Execution Period Option ID cannot be null");
        }
        if (!executionPeriodOptionRepository.existsById(id)) {
            throw new ExecutionPeriodNotFoundException("Execution period option not found with id: " + id);
        }
        executionPeriodOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Execution Period Options by their IDs.
     *
     * @param idList                            - List of Execution Period Option IDs to be soft deleted
     * @return                                  - List of soft-deleted Execution Period Option objects
     * @throws ExecutionPeriodNotFoundException - if any Execution Period Option IDs are not found
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> softDeleteMany(List<String> idList) {
        List<ExecutionPeriodOptionModel> executionPeriodOptionModelList = executionPeriodOptionRepository.findAllById(idList);
        if (executionPeriodOptionModelList.isEmpty()) {
            throw new ExecutionPeriodNotFoundException("No Execution period option found with provided IDList: " + idList);
        }
        for (ExecutionPeriodOptionModel model : executionPeriodOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return executionPeriodOptionRepository.saveAll(executionPeriodOptionModelList);
    }

    /**
     * Hard deletes multiple Execution Period Options by IDs.
     *
     * @param idList - List of Execution Period Option ID to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        executionPeriodOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Execution Period Options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        executionPeriodOptionRepository.deleteAll();
    }
}