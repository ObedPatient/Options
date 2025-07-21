/**
 * REST API controller for managing Unit of Measure options
 * Provides endpoints for creating, retrieving, deleting and updating Unit of Measure option data.
 */
package rw.evolve.eprocurement.unit_of_measure_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.unit_of_measure_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.unit_of_measure_options.dto.UnitOfMeasureOptionDto;
import rw.evolve.eprocurement.unit_of_measure_options.model.UnitOfMeasureOptionModel;
import rw.evolve.eprocurement.unit_of_measure_options.service.UnitOfMeasureOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/unit_of_measure_option")
@Tag(name = "Unit of Measure Option Api")
public class UnitOfMeasureOptionController {

    @Autowired
    private UnitOfMeasureOptionService unitOfMeasureOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a UnitOfMeasureOptionModel to UnitOfMeasureOptionDto.
     * @param model The UnitOfMeasureOptionModel to convert.
     * @return The converted UnitOfMeasureOptionDto.
     */
    private UnitOfMeasureOptionDto convertToDto(UnitOfMeasureOptionModel model){
        return modelMapper.map(model, UnitOfMeasureOptionDto.class);
    }

    /**
     * Converts a UnitOfMeasureOptionDto to UnitOfMeasureOptionModel.
     * @param dto The UnitOfMeasureOptionDto to convert.
     * @return The converted UnitOfMeasureOptionModel.
     */
    private UnitOfMeasureOptionModel convertToModel(UnitOfMeasureOptionDto dto){
        return modelMapper.map(dto, UnitOfMeasureOptionModel.class);
    }

