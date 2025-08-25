/**
 * REST API controller for managing Workspace type options.
 * Handles CRUD operations for Workspace type option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.workspace_type_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.workspace_type_option.dto.WorkspaceTypeOptionDto;
import rw.evolve.eprocurement.workspace_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.workspace_type_option.model.WorkspaceTypeOptionModel;
import rw.evolve.eprocurement.workspace_type_option.service.WorkspaceTypeOptionService;
import rw.evolve.eprocurement.workspace_type_option.utils.WorkspaceTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/workspace_type_option")
@Tag(name = "Workspace Type Option API")
public class WorkspaceTypeOptionController {

    private final WorkspaceTypeOptionService workspaceTypeOptionService;

    private final ModelMapper modelMapper;

    public WorkspaceTypeOptionController(
            WorkspaceTypeOptionService workspaceTypeOptionService,
            ModelMapper modelMapper
    ){
        this.workspaceTypeOptionService = workspaceTypeOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts WorkspaceTypeOptionModel to WorkspaceTypeOptionDto.
     * @param model - WorkspaceTypeOptionModel to convert
     * @return      - Converted WorkspaceTypeOptionDto
     */
    private WorkspaceTypeOptionDto convertToDto(WorkspaceTypeOptionModel model) {
        return modelMapper.map(model, WorkspaceTypeOptionDto.class);
    }

    /**
     * Converts WorkspaceTypeOptionDto to WorkspaceTypeOptionModel.
     * @param workspaceTypeOptionDto - WorkspaceTypeOptionDto to convert
     * @return                       - Converted WorkspaceTypeOptionModel
     */
    private WorkspaceTypeOptionModel convertToModel(WorkspaceTypeOptionDto workspaceTypeOptionDto) {
        return modelMapper.map(workspaceTypeOptionDto, WorkspaceTypeOptionModel.class);
    }

    /**
     * Creates a single WorkspaceType option with a generated ID.
     * @param workspaceTypeOptionDto - WorkspaceType option data
     * @return                       - Response with success message
     */
    @Operation(summary = "Create a single WorkspaceType option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody WorkspaceTypeOptionDto workspaceTypeOptionDto) {
        WorkspaceTypeOptionModel workspaceTypeOptionModel = convertToModel(workspaceTypeOptionDto);
        workspaceTypeOptionModel.setId(WorkspaceTypeOptionIdGenerator.generateId());
        workspaceTypeOptionService.save(workspaceTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "WorkspaceType option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple WorkspaceType options with generated IDs.
     * @param workspaceTypeOptionDtoList - List of WorkspaceType option data
     * @return                           - Response with success message
     */
    @Operation(summary = "Create multiple WorkspaceType options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<WorkspaceTypeOptionDto> workspaceTypeOptionDtoList) {
        List<WorkspaceTypeOptionModel> workspaceTypeOptionModelList = new ArrayList<>();
        for (WorkspaceTypeOptionDto workspaceTypeOptionDto : workspaceTypeOptionDtoList) {
            WorkspaceTypeOptionModel model = convertToModel(workspaceTypeOptionDto);
            model.setId(WorkspaceTypeOptionIdGenerator.generateId());
            workspaceTypeOptionModelList.add(model);
        }
        workspaceTypeOptionService.saveMany(workspaceTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "WorkspaceType options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a WorkspaceType option by ID (excludes soft-deleted).
     * @param id - WorkspaceType option ID
     * @return   - Response with WorkspaceType option data
     */
    @Operation(summary = "Get a single WorkspaceType option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        WorkspaceTypeOptionModel model = workspaceTypeOptionService.readOne(id);
        WorkspaceTypeOptionDto workspaceTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(workspaceTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted WorkspaceType options.
     * @return - Response with list of WorkspaceType option data
     */
    @Operation(summary = "Get all available WorkspaceType options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<WorkspaceTypeOptionModel> workspaceTypeOptionModelList = workspaceTypeOptionService.readAll();
        List<WorkspaceTypeOptionDto> workspaceTypeOptionDtoList = new ArrayList<>();
        for (WorkspaceTypeOptionModel workspaceTypeOptionModel : workspaceTypeOptionModelList) {
            workspaceTypeOptionDtoList.add(convertToDto(workspaceTypeOptionModel));
        }
        return new ResponseEntity<>(workspaceTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all WorkspaceType options, including soft-deleted.
     * @return - Response with list of all WorkspaceType option data
     */
    @Operation(summary = "Get all WorkspaceType options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<WorkspaceTypeOptionModel> modelList = workspaceTypeOptionService.hardReadAll();
        List<WorkspaceTypeOptionDto> workspaceTypeOptionDtoList = new ArrayList<>();
        for (WorkspaceTypeOptionModel model : modelList) {
            workspaceTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(workspaceTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple WorkspaceType options by ID (excludes soft-deleted).
     * @param idList - List of WorkspaceType option ID
     * @return       - Response with list of WorkspaceType option data
     */
    @Operation(summary = "Get multiple WorkspaceType options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<WorkspaceTypeOptionModel> workspaceTypeOptionModelList = workspaceTypeOptionService.readMany(idList);
        List<WorkspaceTypeOptionDto> workspaceTypeOptionDtoList = new ArrayList<>();
        for (WorkspaceTypeOptionModel model : workspaceTypeOptionModelList) {
            workspaceTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(workspaceTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a WorkspaceType option by ID (excludes soft-deleted).
     * @param workspaceTypeOptionDto - Updated WorkspaceType option data
     * @return                       - Response with updated WorkspaceType option data
     */
    @Operation(summary = "Update a single WorkspaceType option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody WorkspaceTypeOptionDto workspaceTypeOptionDto) {
        String modelId = workspaceTypeOptionDto.getId();
        WorkspaceTypeOptionModel savedModel = workspaceTypeOptionService.readOne(modelId);
        savedModel.setName(workspaceTypeOptionDto.getName());
        savedModel.setDescription(workspaceTypeOptionDto.getDescription());
        workspaceTypeOptionService.updateOne(savedModel);
        WorkspaceTypeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple WorkspaceType options (excludes soft-deleted).
     * @param workspaceTypeOptionDtoList - List of updated WorkspaceType option data
     * @return                           - Response with list of updated WorkspaceType option data
     */
    @Operation(summary = "Update multiple WorkspaceType options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<WorkspaceTypeOptionDto> workspaceTypeOptionDtoList) {
        List<WorkspaceTypeOptionModel> inputModelList = new ArrayList<>();
        for (WorkspaceTypeOptionDto workspaceTypeOptionDto : workspaceTypeOptionDtoList) {
            inputModelList.add(convertToModel(workspaceTypeOptionDto));
        }
        List<WorkspaceTypeOptionModel> updatedModelList = workspaceTypeOptionService.updateMany(inputModelList);
        List<WorkspaceTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (WorkspaceTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a WorkspaceType option by ID, including soft-deleted.
     * @param workspaceTypeOptionDto - Updated WorkspaceType option data
     * @return                       - Response with updated WorkspaceType option data
     */
    @Operation(summary = "Update a single WorkspaceType option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody WorkspaceTypeOptionDto workspaceTypeOptionDto) {
        WorkspaceTypeOptionModel workspaceTypeOptionModel = workspaceTypeOptionService.hardUpdate(convertToModel(workspaceTypeOptionDto));
        WorkspaceTypeOptionDto updatedDto = convertToDto(workspaceTypeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all WorkspaceType options, including soft-deleted.
     * @param workspaceTypeOptionDtoList - List of updated WorkspaceType option data
     * @return                           - Response with list of updated WorkspaceType option data
     */
    @Operation(summary = "Update all WorkspaceType option, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<WorkspaceTypeOptionDto> workspaceTypeOptionDtoList) {
        List<WorkspaceTypeOptionModel> inputModelList = new ArrayList<>();
        for (WorkspaceTypeOptionDto workspaceTypeOptionDto : workspaceTypeOptionDtoList) {
            inputModelList.add(convertToModel(workspaceTypeOptionDto));
        }
        List<WorkspaceTypeOptionModel> updatedModelList = workspaceTypeOptionService.hardUpdateAll(inputModelList);
        List<WorkspaceTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (WorkspaceTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a WorkspaceType option by ID.
     * @param id - WorkspaceType option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single WorkspaceType option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        WorkspaceTypeOptionModel deletedModel = workspaceTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "WorkspaceType option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a WorkspaceType option by ID.
     * @param id - WorkspaceType option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single WorkspaceType option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        workspaceTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "WorkspaceType option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple WorkspaceType options by ID.
     * @param idList - List of WorkspaceType option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple WorkspaceType options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<WorkspaceTypeOptionModel> deletedModelList = workspaceTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "WorkspaceType options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple WorkspaceType options by ID.
     * @param idList - List of WorkspaceType option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple WorkspaceType options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        workspaceTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All WorkspaceType options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all WorkspaceType options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all WorkspaceType options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        workspaceTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All WorkspaceType options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}