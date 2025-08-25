/**
 * REST API controller for managing Business Type options.
 * Handles CRUD operations for Business Type option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.business_type_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.business_type_option.dto.BusinessTypeOptionDto;
import rw.evolve.eprocurement.business_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.business_type_option.model.BusinessTypeOptionModel;
import rw.evolve.eprocurement.business_type_option.service.BusinessTypeOptionService;
import rw.evolve.eprocurement.business_type_option.utils.BusinessTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/business_type_option")
@Tag(name = "Business Type Option API")
public class BusinessTypeOptionController {

    private final BusinessTypeOptionService businessTypeOptionService;

    private final ModelMapper modelMapper;

    public BusinessTypeOptionController(
            BusinessTypeOptionService businessTypeOptionService,
            ModelMapper modelMapper
    ){
        this.businessTypeOptionService = businessTypeOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts BusinessTypeOptionModel to BusinessTypeOptionDto.
     * @param model - BusinessTypeOptionModel to convert
     * @return      - Converted BusinessTypeOptionDto
     */
    private BusinessTypeOptionDto convertToDto(BusinessTypeOptionModel model) {
        return modelMapper.map(model, BusinessTypeOptionDto.class);
    }

    /**
     * Converts BusinessTypeOptionDto to BusinessTypeOptionModel.
     * @param businessTypeOptionDto - BusinessTypeOptionDto to convert
     * @return                      - Converted BusinessTypeOptionModel
     */
    private BusinessTypeOptionModel convertToModel(BusinessTypeOptionDto businessTypeOptionDto) {
        return modelMapper.map(businessTypeOptionDto, BusinessTypeOptionModel.class);
    }

    /**
     * Creates a single Business Type option with a generated ID.
     * @param businessTypeOptionDto - Business Type option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create a single Business Type option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody BusinessTypeOptionDto businessTypeOptionDto) {
        BusinessTypeOptionModel businessTypeOptionModel = convertToModel(businessTypeOptionDto);
        businessTypeOptionModel.setId(BusinessTypeOptionIdGenerator.generateId());
        businessTypeOptionService.save(businessTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business type option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Business Type options with generated IDs.
     * @param businessTypeOptionDtoList - List of Business Type option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create multiple Business Type options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<BusinessTypeOptionDto> businessTypeOptionDtoList) {
        List<BusinessTypeOptionModel> businessTypeOptionModelList = new ArrayList<>();
        for (BusinessTypeOptionDto businessTypeOptionDto : businessTypeOptionDtoList) {
            BusinessTypeOptionModel model = convertToModel(businessTypeOptionDto);
            model.setId(BusinessTypeOptionIdGenerator.generateId());
            businessTypeOptionModelList.add(model);
        }
        businessTypeOptionService.saveMany(businessTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business type options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Business Type option by ID (excludes soft-deleted).
     * @param id - Business Type option ID
     * @return   - Response with Business Type option data
     */
    @Operation(summary = "Get a single Business Type option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        BusinessTypeOptionModel model = businessTypeOptionService.readOne(id);
        BusinessTypeOptionDto businessTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(businessTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Business Type options.
     * @return - Response with list of Business Type option data
     */
    @Operation(summary = "Get all available Business Type options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<BusinessTypeOptionModel> businessTypeOptionModelList = businessTypeOptionService.readAll();
        List<BusinessTypeOptionDto> businessTypeOptionDtoList = new ArrayList<>();
        for (BusinessTypeOptionModel businessTypeOptionModel : businessTypeOptionModelList) {
            businessTypeOptionDtoList.add(convertToDto(businessTypeOptionModel));
        }
        return new ResponseEntity<>(businessTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Business Type options, including soft-deleted.
     * @return - Response with list of all Business Type option data
     */
    @Operation(summary = "Get all Business Type options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<BusinessTypeOptionModel> modelList = businessTypeOptionService.hardReadAll();
        List<BusinessTypeOptionDto> businessTypeOptionDtoList = new ArrayList<>();
        for (BusinessTypeOptionModel model : modelList) {
            businessTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(businessTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Business Type options by ID (excludes soft-deleted).
     * @param idList - List of Business Type option IDs
     * @return       - Response with list of Business Type option data
     */
    @Operation(summary = "Get multiple Business Type options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<BusinessTypeOptionModel> businessTypeOptionModelList = businessTypeOptionService.readMany(idList);
        List<BusinessTypeOptionDto> businessTypeOptionDtoList = new ArrayList<>();
        for (BusinessTypeOptionModel model : businessTypeOptionModelList) {
            businessTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(businessTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Business Type option by ID (excludes soft-deleted).
     * @param businessTypeOptionDto - Updated Business Type option data
     * @return                      - Response with updated Business Type option data
     */
    @Operation(summary = "Update a single Business Type option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody BusinessTypeOptionDto businessTypeOptionDto) {
        String modelId = businessTypeOptionDto.getId();
        BusinessTypeOptionModel savedModel = businessTypeOptionService.readOne(modelId);
        savedModel.setName(businessTypeOptionDto.getName());
        savedModel.setDescription(businessTypeOptionDto.getDescription());
        businessTypeOptionService.updateOne(savedModel);
        BusinessTypeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Business Type options (excludes soft-deleted).
     * @param businessTypeOptionDtoList - List of updated Business Type option data
     * @return                          - Response with list of updated Business Type option data
     */
    @Operation(summary = "Update multiple Business Type options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<BusinessTypeOptionDto> businessTypeOptionDtoList) {
        List<BusinessTypeOptionModel> inputModelList = new ArrayList<>();
        for (BusinessTypeOptionDto businessTypeOptionDto : businessTypeOptionDtoList) {
            inputModelList.add(convertToModel(businessTypeOptionDto));
        }
        List<BusinessTypeOptionModel> updatedModelList = businessTypeOptionService.updateMany(inputModelList);
        List<BusinessTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (BusinessTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Business Type option by ID, including soft-deleted.
     * @param businessTypeOptionDto - Updated Business Type option data
     * @return                      - Response with updated Business Type option data
     */
    @Operation(summary = "Update a single Business Type option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody BusinessTypeOptionDto businessTypeOptionDto) {
        BusinessTypeOptionModel businessTypeOptionModel = businessTypeOptionService.hardUpdate(convertToModel(businessTypeOptionDto));
        BusinessTypeOptionDto updatedDto = convertToDto(businessTypeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Business Type options, including soft-deleted.
     * @param businessTypeOptionDtoList - List of updated Business Type option data
     * @return                          - Response with list of updated Business Type option data
     */
    @Operation(summary = "Update all Business Type options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<BusinessTypeOptionDto> businessTypeOptionDtoList) {
        List<BusinessTypeOptionModel> inputModelList = new ArrayList<>();
        for (BusinessTypeOptionDto businessTypeOptionDto : businessTypeOptionDtoList) {
            inputModelList.add(convertToModel(businessTypeOptionDto));
        }
        List<BusinessTypeOptionModel> updatedModelList = businessTypeOptionService.hardUpdateAll(inputModelList);
        List<BusinessTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (BusinessTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Business Type option by ID.
     * @param id - Business Type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Business Type option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        BusinessTypeOptionModel deletedModel = businessTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business type option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Business Type option by ID.
     * @param id - Business Type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Business Type option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        businessTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business type option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Business Type options by ID.
     * @param idList - List of Business Type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Business Type options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<BusinessTypeOptionModel> deletedModelList = businessTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business type options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Business Type options by ID.
     * @param idList - List of Business Type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Business Type options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        businessTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Business type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Business Type options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Business Type options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        businessTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Business type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}