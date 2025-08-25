/**
 * Service for managing BusinessTypeOption model.
 * Provides functionality to create, read, update, and delete BusinessTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.business_type_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.business_type_option.exception.BusinessTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.business_type_option.exception.BusinessTypeOptionNotFoundException;
import rw.evolve.eprocurement.business_type_option.model.BusinessTypeOptionModel;
import rw.evolve.eprocurement.business_type_option.repository.BusinessTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class BusinessTypeOptionService {

    private BusinessTypeOptionRepository businessTypeOptionRepository;

    /**
     * Creates a single BusinessTypeOption model with a generated ID.
     *
     * @param businessTypeOptionModel                  - the BusinessTypeOptionModel to be created
     * @return                                         - the saved BusinessTypeOption model
     * @throws BusinessTypeOptionAlreadyExistException - if a BusinessTypeOption with the same name exists
     */
    @Transactional
    public BusinessTypeOptionModel save(BusinessTypeOptionModel businessTypeOptionModel) {
        if (businessTypeOptionModel == null) {
            throw new NullPointerException("Business type option cannot be null");
        }
        if (businessTypeOptionRepository.existsByName(businessTypeOptionModel.getName())) {
            throw new BusinessTypeOptionAlreadyExistException("Business type option already exists: " + businessTypeOptionModel.getName());
        }
        return businessTypeOptionRepository.save(businessTypeOptionModel);
    }

    /**
     * Creates multiple BusinessTypeOption model, each with a unique generated ID.
     *
     * @param businessTypeOptionModelList - the list of BusinessTypeOption models to be created
     * @return                            - a list of saved BusinessTypeOption models
     * @throws NullPointerException       - if the input list is null
     */
    @Transactional
    public List<BusinessTypeOptionModel> saveMany(List<BusinessTypeOptionModel> businessTypeOptionModelList) {
        if (businessTypeOptionModelList == null || businessTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Business type option model list cannot be null or empty");
        }
        for (BusinessTypeOptionModel businessTypeOptionModel : businessTypeOptionModelList) {
            if (businessTypeOptionRepository.existsByName(businessTypeOptionModel.getName())) {
                throw new BusinessTypeOptionAlreadyExistException("Business type option already exists: " + businessTypeOptionModel.getName());
            }
        }
        return businessTypeOptionRepository.saveAll(businessTypeOptionModelList);
    }

    /**
     * Retrieves a single BusinessTypeOption model by its ID.
     * Throws a BusinessTypeOptionNotFoundException if the BusinessTypeOption is not found or has been deleted.
     *
     * @param id                                   - the ID of the BusinessTypeOption to retrieve
     * @return                                     - the BusinessTypeOption model if found and not deleted
     * @throws BusinessTypeOptionNotFoundException - if the BusinessTypeOption is not found
     * @throws NullPointerException                - if BusinessTypeOption ID is null
     */
    @Transactional
    public BusinessTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Business type option ID cannot be null");
        }
        BusinessTypeOptionModel businessTypeOptionModel = businessTypeOptionRepository.findById(id)
                .orElseThrow(() -> new BusinessTypeOptionNotFoundException("Business type option not found with ID: " + id));
        if (businessTypeOptionModel.getDeletedAt() != null) {
            throw new BusinessTypeOptionNotFoundException("Business type option not found with ID: " + id);
        }
        return businessTypeOptionModel;
    }

    /**
     * Retrieves a list of BusinessTypeOption model based on the provided BusinessTypeOption IDs.
     *
     * @param businessTypeOptionIdList    - A list of BusinessTypeOption IDs to retrieve
     * @return                            - A list of BusinessTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException       - if a BusinessTypeOption ID list is null
     */
    @Transactional
    public List<BusinessTypeOptionModel> readMany(List<String> businessTypeOptionIdList) {
        if (businessTypeOptionIdList == null || businessTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Business type option ID list cannot be null");
        }
        List<BusinessTypeOptionModel> modelList = new ArrayList<>();
        for (String id : businessTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Business type option ID cannot be null");
            }
            BusinessTypeOptionModel businessTypeOptionModel = businessTypeOptionRepository.findById(id)
                    .orElse(null);
            if (businessTypeOptionModel == null)
                continue;
            if (businessTypeOptionModel.getDeletedAt() == null) {
                modelList.add(businessTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all BusinessTypeOption model that are not marked as deleted.
     *
     * @return - a List of BusinessTypeOption model where deletedAt is null
     */
    @Transactional
    public List<BusinessTypeOptionModel> readAll() {
        return businessTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all BusinessTypeOption model, including those marked as deleted.
     *
     * @return - A list of all BusinessTypeOptionModel objects
     */
    @Transactional
    public List<BusinessTypeOptionModel> hardReadAll() {
        return businessTypeOptionRepository.findAll();
    }

    /**
     * Updates a single BusinessTypeOption model identified by the provided ID.
     *
     * @param model                                - The BusinessTypeOptionModel containing updated data
     * @return                                     - The updated BusinessTypeOptionModel
     * @throws BusinessTypeOptionNotFoundException - if BusinessTypeOption is not found or marked as deleted
     */
    @Transactional
    public BusinessTypeOptionModel updateOne(BusinessTypeOptionModel model) {
        BusinessTypeOptionModel existing = businessTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new BusinessTypeOptionNotFoundException("Business type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new BusinessTypeOptionNotFoundException("Business type option with ID: " + model.getId() + " is not found");
        }
        return businessTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple BusinessTypeOption models in a transactional manner.
     *
     * @param modelList                            - List of BusinessTypeOptionModel objects containing updated data
     * @return                                     - List of updated BusinessTypeOptionModel objects
     * @throws BusinessTypeOptionNotFoundException - if a BusinessTypeOption is not found or marked as deleted
     */
    @Transactional
    public List<BusinessTypeOptionModel> updateMany(List<BusinessTypeOptionModel> modelList) {
        for (BusinessTypeOptionModel model : modelList) {
            BusinessTypeOptionModel existing = businessTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new BusinessTypeOptionNotFoundException("Business type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new BusinessTypeOptionNotFoundException("Business type option with ID: " + model.getId() + " is not found");
            }
        }
        return businessTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single BusinessTypeOption model by ID, including deleted ones.
     *
     * @param model                                - The BusinessTypeOptionModel containing updated data
     * @return                                     - The updated BusinessTypeOptionModel
     */
    @Transactional
    public BusinessTypeOptionModel hardUpdate(BusinessTypeOptionModel model) {
        return businessTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple BusinessTypeOption models by their IDs, including deleted ones.
     *
     * @param businessTypeOptionModelList - List of BusinessTypeOptionModel objects containing updated data
     * @return                            - List of updated BusinessTypeOptionModel objects
     */
    @Transactional
    public List<BusinessTypeOptionModel> hardUpdateAll(List<BusinessTypeOptionModel> businessTypeOptionModelList) {
        return businessTypeOptionRepository.saveAll(businessTypeOptionModelList);
    }

    /**
     * Soft deletes a BusinessTypeOption by ID.
     *
     * @param id                                   - The ID of the BusinessTypeOption to soft delete
     * @return                                     - The soft-deleted BusinessTypeOptionModel
     * @throws BusinessTypeOptionNotFoundException - if BusinessTypeOption ID is not found
     */
    @Transactional
    public BusinessTypeOptionModel softDelete(String id) {
        BusinessTypeOptionModel businessTypeOptionModel = businessTypeOptionRepository.findById(id)
                .orElseThrow(() -> new BusinessTypeOptionNotFoundException("Business type option not found with id: " + id));
        businessTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return businessTypeOptionRepository.save(businessTypeOptionModel);
    }

    /**
     * Hard deletes a BusinessTypeOption by ID.
     *
     * @param id                                   - ID of the BusinessTypeOption to hard delete
     * @throws NullPointerException                - if the BusinessTypeOption ID is null
     * @throws BusinessTypeOptionNotFoundException - if the BusinessTypeOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Business type option ID cannot be null");
        }
        if (!businessTypeOptionRepository.existsById(id)) {
            throw new BusinessTypeOptionNotFoundException("Business type option not found with id: " + id);
        }
        businessTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple BusinessTypeOptions by their IDs.
     *
     * @param idList                               - List of BusinessTypeOption IDs to be soft deleted
     * @return                                     - List of soft-deleted BusinessTypeOption objects
     * @throws BusinessTypeOptionNotFoundException - if any BusinessTypeOption IDs are not found
     */
    @Transactional
    public List<BusinessTypeOptionModel> softDeleteMany(List<String> idList) {
        List<BusinessTypeOptionModel> businessTypeOptionModelList = businessTypeOptionRepository.findAllById(idList);
        if (businessTypeOptionModelList.isEmpty()) {
            throw new BusinessTypeOptionNotFoundException("No business type options found with provided IDList: " + idList);
        }
        for (BusinessTypeOptionModel model : businessTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return businessTypeOptionRepository.saveAll(businessTypeOptionModelList);
    }

    /**
     * Hard deletes multiple BusinessTypeOption by ID.
     *
     * @param idList - List of BusinessTypeOption ID to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        businessTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all BusinessTypeOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        businessTypeOptionRepository.deleteAll();
    }
}