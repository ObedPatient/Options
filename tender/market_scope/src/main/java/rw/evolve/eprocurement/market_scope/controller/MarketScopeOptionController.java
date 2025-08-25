/**
 * REST API controller for managing MarketScope options.
 * Handles CRUD operations for MarketScope option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.market_scope.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.market_scope.dto.ResponseMessageDto;
import rw.evolve.eprocurement.market_scope.dto.MarketScopeOptionDto;
import rw.evolve.eprocurement.market_scope.model.MarketScopeOptionModel;
import rw.evolve.eprocurement.market_scope.service.MarketScopeOptionService;
import rw.evolve.eprocurement.market_scope.utils.MarketScopeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/market_scope_option")
@Tag(name = "Market Scope Option API")
public class MarketScopeOptionController {

    private final MarketScopeOptionService marketScopeOptionService;

    private  ModelMapper modelMapper = new ModelMapper();

    public MarketScopeOptionController(
            MarketScopeOptionService marketScopeOptionService,
            ModelMapper modelMapper
    ){
        this.marketScopeOptionService = marketScopeOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts MarketScopeOptionModel to MarketScopeOptionDto.
     * @param model - MarketScopeOptionModel to convert
     * @return      - Converted MarketScopeOptionDto
     */
    private MarketScopeOptionDto convertToDto(MarketScopeOptionModel model) {
        return modelMapper.map(model, MarketScopeOptionDto.class);
    }

    /**
     * Converts MarketScopeOptionDto to MarketScopeOptionModel.
     * @param marketScopeOptionDto - MarketScopeOptionDto to convert
     * @return                     - Converted MarketScopeOptionModel
     */
    private MarketScopeOptionModel convertToModel(MarketScopeOptionDto marketScopeOptionDto) {
        return modelMapper.map(marketScopeOptionDto, MarketScopeOptionModel.class);
    }

    /**
     * Creates a single MarketScope option with a generated ID.
     * @param marketScopeOptionDto - MarketScope option data
     * @return                     - Response with success message
     */
    @Operation(summary = "Create a single MarketScope option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody MarketScopeOptionDto marketScopeOptionDto) {
        MarketScopeOptionModel marketScopeOptionModel = convertToModel(marketScopeOptionDto);
        marketScopeOptionModel.setId(MarketScopeOptionIdGenerator.generateId());
        marketScopeOptionService.save(marketScopeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Market scope option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple MarketScope options with generated IDList.
     * @param marketScopeOptionDtoList - List of MarketScope option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create multiple MarketScope options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<MarketScopeOptionDto> marketScopeOptionDtoList) {
        List<MarketScopeOptionModel> marketScopeOptionModelList = new ArrayList<>();
        for (MarketScopeOptionDto marketScopeOptionDto : marketScopeOptionDtoList) {
            MarketScopeOptionModel model = convertToModel(marketScopeOptionDto);
            model.setId(MarketScopeOptionIdGenerator.generateId());
            marketScopeOptionModelList.add(model);
        }
        marketScopeOptionService.saveMany(marketScopeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Market scope options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a MarketScope option by ID (excludes soft-deleted).
     * @param id - MarketScope option ID
     * @return   - Response with MarketScope option data
     */
    @Operation(summary = "Get a single MarketScope option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        MarketScopeOptionModel model = marketScopeOptionService.readOne(id);
        MarketScopeOptionDto marketScopeOptionDto = convertToDto(model);
        return new ResponseEntity<>(marketScopeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted MarketScope options.
     * @return  - Response with list of MarketScope option data
     */
    @Operation(summary = "Get all available MarketScope options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<MarketScopeOptionModel> marketScopeOptionModelList = marketScopeOptionService.readAll();
        List<MarketScopeOptionDto> marketScopeOptionDtoList = new ArrayList<>();
        for (MarketScopeOptionModel marketScopeOptionModel : marketScopeOptionModelList) {
            marketScopeOptionDtoList.add(convertToDto(marketScopeOptionModel));
        }
        return new ResponseEntity<>(marketScopeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all MarketScope options, including soft-deleted.
     * @return        - Response with list of all MarketScope option data
     */
    @Operation(summary = "Get all MarketScope options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<MarketScopeOptionModel> modelList = marketScopeOptionService.hardReadAll();
        List<MarketScopeOptionDto> marketScopeOptionDtoList = new ArrayList<>();
        for (MarketScopeOptionModel model : modelList) {
            marketScopeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(marketScopeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple MarketScope options by ID (excludes soft-deleted).
     * @param idList - List of MarketScope option ID
     * @return       - Response with list of MarketScope option data
     */
    @Operation(summary = "Get multiple MarketScope options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<MarketScopeOptionModel> marketScopeOptionModelList = marketScopeOptionService.readMany(idList);
        List<MarketScopeOptionDto> marketScopeOptionDtoList = new ArrayList<>();
        for (MarketScopeOptionModel model : marketScopeOptionModelList) {
            marketScopeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(marketScopeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a MarketScope option by ID (excludes soft-deleted).
     *
     * @param marketScopeOptionDto - Updated MarketScope option data
     * @return                     - Response with updated MarketScope option data
     */
    @Operation(summary = "Update a single MarketScope option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody MarketScopeOptionDto marketScopeOptionDto){
        String modelId = marketScopeOptionDto.getId();
        MarketScopeOptionModel savedModel = marketScopeOptionService.readOne(modelId);
        savedModel.setName(marketScopeOptionDto.getName());
        savedModel.setDescription(marketScopeOptionDto.getDescription());
        marketScopeOptionService.updateOne(savedModel);
        MarketScopeOptionDto marketScopeOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(marketScopeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple MarketScope options (excludes soft-deleted).
     * @param marketScopeOptionDtoList - a List of updated MarketScope option data
     * @return                         - Response with list of updated MarketScope option data
     */
    @Operation(summary = "Update multiple MarketScope options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<MarketScopeOptionDto> marketScopeOptionDtoList) {
        List<MarketScopeOptionModel> inputModelList = new ArrayList<>();
        for (MarketScopeOptionDto marketScopeOptionDto : marketScopeOptionDtoList) {
            inputModelList.add(convertToModel(marketScopeOptionDto));
        }
        List<MarketScopeOptionModel> updatedModelList = marketScopeOptionService.updateMany(inputModelList);
        List<MarketScopeOptionDto> marketScopeOptionDtoArrayList = new ArrayList<>();
        for (MarketScopeOptionModel model : updatedModelList) {
            marketScopeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(marketScopeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a MarketScope option by ID, including soft-deleted.
     *
     * @param marketScopeOptionDto - Updated MarketScope option data
     * @return                     - Response with updated MarketScope option data
     */
    @Operation(summary = "Update a single MarketScope option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody MarketScopeOptionDto marketScopeOptionDto) {
        MarketScopeOptionModel marketScopeOptionModel = marketScopeOptionService.hardUpdate(convertToModel(marketScopeOptionDto));
        MarketScopeOptionDto marketScopeOptionDto1 = convertToDto(marketScopeOptionModel);
        return new ResponseEntity<>(marketScopeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all MarketScope options, including soft-deleted.
     * @param marketScopeOptionDtoList - List of updated MarketScope option data
     * @return                         - Response with list of updated MarketScope option data
     */
    @Operation(summary = "Update all MarketScope options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<MarketScopeOptionDto> marketScopeOptionDtoList) {
        List<MarketScopeOptionModel> inputModelList = new ArrayList<>();
        for (MarketScopeOptionDto marketScopeOptionDto : marketScopeOptionDtoList) {
            inputModelList.add(convertToModel(marketScopeOptionDto));
        }
        List<MarketScopeOptionModel> updatedModelList = marketScopeOptionService.hardUpdateAll(inputModelList);
        List<MarketScopeOptionDto> updatedMarketScopeOptionDtoList = new ArrayList<>();
        for (MarketScopeOptionModel model : updatedModelList) {
            updatedMarketScopeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedMarketScopeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a MarketScope option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single MarketScope option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        MarketScopeOptionModel deleteMarketScopeOptionModel = marketScopeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Market scope option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a MarketScope option by ID.
     * @param id       - MarketScope option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single MarketScope option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        marketScopeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Market scope option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple MarketScope options by ID.
     * @param idList    - List of MarketScope option IDList
     * @return          - Response with list of soft-deleted MarketScope option data
     */
    @Operation(summary = "Soft delete multiple MarketScope options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<MarketScopeOptionModel> deletedMarketScopeOptionModelList = marketScopeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Market scope options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple MarketScope options by ID.
     * @param idList   - List of MarketScope option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple MarketScope options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        marketScopeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Market scope options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all MarketScope options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all MarketScope options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        marketScopeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Market scope options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}