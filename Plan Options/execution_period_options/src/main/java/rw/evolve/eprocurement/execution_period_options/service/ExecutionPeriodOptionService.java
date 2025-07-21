/**
 * Service for managing ExecutionPeriodOption entities.
 * Provides functionality to create, read, update, and delete ExecutionPeriodOption data, supporting both
 * soft and hard deletion operations.
 */
package rw.evolve.eprocurement.execution_period_options.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.execution_period_options.exception.ExecutionPeriodAlreadyExistException;
import rw.evolve.eprocurement.execution_period_options.exception.ExecutionPeriodNotFoundException;
import rw.evolve.eprocurement.execution_period_options.model.ExecutionPeriodOptionModel;
import rw.evolve.eprocurement.execution_period_options.repository.ExecutionPeriodOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutionPeriodOptionService {

    @Autowired
    private ExecutionPeriodOptionRepository executionPeriodOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Execution Period Option entity.
     *
     * @param executionPeriodOptionModel the ExecutionPeriodOptionModel to be created
     * @return the saved ExecutionPeriodOption model
     */
    @Transactional
    public ExecutionPeriodOptionModel createExecutionPeriodOption(ExecutionPeriodOptionModel executionPeriodOptionModel){
        if (executionPeriodOptionRepository.existsByName(executionPeriodOptionModel.getName())){
            throw new ExecutionPeriodAlreadyExistException("Execution Period Option Already exists: " + executionPeriodOptionModel.getName());
        }
        return executionPeriodOptionRepository.save(executionPeriodOptionModel);
    }

    /**
     * Creates multiple Execution Period Option entities, each with a unique ID.
     * Iterates through the provided list of Execution Period Option models
     *
     * @param executionPeriodOptionModels the list of Execution Period Option models to be created
     * @return a list of saved Execution Period Option models.
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> createExecutionPeriodOptions(List<ExecutionPeriodOptionModel> executionPeriodOptionModels){
        if (executionPeriodOptionModels == null){
            throw new IllegalArgumentException("Execution Period Option model cannot be null");
        }
        List<ExecutionPeriodOptionModel> savedExecutionPeriodModels = new ArrayList<>();
        for (ExecutionPeriodOptionModel executionPeriodOptionModel: executionPeriodOptionModels){
            ExecutionPeriodOptionModel savedExecutionPeriodModel = executionPeriodOptionRepository.save(executionPeriodOptionModel);
            savedExecutionPeriodModels.add(savedExecutionPeriodModel);
        }
        return savedExecutionPeriodModels;
    }

    /**
     * Retrieves a single Execution Period Option entity by its ID.
     * Throws an ExecutionPeriodNotFoundException if the Execution Period Option is not found or has been deleted.
     *
     * @param id the ID of the Execution Period Option to retrieve
     * @return the Execution Period Option model if found and not deleted
     * @throws ExecutionPeriodNotFoundException if the Execution Period Option is not found.
     */
    @Transactional
    public ExecutionPeriodOptionModel readOne(Long id){
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id));
        if (executionPeriodOptionModel.getDeletedAt() != null){
            throw new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id);
        }
        return executionPeriodOptionModel;
    }

    /**
     * Retrieves a list of Execution Period Option objects based on the provided Execution Period Option IDs.
     *
     * @param executionPeriodOptionIds A list of Execution Period Option IDs to retrieve
     * @return A list of ExecutionPeriodOptionModel objects that are not marked as deleted
     * @throws ExecutionPeriodNotFoundException if an Execution Period Option with the given ID is not found
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> readMany(List<Long> executionPeriodOptionIds){
        if (executionPeriodOptionIds == null){
            throw new IllegalArgumentException("Execution Period Option id cannot be null or empty");
        }
        List<ExecutionPeriodOptionModel> models = new ArrayList<>();
        for (Long id: executionPeriodOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Execution Period Option id cannot be null or empty");
            }
            ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                    .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id));
            if (executionPeriodOptionModel.getDeletedAt() == null){
                models.add(executionPeriodOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all Execution Period Options that are not marked as deleted
     * @return a List of Execution Period Option objects where deleted is null
     * @throws ExecutionPeriodNotFoundException if no Execution Period Option found
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> readAll(){
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = executionPeriodOptionRepository.findByDeletedAtIsNull();
        if (executionPeriodOptionModels.isEmpty()){
            throw new ExecutionPeriodNotFoundException("No Execution Period Option found");
        }
        return executionPeriodOptionModels;
    }

    /**
     * Retrieves all ExecutionPeriodOptionModels, including those marked as deleted.
     *
     * @return A list of all ExecutionPeriodOptionModel objects
     * @throws ExecutionPeriodNotFoundException if no Execution Period Option found
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> hardReadAll(){
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = executionPeriodOptionRepository.findAll();
        if (executionPeriodOptionModels.isEmpty()){
            throw new ExecutionPeriodNotFoundException("No Execution Period Option found");
        }
        return executionPeriodOptionModels;
    }

    /**
     * Updates a single ExecutionPeriodOptionModel identified by the provided ID.
     * @param id The ID of the Execution Period Option to update
     * @param model The ExecutionPeriodOptionModel containing updated data
     * @return The updated ExecutionPeriodOptionModel
     * @throws ExecutionPeriodNotFoundException if the ExecutionPeriodOptionModel is not found or is marked as deleted
     */
    @Transactional
    public ExecutionPeriodOptionModel updateOne(Long id, ExecutionPeriodOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Execution Period Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Execution Period Option cannot be null");
        }
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id));
        if (executionPeriodOptionModel.getDeletedAt() != null){
            throw new ExecutionPeriodNotFoundException("Execution Period Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, executionPeriodOptionModel);
        executionPeriodOptionModel.setUpdatedAt(LocalDateTime.now());
        return executionPeriodOptionRepository.save(executionPeriodOptionModel);
    }

    /**
     * Updates multiple ExecutionPeriodOption models in a transactional manner.
     *
     * @param models List of ExecutionPeriodOptionModel objects containing updated data
     * @return List of updated ExecutionPeriodOptionModel objects
     * @throws IllegalArgumentException if any ExecutionPeriodOptionModel is null
     * @throws ExecutionPeriodNotFoundException if an ExecutionPeriodOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> updateMany(List<ExecutionPeriodOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Execution Period Option model List cannot be null or empty");
        }
        List<ExecutionPeriodOptionModel> updatedModel = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Execution Period Option id cannot be null");
            }
            ExecutionPeriodOptionModel existingModel = executionPeriodOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() != null){
                throw new ExecutionPeriodNotFoundException("Execution Period Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(executionPeriodOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Execution Period Option model by ID.
     * @param id The ID of the Execution Period Option to update
     * @param model The ExecutionPeriodOptionModel containing updated data
     * @return The updated ExecutionPeriodOptionModel
     * @throws IllegalArgumentException if the Execution Period Option ID is null
     * @throws ExecutionPeriodNotFoundException if the Execution Period Option is not found
     */
    @Transactional
    public ExecutionPeriodOptionModel hardUpdateOne(Long id, ExecutionPeriodOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Execution Period Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Execution Period Option model cannot be null");
        }
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id));

        modelMapper.map(model, executionPeriodOptionModel);
        executionPeriodOptionModel.setUpdatedAt(LocalDateTime.now());
        return executionPeriodOptionRepository.save(executionPeriodOptionModel);
    }

    /**
     * Updates multiple ExecutionPeriodOptionModel models by their IDs
     * @param executionPeriodOptionModels List of ExecutionPeriodOptionModel objects containing updated data
     * @return List of updated ExecutionPeriodOptionModel objects
     * @throws IllegalArgumentException if any Execution Period Option ID is null
     * @throws ExecutionPeriodNotFoundException if any Execution Period Option is not found
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> hardUpdateAll(List<ExecutionPeriodOptionModel> executionPeriodOptionModels){
        if (executionPeriodOptionModels == null || executionPeriodOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Execution Period Option model list cannot be null or empty");
        }
        List<ExecutionPeriodOptionModel> updatedModels = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: executionPeriodOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Execution Period Option id cannot be null on Hard update all");
            }
            ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + model.getId()));

            modelMapper.map(model, executionPeriodOptionModel);
            executionPeriodOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(executionPeriodOptionRepository.save(executionPeriodOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes an Execution Period Option by ID in a transactional manner.
     *
     * @param id The ID of the Execution Period Option to soft delete
     * @return The soft-deleted ExecutionPeriodOptionModel
     * @throws IllegalArgumentException if the Execution Period Option ID is null
     * @throws ExecutionPeriodNotFoundException if the Execution Period Option is not found
     * @throws IllegalStateException if the Execution Period Option is already deleted
     */
    @Transactional
    public ExecutionPeriodOptionModel softDeleteExecutionPeriodOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Execution Period Option ID cannot be null or empty");
        }
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionRepository.findById(id)
                .orElseThrow(() -> new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id));
        if (executionPeriodOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Execution Period Option with id:" + id + " is already deleted");
        }
        executionPeriodOptionModel.setDeletedAt(LocalDateTime.now());
        return executionPeriodOptionRepository.save(executionPeriodOptionModel);
    }

    /**
     * Hard deletes an Execution Period Option by ID
     * @param id ID of the Execution Period Option to hard delete
     */
    @Transactional
    public void hardDeleteExecutionPeriodOption(Long id){
        if (!executionPeriodOptionRepository.existsById(id)){
            throw new ExecutionPeriodNotFoundException("Execution Period Option not found with id:" + id);
        }
        executionPeriodOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Execution Period Options by their IDs.
     *
     * @param ids List of Execution Period Option IDs to be soft deleted
     * @return List of soft deleted Execution Period Option objects
     * @throws ExecutionPeriodNotFoundException if any Execution Period Option IDs are not found
     * @throws IllegalStateException if any Execution Period Option is already deleted
     */
    @Transactional
    public List<ExecutionPeriodOptionModel> softDeleteExecutionPeriodOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Execution Period Option IDs list cannot be null or empty");
        }
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = executionPeriodOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: executionPeriodOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new ExecutionPeriodNotFoundException("Execution Period Option not found with ids:" + missingIds);
        }
        for (ExecutionPeriodOptionModel model: executionPeriodOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalStateException("Execution Period Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        executionPeriodOptionRepository.saveAll(executionPeriodOptionModels);
        return executionPeriodOptionModels;
    }

    /**
     * Hard deletes multiple Execution Period Options by IDs
     * @param ids List of Execution Period Option IDs to hard delete
     */
    @Transactional
    public void hardDeleteExecutionPeriodOptions(List<Long> ids){
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = executionPeriodOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: executionPeriodOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new ExecutionPeriodNotFoundException("Execution Period Option not found with ids:" + missingIds);
        }
        executionPeriodOptionRepository.deleteAllById(ids);
    }
}