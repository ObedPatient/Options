/**
 * REST API controller for managing Procurement Progress options.
 * Handles CRUD operations for Procurement Progress option data with soft and hard delete capabilities.
 */
        package rw.evolve.eprocurement.procurement_progress_status_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_progress_status_option.dto.ProcurementProgressOptionDto;
import rw.evolve.eprocurement.procurement_progress_status_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_progress_status_option.model.ProcurementProgressOptionModel;
import rw.evolve.eprocurement.procurement_progress_status_option.service.ProcurementProgressOptionService;
import rw.evolve.eprocurement.procurement_progress_status_option.utils.ProcurementProgressOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/procurement_progress_option")
@Tag(name = "Procurement Progress Option API")
public class ProcurementProgressOptionController {

    @Autowired
    private ProcurementProgressOptionService procurementProgressOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts ProcurementProgressOptionModel to ProcurementProgressOptionDto.
     * @param model - ProcurementProgressOptionModel to convert
     * @return      - Converted ProcurementProgressOptionDto
     */
    private ProcurementProgressOptionDto convertToDto(ProcurementProgressOptionModel model) {
        return modelMapper.map(model, ProcurementProgressOptionDto.class);
    }

    /**
     * Converts ProcurementProgressOptionDto to ProcurementProgressOptionModel
     * @param procurementProgressOptionDto - ProcurementProgressOptionDto to convert
     * @return                            - Converted ProcurementProgressOptionModel
     */
    private ProcurementProgressOptionModel convertToModel(ProcurementProgressOptionDto procurementProgressOptionDto) {
        return modelMapper.map(procurementProgressOptionDto, ProcurementProgressOptionModel.class);
    }

