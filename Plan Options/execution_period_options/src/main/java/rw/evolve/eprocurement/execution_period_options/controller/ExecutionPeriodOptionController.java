/**
 * REST API controller for managing Execution Period options
 * Provides endpoints for creating, retrieving, deleting and updating Execution Period option data.
 */
package rw.evolve.eprocurement.execution_period_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.execution_period_options.dto.ExecutionPeriodOptionDto;
import rw.evolve.eprocurement.execution_period_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.execution_period_options.model.ExecutionPeriodOptionModel;
import rw.evolve.eprocurement.execution_period_options.service.ExecutionPeriodOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/execution_period_option")
@Tag(name = "Execution Period Option Api")
public class ExecutionPeriodOptionController {

    @Autowired
    private ExecutionPeriodOptionService executionPeriodOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts an ExecutionPeriodOptionModel to ExecutionPeriodOptionDto.
     * @param model The ExecutionPeriodOptionModel to convert.
     * @return The converted ExecutionPeriodOptionDto.
     */
    private ExecutionPeriodOptionDto convertToDto(ExecutionPeriodOptionModel model){
        return modelMapper.map(model, ExecutionPeriodOptionDto.class);
    }

    /**
     * Converts an ExecutionPeriodOptionDto to ExecutionPeriodOptionModel.
     * @param dto The ExecutionPeriodOptionDto to convert.
     * @return The converted ExecutionPeriodOptionModel.
     */
    private ExecutionPeriodOptionModel convertToModel(ExecutionPeriodOptionDto dto){
        return modelMapper.map(dto, ExecutionPeriodOptionModel.class);
    }

