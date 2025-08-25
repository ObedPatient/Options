/**
 * Service for managing LotBiddingEligibilityOption model.
 * Provides functionality to create, read, update, and delete LotBiddingEligibilityOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.lot_bidding_eligibility.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.lot_bidding_eligibility.exception.LotBiddingEligibilityOptionAlreadyExistException;
import rw.evolve.eprocurement.lot_bidding_eligibility.exception.LotBiddingEligibilityOptionNotFoundException;
import rw.evolve.eprocurement.lot_bidding_eligibility.model.LotBiddingEligibilityOptionModel;
import rw.evolve.eprocurement.lot_bidding_eligibility.repository.LotBiddingEligibilityOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class LotBiddingEligibilityOptionService {

    private LotBiddingEligibilityOptionRepository lotBiddingEligibilityOptionRepository;

    /**
     * Creates a single LotBiddingEligibility option model with a generated ID.
     *
     * @param lotBiddingEligibilityOptionModel                  - the LotBiddingEligibilityOptionModel to be created
     * @return                                                  - the saved LotBiddingEligibilityOption model
     * @throws LotBiddingEligibilityOptionAlreadyExistException - if a LotBiddingEligibilityOption with the same name exists
     */
    @Transactional
    public LotBiddingEligibilityOptionModel save(LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel) {
        if (lotBiddingEligibilityOptionModel == null) {
            throw new NullPointerException("Lot bidding eligibility option cannot be null");
        }
        if (lotBiddingEligibilityOptionRepository.existsByName(lotBiddingEligibilityOptionModel.getName())) {
            throw new LotBiddingEligibilityOptionAlreadyExistException("Lot bidding eligibility option already exists: " + lotBiddingEligibilityOptionModel.getName());
        }
        return lotBiddingEligibilityOptionRepository.save(lotBiddingEligibilityOptionModel);
    }

    /**
     * Creates multiple LotBiddingEligibility Option model, each with a unique generated ID.
     *
     * @param lotBiddingEligibilityOptionModelList - the list of LotBiddingEligibility option model to be created
     * @return                                     - a list of saved LotBiddingEligibility Option model
     * @throws NullPointerException                - if the input list is null
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> saveMany(List<LotBiddingEligibilityOptionModel> lotBiddingEligibilityOptionModelList) {
        if (lotBiddingEligibilityOptionModelList == null || lotBiddingEligibilityOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Lot bidding eligibility option model list cannot be null or empty");
        }
        for (LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel : lotBiddingEligibilityOptionModelList) {
            if (lotBiddingEligibilityOptionRepository.existsByName(lotBiddingEligibilityOptionModel.getName())) {
                throw new LotBiddingEligibilityOptionAlreadyExistException("Lot bidding eligibility option already exists: " + lotBiddingEligibilityOptionModel.getName());
            }
        }
        return lotBiddingEligibilityOptionRepository.saveAll(lotBiddingEligibilityOptionModelList);
    }

    /**
     * Retrieves a single LotBiddingEligibility option model by its ID.
     * Throws a LotBiddingEligibilityOptionNotFoundException if the LotBiddingEligibility option is not found or has been deleted.
     *
     * @param id                                            - the ID of the LotBiddingEligibility option to retrieve
     * @return                                              - the LotBiddingEligibility option model if found and not deleted
     * @throws LotBiddingEligibilityOptionNotFoundException - if the LotBiddingEligibility option is not found
     * @throws NullPointerException                         - if LotBiddingEligibility option ID is null
     */
    @Transactional
    public LotBiddingEligibilityOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Lot bidding eligibility option ID cannot be null");
        }
        LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel = lotBiddingEligibilityOptionRepository.findById(id)
                .orElseThrow(() -> new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option not found with ID: " + id));
        if (lotBiddingEligibilityOptionModel.getDeletedAt() != null) {
            throw new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option not found with ID: " + id);
        }
        return lotBiddingEligibilityOptionModel;
    }

    /**
     * Retrieves a list of LotBiddingEligibilityOption model list based on the provided LotBiddingEligibilityOption ID.
     *
     * @param lotBiddingEligibilityOptionIdList    - A list of LotBiddingEligibilityOption ID to retrieve
     * @return                                     - A list of LotBiddingEligibilityOptionModel objects that are not marked as deleted
     * @throws NullPointerException                - if a LotBiddingEligibilityOption ID list is null
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> readMany(List<String> lotBiddingEligibilityOptionIdList) {
        if (lotBiddingEligibilityOptionIdList == null || lotBiddingEligibilityOptionIdList.isEmpty()) {
            throw new NullPointerException("Lot bidding eligibility option ID list cannot be null");
        }
        List<LotBiddingEligibilityOptionModel> modelList = new ArrayList<>();
        for (String id : lotBiddingEligibilityOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Lot bidding eligibility option ID cannot be null");
            }
            LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel = lotBiddingEligibilityOptionRepository.findById(id)
                    .orElse(null);
            if (lotBiddingEligibilityOptionModel == null)
                continue;
            if (lotBiddingEligibilityOptionModel.getDeletedAt() == null) {
                modelList.add(lotBiddingEligibilityOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all LotBiddingEligibility options that are not marked as deleted
     *
     * @return         - a List of LotBiddingEligibility option model where deletedAt is null
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> readAll() {
        return lotBiddingEligibilityOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all LotBiddingEligibility Option model, including those marked as deleted.
     *
     * @return            - A list of all LotBiddingEligibilityOptionModel objects
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> hardReadAll() {
        return lotBiddingEligibilityOptionRepository.findAll();
    }

    /**
     * Updates a single LotBiddingEligibility Option model identified by the provided ID.
     *
     * @param model                                         - The LotBiddingEligibilityOptionModel containing updated data
     * @return                                              - The updated LotBiddingEligibilityOptionModel
     * @throws LotBiddingEligibilityOptionNotFoundException - if lot bidding eligibility option is not found
     */
    @Transactional
    public LotBiddingEligibilityOptionModel updateOne(LotBiddingEligibilityOptionModel model) {
        LotBiddingEligibilityOptionModel existing = lotBiddingEligibilityOptionRepository.findById(model.getId())
                .orElseThrow(() -> new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option with ID: " + model.getId() + " is not found");
        }
        return lotBiddingEligibilityOptionRepository.save(model);
    }

    /**
     * Updates multiple lot bidding eligibility option model in a transactional manner.
     *
     * @param modelList                                     - List of LotBiddingEligibilityOptionModel objects containing updated data
     * @return                                              - List of updated LotBiddingEligibilityOptionModel objects
     * @throws LotBiddingEligibilityOptionNotFoundException - if lot bidding eligibility option is not found
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> updateMany(List<LotBiddingEligibilityOptionModel> modelList) {
        for (LotBiddingEligibilityOptionModel model : modelList) {
            LotBiddingEligibilityOptionModel existing = lotBiddingEligibilityOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option with ID: " + model.getId() + " is not found");
            }
        }
        return lotBiddingEligibilityOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single LotBiddingEligibility option model by ID, including deleted ones.
     *
     * @param model                                       - The LotBiddingEligibilityOptionModel containing updated data
     * @return                                            - The updated LotBiddingEligibilityOptionModel
     */
    @Transactional
    public LotBiddingEligibilityOptionModel hardUpdate(LotBiddingEligibilityOptionModel model) {
        return lotBiddingEligibilityOptionRepository.save(model);
    }

    /**
     * Updates multiple LotBiddingEligibilityOption modelList by their IDs, including deleted ones.
     *
     * @param lotBiddingEligibilityOptionModelList      - List of LotBiddingEligibilityOptionModel objects containing updated data
     * @return                                          - List of updated LotBiddingEligibilityOptionModel objects
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> hardUpdateAll(List<LotBiddingEligibilityOptionModel> lotBiddingEligibilityOptionModelList) {
        return lotBiddingEligibilityOptionRepository.saveAll(lotBiddingEligibilityOptionModelList);
    }

    /**
     * Soft deletes a LotBiddingEligibility option by ID.
     *
     * @return                                              - The soft-deleted LotBiddingEligibilityOptionModel
     * @throws LotBiddingEligibilityOptionNotFoundException - if lot bidding eligibility option id is not found
     */
    @Transactional
    public LotBiddingEligibilityOptionModel softDelete(String id) {
        LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel = lotBiddingEligibilityOptionRepository.findById(id)
                .orElseThrow(() -> new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option not found with id: " + id));
        lotBiddingEligibilityOptionModel.setDeletedAt(LocalDateTime.now());
        return lotBiddingEligibilityOptionRepository.save(lotBiddingEligibilityOptionModel);
    }

    /**
     * Hard deletes a LotBiddingEligibility option by ID.
     *
     * @param id                                            - ID of the LotBiddingEligibility option to hard delete
     * @throws NullPointerException                         - if the LotBiddingEligibility option ID is null
     * @throws LotBiddingEligibilityOptionNotFoundException - if the LotBiddingEligibility option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Lot bidding eligibility option ID cannot be null");
        }
        if (!lotBiddingEligibilityOptionRepository.existsById(id)) {
            throw new LotBiddingEligibilityOptionNotFoundException("Lot bidding eligibility option not found with id: " + id);
        }
        lotBiddingEligibilityOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple LotBiddingEligibility options by their IDs.
     *
     * @param idList                                        - List of LotBiddingEligibility option IDs to be soft deleted
     * @return                                              - List of soft-deleted LotBiddingEligibilityOption objects
     * @throws LotBiddingEligibilityOptionNotFoundException - if any LotBiddingEligibility option ID are not found
     */
    @Transactional
    public List<LotBiddingEligibilityOptionModel> softDeleteMany(List<String> idList) {
        List<LotBiddingEligibilityOptionModel> lotBiddingEligibilityOptionModelList = lotBiddingEligibilityOptionRepository.findAllById(idList);
        if (lotBiddingEligibilityOptionModelList.isEmpty()) {
            throw new LotBiddingEligibilityOptionNotFoundException("No lot bidding eligibility options found with provided IDList: " + idList);
        }
        for (LotBiddingEligibilityOptionModel model : lotBiddingEligibilityOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return lotBiddingEligibilityOptionRepository.saveAll(lotBiddingEligibilityOptionModelList);
    }

    /**
     * Hard deletes multiple LotBiddingEligibility options by IDs.
     *
     * @param idList     - List of LotBiddingEligibility option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        lotBiddingEligibilityOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all LotBiddingEligibility options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        lotBiddingEligibilityOptionRepository.deleteAll();
    }
}