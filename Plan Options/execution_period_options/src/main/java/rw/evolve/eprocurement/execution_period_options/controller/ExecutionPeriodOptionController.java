/**
 * REST API controller for managing Execution Period options.
 * Handles CRUD operations for Execution Period option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.execution_period_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.execution_period_options.dto.ExecutionPeriodOptionDto;
import rw.evolve.eprocurement.execution_period_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.execution_period_options.model.ExecutionPeriodOptionModel;
import rw.evolve.eprocurement.execution_period_options.service.ExecutionPeriodOptionService;
import rw.evolve.eprocurement.execution_period_options.utils.ExecutionPeriodOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/execution_period_option")
@Tag(name = "Execution Period Option API")
public class ExecutionPeriodOptionController {

    private final ExecutionPeriodOptionService executionPeriodOptionService;

    private final ModelMapper modelMapper;

    public ExecutionPeriodOptionController(
            ExecutionPeriodOptionService executionPeriodOptionService,
            ModelMapper modelMapper
    ){
        this.executionPeriodOptionService = executionPeriodOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts ExecutionPeriodOptionModel to ExecutionPeriodOptionDto.
     * @param model - ExecutionPeriodOptionModel to convert
     * @return      - Converted ExecutionPeriodOptionDto
     */
    private ExecutionPeriodOptionDto convertToDto(ExecutionPeriodOptionModel model) {
        return modelMapper.map(model, ExecutionPeriodOptionDto.class);
    }

    /**
     * Converts ExecutionPeriodOptionDto to ExecutionPeriodOptionModel.
     * @param executionPeriodOptionDto - ExecutionPeriodOptionDto to convert
     * @return                         - Converted ExecutionPeriodOptionModel
     */
    private ExecutionPeriodOptionModel convertToModel(ExecutionPeriodOptionDto executionPeriodOptionDto) {
        return modelMapper.map(executionPeriodOptionDto, ExecutionPeriodOptionModel.class);
    }

    /**
     * Creates a single Execution Period Option.
     * @param executionPeriodOptionDto - Execution Period Option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create a single Execution period option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ExecutionPeriodOptionDto executionPeriodOptionDto) {
        ExecutionPeriodOptionModel executionPeriodOptionModel = convertToModel(executionPeriodOptionDto);
        executionPeriodOptionModel.setId(ExecutionPeriodOptionIdGenerator.generateId());
        executionPeriodOptionService.save(executionPeriodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution period option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Execution Period Options.
     * @param executionPeriodOptionDtoList - List of Execution Period Option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create multiple execution period options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ExecutionPeriodOptionDto> executionPeriodOptionDtoList) {
        List<ExecutionPeriodOptionModel> executionPeriodOptionModelList = new ArrayList<>();
        for (ExecutionPeriodOptionDto executionPeriodOptionDto : executionPeriodOptionDtoList) {
            ExecutionPeriodOptionModel model = convertToModel(executionPeriodOptionDto);
            model.setId(ExecutionPeriodOptionIdGenerator.generateId());
            executionPeriodOptionModelList.add(model);
        }
        executionPeriodOptionService.saveMany(executionPeriodOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution period options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves an Execution Period Option by ID (excludes soft-deleted).
     * @param id - Execution Period Option ID
     * @return   - Response with Execution Period Option data
     */
    @Operation(summary = "Get a single Execution Period Option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ExecutionPeriodOptionModel model = executionPeriodOptionService.readOne(id);
        ExecutionPeriodOptionDto executionPeriodOptionDto = convertToDto(model);
        return new ResponseEntity<>(executionPeriodOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Execution Period Options.
     * @return - Response with list of Execution Period Option data
     */
    @Operation(summary = "Get all available Execution Period Options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ExecutionPeriodOptionModel> executionPeriodOptionModelList = executionPeriodOptionService.readAll();
        List<ExecutionPeriodOptionDto> executionPeriodOptionDtoList = new ArrayList<>();
        for (ExecutionPeriodOptionModel executionPeriodOptionModel : executionPeriodOptionModelList) {
            executionPeriodOptionDtoList.add(convertToDto(executionPeriodOptionModel));
        }
        return new ResponseEntity<>(executionPeriodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Execution Period Options, including soft-deleted.
     * @return - Response with list of all Execution Period Option data
     */
    @Operation(summary = "Get all Execution Period Options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ExecutionPeriodOptionModel> modelList = executionPeriodOptionService.hardReadAll();
        List<ExecutionPeriodOptionDto> executionPeriodOptionDtoList = new ArrayList<>();
        for (ExecutionPeriodOptionModel model : modelList) {
            executionPeriodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(executionPeriodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Execution Period Options by ID (excludes soft-deleted).
     * @param idList - List of Execution Period Option IDs
     * @return       - Response with list of Execution Period Option data
     */
    @Operation(summary = "Get multiple Execution Period Options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ExecutionPeriodOptionModel> executionPeriodOptionModelList = executionPeriodOptionService.readMany(idList);
        List<ExecutionPeriodOptionDto> executionPeriodOptionDtoList = new ArrayList<>();
        for (ExecutionPeriodOptionModel model : executionPeriodOptionModelList) {
            executionPeriodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(executionPeriodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Execution Period Option by ID (excludes soft-deleted).
     * @param executionPeriodOptionDto - Updated Execution Period Option data
     * @return                         - Response with updated Execution Period Option data
     */
    @Operation(summary = "Update a single Execution Period Option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ExecutionPeriodOptionDto executionPeriodOptionDto) {
        String modelId = executionPeriodOptionDto.getId();
        ExecutionPeriodOptionModel savedModel = executionPeriodOptionService.readOne(modelId);
        savedModel.setName(executionPeriodOptionDto.getName());
        savedModel.setDescription(executionPeriodOptionDto.getDescription());
        executionPeriodOptionService.updateOne(savedModel);
        ExecutionPeriodOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Execution Period Options (excludes soft-deleted).
     * @param executionPeriodOptionDtoList - List of updated Execution Period Option data
     * @return                             - Response with list of updated Execution Period Option data
     */
    @Operation(summary = "Update multiple Execution Period Options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ExecutionPeriodOptionDto> executionPeriodOptionDtoList) {
        List<ExecutionPeriodOptionModel> inputModelList = new ArrayList<>();
        for (ExecutionPeriodOptionDto executionPeriodOptionDto : executionPeriodOptionDtoList) {
            inputModelList.add(convertToModel(executionPeriodOptionDto));
        }
        List<ExecutionPeriodOptionModel> updatedModelList = executionPeriodOptionService.updateMany(inputModelList);
        List<ExecutionPeriodOptionDto> updatedDtoList = new ArrayList<>();
        for (ExecutionPeriodOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Execution Period Option by ID, including soft-deleted.
     * @param executionPeriodOptionDto - Updated Execution Period Option data
     * @return                         - Response with updated Execution Period Option data
     */
    @Operation(summary = "Update a single Execution Period Option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ExecutionPeriodOptionDto executionPeriodOptionDto) {
        ExecutionPeriodOptionModel executionPeriodOptionModel = executionPeriodOptionService.hardUpdate(convertToModel(executionPeriodOptionDto));
        ExecutionPeriodOptionDto updatedDto = convertToDto(executionPeriodOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Execution Period Options, including soft-deleted.
     * @param executionPeriodOptionDtoList - List of updated Execution Period Option data
     * @return                             - Response with list of updated Execution Period Option data
     */
    @Operation(summary = "Update all Execution Period Options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ExecutionPeriodOptionDto> executionPeriodOptionDtoList) {
        List<ExecutionPeriodOptionModel> inputModelList = new ArrayList<>();
        for (ExecutionPeriodOptionDto executionPeriodOptionDto : executionPeriodOptionDtoList) {
            inputModelList.add(convertToModel(executionPeriodOptionDto));
        }
        List<ExecutionPeriodOptionModel> updatedModelList = executionPeriodOptionService.hardUpdateAll(inputModelList);
        List<ExecutionPeriodOptionDto> updatedDtoList = new ArrayList<>();
        for (ExecutionPeriodOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes an Execution Period Option by ID.
     * @param id - Execution Period Option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Execution Period Option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ExecutionPeriodOptionModel deletedModel = executionPeriodOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution period option soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes an Execution Period Option by ID.
     * @param id - Execution Period Option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Execution Period Option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        executionPeriodOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution Period Option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Execution Period Options by ID.
     * @param idList - List of Execution Period Option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Execution Period Options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ExecutionPeriodOptionModel> deletedModelList = executionPeriodOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution period options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Execution Period Options by ID.
     * @param idList - List of Execution Period Option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Execution Period Options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        executionPeriodOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Execution period options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Execution Period Options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Execution Period Options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        executionPeriodOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Execution period options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}