    /**
     * Creates a single Execution Period Option
     * @param executionPeriodOptionDto DTO containing Execution Period Option data
     * @return ResponseEntity containing a Map with the created ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Execution Period Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createExecutionPeriodOption(@Valid @RequestBody ExecutionPeriodOptionDto executionPeriodOptionDto){
        ExecutionPeriodOptionModel executionPeriodOptionModel = convertToModel(executionPeriodOptionDto);
        ExecutionPeriodOptionModel createdExecutionPeriodOptionModel = executionPeriodOptionService.createExecutionPeriodOption(executionPeriodOptionModel);
        ExecutionPeriodOptionDto createdExecutionPeriodOptionDto = convertToDto(createdExecutionPeriodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Option", createdExecutionPeriodOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Execution Period Options
     * @param executionPeriodOptionDtos List of Execution Period Option DTOs
     * @return ResponseEntity containing a Map with the created list of ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Execution Period Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createExecutionPeriodOptions(@Valid @RequestBody List<ExecutionPeriodOptionDto> executionPeriodOptionDtos){
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = new ArrayList<>();
        for (ExecutionPeriodOptionDto dto: executionPeriodOptionDtos){
            executionPeriodOptionModels.add(convertToModel(dto));
        }
        List<ExecutionPeriodOptionModel> createdModels = executionPeriodOptionService.createExecutionPeriodOptions(executionPeriodOptionModels);
        List<ExecutionPeriodOptionDto> createdExecutionPeriodDtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: createdModels){
            createdExecutionPeriodDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Execution Period Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", createdExecutionPeriodDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves an Execution Period Option by its ID, excluding soft-deleted options.
     * @param id The ID of the Execution Period Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Execution Period Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("ExecutionPeriodOptionId") Long id){
        ExecutionPeriodOptionModel model = executionPeriodOptionService.readOne(id);
        ExecutionPeriodOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Execution Period Options.
     * @return ResponseEntity containing a Map with a list of ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Execution Period Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = executionPeriodOptionService.readAll();
        List<ExecutionPeriodOptionDto> executionPeriodOptionDtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel executionPeriodOptionModel: executionPeriodOptionModels){
            executionPeriodOptionDtos.add(convertToDto(executionPeriodOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Execution Period Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", executionPeriodOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Execution Period Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Execution Period Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<ExecutionPeriodOptionModel> models = executionPeriodOptionService.hardReadAll();
        List<ExecutionPeriodOptionDto> dtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Execution Period Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple Execution Period Options by their IDs, excluding soft-deleted records.
     * @param ids List of Execution Period Option IDs
     * @return ResponseEntity containing a Map with a list of ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Execution Period Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<ExecutionPeriodOptionModel> executionPeriodOptionModels = executionPeriodOptionService.readMany(ids);
        List<ExecutionPeriodOptionDto> executionPeriodOptionDtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: executionPeriodOptionModels){
            executionPeriodOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", executionPeriodOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an Execution Period Option by its ID, excluding soft-deleted records.
     * @param id The ID of the Execution Period Option to update
     * @param executionPeriodOptionDto The updated Execution Period Option data
     * @return ResponseEntity containing a Map with the updated ExecutionPeriodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Execution Period Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody ExecutionPeriodOptionDto executionPeriodOptionDto){
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionService.updateOne(id, convertToModel(executionPeriodOptionDto));
        ExecutionPeriodOptionDto dto = convertToDto(executionPeriodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Execution Period Options based on the provided list of Execution Period Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param executionPeriodOptionDtos List of ExecutionPeriodOptionDto objects containing updated data
     * @return ResponseEntity containing a Map with the list of updated ExecutionPeriodOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple Execution Period Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<ExecutionPeriodOptionDto> executionPeriodOptionDtos){
        List<ExecutionPeriodOptionModel> inputModels = new ArrayList<>();
        for (ExecutionPeriodOptionDto dto: executionPeriodOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<ExecutionPeriodOptionModel> updatedModels = executionPeriodOptionService.updateMany(inputModels);
        List<ExecutionPeriodOptionDto> dtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Execution Period Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an Execution Period Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the Execution Period Option to update.
     * @param executionPeriodOptionDto The updated Execution Period Option data.
     * @return ResponseEntity containing a Map with the updated ExecutionPeriodOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update Execution Period Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody ExecutionPeriodOptionDto executionPeriodOptionDto){
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionService.hardUpdateOne(id, convertToModel(executionPeriodOptionDto));
        ExecutionPeriodOptionDto dto = convertToDto(executionPeriodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Execution Period Options, including soft-deleted records, based on their IDs.
     *
     * @param executionPeriodOptionDtos The list of updated Execution Period Option data.
     * @return ResponseEntity containing a Map with the list of updated ExecutionPeriodOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all Execution Period Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<ExecutionPeriodOptionDto> executionPeriodOptionDtos){
        List<ExecutionPeriodOptionModel> inputModels = new ArrayList<>();
        for (ExecutionPeriodOptionDto dto: executionPeriodOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<ExecutionPeriodOptionModel> updatedModels = executionPeriodOptionService.hardUpdateAll(inputModels);
        List<ExecutionPeriodOptionDto> dtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel executionPeriodOptionModel: updatedModels){
            dtos.add(convertToDto(executionPeriodOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single Execution Period Option by ID
     * @param id ID of the Execution Period Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted ExecutionPeriodOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single Execution Period Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteExecutionPeriodOption(@RequestParam Long id){
        ExecutionPeriodOptionModel deletedExecutionPeriodOptionModel = executionPeriodOptionService.softDeleteExecutionPeriodOption(id);
        ExecutionPeriodOptionDto deletedExecutionPeriodOptionDto = convertToDto(deletedExecutionPeriodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Option", deletedExecutionPeriodOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Execution Period Option by ID
     * @param id ID of the Execution Period Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single Execution Period Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteExecutionPeriodOption(@RequestParam Long id){
        executionPeriodOptionService.hardDeleteExecutionPeriodOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Execution Period Options by IDs
     * @param ids List of Execution Period Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted ExecutionPeriodOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple Execution Period Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteExecutionPeriodOptions(@RequestBody List<Long> ids){
        List<ExecutionPeriodOptionModel> deletedExecutionPeriodOptionModels = executionPeriodOptionService.softDeleteExecutionPeriodOptions(ids);
        List<ExecutionPeriodOptionDto> deletedExecutionPeriodOptionDtos = new ArrayList<>();
        for (ExecutionPeriodOptionModel model: deletedExecutionPeriodOptionModels){
            deletedExecutionPeriodOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Execution Period Options", deletedExecutionPeriodOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple Execution Period Options by IDs
     * @param ids List of Execution Period Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple Execution Period Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteExecutionPeriodOptions(@RequestBody List<Long> ids){
        executionPeriodOptionService.hardDeleteExecutionPeriodOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Options Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}