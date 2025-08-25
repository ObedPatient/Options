/**
 * Service for managing TenderRequiredDocumentType model.
 * Provides functionality to create, read, update, and delete TenderRequiredDocumentType data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
package rw.evolve.eprocurement.tender_required_document_type.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.tender_required_document_type.exception.TenderRequiredDocumentTypeAlreadyExistException;
import rw.evolve.eprocurement.tender_required_document_type.exception.TenderRequiredDocumentTypeNotFoundException;
import rw.evolve.eprocurement.tender_required_document_type.model.TenderRequiredDocumentTypeModel;
import rw.evolve.eprocurement.tender_required_document_type.repository.TenderRequiredDocumentTypeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class TenderRequiredDocumentTypeOptionService {

    private TenderRequiredDocumentTypeRepository tenderRequiredDocumentTypeRepository;

    /**
     * Creates a single TenderRequiredDocumentType option model with a generated ID.
     *
     * @param tenderRequiredDocumentTypeModel                  - the TenderRequiredDocumentTypeModel to be created
     * @return                                                 - the saved TenderRequiredDocumentType model
     * @throws TenderRequiredDocumentTypeAlreadyExistException - if a TenderRequiredDocumentType with the same name exists
     */
    @Transactional
    public TenderRequiredDocumentTypeModel save(TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel) {
        if (tenderRequiredDocumentTypeModel == null) {
            throw new NullPointerException("Tender required document type cannot be null");
        }
        if (tenderRequiredDocumentTypeRepository.existsByName(tenderRequiredDocumentTypeModel.getName())) {
            throw new TenderRequiredDocumentTypeAlreadyExistException("Tender required document type already exists: " + tenderRequiredDocumentTypeModel.getName());
        }
        return tenderRequiredDocumentTypeRepository.save(tenderRequiredDocumentTypeModel);
    }

    /**
     * Creates multiple TenderRequiredDocumentType Option model, each with a unique generated ID.
     *
     * @param tenderRequiredDocumentTypeModelList - the list of TenderRequiredDocumentType option model to be created
     * @return                                    - a list of saved TenderRequiredDocumentType Option model
     * @throws NullPointerException               - if the input list is null
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> saveMany(List<TenderRequiredDocumentTypeModel> tenderRequiredDocumentTypeModelList) {
        if (tenderRequiredDocumentTypeModelList == null || tenderRequiredDocumentTypeModelList.isEmpty()) {
            throw new IllegalArgumentException("Tender required document type model list cannot be null or empty");
        }
        for (TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel : tenderRequiredDocumentTypeModelList) {
            if (tenderRequiredDocumentTypeRepository.existsByName(tenderRequiredDocumentTypeModel.getName())) {
                throw new TenderRequiredDocumentTypeAlreadyExistException("Tender required document type already exists: " + tenderRequiredDocumentTypeModel.getName());
            }
        }
        return tenderRequiredDocumentTypeRepository.saveAll(tenderRequiredDocumentTypeModelList);
    }

    /**
     * Retrieves a single TenderRequiredDocumentType option model by its ID.
     * Throws a TenderRequiredDocumentTypeNotFoundException if the TenderRequiredDocumentType option is not found or has been deleted.
     *
     * @param id                                           - the ID of the TenderRequiredDocumentType option to retrieve
     * @return                                             - the TenderRequiredDocumentType option model if found and not deleted
     * @throws TenderRequiredDocumentTypeNotFoundException - if the TenderRequiredDocumentType option is not found
     * @throws NullPointerException                        - if TenderRequiredDocumentType option ID is null
     */
    @Transactional
    public TenderRequiredDocumentTypeModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Tender required document type ID cannot be null");
        }
        TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel = tenderRequiredDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new TenderRequiredDocumentTypeNotFoundException("Tender required document type not found with ID: " + id));
        if (tenderRequiredDocumentTypeModel.getDeletedAt() != null) {
            throw new TenderRequiredDocumentTypeNotFoundException("Tender required document type not found with ID: " + id);
        }
        return tenderRequiredDocumentTypeModel;
    }

    /**
     * Retrieves a list of TenderRequiredDocumentType model list based on the provided TenderRequiredDocumentType ID.
     *
     * @param tenderRequiredDocumentTypeIdList - A list of TenderRequiredDocumentType ID to retrieve
     * @return                                 - A list of TenderRequiredDocumentTypeModel objects that are not marked as deleted
     * @throws NullPointerException            - if a TenderRequiredDocumentType ID list is null
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> readMany(List<String> tenderRequiredDocumentTypeIdList) {
        if (tenderRequiredDocumentTypeIdList == null || tenderRequiredDocumentTypeIdList.isEmpty()) {
            throw new NullPointerException("Tender required document type ID list cannot be null");
        }
        List<TenderRequiredDocumentTypeModel> modelList = new ArrayList<>();
        for (String id : tenderRequiredDocumentTypeIdList) {
            if (id == null) {
                throw new NullPointerException("Tender required document type ID cannot be null");
            }
            TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel = tenderRequiredDocumentTypeRepository.findById(id)
                    .orElse(null);
            if (tenderRequiredDocumentTypeModel == null)
                continue;
            if (tenderRequiredDocumentTypeModel.getDeletedAt() == null) {
                modelList.add(tenderRequiredDocumentTypeModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all TenderRequiredDocumentType options that are not marked as deleted
     *
     * @return         - a List of TenderRequiredDocumentType option model where deletedAt is null
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> readAll() {
        return tenderRequiredDocumentTypeRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all TenderRequiredDocumentType Option model, including those marked as deleted.
     *
     * @return            - A list of all TenderRequiredDocumentTypeModel objects
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> hardReadAll() {
        return tenderRequiredDocumentTypeRepository.findAll();
    }

    /**
     * Updates a single TenderRequiredDocumentType Option model identified by the provided ID.
     *
     * @param model                                        - The TenderRequiredDocumentTypeModel containing updated data
     * @return                                             - The updated TenderRequiredDocumentTypeModel
     * @throws TenderRequiredDocumentTypeNotFoundException - if tender required document type is not found
     */
    @Transactional
    public TenderRequiredDocumentTypeModel updateOne(TenderRequiredDocumentTypeModel model) {
        TenderRequiredDocumentTypeModel existing = tenderRequiredDocumentTypeRepository.findById(model.getId())
                .orElseThrow(() -> new TenderRequiredDocumentTypeNotFoundException("Tender required document type not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new TenderRequiredDocumentTypeNotFoundException("Tender required document type with ID: " + model.getId() + " is not found");
        }
        return tenderRequiredDocumentTypeRepository.save(model);
    }

    /**
     * Updates multiple tender required document type model in a transactional manner.
     *
     * @param modelList                                    - List of TenderRequiredDocumentTypeModel objects containing updated data
     * @return                                             - List of updated TenderRequiredDocumentTypeModel objects
     * @throws TenderRequiredDocumentTypeNotFoundException - if tender required document type is not found
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> updateMany(List<TenderRequiredDocumentTypeModel> modelList) {
        for (TenderRequiredDocumentTypeModel model : modelList) {
            TenderRequiredDocumentTypeModel existing = tenderRequiredDocumentTypeRepository.findById(model.getId())
                    .orElseThrow(() -> new TenderRequiredDocumentTypeNotFoundException("Tender required document type not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new TenderRequiredDocumentTypeNotFoundException("Tender required document type with ID: " + model.getId() + " is not found");
            }
        }
        return tenderRequiredDocumentTypeRepository.saveAll(modelList);
    }

    /**
     * Updates a single TenderRequiredDocumentType option model by ID, including deleted ones.
     *
     * @param model                                       - The TenderRequiredDocumentTypeModel containing updated data
     * @return                                            - The updated TenderRequiredDocumentTypeModel
     */
    @Transactional
    public TenderRequiredDocumentTypeModel hardUpdate(TenderRequiredDocumentTypeModel model) {
        return tenderRequiredDocumentTypeRepository.save(model);
    }

    /**
     * Updates multiple TenderRequiredDocumentType modelList by their IDs, including deleted ones.
     *
     * @param tenderRequiredDocumentTypeModelList       - List of TenderRequiredDocumentTypeModel objects containing updated data
     * @return                                          - List of updated TenderRequiredDocumentTypeModel objects
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> hardUpdateAll(List<TenderRequiredDocumentTypeModel> tenderRequiredDocumentTypeModelList) {
        return tenderRequiredDocumentTypeRepository.saveAll(tenderRequiredDocumentTypeModelList);
    }

    /**
     * Soft deletes a TenderRequiredDocumentType option by ID.
     *
     * @return                                             - The soft-deleted TenderRequiredDocumentTypeModel
     * @throws TenderRequiredDocumentTypeNotFoundException - if tender required document type id is not found
     */
    @Transactional
    public TenderRequiredDocumentTypeModel softDelete(String id) {
        TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel = tenderRequiredDocumentTypeRepository.findById(id)
                .orElseThrow(() -> new TenderRequiredDocumentTypeNotFoundException("Tender required document type not found with id: " + id));
        tenderRequiredDocumentTypeModel.setDeletedAt(LocalDateTime.now());
        return tenderRequiredDocumentTypeRepository.save(tenderRequiredDocumentTypeModel);
    }

    /**
     * Hard deletes a TenderRequiredDocumentType option by ID.
     *
     * @param id                                           - ID of the TenderRequiredDocumentType option to hard delete
     * @throws NullPointerException                        - if the TenderRequiredDocumentType option ID is null
     * @throws TenderRequiredDocumentTypeNotFoundException - if the TenderRequiredDocumentType option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Tender required document type ID cannot be null");
        }
        if (!tenderRequiredDocumentTypeRepository.existsById(id)) {
            throw new TenderRequiredDocumentTypeNotFoundException("Tender required document type not found with id: " + id);
        }
        tenderRequiredDocumentTypeRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple TenderRequiredDocumentType options by their IDs.
     *
     * @param idList                                       - List of TenderRequiredDocumentType option IDs to be soft deleted
     * @return                                             - List of soft-deleted TenderRequiredDocumentType objects
     * @throws TenderRequiredDocumentTypeNotFoundException - if any TenderRequiredDocumentType option ID are not found
     */
    @Transactional
    public List<TenderRequiredDocumentTypeModel> softDeleteMany(List<String> idList) {
        List<TenderRequiredDocumentTypeModel> tenderRequiredDocumentTypeModelList = tenderRequiredDocumentTypeRepository.findAllById(idList);
        if (tenderRequiredDocumentTypeModelList.isEmpty()) {
            throw new TenderRequiredDocumentTypeNotFoundException("No tender required document types found with provided IDList: " + idList);
        }
        for (TenderRequiredDocumentTypeModel model : tenderRequiredDocumentTypeModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return tenderRequiredDocumentTypeRepository.saveAll(tenderRequiredDocumentTypeModelList);
    }

    /**
     * Hard deletes multiple TenderRequiredDocumentType options by IDs.
     *
     * @param idList     - List of TenderRequiredDocumentType option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        tenderRequiredDocumentTypeRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all TenderRequiredDocumentType options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        tenderRequiredDocumentTypeRepository.deleteAll();
    }
}