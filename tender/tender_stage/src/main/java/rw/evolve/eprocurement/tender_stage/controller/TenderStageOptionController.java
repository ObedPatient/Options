/**
 * REST API controller for managing TenderStage options.
 * Handles CRUD operations for TenderStage option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.tender_stage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.tender_stage.dto.ResponseMessageDto;
import rw.evolve.eprocurement.tender_stage.dto.TenderStageOptionDto;
import rw.evolve.eprocurement.tender_stage.model.TenderStageOptionModel;
import rw.evolve.eprocurement.tender_stage.service.TenderStageOptionService;
import rw.evolve.eprocurement.tender_stage.utils.TenderStageOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/tender_stage_option")
@Tag(name = "Tender Stage Option API")
public class TenderStageOptionController {

    @Autowired
    private final TenderStageOptionService tenderStageOptionService;

    private  ModelMapper modelMapper = new ModelMapper();

    public TenderStageOptionController(
            TenderStageOptionService tenderStageOptionService,
            ModelMapper modelMapper
    ){
        this.tenderStageOptionService = tenderStageOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts TenderStageOptionModel to TenderStageOptionDto.
     * @param model - TenderStageOptionModel to convert
     * @return      - Converted TenderStageOptionDto
     */
    private TenderStageOptionDto convertToDto(TenderStageOptionModel model) {
        return modelMapper.map(model, TenderStageOptionDto.class);
    }

    /**
     * Converts TenderStageOptionDto to TenderStageOptionModel.
     * @param tenderStageOptionDto - TenderStageOptionDto to convert
     * @return                     - Converted TenderStageOptionModel
     */
    private TenderStageOptionModel convertToModel(TenderStageOptionDto tenderStageOptionDto) {
        return modelMapper.map(tenderStageOptionDto, TenderStageOptionModel.class);
    }

    /**
     * Creates a single TenderStage option with a generated ID.
     * @param tenderStageOptionDto - TenderStage option data
     * @return                     - Response with success message
     */
    @Operation(summary = "Create a single TenderStage option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody TenderStageOptionDto tenderStageOptionDto) {
        TenderStageOptionModel tenderStageOptionModel = convertToModel(tenderStageOptionDto);
        tenderStageOptionModel.setId(TenderStageOptionIdGenerator.generateId());
        tenderStageOptionService.save(tenderStageOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender stage option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple TenderStage options with generated IDList.
     * @param tenderStageOptionDtoList - List of TenderStage option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create multiple TenderStage options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<TenderStageOptionDto> tenderStageOptionDtoList) {
        List<TenderStageOptionModel> tenderStageOptionModelList = new ArrayList<>();
        for (TenderStageOptionDto tenderStageOptionDto : tenderStageOptionDtoList) {
            TenderStageOptionModel model = convertToModel(tenderStageOptionDto);
            model.setId(TenderStageOptionIdGenerator.generateId());
            tenderStageOptionModelList.add(model);
        }
        tenderStageOptionService.saveMany(tenderStageOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender stage options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a TenderStage option by ID (excludes soft-deleted).
     * @param id - TenderStage option ID
     * @return   - Response with TenderStage option data
     */
    @Operation(summary = "Get a single TenderStage option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        TenderStageOptionModel model = tenderStageOptionService.readOne(id);
        TenderStageOptionDto tenderStageOptionDto = convertToDto(model);
        return new ResponseEntity<>(tenderStageOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted TenderStage options.
     * @return  - Response with list of TenderStage option data
     */
    @Operation(summary = "Get all available TenderStage options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<TenderStageOptionModel> tenderStageOptionModelList = tenderStageOptionService.readAll();
        List<TenderStageOptionDto> tenderStageOptionDtoList = new ArrayList<>();
        for (TenderStageOptionModel tenderStageOptionModel : tenderStageOptionModelList) {
            tenderStageOptionDtoList.add(convertToDto(tenderStageOptionModel));
        }
        return new ResponseEntity<>(tenderStageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all TenderStage options, including soft-deleted.
     * @return        - Response with list of all TenderStage option data
     */
    @Operation(summary = "Get all TenderStage options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<TenderStageOptionModel> modelList = tenderStageOptionService.hardReadAll();
        List<TenderStageOptionDto> tenderStageOptionDtoList = new ArrayList<>();
        for (TenderStageOptionModel model : modelList) {
            tenderStageOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderStageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple TenderStage options by ID (excludes soft-deleted).
     * @param idList - List of TenderStage option ID
     * @return       - Response with list of TenderStage option data
     */
    @Operation(summary = "Get multiple TenderStage options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<TenderStageOptionModel> tenderStageOptionModelList = tenderStageOptionService.readMany(idList);
        List<TenderStageOptionDto> tenderStageOptionDtoList = new ArrayList<>();
        for (TenderStageOptionModel model : tenderStageOptionModelList) {
            tenderStageOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderStageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a TenderStage option by ID (excludes soft-deleted).
     *
     * @param tenderStageOptionDto - Updated TenderStage option data
     * @return                     - Response with updated TenderStage option data
     */
    @Operation(summary = "Update a single TenderStage option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody TenderStageOptionDto tenderStageOptionDto){
        String modelId = tenderStageOptionDto.getId();
        TenderStageOptionModel savedModel = tenderStageOptionService.readOne(modelId);
        savedModel.setName(tenderStageOptionDto.getName());
        savedModel.setDescription(tenderStageOptionDto.getDescription());
        tenderStageOptionService.updateOne(savedModel);
        TenderStageOptionDto tenderStageOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(tenderStageOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple TenderStage options (excludes soft-deleted).
     * @param tenderStageOptionDtoList - a List of updated TenderStage option data
     * @return                        - Response with list of updated TenderStage option data
     */
    @Operation(summary = "Update multiple TenderStage options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<TenderStageOptionDto> tenderStageOptionDtoList) {
        List<TenderStageOptionModel> inputModelList = new ArrayList<>();
        for (TenderStageOptionDto tenderStageOptionDto : tenderStageOptionDtoList) {
            inputModelList.add(convertToModel(tenderStageOptionDto));
        }
        List<TenderStageOptionModel> updatedModelList = tenderStageOptionService.updateMany(inputModelList);
        List<TenderStageOptionDto> tenderStageOptionDtoArrayList = new ArrayList<>();
        for (TenderStageOptionModel model : updatedModelList) {
            tenderStageOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(tenderStageOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a TenderStage option by ID, including soft-deleted.
     *
     * @param tenderStageOptionDto - Updated TenderStage option data
     * @return                    - Response with updated TenderStage option data
     */
    @Operation(summary = "Update a single TenderStage option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody TenderStageOptionDto tenderStageOptionDto) {
        TenderStageOptionModel tenderStageOptionModel = tenderStageOptionService.hardUpdate(convertToModel(tenderStageOptionDto));
        TenderStageOptionDto tenderStageOptionDto1 = convertToDto(tenderStageOptionModel);
        return new ResponseEntity<>(tenderStageOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all TenderStage options, including soft-deleted.
     * @param tenderStageOptionDtoList - List of updated TenderStage option data
     * @return                        - Response with list of updated TenderStage option data
     */
    @Operation(summary = "Update all TenderStage options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<TenderStageOptionDto> tenderStageOptionDtoList) {
        List<TenderStageOptionModel> inputModelList = new ArrayList<>();
        for (TenderStageOptionDto tenderStageOptionDto : tenderStageOptionDtoList) {
            inputModelList.add(convertToModel(tenderStageOptionDto));
        }
        List<TenderStageOptionModel> updatedModelList = tenderStageOptionService.hardUpdateAll(inputModelList);
        List<TenderStageOptionDto> updatedTenderStageOptionDtoList = new ArrayList<>();
        for (TenderStageOptionModel model : updatedModelList) {
            updatedTenderStageOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedTenderStageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a TenderStage option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single TenderStage option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        TenderStageOptionModel deleteTenderStageOptionModel = tenderStageOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender stage option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a TenderStage option by ID.
     * @param id       - TenderStage option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single TenderStage option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        tenderStageOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender stage option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple TenderStage options by ID.
     * @param idList    - List of TenderStage option IDList
     * @return          - Response with list of soft-deleted TenderStage option data
     */
    @Operation(summary = "Soft delete multiple TenderStage options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<TenderStageOptionModel> deletedTenderStageOptionModelList = tenderStageOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Tender stage options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple TenderStage options by ID.
     * @param idList   - List of TenderStage option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple TenderStage options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        tenderStageOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Tender stage options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all TenderStage options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all TenderStage options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        tenderStageOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Tender stage options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}