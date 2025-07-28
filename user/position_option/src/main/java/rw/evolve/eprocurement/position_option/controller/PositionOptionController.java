/**
 * REST API controller for managing Position options.
 * Handles CRUD operations for Position option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.position_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.position_option.dto.PositionOptionDto;
import rw.evolve.eprocurement.position_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.position_option.model.PositionOptionModel;
import rw.evolve.eprocurement.position_option.service.PositionOptionService;
import rw.evolve.eprocurement.position_option.utils.PositionOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/position_option")
@Tag(name = "Position Option API")
public class PositionOptionController {

    @Autowired
    private PositionOptionService positionOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts PositionOptionModel to PositionOptionDto.
     * @param model - PositionOptionModel to convert
     * @return      - Converted PositionOptionDto
     */
    private PositionOptionDto convertToDto(PositionOptionModel model) {
        return modelMapper.map(model, PositionOptionDto.class);
    }

    /**
     * Converts PositionOptionDto to PositionOptionModel.
     * @param positionOptionDto - PositionOptionDto to convert
     * @return                  - Converted PositionOptionModel
     */
    private PositionOptionModel convertToModel(PositionOptionDto positionOptionDto) {
        return modelMapper.map(positionOptionDto, PositionOptionModel.class);
    }

    /**
     * Creates a single Position option with a generated ID.
     * @param positionOptionDto - Position option data
     * @return                  - Response with success message
     */
    @Operation(summary = "Create a single Position option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody PositionOptionDto positionOptionDto) {
        PositionOptionModel positionOptionModel = convertToModel(positionOptionDto);
        positionOptionModel.setId(PositionOptionIdGenerator.generateId());
        positionOptionService.save(positionOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Position option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Position options with generated IDs.
     * @param positionOptionDtoList - List of Position option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create multiple Position options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<PositionOptionDto> positionOptionDtoList) {
        List<PositionOptionModel> positionOptionModelList = new ArrayList<>();
        for (PositionOptionDto positionOptionDto : positionOptionDtoList) {
            PositionOptionModel model = convertToModel(positionOptionDto);
            model.setId(PositionOptionIdGenerator.generateId());
            positionOptionModelList.add(model);
        }
        positionOptionService.saveMany(positionOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Position options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Position option by ID (excludes soft-deleted).
     * @param id - Position option ID
     * @return   - Response with Position option data
     */
    @Operation(summary = "Get a single Position option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        PositionOptionModel model = positionOptionService.readOne(id);
        PositionOptionDto positionOptionDto = convertToDto(model);
        return new ResponseEntity<>(positionOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Position options.
     * @return - Response with list of Position option data
     */
    @Operation(summary = "Get all available Position options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<PositionOptionModel> positionOptionModelList = positionOptionService.readAll();
        List<PositionOptionDto> positionOptionDtoList = new ArrayList<>();
        for (PositionOptionModel positionOptionModel : positionOptionModelList) {
            positionOptionDtoList.add(convertToDto(positionOptionModel));
        }
        return new ResponseEntity<>(positionOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Position options, including soft-deleted.
     * @return - Response with list of all Position option data
     */
    @Operation(summary = "Get all Position options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<PositionOptionModel> modelList = positionOptionService.hardReadAll();
        List<PositionOptionDto> positionOptionDtoList = new ArrayList<>();
        for (PositionOptionModel model : modelList) {
            positionOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(positionOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Position options by ID (excludes soft-deleted).
     * @param idList - List of Position option IDs
     * @return       - Response with list of Position option data
     */
    @Operation(summary = "Get multiple Position options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<PositionOptionModel> positionOptionModelList = positionOptionService.readMany(idList);
        List<PositionOptionDto> positionOptionDtoList = new ArrayList<>();
        for (PositionOptionModel model : positionOptionModelList) {
            positionOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(positionOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Position option by ID (excludes soft-deleted).
     * @param positionOptionDto - Updated Position option data
     * @return                  - Response with updated Position option data
     */
    @Operation(summary = "Update a single Position option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody PositionOptionDto positionOptionDto) {
        String modelId = positionOptionDto.getId();
        PositionOptionModel savedModel = positionOptionService.readOne(modelId);
        savedModel.setName(positionOptionDto.getName());
        savedModel.setDescription(positionOptionDto.getDescription());
        positionOptionService.updateOne(savedModel);
        PositionOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Position options (excludes soft-deleted).
     * @param positionOptionDtoList - List of updated Position option data
     * @return                      - Response with list of updated Position option data
     */
    @Operation(summary = "Update multiple Position options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<PositionOptionDto> positionOptionDtoList) {
        List<PositionOptionModel> inputModelList = new ArrayList<>();
        for (PositionOptionDto positionOptionDto : positionOptionDtoList) {
            inputModelList.add(convertToModel(positionOptionDto));
        }
        List<PositionOptionModel> updatedModelList = positionOptionService.updateMany(inputModelList);
        List<PositionOptionDto> updatedDtoList = new ArrayList<>();
        for (PositionOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Position option by ID, including soft-deleted.
     * @param positionOptionDto - Updated Position option data
     * @return                  - Response with updated Position option data
     */
    @Operation(summary = "Update a single Position option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody PositionOptionDto positionOptionDto) {
        PositionOptionModel positionOptionModel = positionOptionService.hardUpdate(convertToModel(positionOptionDto));
        PositionOptionDto updatedDto = convertToDto(positionOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Position options, including soft-deleted.
     * @param positionOptionDtoList - List of updated Position option data
     * @return                      - Response with list of updated Position option data
     */
    @Operation(summary = "Update all Position options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<PositionOptionDto> positionOptionDtoList) {
        List<PositionOptionModel> inputModelList = new ArrayList<>();
        for (PositionOptionDto positionOptionDto : positionOptionDtoList) {
            inputModelList.add(convertToModel(positionOptionDto));
        }
        List<PositionOptionModel> updatedModelList = positionOptionService.hardUpdateAll(inputModelList);
        List<PositionOptionDto> updatedDtoList = new ArrayList<>();
        for (PositionOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Position option by ID.
     * @param id - Position option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Position option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        PositionOptionModel deletedModel = positionOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Position option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Position option by ID.
     * @param id - Position option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Position option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        positionOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Position option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Position options by ID.
     * @param idList - List of Position option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Position options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<PositionOptionModel> deletedModelList = positionOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Position options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Position options by ID.
     * @param idList - List of Position option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Position options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        positionOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Position options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Position options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Position options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        positionOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Position options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}