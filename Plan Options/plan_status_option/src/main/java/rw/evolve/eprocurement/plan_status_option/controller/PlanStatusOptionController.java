/**
 * REST API controller for managing Plan status options.
 * Handles CRUD operations for Plan Status Option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.plan_status_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.plan_status_option.dto.PlanStatusOptionDto;
import rw.evolve.eprocurement.plan_status_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.plan_status_option.model.PlanStatusOptionModel;
import rw.evolve.eprocurement.plan_status_option.service.PlanStatusOptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/plan_status_option")
@Tag(name = "Plan Status Option API")
public class PlanStatusOptionController {

    private final PlanStatusOptionService planStatusOptionService;

    private final ModelMapper modelMapper;

    public PlanStatusOptionController(
            PlanStatusOptionService planStatusOptionService,
            ModelMapper modelMapper
    ){
        this.planStatusOptionService = planStatusOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts PlanStatusOptionModel to PlanStatusOptionDto.
     * @param model - PlanStatusOptionModel to convert
     * @return      - Converted PlanStatusOptionDto
     */
    private PlanStatusOptionDto convertToDto(PlanStatusOptionModel model) {
        return modelMapper.map(model, PlanStatusOptionDto.class);
    }

    /**
     * Converts PlanStatusOptionDto to PlanStatusOptionModel.
     * @param planStatusOptionDto - PlanStatusOptionDto to convert
     * @return                    - Converted PlanStatusOptionModel
     */
    private PlanStatusOptionModel convertToModel(PlanStatusOptionDto planStatusOptionDto) {
        return modelMapper.map(planStatusOptionDto, PlanStatusOptionModel.class);
    }

    /**
     * Creates a single Plan Status Option.
     * @param planStatusOptionDto - Plan Status Option data
     * @return                    - Response with success message
     */
    @Operation(summary = "Create a single Plan Status Option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody PlanStatusOptionDto planStatusOptionDto) {
        PlanStatusOptionModel planStatusOptionModel = convertToModel(planStatusOptionDto);
        planStatusOptionService.save(planStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple Plan Status Options.
     * @param planStatusOptionDtoList - List of Plan Status Option data
     * @return                        - Response with success message
     */
    @Operation(summary = "Create multiple Plan Status Options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<PlanStatusOptionDto> planStatusOptionDtoList) {
        List<PlanStatusOptionModel> planStatusOptionModelList = new ArrayList<>();
        for (PlanStatusOptionDto planStatusOptionDto : planStatusOptionDtoList) {
            planStatusOptionModelList.add(convertToModel(planStatusOptionDto));
        }
        planStatusOptionService.saveMany(planStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options created successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a Plan Status Option by ID (excludes soft-deleted).
     * @param id - Plan Status Option ID
     * @return   - Response with Plan Status Option data
     */
    @Operation(summary = "Get a single Plan Status Option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        PlanStatusOptionModel model = planStatusOptionService.readOne(id);
        PlanStatusOptionDto planStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(planStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted Plan Status Options.
     * @return - Response with list of Plan Status Option data
     */
    @Operation(summary = "Get all available Plan Status Options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<PlanStatusOptionModel> planStatusOptionModelList = planStatusOptionService.readAll();
        List<PlanStatusOptionDto> planStatusOptionDtoList = new ArrayList<>();
        for (PlanStatusOptionModel planStatusOptionModel : planStatusOptionModelList) {
            planStatusOptionDtoList.add(convertToDto(planStatusOptionModel));
        }
        return new ResponseEntity<>(planStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all Plan Status Options, including soft-deleted.
     * @return - Response with list of all Plan Status Option data
     */
    @Operation(summary = "Get all Plan Status Options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<PlanStatusOptionModel> modelList = planStatusOptionService.hardReadAll();
        List<PlanStatusOptionDto> planStatusOptionDtoList = new ArrayList<>();
        for (PlanStatusOptionModel model : modelList) {
            planStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(planStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple Plan Status Options by ID (excludes soft-deleted).
     * @param idList - List of Plan Status Option ID
     * @return       - Response with list of Plan Status Option data
     */
    @Operation(summary = "Get multiple Plan Status Options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<PlanStatusOptionModel> planStatusOptionModelList = planStatusOptionService.readMany(idList);
        List<PlanStatusOptionDto> planStatusOptionDtoList = new ArrayList<>();
        for (PlanStatusOptionModel model : planStatusOptionModelList) {
            planStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(planStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Plan Status Option by ID (excludes soft-deleted).
     * @param planStatusOptionDto - Updated Plan Status Option data
     * @return                    - Response with updated Plan Status Option data
     */
    @Operation(summary = "Update a single Plan Status Option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody PlanStatusOptionDto planStatusOptionDto) {
        String modelId = planStatusOptionDto.getId();
        PlanStatusOptionModel savedModel = planStatusOptionService.readOne(modelId);
        savedModel.setName(planStatusOptionDto.getName());
        savedModel.setDescription(planStatusOptionDto.getDescription());
        planStatusOptionService.updateOne(savedModel);
        PlanStatusOptionDto updatedDto = convertToDto(savedModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates multiple Plan Status Options (excludes soft-deleted).
     * @param planStatusOptionDtoList - List of updated Plan Status Option data
     * @return                        - Response with list of updated Plan Status Option data
     */
    @Operation(summary = "Update multiple Plan Status Options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<PlanStatusOptionDto> planStatusOptionDtoList) {
        List<PlanStatusOptionModel> inputModelList = new ArrayList<>();
        for (PlanStatusOptionDto planStatusOptionDto : planStatusOptionDtoList) {
            inputModelList.add(convertToModel(planStatusOptionDto));
        }
        List<PlanStatusOptionModel> updatedModelList = planStatusOptionService.updateMany(inputModelList);
        List<PlanStatusOptionDto> updatedDtoList = new ArrayList<>();
        for (PlanStatusOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Updates a Plan Status Option by ID, including soft-deleted.
     * @param planStatusOptionDto - Updated Plan Status Option data
     * @return                    - Response with updated Plan Status Option data
     */
    @Operation(summary = "Update a single Plan Status Option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody PlanStatusOptionDto planStatusOptionDto) {
        PlanStatusOptionModel planStatusOptionModel = planStatusOptionService.hardUpdate(convertToModel(planStatusOptionDto));
        PlanStatusOptionDto updatedDto = convertToDto(planStatusOptionModel);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * Updates all Plan Status Options, including soft-deleted.
     * @param planStatusOptionDtoList - List of updated Plan Status Option data
     * @return                        - Response with list of updated Plan Status Option data
     */
    @Operation(summary = "Update all Plan Status Options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<PlanStatusOptionDto> planStatusOptionDtoList) {
        List<PlanStatusOptionModel> inputModelList = new ArrayList<>();
        for (PlanStatusOptionDto planStatusOptionDto : planStatusOptionDtoList) {
            inputModelList.add(convertToModel(planStatusOptionDto));
        }
        List<PlanStatusOptionModel> updatedModelList = planStatusOptionService.hardUpdateAll(inputModelList);
        List<PlanStatusOptionDto> updatedDtoList = new ArrayList<>();
        for (PlanStatusOptionModel model : updatedModelList) {
            updatedDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a Plan Status Option by ID.
     * @param id - Plan Status Option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single Plan Status Option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id) {
        PlanStatusOptionModel deletedModel = planStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a Plan Status Option by ID.
     * @param id - Plan Status Option ID
     * @return   - Response with success message
     */
    @Operation(summary = "Hard delete a single Plan Status Option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        planStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Option hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple Plan Status Options by ID.
     * @param idList - List of Plan Status Option ID
     * @return       - Response with success message
     */
    @Operation(summary = "Soft delete multiple Plan Status Options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<PlanStatusOptionModel> deletedModelList = planStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options soft deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple Plan Status Options by ID.
     * @param idList - List of Plan Status Option ID
     * @return       - Response with success message
     */
    @Operation(summary = "Hard delete multiple Plan Status Options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("id_list") List<String> idList) {
        planStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Plan Status Options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all Plan Status Options, including soft-deleted.
     * @return - Response with success message
     */
    @Operation(summary = "Hard delete all Plan Status Options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        planStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Plan Status Options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}