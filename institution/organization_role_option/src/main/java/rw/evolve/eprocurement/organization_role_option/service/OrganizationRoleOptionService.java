/**
 * Service for managing OrganizationRoleOption model.
 * Provides functionality to create, read, update, and delete OrganizationRoleOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.organization_role_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.organization_role_option.exception.OrganizationRoleOptionAlreadyExistException;
import rw.evolve.eprocurement.organization_role_option.exception.OrganizationRoleOptionNotFoundException;
import rw.evolve.eprocurement.organization_role_option.model.OrganizationRoleOptionModel;
import rw.evolve.eprocurement.organization_role_option.repository.OrganizationRoleOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class OrganizationRoleOptionService {

    private OrganizationRoleOptionRepository organizationRoleOptionRepository;

    /**
     * Creates a single OrganizationRoleOption model with a generated ID.
     *
     * @param organizationRoleOptionModel                  - the OrganizationRoleOptionModel to be created
     * @return                                             - the saved OrganizationRoleOption model
     * @throws OrganizationRoleOptionAlreadyExistException - if an OrganizationRoleOption with the same name exists
     */
    @Transactional
    public OrganizationRoleOptionModel save(OrganizationRoleOptionModel organizationRoleOptionModel) {
        if (organizationRoleOptionModel == null) {
            throw new NullPointerException("OrganizationRoleOption cannot be null");
        }
        if (organizationRoleOptionRepository.existsByName(organizationRoleOptionModel.getName())) {
            throw new OrganizationRoleOptionAlreadyExistException("OrganizationRoleOption already exists: " + organizationRoleOptionModel.getName());
        }
        return organizationRoleOptionRepository.save(organizationRoleOptionModel);
    }

    /**
     * Creates multiple OrganizationRoleOption model, each with a unique generated ID.
     *
     * @param organizationRoleOptionModelList - the list of OrganizationRoleOption models to be created
     * @return                                - a list of saved OrganizationRoleOption models
     * @throws NullPointerException           - if the input list is null
     */
    @Transactional
    public List<OrganizationRoleOptionModel> saveMany(List<OrganizationRoleOptionModel> organizationRoleOptionModelList) {
        if (organizationRoleOptionModelList == null || organizationRoleOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("OrganizationRoleOption model list cannot be null or empty");
        }
        for (OrganizationRoleOptionModel organizationRoleOptionModel : organizationRoleOptionModelList) {
            if (organizationRoleOptionRepository.existsByName(organizationRoleOptionModel.getName())) {
                throw new OrganizationRoleOptionAlreadyExistException("OrganizationRoleOption already exists: " + organizationRoleOptionModel.getName());
            }
        }
        return organizationRoleOptionRepository.saveAll(organizationRoleOptionModelList);
    }

    /**
     * Retrieves a single OrganizationRoleOption model by its ID.
     * Throws an OrganizationRoleOptionNotFoundException if the OrganizationRoleOption is not found or has been deleted.
     *
     * @param id                                       - the ID of the OrganizationRoleOption to retrieve
     * @return                                         - the OrganizationRoleOption model if found and not deleted
     * @throws OrganizationRoleOptionNotFoundException - if the OrganizationRoleOption is not found
     * @throws NullPointerException                    - if OrganizationRoleOption ID is null
     */
    @Transactional
    public OrganizationRoleOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("OrganizationRoleOption ID cannot be null");
        }
        OrganizationRoleOptionModel organizationRoleOptionModel = organizationRoleOptionRepository.findById(id)
                .orElseThrow(() -> new OrganizationRoleOptionNotFoundException("OrganizationRoleOption not found with ID: " + id));
        if (organizationRoleOptionModel.getDeletedAt() != null) {
            throw new OrganizationRoleOptionNotFoundException("OrganizationRoleOption not found with ID: " + id);
        }
        return organizationRoleOptionModel;
    }

    /**
     * Retrieves a list of OrganizationRoleOption models based on the provided OrganizationRoleOption IDs.
     *
     * @param organizationRoleOptionIdList - A list of OrganizationRoleOption IDs to retrieve
     * @return                             - A list of OrganizationRoleOptionModel objects that are not marked as deleted
     * @throws NullPointerException        - if an OrganizationRoleOption ID list is null
     */
    @Transactional
    public List<OrganizationRoleOptionModel> readMany(List<String> organizationRoleOptionIdList) {
        if (organizationRoleOptionIdList == null || organizationRoleOptionIdList.isEmpty()) {
            throw new NullPointerException("OrganizationRoleOption ID list cannot be null");
        }
        List<OrganizationRoleOptionModel> modelList = new ArrayList<>();
        for (String id : organizationRoleOptionIdList) {
            if (id == null) {
                throw new NullPointerException("OrganizationRoleOption ID cannot be null");
            }
            OrganizationRoleOptionModel organizationRoleOptionModel = organizationRoleOptionRepository.findById(id)
                    .orElse(null);
            if (organizationRoleOptionModel == null)
                continue;
            if (organizationRoleOptionModel.getDeletedAt() == null) {
                modelList.add(organizationRoleOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all OrganizationRoleOption that are not marked as deleted
     *
     * @return - a List of OrganizationRoleOption model where deletedAt is null
     */
    @Transactional
    public List<OrganizationRoleOptionModel> readAll() {
        return organizationRoleOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all OrganizationRoleOption model, including those marked as deleted.
     *
     * @return - A list of all OrganizationRoleOptionModel objects
     */
    @Transactional
    public List<OrganizationRoleOptionModel> hardReadAll() {
        return organizationRoleOptionRepository.findAll();
    }

    /**
     * Updates a single OrganizationRoleOption model identified by the provided ID.
     *
     * @param model                                    - The OrganizationRoleOptionModel containing updated data
     * @return                                         - The updated OrganizationRoleOptionModel
     * @throws OrganizationRoleOptionNotFoundException - if OrganizationRoleOption is not found or marked as deleted
     */
    @Transactional
    public OrganizationRoleOptionModel updateOne(OrganizationRoleOptionModel model) {
        OrganizationRoleOptionModel existing = organizationRoleOptionRepository.findById(model.getId())
                .orElseThrow(() -> new OrganizationRoleOptionNotFoundException("OrganizationRoleOption not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new OrganizationRoleOptionNotFoundException("OrganizationRoleOption with ID: " + model.getId() + " is not found");
        }
        return organizationRoleOptionRepository.save(model);
    }

    /**
     * Updates multiple OrganizationRoleOption model.
     *
     * @param modelList                                - List of OrganizationRoleOptionModel objects containing updated data
     * @return                                         - List of updated OrganizationRoleOptionModel objects
     * @throws OrganizationRoleOptionNotFoundException - if an OrganizationRoleOption is not found or marked as deleted
     */
    @Transactional
    public List<OrganizationRoleOptionModel> updateMany(List<OrganizationRoleOptionModel> modelList) {
        for (OrganizationRoleOptionModel model : modelList) {
            OrganizationRoleOptionModel existing = organizationRoleOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new OrganizationRoleOptionNotFoundException("OrganizationRoleOption not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new OrganizationRoleOptionNotFoundException("OrganizationRoleOption with ID: " + model.getId() + " is not found");
            }
        }
        return organizationRoleOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single OrganizationRoleOption model by ID, including deleted ones.
     *
     * @param model                                    - The OrganizationRoleOptionModel containing updated data
     * @return                                         - The updated OrganizationRoleOptionModel
     * @throws OrganizationRoleOptionNotFoundException - if OrganizationRoleOption is not found
     */
    @Transactional
    public OrganizationRoleOptionModel hardUpdate(OrganizationRoleOptionModel model) {
        return organizationRoleOptionRepository.save(model);
    }

    /**
     * Updates multiple OrganizationRoleOption models by their IDs, including deleted ones.
     *
     * @param organizationRoleOptionModelList - List of OrganizationRoleOptionModel objects containing updated data
     * @return                                - List of updated OrganizationRoleOptionModel objects
     */
    @Transactional
    public List<OrganizationRoleOptionModel> hardUpdateAll(List<OrganizationRoleOptionModel> organizationRoleOptionModelList) {
        return organizationRoleOptionRepository.saveAll(organizationRoleOptionModelList);
    }

    /**
     * Soft deletes an OrganizationRoleOption by ID.
     *
     * @param id                                       - The ID of the OrganizationRoleOption to soft delete
     * @return                                         - The soft-deleted OrganizationRoleOptionModel
     * @throws OrganizationRoleOptionNotFoundException - if OrganizationRoleOption ID is not found
     */
    @Transactional
    public OrganizationRoleOptionModel softDelete(String id) {
        OrganizationRoleOptionModel organizationRoleOptionModel = organizationRoleOptionRepository.findById(id)
                .orElseThrow(() -> new OrganizationRoleOptionNotFoundException("OrganizationRoleOption not found with id: " + id));
        organizationRoleOptionModel.setDeletedAt(LocalDateTime.now());
        return organizationRoleOptionRepository.save(organizationRoleOptionModel);
    }

    /**
     * Hard deletes an OrganizationRoleOption by ID.
     *
     * @param id                                       - ID of the OrganizationRoleOption to hard delete
     * @throws NullPointerException                    - if the OrganizationRoleOption ID is null
     * @throws OrganizationRoleOptionNotFoundException - if the OrganizationRoleOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("OrganizationRoleOption ID cannot be null");
        }
        if (!organizationRoleOptionRepository.existsById(id)) {
            throw new OrganizationRoleOptionNotFoundException("OrganizationRoleOption not found with id: " + id);
        }
        organizationRoleOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple OrganizationRoleOptions by their IDs.
     *
     * @param idList                                   - List of OrganizationRoleOption IDs to be soft deleted
     * @return                                         - List of soft-deleted OrganizationRoleOption objects
     * @throws OrganizationRoleOptionNotFoundException - if any OrganizationRoleOption IDs are not found
     */
    @Transactional
    public List<OrganizationRoleOptionModel> softDeleteMany(List<String> idList) {
        List<OrganizationRoleOptionModel> organizationRoleOptionModelList = organizationRoleOptionRepository.findAllById(idList);
        if (organizationRoleOptionModelList.isEmpty()) {
            throw new OrganizationRoleOptionNotFoundException("No OrganizationRoleOptions found with provided IDList: " + idList);
        }
        for (OrganizationRoleOptionModel model : organizationRoleOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return organizationRoleOptionRepository.saveAll(organizationRoleOptionModelList);
    }

    /**
     * Hard deletes multiple OrganizationRoleOptions by ID.
     *
     * @param idList - List of OrganizationRoleOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        organizationRoleOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all OrganizationRoleOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        organizationRoleOptionRepository.deleteAll();
    }
}