/**
 * Service for managing BidSecurityTypeOption model.
 * Provides functionality to create, read, update, and delete BidSecurityTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.bid_security_type.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.bid_security_type.exception.BidSecurityTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.bid_security_type.exception.BidSecurityTypeOptionNotFoundException;
import rw.evolve.eprocurement.bid_security_type.model.BidSecurityTypeOptionModel;
import rw.evolve.eprocurement.bid_security_type.repository.BidSecurityTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BidSecurityTypeOptionService {

    @Autowired
    private BidSecurityTypeOptionRepository bidSecurityTypeOptionRepository;

    /**
     * Creates a single BidSecurityType option model with a generated ID.
     *
     * @param bidSecurityTypeOptionModel                  - the BidSecurityTypeOptionModel to be created
     * @return                                            - the saved BidSecurityTypeOption model
     * @throws BidSecurityTypeOptionAlreadyExistException - if a BidSecurityTypeOption with the same name exists
     */
    @Transactional
    public BidSecurityTypeOptionModel save(BidSecurityTypeOptionModel bidSecurityTypeOptionModel) {
        if (bidSecurityTypeOptionModel == null) {
            throw new NullPointerException("Bid security type option cannot be null");
        }
        if (bidSecurityTypeOptionRepository.existsByName(bidSecurityTypeOptionModel.getName())) {
            throw new BidSecurityTypeOptionAlreadyExistException("Bid security type option already exists: " + bidSecurityTypeOptionModel.getName());
        }
        return bidSecurityTypeOptionRepository.save(bidSecurityTypeOptionModel);
    }

    /**
     * Creates multiple BidSecurityType Option model, each with a unique generated ID.
     *
     * @param bidSecurityTypeOptionModelList - the list of BidSecurityType option model to be created
     * @return                               - a list of saved BidSecurityType Option model
     * @throws NullPointerException          - if the input list is null
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> saveMany(List<BidSecurityTypeOptionModel> bidSecurityTypeOptionModelList) {
        if (bidSecurityTypeOptionModelList == null || bidSecurityTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Bid security type option model list cannot be null or empty");
        }
        for (BidSecurityTypeOptionModel bidSecurityTypeOptionModel : bidSecurityTypeOptionModelList) {
            if (bidSecurityTypeOptionRepository.existsByName(bidSecurityTypeOptionModel.getName())) {
                throw new BidSecurityTypeOptionAlreadyExistException("Bid security type option already exists: " + bidSecurityTypeOptionModel.getName());
            }
        }
        return bidSecurityTypeOptionRepository.saveAll(bidSecurityTypeOptionModelList);
    }

    /**
     * Retrieves a single BidSecurityType option model by its ID.
     * Throws a BidSecurityTypeOptionNotFoundException if the BidSecurityType option is not found or has been deleted.
     *
     * @param id                                      - the ID of the BidSecurityType option to retrieve
     * @return                                        - the BidSecurityType option model if found and not deleted
     * @throws BidSecurityTypeOptionNotFoundException - if the BidSecurityType option is not found
     * @throws NullPointerException                   - if BidSecurityType option ID is null
     */
    @Transactional
    public BidSecurityTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Bid security type option ID cannot be null");
        }
        BidSecurityTypeOptionModel bidSecurityTypeOptionModel = bidSecurityTypeOptionRepository.findById(id)
                .orElseThrow(() -> new BidSecurityTypeOptionNotFoundException("Bid security type option not found with ID: " + id));
        if (bidSecurityTypeOptionModel.getDeletedAt() != null) {
            throw new BidSecurityTypeOptionNotFoundException("Bid security type option not found with ID: " + id);
        }
        return bidSecurityTypeOptionModel;
    }

    /**
     * Retrieves a list of BidSecurityTypeOption model list based on the provided BidSecurityTypeOption ID.
     *
     * @param bidSecurityTypeOptionIdList    - A list of BidSecurityTypeOption ID to retrieve
     * @return                               - A list of BidSecurityTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException          - if a BidSecurityTypeOption ID list is null
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> readMany(List<String> bidSecurityTypeOptionIdList) {
        if (bidSecurityTypeOptionIdList == null || bidSecurityTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Bid security type option ID list cannot be null");
        }
        List<BidSecurityTypeOptionModel> modelList = new ArrayList<>();
        for (String id : bidSecurityTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Bid security type option ID cannot be null");
            }
            BidSecurityTypeOptionModel bidSecurityTypeOptionModel = bidSecurityTypeOptionRepository.findById(id)
                    .orElse(null);
            if (bidSecurityTypeOptionModel == null)
                continue;
            if (bidSecurityTypeOptionModel.getDeletedAt() == null) {
                modelList.add(bidSecurityTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all BidSecurityType options that are not marked as deleted
     *
     * @return         - a List of BidSecurityType option model where deletedAt is null
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> readAll() {
        return bidSecurityTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all BidSecurityType Option model, including those marked as deleted.
     *
     * @return            - A list of all BidSecurityTypeOptionModel objects
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> hardReadAll() {
        return bidSecurityTypeOptionRepository.findAll();
    }

    /**
     * Updates a single BidSecurityType Option model identified by the provided ID.
     *
     * @param model                                   - The BidSecurityTypeOptionModel containing updated data
     * @return                                        - The updated BidSecurityTypeOptionModel
     * @throws BidSecurityTypeOptionNotFoundException - if bid security type option is not found
     */
    @Transactional
    public BidSecurityTypeOptionModel updateOne(BidSecurityTypeOptionModel model) {
        BidSecurityTypeOptionModel existing = bidSecurityTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new BidSecurityTypeOptionNotFoundException("Bid security type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new BidSecurityTypeOptionNotFoundException("Bid security type option with ID: " + model.getId() + " is not found");
        }
        return bidSecurityTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple bid security type option model in a transactional manner.
     *
     * @param modelList                               - List of BidSecurityTypeOptionModel objects containing updated data
     * @return                                        - List of updated BidSecurityTypeOptionModel objects
     * @throws BidSecurityTypeOptionNotFoundException - if bid security type option is not found
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> updateMany(List<BidSecurityTypeOptionModel> modelList) {
        for (BidSecurityTypeOptionModel model : modelList) {
            BidSecurityTypeOptionModel existing = bidSecurityTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new BidSecurityTypeOptionNotFoundException("Bid security type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new BidSecurityTypeOptionNotFoundException("Bid security type option with ID: " + model.getId() + " is not found");
            }
        }
        return bidSecurityTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single BidSecurityType option model by ID, including deleted ones.
     *
     * @param model                                 - The BidSecurityTypeOptionModel containing updated data
     * @return                                      - The updated BidSecurityTypeOptionModel
     */
    @Transactional
    public BidSecurityTypeOptionModel hardUpdate(BidSecurityTypeOptionModel model) {
        return bidSecurityTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple BidSecurityTypeOption modelList by their IDs, including deleted ones.
     *
     * @param bidSecurityTypeOptionModelList      - List of BidSecurityTypeOptionModel objects containing updated data
     * @return                                    - List of updated BidSecurityTypeOptionModel objects
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> hardUpdateAll(List<BidSecurityTypeOptionModel> bidSecurityTypeOptionModelList) {
        return bidSecurityTypeOptionRepository.saveAll(bidSecurityTypeOptionModelList);
    }

    /**
     * Soft deletes a BidSecurityType option by ID.
     *
     * @return                                        - The soft-deleted BidSecurityTypeOptionModel
     * @throws BidSecurityTypeOptionNotFoundException - if bid security type option id is not found
     */
    @Transactional
    public BidSecurityTypeOptionModel softDelete(String id) {
        BidSecurityTypeOptionModel bidSecurityTypeOptionModel = bidSecurityTypeOptionRepository.findById(id)
                .orElseThrow(() -> new BidSecurityTypeOptionNotFoundException("Bid security type option not found with id: " + id));
        bidSecurityTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return bidSecurityTypeOptionRepository.save(bidSecurityTypeOptionModel);
    }

    /**
     * Hard deletes a BidSecurityType option by ID.
     *
     * @param id                                      - ID of the BidSecurityType option to hard delete
     * @throws NullPointerException                   - if the BidSecurityType option ID is null
     * @throws BidSecurityTypeOptionNotFoundException - if the BidSecurityType option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Bid security type option ID cannot be null");
        }
        if (!bidSecurityTypeOptionRepository.existsById(id)) {
            throw new BidSecurityTypeOptionNotFoundException("Bid security type option not found with id: " + id);
        }
        bidSecurityTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple BidSecurityType options by their IDs.
     *
     * @param idList                                  - List of BidSecurityType option IDs to be soft deleted
     * @return                                        - List of soft-deleted BidSecurityTypeOption objects
     * @throws BidSecurityTypeOptionNotFoundException - if any BidSecurityType option ID are not found
     */
    @Transactional
    public List<BidSecurityTypeOptionModel> softDeleteMany(List<String> idList) {
        List<BidSecurityTypeOptionModel> bidSecurityTypeOptionModelList = bidSecurityTypeOptionRepository.findAllById(idList);
        if (bidSecurityTypeOptionModelList.isEmpty()) {
            throw new BidSecurityTypeOptionNotFoundException("No bid security type options found with provided IDList: " + idList);
        }
        for (BidSecurityTypeOptionModel model : bidSecurityTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return bidSecurityTypeOptionRepository.saveAll(bidSecurityTypeOptionModelList);
    }

    /**
     * Hard deletes multiple BidSecurityType options by IDs.
     *
     * @param idList     - List of BidSecurityType option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        bidSecurityTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all BidSecurityType options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        bidSecurityTypeOptionRepository.deleteAll();
    }
}