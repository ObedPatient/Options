/**
 * REST API controller for managing Log level options.
 * Handles CRUD operations for Log level option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.log_level.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.log_level.dto.LogLevelOptionDto;
import rw.evolve.eprocurement.log_level.dto.ResponseMessageDto;
import rw.evolve.eprocurement.log_level.model.LogLevelOptionModel;
import rw.evolve.eprocurement.log_level.service.LogLevelOptionService;
import rw.evolve.eprocurement.log_level.utils.LogLevelOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/log_level_option")
@Tag(name = "Log Level Option API")
public class LogLevelOptionController {

    @Autowired
    private LogLevelOptionService logLevelOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts LogLevelOptionModel to LogLevelOptionDto.
     * @param model - LogLevelOptionModel to convert
     * @return      - Converted LogLevelOptionDto
     */
    private LogLevelOptionDto convertToDto(LogLevelOptionModel model) {
        return modelMapper.map(model, LogLevelOptionDto.class);
    }

    /**
     * Converts LogLevelOptionDto to LogLevelOptionModel.
     * @param logLevelOptionDto - LogLevelOptionDto to convert
     * @return                  - Converted LogLevelOptionModel
     */
    private LogLevelOptionModel convertToModel(LogLevelOptionDto logLevelOptionDto) {
        return modelMapper.map(logLevelOptionDto, LogLevelOptionModel.class);
    }

    /**
     * Creates a single Log level option with a generated ID.
     * @param logLevelOptionDto - Log level option data
     * @return                  - Response with success message
     */
    @Operation(summary = "Create a single Log level option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody LogLevelOptionDto logLevelOptionDto) {
        LogLevelOptionModel logLevelOptionModel = convertToModel(logLevelOptionDto);
        logLevelOptionModel.setId(LogLevelOptionIdGenerator.generateId());
        logLevelOptionService.save(logLevelOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Log level option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Log level options with generated IDs.
     * @param logLevelOptionDtoList - List of Log level option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create multiple Log level options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<LogLevelOptionDto> logLevelOptionDtoList) {
        List<LogLevelOptionModel> logLevelOptionModelList = new ArrayList<>();
        for (LogLevelOptionDto logLevelOptionDto : logLevelOptionDtoList) {
            LogLevelOptionModel model = convertToModel(logLevelOptionDto);
            model.setId(LogLevelOptionIdGenerator.generateId());
            logLevelOptionModelList.add(model);
        }
        logLevelOptionService.saveMany(logLevelOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Log level options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Log level option by ID (excludes soft-deleted).
     * @param id - Log level option ID
     * @return   - Response with Log level option data
     */
    @Operation(summary = "Get a single Log level option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        LogLevelOptionModel model = logLevelOptionService.readOne(id);
        LogLevelOptionDto logLevelOptionDto = convertToDto(model);
        return new ResponseEntity<>(logLevelOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Log level options.
     * @return - Response with list of Log level option data
     */
    @Operation(summary = "Get all available Log level options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<LogLevelOptionModel> logLevelOptionModelList = logLevelOptionService.readAll();
        List<LogLevelOptionDto> logLevelOptionDtoList = new ArrayList<>();
        for (LogLevelOptionModel logLevelOptionModel : logLevelOptionModelList) {
            logLevelOptionDtoList.add(convertToDto(logLevelOptionModel));
        }
        return new ResponseEntity<>(logLevelOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Log level options, including soft-deleted.
     * @return - Response with list of all Log level option data
     */
    @Operation(summary = "Get all Log level options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<LogLevelOptionModel> modelList = logLevelOptionService.hardReadAll();
        List<LogLevelOptionDto> logLevelOptionDtoList = new ArrayList<>();
        for (LogLevelOptionModel model : modelList) {
            logLevelOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(logLevelOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Log level options by ID (excludes soft-deleted).
     * @param idList - List of Log level option IDs
     * @return       - Response with list of Log level option data
     */
    @Operation(summary = "Get multiple Log level options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<LogLevelOptionModel> logLevelOptionModelList = logLevelOptionService.readMany(idList);
        List<LogLevelOptionDto> logLevelOptionDtoList = new ArrayList<>();
        for (LogLevelOptionModel model : logLevelOptionModelList) {
            logLevelOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(logLevelOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Log level option by ID (excludes soft-deleted).
     * @param logLevelOptionDto - Updated Log level option data
     * @return                  - Response with updated Log level option data
     */
    @Operation(summary = "Update a single Log level option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody LogLevelOptionDto logLevelOptionDto) {
        String modelId = logLevelOptionDto.getId();
        LogLevelOptionModel savedModel = logLevelOptionService.readOne(modelId);
        savedModel.setName(logLevelOptionDto.getName());
        savedModel.setDescription(logLevelOptionDto.getDescription());
        logLevelOptionService.updateOne(savedModel);
        LogLevelOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Log level options (excludes soft-deleted).
     * @param logLevelOptionDtoList - List of updated Log level option data
     * @return                      - Response with list of updated Log level option data
     */
    @Operation(summary = "Update multiple Log level options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<LogLevelOptionDto> logLevelOptionDtoList) {
        List<LogLevelOptionModel> inputModelList = new ArrayList<>();
        for (LogLevelOptionDto logLevelOptionDto : logLevelOptionDtoList) {
            inputModelList.add(convertToModel(logLevelOptionDto));
        }
        List<LogLevelOptionModel> updatedModelList = logLevelOptionService.updateMany(inputModelList);
        List<LogLevelOptionDto> updatedDtoList = new ArrayList<>();
        for (LogLevelOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Log level option by ID, including soft-deleted.
     * @param logLevelOptionDto - Updated Log level option data
     * @return                  - Response with updated Log level option data
     */
    @Operation(summary = "Update a single Log level option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody LogLevelOptionDto logLevelOptionDto) {
        LogLevelOptionModel logLevelOptionModel = logLevelOptionService.hardUpdate(convertToModel(logLevelOptionDto));
        LogLevelOptionDto updatedDto = convertToDto(logLevelOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Log level options, including soft-deleted.
     * @param logLevelOptionDtoList - List of updated Log level option data
     * @return                      - Response with list of updated Log level option data
     */
    @Operation(summary = "Update all Log level options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<LogLevelOptionDto> logLevelOptionDtoList) {
        List<LogLevelOptionModel> inputModelList = new ArrayList<>();
        for (LogLevelOptionDto logLevelOptionDto : logLevelOptionDtoList) {
            inputModelList.add(convertToModel(logLevelOptionDto));
        }
        List<LogLevelOptionModel> updatedModelList = logLevelOptionService.hardUpdateAll(inputModelList);
        List<LogLevelOptionDto> updatedDtoList = new ArrayList<>();
        for (LogLevelOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Log level option by ID.
     * @param id - Log level option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Log level option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        LogLevelOptionModel deletedModel = logLevelOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Log level option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Log level option by ID.
     * @param id - Log level option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Log level option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        logLevelOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Log level option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Log level options by ID.
     * @param idList - List of Log level option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Log level options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<LogLevelOptionModel> deletedModelList = logLevelOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Log level options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Log level options by ID.
     * @param idList - List of Log level option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Log level options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        logLevelOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Log level options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Log level options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Log level options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        logLevelOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Log level options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}