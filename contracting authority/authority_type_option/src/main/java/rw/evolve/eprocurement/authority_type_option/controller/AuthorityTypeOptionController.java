/**
 * REST API controller for managing Authority Type options.
 * Handles CRUD operations for Authority Type option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.authority_type_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.authority_type_option.dto.AuthorityTypeOptionDto;
import rw.evolve.eprocurement.authority_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.authority_type_option.model.AuthorityTypeOptionModel;
import rw.evolve.eprocurement.authority_type_option.service.AuthorityTypeOptionService;
import rw.evolve.eprocurement.authority_type_option.utils.AuthorityTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/authority_type_option")
@Tag(name = "Authority Type Option API")
public class AuthorityTypeOptionController {

    private final AuthorityTypeOptionService authorityTypeOptionService;

    private final ModelMapper modelMapper;

    public AuthorityTypeOptionController(
            AuthorityTypeOptionService authorityTypeOptionService,
            ModelMapper modelMapper
    ){
        this.authorityTypeOptionService = authorityTypeOptionService;
        this.modelMapper = modelMapper;
    }
    /**
     * Converts AuthorityTypeOptionModel to AuthorityTypeOptionDto.
     * @param model - AuthorityTypeOptionModel to convert
     * @return      - Converted AuthorityTypeOptionDto
     */
    private AuthorityTypeOptionDto convertToDto(AuthorityTypeOptionModel model) {
        return modelMapper.map(model, AuthorityTypeOptionDto.class);
    }

    /**
     * Converts AuthorityTypeOptionDto to AuthorityTypeOptionModel.
     * @param authorityTypeOptionDto - AuthorityTypeOptionDto to convert
     * @return                       - Converted AuthorityTypeOptionModel
     */
    private AuthorityTypeOptionModel convertToModel(AuthorityTypeOptionDto authorityTypeOptionDto) {
        return modelMapper.map(authorityTypeOptionDto, AuthorityTypeOptionModel.class);
    }

    /**
     * Creates a single Authority Type option with a generated ID.
     * @param authorityTypeOptionDto - Authority Type option data
     * @return                       - Response with success message
     */
    @Operation(summary = "Create a single Authority Type option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody AuthorityTypeOptionDto authorityTypeOptionDto) {
        AuthorityTypeOptionModel authorityTypeOptionModel = convertToModel(authorityTypeOptionDto);
        authorityTypeOptionModel.setId(AuthorityTypeOptionIdGenerator.generateId());
        authorityTypeOptionService.save(authorityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Authority type option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Authority Type option with generated ID.
     * @param authorityTypeOptionDtoList - List of Authority Type option data
     * @return                           - Response with success message
     */
    @Operation(summary = "Create multiple Authority Type options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<AuthorityTypeOptionDto> authorityTypeOptionDtoList) {
        List<AuthorityTypeOptionModel> authorityTypeOptionModelList = new ArrayList<>();
        for (AuthorityTypeOptionDto authorityTypeOptionDto : authorityTypeOptionDtoList) {
            AuthorityTypeOptionModel model = convertToModel(authorityTypeOptionDto);
            model.setId(AuthorityTypeOptionIdGenerator.generateId());
            authorityTypeOptionModelList.add(model);
        }
        authorityTypeOptionService.saveMany(authorityTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Authority type options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves an Authority Type option by ID (excludes soft-deleted).
     * @param id - Authority Type option ID
     * @return   - Response with Authority Type option data
     */
    @Operation(summary = "Get a single Authority Type option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        AuthorityTypeOptionModel model = authorityTypeOptionService.readOne(id);
        AuthorityTypeOptionDto authorityTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(authorityTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Authority Type options.
     * @return - Response with list of Authority Type option data
     */
    @Operation(summary = "Get all available Authority Type options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<AuthorityTypeOptionModel> authorityTypeOptionModelList = authorityTypeOptionService.readAll();
        List<AuthorityTypeOptionDto> authorityTypeOptionDtoList = new ArrayList<>();
        for (AuthorityTypeOptionModel authorityTypeOptionModel : authorityTypeOptionModelList) {
            authorityTypeOptionDtoList.add(convertToDto(authorityTypeOptionModel));
        }
        return new ResponseEntity<>(authorityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Authority Type options, including soft-deleted.
     * @return - Response with list of all Authority Type option data
     */
    @Operation(summary = "Get all Authority Type options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<AuthorityTypeOptionModel> modelList = authorityTypeOptionService.hardReadAll();
        List<AuthorityTypeOptionDto> authorityTypeOptionDtoList = new ArrayList<>();
        for (AuthorityTypeOptionModel model : modelList) {
            authorityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(authorityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Authority Type option by ID (excludes soft-deleted).
     * @param idList - List of Authority Type option ID
     * @return       - Response with list of Authority Type option data
     */
    @Operation(summary = "Get multiple Authority Type options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<AuthorityTypeOptionModel> authorityTypeOptionModelList = authorityTypeOptionService.readMany(idList);
        List<AuthorityTypeOptionDto> authorityTypeOptionDtoList = new ArrayList<>();
        for (AuthorityTypeOptionModel model : authorityTypeOptionModelList) {
            authorityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(authorityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Authority Type option by ID (excludes soft-deleted).
     * @param authorityTypeOptionDto - Updated Authority Type option data
     * @return                       - Response with updated Authority Type option data
     */
    @Operation(summary = "Update a single Authority Type option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody AuthorityTypeOptionDto authorityTypeOptionDto) {
        String modelId = authorityTypeOptionDto.getId();
        AuthorityTypeOptionModel savedModel = authorityTypeOptionService.readOne(modelId);
        savedModel.setName(authorityTypeOptionDto.getName());
        savedModel.setDescription(authorityTypeOptionDto.getDescription());
        authorityTypeOptionService.updateOne(savedModel);
        AuthorityTypeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Authority Type options (excludes soft-deleted).
     * @param authorityTypeOptionDtoList - List of updated Authority Type option data
     * @return                           - Response with list of updated Authority Type option data
     */
    @Operation(summary = "Update multiple Authority Type options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<AuthorityTypeOptionDto> authorityTypeOptionDtoList) {
        List<AuthorityTypeOptionModel> inputModelList = new ArrayList<>();
        for (AuthorityTypeOptionDto authorityTypeOptionDto : authorityTypeOptionDtoList) {
            inputModelList.add(convertToModel(authorityTypeOptionDto));
        }
        List<AuthorityTypeOptionModel> updatedModelList = authorityTypeOptionService.updateMany(inputModelList);
        List<AuthorityTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (AuthorityTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Authority Type option by ID, including soft-deleted.
     * @param authorityTypeOptionDto - Updated Authority Type option data
     * @return                       - Response with updated Authority Type option data
     */
    @Operation(summary = "Update a single Authority Type option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody AuthorityTypeOptionDto authorityTypeOptionDto) {
        AuthorityTypeOptionModel authorityTypeOptionModel = authorityTypeOptionService.hardUpdate(convertToModel(authorityTypeOptionDto));
        AuthorityTypeOptionDto updatedDto = convertToDto(authorityTypeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Authority Type options, including soft-deleted.
     * @param authorityTypeOptionDtoList - List of updated Authority Type option data
     * @return                           - Response with list of updated Authority Type option data
     */
    @Operation(summary = "Update all Authority Type options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<AuthorityTypeOptionDto> authorityTypeOptionDtoList) {
        List<AuthorityTypeOptionModel> inputModelList = new ArrayList<>();
        for (AuthorityTypeOptionDto authorityTypeOptionDto : authorityTypeOptionDtoList) {
            inputModelList.add(convertToModel(authorityTypeOptionDto));
        }
        List<AuthorityTypeOptionModel> updatedModelList = authorityTypeOptionService.hardUpdateAll(inputModelList);
        List<AuthorityTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (AuthorityTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes an Authority Type option by ID.
     * @param id - Authority Type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Authority Type option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        AuthorityTypeOptionModel deletedModel = authorityTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Authority type option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes an Authority Type option by ID.
     * @param id - Authority Type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Authority Type option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        authorityTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Authority type option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Authority Type options by ID.
     * @param idList - List of Authority Type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Authority Type options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<AuthorityTypeOptionModel> deletedModelList = authorityTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Authority type options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Authority Type options by ID.
     * @param idList - List of Authority Type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Authority Type options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        authorityTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Authority type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Authority Type options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Authority Type options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        authorityTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Authority type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}