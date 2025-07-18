/**
 * REST API controller for managing Account type options
 * Provides endpoints for creating, retrieving, deleting and updating Account type option data.
 */
package rw.evolve.eprocurement.account_type_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.account_type_option.dto.AccountTypeOptionDto;
import rw.evolve.eprocurement.account_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.account_type_option.model.AccountTypeOptionModel;
import rw.evolve.eprocurement.account_type_option.service.AccountTypeOptionSevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/account_type_option")
@Tag(name = "Account Type Option Api")
public class AccountTypeOptionController {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AccountTypeOptionSevice accountTypeOptionSevice;

    /**
     * Converts a AccountTypeOptionModel to AccountTypeOptionDto.
     * @param model The AccountTypeOptionModel to convert.
     * @return The converted AccountTypeOptionDto.
     */
    private AccountTypeOptionDto convertToDto(AccountTypeOptionModel model){
        return modelMapper.map(model, AccountTypeOptionDto.class);
    }

    /**
     * Converts a AccountTypeOptionDto to AccountTypeOptionModel.
     * @param dto The AccountTypeOptionDto to convert.
     * @return The converted AccountTypeOptionModel.
     */
    private AccountTypeOptionModel convertToModel(AccountTypeOptionDto dto){
        return modelMapper.map(dto, AccountTypeOptionModel.class);
    }

