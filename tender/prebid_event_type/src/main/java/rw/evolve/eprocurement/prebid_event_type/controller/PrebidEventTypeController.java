package rw.evolve.eprocurement.prebid_event_type.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.prebid_event_type.dto.ResponseMessageDto;
import rw.evolve.eprocurement.prebid_event_type.dto.PrebidEventTypeDto;
import rw.evolve.eprocurement.prebid_event_type.model.PrebidEventTypeModel;
import rw.evolve.eprocurement.prebid_event_type.service.PrebidEventTypeService;
import rw.evolve.eprocurement.prebid_event_type.utils.PrebidEventTypeIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for managing PrebidEventType options.
 * Handles CRUD operations for PrebidEventType option data with soft and hard delete capabilities.
 */
@RestController
@RequestMapping("api/prebid_event_type")
@Tag(name = "Prebid Event Type API")
public class PrebidEventTypeController {

    private final PrebidEventTypeService prebidEventTypeService;

    private ModelMapper modelMapper = new ModelMapper();

    public PrebidEventTypeController(
            PrebidEventTypeService prebidEventTypeService,
            ModelMapper modelMapper
    ){
        this.prebidEventTypeService = prebidEventTypeService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts PrebidEventTypeModel to PrebidEventTypeDto.
     * @param model - PrebidEventTypeModel to convert
     * @return      - Converted PrebidEventTypeDto
     */
    private PrebidEventTypeDto convertToDto(PrebidEventTypeModel model) {
        return modelMapper.map(model, PrebidEventTypeDto.class);
    }

    /**
     * Converts PrebidEventTypeDto to PrebidEventTypeModel.
     * @param prebidEventTypeDto - PrebidEventTypeDto to convert
     * @return                   - Converted PrebidEventTypeModel
     */
    private PrebidEventTypeModel convertToModel(PrebidEventTypeDto prebidEventTypeDto) {
        return modelMapper.map(prebidEventTypeDto, PrebidEventTypeModel.class);
    }

    /**
     * Creates a single PrebidEventType option with a generated ID.
     * @param prebidEventTypeDto - PrebidEventType option data
     * @return                   - Response with success message
     */
    @Operation(summary = "Create a single PrebidEventType option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody PrebidEventTypeDto prebidEventTypeDto) {
        PrebidEventTypeModel prebidEventTypeModel = convertToModel(prebidEventTypeDto);
        prebidEventTypeModel.setId(PrebidEventTypeIdGenerator.generateId());
        prebidEventTypeService.save(prebidEventTypeModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prebid event type created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple PrebidEventType options with generated IDList.
     * @param prebidEventTypeDtoList - List of PrebidEventType option data
     * @return                       - Response with success message
     */
    @Operation(summary = "Create multiple PrebidEventType options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<PrebidEventTypeDto> prebidEventTypeDtoList) {
        List<PrebidEventTypeModel> prebidEventTypeModelList = new ArrayList<>();
        for (PrebidEventTypeDto prebidEventTypeDto : prebidEventTypeDtoList) {
            PrebidEventTypeModel model = convertToModel(prebidEventTypeDto);
            model.setId(PrebidEventTypeIdGenerator.generateId());
            prebidEventTypeModelList.add(model);
        }
        prebidEventTypeService.saveMany(prebidEventTypeModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prebid event types created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a PrebidEventType option by ID (excludes soft-deleted).
     * @param id - PrebidEventType option ID
     * @return   - Response with PrebidEventType option data
     */
    @Operation(summary = "Get a single PrebidEventType option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        PrebidEventTypeModel model = prebidEventTypeService.readOne(id);
        PrebidEventTypeDto prebidEventTypeDto = convertToDto(model);
        return new ResponseEntity<>(prebidEventTypeDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted PrebidEventType options.
     * @return  - Response with list of PrebidEventType option data
     */
    @Operation(summary = "Get all available PrebidEventType options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<PrebidEventTypeModel> prebidEventTypeModelList = prebidEventTypeService.readAll();
        List<PrebidEventTypeDto> prebidEventTypeDtoList = new ArrayList<>();
        for (PrebidEventTypeModel prebidEventTypeModel : prebidEventTypeModelList) {
            prebidEventTypeDtoList.add(convertToDto(prebidEventTypeModel));
        }
        return new ResponseEntity<>(prebidEventTypeDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all PrebidEventType options, including soft-deleted.
     * @return        - Response with list of all PrebidEventType option data
     */
    @Operation(summary = "Get all PrebidEventType options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<PrebidEventTypeModel> modelList = prebidEventTypeService.hardReadAll();
        List<PrebidEventTypeDto> prebidEventTypeDtoList = new ArrayList<>();
        for (PrebidEventTypeModel model : modelList) {
            prebidEventTypeDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(prebidEventTypeDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple PrebidEventType options by ID (excludes soft-deleted).
     * @param idList - List of PrebidEventType option ID
     * @return       - Response with list of PrebidEventType option data
     */
    @Operation(summary = "Get multiple PrebidEventType options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<PrebidEventTypeModel> prebidEventTypeModelList = prebidEventTypeService.readMany(idList);
        List<PrebidEventTypeDto> prebidEventTypeDtoList = new ArrayList<>();
        for (PrebidEventTypeModel model : prebidEventTypeModelList) {
            prebidEventTypeDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(prebidEventTypeDtoList, HttpStatus.OK);
    }

    /**
     * Updates a PrebidEventType option by ID (excludes soft-deleted).
     *
     * @param prebidEventTypeDto - Updated PrebidEventType option data
     * @return                   - Response with updated PrebidEventType option data
     */
    @Operation(summary = "Update a single PrebidEventType option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody PrebidEventTypeDto prebidEventTypeDto){
        String modelId = prebidEventTypeDto.getId();
        PrebidEventTypeModel savedModel = prebidEventTypeService.readOne(modelId);
        savedModel.setName(prebidEventTypeDto.getName());
        savedModel.setDescription(prebidEventTypeDto.getDescription());
        prebidEventTypeService.updateOne(savedModel);
        PrebidEventTypeDto prebidEventTypeDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(prebidEventTypeDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple PrebidEventType options (excludes soft-deleted).
     * @param prebidEventTypeDtoList - a List of updated PrebidEventType option data
     * @return                       - Response with list of updated PrebidEventType option data
     */
    @Operation(summary = "Update multiple PrebidEventType options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<PrebidEventTypeDto> prebidEventTypeDtoList) {
        List<PrebidEventTypeModel> inputModelList = new ArrayList<>();
        for (PrebidEventTypeDto prebidEventTypeDto : prebidEventTypeDtoList) {
            inputModelList.add(convertToModel(prebidEventTypeDto));
        }
        List<PrebidEventTypeModel> updatedModelList = prebidEventTypeService.updateMany(inputModelList);
        List<PrebidEventTypeDto> prebidEventTypeDtoArrayList = new ArrayList<>();
        for (PrebidEventTypeModel model : updatedModelList) {
            prebidEventTypeDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(prebidEventTypeDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a PrebidEventType option by ID, including soft-deleted.
     *
     * @param prebidEventTypeDto - Updated PrebidEventType option data
     * @return                   - Response with updated PrebidEventType option data
     */
    @Operation(summary = "Update a single PrebidEventType option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody PrebidEventTypeDto prebidEventTypeDto) {
        PrebidEventTypeModel prebidEventTypeModel = prebidEventTypeService.hardUpdate(convertToModel(prebidEventTypeDto));
        PrebidEventTypeDto prebidEventTypeDto1 = convertToDto(prebidEventTypeModel);
        return new ResponseEntity<>(prebidEventTypeDto1, HttpStatus.OK);
    }

    /**
     * Updates all PrebidEventType options, including soft-deleted.
     * @param prebidEventTypeDtoList - List of updated PrebidEventType option data
     * @return                       - Response with list of updated PrebidEventType option data
     */
    @Operation(summary = "Update all PrebidEventType options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<PrebidEventTypeDto> prebidEventTypeDtoList) {
        List<PrebidEventTypeModel> inputModelList = new ArrayList<>();
        for (PrebidEventTypeDto prebidEventTypeDto : prebidEventTypeDtoList) {
            inputModelList.add(convertToModel(prebidEventTypeDto));
        }
        List<PrebidEventTypeModel> updatedModelList = prebidEventTypeService.hardUpdateAll(inputModelList);
        List<PrebidEventTypeDto> updatedPrebidEventTypeDtoList = new ArrayList<>();
        for (PrebidEventTypeModel model : updatedModelList) {
            updatedPrebidEventTypeDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedPrebidEventTypeDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a PrebidEventType option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single PrebidEventType option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        PrebidEventTypeModel deletePrebidEventTypeModel = prebidEventTypeService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prebid event type soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a PrebidEventType option by ID.
     * @param id       - PrebidEventType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single PrebidEventType option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        prebidEventTypeService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prebid event type hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple PrebidEventType options by ID.
     * @param idList    - List of PrebidEventType option IDList
     * @return          - Response with list of soft-deleted PrebidEventType option data
     */
    @Operation(summary = "Soft delete multiple PrebidEventType options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<PrebidEventTypeModel> deletedPrebidEventTypeModelList = prebidEventTypeService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Prebid event types soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple PrebidEventType options by ID.
     * @param idList   - List of PrebidEventType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple PrebidEventType options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        prebidEventTypeService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Prebid event types hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all PrebidEventType options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all PrebidEventType options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        prebidEventTypeService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Prebid event types hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}