/**
 * Service for managing PrebidEventType model.
 * Provides functionality to create, read, update, and delete PrebidEventType data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.prebid_event_type.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.prebid_event_type.exception.PrebidEventTypeAlreadyExistException;
import rw.evolve.eprocurement.prebid_event_type.exception.PrebidEventTypeNotFoundException;
import rw.evolve.eprocurement.prebid_event_type.model.PrebidEventTypeModel;
import rw.evolve.eprocurement.prebid_event_type.repository.PrebidEventTypeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class PrebidEventTypeService {

    @Autowired
    private PrebidEventTypeRepository prebidEventTypeRepository;

    /**
     * Creates a single PrebidEventType option model with a generated ID.
     *
     * @param prebidEventTypeModel                  - the PrebidEventTypeModel to be created
     * @return                                      - the saved PrebidEventType model
     * @throws PrebidEventTypeAlreadyExistException - if a PrebidEventType with the same name exists
     */
    @Transactional
    public PrebidEventTypeModel save(PrebidEventTypeModel prebidEventTypeModel) {
        if (prebidEventTypeModel == null) {
            throw new NullPointerException("Prebid event type cannot be null");
        }
        if (prebidEventTypeRepository.existsByName(prebidEventTypeModel.getName())) {
            throw new PrebidEventTypeAlreadyExistException("Prebid event type already exists: " + prebidEventTypeModel.getName());
        }
        return prebidEventTypeRepository.save(prebidEventTypeModel);
    }

    /**
     * Creates multiple PrebidEventType Option model, each with a unique generated ID.
     *
     * @param prebidEventTypeModelList - the list of PrebidEventType option model to be created
     * @return                         - a list of saved PrebidEventType Option model
     * @throws NullPointerException    - if the input list is null
     */
    @Transactional
    public List<PrebidEventTypeModel> saveMany(List<PrebidEventTypeModel> prebidEventTypeModelList) {
        if (prebidEventTypeModelList == null || prebidEventTypeModelList.isEmpty()) {
            throw new IllegalArgumentException("Prebid event type model list cannot be null or empty");
        }
        for (PrebidEventTypeModel prebidEventTypeModel : prebidEventTypeModelList) {
            if (prebidEventTypeRepository.existsByName(prebidEventTypeModel.getName())) {
                throw new PrebidEventTypeAlreadyExistException("Prebid event type already exists: " + prebidEventTypeModel.getName());
            }
        }
        return prebidEventTypeRepository.saveAll(prebidEventTypeModelList);
    }

    /**
     * Retrieves a single PrebidEventType option model by its ID.
     * Throws a PrebidEventTypeNotFoundException if the PrebidEventType option is not found or has been deleted.
     *
     * @param id                                - the ID of the PrebidEventType option to retrieve
     * @return                                  - the PrebidEventType option model if found and not deleted
     * @throws PrebidEventTypeNotFoundException - if the PrebidEventType option is not found
     * @throws NullPointerException             - if PrebidEventType option ID is null
     */
    @Transactional
    public PrebidEventTypeModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Prebid event type ID cannot be null");
        }
        PrebidEventTypeModel prebidEventTypeModel = prebidEventTypeRepository.findById(id)
                .orElseThrow(() -> new PrebidEventTypeNotFoundException("Prebid event type not found with ID: " + id));
        if (prebidEventTypeModel.getDeletedAt() != null) {
            throw new PrebidEventTypeNotFoundException("Prebid event type not found with ID: " + id);
        }
        return prebidEventTypeModel;
    }

    /**
     * Retrieves a list of PrebidEventType model list based on the provided PrebidEventType ID.
     *
     * @param prebidEventTypeIdList    - A list of PrebidEventType ID to retrieve
     * @return                         - A list of PrebidEventTypeModel objects that are not marked as deleted
     * @throws NullPointerException    - if a PrebidEventType ID list is null
     */
    @Transactional
    public List<PrebidEventTypeModel> readMany(List<String> prebidEventTypeIdList) {
        if (prebidEventTypeIdList == null || prebidEventTypeIdList.isEmpty()) {
            throw new NullPointerException("Prebid event type ID list cannot be null");
        }
        List<PrebidEventTypeModel> modelList = new ArrayList<>();
        for (String id : prebidEventTypeIdList) {
            if (id == null) {
                throw new NullPointerException("Prebid event type ID cannot be null");
            }
            PrebidEventTypeModel prebidEventTypeModel = prebidEventTypeRepository.findById(id)
                    .orElse(null);
            if (prebidEventTypeModel == null)
                continue;
            if (prebidEventTypeModel.getDeletedAt() == null) {
                modelList.add(prebidEventTypeModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all PrebidEventType options that are not marked as deleted
     *
     * @return         - a List of PrebidEventType option model where deletedAt is null
     */
    @Transactional
    public List<PrebidEventTypeModel> readAll() {
        return prebidEventTypeRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all PrebidEventType Option model, including those marked as deleted.
     *
     * @return            - A list of all PrebidEventTypeModel objects
     */
    @Transactional
    public List<PrebidEventTypeModel> hardReadAll() {
        return prebidEventTypeRepository.findAll();
    }

    /**
     * Updates a single PrebidEventType Option model identified by the provided ID.
     *
     * @param model                             - The PrebidEventTypeModel containing updated data
     * @return                                  - The updated PrebidEventTypeModel
     * @throws PrebidEventTypeNotFoundException - if prebid event type is not found
     */
    @Transactional
    public PrebidEventTypeModel updateOne(PrebidEventTypeModel model) {
        PrebidEventTypeModel existing = prebidEventTypeRepository.findById(model.getId())
                .orElseThrow(() -> new PrebidEventTypeNotFoundException("Prebid event type not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new PrebidEventTypeNotFoundException("Prebid event type with ID: " + model.getId() + " is not found");
        }
        return prebidEventTypeRepository.save(model);
    }

    /**
     * Updates multiple prebid event type model in a transactional manner.
     *
     * @param modelList                         - List of PrebidEventTypeModel objects containing updated data
     * @return                                  - List of updated PrebidEventTypeModel objects
     * @throws PrebidEventTypeNotFoundException - if prebid event type is not found
     */
    @Transactional
    public List<PrebidEventTypeModel> updateMany(List<PrebidEventTypeModel> modelList) {
        for (PrebidEventTypeModel model : modelList) {
            PrebidEventTypeModel existing = prebidEventTypeRepository.findById(model.getId())
                    .orElseThrow(() -> new PrebidEventTypeNotFoundException("Prebid event type not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new PrebidEventTypeNotFoundException("Prebid event type with ID: " + model.getId() + " is not found");
            }
        }
        return prebidEventTypeRepository.saveAll(modelList);
    }

    /**
     * Updates a single PrebidEventType option model by ID, including deleted ones.
     *
     * @param model                           - The PrebidEventTypeModel containing updated data
     * @return                                - The updated PrebidEventTypeModel
     */
    @Transactional
    public PrebidEventTypeModel hardUpdate(PrebidEventTypeModel model) {
        return prebidEventTypeRepository.save(model);
    }

    /**
     * Updates multiple PrebidEventType modelList by their IDs, including deleted ones.
     *
     * @param prebidEventTypeModelList      - List of PrebidEventTypeModel objects containing updated data
     * @return                              - List of updated PrebidEventTypeModel objects
     */
    @Transactional
    public List<PrebidEventTypeModel> hardUpdateAll(List<PrebidEventTypeModel> prebidEventTypeModelList) {
        return prebidEventTypeRepository.saveAll(prebidEventTypeModelList);
    }

    /**
     * Soft deletes a PrebidEventType option by ID.
     *
     * @return                                  - The soft-deleted PrebidEventTypeModel
     * @throws PrebidEventTypeNotFoundException - if prebid event type id is not found
     */
    @Transactional
    public PrebidEventTypeModel softDelete(String id) {
        PrebidEventTypeModel prebidEventTypeModel = prebidEventTypeRepository.findById(id)
                .orElseThrow(() -> new PrebidEventTypeNotFoundException("Prebid event type not found with id: " + id));
        prebidEventTypeModel.setDeletedAt(LocalDateTime.now());
        return prebidEventTypeRepository.save(prebidEventTypeModel);
    }

    /**
     * Hard deletes a PrebidEventType option by ID.
     *
     * @param id                                - ID of the PrebidEventType option to hard delete
     * @throws NullPointerException             - if the PrebidEventType option ID is null
     * @throws PrebidEventTypeNotFoundException - if the PrebidEventType option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Prebid event type ID cannot be null");
        }
        if (!prebidEventTypeRepository.existsById(id)) {
            throw new PrebidEventTypeNotFoundException("Prebid event type not found with id: " + id);
        }
        prebidEventTypeRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple PrebidEventType options by their IDs.
     *
     * @param idList                            - List of PrebidEventType option IDs to be soft deleted
     * @return                                  - List of soft-deleted PrebidEventType objects
     * @throws PrebidEventTypeNotFoundException - if any PrebidEventType option ID are not found
     */
    @Transactional
    public List<PrebidEventTypeModel> softDeleteMany(List<String> idList) {
        List<PrebidEventTypeModel> prebidEventTypeModelList = prebidEventTypeRepository.findAllById(idList);
        if (prebidEventTypeModelList.isEmpty()) {
            throw new PrebidEventTypeNotFoundException("No prebid event types found with provided IDList: " + idList);
        }
        for (PrebidEventTypeModel model : prebidEventTypeModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return prebidEventTypeRepository.saveAll(prebidEventTypeModelList);
    }

    /**
     * Hard deletes multiple PrebidEventType options by IDs.
     *
     * @param idList     - List of PrebidEventType option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        prebidEventTypeRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all PrebidEventType options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        prebidEventTypeRepository.deleteAll();
    }
}