/**
 * REST API controller for managing Theme status options.
 * Handles CRUD operations for Theme status option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.theme_status.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.theme_status.dto.ThemeStatusOptionDto;
import rw.evolve.eprocurement.theme_status.dto.ResponseMessageDto;
import rw.evolve.eprocurement.theme_status.model.ThemeStatusOptionModel;
import rw.evolve.eprocurement.theme_status.service.ThemeStatusOptionService;
import rw.evolve.eprocurement.theme_status.utils.ThemeStatusOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/theme_status_option")
@Tag(name = "Theme Status Option API")
public class ThemeStatusOptionController {

    @Autowired
    private ThemeStatusOptionService themeStatusOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts ThemeStatusOptionModel to ThemeStatusOptionDto.
     * @param model - ThemeStatusOptionModel to convert
     * @return      - Converted ThemeStatusOptionDto
     */
    private ThemeStatusOptionDto convertToDto(ThemeStatusOptionModel model) {
        return modelMapper.map(model, ThemeStatusOptionDto.class);
    }

    /**
     * Converts ThemeStatusOptionDto to ThemeStatusOptionModel.
     * @param themeStatusOptionDto - ThemeStatusOptionDto to convert
     * @return                     - Converted ThemeStatusOptionModel
     */
    private ThemeStatusOptionModel convertToModel(ThemeStatusOptionDto themeStatusOptionDto) {
        return modelMapper.map(themeStatusOptionDto, ThemeStatusOptionModel.class);
    }

    /**
     * Creates a single Theme status option with a generated ID.
     * @param themeStatusOptionDto - Theme status option data
     * @return                     - Response with success message
     */
    @Operation(summary = "Create a single Theme status option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ThemeStatusOptionDto themeStatusOptionDto) {
        ThemeStatusOptionModel themeStatusOptionModel = convertToModel(themeStatusOptionDto);
        themeStatusOptionModel.setId(ThemeStatusOptionIdGenerator.generateId());
        themeStatusOptionService.save(themeStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Theme status option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Theme status options with generated IDs.
     * @param themeStatusOptionDtoList - List of Theme status option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create multiple Theme status options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ThemeStatusOptionDto> themeStatusOptionDtoList) {
        List<ThemeStatusOptionModel> themeStatusOptionModelList = new ArrayList<>();
        for (ThemeStatusOptionDto themeStatusOptionDto : themeStatusOptionDtoList) {
            ThemeStatusOptionModel model = convertToModel(themeStatusOptionDto);
            model.setId(ThemeStatusOptionIdGenerator.generateId());
            themeStatusOptionModelList.add(model);
        }
        themeStatusOptionService.saveMany(themeStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Theme status options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Theme status option by ID (excludes soft-deleted).
     * @param id - Theme status option ID
     * @return   - Response with Theme status option data
     */
    @Operation(summary = "Get a single Theme status option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ThemeStatusOptionModel model = themeStatusOptionService.readOne(id);
        ThemeStatusOptionDto themeStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(themeStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Theme status options.
     * @return - Response with list of Theme status option data
     */
    @Operation(summary = "Get all available Theme status options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ThemeStatusOptionModel> themeStatusOptionModelList = themeStatusOptionService.readAll();
        List<ThemeStatusOptionDto> themeStatusOptionDtoList = new ArrayList<>();
        for (ThemeStatusOptionModel themeStatusOptionModel : themeStatusOptionModelList) {
            themeStatusOptionDtoList.add(convertToDto(themeStatusOptionModel));
        }
        return new ResponseEntity<>(themeStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Theme status options, including soft-deleted.
     * @return - Response with list of all Theme status option data
     */
    @Operation(summary = "Get all Theme status options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ThemeStatusOptionModel> modelList = themeStatusOptionService.hardReadAll();
        List<ThemeStatusOptionDto> themeStatusOptionDtoList = new ArrayList<>();
        for (ThemeStatusOptionModel model : modelList) {
            themeStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(themeStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Theme status options by ID (excludes soft-deleted).
     * @param idList - List of Theme status option IDs
     * @return       - Response with list of Theme status option data
     */
    @Operation(summary = "Get multiple Theme status options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ThemeStatusOptionModel> themeStatusOptionModelList = themeStatusOptionService.readMany(idList);
        List<ThemeStatusOptionDto> themeStatusOptionDtoList = new ArrayList<>();
        for (ThemeStatusOptionModel model : themeStatusOptionModelList) {
            themeStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(themeStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Theme status option by ID (excludes soft-deleted).
     * @param themeStatusOptionDto - Updated Theme status option data
     * @return                     - Response with updated Theme status option data
     */
    @Operation(summary = "Update a single Theme status option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ThemeStatusOptionDto themeStatusOptionDto) {
        String modelId = themeStatusOptionDto.getId();
        ThemeStatusOptionModel savedModel = themeStatusOptionService.readOne(modelId);
        savedModel.setName(themeStatusOptionDto.getName());
        savedModel.setDescription(themeStatusOptionDto.getDescription());
        themeStatusOptionService.updateOne(savedModel);
        ThemeStatusOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Theme status options (excludes soft-deleted).
     * @param themeStatusOptionDtoList - List of updated Theme status option data
     * @return                         - Response with list of updated Theme status option data
     */
    @Operation(summary = "Update multiple Theme status options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ThemeStatusOptionDto> themeStatusOptionDtoList) {
        List<ThemeStatusOptionModel> inputModelList = new ArrayList<>();
        for (ThemeStatusOptionDto themeStatusOptionDto : themeStatusOptionDtoList) {
            inputModelList.add(convertToModel(themeStatusOptionDto));
        }
        List<ThemeStatusOptionModel> updatedModelList = themeStatusOptionService.updateMany(inputModelList);
        List<ThemeStatusOptionDto> updatedDtoList = new ArrayList<>();
        for (ThemeStatusOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Theme status option by ID, including soft-deleted.
     * @param themeStatusOptionDto - Updated Theme status option data
     * @return                     - Response with updated Theme status option data
     */
    @Operation(summary = "Update a single Theme status option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ThemeStatusOptionDto themeStatusOptionDto) {
        ThemeStatusOptionModel themeStatusOptionModel = themeStatusOptionService.hardUpdate(convertToModel(themeStatusOptionDto));
        ThemeStatusOptionDto updatedDto = convertToDto(themeStatusOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Theme status options, including soft-deleted.
     * @param themeStatusOptionDtoList - List of updated Theme status option data
     * @return                         - Response with list of updated Theme status option data
     */
    @Operation(summary = "Update all Theme status options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ThemeStatusOptionDto> themeStatusOptionDtoList) {
        List<ThemeStatusOptionModel> inputModelList = new ArrayList<>();
        for (ThemeStatusOptionDto themeStatusOptionDto : themeStatusOptionDtoList) {
            inputModelList.add(convertToModel(themeStatusOptionDto));
        }
        List<ThemeStatusOptionModel> updatedModelList = themeStatusOptionService.hardUpdateAll(inputModelList);
        List<ThemeStatusOptionDto> updatedDtoList = new ArrayList<>();
        for (ThemeStatusOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Theme status option by ID.
     * @param id - Theme status option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Theme status option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ThemeStatusOptionModel deletedModel = themeStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Theme status option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Theme status option by ID.
     * @param id - Theme status option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Theme status option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        themeStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Theme status option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Theme status options by ID.
     * @param idList - List of Theme status option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Theme status options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ThemeStatusOptionModel> deletedModelList = themeStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Theme status options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Theme status options by ID.
     * @param idList - List of Theme status option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Theme status options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        themeStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Theme status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Theme status options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Theme status options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        themeStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Theme status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}