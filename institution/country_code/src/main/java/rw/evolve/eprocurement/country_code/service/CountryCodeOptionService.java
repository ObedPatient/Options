/**
 * Service for managing CountryCodeOption model.
 * Provides functionality to create, read, update, and delete CountryCodeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.country_code.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.country_code.exception.CountryCodeOptionAlreadyExistException;
import rw.evolve.eprocurement.country_code.exception.CountryCodeOptionNotFoundException;
import rw.evolve.eprocurement.country_code.model.CountryCodeOptionModel;
import rw.evolve.eprocurement.country_code.repository.CountryCodeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CountryCodeOptionService {

    @Autowired
    private CountryCodeOptionRepository countryCodeOptionRepository;

    /**
     * Creates a single CountryCodeOption model with a generated ID.
     *
     * @param countryCodeOptionModel                  - the CountryCodeOptionModel to be created
     * @return                                        - the saved CountryCodeOption model
     * @throws CountryCodeOptionAlreadyExistException - if a CountryCodeOption with the same name exists
     */
    @Transactional
    public CountryCodeOptionModel save(CountryCodeOptionModel countryCodeOptionModel) {
        if (countryCodeOptionModel == null) {
            throw new NullPointerException("Country code option cannot be null");
        }
        if (countryCodeOptionRepository.existsByName(countryCodeOptionModel.getName())) {
            throw new CountryCodeOptionAlreadyExistException("Country code option already exists: " + countryCodeOptionModel.getName());
        }
        return countryCodeOptionRepository.save(countryCodeOptionModel);
    }

    /**
     * Creates multiple CountryCodeOption model, each with a unique generated ID.
     *
     * @param countryCodeOptionModelList - the list of CountryCodeOption models to be created
     * @return                           - a list of saved CountryCodeOption model
     * @throws NullPointerException      - if the input list is null
     */
    @Transactional
    public List<CountryCodeOptionModel> saveMany(List<CountryCodeOptionModel> countryCodeOptionModelList) {
        if (countryCodeOptionModelList == null || countryCodeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Country code option model list cannot be null or empty");
        }
        for (CountryCodeOptionModel countryCodeOptionModel : countryCodeOptionModelList) {
            if (countryCodeOptionRepository.existsByName(countryCodeOptionModel.getName())) {
                throw new CountryCodeOptionAlreadyExistException("Country code option already exists: " + countryCodeOptionModel.getName());
            }
        }
        return countryCodeOptionRepository.saveAll(countryCodeOptionModelList);
    }

    /**
     * Retrieves a single CountryCodeOption model by its ID.
     * Throws a CountryCodeOptionNotFoundException if the CountryCodeOption is not found or has been deleted.
     *
     * @param id                                  - the ID of the CountryCodeOption to retrieve
     * @return                                    - the CountryCodeOption model if found and not deleted
     * @throws CountryCodeOptionNotFoundException - if the CountryCodeOption is not found
     * @throws NullPointerException               - if CountryCodeOption ID is null
     */
    @Transactional
    public CountryCodeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Country code option ID cannot be null");
        }
        CountryCodeOptionModel countryCodeOptionModel = countryCodeOptionRepository.findById(id)
                .orElseThrow(() -> new CountryCodeOptionNotFoundException("Country code option not found with ID: " + id));
        if (countryCodeOptionModel.getDeletedAt() != null) {
            throw new CountryCodeOptionNotFoundException("Country code option not found with ID: " + id);
        }
        return countryCodeOptionModel;
    }

    /**
     * Retrieves a list of CountryCodeOption model based on the provided CountryCodeOption IDs.
     *
     * @param countryCodeOptionIdList    - A list of CountryCodeOption IDs to retrieve
     * @return                           - A list of CountryCodeOptionModel objects that are not marked as deleted
     * @throws NullPointerException      - if a CountryCodeOption ID list is null
     */
    @Transactional
    public List<CountryCodeOptionModel> readMany(List<String> countryCodeOptionIdList) {
        if (countryCodeOptionIdList == null || countryCodeOptionIdList.isEmpty()) {
            throw new NullPointerException("Country code option ID list cannot be null");
        }
        List<CountryCodeOptionModel> modelList = new ArrayList<>();
        for (String id : countryCodeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Country code option ID cannot be null");
            }
            CountryCodeOptionModel countryCodeOptionModel = countryCodeOptionRepository.findById(id)
                    .orElse(null);
            if (countryCodeOptionModel == null)
                continue;
            if (countryCodeOptionModel.getDeletedAt() == null) {
                modelList.add(countryCodeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all CountryCodeOption models that are not marked as deleted.
     *
     * @return - a List of CountryCodeOption models where deletedAt is null
     */
    @Transactional
    public List<CountryCodeOptionModel> readAll() {
        return countryCodeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all CountryCodeOption models, including those marked as deleted.
     *
     * @return - A list of all CountryCodeOptionModel objects
     */
    @Transactional
    public List<CountryCodeOptionModel> hardReadAll() {
        return countryCodeOptionRepository.findAll();
    }

    /**
     * Updates a single CountryCodeOption model identified by the provided ID.
     *
     * @param model                               - The CountryCodeOptionModel containing updated data
     * @return                                    - The updated CountryCodeOptionModel
     * @throws CountryCodeOptionNotFoundException - if CountryCodeOption is not found or marked as deleted
     */
    @Transactional
    public CountryCodeOptionModel updateOne(CountryCodeOptionModel model) {
        CountryCodeOptionModel existing = countryCodeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new CountryCodeOptionNotFoundException("Country code option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new CountryCodeOptionNotFoundException("Country code option with ID: " + model.getId() + " is not found");
        }
        return countryCodeOptionRepository.save(model);
    }

    /**
     * Updates multiple CountryCodeOption model.
     *
     * @param modelList                           - List of CountryCodeOptionModel objects containing updated data
     * @return                                    - List of updated CountryCodeOptionModel objects
     * @throws CountryCodeOptionNotFoundException - if a CountryCodeOption is not found or marked as deleted
     */
    @Transactional
    public List<CountryCodeOptionModel> updateMany(List<CountryCodeOptionModel> modelList) {
        for (CountryCodeOptionModel model : modelList) {
            CountryCodeOptionModel existing = countryCodeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new CountryCodeOptionNotFoundException("Country code option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new CountryCodeOptionNotFoundException("Country code option with ID: " + model.getId() + " is not found");
            }
        }
        return countryCodeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single CountryCodeOption model by ID, including deleted ones.
     *
     * @param model                               - The CountryCodeOptionModel containing updated data
     * @return                                    - The updated CountryCodeOptionModel
     * @throws CountryCodeOptionNotFoundException - if CountryCodeOption is not found
     */
    @Transactional
    public CountryCodeOptionModel hardUpdate(CountryCodeOptionModel model) {
        return countryCodeOptionRepository.save(model);
    }

    /**
     * Updates multiple CountryCodeOption model by their ID, including deleted ones.
     *
     * @param countryCodeOptionModelList - List of CountryCodeOptionModel objects containing updated data
     * @return                           - List of updated CountryCodeOptionModel objects
     */
    @Transactional
    public List<CountryCodeOptionModel> hardUpdateAll(List<CountryCodeOptionModel> countryCodeOptionModelList) {
        return countryCodeOptionRepository.saveAll(countryCodeOptionModelList);
    }

    /**
     * Soft deletes a CountryCodeOption by ID.
     *
     * @param id                                  - The ID of the CountryCodeOption to soft delete
     * @return                                    - The soft-deleted CountryCodeOptionModel
     * @throws CountryCodeOptionNotFoundException - if CountryCodeOption ID is not found
     */
    @Transactional
    public CountryCodeOptionModel softDelete(String id) {
        CountryCodeOptionModel countryCodeOptionModel = countryCodeOptionRepository.findById(id)
                .orElseThrow(() -> new CountryCodeOptionNotFoundException("Country code option not found with id: " + id));
        countryCodeOptionModel.setDeletedAt(LocalDateTime.now());
        return countryCodeOptionRepository.save(countryCodeOptionModel);
    }

    /**
     * Hard deletes a CountryCodeOption by ID.
     *
     * @param id                                  - ID of the CountryCodeOption to hard delete
     * @throws NullPointerException               - if the CountryCodeOption ID is null
     * @throws CountryCodeOptionNotFoundException - if the CountryCodeOption is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Country code option ID cannot be null");
        }
        if (!countryCodeOptionRepository.existsById(id)) {
            throw new CountryCodeOptionNotFoundException("Country code option not found with id: " + id);
        }
        countryCodeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple CountryCodeOptions by their IDs.
     *
     * @param idList                              - List of CountryCodeOption IDs to be soft deleted
     * @return                                    - List of soft-deleted CountryCodeOption objects
     * @throws CountryCodeOptionNotFoundException - if any CountryCodeOption IDs are not found
     */
    @Transactional
    public List<CountryCodeOptionModel> softDeleteMany(List<String> idList) {
        List<CountryCodeOptionModel> countryCodeOptionModelList = countryCodeOptionRepository.findAllById(idList);
        if (countryCodeOptionModelList.isEmpty()) {
            throw new CountryCodeOptionNotFoundException("No country code options found with provided IDList: " + idList);
        }
        for (CountryCodeOptionModel model : countryCodeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return countryCodeOptionRepository.saveAll(countryCodeOptionModelList);
    }

    /**
     * Hard deletes multiple CountryCodeOptions by IDs.
     *
     * @param idList - List of CountryCodeOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        countryCodeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all CountryCodeOption, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        countryCodeOptionRepository.deleteAll();
    }
}