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
     * @param schemeOptionDto   - dto SchemeOptionDto to convert
     * @return                  - Converted SchemeOptionModel
     */
    private SchemeOptionModel convertToModel(SchemeOptionDto schemeOptionDto) {
        return modelMapper.map(schemeOptionDto, SchemeOptionModel.class);
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
        List<SchemeOptionModel> schemeOptionModelList = new ArrayList<>();
        for (SchemeOptionDto schemeOptionDto : schemeOptionDtoList) {
            SchemeOptionModel model = convertToModel(schemeOptionDto);
            model.setId(SchemeOptionIdGenerator.generateId());
            schemeOptionModelList.add(model);
        }
        schemeOptionService.saveMany(schemeOptionModelList);
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
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        SchemeOptionModel model = schemeOptionService.readOne(id);
        SchemeOptionDto schemeOptionDto = convertToDto(model);
        return new ResponseEntity<>(schemeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Scheme options.
     * @return  - Response with list of Scheme option data
     */
    @Operation(summary = "Get all available Scheme options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<SchemeOptionModel> schemeOptionModelList = schemeOptionService.readAll();
        List<SchemeOptionDto> schemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel schemeOptionModel : schemeOptionModelList) {
            schemeOptionDtoList.add(convertToDto(schemeOptionModel));
        }
        return new ResponseEntity<>(schemeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Scheme options, including soft-deleted.
     * @return        - Response with list of all Scheme option data
     */
    @Operation(summary = "Get all Scheme options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<SchemeOptionModel> modelList = schemeOptionService.hardReadAll();
        List<SchemeOptionDto> schemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel model : modelList) {
            schemeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(schemeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Scheme options by ID (excludes soft-deleted).
     * @param idList - List of Scheme option ID
     * @return       - Response with list of Scheme option data
     */
    @Operation(summary = "Get multiple Scheme options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<SchemeOptionModel> schemeOptionModelList = schemeOptionService.readMany(idList);
        List<SchemeOptionDto> schemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel model : schemeOptionModelList) {
            schemeOptionDtoList.add(convertToDto(model));
        }
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
     * @param schemeOptionDtoList     - a List of updated Scheme option data
     * @return Response with list     - of updated Scheme option data
     */
    @Operation(summary = "Update multiple Scheme options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtoList) {
        List<SchemeOptionModel> inputModelList = new ArrayList<>();
        for (SchemeOptionDto schemeOptionDto : schemeOptionDtoList) {
            inputModelList.add(convertToModel(schemeOptionDto));
        }
        List<SchemeOptionModel> updatedModelList = schemeOptionService.updateMany(inputModelList);
        List<SchemeOptionDto> schemeOptionDtoArrayList = new ArrayList<>();
        for (SchemeOptionModel model : updatedModelList) {
            schemeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(schemeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a Scheme option by ID, including soft-deleted.
     *
     * @param schemeOptionDto   - Updated Scheme option data
     * @return Response with    - updated Scheme option data
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
     * @param schemeOptionDtoList   - List of updated Scheme option data
     * @return                      - Response with list of updated Scheme option data
     */
    @Operation(summary = "Update all Scheme options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtoList) {
        List<SchemeOptionModel> inputModelList = new ArrayList<>();
        for (SchemeOptionDto schemeOptionDto : schemeOptionDtoList) {
            inputModelList.add(convertToModel(schemeOptionDto));
        }
        List<SchemeOptionModel> updatedModelList = schemeOptionService.hardUpdateAll(inputModelList);
        List<SchemeOptionDto> updatedSchemeOptionDtoList = new ArrayList<>();
        for (SchemeOptionModel model : updatedModelList) {
            updatedSchemeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedSchemeOptionDtoList, HttpStatus.OK);
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
                "Scheme option Soft Deleted successfully",
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
     * Soft deletes multiple Scheme options by ID.
     * @param idList    - List of Scheme option IDList
     * @return          - Response with list of soft-deleted Scheme option data
     */
    @Operation(summary = "Soft delete multiple Scheme options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<SchemeOptionModel> deletedSchemeOptionModelList = schemeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Scheme options by ID.
     * @param idList   - List of Scheme option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple Scheme options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        schemeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Scheme options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
    /**
     * Hard deletes all Scheme options, including soft-deleted.
     * @return          - Response with success message
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