    /**
     * Creates a single Unit of Measure Option
     * @param unitOfMeasureOptionDto DTO containing Unit of Measure Option data
     * @return ResponseEntity containing a Map with the created UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Unit of Measure Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createUnitOfMeasureOption(@Valid @RequestBody UnitOfMeasureOptionDto unitOfMeasureOptionDto){
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = convertToModel(unitOfMeasureOptionDto);
        UnitOfMeasureOptionModel createdUnitOfMeasureOptionModel = unitOfMeasureOptionService.createUnitOfMeasureOption(unitOfMeasureOptionModel);
        UnitOfMeasureOptionDto createdUnitOfMeasureOptionDto = convertToDto(createdUnitOfMeasureOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Option", createdUnitOfMeasureOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Unit of Measure Options
     * @param unitOfMeasureOptionDtos List of Unit of Measure Option DTOs
     * @return ResponseEntity containing a Map with the created list of UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Unit of Measure Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createUnitOfMeasureOptions(@Valid @RequestBody List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtos){
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = new ArrayList<>();
        for (UnitOfMeasureOptionDto dto: unitOfMeasureOptionDtos){
            unitOfMeasureOptionModels.add(convertToModel(dto));
        }
        List<UnitOfMeasureOptionModel> createdModels = unitOfMeasureOptionService.createUnitOfMeasureOptions(unitOfMeasureOptionModels);
        List<UnitOfMeasureOptionDto> createdUnitOfMeasureDtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: createdModels){
            createdUnitOfMeasureDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Unit of Measure Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", createdUnitOfMeasureDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a Unit of Measure Option by its ID, excluding soft-deleted options.
     * @param id The ID of the Unit of Measure Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Unit of Measure Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("UnitOfMeasureOptionId") Long id){
        UnitOfMeasureOptionModel model = unitOfMeasureOptionService.readOne(id);
        UnitOfMeasureOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Unit of Measure Options.
     * @return ResponseEntity containing a Map with a list of UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Unit of Measure Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = unitOfMeasureOptionService.readAll();
        List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel unitOfMeasureOptionModel: unitOfMeasureOptionModels){
            unitOfMeasureOptionDtos.add(convertToDto(unitOfMeasureOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Unit of Measure Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", unitOfMeasureOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Unit of Measure Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Unit of Measure Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<UnitOfMeasureOptionModel> models = unitOfMeasureOptionService.hardReadAll();
        List<UnitOfMeasureOptionDto> dtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Unit of Measure Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple Unit of Measure Options by their IDs, excluding soft-deleted records.
     * @param ids List of Unit of Measure Option IDs
     * @return ResponseEntity containing a Map with a list of UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Unit of Measure Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<UnitOfMeasureOptionModel> unitOfMeasureOptionModels = unitOfMeasureOptionService.readMany(ids);
        List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: unitOfMeasureOptionModels){
            unitOfMeasureOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", unitOfMeasureOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Unit of Measure Option by its ID, excluding soft-deleted records.
     * @param id The ID of the Unit of Measure Option to update
     * @param unitOfMeasureOptionDto The updated Unit of Measure Option data
     * @return ResponseEntity containing a Map with the updated UnitOfMeasureOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Unit of Measure Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody UnitOfMeasureOptionDto unitOfMeasureOptionDto){
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionService.updateOne(id, convertToModel(unitOfMeasureOptionDto));
        UnitOfMeasureOptionDto dto = convertToDto(unitOfMeasureOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Unit of Measure Options based on the provided list of Unit of Measure Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param unitOfMeasureOptionDtos List of UnitOfMeasureOptionDto objects containing updated data
     * @return ResponseEntity containing a Map with the list of updated UnitOfMeasureOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple Unit of Measure Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtos){
        List<UnitOfMeasureOptionModel> inputModels = new ArrayList<>();
        for (UnitOfMeasureOptionDto dto: unitOfMeasureOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<UnitOfMeasureOptionModel> updatedModels = unitOfMeasureOptionService.updateMany(inputModels);
        List<UnitOfMeasureOptionDto> dtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Unit of Measure Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Unit of Measure Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the Unit of Measure Option to update.
     * @param unitOfMeasureOptionDto The updated Unit of Measure Option data.
     * @return ResponseEntity containing a Map with the updated UnitOfMeasureOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update Unit of Measure Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody UnitOfMeasureOptionDto unitOfMeasureOptionDto){
        UnitOfMeasureOptionModel unitOfMeasureOptionModel = unitOfMeasureOptionService.hardUpdateOne(id, convertToModel(unitOfMeasureOptionDto));
        UnitOfMeasureOptionDto dto = convertToDto(unitOfMeasureOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Unit of Measure Options, including soft-deleted records, based on their IDs.
     *
     * @param unitOfMeasureOptionDtos The list of updated Unit of Measure Option data.
     * @return ResponseEntity containing a Map with the list of updated UnitOfMeasureOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all Unit of Measure Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<UnitOfMeasureOptionDto> unitOfMeasureOptionDtos){
        List<UnitOfMeasureOptionModel> inputModels = new ArrayList<>();
        for (UnitOfMeasureOptionDto dto: unitOfMeasureOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<UnitOfMeasureOptionModel> updatedModels = unitOfMeasureOptionService.hardUpdateAll(inputModels);
        List<UnitOfMeasureOptionDto> dtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel unitOfMeasureOptionModel: updatedModels){
            dtos.add(convertToDto(unitOfMeasureOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single Unit of Measure Option by ID
     * @param id ID of the Unit of Measure Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted UnitOfMeasureOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single Unit of Measure Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteUnitOfMeasureOption(@RequestParam Long id){
        UnitOfMeasureOptionModel deletedUnitOfMeasureOptionModel = unitOfMeasureOptionService.softDeleteUnitOfMeasureOption(id);
        UnitOfMeasureOptionDto deletedUnitOfMeasureOptionDto = convertToDto(deletedUnitOfMeasureOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Option", deletedUnitOfMeasureOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Unit of Measure Option by ID
     * @param id ID of the Unit of Measure Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single Unit of Measure Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteUnitOfMeasureOption(@RequestParam Long id){
        unitOfMeasureOptionService.hardDeleteUnitOfMeasureOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Unit of Measure Options by IDs
     * @param ids List of Unit of Measure Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted UnitOfMeasureOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple Unit of Measure Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteUnitOfMeasureOptions(@RequestBody List<Long> ids){
        List<UnitOfMeasureOptionModel> deletedUnitOfMeasureOptionModels = unitOfMeasureOptionService.softDeleteUnitOfMeasureOptions(ids);
        List<UnitOfMeasureOptionDto> deletedUnitOfMeasureOptionDtos = new ArrayList<>();
        for (UnitOfMeasureOptionModel model: deletedUnitOfMeasureOptionModels){
            deletedUnitOfMeasureOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Unit of Measure Options", deletedUnitOfMeasureOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple Unit of Measure Options by IDs
     * @param ids List of Unit of Measure Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple Unit of Measure Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteUnitOfMeasureOptions(@RequestBody List<Long> ids){
        unitOfMeasureOptionService.hardDeleteUnitOfMeasureOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Unit of Measure Options Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}