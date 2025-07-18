package rw.evolve.eprocurement.selection_method_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.selection_method_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.selection_method_option.dto.SelectionMethodOptionDto;
import rw.evolve.eprocurement.selection_method_option.model.SelectionMethodOptionModel;
import rw.evolve.eprocurement.selection_method_option.service.SelectionMethodOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/selection_method_option")
@Tag(name = "Selection Method Option Api")
public class SelectionMethodOptionController {


    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private SelectionMethodOptionService selectionMethodOptionService;

    /**
     * Converts a SelectionMethodOptionModel to SelectionMethodOptionDto.
     * @param model The SelectionMethodOptionModel to convert.
     * @return The converted SelectionMethodOptionDto.
     */
    private SelectionMethodOptionDto convertToDto(SelectionMethodOptionModel model){
        return modelMapper.map(model, SelectionMethodOptionDto.class);
    }

    /**
     * Converts a SelectionMethodOptionDto to SelectionMethodOptionModel.
     * @param dto The SelectionMethodOptionDto to convert.
     * @return The converted SelectionMethodOptionModel.
     */
    private SelectionMethodOptionModel convertToModel(SelectionMethodOptionDto dto){
        return modelMapper.map(dto, SelectionMethodOptionModel.class);
    }

    /**
     * Creates a single Selection Method option
     * @param selectionMethodOptionDto DTO containing Selection Method option data
     * @return ResponseEntity containing a Map with the created SelectionMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Selection method option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createSelectionType(@Valid @RequestBody SelectionMethodOptionDto selectionMethodOptionDto){
        SelectionMethodOptionModel selectionMethodOptionModel = convertToModel(selectionMethodOptionDto);
        SelectionMethodOptionModel createdSelectionMethodOptionModel = selectionMethodOptionService.createSelectionMethod(selectionMethodOptionModel);
        SelectionMethodOptionDto createdSelectionMethodOptionDto = convertToDto(createdSelectionMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method Options", createdSelectionMethodOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Selection Method options
     * @param selectionMethodOptionDtos List of Selection Method option DTOs
     * @return ResponseEntity containing a Map with the created list of SelectionMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Selection types Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createSelectionMethods(@Valid @RequestBody List<SelectionMethodOptionDto> selectionMethodOptionDtos ){
        List<SelectionMethodOptionModel> selectionMethodOptionModels = new ArrayList<>();
        for (SelectionMethodOptionDto dto: selectionMethodOptionDtos){
            selectionMethodOptionModels.add(convertToModel(dto));
        }
        List<SelectionMethodOptionModel> createdModels = selectionMethodOptionService.createSelectionMethods(selectionMethodOptionModels);
        List<SelectionMethodOptionDto> createdSelectionMethodDtos = new ArrayList<>();
        for (SelectionMethodOptionModel model: createdModels){
            createdSelectionMethodDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Selection Method options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method Options", createdSelectionMethodDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves a Selection Method option by its ID, excluding soft-deleted Selection Methods.
     * @param id The ID of the Selection Method to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the SelectionMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Selection Method  API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("SelectionMethodOptionId") Long id){
        SelectionMethodOptionModel model = selectionMethodOptionService.readOne(id);
        SelectionMethodOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Selection Methods.
     * @return ResponseEntity containing a Map with a list of SelectionMethodOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Selection Methods Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<SelectionMethodOptionModel> selectionMethodOptionModels = selectionMethodOptionService.readAll();
        List<SelectionMethodOptionDto> selectionMethodOptionDtos = new ArrayList<>();
        for (SelectionMethodOptionModel selectionMethodOptionModel: selectionMethodOptionModels){
            selectionMethodOptionDtos.add(convertToDto(selectionMethodOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Selection Method options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method Options", selectionMethodOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Selection Methods, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of SelectionMethodDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Selection Methods Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<SelectionMethodOptionModel> models = selectionMethodOptionService.hardReadAll();
        List<SelectionMethodOptionDto> dtos = new ArrayList<>();
        for (SelectionMethodOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Selection Method option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Methods  options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves multiple Selection type options  by their IDs, excluding soft-deleted records.
     * @param ids List of Selection type year IDs
     * @return ResponseEntity containing a Map with a list of SelectionTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Selection types year with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<SelectionMethodOptionModel> selectionMethodOptionModels = selectionMethodOptionService.readMany(ids);
        List<SelectionMethodOptionDto> selectionMethodOptionDtos = new ArrayList<>();
        for (SelectionMethodOptionModel model: selectionMethodOptionModels){
            selectionMethodOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method options", selectionMethodOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Selection Method by its ID, excluding soft-deleted records.
     * @param id The ID of the Selection Method year to update
     * @param selectionMethodOptionDto The updated Selection Method data
     * @return ResponseEntity containing a Map with the updated SelectionMethodDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Selection Method option year Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody SelectionMethodOptionDto selectionMethodOptionDto){
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionService.updateOne(id, convertToModel((selectionMethodOptionDto)));
        SelectionMethodOptionDto dto = convertToDto(selectionMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method option Year Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Selection Method option  based on the provided list of Selection Method option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param selectionMethodOptionDtos List of SelectionMethodDto objects containing updated Selection Method data
     * @return ResponseEntity containing a Map with the list of updated SelectionMethodDtos and ResponseMessageEntity
     */
    @Operation(summary = "Upadate multiple Selection Method options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<SelectionMethodOptionDto> selectionMethodOptionDtos){
        List<SelectionMethodOptionModel> inputModels = new ArrayList<>();
        for (SelectionMethodOptionDto dto: selectionMethodOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<SelectionMethodOptionModel> updatedModels = selectionMethodOptionService.updateMany((inputModels));
        List<SelectionMethodOptionDto> dtos = new ArrayList<>();
        for (SelectionMethodOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Selection Methods Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Updates a Selection Method by its ID, including soft-deleted records.
     *
     * @param id The ID of the Selection Method to update.
     * @param selectionMethodOptionDto The updated Selection Method options data.
     * @return ResponseEntity containing a Map with the updated SelectionMethodOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Hard update Selection Method by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody SelectionMethodOptionDto selectionMethodOptionDto){
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionService.hardUpdateOne(id, convertToModel(selectionMethodOptionDto));
        SelectionMethodOptionDto dto = convertToDto(selectionMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method options", dto);
        response.put("Response Message", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Selection Methods, including soft-deleted records, based on their IDs.
     *
     * @param selectionTypeOptionDtos The list of updated Selection Method options data.
     * @return ResponseEntity containing a Map with the list of updated SelectionMethodOptionDtos and ResponseMessageEntity
     */
    @Operation(summary = "Hard update all Selection Methods")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<SelectionMethodOptionDto> selectionTypeOptionDtos){
        List<SelectionMethodOptionModel> inputModels = new ArrayList<>();
        for (SelectionMethodOptionDto dto: selectionTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<SelectionMethodOptionModel> updatedModels = selectionMethodOptionService.hardUpdateAll(inputModels);
        List<SelectionMethodOptionDto> dtos = new ArrayList<>();
        for (SelectionMethodOptionModel selectionMethodOptionModel: updatedModels){
            dtos.add(convertToDto(selectionMethodOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Methods Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Soft deletes a single Selection Method by ID
     * @param id ID of the Selection Method to softly delete
     * @return ResponseEntity containing a Map with the soft deleted SelectionMethodOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete a single Selection Method")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteSelectionTypeOption(@RequestParam Long id){
        SelectionMethodOptionModel deletedSelectionMethodOptionModel = selectionMethodOptionService.softDeleteSelectionMethodOption(id);
        SelectionMethodOptionDto deletedSelectionMethodOptionDto = convertToDto(deletedSelectionMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method", deletedSelectionMethodOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Selection Method by ID
     * @param id ID of the Selection Method option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete a single Selection Method Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteSelectionMethodOption(@RequestParam Long id){
        selectionMethodOptionService.hardDeleteSelectionMethodOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Selection Methods by IDs
     * @param ids List of Selection Method IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted SelectionMethodOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete multiple Selection Method options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteProcurementMethodOptions(@RequestBody List<Long> ids){
        List<SelectionMethodOptionModel> deletedSelectionMethodOptionModels = selectionMethodOptionService.softDeleteSelectionMethodOptions(ids);
        List<SelectionMethodOptionDto> deletedSelectionMethodOptionDtos = new ArrayList<>();
        for (SelectionMethodOptionModel model: deletedSelectionMethodOptionModels){
            deletedSelectionMethodOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Selection Method options", deletedSelectionMethodOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Hard deletes multiple Selection Methods by IDs
     * @param ids List of Selection Method options IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete multiple Selection Methods")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteSelectionMethodOptions(@RequestBody List<Long> ids){
        selectionMethodOptionService.hardDeleteSelectionMethodOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection Method hard deleted successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}
