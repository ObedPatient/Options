/**
 * REST API controller for managing Scheme options.
 * Handles CRUD operations for Scheme option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.schemes_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.schemes_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.schemes_option.dto.SchemeOptionDto;
import rw.evolve.eprocurement.schemes_option.model.SchemeOptionModel;
import rw.evolve.eprocurement.schemes_option.service.SchemeOptionService;
import rw.evolve.eprocurement.schemes_option.utils.SchemeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/scheme_option")
@Tag(name = "Scheme Option API")
public class SchemeOptionController {

    @Autowired
    private SchemeOptionService schemeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts SchemeOptionModel to SchemeOptionDto.
     * @param model - SchemeOptionModel to convert
     * @return      - Converted SchemeOptionDto
     */
    private SchemeOptionDto convertToDto(SchemeOptionModel model) {
        return modelMapper.map(model, SchemeOptionDto.class);
    }

    /**
     * Converts SchemeOptionDto to SchemeOptionModel.
     * @param    - dto SchemeOptionDto to convert
     * @return   - Converted SchemeOptionModel
     */
    private SchemeOptionModel convertToModel(SchemeOptionDto dto) {
        return modelMapper.map(dto, SchemeOptionModel.class);
    }

    /**
     * Creates a single Scheme option with a generated ID.
     * @param schemeOptionDto - Scheme option data
     * @return                - Response with success message
     */
    @Operation(summary = "Create a single Scheme option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody SchemeOptionDto schemeOptionDto) {
        SchemeOptionModel schemeOptionModel = convertToModel(schemeOptionDto);
        schemeOptionModel.setId(SchemeOptionIdGenerator.generateId());
        schemeOptionService.save(schemeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Scheme options with generated IDList.
     * @param schemeOptionDtoList - List of Scheme option data
     * @return                    - Response with success message
     */
    @Operation(summary = "Create multiple Scheme options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtoList) {
        List<SchemeOptionModel> schemeOptionModels = new ArrayList<>();
        for (SchemeOptionDto dto : schemeOptionDtoList) {
            SchemeOptionModel model = convertToModel(dto);
            model.setId(SchemeOptionIdGenerator.generateId());
            schemeOptionModels.add(model);
        }
        schemeOptionService.saveMany(schemeOptionModels);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Scheme option by ID (excludes soft-deleted).
     * @param id - Scheme option ID
     * @return   - Response with Scheme option data
     */
    @Operation(summary = "Get a single Scheme option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam String id) {
        SchemeOptionModel model = schemeOptionService.readOne(id);
        SchemeOptionDto schemeOptionDto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option retrieved successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(schemeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Scheme options.
     * @return  - Response with list of Scheme option data
     */
    @Operation(summary = "Get all non-deleted Scheme options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<SchemeOptionModel> schemeOptionModels = schemeOptionService.readAll();
        List<SchemeOptionDto> schemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel schemeOptionModel : schemeOptionModels) {
            schemeOptionDtoList.add(convertToDto(schemeOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme options retrieved successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(schemeOptionDtoList, HttpStatus.OK);

    }

    /**
     * Retrieves all Scheme options, including soft-deleted.
     * @return        - Response with list of all Scheme option data
     */
    @Operation(summary = "Get all Scheme options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<SchemeOptionModel> models = schemeOptionService.hardReadAll();
        List<SchemeOptionDto> schemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel model : models) {
            schemeOptionDtoList.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Scheme options retrieved successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(schemeOptionDtoList, HttpStatus.OK);

    }

    /**
     * Retrieves multiple Scheme options by IDs (excludes soft-deleted).
     * @param idList - List of Scheme option IDs
     * @return       - Response with list of Scheme option data
     */
    @Operation(summary = "Get multiple Scheme options by IDs")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestBody List<String> idList) {
        List<SchemeOptionModel> schemeOptionModels = schemeOptionService.readMany(idList);
        List<SchemeOptionDto> schemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel model : schemeOptionModels) {
            schemeOptionDtoList.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme options retrieved successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(schemeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Scheme option by ID (excludes soft-deleted).
     *
     * @param schemeOptionDto - Updated Scheme option data
     * @return                - Response with updated Scheme option data
     */
    @Operation(summary = "Update a single Scheme option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody SchemeOptionDto schemeOptionDto){
        String modelId = schemeOptionDto.getId();
        SchemeOptionModel savedModel = schemeOptionService.readOne(modelId);
        savedModel.setName(schemeOptionDto.getName());
        savedModel.setDescription(schemeOptionDto.getDescription());
        schemeOptionService.updateOne(savedModel);
        SchemeOptionDto schemeOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(schemeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple Scheme options (excludes soft-deleted).
     * @param schemeOptionDtoList - a List of updated Scheme option data
     * @return Response with list - of updated Scheme option data
     */
    @Operation(summary = "Update multiple Scheme options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtoList) {
        List<SchemeOptionModel> inputModels = new ArrayList<>();
        for (SchemeOptionDto dto : schemeOptionDtoList) {
            inputModels.add(convertToModel(dto));
        }
        List<SchemeOptionModel> updatedModels = schemeOptionService.updateMany(inputModels);
        List<SchemeOptionDto> dtoList = new ArrayList<>();
        for (SchemeOptionModel model : updatedModels) {
            dtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * Updates a Scheme option by ID, including soft-deleted.
     *
     * @param schemeOptionDto - Updated Scheme option data
     * @return Response with  - updated Scheme option data
     */
    @Operation(summary = "Update a single Scheme option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody SchemeOptionDto schemeOptionDto) {
        SchemeOptionModel schemeOptionModel = schemeOptionService.hardUpdate(convertToModel(schemeOptionDto));
        SchemeOptionDto schemeOptionDto1 = convertToDto(schemeOptionModel);
        return new ResponseEntity<>(schemeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all Scheme options, including soft-deleted.
     * @param schemeOptionDtoList  - List of updated Scheme option data
     * @return                     - Response with list of updated Scheme option data
     */
    @Operation(summary = "Update all Scheme options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtoList) {
        List<SchemeOptionModel> inputModels = new ArrayList<>();
        for (SchemeOptionDto dto : schemeOptionDtoList) {
            inputModels.add(convertToModel(dto));
        }
        List<SchemeOptionModel> updatedModels = schemeOptionService.hardUpdateAll(inputModels);
        List<SchemeOptionDto> dtoList = new ArrayList<>();
        for (SchemeOptionModel model : updatedModels) {
            dtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Scheme option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Scheme option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        SchemeOptionModel deleteSchemeOptionModel = schemeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type Soft Deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Scheme option by ID.
     * @param id       - Scheme option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single Scheme option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        schemeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Scheme options by IDs.
     * @param idList    - List of Scheme option IDs
     * @return          - Response with list of soft-deleted Scheme option data
     */
    @Operation(summary = "Soft delete multiple Scheme options by IDs")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestBody List<String> idList) {
        List<SchemeOptionModel> deletedSchemeOptionModels = schemeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Scheme options by IDs.
     * @param idList   - List of Scheme option IDs
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple Scheme options by IDs")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestBody List<String> idList) {
        schemeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Scheme options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
    /**
     * Hard deletes all Scheme options, including soft-deleted.
     * @return Response with success message
     */
    @Operation(summary = "Hard delete all Scheme options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        schemeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Scheme options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}