/**
 * Service for managing ProcurementTypeOption entities.
 * Provides functionality to create, read, update, and delete ProcurementTypeOption data, supporting both
 * soft and hard deletion operation.
 */
package rw.evolve.eprocurement.procurement_type_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.procurement_type_option.exception.ProcurementTypeExistException;
import rw.evolve.eprocurement.procurement_type_option.exception.ProcurementTypeOptionNotFoundException;
import rw.evolve.eprocurement.procurement_type_option.model.ProcurementTypeOptionModel;
import rw.evolve.eprocurement.procurement_type_option.repository.ProcurementTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementTypeOptionService {

    @Autowired
    private ProcurementTypeOptionRepository procurementTypeOptionRepository;


    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Procurement type option entity.
     *
     * @param @ProcurementTypeOptionModel the ProcurementTypeOptionModel to be created
     * @return the saved ProcurementTypeOption model
     */
    @Transactional
    public ProcurementTypeOptionModel createProcurementType(ProcurementTypeOptionModel procurementTypeOptionModel){
        if (procurementTypeOptionRepository.existsByName(procurementTypeOptionModel.getName())){
            throw new ProcurementTypeExistException("Procurement type Already exists: " + procurementTypeOptionModel.getName());
        }
        return procurementTypeOptionRepository.save(procurementTypeOptionModel);
    }

    /**
     * Creates multiple Procurement type entities, each with a unique ID.
     * Iterates through the provided list of Account type models
     *
     * @param procurementTypeOptionModels the list of Procurement type option models to be created
     * @return a list of saved Procurement type Option models.
     */
    @Transactional
    public List<ProcurementTypeOptionModel> createProcurementTypes(List<ProcurementTypeOptionModel> procurementTypeOptionModels){
        if (procurementTypeOptionModels == null){
            throw new IllegalArgumentException("Procurement type option model cannot be null");
        }
        List<ProcurementTypeOptionModel> savedProcurementTypeModels = new ArrayList<>();
        for (ProcurementTypeOptionModel procurementTypeOptionModel: procurementTypeOptionModels){
            ProcurementTypeOptionModel savedProcurementTypeModel = procurementTypeOptionRepository.save(procurementTypeOptionModel);
            savedProcurementTypeModels.add(savedProcurementTypeModel);
        }
        return savedProcurementTypeModels;
    }

    /**
     * Retrieves a single Account type option entity by its ID.
     * Throws a ProcurementTypeOptionNotFoundException if the Account type option is not found or has been deleted.
     *
     * @param @ProcurementTypeOption id the ID of the Procurement type option to retrieve
     * @return the Procurement type option model if found and not deleted
     * @throws ProcurementTypeExistException if the Procurement type option is not found.
     */
    @Transactional
    public ProcurementTypeOptionModel readOne(Long id){
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementTypeExistException("Procurement Type option not found with id:" + id));
        if (procurementTypeOptionModel.getDeletedAt() != null){
            throw new ProcurementTypeExistException("Procurement Type option not found with id:" + id);
        }
        return procurementTypeOptionModel;
    }

    /**
     * Retrieves a list of ProcurementTypeOption objects based on the provided ProcurementTypeOption IDs.
     *
     * @param procurementTypeOptionIds A list of AccountTypeOption IDs to retrieve
     * @return A list of ProcurementTypeOptionModel objects that are not marked as deleted
     * @throws ProcurementTypeExistException if a ProcurementTypeOption with the given ID is not found
     */
    @Transactional
    public List<ProcurementTypeOptionModel> readMany(List<Long> procurementTypeOptionIds){
        if (procurementTypeOptionIds == null){
            throw new IllegalArgumentException("Procurement type option id cannot be null or empty");
        }
        List<ProcurementTypeOptionModel> models = new ArrayList<>();
        for (Long id: procurementTypeOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Procurement type option id cannot be null or emty");
            }
            ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                    .orElseThrow(()-> new ProcurementTypeExistException("Procurement type Not found with id:" + id));
            if (procurementTypeOptionModel.getDeletedAt() == null){
                models.add(procurementTypeOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all ProcurementType Option that are not marked as deleted
     * @return a List of Procurement type option object where deleted in null
     * @throws ProcurementTypeExistException if no Account type option found
     */
    @Transactional
    public List<ProcurementTypeOptionModel> readAll(){
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionRepository.findByDeletedAtIsNull();
        if (procurementTypeOptionModels.isEmpty()){
            throw new ProcurementTypeExistException("No Procurement type found");
        }
        return procurementTypeOptionModels;
    }

    /**
     * Retrieves all ProcurementTypeOptionModels, including those marked as deleted.
     *
     * @return A list of all ProcurementTypeOptionModel objects
     * @throws ProcurementTypeOptionNotFoundException if no ProcurementTypeOption are found
     */
    @Transactional
    public List<ProcurementTypeOptionModel> hardReadAll(){
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionRepository.findAll();
        if (procurementTypeOptionModels.isEmpty()){
            throw new ProcurementTypeOptionNotFoundException("No Procurement type option found");
        }
        return procurementTypeOptionModels;
    }

    /**
     * Updates a single ProcurementTypeOptionModel model identified by the provided ID.
     * @param id The ID of the ProcurementTypeOption to update
     * @param model The ProcurementTypeOptionModel containing updated data
     * @return The updated ProcurementTypeOptionModel
     * @throws ProcurementTypeOptionNotFoundException if the ProcurementTypeOptionModel is not found or is marked as deleted
     */
    @Transactional
    public ProcurementTypeOptionModel updateOne(Long id, ProcurementTypeOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Procurement Type option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Procurement type Option cannot be null");
        }
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementTypeOptionNotFoundException("Procurement type option not found with id:" + id));
        if (procurementTypeOptionModel.getDeletedAt() != null){
            throw new ProcurementTypeOptionNotFoundException("Procurement type option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, procurementTypeOptionModel);
        procurementTypeOptionModel.setUpdatedAt(LocalDateTime.now());
        return procurementTypeOptionRepository.save(procurementTypeOptionModel);
    }

    /**
     * Updates multiple ProcurementTypeOption models in a transactional manner.
     *
     * @param models List of ProcurementTypeOptionModel objects containing updated data
     * @return List of updated ProcurementTypeOptionModel objects
     * @throws IllegalArgumentException if any ProcurementTypeOptionModel is null
     * @throws ProcurementTypeOptionNotFoundException if a ProcurementTypeOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<ProcurementTypeOptionModel> updateMany(List<ProcurementTypeOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Procurement Type option model List cannot be null or empty");
        }
        List<ProcurementTypeOptionModel> updatedModel = new ArrayList<>();
        for (ProcurementTypeOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Procurement type option id cannot be null");
            }
            ProcurementTypeOptionModel existingModel = procurementTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new ProcurementTypeOptionNotFoundException("Procurement type option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new ProcurementTypeOptionNotFoundException("Account type option with id:" + model.getId() + "marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(procurementTypeOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single procurement type option model by ID.
     * @param id The ID of the procurement type option to update
     * @param model The ProcurementTypeOptionModel containing updated data
     * @return The updated ProcurementTypeOptionModel
     * @throws IllegalArgumentException if the Procurement type option ID is null
     * @throws ProcurementTypeOptionNotFoundException if the Procurement type option is not found
     */
    @Transactional
    public ProcurementTypeOptionModel hardUpdateOne(Long id, ProcurementTypeOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Procurement type Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Procurement type Option model cannot be null");
        }
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementTypeOptionNotFoundException("Procurement Type Option not found with Id:" + id));

        modelMapper.map(model, procurementTypeOptionModel);
        procurementTypeOptionModel.setUpdatedAt(LocalDateTime.now());
        return procurementTypeOptionRepository.save(procurementTypeOptionModel);
    }


    /**
     * Updates multiple ProcurementTypeOptionModel models by their IDs
     * @param procurementTypeOptionModels List of ProcurementTypeOptionModel objects containing updated data
     * @return List of updated ProcurementTypeOptionModel objects
     * @throws IllegalArgumentException if any Procurement Type Option ID is null
     * @throws ProcurementTypeOptionNotFoundException if any ProcurementTypeOption is not found
     */
    @Transactional
    public List<ProcurementTypeOptionModel> hardUpdateAll(List<ProcurementTypeOptionModel> procurementTypeOptionModels){
        if (procurementTypeOptionModels == null || procurementTypeOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Procurement type model list cannot be null or empty");
        }
        List<ProcurementTypeOptionModel> updatedModels = new ArrayList<>();
        for (ProcurementTypeOptionModel model: procurementTypeOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("ProcurementTypeOption id cannot be null on Hard update all");
            }
            ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new ProcurementTypeOptionNotFoundException("Procurement type option not found with id:" + model.getId()));

            modelMapper.map(model, procurementTypeOptionModel);
            procurementTypeOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(procurementTypeOptionRepository.save(procurementTypeOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a procurement type option by ID in a transactional manner.
     *
     * @param id The ID of the procurement type option to soft delete
     * @return The soft-deleted ProcurementTypeOptionModel
     * @throws IllegalArgumentException if the procurement type option ID is null
     * @throws ProcurementTypeOptionNotFoundException if the procurement type option is not found
     * @throws IllegalStateException if the procurement type option is already deleted
     */
    @Transactional
    public ProcurementTypeOptionModel softDeleteProcurementTypeOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Procurement type Option ID cannot be null or empty");
        }
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionRepository.findById(id)
                .orElseThrow(()-> new ProcurementTypeOptionNotFoundException("Procurement type option not found with id:" + id));
        if (procurementTypeOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Procurement type option with id:" + id + "is already deleted");
        }
        procurementTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return procurementTypeOptionRepository.save(procurementTypeOptionModel);
    }
    /**
     * Hard deletes a Procurement type option by ID
     * @param id ID of the fiscal year to hard delete
     */
    @Transactional
    public void hardDeleteProcurementTypeOption(Long id){
        if (!procurementTypeOptionRepository.existsById(id)){
            throw new ProcurementTypeOptionNotFoundException("Procurement Type option not found with id:" + id);
        }
        procurementTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple procurement type option by their IDs.
     *
     * @param ids List of procurement type option IDs to be soft deleted
     * @return List of soft deleted ProcurementTypeOption objects
     * @throws ProcurementTypeOptionNotFoundException if any procurement type option IDs are not found
     * @throws IllegalStateException if any procurement type option is already deleted
     */
    @Transactional
    public List<ProcurementTypeOptionModel> softDeleteProcurementTypeOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Procurement type option IDs list cannot be null or empty");
        }
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (ProcurementTypeOptionModel model: procurementTypeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new ProcurementTypeOptionNotFoundException("Procurement Type option not Found with ids:" + missingIds);
        }
        for (ProcurementTypeOptionModel model: procurementTypeOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Procurement Type option with id:" + model.getId() + "is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        procurementTypeOptionRepository.saveAll(procurementTypeOptionModels);
        return procurementTypeOptionModels;
    }

    /**
     * Hard deletes multiple procurement type options by IDs
     * @param ids List of Procurement type option IDs to hard delete
     */
    @Transactional
    public void hardDeleteProcurementTypeOptions(List<Long> ids){
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (ProcurementTypeOptionModel model: procurementTypeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new ProcurementTypeOptionNotFoundException("Procurement type option not found with ids:" +missingIds);
        }
        procurementTypeOptionRepository.deleteAllById(ids);
    }
}

