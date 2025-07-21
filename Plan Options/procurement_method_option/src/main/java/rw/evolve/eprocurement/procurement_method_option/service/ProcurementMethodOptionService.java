/**
 * Service for managing ProcurementMethodOption entities.
 * Provides functionality to create, read, update, and delete ProcurementMethodOption data, supporting both
 * soft and hard deletion operation.
 */
package rw.evolve.eprocurement.procurement_method_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_method_option.exception.ProcurementMethodNotFoundException;
import rw.evolve.eprocurement.procurement_method_option.exception.ProcurementMethodOptionAlreadyExistException;
import rw.evolve.eprocurement.procurement_method_option.model.ProcurementMethodOptionModel;
import rw.evolve.eprocurement.procurement_method_option.repository.ProcurementMethodOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementMethodOptionService {



    @Autowired
    private ProcurementMethodOptionRepository procurementMethodOptionRepository;


    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Procurement Method option entity.
     *
     * @param @ProcurementMethodOptionModel the ProcurementMethodOptionModel to be created
     * @return the saved ProcurementMethodOption model
     */
    @Transactional
    public ProcurementMethodOptionModel createProcurementMethod(ProcurementMethodOptionModel procurementMethodOptionModel){
        if (procurementMethodOptionRepository.existsByName(procurementMethodOptionModel.getName())){
            throw new ProcurementMethodOptionAlreadyExistException("Procurement Method Already exists: " + procurementMethodOptionModel.getName());
        }
        return procurementMethodOptionRepository.save(procurementMethodOptionModel);
    }

    /**
     * Creates multiple Procurement Method entities, each with a unique ID.
     * Iterates through the provided list of Procurement Method models
     *
     * @param procurementMethodOptionModels the list of Procurement Method option models to be created
     * @return a list of saved Procurement Method Option models.
     */
    @Transactional
    public List<ProcurementMethodOptionModel> createProcurementMethods(List<ProcurementMethodOptionModel> procurementMethodOptionModels){
        if (procurementMethodOptionModels == null){
            throw new IllegalArgumentException("Procurement Method option model cannot be null");
        }
        List<ProcurementMethodOptionModel> savedProcurementMethodModels = new ArrayList<>();
        for (ProcurementMethodOptionModel procurementMethodOptionModel: procurementMethodOptionModels){
            ProcurementMethodOptionModel savedProcurementMethodModel = procurementMethodOptionRepository.save(procurementMethodOptionModel);
            savedProcurementMethodModels.add(savedProcurementMethodModel);
        }
        return savedProcurementMethodModels;
    }

    /**
     * Retrieves a single Account Method option entity by its ID.
     * Throws a ProcurementMethodOptionNotFoundException if the Account Method option is not found or has been deleted.
     *
     * @param @ProcurementMethodOption id the ID of the Procurement Method option to retrieve
     * @return the Procurement Method option model if found and not deleted
     * @throws ProcurementMethodNotFoundException if the Procurement Method option is not found.
     */
    @Transactional
    public ProcurementMethodOptionModel readOne(Long id){
        ProcurementMethodOptionModel procurementTypeOptionModel = procurementMethodOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + id));
        if (procurementTypeOptionModel.getDeletedAt() != null){
            throw new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + id);
        }
        return procurementTypeOptionModel;
    }

    /**
     * Retrieves a list of ProcurementMethodOption objects based on the provided ProcurementMethodOption IDs.
     *
     * @param procurementMethodOptionIds A list of ProcurementMethodOption IDs to retrieve
     * @return A list of ProcurementMethodOptionModel objects that are not marked as deleted
     * @throws ProcurementMethodNotFoundException if a ProcurementMethodOption with the given ID is not found
     */
    @Transactional
    public List<ProcurementMethodOptionModel> readMany(List<Long> procurementMethodOptionIds){
        if (procurementMethodOptionIds == null){
            throw new IllegalArgumentException("Procurement Method option id cannot be null or empty");
        }
        List<ProcurementMethodOptionModel> models = new ArrayList<>();
        for (Long id: procurementMethodOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Procurement Method option id cannot be null or emty");
            }
            ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                    .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method Not found with id:" + id));
            if (procurementMethodOptionModel.getDeletedAt() == null){
                models.add(procurementMethodOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all ProcurementMethod Option that are not marked as deleted
     * @return a List of Procurement Method option object where deleted in null
     * @throws ProcurementMethodNotFoundException if no Procurement Method option found
     */
    @Transactional
    public List<ProcurementMethodOptionModel> readAll(){
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionRepository.findByDeletedAtIsNull();
        if (procurementMethodOptionModels.isEmpty()){
            throw new ProcurementMethodNotFoundException("No Procurement Method found");
        }
        return procurementMethodOptionModels;
    }

    /**
     * Retrieves all ProcurementMethodOptionModels, including those marked as deleted.
     *
     * @return A list of all ProcurementMethodOptionModel objects
     * @throws ProcurementMethodNotFoundException if no ProcurementMethodOption are found
     */
    @Transactional
    public List<ProcurementMethodOptionModel> hardReadAll(){
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionRepository.findAll();
        if (procurementMethodOptionModels.isEmpty()){
            throw new ProcurementMethodNotFoundException("No Procurement Method option found");
        }
        return procurementMethodOptionModels;
    }

    /**
     * Updates a single ProcurementMethodOptionModel model identified by the provided ID.
     * @param id The ID of the ProcurementMethodOption to update
     * @param model The ProcurementMethodOptionModel containing updated data
     * @return The updated ProcurementMethodOptionModel
     * @throws ProcurementMethodNotFoundException if the ProcurementMethodOptionModel is not found or is marked as deleted
     */
    @Transactional
    public ProcurementMethodOptionModel updateOne(Long id, ProcurementMethodOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Procurement Method option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Procurement Method Option cannot be null");
        }
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + id));
        if (procurementMethodOptionModel.getDeletedAt() != null){
            throw new ProcurementMethodNotFoundException("Procurement Method option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, procurementMethodOptionModel);
        procurementMethodOptionModel.setUpdatedAt(LocalDateTime.now());
        return procurementMethodOptionRepository.save(procurementMethodOptionModel);
    }

    /**
     * Updates multiple ProcurementTypeOption models in a transactional manner.
     *
     * @param models List of ProcurementTypeOptionModel objects containing updated data
     * @return List of updated ProcurementTypeOptionModel objects
     * @throws IllegalArgumentException if any ProcurementTypeOptionModel is null
     * @throws ProcurementMethodNotFoundException if a ProcurementTypeOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<ProcurementMethodOptionModel> updateMany(List<ProcurementMethodOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Procurement Method option model List cannot be null or empty");
        }
        List<ProcurementMethodOptionModel> updatedModel = new ArrayList<>();
        for (ProcurementMethodOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Procurement Method option id cannot be null");
            }
            ProcurementMethodOptionModel existingModel = procurementMethodOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new ProcurementMethodNotFoundException("Procurement Method option with id:" + model.getId() + "marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(procurementMethodOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single procurement Method option model by ID.
     * @param id The ID of the procurement Method option to update
     * @param model The ProcurementMethodOptionModel containing updated data
     * @return The updated ProcurementMethodOptionModel
     * @throws IllegalArgumentException if the Procurement Method option ID is null
     * @throws ProcurementMethodNotFoundException if the Procurement Method option is not found
     */
    @Transactional
    public ProcurementMethodOptionModel hardUpdateOne(Long id, ProcurementMethodOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Procurement Method Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Procurement Method Option model cannot be null");
        }
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method Option not found with Id:" + id));

        modelMapper.map(model, procurementMethodOptionModel);
        procurementMethodOptionModel.setUpdatedAt(LocalDateTime.now());
        return procurementMethodOptionRepository.save(procurementMethodOptionModel);
    }


    /**
     * Updates multiple ProcurementMethodOptionModel models by their IDs
     * @param procurementMethodOptionModels List of ProcurementMethodOptionModel objects containing updated data
     * @return List of updated ProcurementMethodOptionModel objects
     * @throws IllegalArgumentException if any Procurement Method Option ID is null
     * @throws ProcurementMethodNotFoundException if any ProcurementMethodOption is not found
     */
    @Transactional
    public List<ProcurementMethodOptionModel> hardUpdateAll(List<ProcurementMethodOptionModel> procurementMethodOptionModels){
        if (procurementMethodOptionModels == null || procurementMethodOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Procurement Method model list cannot be null or empty");
        }
        List<ProcurementMethodOptionModel> updatedModels = new ArrayList<>();
        for (ProcurementMethodOptionModel model: procurementMethodOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("ProcurementMethodOption id cannot be null on Hard update all");
            }
            ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + model.getId()));

            modelMapper.map(model, procurementMethodOptionModel);
            procurementMethodOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(procurementMethodOptionRepository.save(procurementMethodOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a procurement Method option by ID in a transactional manner.
     *
     * @param id The ID of the procurement Method option to soft delete
     * @return The soft-deleted ProcurementMethodOptionModel
     * @throws IllegalArgumentException if the procurement Method option ID is null
     * @throws ProcurementMethodNotFoundException if the procurement Method option is not found
     * @throws IllegalStateException if the procurement Method option is already deleted
     */
    @Transactional
    public ProcurementMethodOptionModel softDeleteProcurementMethodOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Procurement Method Option ID cannot be null or empty");
        }
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + id));
        if (procurementMethodOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Procurement Method option with id:" + id + "is already deleted");
        }
        procurementMethodOptionModel.setDeletedAt(LocalDateTime.now());
        return procurementMethodOptionRepository.save(procurementMethodOptionModel);
    }
    /**
     * Hard deletes a Procurement Method option by ID
     * @param id ID of the Procurement Method to hard delete
     */
    @Transactional
    public void hardDeleteProcurementMethodOption(Long id){
        if (!procurementMethodOptionRepository.existsById(id)){
            throw new ProcurementMethodNotFoundException("Procurement Method option not found with id:" + id);
        }
        procurementMethodOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple procurement Method option by their IDs.
     *
     * @param ids List of procurement Method option IDs to be soft deleted
     * @return List of soft deleted ProcurementMethodOption objects
     * @throws ProcurementMethodNotFoundException if any procurement Method option IDs are not found
     * @throws IllegalStateException if any procurement Method option is already deleted
     */
    @Transactional
    public List<ProcurementMethodOptionModel> softDeleteProcurementMethodOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Procurement Method option IDs list cannot be null or empty");
        }
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (ProcurementMethodOptionModel model: procurementMethodOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new ProcurementMethodNotFoundException("Procurement Method option not Found with ids:" + missingIds);
        }
        for (ProcurementMethodOptionModel model: procurementMethodOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Procurement Method option with id:" + model.getId() + "is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        procurementMethodOptionRepository.saveAll(procurementMethodOptionModels);
        return procurementMethodOptionModels;
    }

    /**
     * Hard deletes multiple procurement Method options by IDs
     * @param ids List of Procurement Method option IDs to hard delete
     */
    @Transactional
    public void hardDeleteProcurementMethodOptions(List<Long> ids){
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (ProcurementMethodOptionModel model: procurementMethodOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new ProcurementMethodNotFoundException("Procurement type option not found with ids:" +missingIds);
        }
        procurementMethodOptionRepository.deleteAllById(ids);
    }
}
