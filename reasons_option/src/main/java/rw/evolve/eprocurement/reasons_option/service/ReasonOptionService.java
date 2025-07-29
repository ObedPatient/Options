/**
 * Service for managing ReasonOption model.
 * Provides functionality to create, read, update, and delete ReasonOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.reasons_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.reasons_option.exception.ReasonAlreadyExistException;
import rw.evolve.eprocurement.reasons_option.exception.ReasonNotFoundException;
import rw.evolve.eprocurement.reasons_option.model.ReasonOptionModel;
import rw.evolve.eprocurement.reasons_option.repository.ReasonOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReasonOptionService {

    @Autowired
    private ReasonOptionRepository reasonOptionRepository;

    /**
     * Creates a single Reason option model with a generated ID.
     *
     * @param reasonOptionModel            - the ReasonOptionModel to be created
     * @return                             - the saved ReasonOption model
     * @throws ReasonAlreadyExistException - if a ReasonOption with the same name exists
     */
    @Transactional
    public ReasonOptionModel save(ReasonOptionModel reasonOptionModel) {
        if (reasonOptionModel == null) {
            throw new NullPointerException("Reason option cannot be null");
        }
        if (reasonOptionRepository.existsByName(reasonOptionModel.getName())) {
            throw new ReasonAlreadyExistException("Reason option already exists: " + reasonOptionModel.getName());
        }
        return reasonOptionRepository.save(reasonOptionModel);
    }

    /**
     * Creates multiple Reason Option model, each with a unique generated ID.
     *
     * @param reasonOptionModelList - the list of Reason option model to be created
     * @return                      - a list of saved Reason Option model
     * @throws NullPointerException - if the input list is null
     */
    @Transactional
    public List<ReasonOptionModel> saveMany(List<ReasonOptionModel> reasonOptionModelList) {
        if (reasonOptionModelList == null || reasonOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Reason option model list cannot be null or empty");
        }
        for (ReasonOptionModel reasonOptionModel : reasonOptionModelList) {
            if (reasonOptionRepository.existsByName(reasonOptionModel.getName())) {
                throw new ReasonAlreadyExistException("Reason option already exists: " + reasonOptionModel.getName());
            }
        }
        return reasonOptionRepository.saveAll(reasonOptionModelList);
    }

    /**
     * Retrieves a single Reason option model by its ID.
     * Throws a ReasonNotFoundException if the Reason option is not found or has been deleted.
     *
     * @param id                       - the ID of the Reason option to retrieve
     * @return                         - the Reason option model if found and not deleted
     * @throws ReasonNotFoundException - if the Reason option is not found
     * @throws NullPointerException    - if Reason option ID is null
     */
    @Transactional
    public ReasonOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Reason option ID cannot be null");
        }
        ReasonOptionModel reasonOptionModel = reasonOptionRepository.findById(id)
                .orElseThrow(() -> new ReasonNotFoundException("Reason option not found with ID: " + id));
        if (reasonOptionModel.getDeletedAt() != null) {
            throw new ReasonNotFoundException("Reason option not found with ID: " + id);
        }
        return reasonOptionModel;
    }

    /**
     * Retrieves a list of ReasonOption model list based on the provided ReasonOption ID.
     *
     * @param reasonOptionIdList       - A list of ReasonOption ID to retrieve
     * @return                         - A list of ReasonOptionModel objects that are not marked as deleted
     * @throws NullPointerException    - if a ReasonOption ID list is null
     */
    @Transactional
    public List<ReasonOptionModel> readMany(List<String> reasonOptionIdList) {
        if (reasonOptionIdList == null || reasonOptionIdList.isEmpty()) {
            throw new NullPointerException("Reason option ID list cannot be null");
        }
        List<ReasonOptionModel> modelList = new ArrayList<>();
        for (String id : reasonOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Reason option ID cannot be null");
            }
            ReasonOptionModel reasonOptionModel = reasonOptionRepository.findById(id)
                    .orElse(null);
            if (reasonOptionModel == null)
                continue;
            if (reasonOptionModel.getDeletedAt() == null) {
                modelList.add(reasonOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Reason options that are not marked as deleted
     *
     * @return         - a List of Reason option model where deletedAt is null
     */
    @Transactional
    public List<ReasonOptionModel> readAll() {
        return reasonOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all Reason Option model, including those marked as deleted.
     *
     * @return            - A list of all ReasonOptionModel objects
     */
    @Transactional
    public List<ReasonOptionModel> hardReadAll() {
        return reasonOptionRepository.findAll();
    }

    /**
     * Updates a single Reason Option model identified by the provided ID.
     *
     * @param model                          - The ReasonOptionModel containing updated data
     * @return                               - The updated ReasonOptionModel
     * @throws ReasonNotFoundException       - if reason option is not found
     */
    @Transactional
    public ReasonOptionModel updateOne(ReasonOptionModel model) {
        ReasonOptionModel existing = reasonOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ReasonNotFoundException("Reason option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ReasonNotFoundException("Reason option with ID: " + model.getId() + " is not found");
        }
        return reasonOptionRepository.save(model);
    }

    /**
     * Updates multiple reason option model in a transactional manner.
     *
     * @param modelList                      - List of ReasonOptionModel objects containing updated data
     * @return                               - List of updated ReasonOptionModel objects
     * @throws ReasonNotFoundException       - if reason option is not found
     */
    @Transactional
    public List<ReasonOptionModel> updateMany(List<ReasonOptionModel> modelList) {
        for (ReasonOptionModel model : modelList) {
            ReasonOptionModel existing = reasonOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ReasonNotFoundException("Reason option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ReasonNotFoundException("Reason option with ID: " + model.getId() + " is not found");
            }
        }
        return reasonOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Reason option model by ID, including deleted ones.
     *
     * @param model                          - The ReasonOptionModel containing updated data
     * @return                               - The updated ReasonOptionModel
     */
    @Transactional
    public ReasonOptionModel hardUpdate(ReasonOptionModel model) {
        return reasonOptionRepository.save(model);
    }

    /**
     * Updates multiple ReasonOption modelList by their IDs, including deleted ones.
     *
     * @param reasonOptionModelList        - List of ReasonOptionModel objects containing updated data
     * @return                             - List of updated ReasonOptionModel objects
     */
    @Transactional
    public List<ReasonOptionModel> hardUpdateAll(List<ReasonOptionModel> reasonOptionModelList) {
        return reasonOptionRepository.saveAll(reasonOptionModelList);
    }

    /**
     * Soft deletes a Reason option by ID.
     *
     * @return                         - The soft-deleted ReasonOptionModel
     * @throws ReasonNotFoundException - if reason option id is not found
     */
    @Transactional
    public ReasonOptionModel softDelete(String id) {
        ReasonOptionModel reasonOptionModel = reasonOptionRepository.findById(id)
                .orElseThrow(() -> new ReasonNotFoundException("Reason option not found with id: " + id));
        reasonOptionModel.setDeletedAt(LocalDateTime.now());
        return reasonOptionRepository.save(reasonOptionModel);
    }

    /**
     * Hard deletes a Reason option by ID.
     *
     * @param id                       - ID of the Reason option to hard delete
     * @throws NullPointerException    - if the Reason option ID is null
     * @throws ReasonNotFoundException - if the Reason option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Reason option ID cannot be null");
        }
        if (!reasonOptionRepository.existsById(id)) {
            throw new ReasonNotFoundException("Reason option not found with id: " + id);
        }
        reasonOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Reason options by their IDs.
     *
     * @param idList                   - List of Reason option IDs to be soft deleted
     * @return                         - List of soft-deleted ReasonOption objects
     * @throws ReasonNotFoundException - if any Reason option ID are not found
     */
    @Transactional
    public List<ReasonOptionModel> softDeleteMany(List<String> idList) {
        List<ReasonOptionModel> reasonOptionModelList = reasonOptionRepository.findAllById(idList);
        if (reasonOptionModelList.isEmpty()) {
            throw new ReasonNotFoundException("No reason options found with provided IDList: " + idList);
        }
        for (ReasonOptionModel model : reasonOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return reasonOptionRepository.saveAll(reasonOptionModelList);
    }

    /**
     * Hard deletes multiple Reason options by IDs.
     *
     * @param idList     - List of Reason option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        reasonOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Reason options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        reasonOptionRepository.deleteAll();
    }
}