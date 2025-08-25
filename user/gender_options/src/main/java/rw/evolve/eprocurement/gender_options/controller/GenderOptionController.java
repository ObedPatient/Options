package rw.evolve.eprocurement.gender_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.gender_options.dto.GenderOptionDto;
import rw.evolve.eprocurement.gender_options.model.GenderOptionModel;
import rw.evolve.eprocurement.gender_options.service.GenderOptionService;
import rw.evolve.eprocurement.gender_options.utils.GenderOptionIdGenerator;
import rw.evolve.eprocurement.gender_options.dto.ResponseMessageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for managing Gender options.
 * Handles CRUD operations for Gender option data with soft and hard delete capabilities.
 */
@RestController
@RequestMapping("api/gender_option")
@Tag(name = "Gender Option API")
public class GenderOptionController {

    private final GenderOptionService genderOptionService;

    private final ModelMapper modelMapper;

    public GenderOptionController(
            GenderOptionService genderOptionService,
            ModelMapper modelMapper
    ){
        this.genderOptionService = genderOptionService;
        this.modelMapper = modelMapper;
    }
    /**
     * Converts GenderOptionModel to GenderOptionDto.
     * @param model - GenderOptionModel to convert
     * @return      - Converted GenderOptionDto
     */
    private GenderOptionDto convertToDto(GenderOptionModel model) {
        return modelMapper.map(model, GenderOptionDto.class);
    }

    /**
     * Converts GenderOptionDto to GenderOptionModel.
     * @param genderOptionDto - GenderOptionDto to convert
     * @return                - Converted GenderOptionModel
     */
    private GenderOptionModel convertToModel(GenderOptionDto genderOptionDto) {
        return modelMapper.map(genderOptionDto, GenderOptionModel.class);
    }

    /**
     * Creates a single Gender option with a generated ID.
     * @param genderOptionDto - Gender option data
     * @return                - Response with success message
     */
    @Operation(summary = "Create a single Gender option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody GenderOptionDto genderOptionDto) {
        GenderOptionModel genderOptionModel = convertToModel(genderOptionDto);
        genderOptionModel.setId(GenderOptionIdGenerator.generateId());
        genderOptionService.save(genderOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Gender options with generated ID.
     * @param genderOptionDtoList - List of Gender option data
     * @return                    - Response with success message
     */
    @Operation(summary = "Create multiple Gender options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<GenderOptionDto> genderOptionDtoList) {
        List<GenderOptionModel> genderOptionModelList = new ArrayList<>();
        for (GenderOptionDto genderOptionDto : genderOptionDtoList) {
            GenderOptionModel model = convertToModel(genderOptionDto);
            model.setId(GenderOptionIdGenerator.generateId());
            genderOptionModelList.add(model);
        }
        genderOptionService.saveMany(genderOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Gender option by ID (excludes soft-deleted).
     * @param id - Gender option ID
     * @return   - Response with Gender option data
     */
    @Operation(summary = "Get a single Gender option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        GenderOptionModel model = genderOptionService.readOne(id);
        GenderOptionDto genderOptionDto = convertToDto(model);
        return new ResponseEntity<>(genderOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Gender options.
     * @return  - Response with list of Gender option data
     */
    @Operation(summary = "Get all available Gender options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<GenderOptionModel> genderOptionModelList = genderOptionService.readAll();
        List<GenderOptionDto> genderOptionDtoList = new ArrayList<>();
        for (GenderOptionModel genderOptionModel : genderOptionModelList) {
            genderOptionDtoList.add(convertToDto(genderOptionModel));
        }
        return new ResponseEntity<>(genderOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Gender options, including soft-deleted.
     * @return  - Response with list of all Gender option data
     */
    @Operation(summary = "Get all Gender options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<GenderOptionModel> modelList = genderOptionService.hardReadAll();
        List<GenderOptionDto> genderOptionDtoList = new ArrayList<>();
        for (GenderOptionModel model : modelList) {
            genderOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(genderOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Gender options by ID (excludes soft-deleted).
     * @param idList - List of Gender option IDs
     * @return       - Response with list of Gender option data
     */
    @Operation(summary = "Get multiple Gender options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<GenderOptionModel> genderOptionModelList = genderOptionService.readMany(idList);
        List<GenderOptionDto> genderOptionDtoList = new ArrayList<>();
        for (GenderOptionModel model : genderOptionModelList) {
            genderOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(genderOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Gender option by ID (excludes soft-deleted).
     * @param genderOptionDto - Updated Gender option data
     * @return                - Response with updated Gender option data
     */
    @Operation(summary = "Update a single Gender option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody GenderOptionDto genderOptionDto) {
        String modelId = genderOptionDto.getId();
        GenderOptionModel savedModel = genderOptionService.readOne(modelId);
        savedModel.setName(genderOptionDto.getName());
        savedModel.setDescription(genderOptionDto.getDescription());
        genderOptionService.updateOne(savedModel);
        GenderOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Gender options (excludes soft-deleted).
     * @param genderOptionDtoList - List of updated Gender option data
     * @return                    - Response with list of updated Gender option data
     */
    @Operation(summary = "Update multiple Gender options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<GenderOptionDto> genderOptionDtoList) {
        List<GenderOptionModel> inputModelList = new ArrayList<>();
        for (GenderOptionDto genderOptionDto : genderOptionDtoList) {
            inputModelList.add(convertToModel(genderOptionDto));
        }
        List<GenderOptionModel> updatedModelList = genderOptionService.updateMany(inputModelList);
        List<GenderOptionDto> updatedDtoList = new ArrayList<>();
        for (GenderOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Gender option by ID, including soft-deleted.
     * @param genderOptionDto - Updated Gender option data
     * @return                - Response with updated Gender option data
     */
    @Operation(summary = "Update a single Gender option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody GenderOptionDto genderOptionDto) {
        GenderOptionModel genderOptionModel = genderOptionService.hardUpdate(convertToModel(genderOptionDto));
        GenderOptionDto updatedDto = convertToDto(genderOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Gender options, including soft-deleted.
     * @param genderOptionDtoList - List of updated Gender option data
     * @return                    - Response with list of updated Gender option data
     */
    @Operation(summary = "Update all Gender options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<GenderOptionDto> genderOptionDtoList) {
        List<GenderOptionModel> inputModelList = new ArrayList<>();
        for (GenderOptionDto genderOptionDto : genderOptionDtoList) {
            inputModelList.add(convertToModel(genderOptionDto));
        }
        List<GenderOptionModel> updatedModelList = genderOptionService.hardUpdateAll(inputModelList);
        List<GenderOptionDto> updatedDtoList = new ArrayList<>();
        for (GenderOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Gender option by ID.
     * @param id - Gender option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Gender option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        GenderOptionModel deletedModel = genderOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Gender option by ID.
     * @param id - Gender option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Gender option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        genderOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Gender options by ID.
     * @param idList - List of Gender option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Gender options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<GenderOptionModel> deletedModelList = genderOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Gender options by ID.
     * @param idList - List of Gender option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Gender options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        genderOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Gender options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Gender options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        genderOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Gender options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}