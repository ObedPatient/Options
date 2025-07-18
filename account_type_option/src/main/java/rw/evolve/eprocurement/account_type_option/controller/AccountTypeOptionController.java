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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.evolve.eprocurement.account_type_option.dto.AccountTypeOptionDto;
import rw.evolve.eprocurement.account_type_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.account_type_option.model.AccountTypeOptionModel;
import rw.evolve.eprocurement.account_type_option.service.AccountTypeOptionSevice;

import java.time.LocalDateTime;
import java.util.HashMap;
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
}
