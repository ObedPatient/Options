/**
 * Service for managing TenderStageOption model.
 * Provides functionality to create, read, update, and delete TenderStageOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.tender_stage.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.tender_stage.exception.TenderStageOptionAlreadyExistException;
import rw.evolve.eprocurement.tender_stage.exception.TenderStageOptionNotFoundException;
import rw.evolve.eprocurement.tender_stage.model.TenderStageOptionModel;
import rw.evolve.eprocurement.tender_stage.repository.TenderStageOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class TenderStageOptionService {

    private TenderStageOptionRepository tenderStageOptionRepository;

    /**
     * Creates a single TenderStage option model with a generated ID.
     *
     * @param tenderStageOptionModel                  - the TenderStageOptionModel to be created
     * @return                                        - the saved TenderStageOption model
     * @throws TenderStageOptionAlreadyExistException - if a TenderStageOption with the same name exists
     */
    @Transactional
    public TenderStageOptionModel save(TenderStageOptionModel tenderStageOptionModel) {
        if (tenderStageOptionModel == null) {
            throw new NullPointerException("Tender stage option cannot be null");
        }
        if (tenderStageOptionRepository.existsByName(tenderStageOptionModel.getName())) {
            throw new TenderStageOptionAlreadyExistException("Tender stage option already exists: " + tenderStageOptionModel.getName());
        }
        return tenderStageOptionRepository.save(tenderStageOptionModel);
    }

    /**
     * Creates multiple TenderStage Option model, each with a unique generated ID.
     *
     * @param tenderStageOptionModelList - the list of TenderStage option model to be created
     * @return                           - a list of saved TenderStage Option model
     * @throws NullPointerException      - if the input list is null
     */
    @Transactional
    public List<TenderStageOptionModel> saveMany(List<TenderStageOptionModel> tenderStageOptionModelList) {
        if (tenderStageOptionModelList == null || tenderStageOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Tender stage option model list cannot be null or empty");
        }
        for (TenderStageOptionModel tenderStageOptionModel : tenderStageOptionModelList) {
            if (tenderStageOptionRepository.existsByName(tenderStageOptionModel.getName())) {
                throw new TenderStageOptionAlreadyExistException("Tender stage option already exists: " + tenderStageOptionModel.getName());
            }
        }
        return tenderStageOptionRepository.saveAll(tenderStageOptionModelList);
    }

    /**
     * Retrieves a single TenderStage option model by its ID.
     * Throws a TenderStageOptionNotFoundException if the TenderStage option is not found or has been deleted.
     *
     * @param id                                  - the ID of the TenderStage option to retrieve
     * @return                                    - the TenderStage option model if found and not deleted
     * @throws TenderStageOptionNotFoundException - if the TenderStage option is not found
     * @throws NullPointerException               - if TenderStage option ID is null
     */
    @Transactional
    public TenderStageOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Tender stage option ID cannot be null");
        }
        TenderStageOptionModel tenderStageOptionModel = tenderStageOptionRepository.findById(id)
                .orElseThrow(() -> new TenderStageOptionNotFoundException("Tender stage option not found with ID: " + id));
        if (tenderStageOptionModel.getDeletedAt() != null) {
            throw new TenderStageOptionNotFoundException("Tender stage option not found with ID: " + id);
        }
        return tenderStageOptionModel;
    }

    /**
     * Retrieves a list of TenderStageOption model list based on the provided TenderStageOption ID.
     *
     * @param tenderStageOptionIdList    - A list of TenderStageOption ID to retrieve
     * @return                           - A list of TenderStageOptionModel objects that are not marked as deleted
     * @throws NullPointerException      - if a TenderStageOption ID list is null
     */
    @Transactional
    public List<TenderStageOptionModel> readMany(List<String> tenderStageOptionIdList) {
        if (tenderStageOptionIdList == null || tenderStageOptionIdList.isEmpty()) {
            throw new NullPointerException("Tender stage option ID list cannot be null");
        }
        List<TenderStageOptionModel> modelList = new ArrayList<>();
        for (String id : tenderStageOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Tender stage option ID cannot be null");
            }
            TenderStageOptionModel tenderStageOptionModel = tenderStageOptionRepository.findById(id)
                    .orElse(null);
            if (tenderStageOptionModel == null)
                continue;
            if (tenderStageOptionModel.getDeletedAt() == null) {
                modelList.add(tenderStageOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all TenderStage options that are not marked as deleted
     *
     * @return         - a List of TenderStage option model where deletedAt is null
     */
    @Transactional
    public List<TenderStageOptionModel> readAll() {
        return tenderStageOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all TenderStage Option model, including those marked as deleted.
     *
     * @return            - A list of all TenderStageOptionModel objects
     */
    @Transactional
    public List<TenderStageOptionModel> hardReadAll() {
        return tenderStageOptionRepository.findAll();
    }

    /**
     * Updates a single TenderStage Option model identified by the provided ID.
     *
     * @param model                               - The TenderStageOptionModel containing updated data
     * @return                                    - The updated TenderStageOptionModel
     * @throws TenderStageOptionNotFoundException - if tender stage option is not found
     */
    @Transactional
    public TenderStageOptionModel updateOne(TenderStageOptionModel model) {
        TenderStageOptionModel existing = tenderStageOptionRepository.findById(model.getId())
                .orElseThrow(() -> new TenderStageOptionNotFoundException("Tender stage option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new TenderStageOptionNotFoundException("Tender stage option with ID: " + model.getId() + " is not found");
        }
        return tenderStageOptionRepository.save(model);
    }

    /**
     * Updates multiple tender stage option model in a transactional manner.
     *
     * @param modelList                           - List of TenderStageOptionModel objects containing updated data
     * @return                                    - List of updated TenderStageOptionModel objects
     * @throws TenderStageOptionNotFoundException - if tender stage option is not found
     */
    @Transactional
    public List<TenderStageOptionModel> updateMany(List<TenderStageOptionModel> modelList) {
        for (TenderStageOptionModel model : modelList) {
            TenderStageOptionModel existing = tenderStageOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new TenderStageOptionNotFoundException("Tender stage option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new TenderStageOptionNotFoundException("Tender stage option with ID: " + model.getId() + " is not found");
            }
        }
        return tenderStageOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single TenderStage option model by ID, including deleted ones.
     *
     * @param model                             - The TenderStageOptionModel containing updated data
     * @return                                  - The updated TenderStageOptionModel
     */
    @Transactional
    public TenderStageOptionModel hardUpdate(TenderStageOptionModel model) {
        return tenderStageOptionRepository.save(model);
    }

    /**
     * Updates multiple TenderStageOption modelList by their IDs, including deleted ones.
     *
     * @param tenderStageOptionModelList      - List of TenderStageOptionModel objects containing updated data
     * @return                                - List of updated TenderStageOptionModel objects
     */
    @Transactional
    public List<TenderStageOptionModel> hardUpdateAll(List<TenderStageOptionModel> tenderStageOptionModelList) {
        return tenderStageOptionRepository.saveAll(tenderStageOptionModelList);
    }

    /**
     * Soft deletes a TenderStage option by ID.
     *
     * @return                                    - The soft-deleted TenderStageOptionModel
     * @throws TenderStageOptionNotFoundException - if tender stage option id is not found
     */
    @Transactional
    public TenderStageOptionModel softDelete(String id) {
        TenderStageOptionModel tenderStageOptionModel = tenderStageOptionRepository.findById(id)
                .orElseThrow(() -> new TenderStageOptionNotFoundException("Tender stage option not found with id: " + id));
        tenderStageOptionModel.setDeletedAt(LocalDateTime.now());
        return tenderStageOptionRepository.save(tenderStageOptionModel);
    }

    /**
     * Hard deletes a TenderStage option by ID.
     *
     * @param id                                  - ID of the TenderStage option to hard delete
     * @throws NullPointerException               - if the TenderStage option ID is null
     * @throws TenderStageOptionNotFoundException - if the TenderStage option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Tender stage option ID cannot be null");
        }
        if (!tenderStageOptionRepository.existsById(id)) {
            throw new TenderStageOptionNotFoundException("Tender stage option not found with id: " + id);
        }
        tenderStageOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple TenderStage options by their IDs.
     *
     * @param idList                              - List of TenderStage option IDs to be soft deleted
     * @return                                    - List of soft-deleted TenderStageOption objects
     * @throws TenderStageOptionNotFoundException - if any TenderStage option ID are not found
     */
    @Transactional
    public List<TenderStageOptionModel> softDeleteMany(List<String> idList) {
        List<TenderStageOptionModel> tenderStageOptionModelList = tenderStageOptionRepository.findAllById(idList);
        if (tenderStageOptionModelList.isEmpty()) {
            throw new TenderStageOptionNotFoundException("No tender stage options found with provided IDList: " + idList);
        }
        for (TenderStageOptionModel model : tenderStageOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return tenderStageOptionRepository.saveAll(tenderStageOptionModelList);
    }

    /**
     * Hard deletes multiple TenderStage options by IDs.
     *
     * @param idList     - List of TenderStage option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        tenderStageOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all TenderStage options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        tenderStageOptionRepository.deleteAll();
    }
}