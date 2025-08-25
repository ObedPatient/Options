package rw.evolve.eprocurement.language_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.language_option.dto.LanguageOptionDto;
import rw.evolve.eprocurement.language_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.language_option.model.LanguageOptionModel;
import rw.evolve.eprocurement.language_option.service.LanguageOptionService;
import rw.evolve.eprocurement.language_option.utils.LanguageOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for managing Language options.
 * Handles CRUD operations for Language option data with soft and hard delete capabilities.
 */
@RestController
@RequestMapping("api/language_option")
@Tag(name = "Language Option API")
public class LanguageOptionController {

    private final LanguageOptionService languageOptionService;

    private final ModelMapper modelMapper;

    public LanguageOptionController(
            LanguageOptionService languageOptionService,
            ModelMapper modelMapper
    ){
        this.languageOptionService = languageOptionService;
        this.modelMapper = modelMapper;

    }
    /**
     * Converts LanguageOptionModel to LanguageOptionDto.
     * @param model - LanguageOptionModel to convert
     * @return      - Converted LanguageOptionDto
     */
    private LanguageOptionDto convertToDto(LanguageOptionModel model) {
        return modelMapper.map(model, LanguageOptionDto.class);
    }

    /**
     * Converts LanguageOptionDto to LanguageOptionModel.
     * @param languageOptionDto - LanguageOptionDto to convert
     * @return                  - Converted LanguageOptionModel
     */
    private LanguageOptionModel convertToModel(LanguageOptionDto languageOptionDto) {
        return modelMapper.map(languageOptionDto, LanguageOptionModel.class);
    }

    /**
     * Creates a single Language option with a generated ID.
     * @param languageOptionDto - Language option data
     * @return                  - Response with success message
     */
    @Operation(summary = "Create a single Language option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody LanguageOptionDto languageOptionDto) {
        LanguageOptionModel languageOptionModel = convertToModel(languageOptionDto);
        languageOptionModel.setId(LanguageOptionIdGenerator.generateId());
        languageOptionService.save(languageOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Language option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Language options with generated IDs.
     * @param languageOptionDtoList - List of Language option data
     * @return                      - Response with success message
     */
    @Operation(summary = "Create multiple Language options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<LanguageOptionDto> languageOptionDtoList) {
        List<LanguageOptionModel> languageOptionModelList = new ArrayList<>();
        for (LanguageOptionDto languageOptionDto : languageOptionDtoList) {
            LanguageOptionModel model = convertToModel(languageOptionDto);
            model.setId(LanguageOptionIdGenerator.generateId());
            languageOptionModelList.add(model);
        }
        languageOptionService.saveMany(languageOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Language options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Language option by ID (excludes soft-deleted).
     * @param id - Language option ID
     * @return   - Response with Language option data
     */
    @Operation(summary = "Get a single Language option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        LanguageOptionModel model = languageOptionService.readOne(id);
        LanguageOptionDto languageOptionDto = convertToDto(model);
        return new ResponseEntity<>(languageOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Language options.
     * @return - Response with list of Language option data
     */
    @Operation(summary = "Get all available Language options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<LanguageOptionModel> languageOptionModelList = languageOptionService.readAll();
        List<LanguageOptionDto> languageOptionDtoList = new ArrayList<>();
        for (LanguageOptionModel languageOptionModel : languageOptionModelList) {
            languageOptionDtoList.add(convertToDto(languageOptionModel));
        }
        return new ResponseEntity<>(languageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Language options, including soft-deleted.
     * @return - Response with list of all Language option data
     */
    @Operation(summary = "Get all Language options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<LanguageOptionModel> modelList = languageOptionService.hardReadAll();
        List<LanguageOptionDto> languageOptionDtoList = new ArrayList<>();
        for (LanguageOptionModel model : modelList) {
            languageOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(languageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Language options by ID (excludes soft-deleted).
     * @param idList - List of Language option IDs
     * @return       - Response with list of Language option data
     */
    @Operation(summary = "Get multiple Language options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<LanguageOptionModel> languageOptionModelList = languageOptionService.readMany(idList);
        List<LanguageOptionDto> languageOptionDtoList = new ArrayList<>();
        for (LanguageOptionModel model : languageOptionModelList) {
            languageOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(languageOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Language option by ID (excludes soft-deleted).
     * @param languageOptionDto - Updated Language option data
     * @return                  - Response with updated Language option data
     */
    @Operation(summary = "Update a single Language option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody LanguageOptionDto languageOptionDto) {
        String modelId = languageOptionDto.getId();
        LanguageOptionModel savedModel = languageOptionService.readOne(modelId);
        savedModel.setName(languageOptionDto.getName());
        savedModel.setDescription(languageOptionDto.getDescription());
        languageOptionService.updateOne(savedModel);
        LanguageOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Language options (excludes soft-deleted).
     * @param languageOptionDtoList - List of updated Language option data
     * @return                      - Response with list of updated Language option data
     */
    @Operation(summary = "Update multiple Language options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<LanguageOptionDto> languageOptionDtoList) {
        List<LanguageOptionModel> inputModelList = new ArrayList<>();
        for (LanguageOptionDto languageOptionDto : languageOptionDtoList) {
            inputModelList.add(convertToModel(languageOptionDto));
        }
        List<LanguageOptionModel> updatedModelList = languageOptionService.updateMany(inputModelList);
        List<LanguageOptionDto> updatedDtoList = new ArrayList<>();
        for (LanguageOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Language option by ID, including soft-deleted.
     * @param languageOptionDto - Updated Language option data
     * @return                  - Response with updated Language option data
     */
    @Operation(summary = "Update a single Language option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody LanguageOptionDto languageOptionDto) {
        LanguageOptionModel languageOptionModel = languageOptionService.hardUpdate(convertToModel(languageOptionDto));
        LanguageOptionDto updatedDto = convertToDto(languageOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Language options, including soft-deleted.
     * @param languageOptionDtoList - List of updated Language option data
     * @return                      - Response with list of updated Language option data
     */
    @Operation(summary = "Update all Language options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<LanguageOptionDto> languageOptionDtoList) {
        List<LanguageOptionModel> inputModelList = new ArrayList<>();
        for (LanguageOptionDto languageOptionDto : languageOptionDtoList) {
            inputModelList.add(convertToModel(languageOptionDto));
        }
        List<LanguageOptionModel> updatedModelList = languageOptionService.hardUpdateAll(inputModelList);
        List<LanguageOptionDto> updatedDtoList = new ArrayList<>();
        for (LanguageOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Language option by ID.
     * @param id - Language option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Language option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        LanguageOptionModel deletedModel = languageOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Language option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Language option by ID.
     * @param id - Language option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Language option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        languageOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Language option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Language options by ID.
     * @param idList - List of Language option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Language options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<LanguageOptionModel> deletedModelList = languageOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Language options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Language options by ID.
     * @param idList - List of Language option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Language options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        languageOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Language options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Language options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Language options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        languageOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Language options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}