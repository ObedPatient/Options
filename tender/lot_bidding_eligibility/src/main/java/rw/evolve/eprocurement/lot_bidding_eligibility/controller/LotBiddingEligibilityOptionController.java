/**
 * REST API controller for managing LotBiddingEligibility options.
 * Handles CRUD operations for LotBiddingEligibility option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.lot_bidding_eligibility.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.lot_bidding_eligibility.dto.ResponseMessageDto;
import rw.evolve.eprocurement.lot_bidding_eligibility.dto.LotBiddingEligibilityOptionDto;
import rw.evolve.eprocurement.lot_bidding_eligibility.model.LotBiddingEligibilityOptionModel;
import rw.evolve.eprocurement.lot_bidding_eligibility.service.LotBiddingEligibilityOptionService;
import rw.evolve.eprocurement.lot_bidding_eligibility.utils.LotBiddingEligibilityOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/lot_bidding_eligibility_option")
@Tag(name = "Lot Bidding Eligibility Option API")
public class LotBiddingEligibilityOptionController {

    @Autowired
    private final LotBiddingEligibilityOptionService lotBiddingEligibilityOptionService;

    private ModelMapper modelMapper = new ModelMapper();

    public LotBiddingEligibilityOptionController(
            LotBiddingEligibilityOptionService lotBiddingEligibilityOptionService,
            ModelMapper modelMapper
    ){
        this.lotBiddingEligibilityOptionService = lotBiddingEligibilityOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts LotBiddingEligibilityOptionModel to LotBiddingEligibilityOptionDto.
     * @param model - LotBiddingEligibilityOptionModel to convert
     * @return      - Converted LotBiddingEligibilityOptionDto
     */
    private LotBiddingEligibilityOptionDto convertToDto(LotBiddingEligibilityOptionModel model) {
        return modelMapper.map(model, LotBiddingEligibilityOptionDto.class);
    }

    /**
     * Converts LotBiddingEligibilityOptionDto to LotBiddingEligibilityOptionModel.
     * @param lotBiddingEligibilityOptionDto - LotBiddingEligibilityOptionDto to convert
     * @return                               - Converted LotBiddingEligibilityOptionModel
     */
    private LotBiddingEligibilityOptionModel convertToModel(LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto) {
        return modelMapper.map(lotBiddingEligibilityOptionDto, LotBiddingEligibilityOptionModel.class);
    }

    /**
     * Creates a single LotBiddingEligibility option with a generated ID.
     * @param lotBiddingEligibilityOptionDto - LotBiddingEligibility option data
     * @return                               - Response with success message
     */
    @Operation(summary = "Create a single LotBiddingEligibility option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto) {
        LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel = convertToModel(lotBiddingEligibilityOptionDto);
        lotBiddingEligibilityOptionModel.setId(LotBiddingEligibilityOptionIdGenerator.generateId());
        lotBiddingEligibilityOptionService.save(lotBiddingEligibilityOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Lot bidding eligibility option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple LotBiddingEligibility options with generated IDList.
     * @param lotBiddingEligibilityOptionDtoList - List of LotBiddingEligibility option data
     * @return                                   - Response with success message
     */
    @Operation(summary = "Create multiple LotBiddingEligibility options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoList) {
        List<LotBiddingEligibilityOptionModel> lotBiddingEligibilityOptionModelList = new ArrayList<>();
        for (LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto : lotBiddingEligibilityOptionDtoList) {
            LotBiddingEligibilityOptionModel model = convertToModel(lotBiddingEligibilityOptionDto);
            model.setId(LotBiddingEligibilityOptionIdGenerator.generateId());
            lotBiddingEligibilityOptionModelList.add(model);
        }
        lotBiddingEligibilityOptionService.saveMany(lotBiddingEligibilityOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Lot bidding eligibility options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a LotBiddingEligibility option by ID (excludes soft-deleted).
     * @param id - LotBiddingEligibility option ID
     * @return   - Response with LotBiddingEligibility option data
     */
    @Operation(summary = "Get a single LotBiddingEligibility option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        LotBiddingEligibilityOptionModel model = lotBiddingEligibilityOptionService.readOne(id);
        LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto = convertToDto(model);
        return new ResponseEntity<>(lotBiddingEligibilityOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted LotBiddingEligibility options.
     * @return  - Response with list of LotBiddingEligibility option data
     */
    @Operation(summary = "Get all available LotBiddingEligibility options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<LotBiddingEligibilityOptionModel> lotBiddingEligibilityOptionModelList = lotBiddingEligibilityOptionService.readAll();
        List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoList = new ArrayList<>();
        for (LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel : lotBiddingEligibilityOptionModelList) {
            lotBiddingEligibilityOptionDtoList.add(convertToDto(lotBiddingEligibilityOptionModel));
        }
        return new ResponseEntity<>(lotBiddingEligibilityOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all LotBiddingEligibility options, including soft-deleted.
     * @return        - Response with list of all LotBiddingEligibility option data
     */
    @Operation(summary = "Get all LotBiddingEligibility options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<LotBiddingEligibilityOptionModel> modelList = lotBiddingEligibilityOptionService.hardReadAll();
        List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoList = new ArrayList<>();
        for (LotBiddingEligibilityOptionModel model : modelList) {
            lotBiddingEligibilityOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(lotBiddingEligibilityOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple LotBiddingEligibility options by ID (excludes soft-deleted).
     * @param idList - List of LotBiddingEligibility option ID
     * @return       - Response with list of LotBiddingEligibility option data
     */
    @Operation(summary = "Get multiple LotBiddingEligibility options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<LotBiddingEligibilityOptionModel> lotBiddingEligibilityOptionModelList = lotBiddingEligibilityOptionService.readMany(idList);
        List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoList = new ArrayList<>();
        for (LotBiddingEligibilityOptionModel model : lotBiddingEligibilityOptionModelList) {
            lotBiddingEligibilityOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(lotBiddingEligibilityOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a LotBiddingEligibility option by ID (excludes soft-deleted).
     *
     * @param lotBiddingEligibilityOptionDto - Updated LotBiddingEligibility option data
     * @return                               - Response with updated LotBiddingEligibility option data
     */
    @Operation(summary = "Update a single LotBiddingEligibility option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto){
        String modelId = lotBiddingEligibilityOptionDto.getId();
        LotBiddingEligibilityOptionModel savedModel = lotBiddingEligibilityOptionService.readOne(modelId);
        savedModel.setName(lotBiddingEligibilityOptionDto.getName());
        savedModel.setDescription(lotBiddingEligibilityOptionDto.getDescription());
        lotBiddingEligibilityOptionService.updateOne(savedModel);
        LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(lotBiddingEligibilityOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple LotBiddingEligibility options (excludes soft-deleted).
     * @param lotBiddingEligibilityOptionDtoList - a List of updated LotBiddingEligibility option data
     * @return                                   - Response with list of updated LotBiddingEligibility option data
     */
    @Operation(summary = "Update multiple LotBiddingEligibility options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoList) {
        List<LotBiddingEligibilityOptionModel> inputModelList = new ArrayList<>();
        for (LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto : lotBiddingEligibilityOptionDtoList) {
            inputModelList.add(convertToModel(lotBiddingEligibilityOptionDto));
        }
        List<LotBiddingEligibilityOptionModel> updatedModelList = lotBiddingEligibilityOptionService.updateMany(inputModelList);
        List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoArrayList = new ArrayList<>();
        for (LotBiddingEligibilityOptionModel model : updatedModelList) {
            lotBiddingEligibilityOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(lotBiddingEligibilityOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a LotBiddingEligibility option by ID, including soft-deleted.
     *
     * @param lotBiddingEligibilityOptionDto - Updated LotBiddingEligibility option data
     * @return                               - Response with updated LotBiddingEligibility option data
     */
    @Operation(summary = "Update a single LotBiddingEligibility option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto) {
        LotBiddingEligibilityOptionModel lotBiddingEligibilityOptionModel = lotBiddingEligibilityOptionService.hardUpdate(convertToModel(lotBiddingEligibilityOptionDto));
        LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto1 = convertToDto(lotBiddingEligibilityOptionModel);
        return new ResponseEntity<>(lotBiddingEligibilityOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all LotBiddingEligibility options, including soft-deleted.
     * @param lotBiddingEligibilityOptionDtoList - List of updated LotBiddingEligibility option data
     * @return                                   - Response with list of updated LotBiddingEligibility option data
     */
    @Operation(summary = "Update all LotBiddingEligibility options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<LotBiddingEligibilityOptionDto> lotBiddingEligibilityOptionDtoList) {
        List<LotBiddingEligibilityOptionModel> inputModelList = new ArrayList<>();
        for (LotBiddingEligibilityOptionDto lotBiddingEligibilityOptionDto : lotBiddingEligibilityOptionDtoList) {
            inputModelList.add(convertToModel(lotBiddingEligibilityOptionDto));
        }
        List<LotBiddingEligibilityOptionModel> updatedModelList = lotBiddingEligibilityOptionService.hardUpdateAll(inputModelList);
        List<LotBiddingEligibilityOptionDto> updatedLotBiddingEligibilityOptionDtoList = new ArrayList<>();
        for (LotBiddingEligibilityOptionModel model : updatedModelList) {
            updatedLotBiddingEligibilityOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedLotBiddingEligibilityOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a LotBiddingEligibility option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single LotBiddingEligibility option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        LotBiddingEligibilityOptionModel deleteLotBiddingEligibilityOptionModel = lotBiddingEligibilityOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Lot bidding eligibility option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a LotBiddingEligibility option by ID.
     * @param id       - LotBiddingEligibility option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single LotBiddingEligibility option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        lotBiddingEligibilityOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Lot bidding eligibility option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple LotBiddingEligibility options by ID.
     * @param idList    - List of LotBiddingEligibility option IDList
     * @return          - Response with list of soft-deleted LotBiddingEligibility option data
     */
    @Operation(summary = "Soft delete multiple LotBiddingEligibility options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<LotBiddingEligibilityOptionModel> deletedLotBiddingEligibilityOptionModelList = lotBiddingEligibilityOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Lot bidding eligibility options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple LotBiddingEligibility options by ID.
     * @param idList   - List of LotBiddingEligibility option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple LotBiddingEligibility options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        lotBiddingEligibilityOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Lot bidding eligibility options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all LotBiddingEligibility options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all LotBiddingEligibility options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        lotBiddingEligibilityOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Lot bidding eligibility options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}