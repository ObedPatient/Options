package rw.evolve.eprocurement.prerequisites_activity_type_options.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.prerequisites_activity_type_options.exception.PrerequisitesActivityFileTypeNotFoundException;
import rw.evolve.eprocurement.prerequisites_activity_type_options.exception.PrerequisitesActivityFileTypeNotFoundException;
import rw.evolve.eprocurement.prerequisites_activity_type_options.exception.PrerequisitesActivictyTypeAlreadyExistException;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.PrerequisitesActivityTypeOptionModel;
import rw.evolve.eprocurement.prerequisites_activity_type_options.repository.PrerequisitesActivityTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrerequisitesActivityTypeOptionService {

    @Autowired
    private PrerequisitesActivityTypeOptionRepository prerequisitesActivityTypeOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single PrerequisitesActivityTypeOption entity.
     *
     * @param prerequisitesActivityTypeOptionModel the PrerequisitesActivityTypeOptionModel to be created
     * @return the saved PrerequisitesActivityTypeOption model
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel createPrerequisitesActivityTypeOption(PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel){
        if (prerequisitesActivityTypeOptionRepository.existsByName(prerequisitesActivityTypeOptionModel.getName())){
            throw new PrerequisitesActivictyTypeAlreadyExistException("Prerequisites Activity Type Option Already exists: " + prerequisitesActivityTypeOptionModel.getName());
        }
        return prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel);
    }

    /**
     * Creates multiple PrerequisitesActivityTypeOption entities, each with a unique ID.
     * Iterates through the provided list of PrerequisitesActivityTypeOption models
     *
     * @param prerequisitesActivityTypeOptionModels the list of PrerequisitesActivityTypeOption models to be created
     * @return a list of saved PrerequisitesActivityTypeOption models.
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> createPrerequisitesActivityTypeOptions(List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels){
        if (prerequisitesActivityTypeOptionModels == null){
            throw new IllegalArgumentException("Prerequisites Activity Type Option model cannot be null");
        }
        List<PrerequisitesActivityTypeOptionModel> savedPrerequisitesActivityTypeModels = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel: prerequisitesActivityTypeOptionModels){
            PrerequisitesActivityTypeOptionModel savedPrerequisitesActivityTypeModel = prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel);
            savedPrerequisitesActivityTypeModels.add(savedPrerequisitesActivityTypeModel);
        }
        return savedPrerequisitesActivityTypeModels;
    }

    /**
     * Retrieves a single PrerequisitesActivityTypeOption entity by its ID.
     * Throws a PrerequisitesActivityTypeNotFoundException if the PrerequisitesActivityTypeOption is not found or has been deleted.
     *
     * @param id the ID of the PrerequisitesActivityTypeOption to retrieve
     * @return the PrerequisitesActivityTypeOption model if found and not deleted
     * @throws PrerequisitesActivityFileTypeNotFoundException if the PrerequisitesActivityTypeOption is not found.
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel readOne(Long id){
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionRepository.findById(id)
                .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + id));
        if (prerequisitesActivityTypeOptionModel.getDeletedAt() != null){
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + id);
        }
        return prerequisitesActivityTypeOptionModel;
    }

    /**
     * Retrieves a list of PrerequisitesActivityTypeOption objects based on the provided PrerequisitesActivityTypeOption IDs.
     *
     * @param prerequisitesActivityTypeOptionIds A list of PrerequisitesActivityTypeOption IDs to retrieve
     * @return A list of PrerequisitesActivityTypeOptionModel objects that are not marked as deleted
     * @throws PrerequisitesActivityFileTypeNotFoundException if a PrerequisitesActivityTypeOption with the given ID is not found
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> readMany(List<Long> prerequisitesActivityTypeOptionIds){
        if (prerequisitesActivityTypeOptionIds == null){
            throw new IllegalArgumentException("Prerequisites Activity Type Option id cannot be null or empty");
        }
        List<PrerequisitesActivityTypeOptionModel> models = new ArrayList<>();
        for (Long id: prerequisitesActivityTypeOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Prerequisites Activity Type Option id cannot be null or empty");
            }
            PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionRepository.findById(id)
                    .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + id));
            if (prerequisitesActivityTypeOptionModel.getDeletedAt() == null){
                models.add(prerequisitesActivityTypeOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all PrerequisitesActivityTypeOption that are not marked as deleted
     * @return a List of PrerequisitesActivityTypeOption object where deleted is null
     * @throws PrerequisitesActivityFileTypeNotFoundException if no PrerequisitesActivityTypeOption found
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> readAll(){
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionRepository.findByDeletedAtIsNull();
        if (prerequisitesActivityTypeOptionModels.isEmpty()){
            throw new PrerequisitesActivityFileTypeNotFoundException("No Prerequisites Activity Type Option found");
        }
        return prerequisitesActivityTypeOptionModels;
    }

    /**
     * Retrieves all PrerequisitesActivityTypeOptionModels, including those marked as deleted.
     *
     * @return A list of all PrerequisitesActivityTypeOptionModel objects
     * @throws PrerequisitesActivityFileTypeNotFoundException if no PrerequisitesActivityTypeOption are found
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> hardReadAll(){
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionRepository.findAll();
        if (prerequisitesActivityTypeOptionModels.isEmpty()){
            throw new PrerequisitesActivityFileTypeNotFoundException("No Prerequisites Activity Type Option found");
        }
        return prerequisitesActivityTypeOptionModels;
    }

    /**
     * Updates a single PrerequisitesActivityTypeOptionModel model identified by the provided ID.
     * @param id The ID of the predecesorActivityTypeOption to update
     * @param model The PrerequisitesActivityTypeOptionModel containing updated data
     * @return The updated PrerequisitesActivityTypeOptionModel
     * @throws PrerequisitesActivityFileTypeNotFoundException if the PrerequisitesActivityTypeOptionModel is not found or is marked as deleted
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel updateOne(Long id, PrerequisitesActivityTypeOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Prerequisites Activity Type Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Prerequisites Activity Type Option cannot be null");
        }
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionRepository.findById(id)
                .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + id));
        if (prerequisitesActivityTypeOptionModel.getDeletedAt() != null){
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, prerequisitesActivityTypeOptionModel);
        prerequisitesActivityTypeOptionModel.setUpdatedAt(LocalDateTime.now());
        return prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel);
    }

    /**
     * Updates multiple PrerequisitesActivityTypeOption models in a transactional manner.
     *
     * @param models List of PrerequisitesActivityTypeOptionModel objects containing updated data
     * @return List of updated PrerequisitesActivityTypeOptionModel objects
     * @throws IllegalArgumentException if any PrerequisitesActivityTypeOptionModel is null
     * @throws PrerequisitesActivityFileTypeNotFoundException if a PrerequisitesActivityTypeOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> updateMany(List<PrerequisitesActivityTypeOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Prerequisites Activity Type Option model List cannot be null or empty");
        }
        List<PrerequisitesActivityTypeOptionModel> updatedModel = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Prerequisites Activity Type Option id cannot be null");
            }
            PrerequisitesActivityTypeOptionModel existingModel = prerequisitesActivityTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(prerequisitesActivityTypeOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single PrerequisitesActivityTypeOption model by ID.
     * @param id The ID of the PrerequisitesActivityTypeOption to update
     * @param model The PrerequisitesActivityTypeOptionModel containing updated data
     * @return The updated PrerequisitesActivityTypeOptionModel
     * @throws IllegalArgumentException if the PrerequisitesActivityTypeOption ID is null
     * @throws PrerequisitesActivityFileTypeNotFoundException if the PrerequisitesActivityTypeOption is not found
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel hardUpdateOne(Long id, PrerequisitesActivityTypeOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Prerequisites Activity Type Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Prerequisites Activity Type Option model cannot be null");
        }
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionRepository.findById(id)
                .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with Id:" + id));

        modelMapper.map(model, prerequisitesActivityTypeOptionModel);
        prerequisitesActivityTypeOptionModel.setUpdatedAt(LocalDateTime.now());
        return prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel);
    }

    /**
     * Updates multiple PrerequisitesActivityTypeOptionModel models by their IDs
     * @param prerequisitesActivityTypeOptionModels List of PrerequisitesActivityTypeOptionModel objects containing updated data
     * @return List of updated PrerequisitesActivityTypeOptionModel objects
     * @throws IllegalArgumentException if any Prerequisites Activity Type Option ID is null
     * @throws PrerequisitesActivityFileTypeNotFoundException if any PrerequisitesActivityTypeOption is not found
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> hardUpdateAll(List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels){
        if (prerequisitesActivityTypeOptionModels == null || prerequisitesActivityTypeOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Prerequisites Activity Type Option model list cannot be null or empty");
        }
        List<PrerequisitesActivityTypeOptionModel> updatedModels = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: prerequisitesActivityTypeOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Prerequisites Activity Type Option id cannot be null on Hard update all");
            }
            PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + model.getId()));

            modelMapper.map(model, prerequisitesActivityTypeOptionModel);
            prerequisitesActivityTypeOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a PrerequisitesActivityTypeOption by ID in a transactional manner.
     *
     * @param id The ID of the PrerequisitesActivityTypeOption to soft delete
     * @return The soft-deleted PrerequisitesActivityTypeOptionModel
     * @throws IllegalArgumentException if the PrerequisitesActivityTypeOption ID is null
     * @throws PrerequisitesActivityFileTypeNotFoundException if the PrerequisitesActivityTypeOption is not found
     * @throws IllegalStateException if the PrerequisitesActivityTypeOption is already deleted
     */
    @Transactional
    public PrerequisitesActivityTypeOptionModel softDeletePrerequisitesActivityTypeOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Prerequisites Activity Type Option ID cannot be null or empty");
        }
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionRepository.findById(id)
                .orElseThrow(()-> new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with id:" + id));
        if (prerequisitesActivityTypeOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Prerequisites Activity Type Option with id:" + id + " is already deleted");
        }
        prerequisitesActivityTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return prerequisitesActivityTypeOptionRepository.save(prerequisitesActivityTypeOptionModel);
    }

    /**
     * Hard deletes a PrerequisitesActivityTypeOption by ID
     * @param id ID of the PrerequisitesActivityTypeOption to hard delete
     */
    @Transactional
    public void hardDeletePrerequisitesActivityTypeOption(Long id){
        if (!prerequisitesActivityTypeOptionRepository.existsById(id)){
            throw new PrerequisitesActivictyTypeAlreadyExistException("Prerequisites Activity Type Option not found with id:" + id);
        }
        prerequisitesActivityTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple PrerequisitesActivityTypeOption by their IDs.
     *
     * @param ids List of PrerequisitesActivityTypeOption IDs to be soft deleted
     * @return List of soft deleted PrerequisitesActivityTypeOption objects
     * @throws PrerequisitesActivityFileTypeNotFoundException if any PrerequisitesActivityTypeOption IDs are not found
     * @throws IllegalStateException if any PrerequisitesActivityTypeOption is already deleted
     */
    @Transactional
    public List<PrerequisitesActivityTypeOptionModel> softDeletePrerequisitesActivityTypeOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Prerequisites Activity Type Option IDs list cannot be null or empty");
        }
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: prerequisitesActivityTypeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with ids:" + missingIds);
        }
        for (PrerequisitesActivityTypeOptionModel model: prerequisitesActivityTypeOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Prerequisites Activity Type Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        prerequisitesActivityTypeOptionRepository.saveAll(prerequisitesActivityTypeOptionModels);
        return prerequisitesActivityTypeOptionModels;
    }

    /**
     * Hard deletes multiple PrerequisitesActivityTypeOption by IDs
     * @param ids List of PrerequisitesActivityTypeOption IDs to hard delete
     */
    @Transactional
    public void hardDeletePrerequisitesActivityTypeOptions(List<Long> ids){
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: prerequisitesActivityTypeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new PrerequisitesActivityFileTypeNotFoundException("Prerequisites Activity Type Option not found with ids:" + missingIds);
        }
        prerequisitesActivityTypeOptionRepository.deleteAllById(ids);
    }
}