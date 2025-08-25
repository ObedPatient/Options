/**
 * REST API controller for managing BidSecurityType options.
 * Handles CRUD operations for BidSecurityType option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.bid_security_type.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.bid_security_type.dto.ResponseMessageDto;
import rw.evolve.eprocurement.bid_security_type.dto.BidSecurityTypeOptionDto;
import rw.evolve.eprocurement.bid_security_type.model.BidSecurityTypeOptionModel;
import rw.evolve.eprocurement.bid_security_type.service.BidSecurityTypeOptionService;
import rw.evolve.eprocurement.bid_security_type.utils.BidSecurityTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/bid_security_type_option")
@Tag(name = "Bid Security Type Option API")
public class BidSecurityTypeOptionController {

    private final BidSecurityTypeOptionService bidSecurityTypeOptionService;

    private final ModelMapper modelMapper;

    public BidSecurityTypeOptionController(
            BidSecurityTypeOptionService bidSecurityTypeOptionService,
            ModelMapper modelMapper
    ){
        this.bidSecurityTypeOptionService = bidSecurityTypeOptionService;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Converts BidSecurityTypeOptionModel to BidSecurityTypeOptionDto.
     * @param model - BidSecurityTypeOptionModel to convert
     * @return      - Converted BidSecurityTypeOptionDto
     */
    private BidSecurityTypeOptionDto convertToDto(BidSecurityTypeOptionModel model) {
        return modelMapper.map(model, BidSecurityTypeOptionDto.class);
    }

    /**
     * Converts BidSecurityTypeOptionDto to BidSecurityTypeOptionModel.
     * @param bidSecurityTypeOptionDto - BidSecurityTypeOptionDto to convert
     * @return                         - Converted BidSecurityTypeOptionModel
     */
    private BidSecurityTypeOptionModel convertToModel(BidSecurityTypeOptionDto bidSecurityTypeOptionDto) {
        return modelMapper.map(bidSecurityTypeOptionDto, BidSecurityTypeOptionModel.class);
    }

    /**
     * Creates a single BidSecurityType option with a generated ID.
     * @param bidSecurityTypeOptionDto - BidSecurityType option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create a single BidSecurityType option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody BidSecurityTypeOptionDto bidSecurityTypeOptionDto) {
        BidSecurityTypeOptionModel bidSecurityTypeOptionModel = convertToModel(bidSecurityTypeOptionDto);
        bidSecurityTypeOptionModel.setId(BidSecurityTypeOptionIdGenerator.generateId());
        bidSecurityTypeOptionService.save(bidSecurityTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Bid security type option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple BidSecurityType options with generated IDList.
     * @param bidSecurityTypeOptionDtoList - List of BidSecurityType option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create multiple BidSecurityType options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoList) {
        List<BidSecurityTypeOptionModel> bidSecurityTypeOptionModelList = new ArrayList<>();
        for (BidSecurityTypeOptionDto bidSecurityTypeOptionDto : bidSecurityTypeOptionDtoList) {
            BidSecurityTypeOptionModel model = convertToModel(bidSecurityTypeOptionDto);
            model.setId(BidSecurityTypeOptionIdGenerator.generateId());
            bidSecurityTypeOptionModelList.add(model);
        }
        bidSecurityTypeOptionService.saveMany(bidSecurityTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Bid security type options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a BidSecurityType option by ID (excludes soft-deleted).
     * @param id - BidSecurityType option ID
     * @return   - Response with BidSecurityType option data
     */
    @Operation(summary = "Get a single BidSecurityType option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        BidSecurityTypeOptionModel model = bidSecurityTypeOptionService.readOne(id);
        BidSecurityTypeOptionDto bidSecurityTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(bidSecurityTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted BidSecurityType options.
     * @return  - Response with list of BidSecurityType option data
     */
    @Operation(summary = "Get all available BidSecurityType options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<BidSecurityTypeOptionModel> bidSecurityTypeOptionModelList = bidSecurityTypeOptionService.readAll();
        List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoList = new ArrayList<>();
        for (BidSecurityTypeOptionModel bidSecurityTypeOptionModel : bidSecurityTypeOptionModelList) {
            bidSecurityTypeOptionDtoList.add(convertToDto(bidSecurityTypeOptionModel));
        }
        return new ResponseEntity<>(bidSecurityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all BidSecurityType options, including soft-deleted.
     * @return        - Response with list of all BidSecurityType option data
     */
    @Operation(summary = "Get all BidSecurityType options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<BidSecurityTypeOptionModel> modelList = bidSecurityTypeOptionService.hardReadAll();
        List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoList = new ArrayList<>();
        for (BidSecurityTypeOptionModel model : modelList) {
            bidSecurityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(bidSecurityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple BidSecurityType options by ID (excludes soft-deleted).
     * @param idList - List of BidSecurityType option ID
     * @return       - Response with list of BidSecurityType option data
     */
    @Operation(summary = "Get multiple BidSecurityType options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<BidSecurityTypeOptionModel> bidSecurityTypeOptionModelList = bidSecurityTypeOptionService.readMany(idList);
        List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoList = new ArrayList<>();
        for (BidSecurityTypeOptionModel model : bidSecurityTypeOptionModelList) {
            bidSecurityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(bidSecurityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a BidSecurityType option by ID (excludes soft-deleted).
     *
     * @param bidSecurityTypeOptionDto - Updated BidSecurityType option data
     * @return                         - Response with updated BidSecurityType option data
     */
    @Operation(summary = "Update a single BidSecurityType option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody BidSecurityTypeOptionDto bidSecurityTypeOptionDto){
        String modelId = bidSecurityTypeOptionDto.getId();
        BidSecurityTypeOptionModel savedModel = bidSecurityTypeOptionService.readOne(modelId);
        savedModel.setName(bidSecurityTypeOptionDto.getName());
        savedModel.setDescription(bidSecurityTypeOptionDto.getDescription());
        bidSecurityTypeOptionService.updateOne(savedModel);
        BidSecurityTypeOptionDto bidSecurityTypeOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(bidSecurityTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple BidSecurityType options (excludes soft-deleted).
     * @param bidSecurityTypeOptionDtoList - a List of updated BidSecurityType option data
     * @return                            - Response with list of updated BidSecurityType option data
     */
    @Operation(summary = "Update multiple BidSecurityType options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoList) {
        List<BidSecurityTypeOptionModel> inputModelList = new ArrayList<>();
        for (BidSecurityTypeOptionDto bidSecurityTypeOptionDto : bidSecurityTypeOptionDtoList) {
            inputModelList.add(convertToModel(bidSecurityTypeOptionDto));
        }
        List<BidSecurityTypeOptionModel> updatedModelList = bidSecurityTypeOptionService.updateMany(inputModelList);
        List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoArrayList = new ArrayList<>();
        for (BidSecurityTypeOptionModel model : updatedModelList) {
            bidSecurityTypeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(bidSecurityTypeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a BidSecurityType option by ID, including soft-deleted.
     *
     * @param bidSecurityTypeOptionDto - Updated BidSecurityType option data
     * @return                        - Response with updated BidSecurityType option data
     */
    @Operation(summary = "Update a single BidSecurityType option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody BidSecurityTypeOptionDto bidSecurityTypeOptionDto) {
        BidSecurityTypeOptionModel bidSecurityTypeOptionModel = bidSecurityTypeOptionService.hardUpdate(convertToModel(bidSecurityTypeOptionDto));
        BidSecurityTypeOptionDto bidSecurityTypeOptionDto1 = convertToDto(bidSecurityTypeOptionModel);
        return new ResponseEntity<>(bidSecurityTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all BidSecurityType options, including soft-deleted.
     * @param bidSecurityTypeOptionDtoList - List of updated BidSecurityType option data
     * @return                            - Response with list of updated BidSecurityType option data
     */
    @Operation(summary = "Update all BidSecurityType options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<BidSecurityTypeOptionDto> bidSecurityTypeOptionDtoList) {
        List<BidSecurityTypeOptionModel> inputModelList = new ArrayList<>();
        for (BidSecurityTypeOptionDto bidSecurityTypeOptionDto : bidSecurityTypeOptionDtoList) {
            inputModelList.add(convertToModel(bidSecurityTypeOptionDto));
        }
        List<BidSecurityTypeOptionModel> updatedModelList = bidSecurityTypeOptionService.hardUpdateAll(inputModelList);
        List<BidSecurityTypeOptionDto> updatedBidSecurityTypeOptionDtoList = new ArrayList<>();
        for (BidSecurityTypeOptionModel model : updatedModelList) {
            updatedBidSecurityTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedBidSecurityTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a BidSecurityType option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single BidSecurityType option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        BidSecurityTypeOptionModel deleteBidSecurityTypeOptionModel = bidSecurityTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Bid security type option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a BidSecurityType option by ID.
     * @param id       - BidSecurityType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single BidSecurityType option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        bidSecurityTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Bid security type option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple BidSecurityType options by ID.
     * @param idList    - List of BidSecurityType option IDList
     * @return          - Response with list of soft-deleted BidSecurityType option data
     */
    @Operation(summary = "Soft delete multiple BidSecurityType options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<BidSecurityTypeOptionModel> deletedBidSecurityTypeOptionModelList = bidSecurityTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Bid security type options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple BidSecurityType options by ID.
     * @param idList   - List of BidSecurityType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple BidSecurityType options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        bidSecurityTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Bid security type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all BidSecurityType options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all BidSecurityType options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        bidSecurityTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Bid security type options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}