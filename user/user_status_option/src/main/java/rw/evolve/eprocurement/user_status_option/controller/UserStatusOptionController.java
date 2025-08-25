/**
 * REST API controller for managing User Status options.
 * Handles CRUD operations for User Status option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.user_status_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.user_status_option.dto.UserStatusOptionDto;
import rw.evolve.eprocurement.user_status_option.model.UserStatusOptionModel;
import rw.evolve.eprocurement.user_status_option.service.UserStatusOptionService;
import rw.evolve.eprocurement.user_status_option.utils.UserStatusOptionIdGenerator;
import rw.evolve.eprocurement.user_status_option.dto.ResponseMessageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/user_status_option")
@Tag(name = "User Status Option API")
public class UserStatusOptionController {

    private final UserStatusOptionService userStatusOptionService;

    private final ModelMapper modelMapper;

    public UserStatusOptionController(
            UserStatusOptionService userStatusOptionService,
            ModelMapper modelMapper
    ){
        this.userStatusOptionService = userStatusOptionService;
        this.modelMapper = modelMapper;

    }
    /**
     * Converts UserStatusOptionModel to UserStatusOptionDto.
     * @param model - UserStatusOptionModel to convert
     * @return      - Converted UserStatusOptionDto
     */
    private UserStatusOptionDto convertToDto(UserStatusOptionModel model) {
        return modelMapper.map(model, UserStatusOptionDto.class);
    }

    /**
     * Converts UserStatusOptionDto to UserStatusOptionModel.
     * @param userStatusOptionDto - UserStatusOptionDto to convert
     * @return                    - Converted UserStatusOptionModel
     */
    private UserStatusOptionModel convertToModel(UserStatusOptionDto userStatusOptionDto) {
        return modelMapper.map(userStatusOptionDto, UserStatusOptionModel.class);
    }

    /**
     * Creates a single User Status option with a generated ID.
     * @param userStatusOptionDto - User Status option data
     * @return                    - Response with success message
     */
    @Operation(summary = "Create a single User Status option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody UserStatusOptionDto userStatusOptionDto) {
        UserStatusOptionModel userStatusOptionModel = convertToModel(userStatusOptionDto);
        userStatusOptionModel.setId(UserStatusOptionIdGenerator.generateId());
        userStatusOptionService.save(userStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple User Status options with generated IDs.
     * @param userStatusOptionDtoList - List of User Status option data
     * @return                        - Response with success message
     */
    @Operation(summary = "Create multiple User Status options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<UserStatusOptionDto> userStatusOptionDtoList) {
        List<UserStatusOptionModel> userStatusOptionModelList = new ArrayList<>();
        for (UserStatusOptionDto userStatusOptionDto : userStatusOptionDtoList) {
            UserStatusOptionModel model = convertToModel(userStatusOptionDto);
            model.setId(UserStatusOptionIdGenerator.generateId());
            userStatusOptionModelList.add(model);
        }
        userStatusOptionService.saveMany(userStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User status options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a User Status option by ID (excludes soft-deleted).
     * @param id - User Status option ID
     * @return   - Response with User Status option data
     */
    @Operation(summary = "Get a single User Status option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        UserStatusOptionModel model = userStatusOptionService.readOne(id);
        UserStatusOptionDto userStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(userStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted User Status options.
     * @return  - Response with list of User Status option data
     */
    @Operation(summary = "Get all available User Status options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<UserStatusOptionModel> userStatusOptionModelList = userStatusOptionService.readAll();
        List<UserStatusOptionDto> userStatusOptionDtoList = new ArrayList<>();
        for (UserStatusOptionModel userStatusOptionModel : userStatusOptionModelList) {
            userStatusOptionDtoList.add(convertToDto(userStatusOptionModel));
        }
        return new ResponseEntity<>(userStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all User Status options, including soft-deleted.
     * @return  - Response with list of all User Status option data
     */
    @Operation(summary = "Get all User Status options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<UserStatusOptionModel> modelList = userStatusOptionService.hardReadAll();
        List<UserStatusOptionDto> userStatusOptionDtoList = new ArrayList<>();
        for (UserStatusOptionModel model : modelList) {
            userStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(userStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple User Status options by ID (excludes soft-deleted).
     * @param idList - List of User Status option IDs
     * @return       - Response with list of User Status option data
     */
    @Operation(summary = "Get multiple User Status options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<UserStatusOptionModel> userStatusOptionModelList = userStatusOptionService.readMany(idList);
        List<UserStatusOptionDto> userStatusOptionDtoList = new ArrayList<>();
        for (UserStatusOptionModel model : userStatusOptionModelList) {
            userStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(userStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a User Status option by ID (excludes soft-deleted).
     * @param userStatusOptionDto - Updated User Status option data
     * @return                    - Response with updated User Status option data
     */
    @Operation(summary = "Update a single User Status option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody UserStatusOptionDto userStatusOptionDto) {
        String modelId = userStatusOptionDto.getId();
        UserStatusOptionModel savedModel = userStatusOptionService.readOne(modelId);
        savedModel.setName(userStatusOptionDto.getName());
        savedModel.setDescription(userStatusOptionDto.getDescription());
        userStatusOptionService.updateOne(savedModel);
        UserStatusOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple User Status options (excludes soft-deleted).
     * @param userStatusOptionDtoList - List of updated User Status option data
     * @return                        - Response with list of updated User Status option data
     */
    @Operation(summary = "Update multiple User Status options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<UserStatusOptionDto> userStatusOptionDtoList) {
        List<UserStatusOptionModel> inputModelList = new ArrayList<>();
        for (UserStatusOptionDto userStatusOptionDto : userStatusOptionDtoList) {
            inputModelList.add(convertToModel(userStatusOptionDto));
        }
        List<UserStatusOptionModel> updatedModelList = userStatusOptionService.updateMany(inputModelList);
        List<UserStatusOptionDto> updatedDtoList = new ArrayList<>();
        for (UserStatusOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a User Status option by ID, including soft-deleted.
     * @param userStatusOptionDto - Updated User Status option data
     * @return                    - Response with updated User Status option data
     */
    @Operation(summary = "Update a single User Status option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody UserStatusOptionDto userStatusOptionDto) {
        UserStatusOptionModel userStatusOptionModel = userStatusOptionService.hardUpdate(convertToModel(userStatusOptionDto));
        UserStatusOptionDto updatedDto = convertToDto(userStatusOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all User Status options, including soft-deleted.
     * @param userStatusOptionDtoList - List of updated User Status option data
     * @return                        - Response with list of updated User Status option data
     */
    @Operation(summary = "Update all User Status options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<UserStatusOptionDto> userStatusOptionDtoList) {
        List<UserStatusOptionModel> inputModelList = new ArrayList<>();
        for (UserStatusOptionDto userStatusOptionDto : userStatusOptionDtoList) {
            inputModelList.add(convertToModel(userStatusOptionDto));
        }
        List<UserStatusOptionModel> updatedModelList = userStatusOptionService.hardUpdateAll(inputModelList);
        List<UserStatusOptionDto> updatedDtoList = new ArrayList<>();
        for (UserStatusOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a User Status option by ID.
     * @param id - User Status option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single User Status option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        UserStatusOptionModel deletedModel = userStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User status option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a User Status option by ID.
     * @param id - User Status option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single User Status option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        userStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User status option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple User Status options by ID.
     * @param idList - List of User Status option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple User Status options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<UserStatusOptionModel> deletedModelList = userStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User status options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple User Status options by ID.
     * @param idList - List of User Status option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple User Status options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        userStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all User Status options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all User Status options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        userStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All User status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}