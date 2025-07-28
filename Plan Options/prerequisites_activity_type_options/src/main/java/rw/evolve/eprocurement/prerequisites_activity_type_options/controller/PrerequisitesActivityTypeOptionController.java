/**
 * REST API controller for managing Prerequisites Activity Type options.
 * Handles CRUD operations for Prerequisites Activity Type option data with soft and hard delete capabilities.
 */
        package rw.evolve.eprocurement.prerequisites_activity_type_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.prerequisites_activity_type_options.dto.PrerequisitesActivityTypeOptionDto;
import rw.evolve.eprocurement.prerequisites_activity_type_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.PrerequisitesActivityTypeOptionModel;
import rw.evolve.eprocurement.prerequisites_activity_type_options.service.PrerequisitesActivityTypeOptionService;
import rw.evolve.eprocurement.prerequisites_activity_type_options.utils.PrerequisiteActivityTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/prerequisites_activity_type_option")
@Tag(name = "Prerequisites Activity Type Option API")
public class PrerequisitesActivityTypeOptionController {

    @Autowired
    private PrerequisitesActivityTypeOptionService prerequisitesActivityTypeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts PrerequisitesActivityTypeOptionModel to PrerequisitesActivityTypeOptionDto.
     * @param model - PrerequisitesActivityTypeOptionModel to convert
     * @return      - Converted PrerequisitesActivityTypeOptionDto
     */
    private PrerequisitesActivityTypeOptionDto convertToDto(PrerequisitesActivityTypeOptionModel model) {
        return modelMapper.map(model, PrerequisitesActivityTypeOptionDto.class);
    }

    /**
     * Converts PrerequisitesActivityTypeOptionDto to PrerequisitesActivityTypeOptionModel, skipping ID mapping.
     * @param prerequisitesActivityTypeOptionDto - PrerequisitesActivityTypeOptionDto to convert
     * @return                                   - Converted PrerequisitesActivityTypeOptionModel
     */
    private PrerequisitesActivityTypeOptionModel convertToModel(PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto) {
        modelMapper.typeMap(PrerequisitesActivityTypeOptionDto.class, PrerequisitesActivityTypeOptionModel.class)
                .addMappings(mapper -> mapper.skip(PrerequisitesActivityTypeOptionModel::setId));
        return modelMapper.map(prerequisitesActivityTypeOptionDto, PrerequisitesActivityTypeOptionModel.class);
    }

