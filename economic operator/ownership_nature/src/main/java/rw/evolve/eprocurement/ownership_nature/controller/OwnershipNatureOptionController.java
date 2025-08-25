/**
 * REST API controller for managing Ownership Nature options.
 * Handles CRUD operations for Ownership Nature option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.ownership_nature.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.ownership_nature.dto.OwnershipNatureOptionDto;
import rw.evolve.eprocurement.ownership_nature.dto.ResponseMessageDto;
import rw.evolve.eprocurement.ownership_nature.model.OwnershipNatureOptionModel;
import rw.evolve.eprocurement.ownership_nature.service.OwnershipNatureOptionService;
import rw.evolve.eprocurement.ownership_nature.utils.OwnershipNatureOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/ownership_nature_option")
@Tag(name = "Ownership Nature Option API")
public class OwnershipNatureOptionController {

    private final OwnershipNatureOptionService ownershipNatureOptionService;

    private  ModelMapper modelMapper = new ModelMapper();

    public OwnershipNatureOptionController(
            OwnershipNatureOptionService ownershipNatureOptionService,
            ModelMapper modelMapper
    ){
        this.ownershipNatureOptionService = ownershipNatureOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts OwnershipNatureOptionModel to OwnershipNatureOptionDto.
     * @param model - OwnershipNatureOptionModel to convert
     * @return      - Converted OwnershipNatureOptionDto
     */
    private OwnershipNatureOptionDto convertToDto(OwnershipNatureOptionModel model) {
        return modelMapper.map(model, OwnershipNatureOptionDto.class);
    }

    /**
     * Converts OwnershipNatureOptionDto to OwnershipNatureOptionModel.
     * @param ownershipNatureOptionDto - OwnershipNatureOptionDto to convert
     * @return                         - Converted OwnershipNatureOptionModel
     */
    private OwnershipNatureOptionModel convertToModel(OwnershipNatureOptionDto ownershipNatureOptionDto) {
        return modelMapper.map(ownershipNatureOptionDto, OwnershipNatureOptionModel.class);
    }

    /**
     * Creates a single Ownership Nature option with a generated ID.
     * @param ownershipNatureOptionDto - Ownership Nature option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create a single Ownership Nature option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody OwnershipNatureOptionDto ownershipNatureOptionDto) {
        OwnershipNatureOptionModel ownershipNatureOptionModel = convertToModel(ownershipNatureOptionDto);
        ownershipNatureOptionModel.setId(OwnershipNatureOptionIdGenerator.generateId());
        ownershipNatureOptionService.save(ownershipNatureOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Ownership nature option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Ownership Nature options with generated IDs.
     * @param ownershipNatureOptionDtoList - List of Ownership Nature option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create multiple Ownership Nature options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<OwnershipNatureOptionDto> ownershipNatureOptionDtoList) {
        List<OwnershipNatureOptionModel> ownershipNatureOptionModelList = new ArrayList<>();
        for (OwnershipNatureOptionDto ownershipNatureOptionDto : ownershipNatureOptionDtoList) {
            OwnershipNatureOptionModel model = convertToModel(ownershipNatureOptionDto);
            model.setId(OwnershipNatureOptionIdGenerator.generateId());
            ownershipNatureOptionModelList.add(model);
        }
        ownershipNatureOptionService.saveMany(ownershipNatureOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Ownership nature options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Ownership Nature option by ID (excludes soft-deleted).
     * @param id - Ownership Nature option ID
     * @return   - Response with Ownership Nature option data
     */
    @Operation(summary = "Get a single Ownership Nature option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        OwnershipNatureOptionModel model = ownershipNatureOptionService.readOne(id);
        OwnershipNatureOptionDto ownershipNatureOptionDto = convertToDto(model);
        return new ResponseEntity<>(ownershipNatureOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Ownership Nature options.
     * @return - Response with list of Ownership Nature option data
     */
    @Operation(summary = "Get all available Ownership Nature options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<OwnershipNatureOptionModel> ownershipNatureOptionModelList = ownershipNatureOptionService.readAll();
        List<OwnershipNatureOptionDto> ownershipNatureOptionDtoList = new ArrayList<>();
        for (OwnershipNatureOptionModel ownershipNatureOptionModel : ownershipNatureOptionModelList) {
            ownershipNatureOptionDtoList.add(convertToDto(ownershipNatureOptionModel));
        }
        return new ResponseEntity<>(ownershipNatureOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Ownership Nature options, including soft-deleted.
     * @return - Response with list of all Ownership Nature option data
     */
    @Operation(summary = "Get all Ownership Nature options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<OwnershipNatureOptionModel> modelList = ownershipNatureOptionService.hardReadAll();
        List<OwnershipNatureOptionDto> ownershipNatureOptionDtoList = new ArrayList<>();
        for (OwnershipNatureOptionModel model : modelList) {
            ownershipNatureOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(ownershipNatureOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Ownership Nature options by ID (excludes soft-deleted).
     * @param idList - List of Ownership Nature option IDs
     * @return       - Response with list of Ownership Nature option data
     */
    @Operation(summary = "Get multiple Ownership Nature options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<OwnershipNatureOptionModel> ownershipNatureOptionModelList = ownershipNatureOptionService.readMany(idList);
        List<OwnershipNatureOptionDto> ownershipNatureOptionDtoList = new ArrayList<>();
        for (OwnershipNatureOptionModel model : ownershipNatureOptionModelList) {
            ownershipNatureOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(ownershipNatureOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Ownership Nature option by ID (excludes soft-deleted).
     * @param ownershipNatureOptionDto - Updated Ownership Nature option data
     * @return                         - Response with updated Ownership Nature option data
     */
    @Operation(summary = "Update a single Ownership Nature option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody OwnershipNatureOptionDto ownershipNatureOptionDto) {
        String modelId = ownershipNatureOptionDto.getId();
        OwnershipNatureOptionModel savedModel = ownershipNatureOptionService.readOne(modelId);
        savedModel.setName(ownershipNatureOptionDto.getName());
        savedModel.setDescription(ownershipNatureOptionDto.getDescription());
        ownershipNatureOptionService.updateOne(savedModel);
        OwnershipNatureOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Ownership Nature options (excludes soft-deleted).
     * @param ownershipNatureOptionDtoList - List of updated Ownership Nature option data
     * @return                             - Response with list of updated Ownership Nature option data
     */
    @Operation(summary = "Update multiple Ownership Nature options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<OwnershipNatureOptionDto> ownershipNatureOptionDtoList) {
        List<OwnershipNatureOptionModel> inputModelList = new ArrayList<>();
        for (OwnershipNatureOptionDto ownershipNatureOptionDto : ownershipNatureOptionDtoList) {
            inputModelList.add(convertToModel(ownershipNatureOptionDto));
        }
        List<OwnershipNatureOptionModel> updatedModelList = ownershipNatureOptionService.updateMany(inputModelList);
        List<OwnershipNatureOptionDto> updatedDtoList = new ArrayList<>();
        for (OwnershipNatureOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Ownership Nature option by ID, including soft-deleted.
     * @param ownershipNatureOptionDto - Updated Ownership Nature option data
     * @return                         - Response with updated Ownership Nature option data
     */
    @Operation(summary = "Update a single Ownership Nature option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody OwnershipNatureOptionDto ownershipNatureOptionDto) {
        OwnershipNatureOptionModel ownershipNatureOptionModel = ownershipNatureOptionService.hardUpdate(convertToModel(ownershipNatureOptionDto));
        OwnershipNatureOptionDto updatedDto = convertToDto(ownershipNatureOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Ownership Nature options, including soft-deleted.
     * @param ownershipNatureOptionDtoList - List of updated Ownership Nature option data
     * @return                             - Response with list of updated Ownership Nature option data
     */
    @Operation(summary = "Update all Ownership Nature options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<OwnershipNatureOptionDto> ownershipNatureOptionDtoList) {
        List<OwnershipNatureOptionModel> inputModelList = new ArrayList<>();
        for (OwnershipNatureOptionDto ownershipNatureOptionDto : ownershipNatureOptionDtoList) {
            inputModelList.add(convertToModel(ownershipNatureOptionDto));
        }
        List<OwnershipNatureOptionModel> updatedModelList = ownershipNatureOptionService.hardUpdateAll(inputModelList);
        List<OwnershipNatureOptionDto> updatedDtoList = new ArrayList<>();
        for (OwnershipNatureOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Ownership Nature option by ID.
     * @param id - Ownership Nature option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Ownership Nature option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        OwnershipNatureOptionModel deletedModel = ownershipNatureOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Ownership nature option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Ownership Nature option by ID.
     * @param id - Ownership Nature option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Ownership Nature option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        ownershipNatureOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Ownership nature option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Ownership Nature options by ID.
     * @param idList - List of Ownership Nature option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Ownership Nature options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<OwnershipNatureOptionModel> deletedModelList = ownershipNatureOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Ownership nature options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Ownership Nature options by ID.
     * @param idList - List of Ownership Nature option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Ownership Nature options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        ownershipNatureOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Ownership nature options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Ownership Nature options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Ownership Nature options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        ownershipNatureOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Ownership nature options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}