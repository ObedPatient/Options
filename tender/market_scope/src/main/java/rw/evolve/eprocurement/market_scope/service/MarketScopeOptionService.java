/**
 * Service for managing MarketScopeOption model.
 * Provides functionality to create, read, update, and delete MarketScopeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.market_scope.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.market_scope.exception.MarketScopeOptionAlreadyExistException;
import rw.evolve.eprocurement.market_scope.exception.MarketScopeOptionNotFoundException;
import rw.evolve.eprocurement.market_scope.model.MarketScopeOptionModel;
import rw.evolve.eprocurement.market_scope.repository.MarketScopeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class MarketScopeOptionService {

    @Autowired
    private MarketScopeOptionRepository marketScopeOptionRepository;

    /**
     * Creates a single MarketScope option model with a generated ID.
     *
     * @param marketScopeOptionModel                  - the MarketScopeOptionModel to be created
     * @return                                        - the saved MarketScopeOption model
     * @throws MarketScopeOptionAlreadyExistException - if a MarketScopeOption with the same name exists
     */
    @Transactional
    public MarketScopeOptionModel save(MarketScopeOptionModel marketScopeOptionModel) {
        if (marketScopeOptionModel == null) {
            throw new NullPointerException("Market scope option cannot be null");
        }
        if (marketScopeOptionRepository.existsByName(marketScopeOptionModel.getName())) {
            throw new MarketScopeOptionAlreadyExistException("Market scope option already exists: " + marketScopeOptionModel.getName());
        }
        return marketScopeOptionRepository.save(marketScopeOptionModel);
    }

    /**
     * Creates multiple MarketScope Option model, each with a unique generated ID.
     *
     * @param marketScopeOptionModelList - the list of MarketScope option model to be created
     * @return                           - a list of saved MarketScope Option model
     * @throws NullPointerException      - if the input list is null
     */
    @Transactional
    public List<MarketScopeOptionModel> saveMany(List<MarketScopeOptionModel> marketScopeOptionModelList) {
        if (marketScopeOptionModelList == null || marketScopeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Market scope option model list cannot be null or empty");
        }
        for (MarketScopeOptionModel marketScopeOptionModel : marketScopeOptionModelList) {
            if (marketScopeOptionRepository.existsByName(marketScopeOptionModel.getName())) {
                throw new MarketScopeOptionAlreadyExistException("Market scope option already exists: " + marketScopeOptionModel.getName());
            }
        }
        return marketScopeOptionRepository.saveAll(marketScopeOptionModelList);
    }

    /**
     * Retrieves a single MarketScope option model by its ID.
     * Throws a MarketScopeOptionNotFoundException if the MarketScope option is not found or has been deleted.
     *
     * @param id                                  - the ID of the MarketScope option to retrieve
     * @return                                    - the MarketScope option model if found and not deleted
     * @throws MarketScopeOptionNotFoundException - if the MarketScope option is not found
     * @throws NullPointerException               - if MarketScope option ID is null
     */
    @Transactional
    public MarketScopeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Market scope option ID cannot be null");
        }
        MarketScopeOptionModel marketScopeOptionModel = marketScopeOptionRepository.findById(id)
                .orElseThrow(() -> new MarketScopeOptionNotFoundException("Market scope option not found with ID: " + id));
        if (marketScopeOptionModel.getDeletedAt() != null) {
            throw new MarketScopeOptionNotFoundException("Market scope option not found with ID: " + id);
        }
        return marketScopeOptionModel;
    }

    /**
     * Retrieves a list of MarketScopeOption model list based on the provided MarketScopeOption ID.
     *
     * @param marketScopeOptionIdList    - A list of MarketScopeOption ID to retrieve
     * @return                           - A list of MarketScopeOptionModel objects that are not marked as deleted
     * @throws NullPointerException      - if a MarketScopeOption ID list is null
     */
    @Transactional
    public List<MarketScopeOptionModel> readMany(List<String> marketScopeOptionIdList) {
        if (marketScopeOptionIdList == null || marketScopeOptionIdList.isEmpty()) {
            throw new NullPointerException("Market scope option ID list cannot be null");
        }
        List<MarketScopeOptionModel> modelList = new ArrayList<>();
        for (String id : marketScopeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Market scope option ID cannot be null");
            }
            MarketScopeOptionModel marketScopeOptionModel = marketScopeOptionRepository.findById(id)
                    .orElse(null);
            if (marketScopeOptionModel == null)
                continue;
            if (marketScopeOptionModel.getDeletedAt() == null) {
                modelList.add(marketScopeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all MarketScope options that are not marked as deleted
     *
     * @return         - a List of MarketScope option model where deletedAt is null
     */
    @Transactional
    public List<MarketScopeOptionModel> readAll() {
        return marketScopeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all MarketScope Option model, including those marked as deleted.
     *
     * @return            - A list of all MarketScopeOptionModel objects
     */
    @Transactional
    public List<MarketScopeOptionModel> hardReadAll() {
        return marketScopeOptionRepository.findAll();
    }

    /**
     * Updates a single MarketScope Option model identified by the provided ID.
     *
     * @param model                               - The MarketScopeOptionModel containing updated data
     * @return                                    - The updated MarketScopeOptionModel
     * @throws MarketScopeOptionNotFoundException - if market scope option is not found
     */
    @Transactional
    public MarketScopeOptionModel updateOne(MarketScopeOptionModel model) {
        MarketScopeOptionModel existing = marketScopeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new MarketScopeOptionNotFoundException("Market scope option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new MarketScopeOptionNotFoundException("Market scope option with ID: " + model.getId() + " is not found");
        }
        return marketScopeOptionRepository.save(model);
    }

    /**
     * Updates multiple market scope option model in a transactional manner.
     *
     * @param modelList                           - List of MarketScopeOptionModel objects containing updated data
     * @return                                    - List of updated MarketScopeOptionModel objects
     * @throws MarketScopeOptionNotFoundException - if market scope option is not found
     */
    @Transactional
    public List<MarketScopeOptionModel> updateMany(List<MarketScopeOptionModel> modelList) {
        for (MarketScopeOptionModel model : modelList) {
            MarketScopeOptionModel existing = marketScopeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new MarketScopeOptionNotFoundException("Market scope option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new MarketScopeOptionNotFoundException("Market scope option with ID: " + model.getId() + " is not found");
            }
        }
        return marketScopeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single MarketScope option model by ID, including deleted ones.
     *
     * @param model                             - The MarketScopeOptionModel containing updated data
     * @return                                  - The updated MarketScopeOptionModel
     */
    @Transactional
    public MarketScopeOptionModel hardUpdate(MarketScopeOptionModel model) {
        return marketScopeOptionRepository.save(model);
    }

    /**
     * Updates multiple MarketScopeOption modelList by their IDs, including deleted ones.
     *
     * @param marketScopeOptionModelList      - List of MarketScopeOptionModel objects containing updated data
     * @return                                - List of updated MarketScopeOptionModel objects
     */
    @Transactional
    public List<MarketScopeOptionModel> hardUpdateAll(List<MarketScopeOptionModel> marketScopeOptionModelList) {
        return marketScopeOptionRepository.saveAll(marketScopeOptionModelList);
    }

    /**
     * Soft deletes a MarketScope option by ID.
     *
     * @return                                    - The soft-deleted MarketScopeOptionModel
     * @throws MarketScopeOptionNotFoundException - if market scope option id is not found
     */
    @Transactional
    public MarketScopeOptionModel softDelete(String id) {
        MarketScopeOptionModel marketScopeOptionModel = marketScopeOptionRepository.findById(id)
                .orElseThrow(() -> new MarketScopeOptionNotFoundException("Market scope option not found with id: " + id));
        marketScopeOptionModel.setDeletedAt(LocalDateTime.now());
        return marketScopeOptionRepository.save(marketScopeOptionModel);
    }

    /**
     * Hard deletes a MarketScope option by ID.
     *
     * @param id                                  - ID of the MarketScope option to hard delete
     * @throws NullPointerException               - if the MarketScope option ID is null
     * @throws MarketScopeOptionNotFoundException - if the MarketScope option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Market scope option ID cannot be null");
        }
        if (!marketScopeOptionRepository.existsById(id)) {
            throw new MarketScopeOptionNotFoundException("Market scope option not found with id: " + id);
        }
        marketScopeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple MarketScope options by their IDs.
     *
     * @param idList                              - List of MarketScope option IDs to be soft deleted
     * @return                                    - List of soft-deleted MarketScopeOption objects
     * @throws MarketScopeOptionNotFoundException - if any MarketScope option ID are not found
     */
    @Transactional
    public List<MarketScopeOptionModel> softDeleteMany(List<String> idList) {
        List<MarketScopeOptionModel> marketScopeOptionModelList = marketScopeOptionRepository.findAllById(idList);
        if (marketScopeOptionModelList.isEmpty()) {
            throw new MarketScopeOptionNotFoundException("No market scope options found with provided IDList: " + idList);
        }
        for (MarketScopeOptionModel model : marketScopeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return marketScopeOptionRepository.saveAll(marketScopeOptionModelList);
    }

    /**
     * Hard deletes multiple MarketScope options by IDs.
     *
     * @param idList     - List of MarketScope option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        marketScopeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all MarketScope options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        marketScopeOptionRepository.deleteAll();
    }
}