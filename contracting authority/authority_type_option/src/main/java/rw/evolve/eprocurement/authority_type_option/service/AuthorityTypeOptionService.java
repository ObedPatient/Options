/**
 * Service for managing AuthorityTypeOption model.
 * Provides functionality to create, read, update, and delete AuthorityTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.authority_type_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.authority_type_option.exception.AuthorityTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.authority_type_option.exception.AuthorityTypeOptionNotFoundException;
import rw.evolve.eprocurement.authority_type_option.model.AuthorityTypeOptionModel;
import rw.evolve.eprocurement.authority_type_option.repository.AuthorityTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorityTypeOptionService {

    @Autowired
    private AuthorityTypeOptionRepository authorityTypeOptionRepository;

    /**
     * Creates a single AuthorityTypeOption model with a generated ID.
     *
     * @param authorityTypeOptionModel                  - the AuthorityTypeOptionModel to be created
     * @return                                          - the saved AuthorityTypeOption model
     * @throws AuthorityTypeOptionAlreadyExistException - if an AuthorityTypeOption with the same name exists
     */
    @Transactional
    public AuthorityTypeOptionModel save(AuthorityTypeOptionModel authorityTypeOptionModel) {
        if (authorityTypeOptionModel == null) {
            throw new NullPointerException("Authority type option cannot be null");
        }
        if (authorityTypeOptionRepository.existsByName(authorityTypeOptionModel.getName())) {
            throw new AuthorityTypeOptionAlreadyExistException("Authority type option already exists: " + authorityTypeOptionModel.getName());
        }
        return authorityTypeOptionRepository.save(authorityTypeOptionModel);
    }

    /**
     * Creates multiple AuthorityTypeOption models, each with a unique generated ID.
     *
     * @param authorityTypeOptionModelList - the list of AuthorityTypeOption models to be created
     * @return                             - a list of saved AuthorityTypeOption models
     * @throws NullPointerException        - if the input list is null
     */
    @Transactional
    public List<AuthorityTypeOptionModel> saveMany(List<AuthorityTypeOptionModel> authorityTypeOptionModelList) {
        if (authorityTypeOptionModelList == null || authorityTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Authority type option model list cannot be null or empty");
        }
        for (AuthorityTypeOptionModel authorityTypeOptionModel : authorityTypeOptionModelList) {
            if (authorityTypeOptionRepository.existsByName(authorityTypeOptionModel.getName())) {
                throw new AuthorityTypeOptionAlreadyExistException("Authority type option already exists: " + authorityTypeOptionModel.getName());
            }
        }
        return authorityTypeOptionRepository.saveAll(authorityTypeOptionModelList);
    }

    /**
     * Retrieves a single AuthorityTypeOption model by its ID.
     * Throws an AuthorityTypeOptionNotFoundException if the AuthorityTypeOption is not found or has been deleted.
     *
     * @param id                                    - the ID of the AuthorityTypeOption to retrieve
     * @return                                      - the AuthorityTypeOption model if found and not deleted
     * @throws AuthorityTypeOptionNotFoundException - if the AuthorityTypeOption is not found
     * @throws NullPointerException                 - if AuthorityTypeOption ID is null
     */
    @Transactional
    public AuthorityTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Authority type option ID cannot be null");
        }
        AuthorityTypeOptionModel authorityTypeOptionModel = authorityTypeOptionRepository.findById(id)
                .orElseThrow(() -> new AuthorityTypeOptionNotFoundException("Authority type option not found with ID: " + id));
        if (authorityTypeOptionModel.getDeletedAt() != null) {
            throw new AuthorityTypeOptionNotFoundException("Authority type option not found with ID: " + id);
        }
        return authorityTypeOptionModel;
    }

    /**
     * Retrieves a list of AuthorityTypeOption model based on the provided AuthorityTypeOption IDs.
     *
     * @param authorityTypeOptionIdList    - A list of AuthorityTypeOption IDs to retrieve
     * @return                             - A list of AuthorityTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException        - if an AuthorityTypeOption ID list is null
     */
    @Transactional
    public List<AuthorityTypeOptionModel> readMany(List<String> authorityTypeOptionIdList) {
        if (authorityTypeOptionIdList == null || authorityTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Authority type option ID list cannot be null");
        }
        List<AuthorityTypeOptionModel> modelList = new ArrayList<>();
        for (String id : authorityTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Authority type option ID cannot be null");
            }
            AuthorityTypeOptionModel authorityTypeOptionModel = authorityTypeOptionRepository.findById(id)
                    .orElse(null);
            if (authorityTypeOptionModel == null)
                continue;
            if (authorityTypeOptionModel.getDeletedAt() == null) {
                modelList.add(authorityTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all AuthorityTypeOption model that are not marked as deleted.
     *
     * @return - a List of AuthorityTypeOption model where deletedAt is null
     */
    @Transactional
    public List<AuthorityTypeOptionModel> readAll() {
        return authorityTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all AuthorityTypeOption models, including those marked as deleted.
     *
     * @return - A list of all AuthorityTypeOptionModel objects
     */
    @Transactional
    public List<AuthorityTypeOptionModel> hardReadAll() {
        return authorityTypeOptionRepository.findAll();
    }

    /**
     * Updates a single AuthorityTypeOption model identified by the provided ID.
     *
     * @param model                                 - The AuthorityTypeOptionModel containing updated data
     * @return                                      - The updated AuthorityTypeOptionModel
     * @throws AuthorityTypeOptionNotFoundException - if AuthorityTypeOption is not found or marked as deleted
     */
    @Transactional
    public AuthorityTypeOptionModel updateOne(AuthorityTypeOptionModel model) {
        AuthorityTypeOptionModel existing = authorityTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new AuthorityTypeOptionNotFoundException("Authority type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new AuthorityTypeOptionNotFoundException("Authority type option with ID: " + model.getId() + " is not found");
        }
        return authorityTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple AuthorityTypeOption models in a transactional manner.
     *
     * @param modelList                             - List of AuthorityTypeOptionModel objects containing updated data
     * @return                                      - List of updated AuthorityTypeOptionModel objects
     * @throws AuthorityTypeOptionNotFoundException - if an AuthorityTypeOption is not found or marked as deleted
     */
    @Transactional
    public List<AuthorityTypeOptionModel> updateMany(List<AuthorityTypeOptionModel> modelList) {
        for (AuthorityTypeOptionModel model : modelList) {
            AuthorityTypeOptionModel existing = authorityTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new AuthorityTypeOptionNotFoundException("Authority type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new AuthorityTypeOptionNotFoundException("Authority type option with ID: " + model.getId() + " is not found");
            }
        }
        return authorityTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single AuthorityTypeOption model by ID, including deleted ones.
     *
     * @param model                                 - The AuthorityTypeOptionModel containing updated data
     * @return                                      - The updated AuthorityTypeOptionModel
     * @throws AuthorityTypeOptionNotFoundException - if AuthorityTypeOption is not found
     */
    @Transactional
    public AuthorityTypeOptionModel hardUpdate(AuthorityTypeOptionModel model) {
        return authorityTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple AuthorityTypeOption models by their IDs, including deleted ones.
     *
     * @param authorityTypeOptionModelList - List of AuthorityTypeOptionModel objects containing updated data
     * @return                             - List of updated AuthorityTypeOptionModel objects
     */
    @Transactional
    public List<AuthorityTypeOptionModel> hardUpdateAll(List<AuthorityTypeOptionModel> authorityTypeOptionModelList) {
        return authorityTypeOptionRepository.saveAll(authorityTypeOptionModelList);
    }

    /**
     * Soft deletes an AuthorityTypeOption by ID.
     *
     * @param id                                    - The ID of the AuthorityTypeOption to soft delete
     * @return                                      - The soft-deleted AuthorityTypeOptionModel
     * @throws AuthorityTypeOptionNotFoundException - if AuthorityTypeOption ID is not found
     */
    @Transactional
    public AuthorityTypeOptionModel softDelete(String id) {
        AuthorityTypeOptionModel authorityTypeOptionModel = authorityTypeOptionRepository.findById(id)
                .orElseThrow(() -> new AuthorityTypeOptionNotFoundException("Authority type option not found with id: " + id));
        authorityTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return authorityTypeOptionRepository.save(authorityTypeOptionModel);
    }

    /**
     * Hard deletes an AuthorityTypeOption by ID.
     *
     * @param id                                    - ID of the AuthorityTypeOption to hard delete
     * @throws NullPointerException                 - if the AuthorityTypeOption ID is null
     * @throws AuthorityTypeOptionNotFoundException - if the AuthorityTypeOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Authority type option ID cannot be null");
        }
        if (!authorityTypeOptionRepository.existsById(id)) {
            throw new AuthorityTypeOptionNotFoundException("Authority type option not found with id: " + id);
        }
        authorityTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple AuthorityTypeOption by their ID.
     *
     * @param idList                                - List of AuthorityTypeOption IDs to be soft deleted
     * @return                                      - List of soft-deleted AuthorityTypeOption objects
     * @throws AuthorityTypeOptionNotFoundException - if any AuthorityTypeOption IDs are not found
     */
    @Transactional
    public List<AuthorityTypeOptionModel> softDeleteMany(List<String> idList) {
        List<AuthorityTypeOptionModel> authorityTypeOptionModelList = authorityTypeOptionRepository.findAllById(idList);
        if (authorityTypeOptionModelList.isEmpty()) {
            throw new AuthorityTypeOptionNotFoundException("No authority type options found with provided IDList: " + idList);
        }
        for (AuthorityTypeOptionModel model : authorityTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return authorityTypeOptionRepository.saveAll(authorityTypeOptionModelList);
    }

    /**
     * Hard deletes multiple AuthorityTypeOptions by IDs.
     *
     * @param idList - List of AuthorityTypeOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        authorityTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all AuthorityTypeOptions, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        authorityTypeOptionRepository.deleteAll();
    }
}