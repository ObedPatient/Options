package rw.evolve.eprocurement.currency_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.currency_option.dto.CurrencyOptionDto;
import rw.evolve.eprocurement.currency_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.currency_option.model.CurrencyOptionModel;
import rw.evolve.eprocurement.currency_option.service.CurrencyOptionService;
import rw.evolve.eprocurement.currency_option.utils.CurrencyOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for managing Currency options.
 * Handles CRUD operations for Currency option data with soft and hard delete capabilities.
 */
@RestController
@RequestMapping("api/currency_option")
@Tag(name = "Currency Option API")
public class CurrencyOptionController {

    private final CurrencyOptionService currencyOptionService;

    private final ModelMapper modelMapper;

    public CurrencyOptionController(
            CurrencyOptionService currencyOptionService,
            ModelMapper modelMapper
    ){
        this.currencyOptionService = currencyOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts CurrencyOptionModel to CurrencyOptionDto.
     * @param model - CurrencyOptionModel to convert
     * @return      - Converted CurrencyOptionDto
     */
    private CurrencyOptionDto convertToDto(CurrencyOptionModel model) {
        return modelMapper.map(model, CurrencyOptionDto.class);
    }

    /**
     * Converts CurrencyOptionDto to CurrencyOptionModel.
     * @param currencyOptionDto - CurrencyOptionDto to convert
     * @return                  - Converted CurrencyOptionModel
     */
    private CurrencyOptionModel convertToModel(CurrencyOptionDto currencyOptionDto) {
        return modelMapper.map(currencyOptionDto, CurrencyOptionModel.class);
    }

    /**
     * Creates a single Currency option with a generated ID.
     * @param currencyOptionDto - Currency option data
     * @return                  - Response with success message
     */
    @Operation(summary = "Create a single Currency option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody CurrencyOptionDto currencyOptionDto) {
        CurrencyOptionModel currencyOptionModel = convertToModel(currencyOptionDto);
        currencyOptionModel.setId(CurrencyOptionIdGenerator.generateId());
        currencyOptionService.save(currencyOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Currency option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Currency options with generated IDs.
     * @param currencyOptionDtoList - List of Currency option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create multiple Currency options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<CurrencyOptionDto> currencyOptionDtoList) {
        List<CurrencyOptionModel> currencyOptionModelList = new ArrayList<>();
        for (CurrencyOptionDto currencyOptionDto : currencyOptionDtoList) {
            CurrencyOptionModel model = convertToModel(currencyOptionDto);
            model.setId(CurrencyOptionIdGenerator.generateId());
            currencyOptionModelList.add(model);
        }
        currencyOptionService.saveMany(currencyOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Currency options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Currency option by ID (excludes soft-deleted).
     * @param id - Currency option ID
     * @return   - Response with Currency option data
     */
    @Operation(summary = "Get a single Currency option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        CurrencyOptionModel model = currencyOptionService.readOne(id);
        CurrencyOptionDto currencyOptionDto = convertToDto(model);
        return new ResponseEntity<>(currencyOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Currency options.
     * @return - Response with list of Currency option data
     */
    @Operation(summary = "Get all available Currency options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<CurrencyOptionModel> currencyOptionModelList = currencyOptionService.readAll();
        List<CurrencyOptionDto> currencyOptionDtoList = new ArrayList<>();
        for (CurrencyOptionModel currencyOptionModel : currencyOptionModelList) {
            currencyOptionDtoList.add(convertToDto(currencyOptionModel));
        }
        return new ResponseEntity<>(currencyOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Currency options, including soft-deleted.
     * @return - Response with list of all Currency option data
     */
    @Operation(summary = "Get all Currency options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<CurrencyOptionModel> modelList = currencyOptionService.hardReadAll();
        List<CurrencyOptionDto> currencyOptionDtoList = new ArrayList<>();
        for (CurrencyOptionModel model : modelList) {
            currencyOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(currencyOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Currency options by ID (excludes soft-deleted).
     * @param idList - List of Currency option IDs
     * @return       - Response with list of Currency option data
     */
    @Operation(summary = "Get multiple Currency options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<CurrencyOptionModel> currencyOptionModelList = currencyOptionService.readMany(idList);
        List<CurrencyOptionDto> currencyOptionDtoList = new ArrayList<>();
        for (CurrencyOptionModel model : currencyOptionModelList) {
            currencyOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(currencyOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Currency option by ID (excludes soft-deleted).
     * @param currencyOptionDto - Updated Currency option data
     * @return                  - Response with updated Currency option data
     */
    @Operation(summary = "Update a single Currency option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody CurrencyOptionDto currencyOptionDto) {
        String modelId = currencyOptionDto.getId();
        CurrencyOptionModel savedModel = currencyOptionService.readOne(modelId);
        savedModel.setName(currencyOptionDto.getName());
        savedModel.setDescription(currencyOptionDto.getDescription());
        currencyOptionService.updateOne(savedModel);
        CurrencyOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Currency options (excludes soft-deleted).
     * @param currencyOptionDtoList - List of updated Currency option data
     * @return                      - Response with list of updated Currency option data
     */
    @Operation(summary = "Update multiple Currency options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<CurrencyOptionDto> currencyOptionDtoList) {
        List<CurrencyOptionModel> inputModelList = new ArrayList<>();
        for (CurrencyOptionDto currencyOptionDto : currencyOptionDtoList) {
            inputModelList.add(convertToModel(currencyOptionDto));
        }
        List<CurrencyOptionModel> updatedModelList = currencyOptionService.updateMany(inputModelList);
        List<CurrencyOptionDto> updatedDtoList = new ArrayList<>();
        for (CurrencyOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Currency option by ID, including soft-deleted.
     * @param currencyOptionDto - Updated Currency option data
     * @return                  - Response with updated Currency option data
     */
    @Operation(summary = "Update a single Currency option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody CurrencyOptionDto currencyOptionDto) {
        CurrencyOptionModel currencyOptionModel = currencyOptionService.hardUpdate(convertToModel(currencyOptionDto));
        CurrencyOptionDto updatedDto = convertToDto(currencyOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Currency options, including soft-deleted.
     * @param currencyOptionDtoList - List of updated Currency option data
     * @return                      - Response with list of updated Currency option data
     */
    @Operation(summary = "Update all Currency options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<CurrencyOptionDto> currencyOptionDtoList) {
        List<CurrencyOptionModel> inputModelList = new ArrayList<>();
        for (CurrencyOptionDto currencyOptionDto : currencyOptionDtoList) {
            inputModelList.add(convertToModel(currencyOptionDto));
        }
        List<CurrencyOptionModel> updatedModelList = currencyOptionService.hardUpdateAll(inputModelList);
        List<CurrencyOptionDto> updatedDtoList = new ArrayList<>();
        for (CurrencyOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Currency option by ID.
     * @param id - Currency option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Currency option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        CurrencyOptionModel deletedModel = currencyOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Currency option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Currency option by ID.
     * @param id - Currency option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Currency option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        currencyOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Currency option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Currency options by ID.
     * @param idList - List of Currency option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Currency options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<CurrencyOptionModel> deletedModelList = currencyOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Currency options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Currency options by ID.
     * @param idList - List of Currency option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Currency options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        currencyOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Currency options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Currency options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Currency options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        currencyOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Currency options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}