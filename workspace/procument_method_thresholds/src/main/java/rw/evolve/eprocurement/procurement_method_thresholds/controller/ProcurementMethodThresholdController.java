package rw.evolve.eprocurement.procurement_method_thresholds.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_method_thresholds.dto.ProcurementMethodThresholdDto;
import rw.evolve.eprocurement.procurement_method_thresholds.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_method_thresholds.model.ProcurementMethodThresholdModel;
import rw.evolve.eprocurement.procurement_method_thresholds.service.ProcurementMethodThresholdService;
import rw.evolve.eprocurement.procurement_method_thresholds.utils.ProcurementMethodThresholdIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for managing Procurement Method Threshold options.
 * Handles CRUD operations for Procurement Method Threshold data with soft and hard delete capabilities.
 */
@RestController
@RequestMapping("api/procurement_method_threshold")
@Tag(name = "Procurement Method Threshold API")
public class ProcurementMethodThresholdController {

    private final ProcurementMethodThresholdService procurementMethodThresholdService;

    private final ModelMapper modelMapper;

    public ProcurementMethodThresholdController(
            ProcurementMethodThresholdService procurementMethodThresholdService,
            ModelMapper modelMapper
    ){
        this.procurementMethodThresholdService = procurementMethodThresholdService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts ProcurementMethodThresholdModel to ProcurementMethodThresholdDto.
     * @param model - ProcurementMethodThresholdModel to convert
     * @return      - Converted ProcurementMethodThresholdDto
     */
    private ProcurementMethodThresholdDto convertToDto(ProcurementMethodThresholdModel model) {
        return modelMapper.map(model, ProcurementMethodThresholdDto.class);
    }

    /**
     * Converts ProcurementMethodThresholdDto to ProcurementMethodThresholdModel.
     * @param procurementMethodThresholdDto - ProcurementMethodThresholdDto to convert
     * @return                              - Converted ProcurementMethodThresholdModel
     */
    private ProcurementMethodThresholdModel convertToModel(ProcurementMethodThresholdDto procurementMethodThresholdDto) {
        return modelMapper.map(procurementMethodThresholdDto, ProcurementMethodThresholdModel.class);
    }

    /**
     * Creates a single Procurement Method Threshold option with a generated ID.
     * @param procurementMethodThresholdDto - Procurement Method Threshold data
     * @return                              - Response with success message
     */
    @Operation(summary = "Create a single Procurement Method Threshold option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ProcurementMethodThresholdDto procurementMethodThresholdDto) {
        ProcurementMethodThresholdModel procurementMethodThresholdModel = convertToModel(procurementMethodThresholdDto);
        procurementMethodThresholdModel.setId(ProcurementMethodThresholdIdGenerator.generateId());
        procurementMethodThresholdService.save(procurementMethodThresholdModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement method threshold created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Procurement Method Threshold options with generated IDs.
     * @param procurementMethodThresholdDtoList - List of Procurement Method Threshold data
     * @return                                  - Response with success message
     */
    @Operation(summary = "Create multiple Procurement Method Threshold options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ProcurementMethodThresholdDto> procurementMethodThresholdDtoList) {
        List<ProcurementMethodThresholdModel> procurementMethodThresholdModelList = new ArrayList<>();
        for (ProcurementMethodThresholdDto procurementMethodThresholdDto : procurementMethodThresholdDtoList) {
            ProcurementMethodThresholdModel model = convertToModel(procurementMethodThresholdDto);
            model.setId(ProcurementMethodThresholdIdGenerator.generateId());
            procurementMethodThresholdModelList.add(model);
        }
        procurementMethodThresholdService.saveMany(procurementMethodThresholdModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement method thresholds created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Procurement Method Threshold option by ID (excludes soft-deleted).
     * @param id - Procurement Method Threshold ID
     * @return   - Response with Procurement Method Threshold data
     */
    @Operation(summary = "Get a single Procurement Method Threshold option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ProcurementMethodThresholdModel model = procurementMethodThresholdService.readOne(id);
        ProcurementMethodThresholdDto procurementMethodThresholdDto = convertToDto(model);
        return new ResponseEntity<>(procurementMethodThresholdDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Procurement Method Threshold options.
     * @return - Response with list of Procurement Method Threshold data
     */
    @Operation(summary = "Get all available Procurement Method Threshold options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ProcurementMethodThresholdModel> procurementMethodThresholdModelList = procurementMethodThresholdService.readAll();
        List<ProcurementMethodThresholdDto> procurementMethodThresholdDtoList = new ArrayList<>();
        for (ProcurementMethodThresholdModel procurementMethodThresholdModel : procurementMethodThresholdModelList) {
            procurementMethodThresholdDtoList.add(convertToDto(procurementMethodThresholdModel));
        }
        return new ResponseEntity<>(procurementMethodThresholdDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Procurement Method Threshold options, including soft-deleted.
     * @return - Response with list of all Procurement Method Threshold data
     */
    @Operation(summary = "Get all Procurement Method Threshold options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ProcurementMethodThresholdModel> modelList = procurementMethodThresholdService.hardReadAll();
        List<ProcurementMethodThresholdDto> procurementMethodThresholdDtoList = new ArrayList<>();
        for (ProcurementMethodThresholdModel model : modelList) {
            procurementMethodThresholdDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementMethodThresholdDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Procurement Method Threshold options by ID (excludes soft-deleted).
     * @param idList - List of Procurement Method Threshold IDs
     * @return       - Response with list of Procurement Method Threshold data
     */
    @Operation(summary = "Get multiple Procurement Method Threshold options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ProcurementMethodThresholdModel> procurementMethodThresholdModelList = procurementMethodThresholdService.readMany(idList);
        List<ProcurementMethodThresholdDto> procurementMethodThresholdDtoList = new ArrayList<>();
        for (ProcurementMethodThresholdModel model : procurementMethodThresholdModelList) {
            procurementMethodThresholdDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementMethodThresholdDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement Method Threshold option by ID (excludes soft-deleted).
     * @param procurementMethodThresholdDto - Updated Procurement Method Threshold data
     * @return                              - Response with updated Procurement Method Threshold data
     */
    @Operation(summary = "Update a single Procurement Method Threshold option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ProcurementMethodThresholdDto procurementMethodThresholdDto) {
        String modelId = procurementMethodThresholdDto.getId();
        ProcurementMethodThresholdModel savedModel = procurementMethodThresholdService.readOne(modelId);
        savedModel.setName(procurementMethodThresholdDto.getName());
        savedModel.setDescription(procurementMethodThresholdDto.getDescription());
        procurementMethodThresholdService.updateOne(savedModel);
        ProcurementMethodThresholdDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Procurement Method Threshold options (excludes soft-deleted).
     * @param procurementMethodThresholdDtoList - List of updated Procurement Method Threshold data
     * @return                                  - Response with list of updated Procurement Method Threshold data
     */
    @Operation(summary = "Update multiple Procurement Method Threshold options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ProcurementMethodThresholdDto> procurementMethodThresholdDtoList) {
        List<ProcurementMethodThresholdModel> inputModelList = new ArrayList<>();
        for (ProcurementMethodThresholdDto procurementMethodThresholdDto : procurementMethodThresholdDtoList) {
            inputModelList.add(convertToModel(procurementMethodThresholdDto));
        }
        List<ProcurementMethodThresholdModel> updatedModelList = procurementMethodThresholdService.updateMany(inputModelList);
        List<ProcurementMethodThresholdDto> updatedDtoList = new ArrayList<>();
        for (ProcurementMethodThresholdModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement Method Threshold option by ID, including soft-deleted.
     * @param procurementMethodThresholdDto - Updated Procurement Method Threshold data
     * @return                              - Response with updated Procurement Method Threshold data
     */
    @Operation(summary = "Update a single Procurement Method Threshold option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ProcurementMethodThresholdDto procurementMethodThresholdDto) {
        ProcurementMethodThresholdModel procurementMethodThresholdModel = procurementMethodThresholdService.hardUpdate(convertToModel(procurementMethodThresholdDto));
        ProcurementMethodThresholdDto updatedDto = convertToDto(procurementMethodThresholdModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Procurement Method Threshold options, including soft-deleted.
     * @param procurementMethodThresholdDtoList - List of updated Procurement Method Threshold data
     * @return                                  - Response with list of updated Procurement Method Threshold data
     */
    @Operation(summary = "Update all Procurement Method Threshold options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ProcurementMethodThresholdDto> procurementMethodThresholdDtoList) {
        List<ProcurementMethodThresholdModel> inputModelList = new ArrayList<>();
        for (ProcurementMethodThresholdDto procurementMethodThresholdDto : procurementMethodThresholdDtoList) {
            inputModelList.add(convertToModel(procurementMethodThresholdDto));
        }
        List<ProcurementMethodThresholdModel> updatedModelList = procurementMethodThresholdService.hardUpdateAll(inputModelList);
        List<ProcurementMethodThresholdDto> updatedDtoList = new ArrayList<>();
        for (ProcurementMethodThresholdModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Procurement Method Threshold option by ID.
     * @param id - Procurement Method Threshold ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Procurement Method Threshold option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ProcurementMethodThresholdModel deletedModel = procurementMethodThresholdService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement method threshold soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Procurement Method Threshold option by ID.
     * @param id - Procurement Method Threshold ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Procurement Method Threshold option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        procurementMethodThresholdService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement method threshold hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Procurement Method Threshold options by ID.
     * @param idList - List of Procurement Method Threshold IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Procurement Method Threshold options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ProcurementMethodThresholdModel> deletedModelList = procurementMethodThresholdService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement method thresholds soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Procurement Method Threshold options by ID.
     * @param idList - List of Procurement Method Threshold IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Procurement Method Threshold options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        procurementMethodThresholdService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement method thresholds hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Procurement Method Threshold options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Procurement Method Threshold options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        procurementMethodThresholdService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement method thresholds hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}