/**
 * Service for managing EvaluationCriteriaPhaseOption model.
 * Provides functionality to create, read, update, and delete EvaluationCriteriaPhaseOption data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.evaluation_criteria_phase_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.exception.EvaluationCriteriaPhaseAlreadyExistException;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.exception.EvaluationCriteriaPhaseNotFoundException;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.model.EvaluationCriteriaPhaseOptionModel;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.repository.EvaluationCriteriaPhaseOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class EvaluationCriteriaPhaseOptionService {

    @Autowired
    private EvaluationCriteriaPhaseOptionRepository evaluationCriteriaPhaseOptionRepository;

    /**
     * Creates a single EvaluationCriteriaPhase option model with a generated ID.
     *
     * @param evaluationCriteriaPhaseOptionModel            - the EvaluationCriteriaPhaseOptionModel to be created
     * @return                                              - the saved EvaluationCriteriaPhaseOption model
     * @throws EvaluationCriteriaPhaseAlreadyExistException - if a EvaluationCriteriaPhaseOption with the same name exists
     */
    @Transactional
    public EvaluationCriteriaPhaseOptionModel save(EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel) {
        if (evaluationCriteriaPhaseOptionModel == null) {
            throw new NullPointerException("Evaluation criteria phase option cannot be null");
        }
        if (evaluationCriteriaPhaseOptionRepository.existsByName(evaluationCriteriaPhaseOptionModel.getName())) {
            throw new EvaluationCriteriaPhaseAlreadyExistException("Evaluation criteria phase option already exists: " + evaluationCriteriaPhaseOptionModel.getName());
        }
        return evaluationCriteriaPhaseOptionRepository.save(evaluationCriteriaPhaseOptionModel);
    }

    /**
     * Creates multiple EvaluationCriteriaPhaseOption model, each with a unique generated ID.
     *
     * @param evaluationCriteriaPhaseOptionModelList - the list of EvaluationCriteriaPhase option model to be created
     * @return                                       - a list of saved EvaluationCriteriaPhaseOption model
     * @throws NullPointerException                  - if the input list is null
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> saveMany(List<EvaluationCriteriaPhaseOptionModel> evaluationCriteriaPhaseOptionModelList) {
        if (evaluationCriteriaPhaseOptionModelList == null || evaluationCriteriaPhaseOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Evaluation criteria phase option model list cannot be null or empty");
        }
        for (EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel : evaluationCriteriaPhaseOptionModelList) {
            if (evaluationCriteriaPhaseOptionRepository.existsByName(evaluationCriteriaPhaseOptionModel.getName())) {
                throw new EvaluationCriteriaPhaseAlreadyExistException("Evaluation criteria phase option already exists: " + evaluationCriteriaPhaseOptionModel.getName());
            }
        }
        return evaluationCriteriaPhaseOptionRepository.saveAll(evaluationCriteriaPhaseOptionModelList);
    }

    /**
     * Retrieves a single EvaluationCriteriaPhase option model by its ID.
     * Throws a EvaluationCriteriaPhaseNotFoundException if the EvaluationCriteriaPhase option is not found or has been deleted.
     *
     * @param id                                        - the ID of the EvaluationCriteriaPhase option to retrieve
     * @return                                          - the EvaluationCriteriaPhase option model if found and not deleted
     * @throws EvaluationCriteriaPhaseNotFoundException - if the EvaluationCriteriaPhase option is not found
     * @throws NullPointerException                     - if EvaluationCriteriaPhase option ID is null
     */
    @Transactional
    public EvaluationCriteriaPhaseOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Evaluation criteria phase option ID cannot be null");
        }
        EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel = evaluationCriteriaPhaseOptionRepository.findById(id)
                .orElseThrow(() -> new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option not found with ID: " + id));
        if (evaluationCriteriaPhaseOptionModel.getDeletedAt() != null) {
            throw new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option not found with ID: " + id);
        }
        return evaluationCriteriaPhaseOptionModel;
    }

    /**
     * Retrieves a list of EvaluationCriteriaPhaseOption model list based on the provided EvaluationCriteriaPhaseOption ID.
     *
     * @param evaluationCriteriaPhaseOptionIdList - A list of EvaluationCriteriaPhaseOption ID to retrieve
     * @return                                    - A list of EvaluationCriteriaPhaseOptionModel objects that are not marked as deleted
     * @throws NullPointerException               - if a EvaluationCriteriaPhaseOption ID list is null
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> readMany(List<String> evaluationCriteriaPhaseOptionIdList) {
        if (evaluationCriteriaPhaseOptionIdList == null || evaluationCriteriaPhaseOptionIdList.isEmpty()) {
            throw new NullPointerException("Evaluation criteria phase option ID list cannot be null");
        }
        List<EvaluationCriteriaPhaseOptionModel> modelList = new ArrayList<>();
        for (String id : evaluationCriteriaPhaseOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Evaluation criteria phase option ID cannot be null");
            }
            EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel = evaluationCriteriaPhaseOptionRepository.findById(id)
                    .orElse(null);
            if (evaluationCriteriaPhaseOptionModel == null)
                continue;
            if (evaluationCriteriaPhaseOptionModel.getDeletedAt() == null) {
                modelList.add(evaluationCriteriaPhaseOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all EvaluationCriteriaPhase options that are not marked as deleted
     *
     * @return         - a List of EvaluationCriteriaPhase option model where deletedAt is null
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> readAll() {
        return evaluationCriteriaPhaseOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all EvaluationCriteriaPhaseOption model, including those marked as deleted.
     *
     * @return            - A list of all EvaluationCriteriaPhaseOptionModel objects
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> hardReadAll() {
        return evaluationCriteriaPhaseOptionRepository.findAll();
    }

    /**
     * Updates a single EvaluationCriteriaPhaseOption model identified by the provided ID.
     *
     * @param model                                     - The EvaluationCriteriaPhaseOptionModel containing updated data
     * @return                                          - The updated EvaluationCriteriaPhaseOptionModel
     * @throws EvaluationCriteriaPhaseNotFoundException - if evaluation criteria phase option is not found
     */
    @Transactional
    public EvaluationCriteriaPhaseOptionModel updateOne(EvaluationCriteriaPhaseOptionModel model) {
        EvaluationCriteriaPhaseOptionModel existing = evaluationCriteriaPhaseOptionRepository.findById(model.getId())
                .orElseThrow(() -> new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option with ID: " + model.getId() + " is not found");
        }
        return evaluationCriteriaPhaseOptionRepository.save(model);
    }

    /**
     * Updates multiple evaluation criteria phase option model in a transactional manner.
     *
     * @param modelList                                 - List of EvaluationCriteriaPhaseOptionModel objects containing updated data
     * @return                                          - List of updated EvaluationCriteriaPhaseOptionModel objects
     * @throws EvaluationCriteriaPhaseNotFoundException - if evaluation criteria phase option is not found
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> updateMany(List<EvaluationCriteriaPhaseOptionModel> modelList) {
        for (EvaluationCriteriaPhaseOptionModel model : modelList) {
            EvaluationCriteriaPhaseOptionModel existing = evaluationCriteriaPhaseOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option with ID: " + model.getId() + " is not found");
            }
        }
        return evaluationCriteriaPhaseOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single EvaluationCriteriaPhase option model by ID, including deleted ones.
     *
     * @param model                                  - The EvaluationCriteriaPhaseOptionModel containing updated data
     * @return                                       - The updated EvaluationCriteriaPhaseOptionModel
     */
    @Transactional
    public EvaluationCriteriaPhaseOptionModel hardUpdate(EvaluationCriteriaPhaseOptionModel model) {
        return evaluationCriteriaPhaseOptionRepository.save(model);
    }

    /**
     * Updates multiple EvaluationCriteriaPhaseOption modelList by their IDs, including deleted ones.
     *
     * @param evaluationCriteriaPhaseOptionModelList - List of EvaluationCriteriaPhaseOptionModel objects containing updated data
     * @return                                       - List of updated EvaluationCriteriaPhaseOptionModel objects
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> hardUpdateAll(List<EvaluationCriteriaPhaseOptionModel> evaluationCriteriaPhaseOptionModelList) {
        return evaluationCriteriaPhaseOptionRepository.saveAll(evaluationCriteriaPhaseOptionModelList);
    }

    /**
     * Soft deletes a EvaluationCriteriaPhase option by ID.
     *
     * @return                                          - The soft-deleted EvaluationCriteriaPhaseOptionModel
     * @throws EvaluationCriteriaPhaseNotFoundException - if evaluation criteria phase option id is not found
     */
    @Transactional
    public EvaluationCriteriaPhaseOptionModel softDelete(String id) {
        EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel = evaluationCriteriaPhaseOptionRepository.findById(id)
                .orElseThrow(() -> new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option not found with id: " + id));
        evaluationCriteriaPhaseOptionModel.setDeletedAt(LocalDateTime.now());
        return evaluationCriteriaPhaseOptionRepository.save(evaluationCriteriaPhaseOptionModel);
    }

    /**
     * Hard deletes a EvaluationCriteriaPhase option by ID.
     *
     * @param id                                        - ID of the EvaluationCriteriaPhase option to hard delete
     * @throws NullPointerException                     - if the EvaluationCriteriaPhase option ID is null
     * @throws EvaluationCriteriaPhaseNotFoundException - if the EvaluationCriteriaPhase option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Evaluation criteria phase option ID cannot be null");
        }
        if (!evaluationCriteriaPhaseOptionRepository.existsById(id)) {
            throw new EvaluationCriteriaPhaseNotFoundException("Evaluation criteria phase option not found with id: " + id);
        }
        evaluationCriteriaPhaseOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple EvaluationCriteriaPhase options by their IDs.
     *
     * @param idList                                    - List of EvaluationCriteriaPhase option IDs to be soft deleted
     * @return                                          - List of soft-deleted EvaluationCriteriaPhaseOption objects
     * @throws EvaluationCriteriaPhaseNotFoundException - if any EvaluationCriteriaPhase option ID are not found
     */
    @Transactional
    public List<EvaluationCriteriaPhaseOptionModel> softDeleteMany(List<String> idList) {
        List<EvaluationCriteriaPhaseOptionModel> evaluationCriteriaPhaseOptionModelList = evaluationCriteriaPhaseOptionRepository.findAllById(idList);
        if (evaluationCriteriaPhaseOptionModelList.isEmpty()) {
            throw new EvaluationCriteriaPhaseNotFoundException("No evaluation criteria phase options found with provided IDList: " + idList);
        }
        for (EvaluationCriteriaPhaseOptionModel model : evaluationCriteriaPhaseOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return evaluationCriteriaPhaseOptionRepository.saveAll(evaluationCriteriaPhaseOptionModelList);
    }

    /**
     * Hard deletes multiple EvaluationCriteriaPhase options by IDs.
     *
     * @param idList     - List of EvaluationCriteriaPhase option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        evaluationCriteriaPhaseOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all EvaluationCriteriaPhase options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        evaluationCriteriaPhaseOptionRepository.deleteAll();
    }
}