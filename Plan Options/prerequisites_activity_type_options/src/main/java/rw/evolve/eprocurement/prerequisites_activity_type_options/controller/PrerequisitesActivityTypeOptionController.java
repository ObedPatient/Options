/**
 * REST API controller for managing Prerequisites Activity Type options
 * Provides endpoints for creating, retrieving, deleting and updating Prerequisites Activity Type option data.
 */
package rw.evolve.eprocurement.prerequisites_activity_type_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.prerequisites_activity_type_options.dto.PrerequisitesActivityFileTypeOptionDto;
import rw.evolve.eprocurement.prerequisites_activity_type_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.prerequisites_activity_type_options.model.PrerequisitesActivityTypeOptionModel;
import rw.evolve.eprocurement.prerequisites_activity_type_options.service.PrerequisitesActivityTypeOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/prerequisites_activity_type_option")
@Tag(name = "Prerequisites Activity Type Option Api")
public class PrerequisitesActivityTypeOptionController {

    @Autowired
    private PrerequisitesActivityTypeOptionService prerequisitesActivityTypeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a PrerequisitesActivityTypeOptionModel to PrerequisitesActivityTypeOptionDto.
     * @param model The PrerequisitesActivityTypeOptionModel to convert.
     * @return The converted PrerequisitesActivityFileTypeOptionDto.
     */
    private PrerequisitesActivityFileTypeOptionDto convertToDto(PrerequisitesActivityTypeOptionModel model){
        return modelMapper.map(model, PrerequisitesActivityFileTypeOptionDto.class);
    }

    /**
     * Converts a PrerequisitesActivityFileTypeOptionDto to PrerequisitesActivityTypeOptionModel.
     * @param dto The PrerequisitesActivityFileTypeOptionDto to convert.
     * @return The converted PrerequisitesActivityTypeOptionModel.
     */
    private PrerequisitesActivityTypeOptionModel convertToModel(PrerequisitesActivityFileTypeOptionDto dto){
        return modelMapper.map(dto, PrerequisitesActivityTypeOptionModel.class);
    }

