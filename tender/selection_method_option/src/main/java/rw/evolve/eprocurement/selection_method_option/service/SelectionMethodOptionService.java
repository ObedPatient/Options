/**
 * Service for managing SelectionMethodOption model.
 * Provides functionality to create, read, update, and delete SelectionMethodOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.selection_method_option.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.selection_method_option.exception.SelectionMethodAlreadyExistException;
import rw.evolve.eprocurement.selection_method_option.exception.SelectionMethodNotFoundException;
import rw.evolve.eprocurement.selection_method_option.model.SelectionMethodOptionModel;
import rw.evolve.eprocurement.selection_method_option.repository.SelectionMethodOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class SelectionMethodOptionService {


    private SelectionMethodOptionRepository selectionMethodOptionRepository;

    /**
     * Creates a single SelectionMethod option model with a generated ID.
     *
     * @param selectionMethodOptionModel            - the SelectionMethodOptionModel to be created
     * @return                                      - the saved SelectionMethodOption model
     * @throws SelectionMethodAlreadyExistException - if a SelectionMethodOption with the same name exists
     */
    @Transactional
    public SelectionMethodOptionModel save(SelectionMethodOptionModel selectionMethodOptionModel) {
        if (selectionMethodOptionModel == null) {
            throw new NullPointerException("Selection method option cannot be null");
        }
        if (selectionMethodOptionRepository.existsByName(selectionMethodOptionModel.getName())) {
            throw new SelectionMethodAlreadyExistException("Selection method option already exists: " + selectionMethodOptionModel.getName());
        }
        return selectionMethodOptionRepository.save(selectionMethodOptionModel);
    }

    /**
     * Creates multiple SelectionMethodOption model, each with a unique generated ID.
     *
     * @param selectionMethodOptionModelList - the list of SelectionMethod option model to be created
     * @return                               - a list of saved SelectionMethodOption model
     * @throws NullPointerException          - if the input list is null
     */
    @Transactional
    public List<SelectionMethodOptionModel> saveMany(List<SelectionMethodOptionModel> selectionMethodOptionModelList) {
        if (selectionMethodOptionModelList == null || selectionMethodOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Selection method option model list cannot be null or empty");
        }
        for (SelectionMethodOptionModel selectionMethodOptionModel : selectionMethodOptionModelList) {
            if (selectionMethodOptionRepository.existsByName(selectionMethodOptionModel.getName())) {
                throw new SelectionMethodAlreadyExistException("Selection method option already exists: " + selectionMethodOptionModel.getName());
            }
        }
        return selectionMethodOptionRepository.saveAll(selectionMethodOptionModelList);
    }

    /**
     * Retrieves a single SelectionMethod option model by its ID.
     * Throws a SelectionMethodNotFoundException if the SelectionMethod option is not found or has been deleted.
     *
     * @param id                                - the ID of the SelectionMethod option to retrieve
     * @return                                  - the SelectionMethod option model if found and not deleted
     * @throws SelectionMethodNotFoundException - if the SelectionMethod option is not found
     * @throws NullPointerException             - if SelectionMethod option ID is null
     */
    @Transactional
    public SelectionMethodOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Selection method option ID cannot be null");
        }
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                .orElseThrow(() -> new SelectionMethodNotFoundException("Selection method option not found with ID: " + id));
        if (selectionMethodOptionModel.getDeletedAt() != null) {
            throw new SelectionMethodNotFoundException("Selection method option not found with ID: " + id);
        }
        return selectionMethodOptionModel;
    }

    /**
     * Retrieves a list of SelectionMethodOption model list based on the provided SelectionMethodOption ID.
     *
     * @param selectionMethodOptionIdList - A list of SelectionMethodOption ID to retrieve
     * @return                            - A list of SelectionMethodOptionModel objects that are not marked as deleted
     * @throws NullPointerException       - if a SelectionMethodOption ID list is null
     */
    @Transactional
    public List<SelectionMethodOptionModel> readMany(List<String> selectionMethodOptionIdList) {
        if (selectionMethodOptionIdList == null || selectionMethodOptionIdList.isEmpty()) {
            throw new NullPointerException("Selection method option ID list cannot be null");
        }
        List<SelectionMethodOptionModel> modelList = new ArrayList<>();
        for (String id : selectionMethodOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Selection method option ID cannot be null");
            }
            SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                    .orElse(null);
            if (selectionMethodOptionModel == null)
                continue;
            if (selectionMethodOptionModel.getDeletedAt() == null) {
                modelList.add(selectionMethodOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all SelectionMethod options that are not marked as deleted
     *
     * @return         - a List of SelectionMethod option model where deletedAt is null
     */
    @Transactional
    public List<SelectionMethodOptionModel> readAll() {
        return selectionMethodOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all SelectionMethodOption model, including those marked as deleted.
     *
     * @return            - A list of all SelectionMethodOptionModel objects
     */
    @Transactional
    public List<SelectionMethodOptionModel> hardReadAll() {
        return selectionMethodOptionRepository.findAll();
    }

    /**
     * Updates a single SelectionMethodOption model identified by the provided ID.
     *
     * @param model                              - The SelectionMethodOptionModel containing updated data
     * @return                                   - The updated SelectionMethodOptionModel
     * @throws SelectionMethodNotFoundException  - if selection method option is not found
     */
    @Transactional
    public SelectionMethodOptionModel updateOne(SelectionMethodOptionModel model) {
        SelectionMethodOptionModel existing = selectionMethodOptionRepository.findById(model.getId())
                .orElseThrow(() -> new SelectionMethodNotFoundException("Selection method option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new SelectionMethodNotFoundException("Selection method option with ID: " + model.getId() + " is not found");
        }
        return selectionMethodOptionRepository.save(model);
    }

    /**
     * Updates multiple selection method option model in a transactional manner.
     *
     * @param modelList                          - List of SelectionMethodOptionModel objects containing updated data
     * @return                                   - List of updated SelectionMethodOptionModel objects
     * @throws SelectionMethodNotFoundException  - if selection method option is not found
     */
    @Transactional
    public List<SelectionMethodOptionModel> updateMany(List<SelectionMethodOptionModel> modelList) {
        for (SelectionMethodOptionModel model : modelList) {
            SelectionMethodOptionModel existing = selectionMethodOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new SelectionMethodNotFoundException("Selection method option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new SelectionMethodNotFoundException("Selection method option with ID: " + model.getId() + " is not found");
            }
        }
        return selectionMethodOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single SelectionMethod option model by ID, including deleted ones.
     *
     * @param model                              - The SelectionMethodOptionModel containing updated data
     * @return                                   - The updated SelectionMethodOptionModel
     */
    @Transactional
    public SelectionMethodOptionModel hardUpdate(SelectionMethodOptionModel model) {
        return selectionMethodOptionRepository.save(model);
    }

    /**
     * Updates multiple SelectionMethodOption modelList by their IDs, including deleted ones.
     *
     * @param selectionMethodOptionModelList   - List of SelectionMethodOptionModel objects containing updated data
     * @return                                 - List of updated SelectionMethodOptionModel objects
     */
    @Transactional
    public List<SelectionMethodOptionModel> hardUpdateAll(List<SelectionMethodOptionModel> selectionMethodOptionModelList) {
        return selectionMethodOptionRepository.saveAll(selectionMethodOptionModelList);
    }

    /**
     * Soft deletes a SelectionMethod option by ID.
     *
     * @return                                  - The soft-deleted SelectionMethodOptionModel
     * @throws SelectionMethodNotFoundException - if selection method option id is not found
     */
    @Transactional
    public SelectionMethodOptionModel softDelete(String id) {
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionRepository.findById(id)
                .orElseThrow(() -> new SelectionMethodNotFoundException("Selection method option not found with id: " + id));
        selectionMethodOptionModel.setDeletedAt(LocalDateTime.now());
        return selectionMethodOptionRepository.save(selectionMethodOptionModel);
    }

    /**
     * Hard deletes a SelectionMethod option by ID.
     *
     * @param id                                - ID of the SelectionMethod option to hard delete
     * @throws NullPointerException             - if the SelectionMethod option ID is null
     * @throws SelectionMethodNotFoundException - if the SelectionMethod option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Selection method option ID cannot be null");
        }
        if (!selectionMethodOptionRepository.existsById(id)) {
            throw new SelectionMethodNotFoundException("Selection method option not found with id: " + id);
        }
        selectionMethodOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple SelectionMethod options by their IDs.
     *
     * @param idList                            - List of SelectionMethod option IDs to be soft deleted
     * @return                                  - List of soft-deleted SelectionMethodOption objects
     * @throws SelectionMethodNotFoundException - if any SelectionMethod option ID are not found
     */
    @Transactional
    public List<SelectionMethodOptionModel> softDeleteMany(List<String> idList) {
        List<SelectionMethodOptionModel> selectionMethodOptionModelList = selectionMethodOptionRepository.findAllById(idList);
        if (selectionMethodOptionModelList.isEmpty()) {
            throw new SelectionMethodNotFoundException("No selection method options found with provided IDList: " + idList);
        }
        for (SelectionMethodOptionModel model : selectionMethodOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return selectionMethodOptionRepository.saveAll(selectionMethodOptionModelList);
    }

    /**
     * Hard deletes multiple SelectionMethod options by IDs.
     *
     * @param idList     - List of SelectionMethod option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        selectionMethodOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all SelectionMethod options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        selectionMethodOptionRepository.deleteAll();
    }
}