    /**
     * create a single Account type
     * @param @AccountTypeOptionDto DTO containing Account Type option data
     * @return ResponseEntity containing a Map with the created AccountTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create one Account type option")
    @PostMapping("/create/one")
    public ResponseEntity<Map<String, Object>> createAccountType(@Valid @RequestBody AccountTypeOptionDto accountTypeOptionDto){
        AccountTypeOptionModel accountTypeOptionModel = convertToModel(accountTypeOptionDto);
        AccountTypeOptionModel createdAccountTypeModel = accountTypeOptionSevice.createAccountType(accountTypeOptionModel);
        AccountTypeOptionDto createdAccountTypeDto = convertToDto(createdAccountTypeModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account Type option created successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account type options", createdAccountTypeDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates multiple Account type options
     * @param accountTypeOptionDtos List of account type option DTOs
     * @return ResponseEntity containing a Map with the created list of AccountTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Create Many Account types Api endpoint")
    @PostMapping("/create/many")
    public ResponseEntity<Map<String, Object>> createAccountTypes(@Valid @RequestBody List<AccountTypeOptionDto> accountTypeOptionDtos ){
        List<AccountTypeOptionModel> accountTypeOptionModels = new ArrayList<>();
        for (AccountTypeOptionDto dto: accountTypeOptionDtos){
            accountTypeOptionModels.add(convertToModel(dto));
        }
        List<AccountTypeOptionModel> createdModels = accountTypeOptionSevice.createAccountTypes(accountTypeOptionModels);
        List<AccountTypeOptionDto> createdAccountTypesDtos = new ArrayList<>();
        for (AccountTypeOptionModel model: createdModels){
            createdAccountTypesDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Account type options Created Successfully",
                "OK",
                201,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account Type Options", createdAccountTypesDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves a Account type option by its ID, excluding soft-deleted account types.
     * @param id The ID of the Account type to retrieve, provided as a request parameter.
     * @return ResponseEntity containing a Map with the AccountTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Get One Account type option API")
    @GetMapping("/read/one/{id}")
    public ResponseEntity<Map<String, Object>> readOne(@RequestParam("AccountTypeOptionId") Long id){
        AccountTypeOptionModel model = accountTypeOptionSevice.readOne(id);
        AccountTypeOptionDto dto = convertToDto(model);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account Type Option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all non-deleted Account types.
     * @return ResponseEntity containing a Map with a list of AccountTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Read all Account Types Api endpoint")
    @GetMapping("/read/all")
    public ResponseEntity<Map<String, Object>> readAll(){
        List<AccountTypeOptionModel> accountTypeOptionModels = accountTypeOptionSevice.readAll();
        List<AccountTypeOptionDto> accountTypeOptionDtos = new ArrayList<>();
        for (AccountTypeOptionModel accountTypeOptionModel: accountTypeOptionModels){
            accountTypeOptionDtos.add(convertToDto(accountTypeOptionModel));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Account type options Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account Type Options", accountTypeOptionDtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all Account types, including soft-deleted ones.
     * @return ResponseEntity containing a Map with a list of AccountTypeDto and a ResponseMessageDto
     */
    @Operation(summary = "Hard read all Account types Api endpoint")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Map<String, Object>> hardReadAll(){
        List<AccountTypeOptionModel> models = accountTypeOptionSevice.hardReadAll();
        List<AccountTypeOptionDto> dtos = new ArrayList<>();
        for (AccountTypeOptionModel model: models){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "All Account type option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account types  options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves multiple Account type options  by their IDs, excluding soft-deleted data.
     * @param ids List of Account type year IDs
     * @return ResponseEntity containing a Map with a list of AccountTypeOptionDto and a ResponseMessageDto
     */
    @Operation(summary = "Retrieve multiple Account types year with their Ids Api")
    @PostMapping("read/many")
    public ResponseEntity<Map<String, Object>> readMany(@Valid @RequestBody List<Long> ids){
        List<AccountTypeOptionModel> accountTypeOptionModels = accountTypeOptionSevice.readMany(ids);
        List<AccountTypeOptionDto> accountTypeOptionDtos = new ArrayList<>();
        for (AccountTypeOptionModel model: accountTypeOptionModels){
            accountTypeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type option Retrieved Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account type options", accountTypeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a Account type by its ID, excluding soft-deleted records.
     * @param id The ID of the Account type year to update
     * @param accountTypeOptionDto The updated Account type data
     * @return ResponseEntity containing a Map with the updated AccountTypeDto and a ResponseMessageDto
     */
    @Operation(summary = "Update One Account type option year Api")
    @PutMapping("/update/one/{id}")
    public ResponseEntity<Map<String, Object>> updateOne(@Valid @RequestParam Long id,
                                                         @Valid @RequestBody AccountTypeOptionDto accountTypeOptionDto){
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionSevice.updateOne(id, convertToModel((accountTypeOptionDto)));
        AccountTypeOptionDto dto = convertToDto(accountTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type option Year Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Account Type option", dto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates multiple Account type option  based on the provided list of Account type option DTOs.
     * Excludes soft-deleted records from updates.
     *
     * @param accountTypeOptionDtos List of AccountTypeDto objects containing updated Account type data
     * @return ResponseEntity containing a Map with the list of updated AccountTypeDtos and ResponseMessageEntity
     */
    @Operation(summary = "Upadate multiple Account type options Api endpoint")
    @PutMapping("/update/many")
    public ResponseEntity<Map<String, Object>> updateMany(@Valid @RequestBody List<AccountTypeOptionDto> accountTypeOptionDtos){
        List<AccountTypeOptionModel> inputModels = new ArrayList<>();
        for (AccountTypeOptionDto dto: accountTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<AccountTypeOptionModel> updatedModels = accountTypeOptionSevice.updateMany((inputModels));
        List<AccountTypeOptionDto> dtos = new ArrayList<>();
        for (AccountTypeOptionModel model: updatedModels){
            dtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessage = new ResponseMessageDto(
                "Account types Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Account Type options", dtos);
        response.put("responseMessage", responseMessage);
        return ResponseEntity.ok(response);
    }
    /**
     * Updates a Account type by its ID, including soft-deleted records.
     *
     * @param id The ID of the Account type to update.
     * @param accountTypeOptionDto The updated Account type data.
     * @return ResponseEntity containing a Map with the updated AccountTypeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Hard update procurement type by Id Api endpoint")
    @PutMapping("/update/hard/one/{id}")
    public ResponseEntity<Map<String, Object>> hardUpdate(@RequestParam Long id, @Valid @RequestBody AccountTypeOptionDto accountTypeOptionDto){
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionSevice.hardUpdateOne(id, convertToModel(accountTypeOptionDto));
        AccountTypeOptionDto dto = convertToDto(accountTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type Updated Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account Type options", dto);
        response.put("Response Message", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates all Account types, including soft-deleted records, based on their IDs.
     *
     * @param accountTypeOptionDtos The list of updated Account type option data.
     * @return ResponseEntity containing a Map with the list of updated AccountTypeOptionDtos and ResponseMessageEntity
     */
    @Operation(summary = "Hard update all Account types")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Map<String, Object>> hardUpdateAll(@Valid @RequestBody List<AccountTypeOptionDto> accountTypeOptionDtos){
        List<AccountTypeOptionModel> inputModels = new ArrayList<>();
        for (AccountTypeOptionDto dto: accountTypeOptionDtos){
            inputModels.add(convertToModel(dto));
        }
        List<AccountTypeOptionModel> updatedModels = accountTypeOptionSevice.hardUpdateAll(inputModels);
        List<AccountTypeOptionDto> dtos = new ArrayList<>();
        for (AccountTypeOptionModel accountTypeOptionModel: updatedModels){
            dtos.add(convertToDto(accountTypeOptionModel));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account types Hard updated successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account type Options", dtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Soft deletes a single Account type by ID
     * @param id ID of the Account type to softly delete
     * @return ResponseEntity containing a Map with the soft deleted AccountTypeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete a single procurement type")
    @PutMapping("/soft/delete/one/{id}")
    public ResponseEntity<Map<String, Object>> softDeleteAccountTypeOption(@RequestParam Long id){
        AccountTypeOptionModel deletedAccountTypeOptionModel = accountTypeOptionSevice.softDeleteAccountTypeOption(id);
        AccountTypeOptionDto deletedAccountTypeOptionDto = convertToDto(deletedAccountTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type Soft Deleted successfully",
                "OK",
                200,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Account type", deletedAccountTypeOptionDto);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Hard deletes a single Account type by ID
     * @param id ID of the Account type option to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete a single Account type Api endpoint")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Map<String, Object>> hardDeleteAccountTypeOption(@RequestParam Long id){
        accountTypeOptionSevice.hardDeleteAccountTypeOption(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type Hard Deleted Successfully",
                "OK",
                204,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Soft deletes multiple Account types by IDs
     * @param ids List of Account type IDs to softly delete
     * @return ResponseEntity containing a Map with the list of soft deleted AccountTypeOptionDto and ResponseMessageEntity
     */
    @Operation(summary = "Soft delete multiple Account type API")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Map<String, Object>> softDeleteAccountTypeOptions(@RequestBody List<Long> ids){
        List<AccountTypeOptionModel> deletedAccountTypeOptionModels = accountTypeOptionSevice.softDeleteAccountTypeOptions(ids);
        List<AccountTypeOptionDto> deletedAccountTypeOptionDtos = new ArrayList<>();
        for (AccountTypeOptionModel model: deletedAccountTypeOptionModels){
            deletedAccountTypeOptionDtos.add(convertToDto(model));
        }
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type Soft Deleted Successfully",
                "OK",
                200,
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("Account type options", deletedAccountTypeOptionDtos);
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }
    /**
     * Hard deletes multiple procurement types by IDs
     * @param ids List of Account type IDs to hard delete
     * @return ResponseEntity containing a Map with ResponseMessageEntity
     */
    @Operation(summary = "Hard delete multiple procurement types")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Map<String, Object>> hardDeleteAccountTypeOptions(@RequestBody List<Long> ids){
        accountTypeOptionSevice.hardDeleteAccountTypeOptions(ids);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type hard deleted successfully",
                "OK",
                204,
                LocalDateTime.now()
        );
        Map<String, Object> response = new HashMap<>();
        response.put("responseMessage", responseMessageDto);
        return ResponseEntity.ok(response);
    }

}
