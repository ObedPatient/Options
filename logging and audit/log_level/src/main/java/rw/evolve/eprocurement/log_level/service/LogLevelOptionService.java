package rw.evolve.eprocurement.log_level.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.log_level.exception.LogLevelOptionAlreadyExistException;
import rw.evolve.eprocurement.log_level.exception.LogLevelOptionNotFoundException;
import rw.evolve.eprocurement.log_level.model.LogLevelOptionModel;
import rw.evolve.eprocurement.log_level.repository.LogLevelOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing LogLevelOption model.
 * Provides functionality to create, read, update, and delete LogLevelOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
public class LogLevelOptionService {

    @Autowired
    private LogLevelOptionRepository logLevelOptionRepository;

    /**
     * Creates a single LogLevelOption model with a generated ID.
     *
     * @param logLevelOptionModel                  - the LogLevelOptionModel to be created
     * @return                                     - the saved LogLevelOption model
     * @throws LogLevelOptionAlreadyExistException - if a LogLevelOption with the same name exists
     */
    @Transactional
    public LogLevelOptionModel save(LogLevelOptionModel logLevelOptionModel) {
        if (logLevelOptionModel == null) {
            throw new NullPointerException("Log level option cannot be null");
        }
        if (logLevelOptionRepository.existsByName(logLevelOptionModel.getName())) {
            throw new LogLevelOptionAlreadyExistException("Log level option already exists: " + logLevelOptionModel.getName());
        }
        return logLevelOptionRepository.save(logLevelOptionModel);
    }

    /**
     * Creates multiple LogLevelOption models, each with a unique generated ID.
     *
     * @param logLevelOptionModelList - the list of LogLevelOption models to be created
     * @return                        - a list of saved LogLevelOption models
     * @throws NullPointerException   - if the input list is null
     */
    @Transactional
    public List<LogLevelOptionModel> saveMany(List<LogLevelOptionModel> logLevelOptionModelList) {
        if (logLevelOptionModelList == null || logLevelOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Log level option model list cannot be null or empty");
        }
        for (LogLevelOptionModel logLevelOptionModel : logLevelOptionModelList) {
            if (logLevelOptionRepository.existsByName(logLevelOptionModel.getName())) {
                throw new LogLevelOptionAlreadyExistException("Log level option already exists: " + logLevelOptionModel.getName());
            }
        }
        return logLevelOptionRepository.saveAll(logLevelOptionModelList);
    }

