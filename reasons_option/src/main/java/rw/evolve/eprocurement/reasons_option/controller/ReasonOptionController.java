/**
 * REST API controller for managing ReasonOption options.
 * Handles CRUD operations for ReasonOption option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.reasons_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.reasons_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.reasons_option.dto.ReasonOptionDto;
import rw.evolve.eprocurement.reasons_option.model.ReasonOptionModel;
import rw.evolve.eprocurement.reasons_option.service.ReasonOptionService;
import rw.evolve.eprocurement.reasons_option.utils.ReasonOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/reason_option")
@Tag(name = "Reason Option API")
public class ReasonOptionController {

    @Autowired
    private ReasonOptionService reasonOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts ReasonOptionModel to ReasonOptionDto.
     * @param model - ReasonOptionModel to convert
     * @return      - Converted ReasonOptionDto
     */
    private ReasonOptionDto convertToDto(ReasonOptionModel model) {
        return modelMapper.map(model, ReasonOptionDto.class);
    }

    /**
     * Converts ReasonOptionDto to ReasonOptionModel.
     * @param reasonOptionDto   - ReasonOptionDto to convert
     * @return                  - Converted ReasonOptionModel
     */
    private ReasonOptionModel convertToModel(ReasonOptionDto reasonOptionDto) {
        return modelMapper.map(reasonOptionDto, ReasonOptionModel.class);
    }

    /**
     * Creates a single Reason option with a generated ID.
     * @param reasonOptionDto - Reason option data
     * @return                - Response with success message
     */
    @Operation(summary = "Create a single Reason option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ReasonOptionDto reasonOptionDto) {
        ReasonOptionModel reasonOptionModel = convertToModel(reasonOptionDto);
        reasonOptionModel.setId(ReasonOptionIdGenerator.generateId());
        reasonOptionService.save(reasonOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Reason option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Reason options with generated IDList.
     * @param reasonOptionDtoList - List of Reason option data
     * @return                    - Response with success message
     */
    @Operation(summary = "Create multiple Reason options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ReasonOptionDto> reasonOptionDtoList) {
        List<ReasonOptionModel> reasonOptionModelList = new ArrayList<>();
        for (ReasonOptionDto reasonOptionDto : reasonOptionDtoList) {
            ReasonOptionModel model = convertToModel(reasonOptionDto);
            model.setId(ReasonOptionIdGenerator.generateId());
            reasonOptionModelList.add(model);
        }
        reasonOptionService.saveMany(reasonOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Reason options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Reason option by ID (excludes soft-deleted).
     * @param id - Reason option ID
     * @return   - Response with Reason option data
     */
    @Operation(summary = "Get a single Reason option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ReasonOptionModel model = reasonOptionService.readOne(id);
        ReasonOptionDto reasonOptionDto = convertToDto(model);
        return new ResponseEntity<>(reasonOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Reason options.
     * @return  - Response with list of Reason option data
     */
    @Operation(summary = "Get all available Reason options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ReasonOptionModel> reasonOptionModelList = reasonOptionService.readAll();
        List<ReasonOptionDto> reasonOptionDtoList = new ArrayList<>();
        for (ReasonOptionModel reasonOptionModel : reasonOptionModelList) {
            reasonOptionDtoList.add(convertToDto(reasonOptionModel));
        }
        return new ResponseEntity<>(reasonOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Reason options, including soft-deleted.
     * @return        - Response with list of all Reason option data
     */
    @Operation(summary = "Get all Reason options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ReasonOptionModel> modelList = reasonOptionService.hardReadAll();
        List<ReasonOptionDto> reasonOptionDtoList = new ArrayList<>();
        for (ReasonOptionModel model : modelList) {
            reasonOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(reasonOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Reason options by ID (excludes soft-deleted).
     * @param idList - List of Reason option ID
     * @return       - Response with list of Reason option data
     */
    @Operation(summary = "Get multiple Reason options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ReasonOptionModel> reasonOptionModelList = reasonOptionService.readMany(idList);
        List<ReasonOptionDto> reasonOptionDtoList = new ArrayList<>();
        for (ReasonOptionModel model : reasonOptionModelList) {
            reasonOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(reasonOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Reason option by ID (excludes soft-deleted).
     *
     * @param reasonOptionDto - Updated Reason option data
     * @return                - Response with updated Reason option data
     */
    @Operation(summary = "Update a single Reason option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ReasonOptionDto reasonOptionDto){
        String modelId = reasonOptionDto.getId();
        ReasonOptionModel savedModel = reasonOptionService.readOne(modelId);
        savedModel.setName(reasonOptionDto.getName());
        savedModel.setDescription(reasonOptionDto.getDescription());
        reasonOptionService.updateOne(savedModel);
        ReasonOptionDto reasonOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(reasonOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple Reason options (excludes soft-deleted).
     * @param reasonOptionDtoList     - a List of updated Reason option data
     * @return Response with list     - of updated Reason option data
     */
    @Operation(summary = "Update multiple Reason options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ReasonOptionDto> reasonOptionDtoList) {
        List<ReasonOptionModel> inputModelList = new ArrayList<>();
        for (ReasonOptionDto reasonOptionDto : reasonOptionDtoList) {
            inputModelList.add(convertToModel(reasonOptionDto));
        }
        List<ReasonOptionModel> updatedModelList = reasonOptionService.updateMany(inputModelList);
        List<ReasonOptionDto> reasonOptionDtoArrayList = new ArrayList<>();
        for (ReasonOptionModel model : updatedModelList) {
            reasonOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(reasonOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a Reason option by ID, including soft-deleted.
     *
     * @param reasonOptionDto   - Updated Reason option data
     * @return Response with    - updated Reason option data
     */
    @Operation(summary = "Update a single Reason option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ReasonOptionDto reasonOptionDto) {
        ReasonOptionModel reasonOptionModel = reasonOptionService.hardUpdate(convertToModel(reasonOptionDto));
        ReasonOptionDto reasonOptionDto1 = convertToDto(reasonOptionModel);
        return new ResponseEntity<>(reasonOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all Reason options, including soft-deleted.
     * @param reasonOptionDtoList   - List of updated Reason option data
     * @return                      - Response with list of updated Reason option data
     */
    @Operation(summary = "Update all Reason options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ReasonOptionDto> reasonOptionDtoList) {
        List<ReasonOptionModel> inputModelList = new ArrayList<>();
        for (ReasonOptionDto reasonOptionDto : reasonOptionDtoList) {
            inputModelList.add(convertToModel(reasonOptionDto));
        }
        List<ReasonOptionModel> updatedModelList = reasonOptionService.hardUpdateAll(inputModelList);
        List<ReasonOptionDto> updatedReasonOptionDtoList = new ArrayList<>();
        for (ReasonOptionModel model : updatedModelList) {
            updatedReasonOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedReasonOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Reason option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Reason option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        ReasonOptionModel deleteReasonOptionModel = reasonOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Reason option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Reason option by ID.
     * @param id       - Reason option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single Reason option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        reasonOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Reason option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Reason options by ID.
     * @param idList    - List of Reason option IDList
     * @return          - Response with list of soft-deleted Reason option data
     */
    @Operation(summary = "Soft delete multiple Reason options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ReasonOptionModel> deletedReasonOptionModelList = reasonOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Reason options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Reason options by ID.
     * @param idList   - List of Reason option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple Reason options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        reasonOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Reason options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Reason options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all Reason options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        reasonOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Reason options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}