    /**
     * Creates a single Prerequisites Activity Type Option
     * @param prerequisitesActivityFileTypeOptionDto DTO containing Prerequisites Activity Type Option data
     * @return ResponseEntity containing a Map with the created PrerequisitesActivityTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Prerequisites Activity Type Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createPrerequisitesActivityTypeOption(@Valid @RequestBody PrerequisitesActivityFileTypeOptionDto prerequisitesActivityFileTypeOptionDto){
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = convertToModel(prerequisitesActivityFileTypeOptionDto);
        PrerequisitesActivityTypeOptionModel createdPrerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionService.createPrerequisitesActivityTypeOption(prerequisitesActivityTypeOptionModel);
        PrerequisitesActivityFileTypeOptionDto createdPrerequisitesActivityTypeOptionDto = convertToDto(createdPrerequisitesActivityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Option", createdPrerequisitesActivityTypeOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Prerequisites Activity Type Options
     * @param prerequisitesActivityFileTypeOptionDtos List of Prerequisites Activity Type Option DTOs
     * @return ResponseEntity containing a Map with the created list of PrerequisitesActivityTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Prerequisites Activity Type Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createPrerequisitesActivityTypeOptions(@Valid @RequestBody List<PrerequisitesActivityFileTypeOptionDto> prerequisitesActivityFileTypeOptionDtos){
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = new ArrayList<>();
        for (PrerequisitesActivityFileTypeOptionDto dto: prerequisitesActivityFileTypeOptionDtos){
            prerequisitesActivityTypeOptionModels.add(convertToModel(dto));
        }
        List<PrerequisitesActivityTypeOptionModel> createdModels = prerequisitesActivityTypeOptionService.createPrerequisitesActivityTypeOptions(prerequisitesActivityTypeOptionModels);
        List<PrerequisitesActivityFileTypeOptionDto> createdPrerequisitesActivityTypeDtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: createdModels){
            createdPrerequisitesActivityTypeDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Prerequisites Activity Type Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", createdPrerequisitesActivityTypeDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a Prerequisites Activity Type Option by its ID, excluding soft-deleted options.
     * @param id The ID of the Prerequisites Activity Type Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the PrerequisitesActivityFileTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Prerequisites Activity Type Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("PrerequisitesActivityTypeOptionId") Long id){
        PrerequisitesActivityTypeOptionModel model = prerequisitesActivityTypeOptionService.readOne(id);
        PrerequisitesActivityFileTypeOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Prerequisites Activity Type Options.
     * @return ResponseEntity containing a Map with a list of PrerequisitesActivityTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Prerequisites Activity Type Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionService.readAll();
        List<PrerequisitesActivityFileTypeOptionDto> prerequisitesActivityFileTypeOptionDtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel: prerequisitesActivityTypeOptionModels){
            prerequisitesActivityFileTypeOptionDtos.add(convertToDto(prerequisitesActivityTypeOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Prerequisites Activity Type Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", prerequisitesActivityFileTypeOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Prerequisites Activity Type Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of PrerequisitesActivityFileTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Prerequisites Activity Type Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<PrerequisitesActivityTypeOptionModel> models = prerequisitesActivityTypeOptionService.hardReadAll();
        List<PrerequisitesActivityFileTypeOptionDto> dtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Prerequisites Activity Type Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple Prerequisites Activity Type Options by their IDs, excluding soft-deleted records.
     * @param ids List of Prerequisites Activity Type Option IDs
     * @return ResponseEntity containing a Map with a list of PrerequisitesActivityFileTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Prerequisites Activity Type Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<PrerequisitesActivityTypeOptionModel> prerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionService.readMany(ids);
        List<PrerequisitesActivityFileTypeOptionDto> prerequisitesActivityFileTypeOptionDtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: prerequisitesActivityTypeOptionModels){
            prerequisitesActivityFileTypeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", prerequisitesActivityFileTypeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Prerequisites Activity Type Option by its ID, excluding soft-deleted records.
     * @param id The ID of the Prerequisites Activity Type Option to update
     * @param prerequisitesActivityFileTypeOptionDto The updated Prerequisites Activity Type Option data
     * @return ResponseEntity containing a Map with the updated PrerequisitesActivityFileTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Prerequisites Activity Type Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody PrerequisitesActivityFileTypeOptionDto prerequisitesActivityFileTypeOptionDto){
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionService.updateOne(id, convertToModel(prerequisitesActivityFileTypeOptionDto));
        PrerequisitesActivityFileTypeOptionDto dto = convertToDto(prerequisitesActivityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Prerequisites Activity Type Options based on the provided list of Prerequisites Activity Type Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param prerequisitesActivityFileTypeOptionDtos List of PrerequisitesActivityTypeOptionDto objects containing updated data
     * @return ResponseEntity containing a Map with the list of updated PrerequisitesActivityFileTypeOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple Prerequisites Activity Type Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<PrerequisitesActivityFileTypeOptionDto> prerequisitesActivityFileTypeOptionDtos){
        List<PrerequisitesActivityTypeOptionModel> inputModels = new ArrayList<>();
        for (PrerequisitesActivityFileTypeOptionDto dto: prerequisitesActivityFileTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<PrerequisitesActivityTypeOptionModel> updatedModels = prerequisitesActivityTypeOptionService.updateMany(inputModels);
        List<PrerequisitesActivityFileTypeOptionDto> dtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Prerequisites Activity Type Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Prerequisites Activity Type Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the Prerequisites Activity Type Option to update.
     * @param prerequisitesActivityFileTypeOptionDto The updated Prerequisites Activity Type Option data.
     * @return ResponseEntity containing a Map with the updated PrerequisitesActivityFileTypeOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update Prerequisites Activity Type Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody PrerequisitesActivityFileTypeOptionDto prerequisitesActivityFileTypeOptionDto){
        PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionService.hardUpdateOne(id, convertToModel(prerequisitesActivityFileTypeOptionDto));
        PrerequisitesActivityFileTypeOptionDto dto = convertToDto(prerequisitesActivityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Prerequisites Activity Type Options, including soft-deleted records, based on their IDs.
     *
     * @param prerequisitesActivityFileTypeOptionDtos The list of updated Prerequisites Activity Type Option data.
     * @return ResponseEntity containing a Map with the list of updated PrerequisitesActivityFileTypeOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all Prerequisites Activity Type Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<PrerequisitesActivityFileTypeOptionDto> prerequisitesActivityFileTypeOptionDtos){
        List<PrerequisitesActivityTypeOptionModel> inputModels = new ArrayList<>();
        for (PrerequisitesActivityFileTypeOptionDto dto: prerequisitesActivityFileTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<PrerequisitesActivityTypeOptionModel> updatedModels = prerequisitesActivityTypeOptionService.hardUpdateAll(inputModels);
        List<PrerequisitesActivityFileTypeOptionDto> dtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel prerequisitesActivityTypeOptionModel: updatedModels){
            dtos.add(convertToDto(prerequisitesActivityTypeOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single Prerequisites Activity Type Option by ID
     * @param id ID of the Prerequisites Activity Type Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted PrerequisitesActivityFileTypeOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single Prerequisites Activity Type Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeletePrerequisitesActivityTypeOption(@RequestParam Long id){
        PrerequisitesActivityTypeOptionModel deletedPrerequisitesActivityTypeOptionModel = prerequisitesActivityTypeOptionService.softDeletePrerequisitesActivityTypeOption(id);
        PrerequisitesActivityFileTypeOptionDto deletedPrerequisitesActivityTypeOptionDto = convertToDto(deletedPrerequisitesActivityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Option", deletedPrerequisitesActivityTypeOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Prerequisites Activity Type Option by ID
     * @param id ID of the Prerequisites Activity Type Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single Prerequisites Activity Type Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeletePrerequisitesActivityTypeOption(@RequestParam Long id){
        prerequisitesActivityTypeOptionService.hardDeletePrerequisitesActivityTypeOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Prerequisites Activity Type Options by IDs
     * @param ids List of Prerequisites Activity Type Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted PrerequisitesActivityFileTypeOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple Prerequisites Activity Type Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeletePrerequisitesActivityTypeOptions(@RequestBody List<Long> ids){
        List<PrerequisitesActivityTypeOptionModel> deletedPrerequisitesActivityTypeOptionModels = prerequisitesActivityTypeOptionService.softDeletePrerequisitesActivityTypeOptions(ids);
        List<PrerequisitesActivityFileTypeOptionDto> deletedPrerequisitesActivityTypeOptionDtos = new ArrayList<>();
        for (PrerequisitesActivityTypeOptionModel model: deletedPrerequisitesActivityTypeOptionModels){
            deletedPrerequisitesActivityTypeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Prerequisites Activity Type Options", deletedPrerequisitesActivityTypeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple Prerequisites Activity Type Options by IDs
     * @param ids List of Prerequisites Activity Type Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple Prerequisites Activity Type Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeletePrerequisitesActivityTypeOptions(@RequestBody List<Long> ids){
        prerequisitesActivityTypeOptionService.hardDeletePrerequisitesActivityTypeOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prerequisites Activity Type Options Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}