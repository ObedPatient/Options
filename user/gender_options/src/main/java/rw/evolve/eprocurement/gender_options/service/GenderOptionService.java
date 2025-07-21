/**
 * Service for managing GenderOption entities.
 * Provides functionality to create, read, update, and delete GenderOption data, supporting both
 * soft and hard deletion operations.
 */
package rw.evolve.eprocurement.gender_options.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.gender_options.exception.GenderOptionAlreadyExistException;
import rw.evolve.eprocurement.gender_options.exception.GenderOptionNotFoundException;
import rw.evolve.eprocurement.gender_options.model.GenderOptionModel;
import rw.evolve.eprocurement.gender_options.repository.GenderOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenderOptionService {

    @Autowired
    private GenderOptionRepository genderOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Gender Option entity.
     *
     * @param genderOptionModel the GenderOptionModel to be created
     * @return the saved GenderOption model
     */
    @Transactional
    public GenderOptionModel createGenderOption(GenderOptionModel genderOptionModel){
        if (genderOptionRepository.existsByName(genderOptionModel.getName())){
            throw new GenderOptionAlreadyExistException("Gender Option Already exists: " + genderOptionModel.getName());
        }
        return genderOptionRepository.save(genderOptionModel);
    }

    /**
     * Creates multiple Gender Option entities, each with a unique ID.
     * Iterates through the provided list of Gender Option models
     *
     * @param genderOptionModels the list of Gender Option models to be created
     * @return a list of saved Gender Option models.
     */
    @Transactional
    public List<GenderOptionModel> createGenderOptions(List<GenderOptionModel> genderOptionModels){
        if (genderOptionModels == null){
            throw new IllegalArgumentException("Gender Option model cannot be null");
        }
        List<GenderOptionModel> savedGenderModels = new ArrayList<>();
        for (GenderOptionModel genderOptionModel: genderOptionModels){
            GenderOptionModel savedGenderModel = genderOptionRepository.save(genderOptionModel);
            savedGenderModels.add(savedGenderModel);
        }
        return savedGenderModels;
    }

    /**
     * Retrieves a single Gender Option entity by its ID.
     * Throws a GenderOptionNotFoundException if the Gender Option is not found or has been deleted.
     *
     * @param id the ID of the Gender Option to retrieve
     * @return the Gender Option model if found and not deleted
     * @throws GenderOptionNotFoundException if the Gender Option is not found.
     */
    @Transactional
    public GenderOptionModel readOne(Long id){
        GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + id));
        if (genderOptionModel.getDeletedAt() != null){
            throw new GenderOptionNotFoundException("Gender Option not found with id:" + id);
        }
        return genderOptionModel;
    }

    /**
     * Retrieves a list of Gender Option objects based on the provided Gender Option IDs.
     *
     * @param genderOptionIds A list of Gender Option IDs to retrieve
     * @return A list of GenderOptionModel objects that are not marked as deleted
     * @throws GenderOptionNotFoundException if a Gender Option with the given ID is not found
     */
    @Transactional
    public List<GenderOptionModel> readMany(List<Long> genderOptionIds){
        if (genderOptionIds == null){
            throw new IllegalArgumentException("Gender Option id cannot be null or empty");
        }
        List<GenderOptionModel> models = new ArrayList<>();
        for (Long id: genderOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Gender Option id cannot be null or empty");
            }
            GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                    .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + id));
            if (genderOptionModel.getDeletedAt() == null){
                models.add(genderOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all Gender Options that are not marked as deleted
     * @return a List of Gender Option objects where deleted is null
     * @throws GenderOptionNotFoundException if no Gender Option found
     */
    @Transactional
    public List<GenderOptionModel> readAll(){
        List<GenderOptionModel> genderOptionModels = genderOptionRepository.findByDeletedAtIsNull();
        if (genderOptionModels.isEmpty()){
            throw new GenderOptionNotFoundException("No Gender Option found");
        }
        return genderOptionModels;
    }

    /**
     * Retrieves all GenderOptionModels, including those marked as deleted.
     *
     * @return A list of all GenderOptionModel objects
     * @throws GenderOptionNotFoundException if no Gender Option found
     */
    @Transactional
    public List<GenderOptionModel> hardReadAll(){
        List<GenderOptionModel> genderOptionModels = genderOptionRepository.findAll();
        if (genderOptionModels.isEmpty()){
            throw new GenderOptionNotFoundException("No Gender Option found");
        }
        return genderOptionModels;
    }

    /**
     * Updates a single GenderOptionModel identified by the provided ID.
     * @param id The ID of the Gender Option to update
     * @param model The GenderOptionModel containing updated data
     * @return The updated GenderOptionModel
     * @throws GenderOptionNotFoundException if the GenderOptionModel is not found or is marked as deleted
     */
    @Transactional
    public GenderOptionModel updateOne(Long id, GenderOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Gender Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Gender Option cannot be null");
        }
        GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + id));
        if (genderOptionModel.getDeletedAt() != null){
            throw new GenderOptionNotFoundException("Gender Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, genderOptionModel);
        genderOptionModel.setUpdatedAt(LocalDateTime.now());
        return genderOptionRepository.save(genderOptionModel);
    }

    /**
     * Updates multiple GenderOption models in a transactional manner.
     *
     * @param models List of GenderOptionModel objects containing updated data
     * @return List of updated GenderOptionModel objects
     * @throws IllegalArgumentException if any GenderOptionModel is null
     * @throws GenderOptionNotFoundException if a GenderOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<GenderOptionModel> updateMany(List<GenderOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Gender Option model List cannot be null or empty");
        }
        List<GenderOptionModel> updatedModel = new ArrayList<>();
        for (GenderOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Gender Option id cannot be null");
            }
            GenderOptionModel existingModel = genderOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() != null){
                throw new GenderOptionNotFoundException("Gender Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(genderOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Gender Option model by ID.
     * @param id The ID of the Gender Option to update
     * @param model The GenderOptionModel containing updated data
     * @return The updated GenderOptionModel
     * @throws IllegalArgumentException if the Gender Option ID is null
     * @throws GenderOptionNotFoundException if the Gender Option is not found
     */
    @Transactional
    public GenderOptionModel hardUpdateOne(Long id, GenderOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Gender Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Gender Option model cannot be null");
        }
        GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + id));

        modelMapper.map(model, genderOptionModel);
        genderOptionModel.setUpdatedAt(LocalDateTime.now());
        return genderOptionRepository.save(genderOptionModel);
    }

    /**
     * Updates multiple GenderOptionModel models by their IDs
     * @param genderOptionModels List of GenderOptionModel objects containing updated data
     * @return List of updated GenderOptionModel objects
     * @throws IllegalArgumentException if any Gender Option ID is null
     * @throws GenderOptionNotFoundException if any Gender Option is not found
     */
    @Transactional
    public List<GenderOptionModel> hardUpdateAll(List<GenderOptionModel> genderOptionModels){
        if (genderOptionModels == null || genderOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Gender Option model list cannot be null or empty");
        }
        List<GenderOptionModel> updatedModels = new ArrayList<>();
        for (GenderOptionModel model: genderOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Gender Option id cannot be null on Hard update all");
            }
            GenderOptionModel genderOptionModel = genderOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + model.getId()));

            modelMapper.map(model, genderOptionModel);
            genderOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(genderOptionRepository.save(genderOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a Gender Option by ID in a transactional manner.
     *
     * @param id The ID of the Gender Option to soft delete
     * @return The soft-deleted GenderOptionModel
     * @throws IllegalArgumentException if the Gender Option ID is null
     * @throws GenderOptionNotFoundException if the Gender Option is not found
     * @throws IllegalStateException if the Gender Option is already deleted
     */
    @Transactional
    public GenderOptionModel softDeleteGenderOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Gender Option ID cannot be null or empty");
        }
        GenderOptionModel genderOptionModel = genderOptionRepository.findById(id)
                .orElseThrow(() -> new GenderOptionNotFoundException("Gender Option not found with id:" + id));
        if (genderOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Gender Option with id:" + id + " is already deleted");
        }
        genderOptionModel.setDeletedAt(LocalDateTime.now());
        return genderOptionRepository.save(genderOptionModel);
    }

    /**
     * Hard deletes a Gender Option by ID
     * @param id ID of the Gender Option to hard delete
     */
    @Transactional
    public void hardDeleteGenderOption(Long id){
        if (!genderOptionRepository.existsById(id)){
            throw new GenderOptionNotFoundException("Gender Option not found with id:" + id);
        }
        genderOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Gender Options by their IDs.
     *
     * @param ids List of Gender Option IDs to be soft deleted
     * @return List of soft deleted Gender Option objects
     * @throws GenderOptionNotFoundException if any Gender Option IDs are not found
     * @throws IllegalStateException if any Gender Option is already deleted
     */
    @Transactional
    public List<GenderOptionModel> softDeleteGenderOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Gender Option IDs list cannot be null or empty");
        }
        List<GenderOptionModel> genderOptionModels = genderOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (GenderOptionModel model: genderOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new GenderOptionNotFoundException("Gender Option not found with ids:" + missingIds);
        }
        for (GenderOptionModel model: genderOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalStateException("Gender Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        genderOptionRepository.saveAll(genderOptionModels);
        return genderOptionModels;
    }

    /**
     * Hard deletes multiple Gender Options by IDs
     * @param ids List of Gender Option IDs to hard delete
     */
    @Transactional
    public void hardDeleteGenderOptions(List<Long> ids){
        List<GenderOptionModel> genderOptionModels = genderOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (GenderOptionModel model: genderOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new GenderOptionNotFoundException("Gender Option not found with ids:" + missingIds);
        }
        genderOptionRepository.deleteAllById(ids);
    }
}