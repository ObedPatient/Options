/**
 * Service for managing UserStatusOption model.
 * Provides functionality to create, read, update, and delete UserStatusOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.user_status_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.user_status_option.exception.UserStatusAlreadyExistException;
import rw.evolve.eprocurement.user_status_option.exception.UserStatusNotFoundException;
import rw.evolve.eprocurement.user_status_option.model.UserStatusOptionModel;
import rw.evolve.eprocurement.user_status_option.repository.UserStatusOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserStatusOptionService {

    @Autowired
    private UserStatusOptionRepository userStatusOptionRepository;

    /**
     * Creates a single User Status option model with a generated ID.
     *
     * @param userStatusOptionModel            - the UserStatusOptionModel to be created
     * @return                                 - the saved UserStatusOption model
     * @throws UserStatusAlreadyExistException - if a UserStatusOption with the same name exists
     */
    @Transactional
    public UserStatusOptionModel save(UserStatusOptionModel userStatusOptionModel) {
        if (userStatusOptionModel == null) {
            throw new NullPointerException("User status option cannot be null");
        }
        if (userStatusOptionRepository.existsByName(userStatusOptionModel.getName())) {
            throw new UserStatusAlreadyExistException("User status option already exists: " + userStatusOptionModel.getName());
        }
        return userStatusOptionRepository.save(userStatusOptionModel);
    }

    /**
     * Creates multiple User Status Option model, each with a unique generated ID.
     *
     * @param userStatusOptionModelList - the list of User Status option models to be created
     * @return                          - a list of saved User Status Option models
     * @throws IllegalArgumentException - if the input list is null or empty
     */
    @Transactional
    public List<UserStatusOptionModel> saveMany(List<UserStatusOptionModel> userStatusOptionModelList) {
        if (userStatusOptionModelList == null || userStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("User status option model list cannot be null or empty");
        }
        for (UserStatusOptionModel userStatusOptionModel : userStatusOptionModelList) {
            if (userStatusOptionRepository.existsByName(userStatusOptionModel.getName())) {
                throw new UserStatusAlreadyExistException("User status option already exists: " + userStatusOptionModel.getName());
            }
        }
        return userStatusOptionRepository.saveAll(userStatusOptionModelList);
    }

    /**
     * Retrieves a single User Status option model by its ID.
     * Throws a UserStatusNotFoundException if the User Status option is not found or has been deleted.
     *
     * @param id                           - the ID of the User Status option to retrieve
     * @return                             - the User Status option model if found and not deleted
     * @throws UserStatusNotFoundException - if the User Status option is not found
     * @throws NullPointerException        - if User Status option ID is null
     */
    @Transactional
    public UserStatusOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("User status option ID cannot be null");
        }
        UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                .orElseThrow(() -> new UserStatusNotFoundException("User status option not found with ID: " + id));
        if (userStatusOptionModel.getDeletedAt() != null) {
            throw new UserStatusNotFoundException("User status option not found with ID: " + id);
        }
        return userStatusOptionModel;
    }

    /**
     * Retrieves a list of UserStatusOption model based on the provided UserStatusOption IDs.
     *
     * @param userStatusOptionIdList   - A list of UserStatusOption IDs to retrieve
     * @return                         - A list of UserStatusOptionModel objects that are not marked as deleted
     * @throws NullPointerException    - if UserStatusOption ID list is null
     */
    @Transactional
    public List<UserStatusOptionModel> readMany(List<String> userStatusOptionIdList) {
        if (userStatusOptionIdList == null || userStatusOptionIdList.isEmpty()) {
            throw new NullPointerException("User status option ID list cannot be null");
        }
        List<UserStatusOptionModel> modelList = new ArrayList<>();
        for (String id : userStatusOptionIdList) {
            if (id == null) {
                throw new NullPointerException("User status option ID cannot be null");
            }
            UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                    .orElse(null);
            if (userStatusOptionModel == null)
                continue;
            if (userStatusOptionModel.getDeletedAt() == null) {
                modelList.add(userStatusOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all User Status options that are not marked as deleted
     *
     * @return         - a List of User Status option models where deletedAt is null
     */
    @Transactional
    public List<UserStatusOptionModel> readAll() {
        return userStatusOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all User Status Option models, including those marked as deleted.
     *
     * @return         - A list of all UserStatusOptionModel objects
     */
    @Transactional
    public List<UserStatusOptionModel> hardReadAll() {
        return userStatusOptionRepository.findAll();
    }

    /**
     * Updates a single User Status Option model identified by the provided ID.
     *
     * @param model                        - The UserStatusOptionModel containing updated data
     * @return                             - The updated UserStatusOptionModel
     * @throws UserStatusNotFoundException - if User Status option is not found or is marked as deleted
     */
    @Transactional
    public UserStatusOptionModel updateOne(UserStatusOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("User status option or ID cannot be null");
        }
        UserStatusOptionModel existing = userStatusOptionRepository.findById(model.getId())
                .orElseThrow(() -> new UserStatusNotFoundException("User Status option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new UserStatusNotFoundException("User status option with ID: " + model.getId() + " is not found");
        }
        return userStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple User Status option model.
     *
     * @param modelList                    - List of UserStatusOptionModel objects containing updated data
     * @return                             - List of updated UserStatusOptionModel objects
     * @throws UserStatusNotFoundException - if User Status option is not found or is marked as deleted
     */
    @Transactional
    public List<UserStatusOptionModel> updateMany(List<UserStatusOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("User status option model list cannot be null or empty");
        }
        for (UserStatusOptionModel model : modelList) {
            if (model.getId() == null) {
                throw new NullPointerException("User status option ID cannot be null");
            }
            UserStatusOptionModel existing = userStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new UserStatusNotFoundException("User Status option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new UserStatusNotFoundException("User status option with ID: " + model.getId() + " is not found");
            }
        }
        return userStatusOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single User Status option model by ID, including deleted ones.
     *
     * @param model                        - The UserStatusOptionModel containing updated data
     * @return                             - The updated UserStatusOptionModel
     * @throws UserStatusNotFoundException - if User Status option is not found
     */
    @Transactional
    public UserStatusOptionModel hardUpdate(UserStatusOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("User status option or ID cannot be null");
        }
        return userStatusOptionRepository.save(model);
    }

    /**
     * Updates multiple UserStatusOption model by their IDs, including deleted ones.
     *
     * @param userStatusOptionModelList - List of UserStatusOptionModel objects containing updated data
     * @return                          - List of updated UserStatusOptionModel objects
     */
    @Transactional
    public List<UserStatusOptionModel> hardUpdateAll(List<UserStatusOptionModel> userStatusOptionModelList) {
        if (userStatusOptionModelList == null || userStatusOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("User status option model list cannot be null or empty");
        }
        return userStatusOptionRepository.saveAll(userStatusOptionModelList);
    }

    /**
     * Soft deletes a User Status option by ID.
     *
     * @param id                           - ID of the User Status option to soft delete
     * @return                             - The soft-deleted UserStatusOptionModel
     * @throws UserStatusNotFoundException - if User Status option id is not found
     */
    @Transactional
    public UserStatusOptionModel softDelete(String id) {
        if (id == null) {
            throw new NullPointerException("User status option ID cannot be null");
        }
        UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                .orElseThrow(() -> new UserStatusNotFoundException("User status option not found with id: " + id));
        userStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return userStatusOptionRepository.save(userStatusOptionModel);
    }

    /**
     * Hard deletes a User Status option by ID.
     *
     * @param id                           - ID of the User Status option to hard delete
     * @throws NullPointerException        - if the User Status option ID is null
     * @throws UserStatusNotFoundException - if the User Status option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("User status option ID cannot be null");
        }
        if (!userStatusOptionRepository.existsById(id)) {
            throw new UserStatusNotFoundException("User status option not found with id: " + id);
        }
        userStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple User Status options by their IDs.
     *
     * @param idList                       - List of User Status option IDs to be soft deleted
     * @return                             - List of soft-deleted UserStatusOption objects
     * @throws UserStatusNotFoundException - if any User Status option IDs are not found
     */
    @Transactional
    public List<UserStatusOptionModel> softDeleteMany(List<String> idList) {
        List<UserStatusOptionModel> userStatusOptionModelList = userStatusOptionRepository.findAllById(idList);
        if (userStatusOptionModelList.isEmpty()) {
            throw new UserStatusNotFoundException("No User status options found with provided IDList: " + idList);
        }
        for (UserStatusOptionModel model : userStatusOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return userStatusOptionRepository.saveAll(userStatusOptionModelList);
    }

    /**
     * Hard deletes multiple User Status options by ID.
     *
     * @param idList     - List of User Status option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        userStatusOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all User Status options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        userStatusOptionRepository.deleteAll();
    }
}