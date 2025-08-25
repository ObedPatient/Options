/**
 * Service for managing TenderStatusOption model.
 * Provides functionality to create, read, update, and delete TenderStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.tender_status.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.tender_status.exception.TenderStatusOptionAlreadyExistException;
import rw.evolve.eprocurement.tender_status.exception.TenderStatusOptionNotFoundException;
import rw.evolve.eprocurement.tender_status.model.TenderStatusOptionModel;
import rw.evolve.eprocurement.tender_status.repository.TenderStatusOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class TenderStatusOptionService {

    private TenderStatusOptionRepository tenderStatusOptionRepository;

    /**
     * Creates a single TenderStatus option model with a generated ID.
     *
     * @param tenderStatusOptionModel                  - the TenderStatusOptionModel to be created
     * @return                                         - the saved TenderStatusOption model
     * @throws TenderStatusOptionAlreadyExistException - if a TenderStatusOption with the same name exists
     */
    @Transactional
    public TenderStatusOptionModel save(TenderStatusOptionModel tenderStatusOptionModel) {
        if (tenderStatusOptionModel == null) {
            throw new NullPointerException("Tender status option cannot be null");
        }
        if (tenderStatusOptionRepository.existsByName(tenderStatusOptionModel.getName())) {
            throw new TenderStatusOptionAlreadyExistException("Tender status option already exists: " + tenderStatusOptionModel.getName());
        }
        return tenderStatusOptionRepository.save(tenderStatusOptionModel);
    }

    /**
     * Creates multiple TenderStatus Option model, each with a unique generated ID.
     *
     * @param tenderStatusOptionModelList - the list of TenderStatus option model to be created
     * @return                            - a list of saved TenderStatus Option model
     * @throws NullPointerException       - if the input list is null
     */
    @Transactional
    public List<TenderStatusOptionModel> saveMany(List<TenderStatusOptionModel> tenderStatusOptionModelList) {
        if (tenderStatusOptionModelList == null || tenderStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Tender status option model list cannot be null or empty");
        }
        for (TenderStatusOptionModel tenderStatusOptionModel : tenderStatusOptionModelList) {
            if (tenderStatusOptionRepository.existsByName(tenderStatusOptionModel.getName())) {
                throw new TenderStatusOptionAlreadyExistException("Tender status option already exists: " + tenderStatusOptionModel.getName());
            }
        }
        return tenderStatusOptionRepository.saveAll(tenderStatusOptionModelList);
    }

    /**
     * Retrieves a single TenderStatus option model by its ID.
     * Throws a TenderStatusOptionNotFoundException if the TenderStatus option is not found or has been deleted.
     *
     * @param id                                   - the ID of the TenderStatus option to retrieve
     * @return                                     - the TenderStatus option model if found and not deleted
     * @throws TenderStatusOptionNotFoundException - if the TenderStatus option is not found
     * @throws NullPointerException                - if TenderStatus option ID is null
     */
    @Transactional
    public TenderStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Tender status option ID cannot be null");
        }
        TenderStatusOptionModel tenderStatusOptionModel = tenderStatusOptionRepository.findById(id)
                .orElseThrow(() -> new TenderStatusOptionNotFoundException("Tender status option not found with ID: " + id));
        if (tenderStatusOptionModel.getDeletedAt() != null) {
            throw new TenderStatusOptionNotFoundException("Tender status option not found with ID: " + id);
        }
        return tenderStatusOptionModel;
    }

    /**
     * Retrieves a list of TenderStatusOption model list based on the provided TenderStatusOption ID.
     *
     * @param tenderStatusOptionIdList    - A list of TenderStatusOption ID to retrieve
     * @return                            - A list of TenderStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException       - if a TenderStatusOption ID list is null
     */
    @Transactional
    public List<TenderStatusOptionModel> readMany(List<String> tenderStatusOptionIdList) {
        if (tenderStatusOptionIdList == null || tenderStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("Tender status option ID list cannot be null");
        }
        List<TenderStatusOptionModel> modelList = new ArrayList<>();
        for (String id : tenderStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Tender status option ID cannot be null");
            }
            TenderStatusOptionModel tenderStatusOptionModel = tenderStatusOptionRepository.findById(id)
                    .orElse(null);
            if (tenderStatusOptionModel == null)
                continue;
            if (tenderStatusOptionModel.getDeletedAt() == null) {
                modelList.add(tenderStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all TenderStatus options that are not marked as deleted
     *
     * @return         - a List of TenderStatus option model where deletedAt is null
     */
    @Transactional
    public List<TenderStatusOptionModel> readAll() {
        return tenderStatusOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all TenderStatus Option model, including those marked as deleted.
     *
     * @return            - A list of all TenderStatusOptionModel objects
     */
    @Transactional
    public List<TenderStatusOptionModel> hardReadAll() {
        return tenderStatusOptionRepository.findAll();
    }

    /**
     * Updates a single TenderStatus Option model identified by the provided ID.
     *
     * @param model                                - The TenderStatusOptionModel containing updated data
     * @return                                     - The updated TenderStatusOptionModel
     * @throws TenderStatusOptionNotFoundException - if tender status option is not found
     */
    @Transactional
    public TenderStatusOptionModel updateOne(TenderStatusOptionModel model) {
        TenderStatusOptionModel existing = tenderStatusOptionRepository.findById(model.getId())
                .orElseThrow(() -> new TenderStatusOptionNotFoundException("Tender status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new TenderStatusOptionNotFoundException("Tender status option with ID: " + model.getId() + " is not found");
        }
        return tenderStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple tender status option model in a transactional manner.
     *
     * @param modelList                            - List of TenderStatusOptionModel objects containing updated data
     * @return                                     - List of updated TenderStatusOptionModel objects
     * @throws TenderStatusOptionNotFoundException - if tender status option is not found
     */
    @Transactional
    public List<TenderStatusOptionModel> updateMany(List<TenderStatusOptionModel> modelList) {
        for (TenderStatusOptionModel model : modelList) {
            TenderStatusOptionModel existing = tenderStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new TenderStatusOptionNotFoundException("Tender status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new TenderStatusOptionNotFoundException("Tender status option with ID: " + model.getId() + " is not found");
            }
        }
        return tenderStatusOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single TenderStatus option model by ID, including deleted ones.
     *
     * @param model                               - The TenderStatusOptionModel containing updated data
     * @return                                    - The updated TenderStatusOptionModel
     */
    @Transactional
    public TenderStatusOptionModel hardUpdate(TenderStatusOptionModel model) {
        return tenderStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple TenderStatusOption modelList by their IDs, including deleted ones.
     *
     * @param tenderStatusOptionModelList       - List of TenderStatusOptionModel objects containing updated data
     * @return                                  - List of updated TenderStatusOptionModel objects
     */
    @Transactional
    public List<TenderStatusOptionModel> hardUpdateAll(List<TenderStatusOptionModel> tenderStatusOptionModelList) {
        return tenderStatusOptionRepository.saveAll(tenderStatusOptionModelList);
    }

    /**
     * Soft deletes a TenderStatus option by ID.
     *
     * @return                                     - The soft-deleted TenderStatusOptionModel
     * @throws TenderStatusOptionNotFoundException - if tender status option id is not found
     */
    @Transactional
    public TenderStatusOptionModel softDelete(String id) {
        TenderStatusOptionModel tenderStatusOptionModel = tenderStatusOptionRepository.findById(id)
                .orElseThrow(() -> new TenderStatusOptionNotFoundException("Tender status option not found with id: " + id));
        tenderStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return tenderStatusOptionRepository.save(tenderStatusOptionModel);
    }

    /**
     * Hard deletes a TenderStatus option by ID.
     *
     * @param id                                   - ID of the TenderStatus option to hard delete
     * @throws NullPointerException                - if the TenderStatus option ID is null
     * @throws TenderStatusOptionNotFoundException - if the TenderStatus option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Tender status option ID cannot be null");
        }
        if (!tenderStatusOptionRepository.existsById(id)) {
            throw new TenderStatusOptionNotFoundException("Tender status option not found with id: " + id);
        }
        tenderStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple TenderStatus options by their IDs.
     *
     * @param idList                               - List of TenderStatus option IDs to be soft deleted
     * @return                                     - List of soft-deleted TenderStatusOption objects
     * @throws TenderStatusOptionNotFoundException - if any TenderStatus option ID are not found
     */
    @Transactional
    public List<TenderStatusOptionModel> softDeleteMany(List<String> idList) {
        List<TenderStatusOptionModel> tenderStatusOptionModelList = tenderStatusOptionRepository.findAllById(idList);
        if (tenderStatusOptionModelList.isEmpty()) {
            throw new TenderStatusOptionNotFoundException("No tender status options found with provided IDList: " + idList);
        }
        for (TenderStatusOptionModel model : tenderStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return tenderStatusOptionRepository.saveAll(tenderStatusOptionModelList);
    }

    /**
     * Hard deletes multiple TenderStatus options by IDs.
     *
     * @param idList     - List of TenderStatus option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        tenderStatusOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all TenderStatus options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        tenderStatusOptionRepository.deleteAll();
    }
}