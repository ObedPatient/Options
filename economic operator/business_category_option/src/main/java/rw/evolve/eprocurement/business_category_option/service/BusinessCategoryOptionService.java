package rw.evolve.eprocurement.business_category_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.business_category_option.exception.BusinessCategoryOptionAlreadyExistException;
import rw.evolve.eprocurement.business_category_option.exception.BusinessCategoryOptionNotFoundException;
import rw.evolve.eprocurement.business_category_option.model.BusinessCategoryOptionModel;
import rw.evolve.eprocurement.business_category_option.repository.BusinessCategoryOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing BusinessCategoryOption model.
 * Provides functionality to create, read, update, and delete BusinessCategoryOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
public class BusinessCategoryOptionService {

    @Autowired
    private BusinessCategoryOptionRepository businessCategoryOptionRepository;

    /**
     * Creates a single BusinessCategoryOption model with a generated ID.
     *
     * @param businessCategoryOptionModel                  - the BusinessCategoryOptionModel to be created
     * @return                                             - the saved BusinessCategoryOption model
     * @throws BusinessCategoryOptionAlreadyExistException - if a BusinessCategoryOption with the same name exists
     */
    @Transactional
    public BusinessCategoryOptionModel save(BusinessCategoryOptionModel businessCategoryOptionModel) {
        if (businessCategoryOptionModel == null) {
            throw new NullPointerException("Business category option cannot be null");
        }
        if (businessCategoryOptionRepository.existsByName(businessCategoryOptionModel.getName())) {
            throw new BusinessCategoryOptionAlreadyExistException("Business category option already exists: " + businessCategoryOptionModel.getName());
        }
        return businessCategoryOptionRepository.save(businessCategoryOptionModel);
    }

    /**
     * Creates multiple BusinessCategoryOption model, each with a unique generated ID.
     *
     * @param businessCategoryOptionModelList - the list of BusinessCategoryOption models to be created
     * @return                                - a list of saved BusinessCategoryOption models
     * @throws NullPointerException           - if the input list is null
     */
    @Transactional
    public List<BusinessCategoryOptionModel> saveMany(List<BusinessCategoryOptionModel> businessCategoryOptionModelList) {
        if (businessCategoryOptionModelList == null || businessCategoryOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Business category option model list cannot be null or empty");
        }
        for (BusinessCategoryOptionModel businessCategoryOptionModel : businessCategoryOptionModelList) {
            if (businessCategoryOptionRepository.existsByName(businessCategoryOptionModel.getName())) {
                throw new BusinessCategoryOptionAlreadyExistException("Business category option already exists: " + businessCategoryOptionModel.getName());
            }
        }
        return businessCategoryOptionRepository.saveAll(businessCategoryOptionModelList);
    }

