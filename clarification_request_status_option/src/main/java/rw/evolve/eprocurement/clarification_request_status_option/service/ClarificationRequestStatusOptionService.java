/**
 * Service for managing ClarificationRequestStatusOption model.
 * Provides functionality to create, read, update, and delete ClarificationRequestStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.clarification_request_status_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.clarification_request_status_option.exception.ClarificationRequestStatusAlreadyExistException;
import rw.evolve.eprocurement.clarification_request_status_option.exception.ClarificationRequestStatusNotFoundException;
import rw.evolve.eprocurement.clarification_request_status_option.model.ClarificationRequestStatusOptionModel;
import rw.evolve.eprocurement.clarification_request_status_option.repository.ClarificationRequestStatusOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ClarificationRequestStatusOptionService {


    private ClarificationRequestStatusOptionRepository clarificationRequestStatusOptionRepository;

    /**
     * Creates a single ClarificationRequestStatus option model with a generated ID.
     *
     * @param clarificationRequestStatusOptionModel            - the ClarificationRequestStatusOptionModel to be created
     * @return                                                 - the saved ClarificationRequestStatusOption model
     * @throws ClarificationRequestStatusAlreadyExistException - if a ClarificationRequestStatusOption with the same name exists
     */
    @Transactional
    public ClarificationRequestStatusOptionModel save(ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel) {
        if (clarificationRequestStatusOptionModel == null) {
            throw new NullPointerException("Clarification request status option cannot be null");
        }
        if (clarificationRequestStatusOptionRepository.existsByName(clarificationRequestStatusOptionModel.getName())) {
            throw new ClarificationRequestStatusAlreadyExistException("Clarification request status option already exists: " + clarificationRequestStatusOptionModel.getName());
        }
        return clarificationRequestStatusOptionRepository.save(clarificationRequestStatusOptionModel);
    }

    /**
     * Creates multiple ClarificationRequestStatusOption model, each with a unique generated ID.
     *
     * @param clarificationRequestStatusOptionModelList - the list of ClarificationRequestStatus option model to be created
     * @return                                          - a list of saved ClarificationRequestStatusOption model
     * @throws NullPointerException                     - if the input list is null
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> saveMany(List<ClarificationRequestStatusOptionModel> clarificationRequestStatusOptionModelList) {
        if (clarificationRequestStatusOptionModelList == null || clarificationRequestStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Clarification request status option model list cannot be null or empty");
        }
        for (ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel : clarificationRequestStatusOptionModelList) {
            if (clarificationRequestStatusOptionRepository.existsByName(clarificationRequestStatusOptionModel.getName())) {
                throw new ClarificationRequestStatusAlreadyExistException("Clarification request status option already exists: " + clarificationRequestStatusOptionModel.getName());
            }
        }
        return clarificationRequestStatusOptionRepository.saveAll(clarificationRequestStatusOptionModelList);
    }

    /**
     * Retrieves a single ClarificationRequestStatus option model by its ID.
     * Throws a ClarificationRequestStatusNotFoundException if the ClarificationRequestStatus option is not found or has been deleted.
     *
     * @param id                                           - the ID of the ClarificationRequestStatus option to retrieve
     * @return                                             - the ClarificationRequestStatus option model if found and not deleted
     * @throws ClarificationRequestStatusNotFoundException - if the ClarificationRequestStatus option is not found
     * @throws NullPointerException                        - if ClarificationRequestStatus option ID is null
     */
    @Transactional
    public ClarificationRequestStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Clarification request status option ID cannot be null");
        }
        ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel = clarificationRequestStatusOptionRepository.findById(id)
                .orElseThrow(() -> new ClarificationRequestStatusNotFoundException("Clarification request status option not found with ID: " + id));
        if (clarificationRequestStatusOptionModel.getDeletedAt() != null) {
            throw new ClarificationRequestStatusNotFoundException("Clarification request status option not found with ID: " + id);
        }
        return clarificationRequestStatusOptionModel;
    }

    /**
     * Retrieves a list of ClarificationRequestStatusOption model list based on the provided ClarificationRequestStatusOption ID.
     *
     * @param clarificationRequestStatusOptionIdList - A list of ClarificationRequestStatusOption ID to retrieve
     * @return                                       - A list of ClarificationRequestStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException                  - if a ClarificationRequestStatusOption ID list is null
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> readMany(List<String> clarificationRequestStatusOptionIdList) {
        if (clarificationRequestStatusOptionIdList == null || clarificationRequestStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("Clarification request status option ID list cannot be null");
        }
        List<ClarificationRequestStatusOptionModel> modelList = new ArrayList<>();
        for (String id : clarificationRequestStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Clarification request status option ID cannot be null");
            }
            ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel = clarificationRequestStatusOptionRepository.findById(id)
                    .orElse(null);
            if (clarificationRequestStatusOptionModel == null)
                continue;
            if (clarificationRequestStatusOptionModel.getDeletedAt() == null) {
                modelList.add(clarificationRequestStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all ClarificationRequestStatus options that are not marked as deleted
     *
     * @return         - a List of ClarificationRequestStatus option model where deletedAt is null
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> readAll() {
        return clarificationRequestStatusOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all ClarificationRequestStatusOption model, including those marked as deleted.
     *
     * @return            - A list of all ClarificationRequestStatusOptionModel objects
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> hardReadAll() {
        return clarificationRequestStatusOptionRepository.findAll();
    }

    /**
     * Updates a single ClarificationRequestStatusOption model identified by the provided ID.
     *
     * @param model                                        - The ClarificationRequestStatusOptionModel containing updated data
     * @return                                             - The updated ClarificationRequestStatusOptionModel
     * @throws ClarificationRequestStatusNotFoundException - if clarification request status option is not found
     */
    @Transactional
    public ClarificationRequestStatusOptionModel updateOne(ClarificationRequestStatusOptionModel model) {
        ClarificationRequestStatusOptionModel existing = clarificationRequestStatusOptionRepository.findById(model.getId())
                .orElseThrow(() -> new ClarificationRequestStatusNotFoundException("Clarification request status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new ClarificationRequestStatusNotFoundException("Clarification request status option with ID: " + model.getId() + " is not found");
        }
        return clarificationRequestStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple clarification request status option model in a transactional manner.
     *
     * @param modelList                                    - List of ClarificationRequestStatusOptionModel objects containing updated data
     * @return                                             - List of updated ClarificationRequestStatusOptionModel objects
     * @throws ClarificationRequestStatusNotFoundException - if clarification request status option is not found
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> updateMany(List<ClarificationRequestStatusOptionModel> modelList) {
        for (ClarificationRequestStatusOptionModel model : modelList) {
            ClarificationRequestStatusOptionModel existing = clarificationRequestStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new ClarificationRequestStatusNotFoundException("Clarification request status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new ClarificationRequestStatusNotFoundException("Clarification request status option with ID: " + model.getId() + " is not found");
            }
        }
        return clarificationRequestStatusOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single ClarificationRequestStatus option model by ID, including deleted ones.
     *
     * @param model                                  - The ClarificationRequestStatusOptionModel containing updated data
     * @return                                       - The updated ClarificationRequestStatusOptionModel
     */
    @Transactional
    public ClarificationRequestStatusOptionModel hardUpdate(ClarificationRequestStatusOptionModel model) {
        return clarificationRequestStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple ClarificationRequestStatusOption modelList by their IDs, including deleted ones.
     *
     * @param clarificationRequestStatusOptionModelList - List of ClarificationRequestStatusOptionModel objects containing updated data
     * @return                                          - List of updated ClarificationRequestStatusOptionModel objects
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> hardUpdateAll(List<ClarificationRequestStatusOptionModel> clarificationRequestStatusOptionModelList) {
        return clarificationRequestStatusOptionRepository.saveAll(clarificationRequestStatusOptionModelList);
    }

    /**
     * Soft deletes a ClarificationRequestStatus option by ID.
     *
     * @return                                             - The soft-deleted ClarificationRequestStatusOptionModel
     * @throws ClarificationRequestStatusNotFoundException - if clarification request status option id is not found
     */
    @Transactional
    public ClarificationRequestStatusOptionModel softDelete(String id) {
        ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel = clarificationRequestStatusOptionRepository.findById(id)
                .orElseThrow(() -> new ClarificationRequestStatusNotFoundException("Clarification request status option not found with id: " + id));
        clarificationRequestStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return clarificationRequestStatusOptionRepository.save(clarificationRequestStatusOptionModel);
    }

    /**
     * Hard deletes a ClarificationRequestStatus option by ID.
     *
     * @param id                                           - ID of the ClarificationRequestStatus option to hard delete
     * @throws NullPointerException                        - if the ClarificationRequestStatus option ID is null
     * @throws ClarificationRequestStatusNotFoundException - if the ClarificationRequestStatus option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Clarification request status option ID cannot be null");
        }
        if (!clarificationRequestStatusOptionRepository.existsById(id)) {
            throw new ClarificationRequestStatusNotFoundException("Clarification request status option not found with id: " + id);
        }
        clarificationRequestStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple ClarificationRequestStatus options by their IDs.
     *
     * @param idList                                       - List of ClarificationRequestStatus option IDs to be soft deleted
     * @return                                             - List of soft-deleted ClarificationRequestStatusOption objects
     * @throws ClarificationRequestStatusNotFoundException - if any ClarificationRequestStatus option ID are not found
     */
    @Transactional
    public List<ClarificationRequestStatusOptionModel> softDeleteMany(List<String> idList) {
        List<ClarificationRequestStatusOptionModel> clarificationRequestStatusOptionModelList = clarificationRequestStatusOptionRepository.findAllById(idList);
        if (clarificationRequestStatusOptionModelList.isEmpty()) {
            throw new ClarificationRequestStatusNotFoundException("No clarification request status options found with provided IDList: " + idList);
        }
        for (ClarificationRequestStatusOptionModel model : clarificationRequestStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return clarificationRequestStatusOptionRepository.saveAll(clarificationRequestStatusOptionModelList);
    }

    /**
     * Hard deletes multiple ClarificationRequestStatus options by IDs.
     *
     * @param idList     - List of ClarificationRequestStatus option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        clarificationRequestStatusOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all ClarificationRequestStatus options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        clarificationRequestStatusOptionRepository.deleteAll();
    }
}