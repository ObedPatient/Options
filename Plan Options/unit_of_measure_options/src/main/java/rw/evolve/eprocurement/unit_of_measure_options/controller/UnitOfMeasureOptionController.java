/**
 * REST API controller for managing Unit of Measure options.
 * Handles CRUD operations for Unit of Measure option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.unit_of_measure_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.unit_of_measure_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.unit_of_measure_options.dto.UnitOfMeasureOptionDto;
import rw.evolve.eprocurement.unit_of_measure_options.model.UnitOfMeasureOptionModel;
import rw.evolve.eprocurement.unit_of_measure_options.service.UnitOfMeasureOptionService;
import rw.evolve.eprocurement.unit_of_measure_options.utils.UnitOfMeasureOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/unit_of_measure_option")
@Tag(name = "Unit of Measure Option API")
public class UnitOfMeasureOptionController {

    private final UnitOfMeasureOptionService unitOfMeasureOptionService;

    private final ModelMapper modelMapper;

    public UnitOfMeasureOptionController(
            UnitOfMeasureOptionService unitOfMeasureOptionService,
            ModelMapper modelMapper
    ){
        this.unitOfMeasureOptionService = unitOfMeasureOptionService;
        this.modelMapper = modelMapper;
    }
    /**
     * Converts UnitOfMeasureOptionModel to UnitOfMeasureOptionDto.
     * @param model - UnitOfMeasureOptionModel to convert
     * @return      - Converted UnitOfMeasureOptionDto
     */
    private UnitOfMeasureOptionDto convertToDto(UnitOfMeasureOptionModel model) {
        return modelMapper.map(model, UnitOfMeasureOptionDto.class);
    }

    /**
     * Converts UnitOfMeasureOptionDto to UnitOfMeasureOptionModel, skipping ID mapping.
     * @param unitOfMeasureOptionDto - UnitOfMeasureOptionDto to convert
     * @return                       - Converted UnitOfMeasureOptionModel
     */
    private UnitOfMeasureOptionModel convertToModel(UnitOfMeasureOptionDto unitOfMeasureOptionDto) {
        return modelMapper.map(unitOfMeasureOptionDto, UnitOfMeasureOptionModel.class);
    }

    /**
     * Creates a single Unit of Measure option with a generated ID.
     * @param unitOfMeasureOptionDto - Unit of Measure option data
     * @return                       - Response with success message
     */
    @Operation(summary = "Create a single Unit of Measure option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody UnitOfMeasureOptionDto unitOfMeasureOptionDto) {
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = convertToModel(unitOfMeasureOptionDto);
        unitOfMeasureOptionModel.setId(UnitOfMeasureOptionIdGenerator.generateId());
        unitOfMeasureOptionService.save(unitOfMeasureOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Unit of Measure options with generated ID.
     * @param unitOfMeasureOptionDtoList - List of Unit of Measure option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create multiple Unit of Measure options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtoList) {
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModelList = new ArrayList<>();
        for (UnitOfMeasureOptionDto unitOfMeasureOptionDto : unitOfMeasureOptionDtoList) {
            UnitOfMeasureOptionModel model = convertToModel(unitOfMeasureOptionDto);
            model.setId(UnitOfMeasureOptionIdGenerator.generateId());
            unitOfMeasureOptionModelList.add(model);
        }
        unitOfMeasureOptionService.saveMany(unitOfMeasureOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of measure options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Unit of Measure option by ID (excludes soft-deleted).
     * @param id - Unit of Measure option ID
     * @return   - Response with Unit of Measure option data
     */
    @Operation(summary = "Get a single Unit of Measure option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        UnitOfMeasureOptionModel model = unitOfMeasureOptionService.readOne(id);
        UnitOfMeasureOptionDto unitOfMeasureOptionDto = convertToDto(model);
        return new ResponseEntity<>(unitOfMeasureOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Unit of Measure options.
     * @return - Response with list of Unit of Measure option data
     */
    @Operation(summary = "Get all available Unit of Measure options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModelList = unitOfMeasureOptionService.readAll();
        List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtoList = new ArrayList<>();
        for (UnitOfMeasureOptionModel unitOfMeasureOptionModel : unitOfMeasureOptionModelList) {
            unitOfMeasureOptionDtoList.add(convertToDto(unitOfMeasureOptionModel));
        }
        return new ResponseEntity<>(unitOfMeasureOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Unit of Measure options, including soft-deleted.
     * @return - Response with list of all Unit of Measure option data
     */
    @Operation(summary = "Get all Unit of Measure options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<UnitOfMeasureOptionModel> modelList = unitOfMeasureOptionService.hardReadAll();
        List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtoList = new ArrayList<>();
        for (UnitOfMeasureOptionModel model : modelList) {
            unitOfMeasureOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(unitOfMeasureOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Unit of Measure options by ID (excludes soft-deleted).
     * @param idList - List of Unit of Measure option IDs
     * @return       - Response with list of Unit of Measure option data
     */
    @Operation(summary = "Get multiple Unit of Measure options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModelList = unitOfMeasureOptionService.readMany(idList);
        List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtoList = new ArrayList<>();
        for (UnitOfMeasureOptionModel model : unitOfMeasureOptionModelList) {
            unitOfMeasureOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(unitOfMeasureOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Unit of Measure option by ID (excludes soft-deleted).
     * @param unitOfMeasureOptionDto - Updated Unit of Measure option data
     * @return                       - Response with updated Unit of Measure option data
     */
    @Operation(summary = "Update a single Unit of Measure option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody UnitOfMeasureOptionDto unitOfMeasureOptionDto) {
        String modelId = unitOfMeasureOptionDto.getId();
        UnitOfMeasureOptionModel savedModel = unitOfMeasureOptionService.readOne(modelId);
        savedModel.setName(unitOfMeasureOptionDto.getName());
        savedModel.setDescription(unitOfMeasureOptionDto.getDescription());
        unitOfMeasureOptionService.updateOne(savedModel);
        UnitOfMeasureOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Unit of Measure options (excludes soft-deleted).
     * @param unitOfMeasureOptionDtoList - List of updated Unit of Measure option data
     * @return                           - Response with list of updated Unit of Measure option data
     */
    @Operation(summary = "Update multiple Unit of Measure options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtoList) {
        List<UnitOfMeasureOptionModel> inputModelList = new ArrayList<>();
        for (UnitOfMeasureOptionDto unitOfMeasureOptionDto : unitOfMeasureOptionDtoList) {
            inputModelList.add(convertToModel(unitOfMeasureOptionDto));
        }
        List<UnitOfMeasureOptionModel> updatedModelList = unitOfMeasureOptionService.updateMany(inputModelList);
        List<UnitOfMeasureOptionDto> updatedDtoList = new ArrayList<>();
        for (UnitOfMeasureOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Unit of Measure option by ID, including soft-deleted.
     * @param unitOfMeasureOptionDto - Updated Unit of Measure option data
     * @return                       - Response with updated Unit of Measure option data
     */
    @Operation(summary = "Update a single Unit of Measure option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody UnitOfMeasureOptionDto unitOfMeasureOptionDto) {
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionService.hardUpdate(convertToModel(unitOfMeasureOptionDto));
        UnitOfMeasureOptionDto updatedDto = convertToDto(unitOfMeasureOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Unit of Measure options, including soft-deleted.
     * @param unitOfMeasureOptionDtoList - List of updated Unit of Measure option data
     * @return                           - Response with list of updated Unit of Measure option data
     */
    @Operation(summary = "Update all Unit of Measure options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtoList) {
        List<UnitOfMeasureOptionModel> inputModelList = new ArrayList<>();
        for (UnitOfMeasureOptionDto unitOfMeasureOptionDto : unitOfMeasureOptionDtoList) {
            inputModelList.add(convertToModel(unitOfMeasureOptionDto));
        }
        List<UnitOfMeasureOptionModel> updatedModelList = unitOfMeasureOptionService.hardUpdateAll(inputModelList);
        List<UnitOfMeasureOptionDto> updatedDtoList = new ArrayList<>();
        for (UnitOfMeasureOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Unit of Measure option by ID.
     * @param id - Unit of Measure option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Unit of Measure option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        UnitOfMeasureOptionModel deletedModel = unitOfMeasureOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure option soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Unit of Measure option by ID.
     * @param id - Unit of Measure option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Unit of Measure option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        unitOfMeasureOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Unit of Measure options by ID.
     * @param idList - List of Unit of Measure option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Unit of Measure options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<UnitOfMeasureOptionModel> deletedUnitOfMeasureOptionModelList = unitOfMeasureOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Unit of Measure options by ID.
     * @param idList - List of Unit of Measure option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Unit of Measure options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        unitOfMeasureOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Unit of Measure options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Unit of Measure options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Unit of Measure options")
    @GetMapping("/hard/delete-all")
    public ResponseEntity<Object> hardDeleteAll() {
        unitOfMeasureOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Unit of Measure options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}