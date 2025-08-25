/**
 * REST API controller for managing Procurement Type Options.
 * Handles CRUD operations for Procurement Type Option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.procurement_type_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_type_option.dto.ProcurementTypeOptionDto;
import rw.evolve.eprocurement.procurement_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_type_option.model.ProcurementTypeOptionModel;
import rw.evolve.eprocurement.procurement_type_option.service.ProcurementTypeOptionService;
import rw.evolve.eprocurement.procurement_type_option.utils.ProcurementTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/procurement_type_option")
@Tag(name = "Procurement Type Option API")
public class ProcurementTypeOptionController {

    private final ProcurementTypeOptionService procurementTypeOptionService;

    private final ModelMapper modelMapper;

    public ProcurementTypeOptionController(
            ProcurementTypeOptionService procurementTypeOptionService,
            ModelMapper modelMapper
    ){
        this.procurementTypeOptionService = procurementTypeOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts ProcurementTypeOptionModel to ProcurementTypeOptionDto.
     * @param model - ProcurementTypeOptionModel to convert
     * @return      - Converted ProcurementTypeOptionDto
     */
    private ProcurementTypeOptionDto convertToDto(ProcurementTypeOptionModel model) {
        return modelMapper.map(model, ProcurementTypeOptionDto.class);
    }

    /**
     * Converts ProcurementTypeOptionDto to ProcurementTypeOptionModel.
     * @param dto - ProcurementTypeOptionDto to convert
     * @return    - Converted ProcurementTypeOptionModel
     */
    private ProcurementTypeOptionModel convertToModel(ProcurementTypeOptionDto dto) {
        return modelMapper.map(dto, ProcurementTypeOptionModel.class);
    }

    /**
     * Creates a single Procurement type option with a generated ID.
     * @param procurementTypeOptionDto - Procurement type option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create a single Procurement type option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ProcurementTypeOptionDto procurementTypeOptionDto) {
        ProcurementTypeOptionModel procurementTypeOptionModel = convertToModel(procurementTypeOptionDto);
        procurementTypeOptionModel.setId(ProcurementTypeOptionIdGenerator.generateId());
        procurementTypeOptionService.save(procurementTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Procurement type options with generated IDs.
     * @param procurementTypeOptionDtoList - List of Procurement type option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create multiple Procurement type options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ProcurementTypeOptionDto> procurementTypeOptionDtoList) {
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = new ArrayList<>();
        for (ProcurementTypeOptionDto dto : procurementTypeOptionDtoList) {
            ProcurementTypeOptionModel model = convertToModel(dto);
            model.setId(ProcurementTypeOptionIdGenerator.generateId());
            procurementTypeOptionModels.add(model);
        }
        procurementTypeOptionService.saveMany(procurementTypeOptionModels);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Procurement type option by ID (excludes soft-deleted).
     * @param id - Procurement type option ID
     * @return   - Response with Procurement type option data
     */
    @Operation(summary = "Get a single Procurement type option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ProcurementTypeOptionModel model = procurementTypeOptionService.readOne(id);
        ProcurementTypeOptionDto procurementTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(procurementTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Procurement type options.
     * @return - Response with list of Procurement type option data
     */
    @Operation(summary = "Get all non-deleted Procurement type options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionService.readAll();
        List<ProcurementTypeOptionDto> procurementTypeOptionDtoList = new ArrayList<>();
        for (ProcurementTypeOptionModel procurementTypeOptionModel : procurementTypeOptionModels) {
            procurementTypeOptionDtoList.add(convertToDto(procurementTypeOptionModel));
        }
        return new ResponseEntity<>(procurementTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Procurement type options, including soft-deleted.
     * @return - Response with list of all Procurement type option data
     */
    @Operation(summary = "Get all Procurement type options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ProcurementTypeOptionModel> models = procurementTypeOptionService.hardReadAll();
        List<ProcurementTypeOptionDto> procurementTypeOptionDtoList = new ArrayList<>();
        for (ProcurementTypeOptionModel model : models) {
            procurementTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Procurement type options by IDs (excludes soft-deleted).
     * @param idList - List of Procurement type option IDs
     * @return       - Response with list of Procurement type option data
     */
    @Operation(summary = "Get multiple Procurement type options by IDs")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionService.readMany(idList);
        List<ProcurementTypeOptionDto> procurementTypeOptionDtoList = new ArrayList<>();
        for (ProcurementTypeOptionModel model : procurementTypeOptionModels) {
            procurementTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement type option by ID (excludes soft-deleted).
     * @param procurementTypeOptionDto - Updated Procurement type option data
     * @return                         - Response with updated Procurement type option data
     */
    @Operation(summary = "Update a single Procurement type option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ProcurementTypeOptionDto procurementTypeOptionDto) {
        String modelId = procurementTypeOptionDto.getId();
        ProcurementTypeOptionModel savedModel = procurementTypeOptionService.readOne(modelId);
        savedModel.setName(procurementTypeOptionDto.getName());
        savedModel.setDescription(procurementTypeOptionDto.getDescription());
        procurementTypeOptionService.updateOne(savedModel);
        ProcurementTypeOptionDto procurementTypeOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(procurementTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple Procurement type options (excludes soft-deleted).
     * @param procurementTypeOptionDtoList - List of updated Procurement type option data
     * @return                             - Response with list of updated Procurement type option data
     */
    @Operation(summary = "Update multiple Procurement type options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ProcurementTypeOptionDto> procurementTypeOptionDtoList) {
        List<ProcurementTypeOptionModel> inputModels = new ArrayList<>();
        for (ProcurementTypeOptionDto procurementTypeOptionDto : procurementTypeOptionDtoList) {
            inputModels.add(convertToModel(procurementTypeOptionDto));
        }
        List<ProcurementTypeOptionModel> updatedModels = procurementTypeOptionService.updateMany(inputModels);
        List<ProcurementTypeOptionDto> procurementTypeOptionDtoArrayList = new ArrayList<>();
        for (ProcurementTypeOptionModel model : updatedModels) {
            procurementTypeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementTypeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement type option by ID, including soft-deleted.
     * @param procurementTypeOptionDto - Updated Procurement type option data
     * @return                         - Response with updated Procurement type option data
     */
    @Operation(summary = "Update a single Procurement type option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ProcurementTypeOptionDto procurementTypeOptionDto) {
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionService.hardUpdate(convertToModel(procurementTypeOptionDto));
        ProcurementTypeOptionDto procurementTypeOptionDto1 = convertToDto(procurementTypeOptionModel);
        return new ResponseEntity<>(procurementTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all Procurement type options, including soft-deleted.
     * @param procurementTypeOptionDtoList - List of updated Procurement type option data
     * @return                             - Response with list of updated Procurement type option data
     */
    @Operation(summary = "Update all Procurement type options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ProcurementTypeOptionDto> procurementTypeOptionDtoList) {
        List<ProcurementTypeOptionModel> inputModels = new ArrayList<>();
        for (ProcurementTypeOptionDto dto : procurementTypeOptionDtoList) {
            inputModels.add(convertToModel(dto));
        }
        List<ProcurementTypeOptionModel> updatedModels = procurementTypeOptionService.hardUpdateAll(inputModels);
        List<ProcurementTypeOptionDto> procurementTypeOptionDtoArrayList = new ArrayList<>();
        for (ProcurementTypeOptionModel model : updatedModels) {
            procurementTypeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementTypeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Procurement type option by ID.
     * @param id  - Procurement type option ID
     * @return    - Response with success message
     */
    @Operation(summary = "Soft delete a single Procurement type option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ProcurementTypeOptionModel deletedProcurementTypeOptionModel = procurementTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Procurement type option by ID.
     * @param id - Procurement type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Procurement type option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        procurementTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Procurement type options by IDs.
     * @param idList - List of Procurement type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Procurement type options by IDs")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ProcurementTypeOptionModel> deletedProcurementTypeOptionModels = procurementTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Procurement type options by IDs.
     * @param idList - List of Procurement type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Procurement type options by IDs")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("id_list") List<String> idList) {
        procurementTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Procurement type options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Procurement type options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        procurementTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}