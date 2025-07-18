/**
 * Service for managing SchemeOption entities.
 * Provides functionality to create, read, update, and delete SchemeOption data, supporting both
 * soft and hard deletion operation.
 */
package rw.evolve.eprocurement.schemes_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.schemes_option.exception.SchemeAlreadyExistException;
import rw.evolve.eprocurement.schemes_option.exception.SchemeNotFoundException;
import rw.evolve.eprocurement.schemes_option.model.SchemeOptionModel;
import rw.evolve.eprocurement.schemes_option.repository.SchemeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchemeOptionService {

    @Autowired
    private SchemeOptionRepository schemeOptionRepository;


    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Scheme option entity.
     *
     * @param @SchemeOptionModel the SchemeOptionModel to be created
     * @return the saved SchemeOption model
     */
    @Transactional
    public SchemeOptionModel createSchemeOption(SchemeOptionModel schemeOptionModel){
        if (schemeOptionRepository.existsByName(schemeOptionModel.getName())){
            throw new SchemeAlreadyExistException("Scheme Option Already exists: " + schemeOptionModel.getName());
        }
        return schemeOptionRepository.save(schemeOptionModel);
    }

    /**
     * Creates multiple Scheme Option entities, each with a unique ID.
     * Iterates through the provided list of Selection Method models
     *
     * @param schemeOptionModels the list of Scheme option models to be created
     * @return a list of saved Scheme Option models.
     */
    @Transactional
    public List<SchemeOptionModel> createSchemeOptions(List<SchemeOptionModel> schemeOptionModels){
        if (schemeOptionModels == null){
            throw new IllegalArgumentException("Scheme option model cannot be null");
        }
        List<SchemeOptionModel> savedSchemeModels = new ArrayList<>();
        for (SchemeOptionModel schemeOptionModel: schemeOptionModels){
            SchemeOptionModel savedSchemeModel = schemeOptionRepository.save(schemeOptionModel);
            savedSchemeModels.add(savedSchemeModel);
        }
        return savedSchemeModels;
    }

    /**
     * Retrieves a single Scheme option entity by its ID.
     * Throws a SchemeOptionNotFoundException if the Scheme option is not found or has been deleted.
     *
     * @param @SchemeOption id the ID of the Scheme option to retrieve
     * @return the Scheme option model if found and not deleted
     * @throws SchemeNotFoundException if the Scheme option is not found.
     */
    @Transactional
    public SchemeOptionModel readOne(Long id){
        SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                .orElseThrow(()-> new SchemeNotFoundException("Scheme option not found with id:" + id));
        if (schemeOptionModel.getDeletedAt() != null){
            throw new SchemeNotFoundException("Scheme option not found with id:" + id);
        }
        return schemeOptionModel;
    }

    /**
     * Retrieves a list of SchemeOption objects based on the provided SchemeOption IDs.
     *
     * @param schemeOptionIds A list of SchemeOption IDs to retrieve
     * @return A list of SchemeOptionModel objects that are not marked as deleted
     * @throws SchemeNotFoundException if a SchemeOption with the given ID is not found
     */
    @Transactional
    public List<SchemeOptionModel> readMany(List<Long> schemeOptionIds){
        if (schemeOptionIds == null){
            throw new IllegalArgumentException("Scheme option id cannot be null or empty");
        }
        List<SchemeOptionModel> models = new ArrayList<>();
        for (Long id: schemeOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Scheme option id cannot be null or emty");
            }
            SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                    .orElseThrow(()-> new SchemeNotFoundException("Scheme Not found with id:" + id));
            if (schemeOptionModel.getDeletedAt() == null){
                models.add(schemeOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all Scheme Option that are not marked as deleted
     * @return a List of Scheme option object where deleted in null
     * @throws SchemeNotFoundException if no Scheme option found
     */
    @Transactional
    public List<SchemeOptionModel> readAll(){
        List<SchemeOptionModel> selectionMethodOptionModels = schemeOptionRepository.findByDeletedAtIsNull();
        if (selectionMethodOptionModels.isEmpty()){
            throw new SchemeNotFoundException("No Scheme found");
        }
        return selectionMethodOptionModels;
    }

    /**
     * Retrieves all SchemeOptionModels, including those marked as deleted.
     *
     * @return A list of all SchemeOptionModel objects
     * @throws SchemeNotFoundException if no SchemeOption are found
     */
    @Transactional
    public List<SchemeOptionModel> hardReadAll(){
        List<SchemeOptionModel> schemeOptionModels = schemeOptionRepository.findAll();
        if (schemeOptionModels.isEmpty()){
            throw new SchemeNotFoundException("No Scheme option found");
        }
        return schemeOptionModels;
    }

    /**
     * Updates a single SchemeOptionModel model identified by the provided ID.
     * @param id The ID of the SchemeOption to update
     * @param model The SchemeOptionModel containing updated data
     * @return The updated SchemeOptionModel
     * @throws SchemeNotFoundException if the SchemeOptionModel is not found or is marked as deleted
     */
    @Transactional
    public SchemeOptionModel updateOne(Long id, SchemeOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Scheme option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Scheme Option cannot be null");
        }
        SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                .orElseThrow(()-> new SchemeNotFoundException("Scheme option not found with id:" + id));
        if (schemeOptionModel.getDeletedAt() != null){
            throw new SchemeNotFoundException("Scheme option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, schemeOptionModel);
        schemeOptionModel.setUpdatedAt(LocalDateTime.now());
        return schemeOptionRepository.save(schemeOptionModel);
    }

    /**
     * Updates multiple SchemeOption models in a transactional manner.
     *
     * @param models List of SchemeOptionModel objects containing updated data
     * @return List of updated SchemeOptionModel objects
     * @throws IllegalArgumentException if any SchemeOptionModel is null
     * @throws SchemeNotFoundException if a SchemeOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<SchemeOptionModel> updateMany(List<SchemeOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Scheme option model List cannot be null or empty");
        }
        List<SchemeOptionModel> updatedModel = new ArrayList<>();
        for (SchemeOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Scheme option id cannot be null");
            }
            SchemeOptionModel existingModel = schemeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new SchemeNotFoundException("Scheme option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new SchemeNotFoundException("Scheme option with id:" + model.getId() + "marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(schemeOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Scheme option model by ID.
     * @param id The ID of the Scheme option to update
     * @param model The SchemeOptionModel containing updated data
     * @return The updated SchemeOptionModel
     * @throws IllegalArgumentException if the Scheme option ID is null
     * @throws SchemeNotFoundException if the Scheme option is not found
     */
    @Transactional
    public SchemeOptionModel hardUpdateOne(Long id, SchemeOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Scheme Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Scheme Option model cannot be null");
        }
        SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                .orElseThrow(()-> new SchemeNotFoundException("Scheme Option not found with Id:" + id));

        modelMapper.map(model, schemeOptionModel);
        schemeOptionModel.setUpdatedAt(LocalDateTime.now());
        return schemeOptionRepository.save(schemeOptionModel);
    }


    /**
     * Updates multiple SchemeOptionModel models by their IDs
     * @param schemeOptionModels List of SchemeOptionModel objects containing updated data
     * @return List of updated SchemeOptionModel objects
     * @throws IllegalArgumentException if any Scheme Option ID is null
     * @throws SchemeNotFoundException if any SchemeOption is not found
     */
    @Transactional
    public List<SchemeOptionModel> hardUpdateAll(List<SchemeOptionModel> schemeOptionModels){
        if (schemeOptionModels == null || schemeOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Scheme model list cannot be null or empty");
        }
        List<SchemeOptionModel> updatedModels = new ArrayList<>();
        for (SchemeOptionModel model: schemeOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Scheme Option id cannot be null on Hard update all");
            }
            SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new SchemeNotFoundException("Scheme option not found with id:" + model.getId()));

            modelMapper.map(model, schemeOptionModel);
            schemeOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(schemeOptionRepository.save(schemeOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a Scheme option by ID in a transactional manner.
     *
     * @param id The ID of the Selection Method option to soft delete
     * @return The soft-deleted SchemeOptionModel
     * @throws IllegalArgumentException if the Scheme option ID is null
     * @throws SchemeNotFoundException if the Scheme option is not found
     * @throws IllegalStateException if the Scheme option is already deleted
     */
    @Transactional
    public SchemeOptionModel softDeleteSchemeOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Scheme Option ID cannot be null or empty");
        }
        SchemeOptionModel schemeOptionModel = schemeOptionRepository.findById(id)
                .orElseThrow(()-> new SchemeNotFoundException("Scheme option not found with id:" + id));
        if (schemeOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Scheme option with id:" + id + "is already deleted");
        }
        schemeOptionModel.setDeletedAt(LocalDateTime.now());
        return schemeOptionRepository.save(schemeOptionModel);
    }
    /**
     * Hard deletes a Selection Method option by ID
     * @param id ID of the Selection Method to hard delete
     */
    @Transactional
    public void hardDeleteSchemeOption(Long id){
        if (!schemeOptionRepository.existsById(id)){
            throw new SchemeNotFoundException("Scheme option not found with id:" + id);
        }
        schemeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Scheme option by their IDs.
     *
     * @param ids List of Scheme option IDs to be soft deleted
     * @return List of soft deleted SchemeOption objects
     * @throws SchemeNotFoundException if any Scheme option IDs are not found
     * @throws IllegalStateException if any Scheme option is already deleted
     */
    @Transactional
    public List<SchemeOptionModel> softDeleteSchemeOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Scheme option IDs list cannot be null or empty");
        }
        List<SchemeOptionModel> schemeOptionModels = schemeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (SchemeOptionModel model: schemeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new SchemeNotFoundException("Scheme option not Found with ids:" + missingIds);
        }
        for (SchemeOptionModel model: schemeOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Scheme option with id:" + model.getId() + "is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        schemeOptionRepository.saveAll(schemeOptionModels);
        return schemeOptionModels;
    }

    /**
     * Hard deletes multiple Scheme options by IDs
     * @param ids List of Scheme option IDs to hard delete
     */
    @Transactional
    public void hardDeleteSchemeOptions(List<Long> ids){
        List<SchemeOptionModel> schemeOptionModels = schemeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (SchemeOptionModel model: schemeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new SchemeNotFoundException("Scheme option not found with ids:" +missingIds);
        }
        schemeOptionRepository.deleteAllById(ids);
    }
}