    /**
     * Creates a single Procurement Progress option with a generated ID.
     * @param procurementProgressOptionDto - Procurement Progress option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create a single Procurement Progress option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ProcurementProgressOptionDto procurementProgressOptionDto) {
        ProcurementProgressOptionModel procurementProgressOptionModel = convertToModel(procurementProgressOptionDto);
        procurementProgressOptionModel.setId(ProcurementProgressOptionIdGenerator.generateId());
        procurementProgressOptionService.save(procurementProgressOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement progress option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Procurement Progress options with generated IDs.
     * @param procurementProgressOptionDtoList - List of Procurement Progress option data
     * @return                                 - Response with success message
     */
    @Operation(summary = "Create multiple Procurement Progress options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ProcurementProgressOptionDto> procurementProgressOptionDtoList) {
        List<ProcurementProgressOptionModel> procurementProgressOptionModelList = new ArrayList<>();
        for (ProcurementProgressOptionDto procurementProgressOptionDto : procurementProgressOptionDtoList) {
            ProcurementProgressOptionModel model = convertToModel(procurementProgressOptionDto);
            model.setId(ProcurementProgressOptionIdGenerator.generateId());
            procurementProgressOptionModelList.add(model);
        }
        procurementProgressOptionService.saveMany(procurementProgressOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement progress options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Procurement Progress option by ID (excludes soft-deleted).
     * @param id - Procurement Progress option ID
     * @return   - Response with Procurement Progress option data
     */
    @Operation(summary = "Get a single Procurement Progress option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ProcurementProgressOptionModel model = procurementProgressOptionService.readOne(id);
        ProcurementProgressOptionDto procurementProgressOptionDto = convertToDto(model);
        return new ResponseEntity<>(procurementProgressOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Procurement Progress options.
     * @return - Response with list of Procurement Progress option data
     */
    @Operation(summary = "Get all available Procurement Progress options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ProcurementProgressOptionModel> procurementProgressOptionModelList = procurementProgressOptionService.readAll();
        List<ProcurementProgressOptionDto> procurementProgressOptionDtoList = new ArrayList<>();
        for (ProcurementProgressOptionModel procurementProgressOptionModel : procurementProgressOptionModelList) {
            procurementProgressOptionDtoList.add(convertToDto(procurementProgressOptionModel));
        }
        return new ResponseEntity<>(procurementProgressOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Procurement Progress options, including soft-deleted.
     * @return - Response with list of all Procurement Progress option data
     */
    @Operation(summary = "Get all Procurement Progress options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ProcurementProgressOptionModel> modelList = procurementProgressOptionService.hardReadAll();
        List<ProcurementProgressOptionDto> procurementProgressOptionDtoList = new ArrayList<>();
        for (ProcurementProgressOptionModel model : modelList) {
            procurementProgressOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementProgressOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Procurement Progress options by ID (excludes soft-deleted).
     * @param idList - List of Procurement Progress option IDs
     * @return       - Response with list of Procurement Progress option data
     */
    @Operation(summary = "Get multiple Procurement Progress options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ProcurementProgressOptionModel> procurementProgressOptionModelList = procurementProgressOptionService.readMany(idList);
        List<ProcurementProgressOptionDto> procurementProgressOptionDtoList = new ArrayList<>();
        for (ProcurementProgressOptionModel model : procurementProgressOptionModelList) {
            procurementProgressOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementProgressOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement Progress option by ID (excludes soft-deleted).
     * @param procurementProgressOptionDto - Updated Procurement Progress option data
     * @return                             - Response with updated Procurement Progress option data
     */
    @Operation(summary = "Update a single Procurement Progress option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ProcurementProgressOptionDto procurementProgressOptionDto) {
        String modelId = procurementProgressOptionDto.getId();
        ProcurementProgressOptionModel savedModel = procurementProgressOptionService.readOne(modelId);
        savedModel.setName(procurementProgressOptionDto.getName());
        savedModel.setDescription(procurementProgressOptionDto.getDescription());
        procurementProgressOptionService.updateOne(savedModel);
        ProcurementProgressOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Procurement Progress options (excludes soft-deleted).
     * @param procurementProgressOptionDtoList - List of updated Procurement Progress option data
     * @return                                 - Response with list of updated Procurement Progress option data
     */
    @Operation(summary = "Update multiple Procurement Progress options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ProcurementProgressOptionDto> procurementProgressOptionDtoList) {
        List<ProcurementProgressOptionModel> inputModelList = new ArrayList<>();
        for (ProcurementProgressOptionDto procurementProgressOptionDto : procurementProgressOptionDtoList) {
            inputModelList.add(convertToModel(procurementProgressOptionDto));
        }
        List<ProcurementProgressOptionModel> updatedModelList = procurementProgressOptionService.updateMany(inputModelList);
        List<ProcurementProgressOptionDto> updatedDtoList = new ArrayList<>();
        for (ProcurementProgressOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Procurement Progress option by ID, including soft-deleted.
     * @param procurementProgressOptionDto - Updated Procurement Progress option data
     * @return                             - Response with updated Procurement Progress option data
     */
    @Operation(summary = "Update a single Procurement Progress option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ProcurementProgressOptionDto procurementProgressOptionDto) {
        ProcurementProgressOptionModel procurementProgressOptionModel = procurementProgressOptionService.hardUpdate(convertToModel(procurementProgressOptionDto));
        ProcurementProgressOptionDto updatedDto = convertToDto(procurementProgressOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Procurement Progress options, including soft-deleted.
     * @param procurementProgressOptionDtoList - List of updated Procurement Progress option data
     * @return                                 - Response with list of updated Procurement Progress option data
     */
    @Operation(summary = "Update all Procurement Progress options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ProcurementProgressOptionDto> procurementProgressOptionDtoList) {
        List<ProcurementProgressOptionModel> inputModelList = new ArrayList<>();
        for (ProcurementProgressOptionDto procurementProgressOptionDto : procurementProgressOptionDtoList) {
            inputModelList.add(convertToModel(procurementProgressOptionDto));
        }
        List<ProcurementProgressOptionModel> updatedModelList = procurementProgressOptionService.hardUpdateAll(inputModelList);
        List<ProcurementProgressOptionDto> updatedDtoList = new ArrayList<>();
        for (ProcurementProgressOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Procurement Progress option by ID.
     * @param id - Procurement Progress option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Procurement Progress option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ProcurementProgressOptionModel deletedModel = procurementProgressOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement progress option soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Procurement Progress option by ID.
     * @param id - Procurement Progress option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Procurement Progress option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        procurementProgressOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Progress option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Procurement Progress options by ID.
     * @param idList - List of Procurement Progress option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Procurement Progress options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ProcurementProgressOptionModel> deletedModelList = procurementProgressOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Progress options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Procurement Progress options by ID.
     * @param idList - List of Procurement Progress option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Procurement Progress options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        procurementProgressOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement progress options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Procurement Progress options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Procurement Progress options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        procurementProgressOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement progress options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}