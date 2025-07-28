/**
 * Service for managing CurrencyOption model.
 * Provides functionality to create, read, update, and delete CurrencyOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.currency_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.currency_option.exception.CurrencyOptionAlreadyExistException;
import rw.evolve.eprocurement.currency_option.exception.CurrencyOptionNotFoundException;
import rw.evolve.eprocurement.currency_option.model.CurrencyOptionModel;
import rw.evolve.eprocurement.currency_option.repository.CurrencyOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyOptionService {

    @Autowired
    private CurrencyOptionRepository currencyOptionRepository;

    /**
     * Creates a single CurrencyOption model with a generated ID.
     *
     * @param currencyOptionModel                  - the CurrencyOptionModel to be created
     * @return                                     - the saved CurrencyOption model
     * @throws CurrencyOptionAlreadyExistException - if a CurrencyOption with the same name exists
     */
    @Transactional
    public CurrencyOptionModel save(CurrencyOptionModel currencyOptionModel) {
        if (currencyOptionModel == null) {
            throw new NullPointerException("Currency option cannot be null");
        }
        if (currencyOptionRepository.existsByName(currencyOptionModel.getName())) {
            throw new CurrencyOptionAlreadyExistException("Currency option already exists: " + currencyOptionModel.getName());
        }
        return currencyOptionRepository.save(currencyOptionModel);
    }

    /**
     * Creates multiple CurrencyOption model, each with a unique generated ID.
     *
     * @param currencyOptionModelList - the list of CurrencyOption models to be created
     * @return                        - a list of saved CurrencyOption models
     * @throws NullPointerException   - if the input list is null
     */
    @Transactional
    public List<CurrencyOptionModel> saveMany(List<CurrencyOptionModel> currencyOptionModelList) {
        if (currencyOptionModelList == null || currencyOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Currency option model list cannot be null or empty");
        }
        for (CurrencyOptionModel currencyOptionModel : currencyOptionModelList) {
            if (currencyOptionRepository.existsByName(currencyOptionModel.getName())) {
                throw new CurrencyOptionAlreadyExistException("Currency option already exists: " + currencyOptionModel.getName());
            }
        }
        return currencyOptionRepository.saveAll(currencyOptionModelList);
    }

    /**
     * Retrieves a single CurrencyOption model by its ID.
     * Throws a CurrencyOptionNotFoundException if the CurrencyOption is not found or has been deleted.
     *
     * @param id                               - the ID of the CurrencyOption to retrieve
     * @return                                 - the CurrencyOption model if found and not deleted
     * @throws CurrencyOptionNotFoundException - if the CurrencyOption is not found
     * @throws NullPointerException            - if CurrencyOption ID is null
     */
    @Transactional
    public CurrencyOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Currency option ID cannot be null");
        }
        CurrencyOptionModel currencyOptionModel = currencyOptionRepository.findById(id)
                .orElseThrow(() -> new CurrencyOptionNotFoundException("Currency option not found with ID: " + id));
        if (currencyOptionModel.getDeletedAt() != null) {
            throw new CurrencyOptionNotFoundException("Currency option not found with ID: " + id);
        }
        return currencyOptionModel;
    }

    /**
     * Retrieves a list of CurrencyOption model based on the provided CurrencyOption IDs.
     *
     * @param currencyOptionIdList    - A list of CurrencyOption IDs to retrieve
     * @return                        - A list of CurrencyOptionModel objects that are not marked as deleted
     * @throws NullPointerException   - if a CurrencyOption ID list is null
     */
    @Transactional
    public List<CurrencyOptionModel> readMany(List<String> currencyOptionIdList) {
        if (currencyOptionIdList == null || currencyOptionIdList.isEmpty()) {
            throw new NullPointerException("Currency option ID list cannot be null");
        }
        List<CurrencyOptionModel> modelList = new ArrayList<>();
        for (String id : currencyOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Currency option ID cannot be null");
            }
            CurrencyOptionModel currencyOptionModel = currencyOptionRepository.findById(id)
                    .orElse(null);
            if (currencyOptionModel == null)
                continue;
            if (currencyOptionModel.getDeletedAt() == null) {
                modelList.add(currencyOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all CurrencyOption model that are not marked as deleted.
     *
     * @return - a List of CurrencyOption models where deletedAt is null
     */
    @Transactional
    public List<CurrencyOptionModel> readAll() {
        return currencyOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all CurrencyOption model, including those marked as deleted.
     *
     * @return - A list of all CurrencyOptionModel objects
     */
    @Transactional
    public List<CurrencyOptionModel> hardReadAll() {
        return currencyOptionRepository.findAll();
    }

    /**
     * Updates a single CurrencyOption model identified by the provided ID.
     *
     * @param model                            - The CurrencyOptionModel containing updated data
     * @return                                 - The updated CurrencyOptionModel
     * @throws CurrencyOptionNotFoundException - if CurrencyOption is not found or marked as deleted
     */
    @Transactional
    public CurrencyOptionModel updateOne(CurrencyOptionModel model) {
        CurrencyOptionModel existing = currencyOptionRepository.findById(model.getId())
                .orElseThrow(() -> new CurrencyOptionNotFoundException("Currency option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new CurrencyOptionNotFoundException("Currency option with ID: " + model.getId() + " is not found");
        }
        return currencyOptionRepository.save(model);
    }

    /**
     * Updates multiple CurrencyOption models in a transactional manner.
     *
     * @param modelList                        - List of CurrencyOptionModel objects containing updated data
     * @return                                 - List of updated CurrencyOptionModel objects
     * @throws CurrencyOptionNotFoundException - if a CurrencyOption is not found or marked as deleted
     */
    @Transactional
    public List<CurrencyOptionModel> updateMany(List<CurrencyOptionModel> modelList) {
        for (CurrencyOptionModel model : modelList) {
            CurrencyOptionModel existing = currencyOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new CurrencyOptionNotFoundException("Currency option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new CurrencyOptionNotFoundException("Currency option with ID: " + model.getId() + " is not found");
            }
        }
        return currencyOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single CurrencyOption model by ID, including deleted ones.
     *
     * @param model                            - The CurrencyOptionModel containing updated data
     * @return                                 - The updated CurrencyOptionModel
     * @throws CurrencyOptionNotFoundException - if CurrencyOption is not found
     */
    @Transactional
    public CurrencyOptionModel hardUpdate(CurrencyOptionModel model) {
        return currencyOptionRepository.save(model);
    }

    /**
     * Updates multiple CurrencyOption models by their IDs, including deleted ones.
     *
     * @param currencyOptionModelList - List of CurrencyOptionModel objects containing updated data
     * @return                        - List of updated CurrencyOptionModel objects
     */
    @Transactional
    public List<CurrencyOptionModel> hardUpdateAll(List<CurrencyOptionModel> currencyOptionModelList) {
        return currencyOptionRepository.saveAll(currencyOptionModelList);
    }

    /**
     * Soft deletes a CurrencyOption by ID.
     *
     * @param id                               - The ID of the CurrencyOption to soft delete
     * @return                                 - The soft-deleted CurrencyOptionModel
     * @throws CurrencyOptionNotFoundException - if CurrencyOption ID is not found
     */
    @Transactional
    public CurrencyOptionModel softDelete(String id) {
        CurrencyOptionModel currencyOptionModel = currencyOptionRepository.findById(id)
                .orElseThrow(() -> new CurrencyOptionNotFoundException("Currency option not found with id: " + id));
        currencyOptionModel.setDeletedAt(LocalDateTime.now());
        return currencyOptionRepository.save(currencyOptionModel);
    }

    /**
     * Hard deletes a CurrencyOption by ID.
     *
     * @param id                               - ID of the CurrencyOption to hard delete
     * @throws NullPointerException            - if the CurrencyOption ID is null
     * @throws CurrencyOptionNotFoundException - if the CurrencyOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Currency option ID cannot be null");
        }
        if (!currencyOptionRepository.existsById(id)) {
            throw new CurrencyOptionNotFoundException("Currency option not found with id: " + id);
        }
        currencyOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple CurrencyOptions by their IDs.
     *
     * @param idList                           - List of CurrencyOption IDs to be soft deleted
     * @return                                 - List of soft-deleted CurrencyOption objects
     * @throws CurrencyOptionNotFoundException - if any CurrencyOption IDs are not found
     */
    @Transactional
    public List<CurrencyOptionModel> softDeleteMany(List<String> idList) {
        List<CurrencyOptionModel> currencyOptionModelList = currencyOptionRepository.findAllById(idList);
        if (currencyOptionModelList.isEmpty()) {
            throw new CurrencyOptionNotFoundException("No currency options found with provided IDList: " + idList);
        }
        for (CurrencyOptionModel model : currencyOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return currencyOptionRepository.saveAll(currencyOptionModelList);
    }

    /**
     * Hard deletes multiple CurrencyOptions by IDs.
     *
     * @param idList - List of CurrencyOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        currencyOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all CurrencyOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        currencyOptionRepository.deleteAll();
    }
}
