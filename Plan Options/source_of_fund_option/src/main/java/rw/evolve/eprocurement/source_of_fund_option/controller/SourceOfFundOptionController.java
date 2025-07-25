/**
 * REST API controller for managing Source of Fund options.
 * Handles CRUD operations for Source of Fund option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.source_of_fund_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.source_of_fund_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.source_of_fund_option.dto.SourceOfFundOptionDto;
import rw.evolve.eprocurement.source_of_fund_option.model.SourceOfFundOptionModel;
import rw.evolve.eprocurement.source_of_fund_option.service.SourceOfFundOptionService;
import rw.evolve.eprocurement.source_of_fund_option.utils.SourceOfFundOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/source_of_fund_option")
@Tag(name = "Source of Fund Option API")
public class SourceOfFundOptionController {

    @Autowired
    private SourceOfFundOptionService sourceOfFundOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts SourceOfFundOptionModel to SourceOfFundOptionDto.
     * @param model - SourceOfFundOptionModel to convert
     * @return      - Converted SourceOfFundOptionDto
     */
    private SourceOfFundOptionDto convertToDto(SourceOfFundOptionModel model) {
        return modelMapper.map(model, SourceOfFundOptionDto.class);
    }

    /**
     * Converts SourceOfFundOptionDto to SourceOfFundOptionModel
     * @param sourceOfFundOptionDto - SourceOfFundOptionDto to convert
     * @return                      - Converted SourceOfFundOptionModel
     */
    private SourceOfFundOptionModel convertToModel(SourceOfFundOptionDto sourceOfFundOptionDto) {
        return modelMapper.map(sourceOfFundOptionDto, SourceOfFundOptionModel.class);
    }

    /**
     * Creates a single Source of Fund option with a generated ID.
     * @param sourceOfFundOptionDto - Source of Fund option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create a single Source of Fund option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody SourceOfFundOptionDto sourceOfFundOptionDto) {
        SourceOfFundOptionModel sourceOfFundOptionModel = convertToModel(sourceOfFundOptionDto);
        sourceOfFundOptionModel.setId(SourceOfFundOptionIdGenerator.generateId());
        sourceOfFundOptionService.save(sourceOfFundOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Source of fund option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Source of Fund options with generated IDs.
     * @param sourceOfFundOptionDtoList - List of Source of Fund option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create multiple Source of Fund options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<SourceOfFundOptionDto> sourceOfFundOptionDtoList) {
        List<SourceOfFundOptionModel> sourceOfFundOptionModelList = new ArrayList<>();
        for (SourceOfFundOptionDto sourceOfFundOptionDto : sourceOfFundOptionDtoList) {
            SourceOfFundOptionModel model = convertToModel(sourceOfFundOptionDto);
            model.setId(SourceOfFundOptionIdGenerator.generateId());
            sourceOfFundOptionModelList.add(model);
        }
        sourceOfFundOptionService.saveMany(sourceOfFundOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Source of fund options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Source of Fund option by ID (excludes soft-deleted).
     * @param id - Source of Fund option ID
     * @return   - Response with Source of Fund option data
     */
    @Operation(summary = "Get a single Source of Fund option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        SourceOfFundOptionModel model = sourceOfFundOptionService.readOne(id);
        SourceOfFundOptionDto sourceOfFundOptionDto = convertToDto(model);
        return new ResponseEntity<>(sourceOfFundOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Source of Fund options.
     * @return - Response with list of Source of Fund option data
     */
    @Operation(summary = "Get all available Source of Fund options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<SourceOfFundOptionModel> sourceOfFundOptionModelList = sourceOfFundOptionService.readAll();
        List<SourceOfFundOptionDto> sourceOfFundOptionDtoList = new ArrayList<>();
        for (SourceOfFundOptionModel sourceOfFundOptionModel : sourceOfFundOptionModelList) {
            sourceOfFundOptionDtoList.add(convertToDto(sourceOfFundOptionModel));
        }
        return new ResponseEntity<>(sourceOfFundOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Source of Fund options, including soft-deleted.
     * @return - Response with list of all Source of Fund option data
     */
    @Operation(summary = "Get all Source of Fund options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<SourceOfFundOptionModel> modelList = sourceOfFundOptionService.hardReadAll();
        List<SourceOfFundOptionDto> sourceOfFundOptionDtoList = new ArrayList<>();
        for (SourceOfFundOptionModel model : modelList) {
            sourceOfFundOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(sourceOfFundOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Source of Fund options by ID (excludes soft-deleted).
     * @param idList - List of Source of Fund option IDs
     * @return       - Response with list of Source of Fund option data
     */
    @Operation(summary = "Get multiple Source of Fund options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<SourceOfFundOptionModel> sourceOfFundOptionModelList = sourceOfFundOptionService.readMany(idList);
        List<SourceOfFundOptionDto> sourceOfFundOptionDtoList = new ArrayList<>();
        for (SourceOfFundOptionModel model : sourceOfFundOptionModelList) {
            sourceOfFundOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(sourceOfFundOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Source of Fund option by ID (excludes soft-deleted).
     * @param sourceOfFundOptionDto - Updated Source of Fund option data
     * @return                      - Response with updated Source of Fund option data
     */
    @Operation(summary = "Update a single Source of Fund option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody SourceOfFundOptionDto sourceOfFundOptionDto) {
        String modelId = sourceOfFundOptionDto.getId();
        SourceOfFundOptionModel savedModel = sourceOfFundOptionService.readOne(modelId);
        savedModel.setName(sourceOfFundOptionDto.getName());
        savedModel.setDescription(sourceOfFundOptionDto.getDescription());
        sourceOfFundOptionService.updateOne(savedModel);
        SourceOfFundOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Source of Fund options (excludes soft-deleted).
     * @param sourceOfFundOptionDtoList - List of updated Source of Fund option data
     * @return                          - Response with list of updated Source of Fund option data
     */
    @Operation(summary = "Update multiple Source of Fund options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<SourceOfFundOptionDto> sourceOfFundOptionDtoList) {
        List<SourceOfFundOptionModel> inputModelList = new ArrayList<>();
        for (SourceOfFundOptionDto sourceOfFundOptionDto : sourceOfFundOptionDtoList) {
            inputModelList.add(convertToModel(sourceOfFundOptionDto));
        }
        List<SourceOfFundOptionModel> updatedModelList = sourceOfFundOptionService.updateMany(inputModelList);
        List<SourceOfFundOptionDto> updatedDtoList = new ArrayList<>();
        for (SourceOfFundOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Source of Fund option by ID, including soft-deleted.
     * @param sourceOfFundOptionDto - Updated Source of Fund option data
     * @return                      - Response with updated Source of Fund option data
     */
    @Operation(summary = "Update a single Source of Fund option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody SourceOfFundOptionDto sourceOfFundOptionDto) {
        SourceOfFundOptionModel sourceOfFundOptionModel = sourceOfFundOptionService.hardUpdate(convertToModel(sourceOfFundOptionDto));
        SourceOfFundOptionDto updatedDto = convertToDto(sourceOfFundOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Source of Fund options, including soft-deleted.
     * @param sourceOfFundOptionDtoList - List of updated Source of Fund option data
     * @return                          - Response with list of updated Source of Fund option data
     */
    @Operation(summary = "Update all Source of Fund options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<SourceOfFundOptionDto> sourceOfFundOptionDtoList) {
        List<SourceOfFundOptionModel> inputModelList = new ArrayList<>();
        for (SourceOfFundOptionDto sourceOfFundOptionDto : sourceOfFundOptionDtoList) {
            inputModelList.add(convertToModel(sourceOfFundOptionDto));
        }
        List<SourceOfFundOptionModel> updatedModelList = sourceOfFundOptionService.hardUpdateAll(inputModelList);
        List<SourceOfFundOptionDto> updatedDtoList = new ArrayList<>();
        for (SourceOfFundOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Source of Fund option by ID.
     * @param id - Source of Fund option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Source of Fund option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        SourceOfFundOptionModel deletedModel = sourceOfFundOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Source of fund option soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Source of Fund option by ID.
     * @param id - Source of Fund option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Source of Fund option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        sourceOfFundOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Source of fund option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Source of Fund options by ID.
     * @param idList - List of Source of Fund option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Source of Fund options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<SourceOfFundOptionModel> deletedSourceOfFundOptionModelList = sourceOfFundOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Source of fund options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Source of Fund options by ID.
     * @param idList - List of Source of Fund option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Source of Fund options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        sourceOfFundOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Source of fund options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Source of Fund options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Source of Fund options")
    @GetMapping("/hard/delete-all")
    public ResponseEntity<Object> hardDeleteAll() {
        sourceOfFundOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Source of fund options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}