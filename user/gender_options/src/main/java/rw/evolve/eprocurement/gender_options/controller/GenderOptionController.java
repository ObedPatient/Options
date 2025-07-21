/**
 * REST API controller for managing Gender options
 * Provides endpoints for creating, retrieving, deleting and updating Gender option data.
 */
package rw.evolve.eprocurement.gender_options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.gender_options.dto.GenderOptionDto;
import rw.evolve.eprocurement.gender_options.dto.ResponseMessageDto;
import rw.evolve.eprocurement.gender_options.model.GenderOptionModel;
import rw.evolve.eprocurement.gender_options.service.GenderOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/gender_option")
@Tag(name = "Gender Option Api")
public class GenderOptionController {

    @Autowired
    private GenderOptionService genderOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a GenderOptionModel to GenderOptionDto.
     * @param model The GenderOptionModel to convert.
     * @return The converted GenderOptionDto.
     */
    private GenderOptionDto convertToDto(GenderOptionModel model){
        return modelMapper.map(model, GenderOptionDto.class);
    }

    /**
     * Converts a GenderOptionDto to GenderOptionModel.
     * @param dto The GenderOptionDto to convert.
     * @return The converted GenderOptionModel.
     */
    private GenderOptionModel convertToModel(GenderOptionDto dto){
        return modelMapper.map(dto, GenderOptionModel.class);
    }

    /**
     * Creates a single Gender Option
     * @param genderOptionDto DTO containing Gender Option data
     * @return ResponseEntity containing a Map with the created GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Gender Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createGenderOption(@Valid @RequestBody GenderOptionDto genderOptionDto){
        GenderOptionModel genderOptionModel = convertToModel(genderOptionDto);
        GenderOptionModel createdGenderOptionModel = genderOptionService.createGenderOption(genderOptionModel);
        GenderOptionDto createdGenderOptionDto = convertToDto(createdGenderOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Option", createdGenderOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Gender Options
     * @param genderOptionDtos List of Gender Option DTOs
     * @return ResponseEntity containing a Map with the created list of GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Gender Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createGenderOptions(@Valid @RequestBody List<GenderOptionDto> genderOptionDtos){
        List<GenderOptionModel> genderOptionModels = new ArrayList<>();
        for (GenderOptionDto dto: genderOptionDtos){
            genderOptionModels.add(convertToModel(dto));
        }
        List<GenderOptionModel> createdModels = genderOptionService.createGenderOptions(genderOptionModels);
        List<GenderOptionDto> createdGenderDtos = new ArrayList<>();
        for (GenderOptionModel model: createdModels){
            createdGenderDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Gender Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", createdGenderDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a Gender Option by its ID, excluding soft-deleted options.
     * @param id The ID of the Gender Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Gender Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("GenderOptionId") Long id){
        GenderOptionModel model = genderOptionService.readOne(id);
        GenderOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Gender Options.
     * @return ResponseEntity containing a Map with a list of GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Gender Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<GenderOptionModel> genderOptionModels = genderOptionService.readAll();
        List<GenderOptionDto> genderOptionDtos = new ArrayList<>();
        for (GenderOptionModel genderOptionModel: genderOptionModels){
            genderOptionDtos.add(convertToDto(genderOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Gender Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", genderOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Gender Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Gender Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<GenderOptionModel> models = genderOptionService.hardReadAll();
        List<GenderOptionDto> dtos = new ArrayList<>();
        for (GenderOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Gender Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple Gender Options by their IDs, excluding soft-deleted records.
     * @param ids List of Gender Option IDs
     * @return ResponseEntity containing a Map with a list of GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Gender Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<GenderOptionModel> genderOptionModels = genderOptionService.readMany(ids);
        List<GenderOptionDto> genderOptionDtos = new ArrayList<>();
        for (GenderOptionModel model: genderOptionModels){
            genderOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", genderOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Gender Option by its ID, excluding soft-deleted records.
     * @param id The ID of the Gender Option to update
     * @param genderOptionDto The updated Gender Option data
     * @return ResponseEntity containing a Map with the updated GenderOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Gender Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody GenderOptionDto genderOptionDto){
        GenderOptionModel genderOptionModel = genderOptionService.updateOne(id, convertToModel(genderOptionDto));
        GenderOptionDto dto = convertToDto(genderOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Gender Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Gender Options based on the provided list of Gender Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param genderOptionDtos List of GenderOptionDto objects containing updated data
     * @return ResponseEntity containing a Map with the list of updated GenderOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple Gender Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<GenderOptionDto> genderOptionDtos){
        List<GenderOptionModel> inputModels = new ArrayList<>();
        for (GenderOptionDto dto: genderOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<GenderOptionModel> updatedModels = genderOptionService.updateMany(inputModels);
        List<GenderOptionDto> dtos = new ArrayList<>();
        for (GenderOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Gender Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Gender Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the Gender Option to update.
     * @param genderOptionDto The updated Gender Option data.
     * @return ResponseEntity containing a Map with the updated GenderOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update Gender Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody GenderOptionDto genderOptionDto){
        GenderOptionModel genderOptionModel = genderOptionService.hardUpdateOne(id, convertToModel(genderOptionDto));
        GenderOptionDto dto = convertToDto(genderOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Gender Options, including soft-deleted records, based on their IDs.
     *
     * @param genderOptionDtos The list of updated Gender Option data.
     * @return ResponseEntity containing a Map with the list of updated GenderOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all Gender Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<GenderOptionDto> genderOptionDtos){
        List<GenderOptionModel> inputModels = new ArrayList<>();
        for (GenderOptionDto dto: genderOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<GenderOptionModel> updatedModels = genderOptionService.hardUpdateAll(inputModels);
        List<GenderOptionDto> dtos = new ArrayList<>();
        for (GenderOptionModel genderOptionModel: updatedModels){
            dtos.add(convertToDto(genderOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single Gender Option by ID
     * @param id ID of the Gender Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted GenderOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single Gender Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteGenderOption(@RequestParam Long id){
        GenderOptionModel deletedGenderOptionModel = genderOptionService.softDeleteGenderOption(id);
        GenderOptionDto deletedGenderOptionDto = convertToDto(deletedGenderOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Gender Option", deletedGenderOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Gender Option by ID
     * @param id ID of the Gender Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single Gender Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteGenderOption(@RequestParam Long id){
        genderOptionService.hardDeleteGenderOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Gender Options by IDs
     * @param ids List of Gender Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted GenderOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple Gender Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteGenderOptions(@RequestBody List<Long> ids){
        List<GenderOptionModel> deletedGenderOptionModels = genderOptionService.softDeleteGenderOptions(ids);
        List<GenderOptionDto> deletedGenderOptionDtos = new ArrayList<>();
        for (GenderOptionModel model: deletedGenderOptionModels){
            deletedGenderOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Gender Options", deletedGenderOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple Gender Options by IDs
     * @param ids List of Gender Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple Gender Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteGenderOptions(@RequestBody List<Long> ids){
        genderOptionService.hardDeleteGenderOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Gender Options Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}