/**
 * REST API controller for managing SelectionMethodOption options.
 * Handles CRUD operations for SelectionMethodOption option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.selection_method_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.selection_method_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.selection_method_option.dto.SelectionMethodOptionDto;
import rw.evolve.eprocurement.selection_method_option.model.SelectionMethodOptionModel;
import rw.evolve.eprocurement.selection_method_option.service.SelectionMethodOptionService;
import rw.evolve.eprocurement.selection_method_option.utils.SelectionMethodOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/selection_method_option")
@Tag(name = "Selection Method Option API")
public class SelectionMethodOptionController {

    @Autowired
    private SelectionMethodOptionService selectionMethodOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts SelectionMethodOptionModel to SelectionMethodOptionDto.
     * @param model - SelectionMethodOptionModel to convert
     * @return      - Converted SelectionMethodOptionDto
     */
    private SelectionMethodOptionDto convertToDto(SelectionMethodOptionModel model) {
        return modelMapper.map(model, SelectionMethodOptionDto.class);
    }

    /**
     * Converts SelectionMethodOptionDto to SelectionMethodOptionModel.
     * @param selectionMethodOptionDto - SelectionMethodOptionDto to convert
     * @return                        - Converted SelectionMethodOptionModel
     */
    private SelectionMethodOptionModel convertToModel(SelectionMethodOptionDto selectionMethodOptionDto) {
        return modelMapper.map(selectionMethodOptionDto, SelectionMethodOptionModel.class);
    }

    /**
     * Creates a single SelectionMethodOption option with a generated ID.
     * @param selectionMethodOptionDto - SelectionMethodOption option data
     * @return                        - Response with success message
     */
    @Operation(summary = "Create a single SelectionMethodOption option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody SelectionMethodOptionDto selectionMethodOptionDto) {
        SelectionMethodOptionModel selectionMethodOptionModel = convertToModel(selectionMethodOptionDto);
        selectionMethodOptionModel.setId(SelectionMethodOptionIdGenerator.generateId());
        selectionMethodOptionService.save(selectionMethodOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection method option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple SelectionMethodOption options with generated IDList.
     * @param selectionMethodOptionDtoList - List of SelectionMethodOption option data
     * @return                            - Response with success message
     */
    @Operation(summary = "Create multiple SelectionMethodOption options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<SelectionMethodOptionDto> selectionMethodOptionDtoList) {
        List<SelectionMethodOptionModel> selectionMethodOptionModelList = new ArrayList<>();
        for (SelectionMethodOptionDto selectionMethodOptionDto : selectionMethodOptionDtoList) {
            SelectionMethodOptionModel model = convertToModel(selectionMethodOptionDto);
            model.setId(SelectionMethodOptionIdGenerator.generateId());
            selectionMethodOptionModelList.add(model);
        }
        selectionMethodOptionService.saveMany(selectionMethodOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection method options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a SelectionMethodOption option by ID (excludes soft-deleted).
     * @param id - SelectionMethodOption option ID
     * @return   - Response with SelectionMethodOption option data
     */
    @Operation(summary = "Get a single SelectionMethodOption option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        SelectionMethodOptionModel model = selectionMethodOptionService.readOne(id);
        SelectionMethodOptionDto selectionMethodOptionDto = convertToDto(model);
        return new ResponseEntity<>(selectionMethodOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted SelectionMethodOption options.
     * @return  - Response with list of SelectionMethodOption option data
     */
    @Operation(summary = "Get all available SelectionMethodOption options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<SelectionMethodOptionModel> selectionMethodOptionModelList = selectionMethodOptionService.readAll();
        List<SelectionMethodOptionDto> selectionMethodOptionDtoList = new ArrayList<>();
        for (SelectionMethodOptionModel selectionMethodOptionModel : selectionMethodOptionModelList) {
            selectionMethodOptionDtoList.add(convertToDto(selectionMethodOptionModel));
        }
        return new ResponseEntity<>(selectionMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all SelectionMethodOption options, including soft-deleted.
     * @return        - Response with list of all SelectionMethodOption option data
     */
    @Operation(summary = "Get all SelectionMethodOption options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<SelectionMethodOptionModel> modelList = selectionMethodOptionService.hardReadAll();
        List<SelectionMethodOptionDto> selectionMethodOptionDtoList = new ArrayList<>();
        for (SelectionMethodOptionModel model : modelList) {
            selectionMethodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(selectionMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple SelectionMethodOption options by ID (excludes soft-deleted).
     * @param idList - List of SelectionMethodOption option ID
     * @return       - Response with list of SelectionMethodOption option data
     */
    @Operation(summary = "Get multiple SelectionMethodOption options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<SelectionMethodOptionModel> selectionMethodOptionModelList = selectionMethodOptionService.readMany(idList);
        List<SelectionMethodOptionDto> selectionMethodOptionDtoList = new ArrayList<>();
        for (SelectionMethodOptionModel model : selectionMethodOptionModelList) {
            selectionMethodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(selectionMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a SelectionMethodOption option by ID (excludes soft-deleted).
     *
     * @param selectionMethodOptionDto - Updated SelectionMethodOption option data
     * @return                        - Response with updated SelectionMethodOption option data
     */
    @Operation(summary = "Update a single SelectionMethodOption option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody SelectionMethodOptionDto selectionMethodOptionDto){
        String modelId = selectionMethodOptionDto.getId();
        SelectionMethodOptionModel savedModel = selectionMethodOptionService.readOne(modelId);
        savedModel.setName(selectionMethodOptionDto.getName());
        savedModel.setDescription(selectionMethodOptionDto.getDescription());
        selectionMethodOptionService.updateOne(savedModel);
        SelectionMethodOptionDto selectionMethodOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(selectionMethodOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple SelectionMethodOption options (excludes soft-deleted).
     * @param selectionMethodOptionDtoList - a List of updated SelectionMethodOption option data
     * @return                            - Response with list of updated SelectionMethodOption option data
     */
    @Operation(summary = "Update multiple SelectionMethodOption options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<SelectionMethodOptionDto> selectionMethodOptionDtoList) {
        List<SelectionMethodOptionModel> inputModelList = new ArrayList<>();
        for (SelectionMethodOptionDto selectionMethodOptionDto : selectionMethodOptionDtoList) {
            inputModelList.add(convertToModel(selectionMethodOptionDto));
        }
        List<SelectionMethodOptionModel> updatedModelList = selectionMethodOptionService.updateMany(inputModelList);
        List<SelectionMethodOptionDto> selectionMethodOptionDtoArrayList = new ArrayList<>();
        for (SelectionMethodOptionModel model : updatedModelList) {
            selectionMethodOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(selectionMethodOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a SelectionMethodOption option by ID, including soft-deleted.
     *
     * @param selectionMethodOptionDto - Updated SelectionMethodOption option data
     * @return                        - Response with updated SelectionMethodOption option data
     */
    @Operation(summary = "Update a single SelectionMethodOption option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody SelectionMethodOptionDto selectionMethodOptionDto) {
        SelectionMethodOptionModel selectionMethodOptionModel = selectionMethodOptionService.hardUpdate(convertToModel(selectionMethodOptionDto));
        SelectionMethodOptionDto selectionMethodOptionDto1 = convertToDto(selectionMethodOptionModel);
        return new ResponseEntity<>(selectionMethodOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all SelectionMethodOption options, including soft-deleted.
     * @param selectionMethodOptionDtoList - List of updated SelectionMethodOption option data
     * @return                            - Response with list of updated SelectionMethodOption option data
     */
    @Operation(summary = "Update all SelectionMethodOption options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<SelectionMethodOptionDto> selectionMethodOptionDtoList) {
        List<SelectionMethodOptionModel> inputModelList = new ArrayList<>();
        for (SelectionMethodOptionDto selectionMethodOptionDto : selectionMethodOptionDtoList) {
            inputModelList.add(convertToModel(selectionMethodOptionDto));
        }
        List<SelectionMethodOptionModel> updatedModelList = selectionMethodOptionService.hardUpdateAll(inputModelList);
        List<SelectionMethodOptionDto> updatedSelectionMethodOptionDtoList = new ArrayList<>();
        for (SelectionMethodOptionModel model : updatedModelList) {
            updatedSelectionMethodOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedSelectionMethodOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a SelectionMethodOption option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single SelectionMethodOption option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        SelectionMethodOptionModel deleteSelectionMethodOptionModel = selectionMethodOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection method option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a SelectionMethodOption option by ID.
     * @param id       - SelectionMethodOption option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single SelectionMethodOption option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        selectionMethodOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection method option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple SelectionMethodOption options by ID.
     * @param idList    - List of SelectionMethodOption option IDList
     * @return          - Response with list of soft-deleted SelectionMethodOption option data
     */
    @Operation(summary = "Soft delete multiple SelectionMethodOption options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<SelectionMethodOptionModel> deletedSelectionMethodOptionModelList = selectionMethodOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Selection method options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple SelectionMethodOption options by ID.
     * @param idList   - List of SelectionMethodOption option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple SelectionMethodOption options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        selectionMethodOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Selection method options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all SelectionMethodOption options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all SelectionMethodOption options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        selectionMethodOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Selection method options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}