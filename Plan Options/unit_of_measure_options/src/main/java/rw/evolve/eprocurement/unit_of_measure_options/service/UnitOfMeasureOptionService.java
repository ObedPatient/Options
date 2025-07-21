package rw.evolve.eprocurement.unit_of_measure_options.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.unit_of_measure_options.exception.UnitOfMeasureAlreadyExistException;
import rw.evolve.eprocurement.unit_of_measure_options.exception.UnitOfMeasureNotFoundException;
import rw.evolve.eprocurement.unit_of_measure_options.model.UnitOfMeasureOptionModel;
import rw.evolve.eprocurement.unit_of_measure_options.repository.UnitOfMeasureOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnitOfMeasureOptionService {

    @Autowired
    private UnitOfMeasureOptionRepository unitOfMeasureOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Unit of Measure Option entity.
     *
     * @param unitOfMeasureOptionModel the UnitOfMeasureOptionModel to be created
     * @return the saved UnitOfMeasureOption model
     */
    @Transactional
    public UnitOfMeasureOptionModel createUnitOfMeasureOption(UnitOfMeasureOptionModel unitOfMeasureOptionModel){
        if (unitOfMeasureOptionRepository.existsByName(unitOfMeasureOptionModel.getName())){
            throw new UnitOfMeasureAlreadyExistException("Unit of Measure Option Already exists: " + unitOfMeasureOptionModel.getName());
        }
        return unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
    }

    /**
     * Creates multiple Unit of Measure Option entities, each with a unique ID.
     * Iterates through the provided list of Unit of Measure Option models
     *
     * @param unitOfMeasureOptionModels the list of Unit of Measure Option models to be created
     * @return a list of saved Unit of Measure Option models.
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> createUnitOfMeasureOptions(List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels){
        if (unitOfMeasureOptionModels == null){
            throw new IllegalArgumentException("Unit of Measure Option model cannot be null");
        }
        List<UnitOfMeasureOptionModel> savedUnitOfMeasureModels = new ArrayList<>();
        for (UnitOfMeasureOptionModel unitOfMeasureOptionModel: unitOfMeasureOptionModels){
            UnitOfMeasureOptionModel savedUnitOfMeasureModel = unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
            savedUnitOfMeasureModels.add(savedUnitOfMeasureModel);
        }
        return savedUnitOfMeasureModels;
    }

    /**
     * Retrieves a single Unit of Measure Option entity by its ID.
     * Throws a UnitOfMeasureNotFoundException if the Unit of Measure Option is not found or has been deleted.
     *
     * @param id the ID of the Unit of Measure Option to retrieve
     * @return the Unit of Measure Option model if found and not deleted
     * @throws UnitOfMeasureNotFoundException if the Unit of Measure Option is not found.
     */
    @Transactional
    public UnitOfMeasureOptionModel readOne(Long id){
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id));
        if (unitOfMeasureOptionModel.getDeletedAt() != null){
            throw new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id);
        }
        return unitOfMeasureOptionModel;
    }

    /**
     * Retrieves a list of Unit of Measure Option objects based on the provided Unit of Measure Option IDs.
     *
     * @param unitOfMeasureOptionIds A list of Unit of Measure Option IDs to retrieve
     * @return A list of UnitOfMeasureOptionModel objects that are not marked as deleted
     * @throws UnitOfMeasureNotFoundException if a Unit of Measure Option with the given ID is not found
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> readMany(List<Long> unitOfMeasureOptionIds){
        if (unitOfMeasureOptionIds == null){
            throw new IllegalArgumentException("Unit of Measure Option id cannot be null or empty");
        }
        List<UnitOfMeasureOptionModel> models = new ArrayList<>();
        for (Long id: unitOfMeasureOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Unit of Measure Option id cannot be null or empty");
            }
            UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                    .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id));
            if (unitOfMeasureOptionModel.getDeletedAt() == null){
                models.add(unitOfMeasureOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all Unit of Measure Options that are not marked as deleted
     * @return a List of Unit of Measure Option objects where deleted is null
     * @throws UnitOfMeasureNotFoundException if no Unit of Measure Option found
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> readAll(){
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = unitOfMeasureOptionRepository.findByDeletedAtIsNull();
        if (unitOfMeasureOptionModels.isEmpty()){
            throw new UnitOfMeasureNotFoundException("No Unit of Measure Option found");
        }
        return unitOfMeasureOptionModels;
    }

    /**
     * Retrieves all UnitOfMeasureOptionModels, including those marked as deleted.
     *
     * @return A list of all UnitOfMeasureOptionModel objects
     * @throws UnitOfMeasureNotFoundException if no Unit of Measure Option found
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> hardReadAll(){
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = unitOfMeasureOptionRepository.findAll();
        if (unitOfMeasureOptionModels.isEmpty()){
            throw new UnitOfMeasureNotFoundException("No Unit of Measure Option found");
        }
        return unitOfMeasureOptionModels;
    }

    /**
     * Updates a single UnitOfMeasureOptionModel identified by the provided ID.
     * @param id The ID of the Unit of Measure Option to update
     * @param model The UnitOfMeasureOptionModel containing updated data
     * @return The updated UnitOfMeasureOptionModel
     * @throws UnitOfMeasureNotFoundException if the UnitOfMeasureOptionModel is not found or is marked as deleted
     */
    @Transactional
    public UnitOfMeasureOptionModel updateOne(Long id, UnitOfMeasureOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Unit of Measure Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Unit of Measure Option cannot be null");
        }
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id));
        if (unitOfMeasureOptionModel.getDeletedAt() != null){
            throw new UnitOfMeasureNotFoundException("Unit of Measure Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, unitOfMeasureOptionModel);
        unitOfMeasureOptionModel.setUpdatedAt(LocalDateTime.now());
        return unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
    }

    /**
     * Updates multiple UnitOfMeasureOption models in a transactional manner.
     *
     * @param models List of UnitOfMeasureOptionModel objects containing updated data
     * @return List of updated UnitOfMeasureOptionModel objects
     * @throws IllegalArgumentException if any UnitOfMeasureOptionModel is null
     * @throws UnitOfMeasureNotFoundException if a UnitOfMeasureOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> updateMany(List<UnitOfMeasureOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Unit of Measure Option model List cannot be null or empty");
        }
        List<UnitOfMeasureOptionModel> updatedModel = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Unit of Measure Option id cannot be null");
            }
            UnitOfMeasureOptionModel existingModel = unitOfMeasureOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() != null){
                throw new UnitOfMeasureNotFoundException("Unit of Measure Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(unitOfMeasureOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Unit of Measure Option model by ID.
     * @param id The ID of the Unit of Measure Option to update
     * @param model The UnitOfMeasureOptionModel containing updated data
     * @return The updated UnitOfMeasureOptionModel
     * @throws IllegalArgumentException if the Unit of Measure Option ID is null
     * @throws UnitOfMeasureNotFoundException if the Unit of Measure Option is not found
     */
    @Transactional
    public UnitOfMeasureOptionModel hardUpdateOne(Long id, UnitOfMeasureOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Unit of Measure Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Unit of Measure Option model cannot be null");
        }
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id));

        modelMapper.map(model, unitOfMeasureOptionModel);
        unitOfMeasureOptionModel.setUpdatedAt(LocalDateTime.now());
        return unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
    }

    /**
     * Updates multiple UnitOfMeasureOptionModel models by their IDs
     * @param unitOfMeasureOptionModels List of UnitOfMeasureOptionModel objects containing updated data
     * @return List of updated UnitOfMeasureOptionModel objects
     * @throws IllegalArgumentException if any Unit of Measure Option ID is null
     * @throws UnitOfMeasureNotFoundException if any Unit of Measure Option is not found
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> hardUpdateAll(List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels){
        if (unitOfMeasureOptionModels == null || unitOfMeasureOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Unit of Measure Option model list cannot be null or empty");
        }
        List<UnitOfMeasureOptionModel> updatedModels = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: unitOfMeasureOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Unit of Measure Option id cannot be null on Hard update all");
            }
            UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + model.getId()));

            modelMapper.map(model, unitOfMeasureOptionModel);
            unitOfMeasureOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a Unit of Measure Option by ID in a transactional manner.
     *
     * @param id The ID of the Unit of Measure Option to soft delete
     * @return The soft-deleted UnitOfMeasureOptionModel
     * @throws IllegalArgumentException if the Unit of Measure Option ID is null
     * @throws UnitOfMeasureNotFoundException if the Unit of Measure Option is not found
     * @throws IllegalStateException if the Unit of Measure Option is already deleted
     */
    @Transactional
    public UnitOfMeasureOptionModel softDeleteUnitOfMeasureOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Unit of Measure Option ID cannot be null or empty");
        }
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                .orElseThrow(()-> new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id));
        if (unitOfMeasureOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Unit of Measure Option with id:" + id + " is already deleted");
        }
        unitOfMeasureOptionModel.setDeletedAt(LocalDateTime.now());
        return unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
    }

    /**
     * Hard deletes a Unit of Measure Option by ID
     * @param id ID of the Unit of Measure Option to hard delete
     */
    @Transactional
    public void hardDeleteUnitOfMeasureOption(Long id){
        if (!unitOfMeasureOptionRepository.existsById(id)){
            throw new UnitOfMeasureNotFoundException("Unit of Measure Option not found with id:" + id);
        }
        unitOfMeasureOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Unit of Measure Options by their IDs.
     *
     * @param ids List of Unit of Measure Option IDs to be soft deleted
     * @return List of soft deleted Unit of Measure Option objects
     * @throws UnitOfMeasureNotFoundException if any Unit of Measure Option IDs are not found
     * @throws IllegalStateException if any Unit of Measure Option is already deleted
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> softDeleteUnitOfMeasureOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Unit of Measure Option IDs list cannot be null or empty");
        }
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = unitOfMeasureOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: unitOfMeasureOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new UnitOfMeasureNotFoundException("Unit of Measure Option not found with ids:" + missingIds);
        }
        for (UnitOfMeasureOptionModel model: unitOfMeasureOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Unit of Measure Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        unitOfMeasureOptionRepository.saveAll(unitOfMeasureOptionModels);
        return unitOfMeasureOptionModels;
    }

    /**
     * Hard deletes multiple Unit of Measure Options by IDs
     * @param ids List of Unit of Measure Option IDs to hard delete
     */
    @Transactional
    public void hardDeleteUnitOfMeasureOptions(List<Long> ids){
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = unitOfMeasureOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: unitOfMeasureOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new UnitOfMeasureNotFoundException("Unit of Measure Option not found with ids:" + missingIds);
        }
        unitOfMeasureOptionRepository.deleteAllById(ids);
    }
}