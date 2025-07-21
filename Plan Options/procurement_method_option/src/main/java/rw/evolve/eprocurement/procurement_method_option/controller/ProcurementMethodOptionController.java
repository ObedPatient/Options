/**
 * REST API controller for managing Procurement Method Options.
 * Provides endpoints for creating, retrieving, deleting and updating procurement Method option data, supporting both soft and hard operations.
 */
package rw.evolve.eprocurement.procurement_method_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_method_option.dto.ProcurementMethodOptionDto;
import rw.evolve.eprocurement.procurement_method_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_method_option.model.ProcurementMethodOptionModel;
import rw.evolve.eprocurement.procurement_method_option.service.ProcurementMethodOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/procurement_method_option")
@Tag(name = "Procurement Method Option Api")
public class ProcurementMethodOptionController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ProcurementMethodOptionService procurementMethodOptionService;

    /**
     * Converts a ProcurementMethodOptionModel to ProcurementMethodOptionDto.
     * @param model The ProcurementMethodOptionModel to convert.
     * @return The converted ProcurementMethodOptionDto.
     */
    private ProcurementMethodOptionDto convertToDto(ProcurementMethodOptionModel model){
        return modelMapper.map(model, ProcurementMethodOptionDto.class);
    }

    /**
     * Converts a ProcurementMethodOptionDto to ProcurementMethodOptionModel.
     * @param dto The ProcurementMethodOptionDto to convert.
     * @return The converted ProcurementMethodOptionModel.
     */
    private ProcurementMethodOptionModel convertToModel(ProcurementMethodOptionDto dto){
        return modelMapper.map(dto, ProcurementMethodOptionModel.class);
    }

    /**
     * Creates a single procurement Method option
     * @param procurementMethodOptionDto DTO containing procurement Method option data
     * @return ResponseEntity containing a Map with the created ProcurementMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Procurement Type option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createProcurementType(@Valid @RequestBody ProcurementMethodOptionDto procurementMethodOptionDto){
        ProcurementMethodOptionModel procurementMethodOptionModel = convertToModel(procurementMethodOptionDto);
        ProcurementMethodOptionModel createdprocurementMethodOptionModel = procurementMethodOptionService.createProcurementMethod(procurementMethodOptionModel);
        ProcurementMethodOptionDto createdprocurementMethodOptionDto = convertToDto(createdprocurementMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method Options", createdprocurementMethodOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Procurement Method options
     * @param procurementMethodOptionDtos List of Procurement Method option DTOs
     * @return ResponseEntity containing a Map with the created list of ProcurementMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Procurement types Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createProcurementMethods(@Valid @RequestBody List<ProcurementMethodOptionDto> procurementMethodOptionDtos ){
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = new ArrayList<>();
        for (ProcurementMethodOptionDto dto: procurementMethodOptionDtos){
            procurementMethodOptionModels.add(convertToModel(dto));
        }
        List<ProcurementMethodOptionModel> createdModels = procurementMethodOptionService.createProcurementMethods(procurementMethodOptionModels);
        List<ProcurementMethodOptionDto> createdProcurementMethodDtos = new ArrayList<>();
        for (ProcurementMethodOptionModel model: createdModels){
            createdProcurementMethodDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Procurement Method options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method Options", createdProcurementMethodDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves a Procurement Method option by its ID, excluding soft-deleted procurement Methods.
     * @param id The ID of the procurement Method to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the ProcurementMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Procurement Method  API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("ProcurementMethodOptionId") Long id){
        ProcurementMethodOptionModel model = procurementMethodOptionService.readOne(id);
        ProcurementMethodOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Procurement Methods.
     * @return ResponseEntity containing a Map with a list of ProcurementMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Procurement Methods Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionService.readAll();
        List<ProcurementMethodOptionDto> procurementMethodOptionDtos = new ArrayList<>();
        for (ProcurementMethodOptionModel procurementMethodOptionModel: procurementMethodOptionModels){
            procurementMethodOptionDtos.add(convertToDto(procurementMethodOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Procurement Method options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method Options", procurementMethodOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Procurement Methods, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of ProcurementMethodDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Procurement Methods Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<ProcurementMethodOptionModel> models = procurementMethodOptionService.hardReadAll();
        List<ProcurementMethodOptionDto> dtos = new ArrayList<>();
        for (ProcurementMethodOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Procurement Method option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Methods  options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves multiple procurement type options  by their IDs, excluding soft-deleted records.
     * @param ids List of procurement type year IDs
     * @return ResponseEntity containing a Map with a list of ProcurementTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Procurement types year with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<ProcurementMethodOptionModel> procurementMethodOptionModels = procurementMethodOptionService.readMany(ids);
        List<ProcurementMethodOptionDto> procurementMethodOptionDtos = new ArrayList<>();
        for (ProcurementMethodOptionModel model: procurementMethodOptionModels){
            procurementMethodOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method options", procurementMethodOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Procurement Method by its ID, excluding soft-deleted records.
     * @param id The ID of the procurement Method year to update
     * @param procurementMethodOptionDto The updated procurement Method data
     * @return ResponseEntity containing a Map with the updated ProcurementMethodDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Procurement Method option year Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody ProcurementMethodOptionDto procurementMethodOptionDto){
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionService.updateOne(id, convertToModel((procurementMethodOptionDto)));
        ProcurementMethodOptionDto dto = convertToDto(procurementMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method option Year Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple procurement Method option  based on the provided list of procurement Method option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param procurementMethodOptionDtos List of ProcurementMethodDto objects containing updated procurement Method data
     * @return ResponseEntity containing a Map with the list of updated ProcurementMethodDtos and ResponseMessageEntity
     */
    @Operation(summary = "Upadate multiple Procurement Method options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<ProcurementMethodOptionDto> procurementMethodOptionDtos){
        List<ProcurementMethodOptionModel> inputModels = new ArrayList<>();
        for (ProcurementMethodOptionDto dto: procurementMethodOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<ProcurementMethodOptionModel> updatedModels = procurementMethodOptionService.updateMany((inputModels));
        List<ProcurementMethodOptionDto> dtos = new ArrayList<>();
        for (ProcurementMethodOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Procurement Methods Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Updates a procurement Method by its ID, including soft-deleted records.
     *
     * @param id The ID of the procurement Method to update.
     * @param procurementMethodOptionDto The updated Procurement Method options data.
     * @return ResponseEntity containing a Map with the updated ProcurementMethodOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Hard update procurement Method by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody ProcurementMethodOptionDto procurementMethodOptionDto){
        ProcurementMethodOptionModel procurementMethodOptionModel = procurementMethodOptionService.hardUpdateOne(id, convertToModel(procurementMethodOptionDto));
        ProcurementMethodOptionDto dto = convertToDto(procurementMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method options", dto);
        response.put("Response Message", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all procurement Methods, including soft-deleted records, based on their IDs.
     *
     * @param procurementTypeOptionDtos The list of updated Procurement Method options data.
     * @return ResponseEntity containing a Map with the list of updated ProcurementMethodOptionDtos and ResponseMessageEntity
     */
    @Operation(summary = "Hard update all procurement Methods")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<ProcurementMethodOptionDto> procurementTypeOptionDtos){
        List<ProcurementMethodOptionModel> inputModels = new ArrayList<>();
        for (ProcurementMethodOptionDto dto: procurementTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<ProcurementMethodOptionModel> updatedModels = procurementMethodOptionService.hardUpdateAll(inputModels);
        List<ProcurementMethodOptionDto> dtos = new ArrayList<>();
        for (ProcurementMethodOptionModel procurementMethodOptionModel: updatedModels){
            dtos.add(convertToDto(procurementMethodOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Methods Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Soft deletes a single procurement Method by ID
     * @param id ID of the procurement Method to softly delete
     * @return ResponseEntity containing a Map with the soft deleted ProcurementMethodOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete a single procurement Method")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteProcurementTypeOption(@RequestParam Long id){
        ProcurementMethodOptionModel deletedProcurementMethodOptionModel = procurementMethodOptionService.softDeleteProcurementMethodOption(id);
        ProcurementMethodOptionDto deletedProcurementMethodOptionDto = convertToDto(deletedProcurementMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method", deletedProcurementMethodOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single procurement Method by ID
     * @param id ID of the Procurement Method option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete a single procurement Method Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteProcurementMethodOption(@RequestParam Long id){
        procurementMethodOptionService.hardDeleteProcurementMethodOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Procurement Methods by IDs
     * @param ids List of procurement Method IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted ProcurementMethodOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete multiple Procurement Method options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteProcurementMethodOptions(@RequestBody List<Long> ids){
        List<ProcurementMethodOptionModel> deletedProcurementMethodOptionModels = procurementMethodOptionService.softDeleteProcurementMethodOptions(ids);
        List<ProcurementMethodOptionDto> deletedProcurementMethodOptionDtos = new ArrayList<>();
        for (ProcurementMethodOptionModel model: deletedProcurementMethodOptionModels){
            deletedProcurementMethodOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Method options", deletedProcurementMethodOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Hard deletes multiple procurement Methods by IDs
     * @param ids List of Procurement Method options IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete multiple procurement Methods")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteProcurementMethodOptions(@RequestBody List<Long> ids){
        procurementMethodOptionService.hardDeleteProcurementMethodOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement Method hard deleted successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

}
