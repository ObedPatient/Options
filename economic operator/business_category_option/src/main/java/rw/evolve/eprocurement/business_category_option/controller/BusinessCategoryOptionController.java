/**
 * REST API controller for managing Business Category options.
 * Handles CRUD operations for Business Category option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.business_category_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.business_category_option.dto.BusinessCategoryOptionDto;
import rw.evolve.eprocurement.business_category_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.business_category_option.model.BusinessCategoryOptionModel;
import rw.evolve.eprocurement.business_category_option.service.BusinessCategoryOptionService;
import rw.evolve.eprocurement.business_category_option.utils.BusinessCategoryOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/business_category_option")
@Tag(name = "Business Category Option API")
public class BusinessCategoryOptionController {

    private final BusinessCategoryOptionService businessCategoryOptionService;

    private final ModelMapper modelMapper;

    public BusinessCategoryOptionController(
            BusinessCategoryOptionService businessCategoryOptionService,
            ModelMapper modelMapper
    ){
        this.businessCategoryOptionService = businessCategoryOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts BusinessCategoryOptionModel to BusinessCategoryOptionDto.
     * @param model - BusinessCategoryOptionModel to convert
     * @return      - Converted BusinessCategoryOptionDto
     */
    private BusinessCategoryOptionDto convertToDto(BusinessCategoryOptionModel model) {
        return modelMapper.map(model, BusinessCategoryOptionDto.class);
    }

    /**
     * Converts BusinessCategoryOptionDto to BusinessCategoryOptionModel.
     * @param businessCategoryOptionDto - BusinessCategoryOptionDto to convert
     * @return                          - Converted BusinessCategoryOptionModel
     */
    private BusinessCategoryOptionModel convertToModel(BusinessCategoryOptionDto businessCategoryOptionDto) {
        return modelMapper.map(businessCategoryOptionDto, BusinessCategoryOptionModel.class);
    }

    /**
     * Creates a single Business Category option with a generated ID.
     * @param businessCategoryOptionDto - Business Category option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create a single Business Category option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody BusinessCategoryOptionDto businessCategoryOptionDto) {
        BusinessCategoryOptionModel businessCategoryOptionModel = convertToModel(businessCategoryOptionDto);
        businessCategoryOptionModel.setId(BusinessCategoryOptionIdGenerator.generateId());
        businessCategoryOptionService.save(businessCategoryOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business category option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Business Category options with generated IDs.
     * @param businessCategoryOptionDtoList - List of Business Category option data
     * @return                              - Response with success message
     */
    @Operation(summary = "Create multiple Business Category options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<BusinessCategoryOptionDto> businessCategoryOptionDtoList) {
        List<BusinessCategoryOptionModel> businessCategoryOptionModelList = new ArrayList<>();
        for (BusinessCategoryOptionDto businessCategoryOptionDto : businessCategoryOptionDtoList) {
            BusinessCategoryOptionModel model = convertToModel(businessCategoryOptionDto);
            model.setId(BusinessCategoryOptionIdGenerator.generateId());
            businessCategoryOptionModelList.add(model);
        }
        businessCategoryOptionService.saveMany(businessCategoryOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business category options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Business Category option by ID (excludes soft-deleted).
     * @param id - Business Category option ID
     * @return   - Response with Business Category option data
     */
    @Operation(summary = "Get a single Business Category option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        BusinessCategoryOptionModel model = businessCategoryOptionService.readOne(id);
        BusinessCategoryOptionDto businessCategoryOptionDto = convertToDto(model);
        return new ResponseEntity<>(businessCategoryOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Business Category options.
     * @return - Response with list of Business Category option data
     */
    @Operation(summary = "Get all available Business Category options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<BusinessCategoryOptionModel> businessCategoryOptionModelList = businessCategoryOptionService.readAll();
        List<BusinessCategoryOptionDto> businessCategoryOptionDtoList = new ArrayList<>();
        for (BusinessCategoryOptionModel businessCategoryOptionModel : businessCategoryOptionModelList) {
            businessCategoryOptionDtoList.add(convertToDto(businessCategoryOptionModel));
        }
        return new ResponseEntity<>(businessCategoryOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Business Category options, including soft-deleted.
     * @return - Response with list of all Business Category option data
     */
    @Operation(summary = "Get all Business Category options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<BusinessCategoryOptionModel> modelList = businessCategoryOptionService.hardReadAll();
        List<BusinessCategoryOptionDto> businessCategoryOptionDtoList = new ArrayList<>();
        for (BusinessCategoryOptionModel model : modelList) {
            businessCategoryOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(businessCategoryOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Business Category options by ID (excludes soft-deleted).
     * @param idList - List of Business Category option IDs
     * @return       - Response with list of Business Category option data
     */
    @Operation(summary = "Get multiple Business Category options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<BusinessCategoryOptionModel> businessCategoryOptionModelList = businessCategoryOptionService.readMany(idList);
        List<BusinessCategoryOptionDto> businessCategoryOptionDtoList = new ArrayList<>();
        for (BusinessCategoryOptionModel model : businessCategoryOptionModelList) {
            businessCategoryOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(businessCategoryOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Business Category option by ID (excludes soft-deleted).
     * @param businessCategoryOptionDto - Updated Business Category option data
     * @return                          - Response with updated Business Category option data
     */
    @Operation(summary = "Update a single Business Category option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody BusinessCategoryOptionDto businessCategoryOptionDto) {
        String modelId = businessCategoryOptionDto.getId();
        BusinessCategoryOptionModel savedModel = businessCategoryOptionService.readOne(modelId);
        savedModel.setName(businessCategoryOptionDto.getName());
        savedModel.setDescription(businessCategoryOptionDto.getDescription());
        businessCategoryOptionService.updateOne(savedModel);
        BusinessCategoryOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Business Category options (excludes soft-deleted).
     * @param businessCategoryOptionDtoList - List of updated Business Category option data
     * @return                              - Response with list of updated Business Category option data
     */
    @Operation(summary = "Update multiple Business Category options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<BusinessCategoryOptionDto> businessCategoryOptionDtoList) {
        List<BusinessCategoryOptionModel> inputModelList = new ArrayList<>();
        for (BusinessCategoryOptionDto businessCategoryOptionDto : businessCategoryOptionDtoList) {
            inputModelList.add(convertToModel(businessCategoryOptionDto));
        }
        List<BusinessCategoryOptionModel> updatedModelList = businessCategoryOptionService.updateMany(inputModelList);
        List<BusinessCategoryOptionDto> updatedDtoList = new ArrayList<>();
        for (BusinessCategoryOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Business Category option by ID, including soft-deleted.
     * @param businessCategoryOptionDto - Updated Business Category option data
     * @return                          - Response with updated Business Category option data
     */
    @Operation(summary = "Update a single Business Category option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody BusinessCategoryOptionDto businessCategoryOptionDto) {
        BusinessCategoryOptionModel businessCategoryOptionModel = businessCategoryOptionService.hardUpdate(convertToModel(businessCategoryOptionDto));
        BusinessCategoryOptionDto updatedDto = convertToDto(businessCategoryOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Business Category options, including soft-deleted.
     * @param businessCategoryOptionDtoList - List of updated Business Category option data
     * @return                              - Response with list of updated Business Category option data
     */
    @Operation(summary = "Update all Business Category options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<BusinessCategoryOptionDto> businessCategoryOptionDtoList) {
        List<BusinessCategoryOptionModel> inputModelList = new ArrayList<>();
        for (BusinessCategoryOptionDto businessCategoryOptionDto : businessCategoryOptionDtoList) {
            inputModelList.add(convertToModel(businessCategoryOptionDto));
        }
        List<BusinessCategoryOptionModel> updatedModelList = businessCategoryOptionService.hardUpdateAll(inputModelList);
        List<BusinessCategoryOptionDto> updatedDtoList = new ArrayList<>();
        for (BusinessCategoryOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Business Category option by ID.
     * @param id - Business Category option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Business Category option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        BusinessCategoryOptionModel deletedModel = businessCategoryOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business category option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Business Category option by ID.
     * @param id - Business Category option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Business Category option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        businessCategoryOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business category option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Business Category options by ID.
     * @param idList - List of Business Category option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Business Category options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<BusinessCategoryOptionModel> deletedModelList = businessCategoryOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Business category options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Business Category options by ID.
     * @param idList - List of Business Category option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Business Category options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        businessCategoryOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Business category options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Business Category options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Business Category options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        businessCategoryOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Business category options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}