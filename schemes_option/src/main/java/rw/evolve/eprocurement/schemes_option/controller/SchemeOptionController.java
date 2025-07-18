/**
 * REST API controller for managing Selection Method options
 * Provides endpoints for creating, retrieving, deleting and updating Selection Method option data.
 */
package rw.evolve.eprocurement.schemes_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.schemes_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.schemes_option.dto.SchemeOptionDto;
import rw.evolve.eprocurement.schemes_option.model.SchemeOptionModel;
import rw.evolve.eprocurement.schemes_option.service.SchemeOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/scheme_option")
@Tag(name = "Scheme Option Api")
public class SchemeOptionController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private SchemeOptionService schemeOptionService;

    /**
     * Converts a SchemeOptionModel to SchemeOptionDto.
     * @param model The SchemeOptionModel to convert.
     * @return The converted SchemeOptionDto.
     */
    private SchemeOptionDto convertToDto(SchemeOptionModel model){
        return modelMapper.map(model, SchemeOptionDto.class);
    }

    /**
     * Converts a SchemeOptionDto to SchemeOptionModel.
     * @param dto The SchemeOptionDto to convert.
     * @return The converted SchemeOptionModel.
     */
    private SchemeOptionModel convertToModel(SchemeOptionDto dto){
        return modelMapper.map(dto, SchemeOptionModel.class);
    }

    /**
     * Creates a single Scheme option
     * @param schemeOptionDto DTO containing Scheme option data
     * @return ResponseEntity containing a Map with the created SchemeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Scheme option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createSchemeOption(@Valid @RequestBody SchemeOptionDto schemeOptionDto){
        SchemeOptionModel schemeOptionModel = convertToModel(schemeOptionDto);
        SchemeOptionModel createdSchemeOptionModel = schemeOptionService.createSchemeOption(schemeOptionModel);
        SchemeOptionDto createdSchemeOptionDto = convertToDto(createdSchemeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme Options", createdSchemeOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Scheme options
     * @param schemeOptionDtos List of Scheme option DTOs
     * @return ResponseEntity containing a Map with the created list of SchemeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Scheme Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createSchemeOptions(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtos ){
        List<SchemeOptionModel> schemeOptionModels = new ArrayList<>();
        for (SchemeOptionDto dto: schemeOptionDtos){
            schemeOptionModels.add(convertToModel(dto));
        }
        List<SchemeOptionModel> createdModels = schemeOptionService.createSchemeOptions(schemeOptionModels);
        List<SchemeOptionDto> createdSchemeDtos = new ArrayList<>();
        for (SchemeOptionModel model: createdModels){
            createdSchemeDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Scheme options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme Options", createdSchemeDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves a Scheme option by its ID, excluding soft-deleted Schemes.
     * @param id The ID of the Scheme to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the SchemeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Scheme  API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("SchemeOptionId") Long id){
        SchemeOptionModel model = schemeOptionService.readOne(id);
        SchemeOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Schemes.
     * @return ResponseEntity containing a Map with a list of SchemeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Scheme Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<SchemeOptionModel> schemeOptionModels = schemeOptionService.readAll();
        List<SchemeOptionDto> schemeOptionDtos = new ArrayList<>();
        for (SchemeOptionModel schemeOptionModel: schemeOptionModels){
            schemeOptionDtos.add(convertToDto(schemeOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Scheme options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme Options", schemeOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Schemes, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of SchemeDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Scheme Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<SchemeOptionModel> models = schemeOptionService.hardReadAll();
        List<SchemeOptionDto> dtos = new ArrayList<>();
        for (SchemeOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Scheme option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme  options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves multiple Scheme options  by their IDs, excluding soft-deleted records.
     * @param ids List of Scheme year IDs
     * @return ResponseEntity containing a Map with a list of SchemeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Scheme year with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<SchemeOptionModel> schemeOptionModels = schemeOptionService.readMany(ids);
        List<SchemeOptionDto> schemeOptionDtos = new ArrayList<>();
        for (SchemeOptionModel model: schemeOptionModels){
            schemeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme options", schemeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Scheme by its ID, excluding soft-deleted records.
     * @param id The ID of the Scheme year to update
     * @param schemeOptionDto The updated Selection Method data
     * @return ResponseEntity containing a Map with the updated SchemeDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Scheme option year Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody SchemeOptionDto schemeOptionDto){
        SchemeOptionModel schemeOptionModel = schemeOptionService.updateOne(id, convertToModel((schemeOptionDto)));
        SchemeOptionDto dto = convertToDto(schemeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme option Year Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Scheme option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Scheme option  based on the provided list of Scheme option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param schemeOptionDtos List of SchemeDto objects containing updated Scheme data
     * @return ResponseEntity containing a Map with the list of updated SchemeDtos and ResponseMessageEntity
     */
    @Operation(summary = "Upadate multiple Scheme options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtos){
        List<SchemeOptionModel> inputModels = new ArrayList<>();
        for (SchemeOptionDto dto: schemeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<SchemeOptionModel> updatedModels = schemeOptionService.updateMany((inputModels));
        List<SchemeOptionDto> dtos = new ArrayList<>();
        for (SchemeOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Schemes Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Scheme options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Updates a SScheme by its ID, including soft-deleted records.
     *
     * @param id The ID of the Scheme to update.
     * @param schemeOptionDto The updated Scheme options data.
     * @return ResponseEntity containing a Map with the updated SchemeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Hard update Scheme by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody SchemeOptionDto schemeOptionDto){
        SchemeOptionModel schemeOptionModel = schemeOptionService.hardUpdateOne(id, convertToModel(schemeOptionDto));
        SchemeOptionDto dto = convertToDto(schemeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme options", dto);
        response.put("Response Message", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Schemes, including soft-deleted records, based on their IDs.
     *
     * @param schemeOptionDtos The list of updated Scheme options data.
     * @return ResponseEntity containing a Map with the list of updated SchemeOptionDtos and ResponseMessageEntity
     */
    @Operation(summary = "Hard update all Schemes")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<SchemeOptionDto> schemeOptionDtos){
        List<SchemeOptionModel> inputModels = new ArrayList<>();
        for (SchemeOptionDto dto: schemeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<SchemeOptionModel> updatedModels = schemeOptionService.hardUpdateAll(inputModels);
        List<SchemeOptionDto> dtos = new ArrayList<>();
        for (SchemeOptionModel schemeOptionModel: updatedModels){
            dtos.add(convertToDto(schemeOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Schemes Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Soft deletes a single Scheme by ID
     * @param id ID of the Scheme to softly delete
     * @return ResponseEntity containing a Map with the soft deleted SchemeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete a single Scheme")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteSchemeOption(@RequestParam Long id){
        SchemeOptionModel deletedSchemeOptionModel = schemeOptionService.softDeleteSchemeOption(id);
        SchemeOptionDto deletedSchemeOptionDto = convertToDto(deletedSchemeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Scheme Option", deletedSchemeOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Scheme by ID
     * @param id ID of the Scheme option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete a single Scheme Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteSchemeOption(@RequestParam Long id){
        schemeOptionService.hardDeleteSchemeOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Scheme by IDs
     * @param ids List of Scheme IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted SchemeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete multiple Scheme options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteProcurementMethodOptions(@RequestBody List<Long> ids){
        List<SchemeOptionModel> deletedSchemeOptionModels = schemeOptionService.softDeleteSchemeOptions(ids);
        List<SchemeOptionDto> deletedSchemeOptionDtos = new ArrayList<>();
        for (SchemeOptionModel model: deletedSchemeOptionModels){
            deletedSchemeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Scheme Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Scheme options", deletedSchemeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Hard deletes multiple Scheme by IDs
     * @param ids List of Scheme options IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete multiple Schemes")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteSelectionMethodOptions(@RequestBody List<Long> ids){
        schemeOptionService.hardDeleteSchemeOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Schemes hard deleted successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}
