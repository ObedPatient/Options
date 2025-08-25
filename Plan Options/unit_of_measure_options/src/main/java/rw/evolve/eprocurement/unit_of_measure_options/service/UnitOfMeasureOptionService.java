/**
 * Service for managing UnitOfMeasureOption model.
 * Provides functionality to create, read, update, and delete UnitOfMeasureOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.unit_of_measure_options.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.unit_of_measure_options.exception.UnitOfMeasureAlreadyExistException;
import rw.evolve.eprocurement.unit_of_measure_options.exception.UnitOfMeasureNotFoundException;
import rw.evolve.eprocurement.unit_of_measure_options.model.UnitOfMeasureOptionModel;
import rw.evolve.eprocurement.unit_of_measure_options.repository.UnitOfMeasureOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UnitOfMeasureOptionService {

    private UnitOfMeasureOptionRepository unitOfMeasureOptionRepository;

    /**
     * Creates a single Unit of Measure option model.
     *
     * @param unitOfMeasureOptionModel            - the UnitOfMeasureOptionModel to be created
     * @return                                    - the saved UnitOfMeasureOption model
     * @throws UnitOfMeasureAlreadyExistException - if a UnitOfMeasureOption with the same name exists
     * @throws NullPointerException               - if the input model is null
     */
    @Transactional
    public UnitOfMeasureOptionModel save(UnitOfMeasureOptionModel unitOfMeasureOptionModel) {
        if (unitOfMeasureOptionModel == null) {
            throw new NullPointerException("Unit of measure option cannot be null");
        }
        if (unitOfMeasureOptionRepository.existsByName(unitOfMeasureOptionModel.getName())) {
            throw new UnitOfMeasureAlreadyExistException("Unit of measure option already exists: " + unitOfMeasureOptionModel.getName());
        }
        return unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
    }

    /**
     * Creates multiple Unit of Measure option model.
     *
     * @param unitOfMeasureOptionModelList        - the list of UnitOfMeasureOption models to be created
     * @return                                    - a list of saved UnitOfMeasureOption models
     * @throws IllegalArgumentException           - if the input list is null or empty
     * @throws UnitOfMeasureAlreadyExistException - if a UnitOfMeasureOption with the same name exists
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> saveMany(List<UnitOfMeasureOptionModel> unitOfMeasureOptionModelList) {
        if (unitOfMeasureOptionModelList == null || unitOfMeasureOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Unit of measure option model list cannot be null or empty");
        }
        for (UnitOfMeasureOptionModel unitOfMeasureOptionModel : unitOfMeasureOptionModelList) {
            if (unitOfMeasureOptionRepository.existsByName(unitOfMeasureOptionModel.getName())) {
                throw new UnitOfMeasureAlreadyExistException("Unit of measure option already exists: " + unitOfMeasureOptionModel.getName());
            }
        }
        return unitOfMeasureOptionRepository.saveAll(unitOfMeasureOptionModelList);
    }

    /**
     * Retrieves a single Unit of Measure option model by its ID.
     * Throws a UnitOfMeasureNotFoundException if the option is not found or has been deleted.
     *
     * @param id                              - the ID of the UnitOfMeasureOption to retrieve
     * @return                                - the UnitOfMeasureOption model if found and not deleted
     * @throws UnitOfMeasureNotFoundException - if the option is not found
     * @throws NullPointerException           - if the ID is null
     */
    @Transactional
    public UnitOfMeasureOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Unit of measure option ID cannot be null");
        }
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                .orElseThrow(() -> new UnitOfMeasureNotFoundException("Unit of Measure option not found with ID: " + id));
        if (unitOfMeasureOptionModel.getDeletedAt() != null) {
            throw new UnitOfMeasureNotFoundException("Unit of measure option not found with ID: " + id);
        }
        return unitOfMeasureOptionModel;
    }

    /**
     * Retrieves a list of UnitOfMeasureOption model based on the provided IDs.
     *
     * @param idList                        - A list of UnitOfMeasureOption IDs to retrieve
     * @return                              - A list of UnitOfMeasureOptionModel objects that are not marked as deleted
     * @throws NullPointerException         - if the ID list is null or contains null IDs
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> readMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new NullPointerException("Unit of Measure option ID list cannot be null");
        }
        List<UnitOfMeasureOptionModel> modelList = new ArrayList<>();
        for (String id : idList) {
            if (id == null) {
                throw new NullPointerException("Unit of Measure option ID cannot be null");
            }
            UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                    .orElse(null);
            if (unitOfMeasureOptionModel == null) {
                continue;
            }
            if (unitOfMeasureOptionModel.getDeletedAt() == null) {
                modelList.add(unitOfMeasureOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieves all UnitOfMeasureOption model that are not marked as deleted.
     *
     * @return - a List of UnitOfMeasureOption model where deletedAt is null
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> readAll() {
        return unitOfMeasureOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all UnitOfMeasureOption models, including those marked as deleted.
     *
     * @return - A list of all UnitOfMeasureOptionModel objects
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> hardReadAll() {
        return unitOfMeasureOptionRepository.findAll();
    }

    /**
     * Updates a single UnitOfMeasureOption model identified by the provided ID.
     *
     * @param model                           - The UnitOfMeasureOptionModel containing updated data
     * @return                                - The updated UnitOfMeasureOptionModel
     * @throws UnitOfMeasureNotFoundException - if the option is not found or is marked as deleted
     */
    @Transactional
    public UnitOfMeasureOptionModel updateOne(UnitOfMeasureOptionModel model) {
        UnitOfMeasureOptionModel existing = unitOfMeasureOptionRepository.findById(model.getId())
                .orElseThrow(() -> new UnitOfMeasureNotFoundException("Unit of Measure option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new UnitOfMeasureNotFoundException("Unit of measure option with ID: " + model.getId() + " is not found");
        }
        return unitOfMeasureOptionRepository.save(model);
    }

    /**
     * Updates multiple UnitOfMeasureOption model in a transactional manner.
     *
     * @param modelList                       - List of UnitOfMeasureOptionModel objects containing updated data
     * @return                                - List of updated UnitOfMeasureOptionModel objects
     * @throws UnitOfMeasureNotFoundException - if any option is not found or is marked as deleted
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> updateMany(List<UnitOfMeasureOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Unit of measure option model list cannot be null or empty");
        }
        for (UnitOfMeasureOptionModel model : modelList) {
            UnitOfMeasureOptionModel existing = unitOfMeasureOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new UnitOfMeasureNotFoundException("Unit of Measure option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new UnitOfMeasureNotFoundException("Unit of measure option with ID: " + model.getId() + " is not found");
            }
        }
        return unitOfMeasureOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single UnitOfMeasureOption model by ID, including soft-deleted ones.
     *
     * @param model - The UnitOfMeasureOptionModel containing updated data
     * @return      - The updated UnitOfMeasureOptionModel
     */
    @Transactional
    public UnitOfMeasureOptionModel hardUpdate(UnitOfMeasureOptionModel model) {
        return unitOfMeasureOptionRepository.save(model);
    }

    /**
     * Updates all UnitOfMeasureOption model by their ID, including soft-deleted ones.
     *
     * @param unitOfMeasureOptionModelList - List of UnitOfMeasureOptionModel objects containing updated data
     * @return                             - List of updated UnitOfMeasureOptionModel objects
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> hardUpdateAll(List<UnitOfMeasureOptionModel> unitOfMeasureOptionModelList) {
        return unitOfMeasureOptionRepository.saveAll(unitOfMeasureOptionModelList);
    }

    /**
     * Soft deletes a Unit of Measure option by ID.
     *
     * @param id                              - The ID of the UnitOfMeasureOption to soft delete
     * @return                                - The soft-deleted UnitOfMeasureOptionModel
     * @throws UnitOfMeasureNotFoundException - if the option is not found
     */
    @Transactional
    public UnitOfMeasureOptionModel softDelete(String id) {
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionRepository.findById(id)
                .orElseThrow(() -> new UnitOfMeasureNotFoundException("Unit of measure option not found with id: " + id));
        unitOfMeasureOptionModel.setDeletedAt(LocalDateTime.now());
        return unitOfMeasureOptionRepository.save(unitOfMeasureOptionModel);
    }

    /**
     * Hard deletes a Unit of Measure option by ID.
     *
     * @param id                              - ID of the UnitOfMeasureOption to hard delete
     * @throws NullPointerException           - if the ID is null
     * @throws UnitOfMeasureNotFoundException - if the option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Unit of measure option ID cannot be null");
        }
        if (!unitOfMeasureOptionRepository.existsById(id)) {
            throw new UnitOfMeasureNotFoundException("Unit of measure option not found with id: " + id);
        }
        unitOfMeasureOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Unit of Measure options by their ID.
     *
     * @param idList                          - List of UnitOfMeasureOption IDs to be soft deleted
     * @return                                - List of soft-deleted UnitOfMeasureOption objects
     * @throws UnitOfMeasureNotFoundException - if any IDs are not found
     */
    @Transactional
    public List<UnitOfMeasureOptionModel> softDeleteMany(List<String> idList) {
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModelList = unitOfMeasureOptionRepository.findAllById(idList);
        if (unitOfMeasureOptionModelList.isEmpty()) {
            throw new UnitOfMeasureNotFoundException("No Unit of measure options found with provided IDs: " + idList);
        }
        for (UnitOfMeasureOptionModel model : unitOfMeasureOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return unitOfMeasureOptionRepository.saveAll(unitOfMeasureOptionModelList);
    }

    /**
     * Hard deletes multiple Unit of Measure options by IDs.
     *
     * @param idList - List of UnitOfMeasureOption IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        unitOfMeasureOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Unit of Measure options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        unitOfMeasureOptionRepository.deleteAll();
    }
}