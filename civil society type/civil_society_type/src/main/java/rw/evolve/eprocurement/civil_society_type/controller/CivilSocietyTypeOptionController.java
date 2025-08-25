/**
 * REST API controller for managing CivilSocietyType options.
 * Handles CRUD operations for CivilSocietyType option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.civil_society_type.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.civil_society_type.dto.ResponseMessageDto;
import rw.evolve.eprocurement.civil_society_type.dto.CivilSocietyTypeOptionDto;
import rw.evolve.eprocurement.civil_society_type.model.CivilSocietyTypeOptionModel;
import rw.evolve.eprocurement.civil_society_type.service.CivilSocietyTypeOptionService;
import rw.evolve.eprocurement.civil_society_type.utils.CivilSocietyTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/civil_society_type_option")
@Tag(name = "Civil Society Type Option API")
public class CivilSocietyTypeOptionController {

    private final CivilSocietyTypeOptionService civilSocietyTypeOptionService;

    private  ModelMapper modelMapper = new ModelMapper();

    public CivilSocietyTypeOptionController(
            CivilSocietyTypeOptionService civilSocietyTypeOptionService,
            ModelMapper modelMapper
    ){
        this.civilSocietyTypeOptionService = civilSocietyTypeOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts CivilSocietyTypeOptionModel to CivilSocietyTypeOptionDto.
     * @param model - CivilSocietyTypeOptionModel to convert
     * @return      - Converted CivilSocietyTypeOptionDto
     */
    private CivilSocietyTypeOptionDto convertToDto(CivilSocietyTypeOptionModel model) {
        return modelMapper.map(model, CivilSocietyTypeOptionDto.class);
    }

    /**
     * Converts CivilSocietyTypeOptionDto to CivilSocietyTypeOptionModel.
     * @param civilSocietyTypeOptionDto - CivilSocietyTypeOptionDto to convert
     * @return                          - Converted CivilSocietyTypeOptionModel
     */
    private CivilSocietyTypeOptionModel convertToModel(CivilSocietyTypeOptionDto civilSocietyTypeOptionDto) {
        return modelMapper.map(civilSocietyTypeOptionDto, CivilSocietyTypeOptionModel.class);
    }

    /**
     * Creates a single CivilSocietyType option with a generated ID.
     * @param civilSocietyTypeOptionDto - CivilSocietyType option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create a single CivilSocietyType option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody CivilSocietyTypeOptionDto civilSocietyTypeOptionDto) {
        CivilSocietyTypeOptionModel civilSocietyTypeOptionModel = convertToModel(civilSocietyTypeOptionDto);
        civilSocietyTypeOptionModel.setId(CivilSocietyTypeOptionIdGenerator.generateId());
        civilSocietyTypeOptionService.save(civilSocietyTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Civil society type option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple CivilSocietyType options with generated IDList.
     * @param civilSocietyTypeOptionDtoList - List of CivilSocietyType option data
     * @return                              - Response with success message
     */
    @Operation(summary = "Create multiple CivilSocietyType options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoList) {
        List<CivilSocietyTypeOptionModel> civilSocietyTypeOptionModelList = new ArrayList<>();
        for (CivilSocietyTypeOptionDto civilSocietyTypeOptionDto : civilSocietyTypeOptionDtoList) {
            CivilSocietyTypeOptionModel model = convertToModel(civilSocietyTypeOptionDto);
            model.setId(CivilSocietyTypeOptionIdGenerator.generateId());
            civilSocietyTypeOptionModelList.add(model);
        }
        civilSocietyTypeOptionService.saveMany(civilSocietyTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Civil society type options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a CivilSocietyType option by ID (excludes soft-deleted).
     * @param id - CivilSocietyType option ID
     * @return   - Response with CivilSocietyType option data
     */
    @Operation(summary = "Get a single CivilSocietyType option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        CivilSocietyTypeOptionModel model = civilSocietyTypeOptionService.readOne(id);
        CivilSocietyTypeOptionDto civilSocietyTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(civilSocietyTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted CivilSocietyType options.
     * @return  - Response with list of CivilSocietyType option data
     */
    @Operation(summary = "Get all available CivilSocietyType options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<CivilSocietyTypeOptionModel> civilSocietyTypeOptionModelList = civilSocietyTypeOptionService.readAll();
        List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoList = new ArrayList<>();
        for (CivilSocietyTypeOptionModel civilSocietyTypeOptionModel : civilSocietyTypeOptionModelList) {
            civilSocietyTypeOptionDtoList.add(convertToDto(civilSocietyTypeOptionModel));
        }
        return new ResponseEntity<>(civilSocietyTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all CivilSocietyType options, including soft-deleted.
     * @return        - Response with list of all CivilSocietyType option data
     */
    @Operation(summary = "Get all CivilSocietyType options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<CivilSocietyTypeOptionModel> modelList = civilSocietyTypeOptionService.hardReadAll();
        List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoList = new ArrayList<>();
        for (CivilSocietyTypeOptionModel model : modelList) {
            civilSocietyTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(civilSocietyTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple CivilSocietyType options by ID (excludes soft-deleted).
     * @param idList - List of CivilSocietyType option ID
     * @return       - Response with list of CivilSocietyType option data
     */
    @Operation(summary = "Get multiple CivilSocietyType options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<CivilSocietyTypeOptionModel> civilSocietyTypeOptionModelList = civilSocietyTypeOptionService.readMany(idList);
        List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoList = new ArrayList<>();
        for (CivilSocietyTypeOptionModel model : civilSocietyTypeOptionModelList) {
            civilSocietyTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(civilSocietyTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a CivilSocietyType option by ID (excludes soft-deleted).
     *
     * @param civilSocietyTypeOptionDto - Updated CivilSocietyType option data
     * @return                          - Response with updated CivilSocietyType option data
     */
    @Operation(summary = "Update a single CivilSocietyType option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody CivilSocietyTypeOptionDto civilSocietyTypeOptionDto){
        String modelId = civilSocietyTypeOptionDto.getId();
        CivilSocietyTypeOptionModel savedModel = civilSocietyTypeOptionService.readOne(modelId);
        savedModel.setName(civilSocietyTypeOptionDto.getName());
        savedModel.setDescription(civilSocietyTypeOptionDto.getDescription());
        civilSocietyTypeOptionService.updateOne(savedModel);
        CivilSocietyTypeOptionDto civilSocietyTypeOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(civilSocietyTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple CivilSocietyType options (excludes soft-deleted).
     * @param civilSocietyTypeOptionDtoList - a List of updated CivilSocietyType option data
     * @return                              - Response with list of updated CivilSocietyType option data
     */
    @Operation(summary = "Update multiple CivilSocietyType options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoList) {
        List<CivilSocietyTypeOptionModel> inputModelList = new ArrayList<>();
        for (CivilSocietyTypeOptionDto civilSocietyTypeOptionDto : civilSocietyTypeOptionDtoList) {
            inputModelList.add(convertToModel(civilSocietyTypeOptionDto));
        }
        List<CivilSocietyTypeOptionModel> updatedModelList = civilSocietyTypeOptionService.updateMany(inputModelList);
        List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoArrayList = new ArrayList<>();
        for (CivilSocietyTypeOptionModel model : updatedModelList) {
            civilSocietyTypeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(civilSocietyTypeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a CivilSocietyType option by ID, including soft-deleted.
     *
     * @param civilSocietyTypeOptionDto - Updated CivilSocietyType option data
     * @return                          - Response with updated CivilSocietyType option data
     */
    @Operation(summary = "Update a single CivilSocietyType option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody CivilSocietyTypeOptionDto civilSocietyTypeOptionDto) {
        CivilSocietyTypeOptionModel civilSocietyTypeOptionModel = civilSocietyTypeOptionService.hardUpdate(convertToModel(civilSocietyTypeOptionDto));
        CivilSocietyTypeOptionDto civilSocietyTypeOptionDto1 = convertToDto(civilSocietyTypeOptionModel);
        return new ResponseEntity<>(civilSocietyTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all CivilSocietyType options, including soft-deleted.
     * @param civilSocietyTypeOptionDtoList - List of updated CivilSocietyType option data
     * @return                              - Response with list of updated CivilSocietyType option data
     */
    @Operation(summary = "Update all CivilSocietyType options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<CivilSocietyTypeOptionDto> civilSocietyTypeOptionDtoList) {
        List<CivilSocietyTypeOptionModel> inputModelList = new ArrayList<>();
        for (CivilSocietyTypeOptionDto civilSocietyTypeOptionDto : civilSocietyTypeOptionDtoList) {
            inputModelList.add(convertToModel(civilSocietyTypeOptionDto));
        }
        List<CivilSocietyTypeOptionModel> updatedModelList = civilSocietyTypeOptionService.hardUpdateAll(inputModelList);
        List<CivilSocietyTypeOptionDto> updatedCivilSocietyTypeOptionDtoList = new ArrayList<>();
        for (CivilSocietyTypeOptionModel model : updatedModelList) {
            updatedCivilSocietyTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedCivilSocietyTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a CivilSocietyType option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single CivilSocietyType option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        CivilSocietyTypeOptionModel deleteCivilSocietyTypeOptionModel = civilSocietyTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Civil society type option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a CivilSocietyType option by ID.
     * @param id       - CivilSocietyType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single CivilSocietyType option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        civilSocietyTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Civil society type option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple CivilSocietyType options by ID.
     * @param idList    - List of CivilSocietyType option IDList
     * @return          - Response with list of soft-deleted CivilSocietyType option data
     */
    @Operation(summary = "Soft delete multiple CivilSocietyType options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<CivilSocietyTypeOptionModel> deletedCivilSocietyTypeOptionModelList = civilSocietyTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Civil society type options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple CivilSocietyType options by ID.
     * @param idList   - List of CivilSocietyType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple CivilSocietyType options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        civilSocietyTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Civil society type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all CivilSocietyType options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all CivilSocietyType options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        civilSocietyTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Civil society type options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}