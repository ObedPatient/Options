/**
 * REST API controller for managing Metadata type options.
 * Handles CRUD operations for Metadata type option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.metadata_type.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.metadata_type.dto.MetadataTypeOptionDto;
import rw.evolve.eprocurement.metadata_type.dto.ResponseMessageDto;
import rw.evolve.eprocurement.metadata_type.model.MetadataTypeOptionModel;
import rw.evolve.eprocurement.metadata_type.service.MetadataTypeOptionService;
import rw.evolve.eprocurement.metadata_type.utils.MetadataTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/metadata_type_option")
@Tag(name = "Metadata Type Option API")
public class MetadataTypeOptionController {

    @Autowired
    private MetadataTypeOptionService metadataTypeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts MetadataTypeOptionModel to MetadataTypeOptionDto.
     * @param model - MetadataTypeOptionModel to convert
     * @return      - Converted MetadataTypeOptionDto
     */
    private MetadataTypeOptionDto convertToDto(MetadataTypeOptionModel model) {
        return modelMapper.map(model, MetadataTypeOptionDto.class);
    }

    /**
     * Converts MetadataTypeOptionDto to MetadataTypeOptionModel.
     * @param metadataTypeOptionDto - MetadataTypeOptionDto to convert
     * @return                      - Converted MetadataTypeOptionModel
     */
    private MetadataTypeOptionModel convertToModel(MetadataTypeOptionDto metadataTypeOptionDto) {
        return modelMapper.map(metadataTypeOptionDto, MetadataTypeOptionModel.class);
    }

    /**
     * Creates a single Metadata type option with a generated ID.
     * @param metadataTypeOptionDto - Metadata type option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create a single Metadata type option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody MetadataTypeOptionDto metadataTypeOptionDto) {
        MetadataTypeOptionModel metadataTypeOptionModel = convertToModel(metadataTypeOptionDto);
        metadataTypeOptionModel.setId(MetadataTypeOptionIdGenerator.generateId());
        metadataTypeOptionService.save(metadataTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Metadata type option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Metadata type options with generated IDs.
     * @param metadataTypeOptionDtoList - List of Metadata type option data
     * @return                          - Response with success message
     */
    @Operation(summary = "Create multiple Metadata type options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<MetadataTypeOptionDto> metadataTypeOptionDtoList) {
        List<MetadataTypeOptionModel> metadataTypeOptionModelList = new ArrayList<>();
        for (MetadataTypeOptionDto metadataTypeOptionDto : metadataTypeOptionDtoList) {
            MetadataTypeOptionModel model = convertToModel(metadataTypeOptionDto);
            model.setId(MetadataTypeOptionIdGenerator.generateId());
            metadataTypeOptionModelList.add(model);
        }
        metadataTypeOptionService.saveMany(metadataTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Metadata type options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Metadata type option by ID (excludes soft-deleted).
     * @param id - Metadata type option ID
     * @return   - Response with Metadata type option data
     */
    @Operation(summary = "Get a single Metadata type option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        MetadataTypeOptionModel model = metadataTypeOptionService.readOne(id);
        MetadataTypeOptionDto metadataTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(metadataTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Metadata type options.
     * @return - Response with list of Metadata type option data
     */
    @Operation(summary = "Get all available Metadata type options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<MetadataTypeOptionModel> metadataTypeOptionModelList = metadataTypeOptionService.readAll();
        List<MetadataTypeOptionDto> metadataTypeOptionDtoList = new ArrayList<>();
        for (MetadataTypeOptionModel metadataTypeOptionModel : metadataTypeOptionModelList) {
            metadataTypeOptionDtoList.add(convertToDto(metadataTypeOptionModel));
        }
        return new ResponseEntity<>(metadataTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Metadata type options, including soft-deleted.
     * @return - Response with list of all Metadata type option data
     */
    @Operation(summary = "Get all Metadata type options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<MetadataTypeOptionModel> modelList = metadataTypeOptionService.hardReadAll();
        List<MetadataTypeOptionDto> metadataTypeOptionDtoList = new ArrayList<>();
        for (MetadataTypeOptionModel model : modelList) {
            metadataTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(metadataTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Metadata type options by ID (excludes soft-deleted).
     * @param idList - List of Metadata type option IDs
     * @return       - Response with list of Metadata type option data
     */
    @Operation(summary = "Get multiple Metadata type options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<MetadataTypeOptionModel> metadataTypeOptionModelList = metadataTypeOptionService.readMany(idList);
        List<MetadataTypeOptionDto> metadataTypeOptionDtoList = new ArrayList<>();
        for (MetadataTypeOptionModel model : metadataTypeOptionModelList) {
            metadataTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(metadataTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Metadata type option by ID (excludes soft-deleted).
     * @param metadataTypeOptionDto - Updated Metadata type option data
     * @return                      - Response with updated Metadata type option data
     */
    @Operation(summary = "Update a single Metadata type option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody MetadataTypeOptionDto metadataTypeOptionDto) {
        String modelId = metadataTypeOptionDto.getId();
        MetadataTypeOptionModel savedModel = metadataTypeOptionService.readOne(modelId);
        savedModel.setName(metadataTypeOptionDto.getName());
        savedModel.setDescription(metadataTypeOptionDto.getDescription());
        metadataTypeOptionService.updateOne(savedModel);
        MetadataTypeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Metadata type options (excludes soft-deleted).
     * @param metadataTypeOptionDtoList - List of updated Metadata type option data
     * @return                          - Response with list of updated Metadata type option data
     */
    @Operation(summary = "Update multiple Metadata type options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<MetadataTypeOptionDto> metadataTypeOptionDtoList) {
        List<MetadataTypeOptionModel> inputModelList = new ArrayList<>();
        for (MetadataTypeOptionDto metadataTypeOptionDto : metadataTypeOptionDtoList) {
            inputModelList.add(convertToModel(metadataTypeOptionDto));
        }
        List<MetadataTypeOptionModel> updatedModelList = metadataTypeOptionService.updateMany(inputModelList);
        List<MetadataTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (MetadataTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Metadata type option by ID, including soft-deleted.
     * @param metadataTypeOptionDto - Updated Metadata type option data
     * @return                      - Response with updated Metadata type option data
     */
    @Operation(summary = "Update a single Metadata type option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody MetadataTypeOptionDto metadataTypeOptionDto) {
        MetadataTypeOptionModel metadataTypeOptionModel = metadataTypeOptionService.hardUpdate(convertToModel(metadataTypeOptionDto));
        MetadataTypeOptionDto updatedDto = convertToDto(metadataTypeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Metadata type options, including soft-deleted.
     * @param metadataTypeOptionDtoList - List of updated Metadata type option data
     * @return                          - Response with list of updated Metadata type option data
     */
    @Operation(summary = "Update all Metadata type options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<MetadataTypeOptionDto> metadataTypeOptionDtoList) {
        List<MetadataTypeOptionModel> inputModelList = new ArrayList<>();
        for (MetadataTypeOptionDto metadataTypeOptionDto : metadataTypeOptionDtoList) {
            inputModelList.add(convertToModel(metadataTypeOptionDto));
        }
        List<MetadataTypeOptionModel> updatedModelList = metadataTypeOptionService.hardUpdateAll(inputModelList);
        List<MetadataTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (MetadataTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Metadata type option by ID.
     * @param id - Metadata type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Metadata type option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        MetadataTypeOptionModel deletedModel = metadataTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Metadata type option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Metadata type option by ID.
     * @param id - Metadata type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Metadata type option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        metadataTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Metadata type option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Metadata type options by ID.
     * @param idList - List of Metadata type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Metadata type options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<MetadataTypeOptionModel> deletedModelList = metadataTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Metadata type options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Metadata type options by ID.
     * @param idList - List of Metadata type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Metadata type options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        metadataTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Metadata type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Metadata type options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Metadata type options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        metadataTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Metadata type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}