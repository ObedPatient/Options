/**
 * REST API controller for managing TenderStatus options.
 * Handles CRUD operations for TenderStatus option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.tender_status.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.tender_status.dto.ResponseMessageDto;
import rw.evolve.eprocurement.tender_status.dto.TenderStatusOptionDto;
import rw.evolve.eprocurement.tender_status.model.TenderStatusOptionModel;
import rw.evolve.eprocurement.tender_status.service.TenderStatusOptionService;
import rw.evolve.eprocurement.tender_status.utils.TenderStatusOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/tender_status_option")
@Tag(name = "Tender Status Option API")
public class TenderStatusOptionController {

    @Autowired
    private TenderStatusOptionService tenderStatusOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts TenderStatusOptionModel to TenderStatusOptionDto.
     * @param model - TenderStatusOptionModel to convert
     * @return      - Converted TenderStatusOptionDto
     */
    private TenderStatusOptionDto convertToDto(TenderStatusOptionModel model) {
        return modelMapper.map(model, TenderStatusOptionDto.class);
    }

    /**
     * Converts TenderStatusOptionDto to TenderStatusOptionModel.
     * @param tenderStatusOptionDto - TenderStatusOptionDto to convert
     * @return                      - Converted TenderStatusOptionModel
     */
    private TenderStatusOptionModel convertToModel(TenderStatusOptionDto tenderStatusOptionDto) {
        return modelMapper.map(tenderStatusOptionDto, TenderStatusOptionModel.class);
    }

    /**
     * Creates a single TenderStatus option with a generated ID.
     * @param tenderStatusOptionDto - TenderStatus option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create a single TenderStatus option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody TenderStatusOptionDto tenderStatusOptionDto) {
        TenderStatusOptionModel tenderStatusOptionModel = convertToModel(tenderStatusOptionDto);
        tenderStatusOptionModel.setId(TenderStatusOptionIdGenerator.generateId());
        tenderStatusOptionService.save(tenderStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender status option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple TenderStatus options with generated IDList.
     * @param tenderStatusOptionDtoList - List of TenderStatus option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create multiple TenderStatus options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<TenderStatusOptionDto> tenderStatusOptionDtoList) {
        List<TenderStatusOptionModel> tenderStatusOptionModelList = new ArrayList<>();
        for (TenderStatusOptionDto tenderStatusOptionDto : tenderStatusOptionDtoList) {
            TenderStatusOptionModel model = convertToModel(tenderStatusOptionDto);
            model.setId(TenderStatusOptionIdGenerator.generateId());
            tenderStatusOptionModelList.add(model);
        }
        tenderStatusOptionService.saveMany(tenderStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender status options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a TenderStatus option by ID (excludes soft-deleted).
     * @param id - TenderStatus option ID
     * @return   - Response with TenderStatus option data
     */
    @Operation(summary = "Get a single TenderStatus option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        TenderStatusOptionModel model = tenderStatusOptionService.readOne(id);
        TenderStatusOptionDto tenderStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(tenderStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted TenderStatus options.
     * @return  - Response with list of TenderStatus option data
     */
    @Operation(summary = "Get all available TenderStatus options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<TenderStatusOptionModel> tenderStatusOptionModelList = tenderStatusOptionService.readAll();
        List<TenderStatusOptionDto> tenderStatusOptionDtoList = new ArrayList<>();
        for (TenderStatusOptionModel tenderStatusOptionModel : tenderStatusOptionModelList) {
            tenderStatusOptionDtoList.add(convertToDto(tenderStatusOptionModel));
        }
        return new ResponseEntity<>(tenderStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all TenderStatus options, including soft-deleted.
     * @return        - Response with list of all TenderStatus option data
     */
    @Operation(summary = "Get all TenderStatus options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<TenderStatusOptionModel> modelList = tenderStatusOptionService.hardReadAll();
        List<TenderStatusOptionDto> tenderStatusOptionDtoList = new ArrayList<>();
        for (TenderStatusOptionModel model : modelList) {
            tenderStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple TenderStatus options by ID (excludes soft-deleted).
     * @param idList - List of TenderStatus option ID
     * @return       - Response with list of TenderStatus option data
     */
    @Operation(summary = "Get multiple TenderStatus options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<TenderStatusOptionModel> tenderStatusOptionModelList = tenderStatusOptionService.readMany(idList);
        List<TenderStatusOptionDto> tenderStatusOptionDtoList = new ArrayList<>();
        for (TenderStatusOptionModel model : tenderStatusOptionModelList) {
            tenderStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a TenderStatus option by ID (excludes soft-deleted).
     *
     * @param tenderStatusOptionDto - Updated TenderStatus option data
     * @return                      - Response with updated TenderStatus option data
     */
    @Operation(summary = "Update a single TenderStatus option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody TenderStatusOptionDto tenderStatusOptionDto){
        String modelId = tenderStatusOptionDto.getId();
        TenderStatusOptionModel savedModel = tenderStatusOptionService.readOne(modelId);
        savedModel.setName(tenderStatusOptionDto.getName());
        savedModel.setDescription(tenderStatusOptionDto.getDescription());
        tenderStatusOptionService.updateOne(savedModel);
        TenderStatusOptionDto tenderStatusOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(tenderStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple TenderStatus options (excludes soft-deleted).
     * @param tenderStatusOptionDtoList - a List of updated TenderStatus option data
     * @return                         - Response with list of updated TenderStatus option data
     */
    @Operation(summary = "Update multiple TenderStatus options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<TenderStatusOptionDto> tenderStatusOptionDtoList) {
        List<TenderStatusOptionModel> inputModelList = new ArrayList<>();
        for (TenderStatusOptionDto tenderStatusOptionDto : tenderStatusOptionDtoList) {
            inputModelList.add(convertToModel(tenderStatusOptionDto));
        }
        List<TenderStatusOptionModel> updatedModelList = tenderStatusOptionService.updateMany(inputModelList);
        List<TenderStatusOptionDto> tenderStatusOptionDtoArrayList = new ArrayList<>();
        for (TenderStatusOptionModel model : updatedModelList) {
            tenderStatusOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderStatusOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a TenderStatus option by ID, including soft-deleted.
     *
     * @param tenderStatusOptionDto - Updated TenderStatus option data
     * @return                      - Response with updated TenderStatus option data
     */
    @Operation(summary = "Update a single TenderStatus option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody TenderStatusOptionDto tenderStatusOptionDto) {
        TenderStatusOptionModel tenderStatusOptionModel = tenderStatusOptionService.hardUpdate(convertToModel(tenderStatusOptionDto));
        TenderStatusOptionDto tenderStatusOptionDto1 = convertToDto(tenderStatusOptionModel);
        return new ResponseEntity<>(tenderStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all TenderStatus options, including soft-deleted.
     * @param tenderStatusOptionDtoList - List of updated TenderStatus option data
     * @return                          - Response with list of updated TenderStatus option data
     */
    @Operation(summary = "Update all TenderStatus options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<TenderStatusOptionDto> tenderStatusOptionDtoList) {
        List<TenderStatusOptionModel> inputModelList = new ArrayList<>();
        for (TenderStatusOptionDto tenderStatusOptionDto : tenderStatusOptionDtoList) {
            inputModelList.add(convertToModel(tenderStatusOptionDto));
        }
        List<TenderStatusOptionModel> updatedModelList = tenderStatusOptionService.hardUpdateAll(inputModelList);
        List<TenderStatusOptionDto> updatedTenderStatusOptionDtoList = new ArrayList<>();
        for (TenderStatusOptionModel model : updatedModelList) {
            updatedTenderStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedTenderStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a TenderStatus option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single TenderStatus option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        TenderStatusOptionModel deleteTenderStatusOptionModel = tenderStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender status option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a TenderStatus option by ID.
     * @param id       - TenderStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single TenderStatus option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        tenderStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender status option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple TenderStatus options by ID.
     * @param idList    - List of TenderStatus option IDList
     * @return          - Response with list of soft-deleted TenderStatus option data
     */
    @Operation(summary = "Soft delete multiple TenderStatus options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<TenderStatusOptionModel> deletedTenderStatusOptionModelList = tenderStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender status options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple TenderStatus options by ID.
     * @param idList   - List of TenderStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple TenderStatus options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        tenderStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Tender status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all TenderStatus options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all TenderStatus options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        tenderStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Tender status options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}