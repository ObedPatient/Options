/**
 * REST API controller for managing Plan status options
 * Provides endpoints for creating, retrieving, deleting and updating Plan status option data.
 */
package rw.evolve.eprocurement.plan_status_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.plan_status_option.dto.PlanStatusOptionDto;
import rw.evolve.eprocurement.plan_status_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.plan_status_option.model.PlanStatusOptionModel;
import rw.evolve.eprocurement.plan_status_option.service.PlanStatusOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/plan_status_option")
@Tag(name = "Plan Status Option Api")
public class PlanStatusOptionController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private PlanStatusOptionService planStatusOptionService;

    /**
     * Converts a PlanStatusOptionModel to PlanStatusOptionDto.
     * @param model The PlanStatusOptionModel to convert.
     * @return The converted PlanStatusOptionDto.
     */
    private PlanStatusOptionDto convertToDto(PlanStatusOptionModel model){
        return modelMapper.map(model, PlanStatusOptionDto.class);
    }

    /**
     * Converts a PlanStatusOptionDto to PlanStatusOptionModel.
     * @param dto The PlanStatusOptionDto to convert.
     * @return The converted PlanStatusOptionModel.
     */
    private PlanStatusOptionModel convertToModel(PlanStatusOptionDto dto){
        return modelMapper.map(dto, PlanStatusOptionModel.class);
    }

    /**
     * Creates a single Plan Status Option
     * @param planStatusOptionDto DTO containing Plan Status Option data
     * @return ResponseEntity containing a Map with the created PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Plan Status Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createPlanStatusOption(@Valid @RequestBody PlanStatusOptionDto planStatusOptionDto){
        PlanStatusOptionModel planStatusOptionModel = convertToModel(planStatusOptionDto);
        PlanStatusOptionModel createdPlanStatusOptionModel = planStatusOptionService.createPlanStatusOption(planStatusOptionModel);
        PlanStatusOptionDto createdPlanStatusOptionDto = convertToDto(createdPlanStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", createdPlanStatusOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Plan Status Options
     * @param planStatusOptionDtos List of Plan Status Option DTOs
     * @return ResponseEntity containing a Map with the created list of PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Plan Status Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createPlanStatusOptions(@Valid @RequestBody List<PlanStatusOptionDto> planStatusOptionDtos ){
        List<PlanStatusOptionModel> planStatusOptionModels = new ArrayList<>();
        for (PlanStatusOptionDto dto: planStatusOptionDtos){
            planStatusOptionModels.add(convertToModel(dto));
        }
        List<PlanStatusOptionModel> createdModels = planStatusOptionService.createPlanStatusOptions(planStatusOptionModels);
        List<PlanStatusOptionDto> createdPlanStatusDtos = new ArrayList<>();
        for (PlanStatusOptionModel model: createdModels){
            createdPlanStatusDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Plan Status Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", createdPlanStatusDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a Plan Status Option by its ID, excluding soft-deleted options.
     * @param id The ID of the Plan Status Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Plan Status Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("PlanStatusOptionId") Long id){
        PlanStatusOptionModel model = planStatusOptionService.readOne(id);
        PlanStatusOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Plan Status Options.
     * @return ResponseEntity containing a Map with a list of PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Plan Status Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<PlanStatusOptionModel> planStatusOptionModels = planStatusOptionService.readAll();
        List<PlanStatusOptionDto> planStatusOptionDtos = new ArrayList<>();
        for (PlanStatusOptionModel planStatusOptionModel: planStatusOptionModels){
            planStatusOptionDtos.add(convertToDto(planStatusOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Plan Status Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", planStatusOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Plan Status Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Plan Status Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<PlanStatusOptionModel> models = planStatusOptionService.hardReadAll();
        List<PlanStatusOptionDto> dtos = new ArrayList<>();
        for (PlanStatusOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Plan Status Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple Plan Status Options by their IDs, excluding soft-deleted records.
     * @param ids List of Plan Status Option IDs
     * @return ResponseEntity containing a Map with a list of PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Plan Status Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<PlanStatusOptionModel> planStatusOptionModels = planStatusOptionService.readMany(ids);
        List<PlanStatusOptionDto> planStatusOptionDtos = new ArrayList<>();
        for (PlanStatusOptionModel model: planStatusOptionModels){
            planStatusOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", planStatusOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Plan Status Option by its ID, excluding soft-deleted records.
     * @param id The ID of the Plan Status Option to update
     * @param planStatusOptionDto The updated Plan Status Option data
     * @return ResponseEntity containing a Map with the updated PlanStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Plan Status Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody PlanStatusOptionDto planStatusOptionDto){
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionService.updateOne(id, convertToModel(planStatusOptionDto));
        PlanStatusOptionDto dto = convertToDto(planStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Plan Status Options based on the provided list of Plan Status Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param planStatusOptionDtos List of PlanStatusOptionDto objects containing updated Plan Status Option data
     * @return ResponseEntity containing a Map with the list of updated PlanStatusOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple Plan Status Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<PlanStatusOptionDto> planStatusOptionDtos){
        List<PlanStatusOptionModel> inputModels = new ArrayList<>();
        for (PlanStatusOptionDto dto: planStatusOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<PlanStatusOptionModel> updatedModels = planStatusOptionService.updateMany(inputModels);
        List<PlanStatusOptionDto> dtos = new ArrayList<>();
        for (PlanStatusOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Plan Status Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Plan Status Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the Plan Status Option to update.
     * @param planStatusOptionDto The updated Plan Status Option data.
     * @return ResponseEntity containing a Map with the updated PlanStatusOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update Plan Status Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody PlanStatusOptionDto planStatusOptionDto){
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionService.hardUpdateOne(id, convertToModel(planStatusOptionDto));
        PlanStatusOptionDto dto = convertToDto(planStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", dto);
        response.put("ResponseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Plan Status Options, including soft-deleted records, based on their IDs.
     *
     * @param planStatusOptionDtos The list of updated Plan Status Option data.
     * @return ResponseEntity containing a Map with the list of updated PlanStatusOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all Plan Status Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<PlanStatusOptionDto> planStatusOptionDtos){
        List<PlanStatusOptionModel> inputModels = new ArrayList<>();
        for (PlanStatusOptionDto dto: planStatusOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<PlanStatusOptionModel> updatedModels = planStatusOptionService.hardUpdateAll(inputModels);
        List<PlanStatusOptionDto> dtos = new ArrayList<>();
        for (PlanStatusOptionModel planStatusOptionModel: updatedModels){
            dtos.add(convertToDto(planStatusOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single Plan Status Option by ID
     * @param id ID of the Plan Status Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted PlanStatusOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single Plan Status Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeletePlanStatusOption(@RequestParam Long id){
        PlanStatusOptionModel deletedPlanStatusOptionModel = planStatusOptionService.softDeletePlanStatusOption(id);
        PlanStatusOptionDto deletedPlanStatusOptionDto = convertToDto(deletedPlanStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Option", deletedPlanStatusOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Plan Status Option by ID
     * @param id ID of the Plan Status Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single Plan Status Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeletePlanStatusOption(@RequestParam Long id){
        planStatusOptionService.hardDeletePlanStatusOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Plan Status Options by IDs
     * @param ids List of Plan Status Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted PlanStatusOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple Plan Status Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeletePlanStatusOptions(@RequestBody List<Long> ids){
        List<PlanStatusOptionModel> deletedPlanStatusOptionModels = planStatusOptionService.softDeletePlanStatusOptions(ids);
        List<PlanStatusOptionDto> deletedPlanStatusOptionDtos = new ArrayList<>();
        for (PlanStatusOptionModel model: deletedPlanStatusOptionModels){
            deletedPlanStatusOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Plan Status Options", deletedPlanStatusOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple Plan Status Options by IDs
     * @param ids List of Plan Status Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple Plan Status Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeletePlanStatusOptions(@RequestBody List<Long> ids){
        planStatusOptionService.hardDeletePlanStatusOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options hard deleted successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}