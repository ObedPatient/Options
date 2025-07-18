/**
 * REST API controller for managing Procurement Type Options.
 * Provides endpoints for creating, retrieving, deleting and updating procurement type option data, supporting both soft and hard operations.
 */
package rw.evolve.eprocurement.procurement_type_option.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_type_option.dto.ProcurementTypeOptionDto;
import rw.evolve.eprocurement.procurement_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_type_option.model.ProcurementTypeOptionModel;
import rw.evolve.eprocurement.procurement_type_option.service.ProcurementTypeOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/procurement_type_option")
@Tag(name = "Procurement Type Option Api")
public class ProcurementTypeOptionController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ProcurementTypeOptionService procurementTypeOptionService;

    /**
     * Converts a ProcurementTypeOptionModel to ProcurementTypeOptionDto.
     * @param model The ProcurementTypeOptionModel to convert.
     * @return The converted ProcurementTypeOptionDto.
     */
    private ProcurementTypeOptionDto convertToDto(ProcurementTypeOptionModel model){
        return modelMapper.map(model, ProcurementTypeOptionDto.class);
    }

    /**
     * Converts a ProcurementTypeOptionDto to ProcurementTypeOptionModel.
     * @param dto The ProcurementTypeOptionDto to convert.
     * @return The converted ProcurementTypeOptionModel.
     */
    private ProcurementTypeOptionModel convertToModel(ProcurementTypeOptionDto dto){
        return modelMapper.map(dto, ProcurementTypeOptionModel.class);
    }

    /**
     * Creates a single procurement Type option
     * @param procurementTypeOptionDto DTO containing procurement type option data
     * @return ResponseEntity containing a Map with the created ProcurementTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Procurement Type option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createProcurementType(@Valid @RequestBody ProcurementTypeOptionDto procurementTypeOptionDto){
        ProcurementTypeOptionModel procurementTypeOptionModel = convertToModel(procurementTypeOptionDto);
        ProcurementTypeOptionModel createdFiscalYearModel = procurementTypeOptionService.createProcurementType(procurementTypeOptionModel);
        ProcurementTypeOptionDto createdFiscalYearDto = convertToDto(createdFiscalYearModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type Options", createdFiscalYearDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Procurement type options
     * @param procurementTypeOptionDtos List of fiscal year DTOs
     * @return ResponseEntity containing a Map with the created list of ProcurementTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Procurement types Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createProcurementTypes(@Valid @RequestBody List<ProcurementTypeOptionDto> procurementTypeOptionDtos ){
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = new ArrayList<>();
        for (ProcurementTypeOptionDto dto: procurementTypeOptionDtos){
            procurementTypeOptionModels.add(convertToModel(dto));
        }
        List<ProcurementTypeOptionModel> createdModels = procurementTypeOptionService.createProcurementTypes(procurementTypeOptionModels);
        List<ProcurementTypeOptionDto> createdProcurementTypesDtos = new ArrayList<>();
        for (ProcurementTypeOptionModel model: createdModels){
            createdProcurementTypesDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Procurement type options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type Options", createdProcurementTypesDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves a Procurement type option by its ID, excluding soft-deleted procurement types.
     * @param id The ID of the procurement type to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the ProcurementTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Fiscal Year API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("ProcurementTypeOption") Long id){
        ProcurementTypeOptionModel model = procurementTypeOptionService.readOne(id);
        ProcurementTypeOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Procurement types.
     * @return ResponseEntity containing a Map with a list of ProcurementTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Procurement Types Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionService.readAll();
        List<ProcurementTypeOptionDto> procurementTypeOptionDtos = new ArrayList<>();
        for (ProcurementTypeOptionModel procurementTypeOptionModel: procurementTypeOptionModels){
            procurementTypeOptionDtos.add(convertToDto(procurementTypeOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Procurement type options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type Options", procurementTypeOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Procurement types, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of ProcurementTypeDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Procurement types Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<ProcurementTypeOptionModel> models = procurementTypeOptionService.hardReadAll();
        List<ProcurementTypeOptionDto> dtos = new ArrayList<>();
        for (ProcurementTypeOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Procurement type option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement types  options", dtos);
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
        List<ProcurementTypeOptionModel> procurementTypeOptionModels = procurementTypeOptionService.readMany(ids);
        List<ProcurementTypeOptionDto> procurementTypeOptionDtos = new ArrayList<>();
        for (ProcurementTypeOptionModel model: procurementTypeOptionModels){
            procurementTypeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement type options", procurementTypeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Procurement type by its ID, excluding soft-deleted records.
     * @param id The ID of the procurement type year to update
     * @param procurementTypeOptionDto The updated procurement type data
     * @return ResponseEntity containing a Map with the updated ProcurementTypeDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Procurement type option year Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody ProcurementTypeOptionDto procurementTypeOptionDto){
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionService.updateOne(id, convertToModel((procurementTypeOptionDto)));
        ProcurementTypeOptionDto dto = convertToDto(procurementTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type option Year Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple procurement type option  based on the provided list of procurement type option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param procurementTypeOptionDtos List of ProcurementTypeDto objects containing updated procurement type data
     * @return ResponseEntity containing a Map with the list of updated ProcurementTypeDtos and ResponseMessageEntity
     */
    @Operation(summary = "Upadate multiple Procurement type options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<ProcurementTypeOptionDto> procurementTypeOptionDtos){
        List<ProcurementTypeOptionModel> inputModels = new ArrayList<>();
        for (ProcurementTypeOptionDto dto: procurementTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<ProcurementTypeOptionModel> updatedModels = procurementTypeOptionService.updateMany((inputModels));
        List<ProcurementTypeOptionDto> dtos = new ArrayList<>();
        for (ProcurementTypeOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Procurement types Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Updates a procurement type by its ID, including soft-deleted records.
     *
     * @param id The ID of the procurement type to update.
     * @param procurementTypeOptionDto The updated fiscal year data.
     * @return ResponseEntity containing a Map with the updated ProcurementTypeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Hard update procurement type by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody ProcurementTypeOptionDto procurementTypeOptionDto){
        ProcurementTypeOptionModel procurementTypeOptionModel = procurementTypeOptionService.hardUpdateOne(id, convertToModel(procurementTypeOptionDto));
        ProcurementTypeOptionDto dto = convertToDto(procurementTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement Type options", dto);
        response.put("Response Message", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all procurement types, including soft-deleted records, based on their IDs.
     *
     * @param procurementTypeOptionDtos The list of updated fiscal year data.
     * @return ResponseEntity containing a Map with the list of updated ProcurementTypeOptionDtos and ResponseMessageEntity
     */
    @Operation(summary = "Hard update all procurement types")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<ProcurementTypeOptionDto> procurementTypeOptionDtos){
        List<ProcurementTypeOptionModel> inputModels = new ArrayList<>();
        for (ProcurementTypeOptionDto dto: procurementTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<ProcurementTypeOptionModel> updatedModels = procurementTypeOptionService.hardUpdateAll(inputModels);
        List<ProcurementTypeOptionDto> dtos = new ArrayList<>();
        for (ProcurementTypeOptionModel fiscalYearModel: updatedModels){
            dtos.add(convertToDto(fiscalYearModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement types Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement type Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Soft deletes a single procurement type by ID
     * @param id ID of the procurement type to softly delete
     * @return ResponseEntity containing a Map with the soft deleted ProcurementTypeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete a single procurement type")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteProcurementTypeOption(@RequestParam Long id){
        ProcurementTypeOptionModel deletedProcurementTypeOptionModel = procurementTypeOptionService.softDeleteProcurementTypeOption(id);
        ProcurementTypeOptionDto deletedProcurementTypeOptionDto = convertToDto(deletedProcurementTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Procurement type", deletedProcurementTypeOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single procurement type by ID
     * @param id ID of the fiscal year to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete a single procurement type Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteProcurementTypeOption(@RequestParam Long id){
        procurementTypeOptionService.hardDeleteProcurementTypeOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Procurement types by IDs
     * @param ids List of procurement type IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted ProcurementTypeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete multiple fiscal years")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteProcurementTypeOptions(@RequestBody List<Long> ids){
        List<ProcurementTypeOptionModel> deletedProcurementTypeOptionModels = procurementTypeOptionService.softDeleteProcurementTypeOptions(ids);
        List<ProcurementTypeOptionDto> deletedProcurementTypeOptionDtos = new ArrayList<>();
        for (ProcurementTypeOptionModel model: deletedProcurementTypeOptionModels){
            deletedProcurementTypeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Procurement type options", deletedProcurementTypeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Hard deletes multiple procurement types by IDs
     * @param ids List of fiscal year IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete multiple procurement types")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteProcurementTypeOptions(@RequestBody List<Long> ids){
        procurementTypeOptionService.hardDeleteProcurementTypeOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement type hard deleted successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }





}
