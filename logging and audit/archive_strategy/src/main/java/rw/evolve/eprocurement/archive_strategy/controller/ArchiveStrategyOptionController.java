/**
 * REST API controller for managing Archive strategy options.
 * Handles CRUD operations for Archive strategy option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.archive_strategy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.archive_strategy.dto.ArchiveStrategyOptionDto;
import rw.evolve.eprocurement.archive_strategy.dto.ResponseMessageDto;
import rw.evolve.eprocurement.archive_strategy.model.ArchiveStrategyOptionModel;
import rw.evolve.eprocurement.archive_strategy.service.ArchiveStrategyOptionService;
import rw.evolve.eprocurement.archive_strategy.utils.ArchiveStrategyOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/archive_strategy_option")
@Tag(name = "Archive Strategy Option API")
public class ArchiveStrategyOptionController {

    @Autowired
    private ArchiveStrategyOptionService archiveStrategyOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts ArchiveStrategyOptionModel to ArchiveStrategyOptionDto.
     * @param model - ArchiveStrategyOptionModel to convert
     * @return      - Converted ArchiveStrategyOptionDto
     */
    private ArchiveStrategyOptionDto convertToDto(ArchiveStrategyOptionModel model) {
        return modelMapper.map(model, ArchiveStrategyOptionDto.class);
    }

    /**
     * Converts ArchiveStrategyOptionDto to ArchiveStrategyOptionModel.
     * @param archiveStrategyOptionDto - ArchiveStrategyOptionDto to convert
     * @return                         - Converted ArchiveStrategyOptionModel
     */
    private ArchiveStrategyOptionModel convertToModel(ArchiveStrategyOptionDto archiveStrategyOptionDto) {
        return modelMapper.map(archiveStrategyOptionDto, ArchiveStrategyOptionModel.class);
    }

    /**
     * Creates a single Archive strategy option with a generated ID.
     * @param archiveStrategyOptionDto - Archive strategy option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create a single Archive strategy option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ArchiveStrategyOptionDto archiveStrategyOptionDto) {
        ArchiveStrategyOptionModel archiveStrategyOptionModel = convertToModel(archiveStrategyOptionDto);
        archiveStrategyOptionModel.setId(ArchiveStrategyOptionIdGenerator.generateId());
        archiveStrategyOptionService.save(archiveStrategyOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Archive strategy option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Archive strategy options with generated IDs.
     * @param archiveStrategyOptionDtoList - List of Archive strategy option data
     * @return                             - Response with success message
     */
    @Operation(summary = "Create multiple Archive strategy options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ArchiveStrategyOptionDto> archiveStrategyOptionDtoList) {
        List<ArchiveStrategyOptionModel> archiveStrategyOptionModelList = new ArrayList<>();
        for (ArchiveStrategyOptionDto archiveStrategyOptionDto : archiveStrategyOptionDtoList) {
            ArchiveStrategyOptionModel model = convertToModel(archiveStrategyOptionDto);
            model.setId(ArchiveStrategyOptionIdGenerator.generateId());
            archiveStrategyOptionModelList.add(model);
        }
        archiveStrategyOptionService.saveMany(archiveStrategyOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Archive strategy options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves an Archive strategy option by ID (excludes soft-deleted).
     * @param id - Archive strategy option ID
     * @return   - Response with Archive strategy option data
     */
    @Operation(summary = "Get a single Archive strategy option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ArchiveStrategyOptionModel model = archiveStrategyOptionService.readOne(id);
        ArchiveStrategyOptionDto archiveStrategyOptionDto = convertToDto(model);
        return new ResponseEntity<>(archiveStrategyOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Archive strategy options.
     * @return - Response with list of Archive strategy option data
     */
    @Operation(summary = "Get all available Archive strategy options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ArchiveStrategyOptionModel> archiveStrategyOptionModelList = archiveStrategyOptionService.readAll();
        List<ArchiveStrategyOptionDto> archiveStrategyOptionDtoList = new ArrayList<>();
        for (ArchiveStrategyOptionModel archiveStrategyOptionModel : archiveStrategyOptionModelList) {
            archiveStrategyOptionDtoList.add(convertToDto(archiveStrategyOptionModel));
        }
        return new ResponseEntity<>(archiveStrategyOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Archive strategy options, including soft-deleted.
     * @return - Response with list of all Archive strategy option data
     */
    @Operation(summary = "Get all Archive strategy options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ArchiveStrategyOptionModel> modelList = archiveStrategyOptionService.hardReadAll();
        List<ArchiveStrategyOptionDto> archiveStrategyOptionDtoList = new ArrayList<>();
        for (ArchiveStrategyOptionModel model : modelList) {
            archiveStrategyOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(archiveStrategyOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Archive strategy options by ID (excludes soft-deleted).
     * @param idList - List of Archive strategy option IDs
     * @return       - Response with list of Archive strategy option data
     */
    @Operation(summary = "Get multiple Archive strategy options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ArchiveStrategyOptionModel> archiveStrategyOptionModelList = archiveStrategyOptionService.readMany(idList);
        List<ArchiveStrategyOptionDto> archiveStrategyOptionDtoList = new ArrayList<>();
        for (ArchiveStrategyOptionModel model : archiveStrategyOptionModelList) {
            archiveStrategyOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(archiveStrategyOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Archive strategy option by ID (excludes soft-deleted).
     * @param archiveStrategyOptionDto - Updated Archive strategy option data
     * @return                         - Response with updated Archive strategy option data
     */
    @Operation(summary = "Update a single Archive strategy option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ArchiveStrategyOptionDto archiveStrategyOptionDto) {
        String modelId = archiveStrategyOptionDto.getId();
        ArchiveStrategyOptionModel savedModel = archiveStrategyOptionService.readOne(modelId);
        savedModel.setName(archiveStrategyOptionDto.getName());
        savedModel.setDescription(archiveStrategyOptionDto.getDescription());
        archiveStrategyOptionService.updateOne(savedModel);
        ArchiveStrategyOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Archive strategy options (excludes soft-deleted).
     * @param archiveStrategyOptionDtoList - List of updated Archive strategy option data
     * @return                             - Response with list of updated Archive strategy option data
     */
    @Operation(summary = "Update multiple Archive strategy options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ArchiveStrategyOptionDto> archiveStrategyOptionDtoList) {
        List<ArchiveStrategyOptionModel> inputModelList = new ArrayList<>();
        for (ArchiveStrategyOptionDto archiveStrategyOptionDto : archiveStrategyOptionDtoList) {
            inputModelList.add(convertToModel(archiveStrategyOptionDto));
        }
        List<ArchiveStrategyOptionModel> updatedModelList = archiveStrategyOptionService.updateMany(inputModelList);
        List<ArchiveStrategyOptionDto> updatedDtoList = new ArrayList<>();
        for (ArchiveStrategyOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Archive strategy option by ID, including soft-deleted.
     * @param archiveStrategyOptionDto - Updated Archive strategy option data
     * @return                         - Response with updated Archive strategy option data
     */
    @Operation(summary = "Update a single Archive strategy option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ArchiveStrategyOptionDto archiveStrategyOptionDto) {
        ArchiveStrategyOptionModel archiveStrategyOptionModel = archiveStrategyOptionService.hardUpdate(convertToModel(archiveStrategyOptionDto));
        ArchiveStrategyOptionDto updatedDto = convertToDto(archiveStrategyOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Archive strategy options, including soft-deleted.
     * @param archiveStrategyOptionDtoList - List of updated Archive strategy option data
     * @return                             - Response with list of updated Archive strategy option data
     */
    @Operation(summary = "Update all Archive strategy options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ArchiveStrategyOptionDto> archiveStrategyOptionDtoList) {
        List<ArchiveStrategyOptionModel> inputModelList = new ArrayList<>();
        for (ArchiveStrategyOptionDto archiveStrategyOptionDto : archiveStrategyOptionDtoList) {
            inputModelList.add(convertToModel(archiveStrategyOptionDto));
        }
        List<ArchiveStrategyOptionModel> updatedModelList = archiveStrategyOptionService.hardUpdateAll(inputModelList);
        List<ArchiveStrategyOptionDto> updatedDtoList = new ArrayList<>();
        for (ArchiveStrategyOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes an Archive strategy option by ID.
     * @param id - Archive strategy option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Archive strategy option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        ArchiveStrategyOptionModel deletedModel = archiveStrategyOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Archive strategy option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes an Archive strategy option by ID.
     * @param id - Archive strategy option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Archive strategy option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        archiveStrategyOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Archive strategy option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Archive strategy options by ID.
     * @param idList - List of Archive strategy option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Archive strategy options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ArchiveStrategyOptionModel> deletedModelList = archiveStrategyOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Archive strategy options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Archive strategy options by ID.
     * @param idList - List of Archive strategy option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Archive strategy options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        archiveStrategyOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Archive strategy options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Archive strategy options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Archive strategy options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        archiveStrategyOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Archive strategy options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}