    /**
     * Creates a single Prerequisites Activity Type option.
     * @param prerequisitesActivityTypeOptionDto - Prerequisites Activity Type option data
     * @return                                   - Response with success message
     */
    @Operation(summary = "Create a single Prerequisites Activity Type option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto) {
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = convertToModel(prerequisitesActivityTypeOptionDto);
        prerequisitesActivityTypeOptionModel.setId(PrerequisiteActivityTypeOptionIdGenerator.generateId());
        prerequisitesActivityTypeOptionService.save(prerequisitesActivityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites activity type option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Prerequisites Activity Type options.
     * @param prerequisitesActivityTypeOptionDtoList - List of Prerequisites Activity Type option data
     * @return                                       - Response with success message
     */
    @Operation(summary = "Create multiple Prerequisites Activity Type options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<PrerequisitesActivityTypeOptionDto> prerequisitesActivityTypeOptionDtoList) {
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModelList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto : prerequisitesActivityTypeOptionDtoList) {
            PrerequisitesActivityTypeOptionModel model = convertToModel(prerequisitesActivityTypeOptionDto);
            model.setId(PrerequisiteActivityTypeOptionIdGenerator.generateId());
            prerequisitesActivityTypeOptionModelList.add(model);
        }
        prerequisitesActivityTypeOptionService.saveMany(prerequisitesActivityTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites activity type options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Prerequisites Activity Type option by ID (excludes soft-deleted).
     * @param id - Prerequisites Activity Type option ID
     * @return   - Response with Prerequisites Activity Type option data
     */
    @Operation(summary = "Get a single Prerequisites Activity Type option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        PrerequisitesActivityTypeOptionModel model = prerequisitesActivityTypeOptionService.readOne(id);
        PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(prerequisitesActivityTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Prerequisites Activity Type options.
     * @return - Response with list of Prerequisites Activity Type option data
     */
    @Operation(summary = "Get all available Prerequisites Activity Type options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModelList = prerequisitesActivityTypeOptionService.readAll();
        List<PrerequisitesActivityTypeOptionDto> prerequisitesActivityTypeOptionDtoList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel : prerequisitesActivityTypeOptionModelList) {
            prerequisitesActivityTypeOptionDtoList.add(convertToDto(prerequisitesActivityTypeOptionModel));
        }
        return new ResponseEntity<>(prerequisitesActivityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Prerequisites Activity Type options, including soft-deleted.
     * @return - Response with list of all Prerequisites Activity Type option data
     */
    @Operation(summary = "Get all Prerequisites Activity Type options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<PrerequisitesActivityTypeOptionModel> modelList = prerequisitesActivityTypeOptionService.hardReadAll();
        List<PrerequisitesActivityTypeOptionDto> prerequisitesActivityTypeOptionDtoList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model : modelList) {
            prerequisitesActivityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(prerequisitesActivityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Prerequisites Activity Type options by ID (excludes soft-deleted).
     * @param idList - List of Prerequisites Activity Type option IDs
     * @return       - Response with list of Prerequisites Activity Type option data
     */
    @Operation(summary = "Get multiple Prerequisites Activity Type options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModelList = prerequisitesActivityTypeOptionService.readMany(idList);
        List<PrerequisitesActivityTypeOptionDto> prerequisitesActivityTypeOptionDtoList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model : prerequisitesActivityTypeOptionModelList) {
            prerequisitesActivityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(prerequisitesActivityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Prerequisites Activity Type option by ID (excludes soft-deleted).
     * @param prerequisitesActivityTypeOptionDto - Updated Prerequisites Activity Type option data
     * @return                                   - Response with updated Prerequisites Activity Type option data
     */
    @Operation(summary = "Update a single Prerequisites Activity Type option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto) {
        String modelId = prerequisitesActivityTypeOptionDto.getId();
        PrerequisitesActivityTypeOptionModel savedModel = prerequisitesActivityTypeOptionService.readOne(modelId);
        savedModel.setName(prerequisitesActivityTypeOptionDto.getName());
        savedModel.setDescription(prerequisitesActivityTypeOptionDto.getDescription());
        prerequisitesActivityTypeOptionService.updateOne(savedModel);
        PrerequisitesActivityTypeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Prerequisites Activity Type options (excludes soft-deleted).
     * @param prerequisitesActivityTypeOptionDtoList - List of updated Prerequisites Activity Type option data
     * @return                                       - Response with list of updated Prerequisites Activity Type option data
     */
    @Operation(summary = "Update multiple Prerequisites Activity Type options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<PrerequisitesActivityTypeOptionDto> prerequisitesActivityTypeOptionDtoList) {
        List<PrerequisitesActivityTypeOptionModel> inputModelList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto : prerequisitesActivityTypeOptionDtoList) {
            inputModelList.add(convertToModel(prerequisitesActivityTypeOptionDto));
        }
        List<PrerequisitesActivityTypeOptionModel> updatedModelList = prerequisitesActivityTypeOptionService.updateMany(inputModelList);
        List<PrerequisitesActivityTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Prerequisites Activity Type option by ID, including soft-deleted.
     * @param prerequisitesActivityTypeOptionDto - Updated Prerequisites Activity Type option data
     * @return                                   - Response with updated Prerequisites Activity Type option data
     */
    @Operation(summary = "Update a single Prerequisites Activity Type option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto) {
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionService.hardUpdate(convertToModel(prerequisitesActivityTypeOptionDto));
        PrerequisitesActivityTypeOptionDto updatedDto = convertToDto(prerequisitesActivityTypeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Prerequisites Activity Type options, including soft-deleted.
     * @param prerequisitesActivityTypeOptionDtoList - List of updated Prerequisites Activity Type option data
     * @return                                       - Response with list of updated Prerequisites Activity Type option data
     */
    @Operation(summary = "Update all Prerequisites Activity Type options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<PrerequisitesActivityTypeOptionDto> prerequisitesActivityTypeOptionDtoList) {
        List<PrerequisitesActivityTypeOptionModel> inputModelList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionDto prerequisitesActivityTypeOptionDto : prerequisitesActivityTypeOptionDtoList) {
            inputModelList.add(convertToModel(prerequisitesActivityTypeOptionDto));
        }
        List<PrerequisitesActivityTypeOptionModel> updatedModelList = prerequisitesActivityTypeOptionService.hardUpdateAll(inputModelList);
        List<PrerequisitesActivityTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Prerequisites Activity Type option by ID.
     * @param id - Prerequisites Activity Type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Prerequisites Activity Type option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        PrerequisitesActivityTypeOptionModel deletedModel = prerequisitesActivityTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type option soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Prerequisites Activity Type option by ID.
     * @param id - Prerequisites Activity Type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Prerequisites Activity Type option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        prerequisitesActivityTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites activity type option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Prerequisites Activity Type options by ID.
     * @param idList - List of Prerequisites Activity Type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Prerequisites Activity Type options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<PrerequisitesActivityTypeOptionModel> deletedModelList = prerequisitesActivityTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites activity type options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Prerequisites Activity Type options by ID.
     * @param idList - List of Prerequisites Activity Type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Prerequisites Activity Type options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        prerequisitesActivityTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites activity type options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Prerequisites Activity Type options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Prerequisites Activity Type options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        prerequisitesActivityTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Prerequisites activity type options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}