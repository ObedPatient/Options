/**
 * REST API controller for managing OrganizationRole options.
 * Handles CRUD operations for OrganizationRole option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.organization_role_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.organization_role_option.dto.OrganizationRoleOptionDto;
import rw.evolve.eprocurement.organization_role_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.organization_role_option.model.OrganizationRoleOptionModel;
import rw.evolve.eprocurement.organization_role_option.service.OrganizationRoleOptionService;
import rw.evolve.eprocurement.organization_role_option.utils.OrganizationRoleOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/organization_role_option")
@Tag(name = "Organization Role Option API")
public class OrganizationRoleOptionController {

    private final OrganizationRoleOptionService organizationRoleOptionService;

    private final ModelMapper modelMapper;

    public OrganizationRoleOptionController(
            OrganizationRoleOptionService organizationRoleOptionService,
            ModelMapper modelMapper
    ){
        this.organizationRoleOptionService = organizationRoleOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts OrganizationRoleOptionModel to OrganizationRoleOptionDto.
     * @param model - OrganizationRoleOptionModel to convert
     * @return      - Converted OrganizationRoleOptionDto
     */
    private OrganizationRoleOptionDto convertToDto(OrganizationRoleOptionModel model) {
        return modelMapper.map(model, OrganizationRoleOptionDto.class);
    }

    /**
     * Converts OrganizationRoleOptionDto to OrganizationRoleOptionModel.
     * @param organizationRoleOptionDto - OrganizationRoleOptionDto to convert
     * @return                          - Converted OrganizationRoleOptionModel
     */
    private OrganizationRoleOptionModel convertToModel(OrganizationRoleOptionDto organizationRoleOptionDto) {
        return modelMapper.map(organizationRoleOptionDto, OrganizationRoleOptionModel.class);
    }

    /**
     * Creates a single OrganizationRole option with a generated ID.
     * @param organizationRoleOptionDto - OrganizationRole option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create a single OrganizationRole option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody OrganizationRoleOptionDto organizationRoleOptionDto) {
        OrganizationRoleOptionModel organizationRoleOptionModel = convertToModel(organizationRoleOptionDto);
        organizationRoleOptionModel.setId(OrganizationRoleOptionIdGenerator.generateId());
        organizationRoleOptionService.save(organizationRoleOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "OrganizationRole option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple OrganizationRole options with generated IDs.
     * @param organizationRoleOptionDtoList - List of OrganizationRole option data
     * @return                              - Response with success message
     */
    @Operation(summary = "Create multiple OrganizationRole options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<OrganizationRoleOptionDto> organizationRoleOptionDtoList) {
        List<OrganizationRoleOptionModel> organizationRoleOptionModelList = new ArrayList<>();
        for (OrganizationRoleOptionDto organizationRoleOptionDto : organizationRoleOptionDtoList) {
            OrganizationRoleOptionModel model = convertToModel(organizationRoleOptionDto);
            model.setId(OrganizationRoleOptionIdGenerator.generateId());
            organizationRoleOptionModelList.add(model);
        }
        organizationRoleOptionService.saveMany(organizationRoleOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "OrganizationRole options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves an OrganizationRole option by ID (excludes soft-deleted).
     * @param id - OrganizationRole option ID
     * @return   - Response with OrganizationRole option data
     */
    @Operation(summary = "Get a single OrganizationRole option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        OrganizationRoleOptionModel model = organizationRoleOptionService.readOne(id);
        OrganizationRoleOptionDto organizationRoleOptionDto = convertToDto(model);
        return new ResponseEntity<>(organizationRoleOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted OrganizationRole options.
     * @return - Response with list of OrganizationRole option data
     */
    @Operation(summary = "Get all available OrganizationRole options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<OrganizationRoleOptionModel> organizationRoleOptionModelList = organizationRoleOptionService.readAll();
        List<OrganizationRoleOptionDto> organizationRoleOptionDtoList = new ArrayList<>();
        for (OrganizationRoleOptionModel organizationRoleOptionModel : organizationRoleOptionModelList) {
            organizationRoleOptionDtoList.add(convertToDto(organizationRoleOptionModel));
        }
        return new ResponseEntity<>(organizationRoleOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all OrganizationRole options, including soft-deleted.
     * @return - Response with list of all OrganizationRole option data
     */
    @Operation(summary = "Get all OrganizationRole options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<OrganizationRoleOptionModel> modelList = organizationRoleOptionService.hardReadAll();
        List<OrganizationRoleOptionDto> organizationRoleOptionDtoList = new ArrayList<>();
        for (OrganizationRoleOptionModel model : modelList) {
            organizationRoleOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(organizationRoleOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple OrganizationRole options by ID (excludes soft-deleted).
     * @param idList - List of OrganizationRole option IDs
     * @return       - Response with list of OrganizationRole option data
     */
    @Operation(summary = "Get multiple OrganizationRole options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<OrganizationRoleOptionModel> organizationRoleOptionModelList = organizationRoleOptionService.readMany(idList);
        List<OrganizationRoleOptionDto> organizationRoleOptionDtoList = new ArrayList<>();
        for (OrganizationRoleOptionModel model : organizationRoleOptionModelList) {
            organizationRoleOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(organizationRoleOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates an OrganizationRole option by ID (excludes soft-deleted).
     * @param organizationRoleOptionDto - Updated OrganizationRole option data
     * @return                          - Response with updated OrganizationRole option data
     */
    @Operation(summary = "Update a single OrganizationRole option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody OrganizationRoleOptionDto organizationRoleOptionDto) {
        String modelId = organizationRoleOptionDto.getId();
        OrganizationRoleOptionModel savedModel = organizationRoleOptionService.readOne(modelId);
        savedModel.setName(organizationRoleOptionDto.getName());
        savedModel.setDescription(organizationRoleOptionDto.getDescription());
        organizationRoleOptionService.updateOne(savedModel);
        OrganizationRoleOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple OrganizationRole options (excludes soft-deleted).
     * @param organizationRoleOptionDtoList - List of updated OrganizationRole option data
     * @return                              - Response with list of updated OrganizationRole option data
     */
    @Operation(summary = "Update multiple OrganizationRole options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<OrganizationRoleOptionDto> organizationRoleOptionDtoList) {
        List<OrganizationRoleOptionModel> inputModelList = new ArrayList<>();
        for (OrganizationRoleOptionDto organizationRoleOptionDto : organizationRoleOptionDtoList) {
            inputModelList.add(convertToModel(organizationRoleOptionDto));
        }
        List<OrganizationRoleOptionModel> updatedModelList = organizationRoleOptionService.updateMany(inputModelList);
        List<OrganizationRoleOptionDto> updatedDtoList = new ArrayList<>();
        for (OrganizationRoleOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates an OrganizationRole option by ID, including soft-deleted.
     * @param organizationRoleOptionDto - Updated OrganizationRole option data
     * @return                          - Response with updated OrganizationRole option data
     */
    @Operation(summary = "Update a single OrganizationRole option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody OrganizationRoleOptionDto organizationRoleOptionDto) {
        OrganizationRoleOptionModel organizationRoleOptionModel = organizationRoleOptionService.hardUpdate(convertToModel(organizationRoleOptionDto));
        OrganizationRoleOptionDto updatedDto = convertToDto(organizationRoleOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all OrganizationRole options, including soft-deleted.
     * @param organizationRoleOptionDtoList - List of updated OrganizationRole option data
     * @return                              - Response with list of updated OrganizationRole option data
     */
    @Operation(summary = "Update all OrganizationRole options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<OrganizationRoleOptionDto> organizationRoleOptionDtoList) {
        List<OrganizationRoleOptionModel> inputModelList = new ArrayList<>();
        for (OrganizationRoleOptionDto organizationRoleOptionDto : organizationRoleOptionDtoList) {
            inputModelList.add(convertToModel(organizationRoleOptionDto));
        }
        List<OrganizationRoleOptionModel> updatedModelList = organizationRoleOptionService.hardUpdateAll(inputModelList);
        List<OrganizationRoleOptionDto> updatedDtoList = new ArrayList<>();
        for (OrganizationRoleOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes an OrganizationRole option by ID.
     * @param id - OrganizationRole option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single OrganizationRole option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        OrganizationRoleOptionModel deletedModel = organizationRoleOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "OrganizationRole option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes an OrganizationRole option by ID.
     * @param id - OrganizationRole option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single OrganizationRole option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        organizationRoleOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "OrganizationRole option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple OrganizationRole options by ID.
     * @param idList - List of OrganizationRole option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple OrganizationRole options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<OrganizationRoleOptionModel> deletedModelList = organizationRoleOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "OrganizationRole options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple OrganizationRole options by ID.
     * @param idList - List of OrganizationRole option ID
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple OrganizationRole options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        organizationRoleOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "OrganizationRole options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all OrganizationRole options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all OrganizationRole options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        organizationRoleOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All OrganizationRole options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}