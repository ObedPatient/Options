/**
 * REST API controller for managing ClarificationRequestStatusOption options.
 * Handles CRUD operations for ClarificationRequestStatusOption option data with soft and hard delete capabilities.
 */package rw.evolve.eprocurement.clarification_request_status_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.clarification_request_status_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.clarification_request_status_option.dto.ClarificationRequestStatusOptionDto;
import rw.evolve.eprocurement.clarification_request_status_option.model.ClarificationRequestStatusOptionModel;
import rw.evolve.eprocurement.clarification_request_status_option.service.ClarificationRequestStatusOptionService;
import rw.evolve.eprocurement.clarification_request_status_option.utils.ClarificationRequestStatusOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/clarification_request_status_option")
@Tag(name = "Clarification Request Status Option API")
public class ClarificationRequestStatusOptionController {


    private final ClarificationRequestStatusOptionService clarificationRequestStatusOptionService;

    private ModelMapper modelMapper = new ModelMapper();

    public ClarificationRequestStatusOptionController(
            ClarificationRequestStatusOptionService clarificationRequestStatusOptionService,
            ModelMapper modelMapper
    ){
        this.clarificationRequestStatusOptionService = clarificationRequestStatusOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts ClarificationRequestStatusOptionModel to ClarificationRequestStatusOptionDto.
     * @param model - ClarificationRequestStatusOptionModel to convert
     * @return      - Converted ClarificationRequestStatusOptionDto
     */
    private ClarificationRequestStatusOptionDto convertToDto(ClarificationRequestStatusOptionModel model) {
        return modelMapper.map(model, ClarificationRequestStatusOptionDto.class);
    }

    /**
     * Converts ClarificationRequestStatusOptionDto to ClarificationRequestStatusOptionModel.
     * @param clarificationRequestStatusOptionDto - ClarificationRequestStatusOptionDto to convert
     * @return                                    - Converted ClarificationRequestStatusOptionModel
     */
    private ClarificationRequestStatusOptionModel convertToModel(ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto) {
        return modelMapper.map(clarificationRequestStatusOptionDto, ClarificationRequestStatusOptionModel.class);
    }

    /**
     * Creates a single ClarificationRequestStatus option with a generated ID.
     * @param clarificationRequestStatusOptionDto - ClarificationRequestStatus option data
     * @return                                    - Response with success message
     */
    @Operation(summary = "Create a single ClarificationRequestStatus option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto) {
        ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel = convertToModel(clarificationRequestStatusOptionDto);
        clarificationRequestStatusOptionModel.setId(ClarificationRequestStatusOptionIdGenerator.generateId());
        clarificationRequestStatusOptionService.save(clarificationRequestStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Clarification request status option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple ClarificationRequestStatus options with generated IDList.
     * @param clarificationRequestStatusOptionDtoList - List of ClarificationRequestStatus option data
     * @return                                        - Response with success message
     */
    @Operation(summary = "Create multiple ClarificationRequestStatus options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoList) {
        List<ClarificationRequestStatusOptionModel> clarificationRequestStatusOptionModelList = new ArrayList<>();
        for (ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto : clarificationRequestStatusOptionDtoList) {
            ClarificationRequestStatusOptionModel model = convertToModel(clarificationRequestStatusOptionDto);
            model.setId(ClarificationRequestStatusOptionIdGenerator.generateId());
            clarificationRequestStatusOptionModelList.add(model);
        }
        clarificationRequestStatusOptionService.saveMany(clarificationRequestStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Clarification request status options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a ClarificationRequestStatus option by ID (excludes soft-deleted).
     * @param id - ClarificationRequestStatus option ID
     * @return   - Response with ClarificationRequestStatus option data
     */
    @Operation(summary = "Get a single ClarificationRequestStatus option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ClarificationRequestStatusOptionModel model = clarificationRequestStatusOptionService.readOne(id);
        ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(clarificationRequestStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted ClarificationRequestStatus options.
     * @return  - Response with list of ClarificationRequestStatus option data
     */
    @Operation(summary = "Get all available ClarificationRequestStatus options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ClarificationRequestStatusOptionModel> clarificationRequestStatusOptionModelList = clarificationRequestStatusOptionService.readAll();
        List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoList = new ArrayList<>();
        for (ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel : clarificationRequestStatusOptionModelList) {
            clarificationRequestStatusOptionDtoList.add(convertToDto(clarificationRequestStatusOptionModel));
        }
        return new ResponseEntity<>(clarificationRequestStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all ClarificationRequestStatus options, including soft-deleted.
     * @return        - Response with list of all ClarificationRequestStatus option data
     */
    @Operation(summary = "Get all ClarificationRequestStatus options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ClarificationRequestStatusOptionModel> modelList = clarificationRequestStatusOptionService.hardReadAll();
        List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoList = new ArrayList<>();
        for (ClarificationRequestStatusOptionModel model : modelList) {
            clarificationRequestStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(clarificationRequestStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple ClarificationRequestStatus options by ID (excludes soft-deleted).
     * @param idList - List of ClarificationRequestStatus option ID
     * @return       - Response with list of ClarificationRequestStatus option data
     */
    @Operation(summary = "Get multiple ClarificationRequestStatus options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ClarificationRequestStatusOptionModel> clarificationRequestStatusOptionModelList = clarificationRequestStatusOptionService.readMany(idList);
        List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoList = new ArrayList<>();
        for (ClarificationRequestStatusOptionModel model : clarificationRequestStatusOptionModelList) {
            clarificationRequestStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(clarificationRequestStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a ClarificationRequestStatus option by ID (excludes soft-deleted).
     *
     * @param clarificationRequestStatusOptionDto - Updated ClarificationRequestStatus option data
     * @return                                   - Response with updated ClarificationRequestStatus option data
     */
    @Operation(summary = "Update a single ClarificationRequestStatus option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto){
        String modelId = clarificationRequestStatusOptionDto.getId();
        ClarificationRequestStatusOptionModel savedModel = clarificationRequestStatusOptionService.readOne(modelId);
        savedModel.setName(clarificationRequestStatusOptionDto.getName());
        savedModel.setDescription(clarificationRequestStatusOptionDto.getDescription());
        clarificationRequestStatusOptionService.updateOne(savedModel);
        ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(clarificationRequestStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple ClarificationRequestStatus options (excludes soft-deleted).
     * @param clarificationRequestStatusOptionDtoList - a List of updated ClarificationRequestStatus option data
     * @return Response with list                     - of updated ClarificationRequestStatus option data
     */
    @Operation(summary = "Update multiple ClarificationRequestStatus options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoList) {
        List<ClarificationRequestStatusOptionModel> inputModelList = new ArrayList<>();
        for (ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto : clarificationRequestStatusOptionDtoList) {
            inputModelList.add(convertToModel(clarificationRequestStatusOptionDto));
        }
        List<ClarificationRequestStatusOptionModel> updatedModelList = clarificationRequestStatusOptionService.updateMany(inputModelList);
        List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoArrayList = new ArrayList<>();
        for (ClarificationRequestStatusOptionModel model : updatedModelList) {
            clarificationRequestStatusOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(clarificationRequestStatusOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a ClarificationRequestStatus option by ID, including soft-deleted.
     *
     * @param clarificationRequestStatusOptionDto - Updated ClarificationRequestStatus option data
     * @return Response with                      - updated ClarificationRequestStatus option data
     */
    @Operation(summary = "Update a single ClarificationRequestStatus option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto) {
        ClarificationRequestStatusOptionModel clarificationRequestStatusOptionModel = clarificationRequestStatusOptionService.hardUpdate(convertToModel(clarificationRequestStatusOptionDto));
        ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto1 = convertToDto(clarificationRequestStatusOptionModel);
        return new ResponseEntity<>(clarificationRequestStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all ClarificationRequestStatus options, including soft-deleted.
     * @param clarificationRequestStatusOptionDtoList - List of updated ClarificationRequestStatus option data
     * @return                                       - Response with list of updated ClarificationRequestStatus option data
     */
    @Operation(summary = "Update all ClarificationRequestStatus options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ClarificationRequestStatusOptionDto> clarificationRequestStatusOptionDtoList) {
        List<ClarificationRequestStatusOptionModel> inputModelList = new ArrayList<>();
        for (ClarificationRequestStatusOptionDto clarificationRequestStatusOptionDto : clarificationRequestStatusOptionDtoList) {
            inputModelList.add(convertToModel(clarificationRequestStatusOptionDto));
        }
        List<ClarificationRequestStatusOptionModel> updatedModelList = clarificationRequestStatusOptionService.hardUpdateAll(inputModelList);
        List<ClarificationRequestStatusOptionDto> updatedClarificationRequestStatusOptionDtoList = new ArrayList<>();
        for (ClarificationRequestStatusOptionModel model : updatedModelList) {
            updatedClarificationRequestStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedClarificationRequestStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a ClarificationRequestStatus option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single ClarificationRequestStatus option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        ClarificationRequestStatusOptionModel deleteClarificationRequestStatusOptionModel = clarificationRequestStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Clarification request status option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a ClarificationRequestStatus option by ID.
     * @param id       - ClarificationRequestStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single ClarificationRequestStatus option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        clarificationRequestStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Clarification request status option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple ClarificationRequestStatus options by ID.
     * @param idList    - List of ClarificationRequestStatus option IDList
     * @return          - Response with list of soft-deleted ClarificationRequestStatus option data
     */
    @Operation(summary = "Soft delete multiple ClarificationRequestStatus options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ClarificationRequestStatusOptionModel> deletedClarificationRequestStatusOptionModelList = clarificationRequestStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Clarification request status options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple ClarificationRequestStatus options by ID.
     * @param idList   - List of ClarificationRequestStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple ClarificationRequestStatus options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        clarificationRequestStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Clarification request status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all ClarificationRequestStatus options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all ClarificationRequestStatus options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        clarificationRequestStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Clarification request status options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}