    /**
     * Retrieves a single LogLevelOption model by its ID.
     * Throws a LogLevelOptionNotFoundException if the LogLevelOption is not found or has been deleted.
     *
     * @param id                               - the ID of the LogLevelOption to retrieve
     * @return                                 - the LogLevelOption model if found and not deleted
     * @throws LogLevelOptionNotFoundException - if the LogLevelOption is not found
     * @throws NullPointerException            - if LogLevelOption ID is null
     */
    @Transactional
    public LogLevelOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Log level option ID cannot be null");
        }
        LogLevelOptionModel logLevelOptionModel = logLevelOptionRepository.findById(id)
                .orElseThrow(() -> new LogLevelOptionNotFoundException("Log level option not found with ID: " + id));
        if (logLevelOptionModel.getDeletedAt() != null) {
            throw new LogLevelOptionNotFoundException("Log level option not found with ID: " + id);
        }
        return logLevelOptionModel;
    }

    /**
     * Retrieves a list of LogLevelOption models based on the provided LogLevelOption IDs.
     *
     * @param logLevelOptionIdList    - A list of LogLevelOption IDs to retrieve
     * @return                        - A list of LogLevelOptionModel objects that are not marked as deleted
     * @throws NullPointerException   - if a LogLevelOption ID list is null
     */
    @Transactional
    public List<LogLevelOptionModel> readMany(List<String> logLevelOptionIdList) {
        if (logLevelOptionIdList == null || logLevelOptionIdList.isEmpty()) {
            throw new NullPointerException("Log level option ID list cannot be null");
        }
        List<LogLevelOptionModel> modelList = new ArrayList<>();
        for (String id : logLevelOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Log level option ID cannot be null");
            }
            LogLevelOptionModel logLevelOptionModel = logLevelOptionRepository.findById(id)
                    .orElse(null);
            if (logLevelOptionModel == null)
                continue;
            if (logLevelOptionModel.getDeletedAt() == null) {
                modelList.add(logLevelOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all LogLevelOption models that are not marked as deleted.
     *
     * @return - a List of LogLevelOption models where deletedAt is null
     */
    @Transactional
    public List<LogLevelOptionModel> readAll() {
        return logLevelOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all LogLevelOption models, including those marked as deleted.
     *
     * @return - A list of all LogLevelOptionModel objects
     */
    @Transactional
    public List<LogLevelOptionModel> hardReadAll() {
        return logLevelOptionRepository.findAll();
    }

    /**
     * Updates a single LogLevelOption model identified by the provided ID.
     *
     * @param model                            - The LogLevelOptionModel containing updated data
     * @return                                 - The updated LogLevelOptionModel
     * @throws LogLevelOptionNotFoundException - if LogLevelOption is not found or marked as deleted
     */
    @Transactional
    public LogLevelOptionModel updateOne(LogLevelOptionModel model) {
        LogLevelOptionModel existing = logLevelOptionRepository.findById(model.getId())
                .orElseThrow(() -> new LogLevelOptionNotFoundException("Log level option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new LogLevelOptionNotFoundException("Log level option with ID: " + model.getId() + " is not found");
        }
        return logLevelOptionRepository.save(model);
    }

    /**
     * Updates multiple LogLevelOption models.
     *
     * @param modelList                        - List of LogLevelOptionModel objects containing updated data
     * @return                                 - List of updated LogLevelOptionModel objects
     * @throws LogLevelOptionNotFoundException - if a LogLevelOption is not found or marked as deleted
     */
    @Transactional
    public List<LogLevelOptionModel> updateMany(List<LogLevelOptionModel> modelList) {
        for (LogLevelOptionModel model : modelList) {
            LogLevelOptionModel existing = logLevelOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new LogLevelOptionNotFoundException("Log level option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new LogLevelOptionNotFoundException("Log level option with ID: " + model.getId() + " is not found");
            }
        }
        return logLevelOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single LogLevelOption model by ID, including deleted ones.
     *
     * @param model                            - The LogLevelOptionModel containing updated data
     * @return                                 - The updated LogLevelOptionModel
     * @throws LogLevelOptionNotFoundException - if LogLevelOption is not found
     */
    @Transactional
    public LogLevelOptionModel hardUpdate(LogLevelOptionModel model) {
        return logLevelOptionRepository.save(model);
    }

    /**
     * Updates multiple LogLevelOption models by their ID, including deleted ones.
     *
     * @param logLevelOptionModelList - List of LogLevelOptionModel objects containing updated data
     * @return                        - List of updated LogLevelOptionModel objects
     */
    @Transactional
    public List<LogLevelOptionModel> hardUpdateAll(List<LogLevelOptionModel> logLevelOptionModelList) {
        return logLevelOptionRepository.saveAll(logLevelOptionModelList);
    }

    /**
     * Soft deletes a LogLevelOption by ID.
     *
     * @param id                               - The ID of the LogLevelOption to soft delete
     * @return                                 - The soft-deleted LogLevelOptionModel
     * @throws LogLevelOptionNotFoundException - if LogLevelOption ID is not found
     */
    @Transactional
    public LogLevelOptionModel softDelete(String id) {
        LogLevelOptionModel logLevelOptionModel = logLevelOptionRepository.findById(id)
                .orElseThrow(() -> new LogLevelOptionNotFoundException("Log level option not found with id: " + id));
        logLevelOptionModel.setDeletedAt(LocalDateTime.now());
        return logLevelOptionRepository.save(logLevelOptionModel);
    }

    /**
     * Hard deletes a LogLevelOption by ID.
     *
     * @param id                               - ID of the LogLevelOption to hard delete
     * @throws NullPointerException            - if the LogLevelOption ID is null
     * @throws LogLevelOptionNotFoundException - if the LogLevelOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Log level option ID cannot be null");
        }
        if (!logLevelOptionRepository.existsById(id)) {
            throw new LogLevelOptionNotFoundException("Log level option not found with id: " + id);
        }
        logLevelOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple LogLevelOptions by their IDs.
     *
     * @param idList                           - List of LogLevelOption IDs to be soft deleted
     * @return                                 - List of soft-deleted LogLevelOption objects
     * @throws LogLevelOptionNotFoundException - if any LogLevelOption IDs are not found
     */
    @Transactional
    public List<LogLevelOptionModel> softDeleteMany(List<String> idList) {
        List<LogLevelOptionModel> logLevelOptionModelList = logLevelOptionRepository.findAllById(idList);
        if (logLevelOptionModelList.isEmpty()) {
            throw new LogLevelOptionNotFoundException("No log level options found with provided IDList: " + idList);
        }
        for (LogLevelOptionModel model : logLevelOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return logLevelOptionRepository.saveAll(logLevelOptionModelList);
    }

    /**
     * Hard deletes multiple LogLevelOptions by IDs.
     *
     * @param idList - List of LogLevelOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        logLevelOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all LogLevelOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        logLevelOptionRepository.deleteAll();
    }
}