    /**
     * Retrieves a single BusinessCategoryOption model by its ID.
     * Throws a BusinessCategoryOptionNotFoundException if the BusinessCategoryOption is not found or has been deleted.
     *
     * @param id                                       - the ID of the BusinessCategoryOption to retrieve
     * @return                                         - the BusinessCategoryOption model if found and not deleted
     * @throws BusinessCategoryOptionNotFoundException - if the BusinessCategoryOption is not found
     * @throws NullPointerException                    - if BusinessCategoryOption ID is null
     */
    @Transactional
    public BusinessCategoryOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Business category option ID cannot be null");
        }
        BusinessCategoryOptionModel businessCategoryOptionModel = businessCategoryOptionRepository.findById(id)
                .orElseThrow(() -> new BusinessCategoryOptionNotFoundException("Business category option not found with ID: " + id));
        if (businessCategoryOptionModel.getDeletedAt() != null) {
            throw new BusinessCategoryOptionNotFoundException("Business category option not found with ID: " + id);
        }
        return businessCategoryOptionModel;
    }

    /**
     * Retrieves a list of BusinessCategoryOption model based on the provided BusinessCategoryOption IDs.
     *
     * @param businessCategoryOptionIdList    - A list of BusinessCategoryOption IDs to retrieve
     * @return                                - A list of BusinessCategoryOptionModel objects that are not marked as deleted
     * @throws NullPointerException           - if a BusinessCategoryOption ID list is null
     */
    @Transactional
    public List<BusinessCategoryOptionModel> readMany(List<String> businessCategoryOptionIdList) {
        if (businessCategoryOptionIdList == null || businessCategoryOptionIdList.isEmpty()) {
            throw new NullPointerException("Business category option ID list cannot be null");
        }
        List<BusinessCategoryOptionModel> modelList = new ArrayList<>();
        for (String id : businessCategoryOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Business category option ID cannot be null");
            }
            BusinessCategoryOptionModel businessCategoryOptionModel = businessCategoryOptionRepository.findById(id)
                    .orElse(null);
            if (businessCategoryOptionModel == null)
                continue;
            if (businessCategoryOptionModel.getDeletedAt() == null) {
                modelList.add(businessCategoryOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all BusinessCategoryOption models that are not marked as deleted.
     *
     * @return - a List of BusinessCategoryOption model where deletedAt is null
     */
    @Transactional
    public List<BusinessCategoryOptionModel> readAll() {
        return businessCategoryOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all BusinessCategoryOption model, including those marked as deleted.
     *
     * @return - A list of all BusinessCategoryOptionModel objects
     */
    @Transactional
    public List<BusinessCategoryOptionModel> hardReadAll() {
        return businessCategoryOptionRepository.findAll();
    }

    /**
     * Updates a single BusinessCategoryOption model identified by the provided ID.
     *
     * @param model                                    - The BusinessCategoryOptionModel containing updated data
     * @return                                         - The updated BusinessCategoryOptionModel
     * @throws BusinessCategoryOptionNotFoundException - if BusinessCategoryOption is not found or marked as deleted
     */
    @Transactional
    public BusinessCategoryOptionModel updateOne(BusinessCategoryOptionModel model) {
        BusinessCategoryOptionModel existing = businessCategoryOptionRepository.findById(model.getId())
                .orElseThrow(() -> new BusinessCategoryOptionNotFoundException("Business category option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new BusinessCategoryOptionNotFoundException("Business category option with ID: " + model.getId() + " is not found");
        }
        return businessCategoryOptionRepository.save(model);
    }

    /**
     * Updates multiple BusinessCategoryOption models in a transactional manner.
     *
     * @param modelList                                - List of BusinessCategoryOptionModel objects containing updated data
     * @return                                         - List of updated BusinessCategoryOptionModel objects
     * @throws BusinessCategoryOptionNotFoundException - if a BusinessCategoryOption is not found or marked as deleted
     */
    @Transactional
    public List<BusinessCategoryOptionModel> updateMany(List<BusinessCategoryOptionModel> modelList) {
        for (BusinessCategoryOptionModel model : modelList) {
            BusinessCategoryOptionModel existing = businessCategoryOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new BusinessCategoryOptionNotFoundException("Business category option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new BusinessCategoryOptionNotFoundException("Business category option with ID: " + model.getId() + " is not found");
            }
        }
        return businessCategoryOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single BusinessCategoryOption model by ID, including deleted ones.
     *
     * @param model                                    - The BusinessCategoryOptionModel containing updated data
     * @return                                         - The updated BusinessCategoryOptionModel
     */
    @Transactional
    public BusinessCategoryOptionModel hardUpdate(BusinessCategoryOptionModel model) {
        return businessCategoryOptionRepository.save(model);
    }

    /**
     * Updates multiple BusinessCategoryOption models by their IDs, including deleted ones.
     *
     * @param businessCategoryOptionModelList - List of BusinessCategoryOptionModel objects containing updated data
     * @return                                - List of updated BusinessCategoryOptionModel objects
     */
    @Transactional
    public List<BusinessCategoryOptionModel> hardUpdateAll(List<BusinessCategoryOptionModel> businessCategoryOptionModelList) {
        return businessCategoryOptionRepository.saveAll(businessCategoryOptionModelList);
    }

    /**
     * Soft deletes a BusinessCategoryOption by ID.
     *
     * @param id                                       - The ID of the BusinessCategoryOption to soft delete
     * @return                                         - The soft-deleted BusinessCategoryOptionModel
     * @throws BusinessCategoryOptionNotFoundException - if BusinessCategoryOption ID is not found
     */
    @Transactional
    public BusinessCategoryOptionModel softDelete(String id) {
        BusinessCategoryOptionModel businessCategoryOptionModel = businessCategoryOptionRepository.findById(id)
                .orElseThrow(() -> new BusinessCategoryOptionNotFoundException("Business category option not found with id: " + id));
        businessCategoryOptionModel.setDeletedAt(LocalDateTime.now());
        return businessCategoryOptionRepository.save(businessCategoryOptionModel);
    }

    /**
     * Hard deletes a BusinessCategoryOption by ID.
     *
     * @param id                                       - ID of the BusinessCategoryOption to hard delete
     * @throws NullPointerException                    - if the BusinessCategoryOption ID is null
     * @throws BusinessCategoryOptionNotFoundException - if the BusinessCategoryOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Business category option ID cannot be null");
        }
        if (!businessCategoryOptionRepository.existsById(id)) {
            throw new BusinessCategoryOptionNotFoundException("Business category option not found with id: " + id);
        }
        businessCategoryOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple BusinessCategoryOptions by their IDs.
     *
     * @param idList                                   - List of BusinessCategoryOption IDs to be soft deleted
     * @return                                         - List of soft-deleted BusinessCategoryOption objects
     * @throws BusinessCategoryOptionNotFoundException - if any BusinessCategoryOption IDs are not found
     */
    @Transactional
    public List<BusinessCategoryOptionModel> softDeleteMany(List<String> idList) {
        List<BusinessCategoryOptionModel> businessCategoryOptionModelList = businessCategoryOptionRepository.findAllById(idList);
        if (businessCategoryOptionModelList.isEmpty()) {
            throw new BusinessCategoryOptionNotFoundException("No business category options found with provided IDList: " + idList);
        }
        for (BusinessCategoryOptionModel model : businessCategoryOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return businessCategoryOptionRepository.saveAll(businessCategoryOptionModelList);
    }

    /**
     * Hard deletes multiple BusinessCategoryOption by ID.
     *
     * @param idList - List of BusinessCategoryOption ID to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        businessCategoryOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all BusinessCategoryOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        businessCategoryOptionRepository.deleteAll();
    }
}