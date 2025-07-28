/**
 * REST API controller for managing Country Code options.
 * Handles CRUD operations for Country Code option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.country_code.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.country_code.dto.CountryCodeOptionDto;
import rw.evolve.eprocurement.country_code.dto.ResponseMessageDto;
import rw.evolve.eprocurement.country_code.model.CountryCodeOptionModel;
import rw.evolve.eprocurement.country_code.service.CountryCodeOptionService;
import rw.evolve.eprocurement.country_code.utils.CountryCodeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/country_code_option")
@Tag(name = "Country Code Option API")
public class CountryCodeOptionController {

    @Autowired
    private CountryCodeOptionService countryCodeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts CountryCodeOptionModel to CountryCodeOptionDto.
     * @param model - CountryCodeOptionModel to convert
     * @return      - Converted CountryCodeOptionDto
     */
    private CountryCodeOptionDto convertToDto(CountryCodeOptionModel model) {
        return modelMapper.map(model, CountryCodeOptionDto.class);
    }

    /**
     * Converts CountryCodeOptionDto to CountryCodeOptionModel.
     * @param countryCodeOptionDto - CountryCodeOptionDto to convert
     * @return                     - Converted CountryCodeOptionModel
     */
    private CountryCodeOptionModel convertToModel(CountryCodeOptionDto countryCodeOptionDto) {
        return modelMapper.map(countryCodeOptionDto, CountryCodeOptionModel.class);
    }

    /**
     * Creates a single Country Code option with a generated ID.
     * @param countryCodeOptionDto - Country Code option data
     * @return                     - Response with success message
     */
    @Operation(summary = "Create a single Country Code option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody CountryCodeOptionDto countryCodeOptionDto) {
        CountryCodeOptionModel countryCodeOptionModel = convertToModel(countryCodeOptionDto);
        countryCodeOptionModel.setId(CountryCodeOptionIdGenerator.generateId());
        countryCodeOptionService.save(countryCodeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country code option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Country Code options with generated IDs.
     * @param countryCodeOptionDtoList - List of Country Code option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create multiple Country Code options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<CountryCodeOptionDto> countryCodeOptionDtoList) {
        List<CountryCodeOptionModel> countryCodeOptionModelList = new ArrayList<>();
        for (CountryCodeOptionDto countryCodeOptionDto : countryCodeOptionDtoList) {
            CountryCodeOptionModel model = convertToModel(countryCodeOptionDto);
            model.setId(CountryCodeOptionIdGenerator.generateId());
            countryCodeOptionModelList.add(model);
        }
        countryCodeOptionService.saveMany(countryCodeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country code options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Country Code option by ID (excludes soft-deleted).
     * @param id - Country Code option ID
     * @return   - Response with Country Code option data
     */
    @Operation(summary = "Get a single Country Code option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        CountryCodeOptionModel model = countryCodeOptionService.readOne(id);
        CountryCodeOptionDto countryCodeOptionDto = convertToDto(model);
        return new ResponseEntity<>(countryCodeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Country Code options.
     * @return - Response with list of Country Code option data
     */
    @Operation(summary = "Get all available Country Code options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<CountryCodeOptionModel> countryCodeOptionModelList = countryCodeOptionService.readAll();
        List<CountryCodeOptionDto> countryCodeOptionDtoList = new ArrayList<>();
        for (CountryCodeOptionModel countryCodeOptionModel : countryCodeOptionModelList) {
            countryCodeOptionDtoList.add(convertToDto(countryCodeOptionModel));
        }
        return new ResponseEntity<>(countryCodeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Country Code options, including soft-deleted.
     * @return - Response with list of all Country Code option data
     */
    @Operation(summary = "Get all Country Code options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<CountryCodeOptionModel> modelList = countryCodeOptionService.hardReadAll();
        List<CountryCodeOptionDto> countryCodeOptionDtoList = new ArrayList<>();
        for (CountryCodeOptionModel model : modelList) {
            countryCodeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(countryCodeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Country Code options by ID (excludes soft-deleted).
     * @param idList - List of Country Code option IDs
     * @return       - Response with list of Country Code option data
     */
    @Operation(summary = "Get multiple Country Code options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<CountryCodeOptionModel> countryCodeOptionModelList = countryCodeOptionService.readMany(idList);
        List<CountryCodeOptionDto> countryCodeOptionDtoList = new ArrayList<>();
        for (CountryCodeOptionModel model : countryCodeOptionModelList) {
            countryCodeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(countryCodeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Country Code option by ID (excludes soft-deleted).
     * @param countryCodeOptionDto - Updated Country Code option data
     * @return                     - Response with updated Country Code option data
     */
    @Operation(summary = "Update a single Country Code option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody CountryCodeOptionDto countryCodeOptionDto) {
        String modelId = countryCodeOptionDto.getId();
        CountryCodeOptionModel savedModel = countryCodeOptionService.readOne(modelId);
        savedModel.setName(countryCodeOptionDto.getName());
        savedModel.setDescription(countryCodeOptionDto.getDescription());
        countryCodeOptionService.updateOne(savedModel);
        CountryCodeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Country Code options (excludes soft-deleted).
     * @param countryCodeOptionDtoList - List of updated Country Code option data
     * @return                         - Response with list of updated Country Code option data
     */
    @Operation(summary = "Update multiple Country Code options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<CountryCodeOptionDto> countryCodeOptionDtoList) {
        List<CountryCodeOptionModel> inputModelList = new ArrayList<>();
        for (CountryCodeOptionDto countryCodeOptionDto : countryCodeOptionDtoList) {
            inputModelList.add(convertToModel(countryCodeOptionDto));
        }
        List<CountryCodeOptionModel> updatedModelList = countryCodeOptionService.updateMany(inputModelList);
        List<CountryCodeOptionDto> updatedDtoList = new ArrayList<>();
        for (CountryCodeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Country Code option by ID, including soft-deleted.
     * @param countryCodeOptionDto - Updated Country Code option data
     * @return                     - Response with updated Country Code option data
     */
    @Operation(summary = "Update a single Country Code option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody CountryCodeOptionDto countryCodeOptionDto) {
        CountryCodeOptionModel countryCodeOptionModel = countryCodeOptionService.hardUpdate(convertToModel(countryCodeOptionDto));
        CountryCodeOptionDto updatedDto = convertToDto(countryCodeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Country Code options, including soft-deleted.
     * @param countryCodeOptionDtoList - List of updated Country Code option data
     * @return                         - Response with list of updated Country Code option data
     */
    @Operation(summary = "Update all Country Code options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<CountryCodeOptionDto> countryCodeOptionDtoList) {
        List<CountryCodeOptionModel> inputModelList = new ArrayList<>();
        for (CountryCodeOptionDto countryCodeOptionDto : countryCodeOptionDtoList) {
            inputModelList.add(convertToModel(countryCodeOptionDto));
        }
        List<CountryCodeOptionModel> updatedModelList = countryCodeOptionService.hardUpdateAll(inputModelList);
        List<CountryCodeOptionDto> updatedDtoList = new ArrayList<>();
        for (CountryCodeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Country Code option by ID.
     * @param id - Country Code option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Country Code option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        CountryCodeOptionModel deletedModel = countryCodeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country code option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Country Code option by ID.
     * @param id - Country Code option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Country Code option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        countryCodeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country code option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Country Code options by ID.
     * @param idList - List of Country Code option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Country Code options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<CountryCodeOptionModel> deletedModelList = countryCodeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country code options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Country Code options by ID.
     * @param idList - List of Country Code option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Country Code options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        countryCodeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Country code options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Country Code options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Country Code options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        countryCodeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Country code options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}