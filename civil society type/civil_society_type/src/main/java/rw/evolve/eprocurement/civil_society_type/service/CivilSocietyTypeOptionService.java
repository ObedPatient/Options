/**
 * Service for managing CivilSocietyTypeOption model.
 * Provides functionality to create, read, update, and delete CivilSocietyTypeOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.civil_society_type.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.civil_society_type.exception.CivilSocietyTypeOptionAlreadyExistException;
import rw.evolve.eprocurement.civil_society_type.exception.CivilSocietyTypeOptionNotFoundException;
import rw.evolve.eprocurement.civil_society_type.model.CivilSocietyTypeOptionModel;
import rw.evolve.eprocurement.civil_society_type.repository.CivilSocietyTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CivilSocietyTypeOptionService {

    @Autowired
    private CivilSocietyTypeOptionRepository civilSocietyTypeOptionRepository;

    /**
     * Creates a single CivilSocietyType option model with a generated ID.
     *
     * @param civilSocietyTypeOptionModel                  - the CivilSocietyTypeOptionModel to be created
     * @return                                             - the saved CivilSocietyTypeOption model
     * @throws CivilSocietyTypeOptionAlreadyExistException - if a CivilSocietyTypeOption with the same name exists
     */
    @Transactional
    public CivilSocietyTypeOptionModel save(CivilSocietyTypeOptionModel civilSocietyTypeOptionModel) {
        if (civilSocietyTypeOptionModel == null) {
            throw new NullPointerException("Civil society type option cannot be null");
        }
        if (civilSocietyTypeOptionRepository.existsByName(civilSocietyTypeOptionModel.getName())) {
            throw new CivilSocietyTypeOptionAlreadyExistException("Civil society type option already exists: " + civilSocietyTypeOptionModel.getName());
        }
        return civilSocietyTypeOptionRepository.save(civilSocietyTypeOptionModel);
    }

    /**
     * Creates multiple CivilSocietyType Option model, each with a unique generated ID.
     *
     * @param civilSocietyTypeOptionModelList - the list of CivilSocietyType option model to be created
     * @return                                - a list of saved CivilSocietyType Option model
     * @throws NullPointerException           - if the input list is null
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> saveMany(List<CivilSocietyTypeOptionModel> civilSocietyTypeOptionModelList) {
        if (civilSocietyTypeOptionModelList == null || civilSocietyTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Civil society type option model list cannot be null or empty");
        }
        for (CivilSocietyTypeOptionModel civilSocietyTypeOptionModel : civilSocietyTypeOptionModelList) {
            if (civilSocietyTypeOptionRepository.existsByName(civilSocietyTypeOptionModel.getName())) {
                throw new CivilSocietyTypeOptionAlreadyExistException("Civil society type option already exists: " + civilSocietyTypeOptionModel.getName());
            }
        }
        return civilSocietyTypeOptionRepository.saveAll(civilSocietyTypeOptionModelList);
    }

    /**
     * Retrieves a single CivilSocietyType option model by its ID.
     * Throws a CivilSocietyTypeOptionNotFoundException if the CivilSocietyType option is not found or has been deleted.
     *
     * @param id                                       - the ID of the CivilSocietyType option to retrieve
     * @return                                         - the CivilSocietyType option model if found and not deleted
     * @throws CivilSocietyTypeOptionNotFoundException - if the CivilSocietyType option is not found
     * @throws NullPointerException                    - if CivilSocietyType option ID is null
     */
    @Transactional
    public CivilSocietyTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Civil society type option ID cannot be null");
        }
        CivilSocietyTypeOptionModel civilSocietyTypeOptionModel = civilSocietyTypeOptionRepository.findById(id)
                .orElseThrow(() -> new CivilSocietyTypeOptionNotFoundException("Civil society type option not found with ID: " + id));
        if (civilSocietyTypeOptionModel.getDeletedAt() != null) {
            throw new CivilSocietyTypeOptionNotFoundException("Civil society type option not found with ID: " + id);
        }
        return civilSocietyTypeOptionModel;
    }

    /**
     * Retrieves a list of CivilSocietyTypeOption model list based on the provided CivilSocietyTypeOption ID.
     *
     * @param civilSocietyTypeOptionIdList - A list of CivilSocietyTypeOption ID to retrieve
     * @return                             - A list of CivilSocietyTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException        - if a CivilSocietyTypeOption ID list is null
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> readMany(List<String> civilSocietyTypeOptionIdList) {
        if (civilSocietyTypeOptionIdList == null || civilSocietyTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Civil society type option ID list cannot be null");
        }
        List<CivilSocietyTypeOptionModel> modelList = new ArrayList<>();
        for (String id : civilSocietyTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Civil society type option ID cannot be null");
            }
            CivilSocietyTypeOptionModel civilSocietyTypeOptionModel = civilSocietyTypeOptionRepository.findById(id)
                    .orElse(null);
            if (civilSocietyTypeOptionModel == null)
                continue;
            if (civilSocietyTypeOptionModel.getDeletedAt() == null) {
                modelList.add(civilSocietyTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all CivilSocietyType options that are not marked as deleted
     *
     * @return         - a List of CivilSocietyType option model where deletedAt is null
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> readAll() {
        return civilSocietyTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all CivilSocietyType Option model, including those marked as deleted.
     *
     * @return            - A list of all CivilSocietyTypeOptionModel objects
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> hardReadAll() {
        return civilSocietyTypeOptionRepository.findAll();
    }

    /**
     * Updates a single CivilSocietyType Option model identified by the provided ID.
     *
     * @param model                                    - The CivilSocietyTypeOptionModel containing updated data
     * @return                                         - The updated CivilSocietyTypeOptionModel
     * @throws CivilSocietyTypeOptionNotFoundException - if civil society type option is not found
     */
    @Transactional
    public CivilSocietyTypeOptionModel updateOne(CivilSocietyTypeOptionModel model) {
        CivilSocietyTypeOptionModel existing = civilSocietyTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new CivilSocietyTypeOptionNotFoundException("Civil society type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new CivilSocietyTypeOptionNotFoundException("Civil society type option with ID: " + model.getId() + " is not found");
        }
        return civilSocietyTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple civil society type option model in a transactional manner.
     *
     * @param modelList                                - List of CivilSocietyTypeOptionModel objects containing updated data
     * @return                                         - List of updated CivilSocietyTypeOptionModel objects
     * @throws CivilSocietyTypeOptionNotFoundException - if civil society type option is not found
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> updateMany(List<CivilSocietyTypeOptionModel> modelList) {
        for (CivilSocietyTypeOptionModel model : modelList) {
            CivilSocietyTypeOptionModel existing = civilSocietyTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new CivilSocietyTypeOptionNotFoundException("Civil society type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new CivilSocietyTypeOptionNotFoundException("Civil society type option with ID: " + model.getId() + " is not found");
            }
        }
        return civilSocietyTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single CivilSocietyType option model by ID, including deleted ones.
     *
     * @param model                               - The CivilSocietyTypeOptionModel containing updated data
     * @return                                    - The updated CivilSocietyTypeOptionModel
     */
    @Transactional
    public CivilSocietyTypeOptionModel hardUpdate(CivilSocietyTypeOptionModel model) {
        return civilSocietyTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple CivilSocietyTypeOption modelList by their IDs, including deleted ones.
     *
     * @param civilSocietyTypeOptionModelList   - List of CivilSocietyTypeOptionModel objects containing updated data
     * @return                                  - List of updated CivilSocietyTypeOptionModel objects
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> hardUpdateAll(List<CivilSocietyTypeOptionModel> civilSocietyTypeOptionModelList) {
        return civilSocietyTypeOptionRepository.saveAll(civilSocietyTypeOptionModelList);
    }

    /**
     * Soft deletes a CivilSocietyType option by ID.
     *
     * @return                                         - The soft-deleted CivilSocietyTypeOptionModel
     * @throws CivilSocietyTypeOptionNotFoundException - if civil society type option id is not found
     */
    @Transactional
    public CivilSocietyTypeOptionModel softDelete(String id) {
        CivilSocietyTypeOptionModel civilSocietyTypeOptionModel = civilSocietyTypeOptionRepository.findById(id)
                .orElseThrow(() -> new CivilSocietyTypeOptionNotFoundException("Civil society type option not found with id: " + id));
        civilSocietyTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return civilSocietyTypeOptionRepository.save(civilSocietyTypeOptionModel);
    }

    /**
     * Hard deletes a CivilSocietyType option by ID.
     *
     * @param id                                       - ID of the CivilSocietyType option to hard delete
     * @throws NullPointerException                    - if the CivilSocietyType option ID is null
     * @throws CivilSocietyTypeOptionNotFoundException - if the CivilSocietyType option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Civil society type option ID cannot be null");
        }
        if (!civilSocietyTypeOptionRepository.existsById(id)) {
            throw new CivilSocietyTypeOptionNotFoundException("Civil society type option not found with id: " + id);
        }
        civilSocietyTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple CivilSocietyType options by their IDs.
     *
     * @param idList                                   - List of CivilSocietyType option IDs to be soft deleted
     * @return                                         - List of soft-deleted CivilSocietyTypeOption objects
     * @throws CivilSocietyTypeOptionNotFoundException - if any CivilSocietyType option ID are not found
     */
    @Transactional
    public List<CivilSocietyTypeOptionModel> softDeleteMany(List<String> idList) {
        List<CivilSocietyTypeOptionModel> civilSocietyTypeOptionModelList = civilSocietyTypeOptionRepository.findAllById(idList);
        if (civilSocietyTypeOptionModelList.isEmpty()) {
            throw new CivilSocietyTypeOptionNotFoundException("No civil society type options found with provided IDList: " + idList);
        }
        for (CivilSocietyTypeOptionModel model : civilSocietyTypeOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return civilSocietyTypeOptionRepository.saveAll(civilSocietyTypeOptionModelList);
    }

    /**
     * Hard deletes multiple CivilSocietyType options by IDs.
     *
     * @param idList     - List of CivilSocietyType option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        civilSocietyTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all CivilSocietyType options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        civilSocietyTypeOptionRepository.deleteAll();
    }
}