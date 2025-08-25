/**
 * REST API controller for managing Procurement Method Options.
 * Handles CRUD operations for Procurement Method option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.procurement_method_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_method_option.dto.ProcurementMethodOptionDto;
import rw.evolve.eprocurement.procurement_method_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_method_option.model.ProcurementMethodOptionModel;
import rw.evolve.eprocurement.procurement_method_option.service.ProcurementMethodOptionService;
import rw.evolve.eprocurement.procurement_method_option.utils.ProcurementMethodOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/procurement_method_option")
@Tag(name = "Procurement Method Option API")
public class ProcurementMethodOptionController {

    private final ProcurementMethodOptionService procurementMethodOptionService;

    private final ModelMapper modelMapper;

    public ProcurementMethodOptionController(
            ProcurementMethodOptionService procurementMethodOptionService,
            ModelMapper modelMapper
    ){
        this.procurementMethodOptionService = procurementMethodOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts ProcurementMethodOptionModel to ProcurementMethodOptionDto.
     * @param model - ProcurementMethodOptionModel to convert
     * @return      - Converted ProcurementMethodOptionDto
     */
    private ProcurementMethodOptionDto convertToDto(ProcurementMethodOptionModel model) {
        return modelMapper.map(model, ProcurementMethodOptionDto.class);
    }

    /**
     * Converts ProcurementMethodOptionDto to ProcurementMethodOptionModel.
     * @param dto - ProcurementMethodOptionDto to convert
     * @return    - Converted ProcurementMethodOptionModel
     */
    private ProcurementMethodOptionModel convertToModel(ProcurementMethodOptionDto dto) {
        return modelMapper.map(dto, ProcurementMethodOptionModel.class);
    }

    /**
     * Creates a single Procurement Method option with a generated ID.
     * @param procurementMethodOptionDto - Procurement Method option data
     * @return                           - Response with success message
     */
    @Operation(summary = "Create a single Procurement Method option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ProcurementMethodOptionDto procurementMethodOptionDto) {
        ProcurementMethodOptionModel procurementMethodOptionModel = convertToModel(procurementMethodOptionDto);
        procurementMethodOptionModel.setId(ProcurementMethodOptionIdGenerator.generateId());
        procurementMethodOptionService.save(procurementMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Procurement Method options with generated IDs.
     * @param procurementMethodOptionDtoList - List of Procurement Method option data
     * @return                               - Response with success message
     */
    @Operation(summary = "Create multiple Procurement Method options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ProcurementMethodOptionDto> procurementMethodOptionDtoList) {
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = new ArrayList<>();
        for (ProcurementMethodOptionDto dto : procurementMethodOptionDtoList) {
            ProcurementMethodOptionModel model = convertToModel(dto);
            model.setId(ProcurementMethodOptionIdGenerator.generateId());
            procurementMethodOptionModels.add(model);
        }
        procurementMethodOptionService.saveMany(procurementMethodOptionModels);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Procurement Method option by ID (excludes soft-deleted).
     * @param id - Procurement Method option ID
     * @return   - Response with Procurement Method option data
     */
    @Operation(summary = "Get a single Procurement Method option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ProcurementMethodOptionModel model = procurementMethodOptionService.readOne(id);
        ProcurementMethodOptionDto procurementMethodOptionDto = convertToDto(model);
        return new ResponseEntity<>(procurementMethodOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Procurement Method options.
     * @return - Response with list of Procurement Method option data
     */
    @Operation(summary = "Get all non-deleted Procurement Method options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionService.readAll();
        List<ProcurementMethodOptionDto> procurementMethodOptionDtoList = new ArrayList<>();
        for (ProcurementMethodOptionModel procurementMethodOptionModel : procurementMethodOptionModels) {
            procurementMethodOptionDtoList.add(convertToDto(procurementMethodOptionModel));
        }
        return new ResponseEntity<>(procurementMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Procurement Method options, including soft-deleted.
     * @return - Response with list of all Procurement Method option data
     */
    @Operation(summary = "Get all Procurement Method options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ProcurementMethodOptionModel> models = procurementMethodOptionService.hardReadAll();
        List<ProcurementMethodOptionDto> procurementMethodOptionDtoList = new ArrayList<>();
        for (ProcurementMethodOptionModel model : models) {
            procurementMethodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Procurement Method options by IDs (excludes soft-deleted).
     * @param idList - List of Procurement Method option IDs
     * @return       - Response with list of Procurement Method option data
     */
    @Operation(summary = "Get multiple Procurement Method options by IDs")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionService.readMany(idList);
        List<ProcurementMethodOptionDto> procurementMethodOptionDtoList = new ArrayList<>();
        for (ProcurementMethodOptionModel model : procurementMethodOptionModels) {
            procurementMethodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement Method option by ID (excludes soft-deleted).
     * @param procurementMethodOptionDto - Updated Procurement Method option data
     * @return                           - Response with updated Procurement Method option data
     */
    @Operation(summary = "Update a single Procurement Method option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ProcurementMethodOptionDto procurementMethodOptionDto) {
        String modelId = procurementMethodOptionDto.getId();
        ProcurementMethodOptionModel savedModel = procurementMethodOptionService.readOne(modelId);
        savedModel.setName(procurementMethodOptionDto.getName());
        savedModel.setDescription(procurementMethodOptionDto.getDescription());
        procurementMethodOptionService.updateOne(savedModel);
        ProcurementMethodOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Procurement Method options (excludes soft-deleted).
     * @param procurementMethodOptionDtoList - List of updated Procurement Method option data
     * @return                               - Response with list of updated Procurement Method option data
     */
    @Operation(summary = "Update multiple Procurement Method options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ProcurementMethodOptionDto> procurementMethodOptionDtoList) {
        List<ProcurementMethodOptionModel> inputModels = new ArrayList<>();
        for (ProcurementMethodOptionDto procurementMethodOptionDto : procurementMethodOptionDtoList) {
            inputModels.add(convertToModel(procurementMethodOptionDto));
        }
        List<ProcurementMethodOptionModel> updatedModels = procurementMethodOptionService.updateMany(inputModels);
        List<ProcurementMethodOptionDto> procurementMethodOptionDtoArrayList = new ArrayList<>();
        for (ProcurementMethodOptionModel model : updatedModels) {
            procurementMethodOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementMethodOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement Method option by ID, including soft-deleted.
     * @param procurementMethodOptionDto - Updated Procurement Method option data
     * @return                           - Response with updated Procurement Method option data
     */
    @Operation(summary = "Update a single Procurement Method option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ProcurementMethodOptionDto procurementMethodOptionDto) {
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionService.hardUpdate(convertToModel(procurementMethodOptionDto));
        ProcurementMethodOptionDto updatedDto = convertToDto(procurementMethodOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Procurement Method options, including soft-deleted.
     * @param procurementMethodOptionDtoList - List of updated Procurement Method option data
     * @return                               - Response with list of updated Procurement Method option data
     */
    @Operation(summary = "Update all Procurement Method options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ProcurementMethodOptionDto> procurementMethodOptionDtoList) {
        List<ProcurementMethodOptionModel> inputModels = new ArrayList<>();
        for (ProcurementMethodOptionDto dto : procurementMethodOptionDtoList) {
            inputModels.add(convertToModel(dto));
        }
        List<ProcurementMethodOptionModel> updatedModels = procurementMethodOptionService.hardUpdateAll(inputModels);
        List<ProcurementMethodOptionDto> procurementMethodOptionDtoArrayList = new ArrayList<>();
        for (ProcurementMethodOptionModel model : updatedModels) {
            procurementMethodOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementMethodOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Procurement Method option by ID.
     * @param id - Procurement Method option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Procurement Method option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ProcurementMethodOptionModel deletedProcurementMethodOptionModel = procurementMethodOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Procurement Method option by ID.
     * @param id - Procurement Method option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Procurement Method option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        procurementMethodOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Procurement Method options by IDs.
     * @param idList - List of Procurement Method option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Procurement Method options by IDs")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ProcurementMethodOptionModel> deletedProcurementMethodOptionModels = procurementMethodOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Procurement Method options by IDs.
     * @param idList - List of Procurement Method option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Procurement Method options by IDs")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        procurementMethodOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Procurement Method options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Procurement Method options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        procurementMethodOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement Method options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}