/**
 * REST API controller for managing User Status options
 * Provides endpoints for creating, retrieving, deleting and updating User Status option data.
 */
package rw.evolve.eprocurement.user_status_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.user_status_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.user_status_option.dto.UserStatusOptionDto;
import rw.evolve.eprocurement.user_status_option.model.UserStatusOptionModel;
import rw.evolve.eprocurement.user_status_option.service.UserStatusOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user_status_option")
@Tag(name = "User Status Option Api")
public class UserStatusOptionController {

    @Autowired
    private UserStatusOptionService userStatusOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a UserStatusOptionModel to UserStatusOptionDto.
     * @param model The UserStatusOptionModel to convert.
     * @return The converted UserStatusOptionDto.
     */
    private UserStatusOptionDto convertToDto(UserStatusOptionModel model){
        return modelMapper.map(model, UserStatusOptionDto.class);
    }

    /**
     * Converts a UserStatusOptionDto to UserStatusOptionModel.
     * @param dto The UserStatusOptionDto to convert.
     * @return The converted UserStatusOptionModel.
     */
    private UserStatusOptionModel convertToModel(UserStatusOptionDto dto){
        return modelMapper.map(dto, UserStatusOptionModel.class);
    }

    /**
     * Creates a single User Status Option
     * @param userStatusOptionDto DTO containing User Status Option data
     * @return ResponseEntity containing a Map with the created UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one User Status Option Api endpoint")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createUserStatusOption(@Valid @RequestBody UserStatusOptionDto userStatusOptionDto){
        UserStatusOptionModel userStatusOptionModel = convertToModel(userStatusOptionDto);
        UserStatusOptionModel createdUserStatusOptionModel = userStatusOptionService.createUserStatusOption(userStatusOptionModel);
        UserStatusOptionDto createdUserStatusOptionDto = convertToDto(createdUserStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Option", createdUserStatusOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple User Status Options
     * @param userStatusOptionDtos List of User Status Option DTOs
     * @return ResponseEntity containing a Map with the created list of UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many User Status Option Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createUserStatusOptions(@Valid @RequestBody List<UserStatusOptionDto> userStatusOptionDtos){
        List<UserStatusOptionModel> userStatusOptionModels = new ArrayList<>();
        for (UserStatusOptionDto dto: userStatusOptionDtos){
            userStatusOptionModels.add(convertToModel(dto));
        }
        List<UserStatusOptionModel> createdModels = userStatusOptionService.createUserStatusOptions(userStatusOptionModels);
        List<UserStatusOptionDto> createdUserStatusDtos = new ArrayList<>();
        for (UserStatusOptionModel model: createdModels){
            createdUserStatusDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "User Status Options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", createdUserStatusDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a User Status Option by its ID, excluding soft-deleted options.
     * @param id The ID of the User Status Option to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One User Status Option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("UserStatusOptionId") Long id){
        UserStatusOptionModel model = userStatusOptionService.readOne(id);
        UserStatusOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted User Status Options.
     * @return ResponseEntity containing a Map with a list of UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all User Status Option Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<UserStatusOptionModel> userStatusOptionModels = userStatusOptionService.readAll();
        List<UserStatusOptionDto> userStatusOptionDtos = new ArrayList<>();
        for (UserStatusOptionModel userStatusOptionModel: userStatusOptionModels){
            userStatusOptionDtos.add(convertToDto(userStatusOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "User Status Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", userStatusOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all User Status Options, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all User Status Option Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<UserStatusOptionModel> models = userStatusOptionService.hardReadAll();
        List<UserStatusOptionDto> dtos = new ArrayList<>();
        for (UserStatusOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All User Status Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves multiple User Status Options by their IDs, excluding soft-deleted records.
     * @param ids List of User Status Option IDs
     * @return ResponseEntity containing a Map with a list of UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple User Status Options with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<UserStatusOptionModel> userStatusOptionModels = userStatusOptionService.readMany(ids);
        List<UserStatusOptionDto> userStatusOptionDtos = new ArrayList<>();
        for (UserStatusOptionModel model: userStatusOptionModels){
            userStatusOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", userStatusOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a User Status Option by its ID, excluding soft-deleted records.
     * @param id The ID of the User Status Option to update
     * @param userStatusOptionDto The updated User Status Option data
     * @return ResponseEntity containing a Map with the updated UserStatusOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One User Status Option Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody UserStatusOptionDto userStatusOptionDto){
        UserStatusOptionModel userStatusOptionModel = userStatusOptionService.updateOne(id, convertToModel(userStatusOptionDto));
        UserStatusOptionDto dto = convertToDto(userStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("User Status Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple User Status Options based on the provided list of User Status Option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param userStatusOptionDtos List of UserStatusOptionDto objects containing updated data
     * @return ResponseEntity containing a Map with the list of updated UserStatusOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Update multiple User Status Options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<UserStatusOptionDto> userStatusOptionDtos){
        List<UserStatusOptionModel> inputModels = new ArrayList<>();
        for (UserStatusOptionDto dto: userStatusOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<UserStatusOptionModel> updatedModels = userStatusOptionService.updateMany(inputModels);
        List<UserStatusOptionDto> dtos = new ArrayList<>();
        for (UserStatusOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "User Status Options Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a User Status Option by its ID, including soft-deleted records.
     *
     * @param id The ID of the User Status Option to update.
     * @param userStatusOptionDto The updated User Status Option data.
     * @return ResponseEntity containing a Map with the updated UserStatusOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Hard update User Status Option by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody UserStatusOptionDto userStatusOptionDto){
        UserStatusOptionModel userStatusOptionModel = userStatusOptionService.hardUpdateOne(id, convertToModel(userStatusOptionDto));
        UserStatusOptionDto dto = convertToDto(userStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Option Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all User Status Options, including soft-deleted records, based on their IDs.
     *
     * @param userStatusOptionDtos The list of updated User Status Option data.
     * @return ResponseEntity containing a Map with the list of updated UserStatusOptionDtos and ResponseMessageDto
     */
    @Operation(summary = "Hard update all User Status Options")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<UserStatusOptionDto> userStatusOptionDtos){
        List<UserStatusOptionModel> inputModels = new ArrayList<>();
        for (UserStatusOptionDto dto: userStatusOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<UserStatusOptionModel> updatedModels = userStatusOptionService.hardUpdateAll(inputModels);
        List<UserStatusOptionDto> dtos = new ArrayList<>();
        for (UserStatusOptionModel userStatusOptionModel: updatedModels){
            dtos.add(convertToDto(userStatusOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Options Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes a single User Status Option by ID
     * @param id ID of the User Status Option to softly delete
     * @return ResponseEntity containing a Map with the soft deleted UserStatusOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete a single User Status Option")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteUserStatusOption(@RequestParam Long id){
        UserStatusOptionModel deletedUserStatusOptionModel = userStatusOptionService.softDeleteUserStatusOption(id);
        UserStatusOptionDto deletedUserStatusOptionDto = convertToDto(deletedUserStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Option Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("User Status Option", deletedUserStatusOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single User Status Option by ID
     * @param id ID of the User Status Option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete a single User Status Option Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteUserStatusOption(@RequestParam Long id){
        userStatusOptionService.hardDeleteUserStatusOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Option Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple User Status Options by IDs
     * @param ids List of User Status Option IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted UserStatusOptionDto and ResponseMessageDto
     */
    @Operation(summary = "Soft delete multiple User Status Options")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteUserStatusOptions(@RequestBody List<Long> ids){
        List<UserStatusOptionModel> deletedUserStatusOptionModels = userStatusOptionService.softDeleteUserStatusOptions(ids);
        List<UserStatusOptionDto> deletedUserStatusOptionDtos = new ArrayList<>();
        for (UserStatusOptionModel model: deletedUserStatusOptionModels){
            deletedUserStatusOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Options Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("User Status Options", deletedUserStatusOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes multiple User Status Options by IDs
     * @param ids List of User Status Option IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageDto
     */
    @Operation(summary = "Hard delete multiple User Status Options")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteUserStatusOptions(@RequestBody List<Long> ids){
        userStatusOptionService.hardDeleteUserStatusOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "User Status Options Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
}