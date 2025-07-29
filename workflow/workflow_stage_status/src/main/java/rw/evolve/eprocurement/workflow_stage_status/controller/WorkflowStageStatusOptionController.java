/**
 * REST API controller for managing WorkflowStageStatus options.
 * Handles CRUD operations for WorkflowStageStatus option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.workflow_stage_status.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.workflow_stage_status.dto.ResponseMessageDto;
import rw.evolve.eprocurement.workflow_stage_status.dto.WorkflowStageStatusOptionDto;
import rw.evolve.eprocurement.workflow_stage_status.model.WorkflowStageStatusOptionModel;
import rw.evolve.eprocurement.workflow_stage_status.service.WorkflowStageStatusOptionService;
import rw.evolve.eprocurement.workflow_stage_status.utils.WorkflowStageStatusOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/workflow_stage_status_option")
@Tag(name = "Workflow Stage Status Option API")
public class WorkflowStageStatusOptionController {

    @Autowired
    private WorkflowStageStatusOptionService workflowStageStatusOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts WorkflowStageStatusOptionModel to WorkflowStageStatusOptionDto.
     * @param model - WorkflowStageStatusOptionModel to convert
     * @return      - Converted WorkflowStageStatusOptionDto
     */
    private WorkflowStageStatusOptionDto convertToDto(WorkflowStageStatusOptionModel model) {
        return modelMapper.map(model, WorkflowStageStatusOptionDto.class);
    }

    /**
     * Converts WorkflowStageStatusOptionDto to WorkflowStageStatusOptionModel.
     * @param workflowStageStatusOptionDto - WorkflowStageStatusOptionDto to convert
     * @return                             - Converted WorkflowStageStatusOptionModel
     */
    private WorkflowStageStatusOptionModel convertToModel(WorkflowStageStatusOptionDto workflowStageStatusOptionDto) {
        return modelMapper.map(workflowStageStatusOptionDto, WorkflowStageStatusOptionModel.class);
    }

    /**
     * Creates a single WorkflowStageStatus option with a generated ID.
     * @param workflowStageStatusOptionDto - WorkflowStageStatus option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create a single WorkflowStageStatus option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody WorkflowStageStatusOptionDto workflowStageStatusOptionDto) {
        WorkflowStageStatusOptionModel workflowStageStatusOptionModel = convertToModel(workflowStageStatusOptionDto);
        workflowStageStatusOptionModel.setId(WorkflowStageStatusOptionIdGenerator.generateId());
        workflowStageStatusOptionService.save(workflowStageStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Workflow stage status option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple WorkflowStageStatus options with generated IDList.
     * @param workflowStageStatusOptionDtoList - List of WorkflowStageStatus option data
     * @return                                 - Response with success message
     */
    @Operation(summary = "Create multiple WorkflowStageStatus options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoList) {
        List<WorkflowStageStatusOptionModel> workflowStageStatusOptionModelList = new ArrayList<>();
        for (WorkflowStageStatusOptionDto workflowStageStatusOptionDto : workflowStageStatusOptionDtoList) {
            WorkflowStageStatusOptionModel model = convertToModel(workflowStageStatusOptionDto);
            model.setId(WorkflowStageStatusOptionIdGenerator.generateId());
            workflowStageStatusOptionModelList.add(model);
        }
        workflowStageStatusOptionService.saveMany(workflowStageStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Workflow stage status options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a WorkflowStageStatus option by ID (excludes soft-deleted).
     * @param id - WorkflowStageStatus option ID
     * @return   - Response with WorkflowStageStatus option data
     */
    @Operation(summary = "Get a single WorkflowStageStatus option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        WorkflowStageStatusOptionModel model = workflowStageStatusOptionService.readOne(id);
        WorkflowStageStatusOptionDto workflowStageStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(workflowStageStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted WorkflowStageStatus options.
     * @return  - Response with list of WorkflowStageStatus option data
     */
    @Operation(summary = "Get all available WorkflowStageStatus options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<WorkflowStageStatusOptionModel> workflowStageStatusOptionModelList = workflowStageStatusOptionService.readAll();
        List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoList = new ArrayList<>();
        for (WorkflowStageStatusOptionModel workflowStageStatusOptionModel : workflowStageStatusOptionModelList) {
            workflowStageStatusOptionDtoList.add(convertToDto(workflowStageStatusOptionModel));
        }
        return new ResponseEntity<>(workflowStageStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all WorkflowStageStatus options, including soft-deleted.
     * @return        - Response with list of all WorkflowStageStatus option data
     */
    @Operation(summary = "Get all WorkflowStageStatus options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<WorkflowStageStatusOptionModel> modelList = workflowStageStatusOptionService.hardReadAll();
        List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoList = new ArrayList<>();
        for (WorkflowStageStatusOptionModel model : modelList) {
            workflowStageStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(workflowStageStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple WorkflowStageStatus options by ID (excludes soft-deleted).
     * @param idList - List of WorkflowStageStatus option ID
     * @return       - Response with list of WorkflowStageStatus option data
     */
    @Operation(summary = "Get multiple WorkflowStageStatus options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<WorkflowStageStatusOptionModel> workflowStageStatusOptionModelList = workflowStageStatusOptionService.readMany(idList);
        List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoList = new ArrayList<>();
        for (WorkflowStageStatusOptionModel model : workflowStageStatusOptionModelList) {
            workflowStageStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(workflowStageStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a WorkflowStageStatus option by ID (excludes soft-deleted).
     *
     * @param workflowStageStatusOptionDto - Updated WorkflowStageStatus option data
     * @return                             - Response with updated WorkflowStageStatus option data
     */
    @Operation(summary = "Update a single WorkflowStageStatus option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody WorkflowStageStatusOptionDto workflowStageStatusOptionDto){
        String modelId = workflowStageStatusOptionDto.getId();
        WorkflowStageStatusOptionModel savedModel = workflowStageStatusOptionService.readOne(modelId);
        savedModel.setName(workflowStageStatusOptionDto.getName());
        savedModel.setDescription(workflowStageStatusOptionDto.getDescription());
        workflowStageStatusOptionService.updateOne(savedModel);
        WorkflowStageStatusOptionDto workflowStageStatusOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(workflowStageStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple WorkflowStageStatus options (excludes soft-deleted).
     * @param workflowStageStatusOptionDtoList - List of updated WorkflowStageStatus option data
     * @return                                 - Response with list of updated WorkflowStageStatus option data
     */
    @Operation(summary = "Update multiple WorkflowStageStatus options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoList) {
        List<WorkflowStageStatusOptionModel> inputModelList = new ArrayList<>();
        for (WorkflowStageStatusOptionDto workflowStageStatusOptionDto : workflowStageStatusOptionDtoList) {
            inputModelList.add(convertToModel(workflowStageStatusOptionDto));
        }
        List<WorkflowStageStatusOptionModel> updatedModelList = workflowStageStatusOptionService.updateMany(inputModelList);
        List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoArrayList = new ArrayList<>();
        for (WorkflowStageStatusOptionModel model : updatedModelList) {
            workflowStageStatusOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(workflowStageStatusOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a WorkflowStageStatus option by ID, including soft-deleted.
     *
     * @param workflowStageStatusOptionDto - Updated WorkflowStageStatus option data
     * @return                             - Response with updated WorkflowStageStatus option data
     */
    @Operation(summary = "Update a single WorkflowStageStatus option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody WorkflowStageStatusOptionDto workflowStageStatusOptionDto) {
        WorkflowStageStatusOptionModel workflowStageStatusOptionModel = workflowStageStatusOptionService.hardUpdate(convertToModel(workflowStageStatusOptionDto));
        WorkflowStageStatusOptionDto workflowStageStatusOptionDto1 = convertToDto(workflowStageStatusOptionModel);
        return new ResponseEntity<>(workflowStageStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all WorkflowStageStatus options, including soft-deleted.
     * @param workflowStageStatusOptionDtoList - List of updated WorkflowStageStatus option data
     * @return                                 - Response with list of updated WorkflowStageStatus option data
     */
    @Operation(summary = "Update all WorkflowStageStatus options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<WorkflowStageStatusOptionDto> workflowStageStatusOptionDtoList) {
        List<WorkflowStageStatusOptionModel> inputModelList = new ArrayList<>();
        for (WorkflowStageStatusOptionDto workflowStageStatusOptionDto : workflowStageStatusOptionDtoList) {
            inputModelList.add(convertToModel(workflowStageStatusOptionDto));
        }
        List<WorkflowStageStatusOptionModel> updatedModelList = workflowStageStatusOptionService.hardUpdateAll(inputModelList);
        List<WorkflowStageStatusOptionDto> updatedWorkflowStageStatusOptionDtoList = new ArrayList<>();
        for (WorkflowStageStatusOptionModel model : updatedModelList) {
            updatedWorkflowStageStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedWorkflowStageStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a WorkflowStageStatus option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single WorkflowStageStatus option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        WorkflowStageStatusOptionModel deleteWorkflowStageStatusOptionModel = workflowStageStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Workflow stage status option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a WorkflowStageStatus option by ID.
     * @param id       - WorkflowStageStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single WorkflowStageStatus option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        workflowStageStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Workflow stage status option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple WorkflowStageStatus options by ID.
     * @param idList    - List of WorkflowStageStatus option IDList
     * @return          - Response with list of soft-deleted WorkflowStageStatus option data
     */
    @Operation(summary = "Soft delete multiple WorkflowStageStatus options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<WorkflowStageStatusOptionModel> deletedWorkflowStageStatusOptionModelList = workflowStageStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Workflow stage status options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple WorkflowStageStatus options by ID.
     * @param idList   - List of WorkflowStageStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple WorkflowStageStatus options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        workflowStageStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Workflow stage status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all WorkflowStageStatus options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all WorkflowStageStatus options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        workflowStageStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Workflow stage status options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}