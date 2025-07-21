/**
 * Service for managing CountryOption entities.
 * Provides functionality to create, read, update, and delete CountryOption data, supporting both
 * soft and hard deletion operations.
 */
package rw.evolve.eprocurement.country.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.country.exception.CountryOptionAlreadyExistException;
import rw.evolve.eprocurement.country.exception.CountryOptionNotFoundException;
import rw.evolve.eprocurement.country.model.CountryOptionModel;
import rw.evolve.eprocurement.country.repository.CountryOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryOptionService {

    @Autowired
    private CountryOptionRepository countryOptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a single Country Option entity.
     *
     * @param countryOptionModel the CountryOptionModel to be created
     * @return the saved CountryOption model
     */
    @Transactional
    public CountryOptionModel createCountryOption(CountryOptionModel countryOptionModel){
        if (countryOptionRepository.existsByName(countryOptionModel.getName())){
            throw new CountryOptionAlreadyExistException("Country Option Already exists: " + countryOptionModel.getName());
        }
        return countryOptionRepository.save(countryOptionModel);
    }

    /**
     * Creates multiple Country Option entities, each with a unique ID.
     * Iterates through the provided list of Country Option models
     *
     * @param countryOptionModels the list of Country Option models to be created
     * @return a list of saved Country Option models.
     */
    @Transactional
    public List<CountryOptionModel> createCountryOptions(List<CountryOptionModel> countryOptionModels){
        if (countryOptionModels == null){
            throw new IllegalArgumentException("Country Option model cannot be null");
        }
        List<CountryOptionModel> savedCountryModels = new ArrayList<>();
        for (CountryOptionModel countryOptionModel: countryOptionModels){
            CountryOptionModel savedCountryModel = countryOptionRepository.save(countryOptionModel);
            savedCountryModels.add(savedCountryModel);
        }
        return savedCountryModels;
    }

    /**
     * Retrieves a single Country Option entity by its ID.
     * Throws a CountryOptionNotFoundException if the Country Option is not found or has been deleted.
     *
     * @param id the ID of the Country Option to retrieve
     * @return the Country Option model if found and not deleted
     * @throws CountryOptionNotFoundException if the Country Option is not found.
     */
    @Transactional
    public CountryOptionModel readOne(Long id){
        CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + id));
        if (countryOptionModel.getDeletedAt() != null){
            throw new CountryOptionNotFoundException("Country Option not found with id:" + id);
        }
        return countryOptionModel;
    }

    /**
     * Retrieves a list of Country Option objects based on the provided Country Option IDs.
     *
     * @param countryOptionIds A list of Country Option IDs to retrieve
     * @return A list of CountryOptionModel objects that are not marked as deleted
     * @throws CountryOptionNotFoundException if a Country Option with the given ID is not found
     */
    @Transactional
    public List<CountryOptionModel> readMany(List<Long> countryOptionIds){
        if (countryOptionIds == null){
            throw new IllegalArgumentException("Country Option id cannot be null or empty");
        }
        List<CountryOptionModel> models = new ArrayList<>();
        for (Long id: countryOptionIds){
            if (id == null){
                throw new IllegalArgumentException("Country Option id cannot be null or empty");
            }
            CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                    .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + id));
            if (countryOptionModel.getDeletedAt() == null){
                models.add(countryOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all Country Options that are not marked as deleted
     * @return a List of Country Option objects where deleted is null
     * @throws CountryOptionNotFoundException if no Country Option found
     */
    @Transactional
    public List<CountryOptionModel> readAll(){
        List<CountryOptionModel> countryOptionModels = countryOptionRepository.findByDeletedAtIsNull();
        if (countryOptionModels.isEmpty()){
            throw new CountryOptionNotFoundException("No Country Option found");
        }
        return countryOptionModels;
    }

    /**
     * Retrieves all CountryOptionModels, including those marked as deleted.
     *
     * @return A list of all CountryOptionModel objects
     * @throws CountryOptionNotFoundException if no Country Option found
     */
    @Transactional
    public List<CountryOptionModel> hardReadAll(){
        List<CountryOptionModel> countryOptionModels = countryOptionRepository.findAll();
        if (countryOptionModels.isEmpty()){
            throw new CountryOptionNotFoundException("No Country Option found");
        }
        return countryOptionModels;
    }

    /**
     * Updates a single CountryOptionModel identified by the provided ID.
     * @param id The ID of the Country Option to update
     * @param model The CountryOptionModel containing updated data
     * @return The updated CountryOptionModel
     * @throws CountryOptionNotFoundException if the CountryOptionModel is not found or is marked as deleted
     */
    @Transactional
    public CountryOptionModel updateOne(Long id, CountryOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Country Option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Country Option cannot be null");
        }
        CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + id));
        if (countryOptionModel.getDeletedAt() != null){
            throw new CountryOptionNotFoundException("Country Option is marked as deleted with id:" + id);
        }
        modelMapper.map(model, countryOptionModel);
        countryOptionModel.setUpdatedAt(LocalDateTime.now());
        return countryOptionRepository.save(countryOptionModel);
    }

    /**
     * Updates multiple CountryOption models in a transactional manner.
     *
     * @param models List of CountryOptionModel objects containing updated data
     * @return List of updated CountryOptionModel objects
     * @throws IllegalArgumentException if any CountryOptionModel is null
     * @throws CountryOptionNotFoundException if a CountryOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<CountryOptionModel> updateMany(List<CountryOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Country Option model List cannot be null or empty");
        }
        List<CountryOptionModel> updatedModel = new ArrayList<>();
        for (CountryOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Country Option id cannot be null");
            }
            CountryOptionModel existingModel = countryOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() != null){
                throw new CountryOptionNotFoundException("Country Option with id:" + model.getId() + " marked as deleted");
            }
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(countryOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Country Option model by ID.
     * @param id The ID of the Country Option to update
     * @param model The CountryOptionModel containing updated data
     * @return The updated CountryOptionModel
     * @throws IllegalArgumentException if the Country Option ID is null
     * @throws CountryOptionNotFoundException if the Country Option is not found
     */
    @Transactional
    public CountryOptionModel hardUpdateOne(Long id, CountryOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Country Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Country Option model cannot be null");
        }
        CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + id));

        modelMapper.map(model, countryOptionModel);
        countryOptionModel.setUpdatedAt(LocalDateTime.now());
        return countryOptionRepository.save(countryOptionModel);
    }

    /**
     * Updates multiple CountryOptionModel models by their IDs
     * @param countryOptionModels List of CountryOptionModel objects containing updated data
     * @return List of updated CountryOptionModel objects
     * @throws IllegalArgumentException if any Country Option ID is null
     * @throws CountryOptionNotFoundException if any Country Option is not found
     */
    @Transactional
    public List<CountryOptionModel> hardUpdateAll(List<CountryOptionModel> countryOptionModels){
        if (countryOptionModels == null || countryOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Country Option model list cannot be null or empty");
        }
        List<CountryOptionModel> updatedModels = new ArrayList<>();
        for (CountryOptionModel model: countryOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("Country Option id cannot be null on Hard update all");
            }
            CountryOptionModel countryOptionModel = countryOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + model.getId()));

            modelMapper.map(model, countryOptionModel);
            countryOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(countryOptionRepository.save(countryOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a Country Option by ID in a transactional manner.
     *
     * @param id The ID of the Country Option to soft delete
     * @return The soft-deleted CountryOptionModel
     * @throws IllegalArgumentException if the Country Option ID is null
     * @throws CountryOptionNotFoundException if the Country Option is not found
     * @throws IllegalStateException if the Country Option is already deleted
     */
    @Transactional
    public CountryOptionModel softDeleteCountryOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Country Option ID cannot be null or empty");
        }
        CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                .orElseThrow(() -> new CountryOptionNotFoundException("Country Option not found with id:" + id));
        if (countryOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Country Option with id:" + id + " is already deleted");
        }
        countryOptionModel.setDeletedAt(LocalDateTime.now());
        return countryOptionRepository.save(countryOptionModel);
    }

    /**
     * Hard deletes a Country Option by ID
     * @param id ID of the Country Option to hard delete
     */
    @Transactional
    public void hardDeleteCountryOption(Long id){
        if (!countryOptionRepository.existsById(id)){
            throw new CountryOptionNotFoundException("Country Option not found with id:" + id);
        }
        countryOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Country Options by their IDs.
     *
     * @param ids List of Country Option IDs to be soft deleted
     * @return List of soft deleted Country Option objects
     * @throws CountryOptionNotFoundException if any Country Option IDs are not found
     * @throws IllegalStateException if any Country Option is already deleted
     */
    @Transactional
    public List<CountryOptionModel> softDeleteCountryOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Country Option IDs list cannot be null or empty");
        }
        List<CountryOptionModel> countryOptionModels = countryOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (CountryOptionModel model: countryOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new CountryOptionNotFoundException("Country Option not found with ids:" + missingIds);
        }
        for (CountryOptionModel model: countryOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalStateException("Country Option with id:" + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        countryOptionRepository.saveAll(countryOptionModels);
        return countryOptionModels;
    }

    /**
     * Hard deletes multiple Country Options by IDs
     * @param ids List of Country Option IDs to hard delete
     */
    @Transactional
    public void hardDeleteCountryOptions(List<Long> ids){
        List<CountryOptionModel> countryOptionModels = countryOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (CountryOptionModel model: countryOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new CountryOptionNotFoundException("Country Option not found with ids:" + missingIds);
        }
        countryOptionRepository.deleteAllById(ids);
    }
}