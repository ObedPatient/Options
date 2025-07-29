/**
 * REST API controller for managing TenderRequiredDocumentType options.
 * Handles CRUD operations for TenderRequiredDocumentType option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.tender_required_document_type.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.tender_required_document_type.dto.ResponseMessageDto;
import rw.evolve.eprocurement.tender_required_document_type.dto.TenderRequiredDocumentTypeDto;
import rw.evolve.eprocurement.tender_required_document_type.model.TenderRequiredDocumentTypeModel;
import rw.evolve.eprocurement.tender_required_document_type.service.TenderRequiredDocumentTypeOptionService;
import rw.evolve.eprocurement.tender_required_document_type.utils.TenderRequiredDocumentTypeIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/tender_required_document_type")
@Tag(name = "Tender Required Document Type API")
public class TenderRequiredDocumentTypeOptionController {

    @Autowired
    private TenderRequiredDocumentTypeOptionService tenderRequiredDocumentTypeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts TenderRequiredDocumentTypeModel to TenderRequiredDocumentTypeDto.
     * @param model - TenderRequiredDocumentTypeModel to convert
     * @return      - Converted TenderRequiredDocumentTypeDto
     */
    private TenderRequiredDocumentTypeDto convertToDto(TenderRequiredDocumentTypeModel model) {
        return modelMapper.map(model, TenderRequiredDocumentTypeDto.class);
    }

    /**
     * Converts TenderRequiredDocumentTypeDto to TenderRequiredDocumentTypeModel.
     * @param tenderRequiredDocumentTypeDto - TenderRequiredDocumentTypeDto to convert
     * @return                             - Converted TenderRequiredDocumentTypeModel
     */
    private TenderRequiredDocumentTypeModel convertToModel(TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto) {
        return modelMapper.map(tenderRequiredDocumentTypeDto, TenderRequiredDocumentTypeModel.class);
    }

    /**
     * Creates a single TenderRequiredDocumentType option with a generated ID.
     * @param tenderRequiredDocumentTypeDto - TenderRequiredDocumentType option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create a single TenderRequiredDocumentType option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto) {
        TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel = convertToModel(tenderRequiredDocumentTypeDto);
        tenderRequiredDocumentTypeModel.setId(TenderRequiredDocumentTypeIdGenerator.generateId());
        tenderRequiredDocumentTypeOptionService.save(tenderRequiredDocumentTypeModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender required document type created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple TenderRequiredDocumentType options with generated IDList.
     * @param tenderRequiredDocumentTypeDtoList - List of TenderRequiredDocumentType option data
     * @return                                 - Response with success message
     */
    @Operation(summary = "Create multiple TenderRequiredDocumentType options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoList) {
        List<TenderRequiredDocumentTypeModel> tenderRequiredDocumentTypeModelList = new ArrayList<>();
        for (TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto : tenderRequiredDocumentTypeDtoList) {
            TenderRequiredDocumentTypeModel model = convertToModel(tenderRequiredDocumentTypeDto);
            model.setId(TenderRequiredDocumentTypeIdGenerator.generateId());
            tenderRequiredDocumentTypeModelList.add(model);
        }
        tenderRequiredDocumentTypeOptionService.saveMany(tenderRequiredDocumentTypeModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender required document types created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a TenderRequiredDocumentType option by ID (excludes soft-deleted).
     * @param id - TenderRequiredDocumentType option ID
     * @return   - Response with TenderRequiredDocumentType option data
     */
    @Operation(summary = "Get a single TenderRequiredDocumentType option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        TenderRequiredDocumentTypeModel model = tenderRequiredDocumentTypeOptionService.readOne(id);
        TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto = convertToDto(model);
        return new ResponseEntity<>(tenderRequiredDocumentTypeDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted TenderRequiredDocumentType options.
     * @return  - Response with list of TenderRequiredDocumentType option data
     */
    @Operation(summary = "Get all available TenderRequiredDocumentType options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<TenderRequiredDocumentTypeModel> tenderRequiredDocumentTypeModelList = tenderRequiredDocumentTypeOptionService.readAll();
        List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoList = new ArrayList<>();
        for (TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel : tenderRequiredDocumentTypeModelList) {
            tenderRequiredDocumentTypeDtoList.add(convertToDto(tenderRequiredDocumentTypeModel));
        }
        return new ResponseEntity<>(tenderRequiredDocumentTypeDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all TenderRequiredDocumentType options, including soft-deleted.
     * @return        - Response with list of all TenderRequiredDocumentType option data
     */
    @Operation(summary = "Get all TenderRequiredDocumentType options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<TenderRequiredDocumentTypeModel> modelList = tenderRequiredDocumentTypeOptionService.hardReadAll();
        List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoList = new ArrayList<>();
        for (TenderRequiredDocumentTypeModel model : modelList) {
            tenderRequiredDocumentTypeDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderRequiredDocumentTypeDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple TenderRequiredDocumentType options by ID (excludes soft-deleted).
     * @param idList - List of TenderRequiredDocumentType option ID
     * @return       - Response with list of TenderRequiredDocumentType option data
     */
    @Operation(summary = "Get multiple TenderRequiredDocumentType options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<TenderRequiredDocumentTypeModel> tenderRequiredDocumentTypeModelList = tenderRequiredDocumentTypeOptionService.readMany(idList);
        List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoList = new ArrayList<>();
        for (TenderRequiredDocumentTypeModel model : tenderRequiredDocumentTypeModelList) {
            tenderRequiredDocumentTypeDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderRequiredDocumentTypeDtoList, HttpStatus.OK);
    }

    /**
     * Updates a TenderRequiredDocumentType option by ID (excludes soft-deleted).
     *
     * @param tenderRequiredDocumentTypeDto - Updated TenderRequiredDocumentType option data
     * @return                             - Response with updated TenderRequiredDocumentType option data
     */
    @Operation(summary = "Update a single TenderRequiredDocumentType option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto){
        String modelId = tenderRequiredDocumentTypeDto.getId();
        TenderRequiredDocumentTypeModel savedModel = tenderRequiredDocumentTypeOptionService.readOne(modelId);
        savedModel.setName(tenderRequiredDocumentTypeDto.getName());
        savedModel.setDescription(tenderRequiredDocumentTypeDto.getDescription());
        tenderRequiredDocumentTypeOptionService.updateOne(savedModel);
        TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(tenderRequiredDocumentTypeDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple TenderRequiredDocumentType options (excludes soft-deleted).
     * @param tenderRequiredDocumentTypeDtoList - a List of updated TenderRequiredDocumentType option data
     * @return                                 - Response with list of updated TenderRequiredDocumentType option data
     */
    @Operation(summary = "Update multiple TenderRequiredDocumentType options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoList) {
        List<TenderRequiredDocumentTypeModel> inputModelList = new ArrayList<>();
        for (TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto : tenderRequiredDocumentTypeDtoList) {
            inputModelList.add(convertToModel(tenderRequiredDocumentTypeDto));
        }
        List<TenderRequiredDocumentTypeModel> updatedModelList = tenderRequiredDocumentTypeOptionService.updateMany(inputModelList);
        List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoArrayList = new ArrayList<>();
        for (TenderRequiredDocumentTypeModel model : updatedModelList) {
            tenderRequiredDocumentTypeDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderRequiredDocumentTypeDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a TenderRequiredDocumentType option by ID, including soft-deleted.
     *
     * @param tenderRequiredDocumentTypeDto - Updated TenderRequiredDocumentType option data
     * @return                             - Response with updated TenderRequiredDocumentType option data
     */
    @Operation(summary = "Update a single TenderRequiredDocumentType option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto) {
        TenderRequiredDocumentTypeModel tenderRequiredDocumentTypeModel = tenderRequiredDocumentTypeOptionService.hardUpdate(convertToModel(tenderRequiredDocumentTypeDto));
        TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto1 = convertToDto(tenderRequiredDocumentTypeModel);
        return new ResponseEntity<>(tenderRequiredDocumentTypeDto1, HttpStatus.OK);
    }

    /**
     * Updates all TenderRequiredDocumentType options, including soft-deleted.
     * @param tenderRequiredDocumentTypeDtoList - List of updated TenderRequiredDocumentType option data
     * @return                                 - Response with list of updated TenderRequiredDocumentType option data
     */
    @Operation(summary = "Update all TenderRequiredDocumentType options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<TenderRequiredDocumentTypeDto> tenderRequiredDocumentTypeDtoList) {
        List<TenderRequiredDocumentTypeModel> inputModelList = new ArrayList<>();
        for (TenderRequiredDocumentTypeDto tenderRequiredDocumentTypeDto : tenderRequiredDocumentTypeDtoList) {
            inputModelList.add(convertToModel(tenderRequiredDocumentTypeDto));
        }
        List<TenderRequiredDocumentTypeModel> updatedModelList = tenderRequiredDocumentTypeOptionService.hardUpdateAll(inputModelList);
        List<TenderRequiredDocumentTypeDto> updatedTenderRequiredDocumentTypeDtoList = new ArrayList<>();
        for (TenderRequiredDocumentTypeModel model : updatedModelList) {
            updatedTenderRequiredDocumentTypeDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedTenderRequiredDocumentTypeDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a TenderRequiredDocumentType option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single TenderRequiredDocumentType option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        TenderRequiredDocumentTypeModel deleteTenderRequiredDocumentTypeModel = tenderRequiredDocumentTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender required document type soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a TenderRequiredDocumentType option by ID.
     * @param id       - TenderRequiredDocumentType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single TenderRequiredDocumentType option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        tenderRequiredDocumentTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender required document type hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple TenderRequiredDocumentType options by ID.
     * @param idList    - List of TenderRequiredDocumentType option IDList
     * @return          - Response with list of soft-deleted TenderRequiredDocumentType option data
     */
    @Operation(summary = "Soft delete multiple TenderRequiredDocumentType options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<TenderRequiredDocumentTypeModel> deletedTenderRequiredDocumentTypeModelList = tenderRequiredDocumentTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender required document types soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple TenderRequiredDocumentType options by ID.
     * @param idList   - List of TenderRequiredDocumentType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple TenderRequiredDocumentType options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        tenderRequiredDocumentTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Tender required document types hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all TenderRequiredDocumentType options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all TenderRequiredDocumentType options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        tenderRequiredDocumentTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Tender required document types hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}