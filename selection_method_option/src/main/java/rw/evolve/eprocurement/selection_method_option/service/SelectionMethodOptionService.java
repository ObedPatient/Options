/**
 * Service for managing SelectionMethodOption entities.
 * Provides functionality to create, read, update, and delete SelectionMethodOption data, supporting both
 * soft and hard deletion operation.
 */
package rw.evolve.eprocurement.selection_method_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.selection_method_option.exception.SelectionMethodAlreadyExistException;
import rw.evolve.eprocurement.selection_method_option.exception.SelectionMethodNotFoundException;
import rw.evolve.eprocurement.selection_method_option.model.SelectionMethodOptionModel;
import rw.evolve.eprocurement.selection_method_option.repository.SelectionMethodOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelectionMethodOptionService {


    @Autowired
    private SelectionMethodOptionRepository selectionMethodOptionRepository;


    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Selection Method option entity.
     *
     * @param @SelectionMethodOptionModel the SelectionMethodOptionModel to be created
     * @return the saved SelectionMethodOption model
     */
    @Transactional
    public SelectionMethodOptionModel createSelectionMethod(SelectionMethodOptionModel selectionMethodOptionModel){
        if (selectionMethodOptionRepository.existsByName(selectionMethodOptionModel.getName())){
            throw new SelectionMethodAlreadyExistException("Selection Method Already exists: " + selectionMethodOptionModel.getName());
        }
        return selectionMethodOptionRepository.save(selectionMethodOptionModel);
    }

    /**
     * Creates multiple Selection Method entities, each with a unique ID.
     * Iterates through the provided list of Selection Method models
     *
     * @param selectionMethodOptionModels the list of Selection Method option models to be created
     * @return a list of saved Selection Method Option models.
     */
    @Transactional
    public List<SelectionMethodOptionModel> createSelectionMethods(List<SelectionMethodOptionModel> selectionMethodOptionModels){
        if (selectionMethodOptionModels == null){
            throw new IllegalArgumentException("Selection Method option model cannot be null");
        }
        List<SelectionMethodOptionModel> savedSelectionMethodModels = new ArrayList<>();
        for (SelectionMethodOptionModel selectionMethodOptionModel: selectionMethodOptionModels){
            SelectionMethodOptionModel savedSelectionMethodModel = selectionMethodOptionRepository.save(selectionMethodOptionModel);
            savedSelectionMethodModels.add(savedSelectionMethodModel);
        }
        return savedSelectionMethodModels;
    }

    /**
     * Retrieves a single Selection Method option entity by its ID.
     * Throws a SelectionMethodOptionNotFoundException if the Selection Method option is not found or has been deleted.
     *
     * @param @SelectionMethodOption id the ID of the Selection Method option to retrieve
     * @return the Selection Method option model if found and not deleted
     * @throws SelectionMethodNotFoundException if the Selection Method option is not found.
     */
    @Transactional
    public SelectionMethodOptionModel readOne(Long id){
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method option not found with id:" + id));
        if (selectionMethodOptionModel.getDeletedAt() != null){
            throw new SelectionMethodNotFoundException("Selection Method option not found with id:" + id);
        }
        return selectionMethodOptionModel;
    }

    /**
     * Retrieves a list of SelectionMethodOption objects based on the provided SelectionMethodOption IDs.
     *
     * @param selectionMethodOptionIds A list of SelectionMethodOption IDs to retrieve
     * @return A list of SelectionMethodOptionModel objects that are not marked as deleted
     * @throws SelectionMethodNotFoundException if a SelectionMethodOption with the given ID is not found
     */
    @Transactional
    public List<SelectionMethodOptionModel> readMany(List<Long> selectionMethodOptionIds){
        if (selectionMethodOptionIds == null){
            throw new IllegalArgumentException("Selection Method option id cannot be null or empty");
        }
        List<SelectionMethodOptionModel> models = new ArrayList<>();
        for (Long id: selectionMethodOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Selection Method option id cannot be null or emty");
            }
            SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                    .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method Not found with id:" + id));
            if (selectionMethodOptionModel.getDeletedAt() == null){
                models.add(selectionMethodOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all SelectionMethod Option that are not marked as deleted
     * @return a List of Selection Method option object where deleted in null
     * @throws SelectionMethodNotFoundException if no Selection Method option found
     */
    @Transactional
    public List<SelectionMethodOptionModel> readAll(){
        List<SelectionMethodOptionModel> selectionMethodOptionModels = selectionMethodOptionRepository.findByDeletedAtIsNull();
        if (selectionMethodOptionModels.isEmpty()){
            throw new SelectionMethodNotFoundException("No Selection Method found");
        }
        return selectionMethodOptionModels;
    }

    /**
     * Retrieves all SelectionMethodOptionModels, including those marked as deleted.
     *
     * @return A list of all SelectionMethodOptionModel objects
     * @throws SelectionMethodNotFoundException if no SelectionMethodOption are found
     */
    @Transactional
    public List<SelectionMethodOptionModel> hardReadAll(){
        List<SelectionMethodOptionModel> selectionMethodOptionModels = selectionMethodOptionRepository.findAll();
        if (selectionMethodOptionModels.isEmpty()){
            throw new SelectionMethodNotFoundException("No Selection Method option found");
        }
        return selectionMethodOptionModels;
    }

    /**
     * Updates a single SelectionMethodOptionModel model identified by the provided ID.
     * @param id The ID of the SelectionMethodOption to update
     * @param model The SelectionMethodOptionModel containing updated data
     * @return The updated SelectionMethodOptionModel
     * @throws SelectionMethodNotFoundException if the SelectionMethodOptionModel is not found or is marked as deleted
     */
    @Transactional
    public SelectionMethodOptionModel updateOne(Long id, SelectionMethodOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Selection Method option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Selection Method Option cannot be null");
        }
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method option not found with id:" + id));
        if (selectionMethodOptionModel.getDeletedAt() != null){
            throw new SelectionMethodNotFoundException("Selection Method option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, selectionMethodOptionModel);
        selectionMethodOptionModel.setUpdatedAt(LocalDateTime.now());
        return selectionMethodOptionRepository.save(selectionMethodOptionModel);
    }

    /**
     * Updates multiple SelectionTypeOption models in a transactional manner.
     *
     * @param models List of SelectionTypeOptionModel objects containing updated data
     * @return List of updated SelectionTypeOptionModel objects
     * @throws IllegalArgumentException if any SelectionTypeOptionModel is null
     * @throws SelectionMethodNotFoundException if a SelectionTypeOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<SelectionMethodOptionModel> updateMany(List<SelectionMethodOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Selection Method option model List cannot be null or empty");
        }
        List<SelectionMethodOptionModel> updatedModel = new ArrayList<>();
        for (SelectionMethodOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Selection Method option id cannot be null");
            }
            SelectionMethodOptionModel existingModel = selectionMethodOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new SelectionMethodNotFoundException("Selection Method option with id:" + model.getId() + "marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(selectionMethodOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Selection Method option model by ID.
     * @param id The ID of the Selection Method option to update
     * @param model The SelectionMethodOptionModel containing updated data
     * @return The updated SelectionMethodOptionModel
     * @throws IllegalArgumentException if the Selection Method option ID is null
     * @throws SelectionMethodNotFoundException if the Selection Method option is not found
     */
    @Transactional
    public SelectionMethodOptionModel hardUpdateOne(Long id, SelectionMethodOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Selection Method Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Selection Method Option model cannot be null");
        }
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method Option not found with Id:" + id));

        modelMapper.map(model, selectionMethodOptionModel);
        selectionMethodOptionModel.setUpdatedAt(LocalDateTime.now());
        return selectionMethodOptionRepository.save(selectionMethodOptionModel);
    }


    /**
     * Updates multiple SelectionMethodOptionModel models by their IDs
     * @param SelectionMethodOptionModels List of SelectionMethodOptionModel objects containing updated data
     * @return List of updated SelectionMethodOptionModel objects
     * @throws IllegalArgumentException if any Selection Method Option ID is null
     * @throws SelectionMethodNotFoundException if any SelectionMethodOption is not found
     */
    @Transactional
    public List<SelectionMethodOptionModel> hardUpdateAll(List<SelectionMethodOptionModel> selectionMethodOptionModels){
        if (selectionMethodOptionModels == null || selectionMethodOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Selection Method model list cannot be null or empty");
        }
        List<SelectionMethodOptionModel> updatedModels = new ArrayList<>();
        for (SelectionMethodOptionModel model: selectionMethodOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("SelectionMethodOption id cannot be null on Hard update all");
            }
            SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method option not found with id:" + model.getId()));

            modelMapper.map(model, selectionMethodOptionModel);
            selectionMethodOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(selectionMethodOptionRepository.save(selectionMethodOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a Selection Method option by ID in a transactional manner.
     *
     * @param id The ID of the Selection Method option to soft delete
     * @return The soft-deleted SelectionMethodOptionModel
     * @throws IllegalArgumentException if the Selection Method option ID is null
     * @throws SelectionMethodNotFoundException if the Selection Method option is not found
     * @throws IllegalStateException if the Selection Method option is already deleted
     */
    @Transactional
    public SelectionMethodOptionModel softDeleteSelectionMethodOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Selection Method Option ID cannot be null or empty");
        }
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                .orElseThrow(()-> new SelectionMethodNotFoundException("Selection Method option not found with id:" + id));
        if (selectionMethodOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Selection Method option with id:" + id + "is already deleted");
        }
        selectionMethodOptionModel.setDeletedAt(LocalDateTime.now());
        return selectionMethodOptionRepository.save(selectionMethodOptionModel);
    }
    /**
     * Hard deletes a Selection Method option by ID
     * @param id ID of the Selection Method to hard delete
     */
    @Transactional
    public void hardDeleteSelectionMethodOption(Long id){
        if (!selectionMethodOptionRepository.existsById(id)){
            throw new SelectionMethodNotFoundException("Selection Method option not found with id:" + id);
        }
        selectionMethodOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Selection Method option by their IDs.
     *
     * @param ids List of Selection Method option IDs to be soft deleted
     * @return List of soft deleted ProcurementMethodOption objects
     * @throws SelectionMethodNotFoundException if any Selection Method option IDs are not found
     * @throws IllegalStateException if any Selection Method option is already deleted
     */
    @Transactional
    public List<SelectionMethodOptionModel> softDeleteSelectionMethodOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Selection Method option IDs list cannot be null or empty");
        }
        List<SelectionMethodOptionModel> selectionMethodOptionModels = selectionMethodOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (SelectionMethodOptionModel model: selectionMethodOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new SelectionMethodNotFoundException("Selection Method option not Found with ids:" + missingIds);
        }
        for (SelectionMethodOptionModel model: selectionMethodOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Selection Method option with id:" + model.getId() + "is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        selectionMethodOptionRepository.saveAll(selectionMethodOptionModels);
        return selectionMethodOptionModels;
    }

    /**
     * Hard deletes multiple Selection Method options by IDs
     * @param ids List of Selection Method option IDs to hard delete
     */
    @Transactional
    public void hardDeleteSelectionMethodOptions(List<Long> ids){
        List<SelectionMethodOptionModel> selectionMethodOptionModels = selectionMethodOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (SelectionMethodOptionModel model: selectionMethodOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new SelectionMethodNotFoundException("Selection type option not found with ids:" +missingIds);
        }
        selectionMethodOptionRepository.deleteAllById(ids);
    }
}
