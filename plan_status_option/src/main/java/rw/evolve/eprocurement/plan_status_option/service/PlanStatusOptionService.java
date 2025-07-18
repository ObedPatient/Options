/**
 * Service for managing PlanStatusOptionModel entities.
 * Provides functionality to create, read, update, and delete PlanStatusOption data, supporting both
 * soft and hard deletion operation.
 */
package rw.evolve.eprocurement.plan_status_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.plan_status_option.exception.PlanStatusAlreadExistException;
import rw.evolve.eprocurement.plan_status_option.exception.PlanStatusNotFoundException;
import rw.evolve.eprocurement.plan_status_option.model.PlanStatusOptionModel;
import rw.evolve.eprocurement.plan_status_option.repository.PlanStatusOptionRepositoy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanStatusOptionService {


    @Autowired
    private PlanStatusOptionRepositoy planStatusOptionRepositoy;


    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single PlanStatusOption entity.
     *
     * @param planStatusOptionModel the PlanStatusOptionModel to be created
     * @return the saved PlanStatusOption model
     */
    @Transactional
    public PlanStatusOptionModel createPlanStatusOption(PlanStatusOptionModel planStatusOptionModel){
        if (planStatusOptionRepositoy.existsByName(planStatusOptionModel.getName())){
            throw new PlanStatusAlreadExistException("Plan Status Option Already exists: " + planStatusOptionModel.getName());
        }
        return planStatusOptionRepositoy.save(planStatusOptionModel);
    }

    /**
     * Creates multiple PlanStatusOption entities, each with a unique ID.
     * Iterates through the provided list of PlanStatusOption models
     *
     * @param planStatusOptionModels the list of PlanStatusOption models to be created
     * @return a list of saved PlanStatusOption models.
     */
    @Transactional
    public List<PlanStatusOptionModel> createPlanStatusOptions(List<PlanStatusOptionModel> planStatusOptionModels){
        if (planStatusOptionModels == null){
            throw new IllegalArgumentException("Plan Status Option model cannot be null");
        }
        List<PlanStatusOptionModel> savedPlanStatusModels = new ArrayList<>();
        for (PlanStatusOptionModel planStatusOptionModel: planStatusOptionModels){
            PlanStatusOptionModel savedPlanStatusModel = planStatusOptionRepositoy.save(planStatusOptionModel);
            savedPlanStatusModels.add(savedPlanStatusModel);
        }
        return savedPlanStatusModels;
    }

    /**
     * Retrieves a single PlanStatusOption entity by its ID.
     * Throws a PlanStatusOptionNotFoundException if the PlanStatusOption is not found or has been deleted.
     *
     * @param id the ID of the PlanStatusOption to retrieve
     * @return the PlanStatusOption model if found and not deleted
     * @throws PlanStatusNotFoundException if the PlanStatusOption is not found.
     */
    @Transactional
    public PlanStatusOptionModel readOne(Long id){
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with id:" + id));
        if (planStatusOptionModel.getDeletedAt() != null){
            throw new PlanStatusNotFoundException("Plan Status Option not found with id:" + id);
        }
        return planStatusOptionModel;
    }

    /**
     * Retrieves a list of PlanStatusOption objects based on the provided PlanStatusOption IDs.
     *
     * @param planStatusOptionIds A list of PlanStatusOption IDs to retrieve
     * @return A list of PlanStatusOptionModel objects that are not marked as deleted
     * @throws PlanStatusNotFoundException if a PlanStatusOption with the given ID is not found
     */
    @Transactional
    public List<PlanStatusOptionModel> readMany(List<Long> planStatusOptionIds){
        if (planStatusOptionIds == null){
            throw new IllegalArgumentException("Plan Status Option id cannot be null or empty");
        }
        List<PlanStatusOptionModel> models = new ArrayList<>();
        for (Long id: planStatusOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Plan Status Option id cannot be null or empty");
            }
            PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                    .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with id:" + id));
            if (planStatusOptionModel.getDeletedAt() == null){
                models.add(planStatusOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all PlanStatusOption that are not marked as deleted
     * @return a List of PlanStatusOption object where deleted is null
     * @throws PlanStatusNotFoundException if no PlanStatusOption found
     */
    @Transactional
    public List<PlanStatusOptionModel> readAll(){
        List<PlanStatusOptionModel> planStatusOptionModels = planStatusOptionRepositoy.findByDeletedAtIsNull();
        if (planStatusOptionModels.isEmpty()){
            throw new PlanStatusNotFoundException("No Plan Status Option found");
        }
        return planStatusOptionModels;
    }


    /**
     * Retrieves all PlanStatusOptionModels, including those marked as deleted.
     *
     * @return A list of all PlanStatusOptionModel objects
     * @throws PlanStatusNotFoundException if no PlanStatusOption are found
     */
    @Transactional
    public List<PlanStatusOptionModel> hardReadAll(){
        List<PlanStatusOptionModel> planStatusOptionModels = planStatusOptionRepositoy.findAll();
        if (planStatusOptionModels.isEmpty()){
            throw new PlanStatusNotFoundException("No Plan Status Option found");
        }
        return planStatusOptionModels;
    }

    /**
     * Updates a single PlanStatusOptionModel model identified by the provided ID.
     * @param id The ID of the PlanStatusOption to update
     * @param model The PlanStatusOptionModel containing updated data
     * @return The updated PlanStatusOptionModel
     * @throws PlanStatusNotFoundException if the PlanStatusOptionModel is not found or is marked as deleted
     */
    @Transactional
    public PlanStatusOptionModel updateOne(Long id, PlanStatusOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Plan Status Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Plan Status Option cannot be null");
        }
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with id:" + id));
        if (planStatusOptionModel.getDeletedAt() != null){
            throw new PlanStatusNotFoundException("Plan Status Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, planStatusOptionModel);
        planStatusOptionModel.setUpdatedAt(LocalDateTime.now());
        return planStatusOptionRepositoy.save(planStatusOptionModel);
    }

    /**
     * Updates multiple PlanStatusOption models in a transactional manner.
     *
     * @param models List of PlanStatusOptionModel objects containing updated data
     * @return List of updated PlanStatusOptionModel objects
     * @throws IllegalArgumentException if any PlanStatusOptionModel is null
     * @throws PlanStatusNotFoundException if a PlanStatusOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<PlanStatusOptionModel> updateMany(List<PlanStatusOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Plan Status Option model List cannot be null or empty");
        }
        List<PlanStatusOptionModel> updatedModel = new ArrayList<>();
        for (PlanStatusOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Plan Status Option id cannot be null");
            }
            PlanStatusOptionModel existingModel = planStatusOptionRepositoy.findById(model.getId())
                    .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new PlanStatusNotFoundException("Plan Status Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(planStatusOptionRepositoy.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single PlanStatusOption model by ID.
     * @param id The ID of the PlanStatusOption to update
     * @param model The PlanStatusOptionModel containing updated data
     * @return The updated PlanStatusOptionModel
     * @throws IllegalArgumentException if the PlanStatusOption ID is null
     * @throws PlanStatusNotFoundException if the PlanStatusOption is not found
     */
    @Transactional
    public PlanStatusOptionModel hardUpdateOne(Long id, PlanStatusOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Plan Status Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Plan Status Option model cannot be null");
        }
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with Id:" + id));

        modelMapper.map(model, planStatusOptionModel);
        planStatusOptionModel.setUpdatedAt(LocalDateTime.now());
        return planStatusOptionRepositoy.save(planStatusOptionModel);
    }

    /**
     * Updates multiple PlanStatusOptionModel models by their IDs
     * @param planStatusOptionModels List of PlanStatusOptionModel objects containing updated data
     * @return List of updated PlanStatusOptionModel objects
     * @throws IllegalArgumentException if any Plan Status Option ID is null
     * @throws PlanStatusNotFoundException if any PlanStatusOption is not found
     */
    @Transactional
    public List<PlanStatusOptionModel> hardUpdateAll(List<PlanStatusOptionModel> planStatusOptionModels){
        if (planStatusOptionModels == null || planStatusOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Plan Status Option model list cannot be null or empty");
        }
        List<PlanStatusOptionModel> updatedModels = new ArrayList<>();
        for (PlanStatusOptionModel model: planStatusOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Plan Status Option id cannot be null on Hard update all");
            }
            PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(model.getId())
                    .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with id:" + model.getId()));

            modelMapper.map(model, planStatusOptionModel);
            planStatusOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(planStatusOptionRepositoy.save(planStatusOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a PlanStatusOption by ID in a transactional manner.
     *
     * @param id The ID of the PlanStatusOption to soft delete
     * @return The soft-deleted PlanStatusOptionModel
     * @throws IllegalArgumentException if the PlanStatusOption ID is null
     * @throws PlanStatusNotFoundException if the PlanStatusOption is not found
     * @throws IllegalStateException if the PlanStatusOption is already deleted
     */
    @Transactional
    public PlanStatusOptionModel softDeletePlanStatusOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Plan Status Option ID cannot be null or empty");
        }
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionRepositoy.findById(id)
                .orElseThrow(()-> new PlanStatusNotFoundException("Plan Status Option not found with id:" + id));
        if (planStatusOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Plan Status Option with id:" + id + " is already deleted");
        }
        planStatusOptionModel.setDeletedAt(LocalDateTime.now());
        return planStatusOptionRepositoy.save(planStatusOptionModel);
    }

    /**
     * Hard deletes a PlanStatusOption by ID
     * @param id ID of the PlanStatusOption to hard delete
     */
    @Transactional
    public void hardDeletePlanStatusOption(Long id){
        if (!planStatusOptionRepositoy.existsById(id)){
            throw new PlanStatusNotFoundException("Plan Status Option not found with id:" + id);
        }
        planStatusOptionRepositoy.deleteById(id);
    }

    /**
     * Soft deletes multiple PlanStatusOption by their IDs.
     *
     * @param ids List of PlanStatusOption IDs to be soft deleted
     * @return List of soft deleted PlanStatusOption objects
     * @throws PlanStatusNotFoundException if any PlanStatusOption IDs are not found
     * @throws IllegalStateException if any PlanStatusOption is already deleted
     */
    @Transactional
    public List<PlanStatusOptionModel> softDeletePlanStatusOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Plan Status Option IDs list cannot be null or empty");
        }
        List<PlanStatusOptionModel> planStatusOptionModels = planStatusOptionRepositoy.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (PlanStatusOptionModel model: planStatusOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new PlanStatusNotFoundException("Plan Status Option not Found with ids:" + missingIds);
        }
        for (PlanStatusOptionModel model: planStatusOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Plan Status Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        planStatusOptionRepositoy.saveAll(planStatusOptionModels);
        return planStatusOptionModels;
    }

    /**
     * Hard deletes multiple PlanStatusOption by IDs
     * @param ids List of PlanStatusOption IDs to hard delete
     */
    @Transactional
    public void hardDeletePlanStatusOptions(List<Long> ids){
        List<PlanStatusOptionModel> planStatusOptionModels = planStatusOptionRepositoy.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (PlanStatusOptionModel model: planStatusOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new PlanStatusNotFoundException("Plan Status Option not found with ids:" + missingIds);
        }
        planStatusOptionRepositoy.deleteAllById(ids);
    }


}
