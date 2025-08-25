/**
 * REST API controller for managing Country options.
 * Handles CRUD operations for Country option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.country.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.country.dto.CountryOptionDto;
import rw.evolve.eprocurement.country.model.CountryOptionModel;
import rw.evolve.eprocurement.country.service.CountryOptionService;
import rw.evolve.eprocurement.country.utils.CountryOptionIdGenerator;
import rw.evolve.eprocurement.country.dto.ResponseMessageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/country_option")
@Tag(name = "Country Option API")
public class CountryOptionController {


    private final CountryOptionService countryOptionService;

    private ModelMapper modelMapper = new ModelMapper();

    public CountryOptionController(
            CountryOptionService countryOptionService,
            ModelMapper modelMapper
    ){
        this.countryOptionService = countryOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts CountryOptionModel to CountryOptionDto.
     * @param model - CountryOptionModel to convert
     * @return      - Converted CountryOptionDto
     */
    private CountryOptionDto convertToDto(CountryOptionModel model) {
        return modelMapper.map(model, CountryOptionDto.class);
    }

    /**
     * Converts CountryOptionDto to CountryOptionModel.
     * @param countryOptionDto - CountryOptionDto to convert
     * @return                 - Converted CountryOptionModel
     */
    private CountryOptionModel convertToModel(CountryOptionDto countryOptionDto) {
        return modelMapper.map(countryOptionDto, CountryOptionModel.class);
    }

    /**
     * Creates a single Country option with a generated ID.
     * @param countryOptionDto - Country option data
     * @return                 - Response with success message
     */
    @Operation(summary = "Create a single Country option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody CountryOptionDto countryOptionDto) {
        CountryOptionModel countryOptionModel = convertToModel(countryOptionDto);
        countryOptionModel.setId(CountryOptionIdGenerator.generateId());
        countryOptionService.save(countryOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Country options with generated IDs.
     * @param countryOptionDtoList - List of Country option data
     * @return                     - Response with success message
     */
    @Operation(summary = "Create multiple Country options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<CountryOptionDto> countryOptionDtoList) {
        List<CountryOptionModel> countryOptionModelList = new ArrayList<>();
        for (CountryOptionDto countryOptionDto : countryOptionDtoList) {
            CountryOptionModel model = convertToModel(countryOptionDto);
            model.setId(CountryOptionIdGenerator.generateId());
            countryOptionModelList.add(model);
        }
        countryOptionService.saveMany(countryOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Country option by ID (excludes soft-deleted).
     * @param id - Country option ID
     * @return   - Response with Country option data
     */
    @Operation(summary = "Get a single Country option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        CountryOptionModel model = countryOptionService.readOne(id);
        CountryOptionDto countryOptionDto = convertToDto(model);
        return new ResponseEntity<>(countryOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Country options.
     * @return  - Response with list of Country option data
     */
    @Operation(summary = "Get all available Country options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<CountryOptionModel> countryOptionModelList = countryOptionService.readAll();
        List<CountryOptionDto> countryOptionDtoList = new ArrayList<>();
        for (CountryOptionModel countryOptionModel : countryOptionModelList) {
            countryOptionDtoList.add(convertToDto(countryOptionModel));
        }
        return new ResponseEntity<>(countryOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Country options, including soft-deleted.
     * @return  - Response with list of all Country option data
     */
    @Operation(summary = "Get all Country options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<CountryOptionModel> modelList = countryOptionService.hardReadAll();
        List<CountryOptionDto> countryOptionDtoList = new ArrayList<>();
        for (CountryOptionModel model : modelList) {
            countryOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(countryOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Country options by ID (excludes soft-deleted).
     * @param idList - List of Country option IDs
     * @return       - Response with list of Country option data
     */
    @Operation(summary = "Get multiple Country options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<CountryOptionModel> countryOptionModelList = countryOptionService.readMany(idList);
        List<CountryOptionDto> countryOptionDtoList = new ArrayList<>();
        for (CountryOptionModel model : countryOptionModelList) {
            countryOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(countryOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Country option by ID (excludes soft-deleted).
     * @param countryOptionDto - Updated Country option data
     * @return                 - Response with updated Country option data
     */
    @Operation(summary = "Update a single Country option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody CountryOptionDto countryOptionDto) {
        String modelId = countryOptionDto.getId();
        CountryOptionModel savedModel = countryOptionService.readOne(modelId);
        savedModel.setName(countryOptionDto.getName());
        savedModel.setDescription(countryOptionDto.getDescription());
        countryOptionService.updateOne(savedModel);
        CountryOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Country options (excludes soft-deleted).
     * @param countryOptionDtoList - List of updated Country option data
     * @return                     - Response with list of updated Country option data
     */
    @Operation(summary = "Update multiple Country options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<CountryOptionDto> countryOptionDtoList) {
        List<CountryOptionModel> inputModelList = new ArrayList<>();
        for (CountryOptionDto countryOptionDto : countryOptionDtoList) {
            inputModelList.add(convertToModel(countryOptionDto));
        }
        List<CountryOptionModel> updatedModelList = countryOptionService.updateMany(inputModelList);
        List<CountryOptionDto> updatedDtoList = new ArrayList<>();
        for (CountryOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Country option by ID, including soft-deleted.
     * @param countryOptionDto - Updated Country option data
     * @return                 - Response with updated Country option data
     */
    @Operation(summary = "Update a single Country option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody CountryOptionDto countryOptionDto) {
        CountryOptionModel countryOptionModel = countryOptionService.hardUpdate(convertToModel(countryOptionDto));
        CountryOptionDto updatedDto = convertToDto(countryOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Country options, including soft-deleted.
     * @param countryOptionDtoList - List of updated Country option data
     * @return                     - Response with list of updated Country option data
     */
    @Operation(summary = "Update all Country options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<CountryOptionDto> countryOptionDtoList) {
        List<CountryOptionModel> inputModelList = new ArrayList<>();
        for (CountryOptionDto countryOptionDto : countryOptionDtoList) {
            inputModelList.add(convertToModel(countryOptionDto));
        }
        List<CountryOptionModel> updatedModelList = countryOptionService.hardUpdateAll(inputModelList);
        List<CountryOptionDto> updatedDtoList = new ArrayList<>();
        for (CountryOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Country option by ID.
     * @param id - Country option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Country option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        CountryOptionModel deletedModel = countryOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Country option by ID.
     * @param id - Country option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Country option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        countryOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Country options by ID.
     * @param idList - List of Country option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Country options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<CountryOptionModel> deletedModelList = countryOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Country options by ID.
     * @param idList - List of Country option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Country options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        countryOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Country options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Country options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        countryOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Country options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}