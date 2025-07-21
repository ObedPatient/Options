/**
 * REST API controller for managing Country options
 * Provides endpoints for creating, retrieving, deleting and updating Country option data.
 */
package rw.evolve.eprocurement.country.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.country.dto.CountryOptionDto;
import rw.evolve.eprocurement.country.dto.ResponseMessageDto;
import rw.evolve.eprocurement.country.model.CountryOptionModel;
import rw.evolve.eprocurement.country.service.CountryOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/country_option")
@Tag(name = "Country Option Api")
public class CountryOptionController {

    @Autowired
    private CountryOptionService countryOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a CountryOptionModel to CountryOptionDto.
     * @param model The CountryOptionModel to convert.
     * @return The converted CountryOptionDto.
     */
    private CountryOptionDto convertToDto(CountryOptionModel model){
        return modelMapper.map(model, CountryOptionDto.class);
    }

    /**
     * Converts a CountryOptionDto to CountryOptionModel.
     * @param dto The CountryOptionDto to convert.
     * @return The converted CountryOptionModel.
     */
    private CountryOptionModel convertToModel(CountryOptionDto dto){
        return modelMapper.map(dto, CountryOptionModel.class);
    }

    /**
     * Creates a single Country Option
     * @param countryOptionDto DTO containing Country Option data
     * @return ResponseEntity containing a Map with the created CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Country Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createCountryOption(@Valid @RequestBody CountryOptionDto countryOptionDto){
        CountryOptionModel countryOptionModel = convertToModel(countryOptionDto);
        CountryOptionModel createdCountryOptionModel = countryOptionService.createCountryOption(countryOptionModel);
        CountryOptionDto createdCountryOptionDto = convertToDto(createdCountryOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Option", createdCountryOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Country Options
     * @param countryOptionDtos List of Country Option DTOs
     * @return ResponseEntity containing a Map with the created list of CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Country Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createCountryOptions(@Valid @RequestBody List<CountryOptionDto> countryOptionDtos){
        List<CountryOptionModel> countryOptionModels = new ArrayList<>();
        for (CountryOptionDto dto: countryOptionDtos){
            countryOptionModels.add(convertToModel(dto));
        }
        List<CountryOptionModel> createdModels = countryOptionService.createCountryOptions(countryOptionModels);
        List<CountryOptionDto> createdCountryDtos = new ArrayList<>();
        for (CountryOptionModel model: createdModels){
            createdCountryDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Country Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", createdCountryDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a Country Option by its ID, excluding soft-deleted options.
     * @param id The ID of the Country Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Country Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("CountryOptionId") Long id){
        CountryOptionModel model = countryOptionService.readOne(id);
        CountryOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Country Options.
     * @return ResponseEntity containing a Map with a list of CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Country Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<CountryOptionModel> countryOptionModels = countryOptionService.readAll();
        List<CountryOptionDto> countryOptionDtos = new ArrayList<>();
        for (CountryOptionModel countryOptionModel: countryOptionModels){
            countryOptionDtos.add(convertToDto(countryOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Country Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", countryOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Country Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Country Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<CountryOptionModel> models = countryOptionService.hardReadAll();
        List<CountryOptionDto> dtos = new ArrayList<>();
        for (CountryOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Country Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple Country Options by their IDs, excluding soft-deleted records.
     * @param ids List of Country Option IDs
     * @return ResponseEntity containing a Map with a list of CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Country Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<CountryOptionModel> countryOptionModels = countryOptionService.readMany(ids);
        List<CountryOptionDto> countryOptionDtos = new ArrayList<>();
        for (CountryOptionModel model: countryOptionModels){
            countryOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", countryOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Country Option by its ID, excluding soft-deleted records.
     * @param id The ID of the Country Option to update
     * @param countryOptionDto The updated Country Option data
     * @return ResponseEntity containing a Map with the updated CountryOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Country Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody CountryOptionDto countryOptionDto){
        CountryOptionModel countryOptionModel = countryOptionService.updateOne(id, convertToModel(countryOptionDto));
        CountryOptionDto dto = convertToDto(countryOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Country Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Country Options based on the provided list of Country Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param countryOptionDtos List of CountryOptionDto objects containing updated data
     * @return ResponseEntity containing a Map with the list of updated CountryOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple Country Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<CountryOptionDto> countryOptionDtos){
        List<CountryOptionModel> inputModels = new ArrayList<>();
        for (CountryOptionDto dto: countryOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<CountryOptionModel> updatedModels = countryOptionService.updateMany(inputModels);
        List<CountryOptionDto> dtos = new ArrayList<>();
        for (CountryOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Country Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Country Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the Country Option to update.
     * @param countryOptionDto The updated Country Option data.
     * @return ResponseEntity containing a Map with the updated CountryOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update Country Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody CountryOptionDto countryOptionDto){
        CountryOptionModel countryOptionModel = countryOptionService.hardUpdateOne(id, convertToModel(countryOptionDto));
        CountryOptionDto dto = convertToDto(countryOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Country Options, including soft-deleted records, based on their IDs.
     *
     * @param countryOptionDtos The list of updated Country Option data.
     * @return ResponseEntity containing a Map with the list of updated CountryOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all Country Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<CountryOptionDto> countryOptionDtos){
        List<CountryOptionModel> inputModels = new ArrayList<>();
        for (CountryOptionDto dto: countryOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<CountryOptionModel> updatedModels = countryOptionService.hardUpdateAll(inputModels);
        List<CountryOptionDto> dtos = new ArrayList<>();
        for (CountryOptionModel countryOptionModel: updatedModels){
            dtos.add(convertToDto(countryOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single Country Option by ID
     * @param id ID of the Country Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted CountryOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single Country Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteCountryOption(@RequestParam Long id){
        CountryOptionModel deletedCountryOptionModel = countryOptionService.softDeleteCountryOption(id);
        CountryOptionDto deletedCountryOptionDto = convertToDto(deletedCountryOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Country Option", deletedCountryOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Country Option by ID
     * @param id ID of the Country Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single Country Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteCountryOption(@RequestParam Long id){
        countryOptionService.hardDeleteCountryOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Country Options by IDs
     * @param ids List of Country Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted CountryOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple Country Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteCountryOptions(@RequestBody List<Long> ids){
        List<CountryOptionModel> deletedCountryOptionModels = countryOptionService.softDeleteCountryOptions(ids);
        List<CountryOptionDto> deletedCountryOptionDtos = new ArrayList<>();
        for (CountryOptionModel model: deletedCountryOptionModels){
            deletedCountryOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Country Options", deletedCountryOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple Country Options by IDs
     * @param ids List of Country Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple Country Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteCountryOptions(@RequestBody List<Long> ids){
        countryOptionService.hardDeleteCountryOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Country Options Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}