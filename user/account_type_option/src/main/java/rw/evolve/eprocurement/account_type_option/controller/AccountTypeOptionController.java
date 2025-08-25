package rw.evolve.eprocurement.account_type_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.account_type_option.dto.AccountTypeOptionDto;
import rw.evolve.eprocurement.account_type_option.model.AccountTypeOptionModel;
import rw.evolve.eprocurement.account_type_option.service.AccountTypeOptionService;
import rw.evolve.eprocurement.account_type_option.utils.AccountTypeOptionIdGenerator;
import rw.evolve.eprocurement.account_type_option.dto.ResponseMessageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API controller for managing Account type options.
 * Handles CRUD operations for Account type option data with soft and hard delete capabilities.
 */
@RestController
@RequestMapping("api/account_type_option")
@Tag(name = "Account Type Option API")
public class AccountTypeOptionController {

    private final AccountTypeOptionService accountTypeOptionService;

    private final ModelMapper modelMapper;

    public AccountTypeOptionController(
            AccountTypeOptionService accountTypeOptionService,
            ModelMapper modelMapper
    ){
        this.accountTypeOptionService = accountTypeOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts AccountTypeOptionModel to AccountTypeOptionDto.
     * @param model - AccountTypeOptionModel to convert
     * @return      - Converted AccountTypeOptionDto
     */
    private AccountTypeOptionDto convertToDto(AccountTypeOptionModel model) {
        return modelMapper.map(model, AccountTypeOptionDto.class);
    }

    /**
     * Converts AccountTypeOptionDto to AccountTypeOptionModel.
     * @param accountTypeOptionDto - AccountTypeOptionDto to convert
     * @return                     - Converted AccountTypeOptionModel
     */
    private AccountTypeOptionModel convertToModel(AccountTypeOptionDto accountTypeOptionDto) {
        return modelMapper.map(accountTypeOptionDto, AccountTypeOptionModel.class);
    }

    /**
     * Creates a single Account type option with a generated ID.
     * @param accountTypeOptionDto - Account type option data
     * @return                     - Response with success message
     */
    @Operation(summary = "Create a single Account type option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody AccountTypeOptionDto accountTypeOptionDto) {
        AccountTypeOptionModel accountTypeOptionModel = convertToModel(accountTypeOptionDto);
        accountTypeOptionModel.setId(AccountTypeOptionIdGenerator.generateId());
        accountTypeOptionService.save(accountTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Account type options with generated IDs.
     * @param accountTypeOptionDtoList - List of Account type option data
     * @return                         - Response with success message
     */
    @Operation(summary = "Create multiple Account type options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<AccountTypeOptionDto> accountTypeOptionDtoList) {
        List<AccountTypeOptionModel> accountTypeOptionModelList = new ArrayList<>();
        for (AccountTypeOptionDto accountTypeOptionDto : accountTypeOptionDtoList) {
            AccountTypeOptionModel model = convertToModel(accountTypeOptionDto);
            model.setId(AccountTypeOptionIdGenerator.generateId());
            accountTypeOptionModelList.add(model);
        }
        accountTypeOptionService.saveMany(accountTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves an Account type option by ID (excludes soft-deleted).
     * @param id - Account type option ID
     * @return   - Response with Account type option data
     */
    @Operation(summary = "Get a single Account type option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        AccountTypeOptionModel model = accountTypeOptionService.readOne(id);
        AccountTypeOptionDto accountTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(accountTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Account type options.
     * @return  - Response with list of Account type option data
     */
    @Operation(summary = "Get all available Account type options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<AccountTypeOptionModel> accountTypeOptionModelList = accountTypeOptionService.readAll();
        List<AccountTypeOptionDto> accountTypeOptionDtoList = new ArrayList<>();
        for (AccountTypeOptionModel accountTypeOptionModel : accountTypeOptionModelList) {
            accountTypeOptionDtoList.add(convertToDto(accountTypeOptionModel));
        }
        return new ResponseEntity<>(accountTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Account type options, including soft-deleted.
     * @return  - Response with list of all Account type option data
     */
    @Operation(summary = "Get all Account type options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<AccountTypeOptionModel> modelList = accountTypeOptionService.hardReadAll();
        List<AccountTypeOptionDto> accountTypeOptionDtoList = new ArrayList<>();
        for (AccountTypeOptionModel model : modelList) {
            accountTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(accountTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Account type options by ID (excludes soft-deleted).
     * @param idList - List of Account type option IDs
     * @return       - Response with list of Account type option data
     */
    @Operation(summary = "Get multiple Account type options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<AccountTypeOptionModel> accountTypeOptionModelList = accountTypeOptionService.readMany(idList);
        List<AccountTypeOptionDto> accountTypeOptionDtoList = new ArrayList<>();
        for (AccountTypeOptionModel model : accountTypeOptionModelList) {
            accountTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(accountTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Account type option by ID (excludes soft-deleted).
     * @param accountTypeOptionDto - Updated Account type option data
     * @return                     - Response with updated Account type option data
     */
    @Operation(summary = "Update a single Account type option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody AccountTypeOptionDto accountTypeOptionDto) {
        String modelId = accountTypeOptionDto.getId();
        AccountTypeOptionModel savedModel = accountTypeOptionService.readOne(modelId);
        savedModel.setName(accountTypeOptionDto.getName());
        savedModel.setDescription(accountTypeOptionDto.getDescription());
        accountTypeOptionService.updateOne(savedModel);
        AccountTypeOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Account type options (excludes soft-deleted).
     * @param accountTypeOptionDtoList - List of updated Account type option data
     * @return                         - Response with list of updated Account type option data
     */
    @Operation(summary = "Update multiple Account type options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<AccountTypeOptionDto> accountTypeOptionDtoList) {
        List<AccountTypeOptionModel> inputModelList = new ArrayList<>();
        for (AccountTypeOptionDto accountTypeOptionDto : accountTypeOptionDtoList) {
            inputModelList.add(convertToModel(accountTypeOptionDto));
        }
        List<AccountTypeOptionModel> updatedModelList = accountTypeOptionService.updateMany(inputModelList);
        List<AccountTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (AccountTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates an Account type option by ID, including soft-deleted.
     * @param accountTypeOptionDto - Updated Account type option data
     * @return                     - Response with updated Account type option data
     */
    @Operation(summary = "Update a single Account type option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody AccountTypeOptionDto accountTypeOptionDto) {
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionService.hardUpdate(convertToModel(accountTypeOptionDto));
        AccountTypeOptionDto updatedDto = convertToDto(accountTypeOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Account type options, including soft-deleted.
     * @param accountTypeOptionDtoList - List of updated Account type option data
     * @return                         - Response with list of updated Account type option data
     */
    @Operation(summary = "Update all Account type options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<AccountTypeOptionDto> accountTypeOptionDtoList) {
        List<AccountTypeOptionModel> inputModelList = new ArrayList<>();
        for (AccountTypeOptionDto accountTypeOptionDto : accountTypeOptionDtoList) {
            inputModelList.add(convertToModel(accountTypeOptionDto));
        }
        List<AccountTypeOptionModel> updatedModelList = accountTypeOptionService.hardUpdateAll(inputModelList);
        List<AccountTypeOptionDto> updatedDtoList = new ArrayList<>();
        for (AccountTypeOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes an Account type option by ID.
     * @param id - Account type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Account type option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        AccountTypeOptionModel deletedModel = accountTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes an Account type option by ID.
     * @param id - Account type option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Account type option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        accountTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Account type options by ID.
     * @param idList - List of Account type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Account type options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<AccountTypeOptionModel> deletedModelList = accountTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Account type options by ID.
     * @param idList - List of Account type option IDs
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Account type options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        accountTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Account type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Account type options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Account type options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        accountTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Account type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}