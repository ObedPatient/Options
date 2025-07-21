/**
 * Service for managing UserStatusOption entities.
 * Provides functionality to create, read, update, and delete UserStatusOption data, supporting both
 * soft and hard deletion operations.
 */
package rw.evolve.eprocurement.user_status_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single User Status Option entity.
     *
     * @param userStatusOptionModel the UserStatusOptionModel to be created
     * @return the saved UserStatusOption model
     */
    @Transactional
    public UserStatusOptionModel createUserStatusOption(UserStatusOptionModel userStatusOptionModel){
        if (userStatusOptionRepository.existsByName(userStatusOptionModel.getName())){
            throw new UserStatusAlreadyExistException("User Status Option Already exists: " + userStatusOptionModel.getName());
        }
        return userStatusOptionRepository.save(userStatusOptionModel);
    }

    /**
     * Creates multiple User Status Option entities, each with a unique ID.
     * Iterates through the provided list of User Status Option models
     *
     * @param userStatusOptionModels the list of User Status Option models to be created
     * @return a list of saved User Status Option models.
     */
    @Transactional
    public List<UserStatusOptionModel> createUserStatusOptions(List<UserStatusOptionModel> userStatusOptionModels){
        if (userStatusOptionModels == null){
            throw new IllegalArgumentException("User Status Option model cannot be null");
        }
        List<UserStatusOptionModel> savedUserStatusModels = new ArrayList<>();
        for (UserStatusOptionModel userStatusOptionModel: userStatusOptionModels){
            UserStatusOptionModel savedUserStatusModel = userStatusOptionRepository.save(userStatusOptionModel);
            savedUserStatusModels.add(savedUserStatusModel);
        }
        return savedUserStatusModels;
    }

    /**
     * Retrieves a single User Status Option entity by its ID.
     * Throws a UserStatusNotFoundException if the User Status Option is not found or has been deleted.
     *
     * @param id the ID of the User Status Option to retrieve
     * @return the User Status Option model if found and not deleted
     * @throws UserStatusNotFoundException if the User Status Option is not found.
     */
    @Transactional
    public UserStatusOptionModel readOne(Long id){
        UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + id));
        if (userStatusOptionModel.getDeletedAt() != null){
            throw new UserStatusNotFoundException("User Status Option not found with id:" + id);
        }
        return userStatusOptionModel;
    }

    /**
     * Retrieves a list of User Status Option objects based on the provided User Status Option IDs.
     *
     * @param userStatusOptionIds A list of User Status Option IDs to retrieve
     * @return A list of UserStatusOptionModel objects that are not marked as deleted
     * @throws UserStatusNotFoundException if a User Status Option with the given ID is not found
     */
    @Transactional
    public List<UserStatusOptionModel> readMany(List<Long> userStatusOptionIds){
        if (userStatusOptionIds == null){
            throw new IllegalArgumentException("User Status Option id cannot be null or empty");
        }
        List<UserStatusOptionModel> models = new ArrayList<>();
        for (Long id: userStatusOptionIds){
            if (id == null){
                throw new IllegalArgumentException("User Status Option id cannot be null or empty");
            }
            UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                    .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + id));
            if (userStatusOptionModel.getDeletedAt() == null){
                models.add(userStatusOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all User Status Options that are not marked as deleted
     * @return a List of User Status Option objects where deleted is null
     * @throws UserStatusNotFoundException if no User Status Option found
     */
    @Transactional
    public List<UserStatusOptionModel> readAll(){
        List<UserStatusOptionModel> userStatusOptionModels = userStatusOptionRepository.findByDeletedAtIsNull();
        if (userStatusOptionModels.isEmpty()){
            throw new UserStatusNotFoundException("No User Status Option found");
        }
        return userStatusOptionModels;
    }

    /**
     * Retrieves all UserStatusOptionModels, including those marked as deleted.
     *
     * @return A list of all UserStatusOptionModel objects
     * @throws UserStatusNotFoundException if no User Status Option found
     */
    @Transactional
    public List<UserStatusOptionModel> hardReadAll(){
        List<UserStatusOptionModel> userStatusOptionModels = userStatusOptionRepository.findAll();
        if (userStatusOptionModels.isEmpty()){
            throw new UserStatusNotFoundException("No User Status Option found");
        }
        return userStatusOptionModels;
    }

    /**
     * Updates a single UserStatusOptionModel identified by the provided ID.
     * @param id The ID of the User Status Option to update
     * @param model The UserStatusOptionModel containing updated data
     * @return The updated UserStatusOptionModel
     * @throws UserStatusNotFoundException if the UserStatusOptionModel is not found or is marked as deleted
     */
    @Transactional
    public UserStatusOptionModel updateOne(Long id, UserStatusOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("User Status Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("User Status Option cannot be null");
        }
        UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + id));
        if (userStatusOptionModel.getDeletedAt() != null){
            throw new UserStatusNotFoundException("User Status Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, userStatusOptionModel);
        userStatusOptionModel.setUpdatedAt(LocalDateTime.now());
        return userStatusOptionRepository.save(userStatusOptionModel);
    }

    /**
     * Updates multiple UserStatusOption models in a transactional manner.
     *
     * @param models List of UserStatusOptionModel objects containing updated data
     * @return List of updated UserStatusOptionModel objects
     * @throws IllegalArgumentException if any UserStatusOptionModel is null
     * @throws UserStatusNotFoundException if a UserStatusOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<UserStatusOptionModel> updateMany(List<UserStatusOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("User Status Option model List cannot be null or empty");
        }
        List<UserStatusOptionModel> updatedModel = new ArrayList<>();
        for (UserStatusOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("User Status Option id cannot be null");
            }
            UserStatusOptionModel existingModel = userStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() != null){
                throw new UserStatusNotFoundException("User Status Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(userStatusOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single User Status Option model by ID.
     * @param id The ID of the User Status Option to update
     * @param model The UserStatusOptionModel containing updated data
     * @return The updated UserStatusOptionModel
     * @throws IllegalArgumentException if the User Status Option ID is null
     * @throws UserStatusNotFoundException if the User Status Option is not found
     */
    @Transactional
    public UserStatusOptionModel hardUpdateOne(Long id, UserStatusOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("User Status Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("User Status Option model cannot be null");
        }
        UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + id));

        modelMapper.map(model, userStatusOptionModel);
        userStatusOptionModel.setUpdatedAt(LocalDateTime.now());
        return userStatusOptionRepository.save(userStatusOptionModel);
    }

    /**
     * Updates multiple UserStatusOptionModel models by their IDs
     * @param userStatusOptionModels List of UserStatusOptionModel objects containing updated data
     * @return List of updated UserStatusOptionModel objects
     * @throws IllegalArgumentException if any User Status Option ID is null
     * @throws UserStatusNotFoundException if any User Status Option is not found
     */
    @Transactional
    public List<UserStatusOptionModel> hardUpdateAll(List<UserStatusOptionModel> userStatusOptionModels){
        if (userStatusOptionModels == null || userStatusOptionModels.isEmpty()) {
            throw new IllegalArgumentException("User Status Option model list cannot be null or empty");
        }
        List<UserStatusOptionModel> updatedModels = new ArrayList<>();
        for (UserStatusOptionModel model: userStatusOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("User Status Option id cannot be null on Hard update all");
            }
            UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + model.getId()));

            modelMapper.map(model, userStatusOptionModel);
            userStatusOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(userStatusOptionRepository.save(userStatusOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a User Status Option by ID in a transactional manner.
     *
     * @param id The ID of the User Status Option to soft delete
     * @return The soft-deleted UserStatusOptionModel
     * @throws IllegalArgumentException if the User Status Option ID is null
     * @throws UserStatusNotFoundException if the User Status Option is not found
     * @throws IllegalStateException if the User Status Option is already deleted
     */
    @Transactional
    public UserStatusOptionModel softDeleteUserStatusOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("User Status Option ID cannot be null or empty");
        }
        UserStatusOptionModel userStatusOptionModel = userStatusOptionRepository.findById(id)
                .orElseThrow(() -> new UserStatusNotFoundException("User Status Option not found with id:" + id));
        if (userStatusOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("User Status Option with id:" + id + " is already deleted");
        }
        userStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return userStatusOptionRepository.save(userStatusOptionModel);
    }

    /**
     * Hard deletes a User Status Option by ID
     * @param id ID of the User Status Option to hard delete
     */
    @Transactional
    public void hardDeleteUserStatusOption(Long id){
        if (!userStatusOptionRepository.existsById(id)){
            throw new UserStatusNotFoundException("User Status Option not found with id:" + id);
        }
        userStatusOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple User Status Options by their IDs.
     *
     * @param ids List of User Status Option IDs to be soft deleted
     * @return List of soft deleted User Status Option objects
     * @throws UserStatusNotFoundException if any User Status Option IDs are not found
     * @throws IllegalStateException if any User Status Option is already deleted
     */
    @Transactional
    public List<UserStatusOptionModel> softDeleteUserStatusOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("User Status Option IDs list cannot be null or empty");
        }
        List<UserStatusOptionModel> userStatusOptionModels = userStatusOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (UserStatusOptionModel model: userStatusOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new UserStatusNotFoundException("User Status Option not found with ids:" + missingIds);
        }
        for (UserStatusOptionModel model: userStatusOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalStateException("User Status Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        userStatusOptionRepository.saveAll(userStatusOptionModels);
        return userStatusOptionModels;
    }

    /**
     * Hard deletes multiple User Status Options by IDs
     * @param ids List of User Status Option IDs to hard delete
     */
    @Transactional
    public void hardDeleteUserStatusOptions(List<Long> ids){
        List<UserStatusOptionModel> userStatusOptionModels = userStatusOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (UserStatusOptionModel model: userStatusOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new UserStatusNotFoundException("User Status Option not found with ids:" + missingIds);
        }
        userStatusOptionRepository.deleteAllById(ids);
    }
}