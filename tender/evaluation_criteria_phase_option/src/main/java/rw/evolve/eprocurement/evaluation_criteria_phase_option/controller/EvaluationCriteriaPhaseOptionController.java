/**
 * REST API controller for managing EvaluationCriteriaPhaseOption options.
 * Handles CRUD operations for EvaluationCriteriaPhaseOption option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.evaluation_criteria_phase_option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.dto.ResponseMessageDto;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.dto.EvaluationCriteriaPhaseOptionDto;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.model.EvaluationCriteriaPhaseOptionModel;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.service.EvaluationCriteriaPhaseOptionService;
import rw.evolve.eprocurement.evaluation_criteria_phase_option.utils.EvaluationCriteriaPhaseOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/evaluation_criteria_phase_option")
@Tag(name = "Evaluation Criteria Phase Option API")
public class EvaluationCriteriaPhaseOptionController {


    private final EvaluationCriteriaPhaseOptionService evaluationCriteriaPhaseOptionService;

    private ModelMapper modelMapper = new ModelMapper();

    public EvaluationCriteriaPhaseOptionController(
            EvaluationCriteriaPhaseOptionService evaluationCriteriaPhaseOptionService,
            ModelMapper modelMapper
    ){
        this.evaluationCriteriaPhaseOptionService = evaluationCriteriaPhaseOptionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Converts EvaluationCriteriaPhaseOptionModel to EvaluationCriteriaPhaseOptionDto.
     * @param model - EvaluationCriteriaPhaseOptionModel to convert
     * @return      - Converted EvaluationCriteriaPhaseOptionDto
     */
    private EvaluationCriteriaPhaseOptionDto convertToDto(EvaluationCriteriaPhaseOptionModel model) {
        return modelMapper.map(model, EvaluationCriteriaPhaseOptionDto.class);
    }

    /**
     * Converts EvaluationCriteriaPhaseOptionDto to EvaluationCriteriaPhaseOptionModel.
     * @param evaluationCriteriaPhaseOptionDto - EvaluationCriteriaPhaseOptionDto to convert
     * @return                                - Converted EvaluationCriteriaPhaseOptionModel
     */
    private EvaluationCriteriaPhaseOptionModel convertToModel(EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto) {
        return modelMapper.map(evaluationCriteriaPhaseOptionDto, EvaluationCriteriaPhaseOptionModel.class);
    }

    /**
     * Creates a single EvaluationCriteriaPhaseOption option with a generated ID.
     * @param evaluationCriteriaPhaseOptionDto - EvaluationCriteriaPhaseOption option data
     * @return                                - Response with success message
     */
    @Operation(summary = "Create a single EvaluationCriteriaPhaseOption option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto) {
        EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel = convertToModel(evaluationCriteriaPhaseOptionDto);
        evaluationCriteriaPhaseOptionModel.setId(EvaluationCriteriaPhaseOptionIdGenerator.generateId());
        evaluationCriteriaPhaseOptionService.save(evaluationCriteriaPhaseOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Evaluation criteria phase option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple EvaluationCriteriaPhaseOption options with generated IDList.
     * @param evaluationCriteriaPhaseOptionDtoList - List of EvaluationCriteriaPhaseOption option data
     * @return                                    - Response with success message
     */
    @Operation(summary = "Create multiple EvaluationCriteriaPhaseOption options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoList) {
        List<EvaluationCriteriaPhaseOptionModel> evaluationCriteriaPhaseOptionModelList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto : evaluationCriteriaPhaseOptionDtoList) {
            EvaluationCriteriaPhaseOptionModel model = convertToModel(evaluationCriteriaPhaseOptionDto);
            model.setId(EvaluationCriteriaPhaseOptionIdGenerator.generateId());
            evaluationCriteriaPhaseOptionModelList.add(model);
        }
        evaluationCriteriaPhaseOptionService.saveMany(evaluationCriteriaPhaseOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Evaluation criteria phase options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a EvaluationCriteriaPhaseOption option by ID (excludes soft-deleted).
     * @param id - EvaluationCriteriaPhaseOption option ID
     * @return   - Response with EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Get a single EvaluationCriteriaPhaseOption option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        EvaluationCriteriaPhaseOptionModel model = evaluationCriteriaPhaseOptionService.readOne(id);
        EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto = convertToDto(model);
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted EvaluationCriteriaPhaseOption options.
     * @return  - Response with list of EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Get all available EvaluationCriteriaPhaseOption options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<EvaluationCriteriaPhaseOptionModel> evaluationCriteriaPhaseOptionModelList = evaluationCriteriaPhaseOptionService.readAll();
        List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel : evaluationCriteriaPhaseOptionModelList) {
            evaluationCriteriaPhaseOptionDtoList.add(convertToDto(evaluationCriteriaPhaseOptionModel));
        }
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all EvaluationCriteriaPhaseOption options, including soft-deleted.
     * @return        - Response with list of all EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Get all EvaluationCriteriaPhaseOption options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<EvaluationCriteriaPhaseOptionModel> modelList = evaluationCriteriaPhaseOptionService.hardReadAll();
        List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionModel model : modelList) {
            evaluationCriteriaPhaseOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple EvaluationCriteriaPhaseOption options by ID (excludes soft-deleted).
     * @param idList - List of EvaluationCriteriaPhaseOption option ID
     * @return       - Response with list of EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Get multiple EvaluationCriteriaPhaseOption options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<EvaluationCriteriaPhaseOptionModel> evaluationCriteriaPhaseOptionModelList = evaluationCriteriaPhaseOptionService.readMany(idList);
        List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionModel model : evaluationCriteriaPhaseOptionModelList) {
            evaluationCriteriaPhaseOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a EvaluationCriteriaPhaseOption option by ID (excludes soft-deleted).
     *
     * @param evaluationCriteriaPhaseOptionDto - Updated EvaluationCriteriaPhaseOption option data
     * @return                                - Response with updated EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Update a single EvaluationCriteriaPhaseOption option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto){
        String modelId = evaluationCriteriaPhaseOptionDto.getId();
        EvaluationCriteriaPhaseOptionModel savedModel = evaluationCriteriaPhaseOptionService.readOne(modelId);
        savedModel.setName(evaluationCriteriaPhaseOptionDto.getName());
        savedModel.setDescription(evaluationCriteriaPhaseOptionDto.getDescription());
        evaluationCriteriaPhaseOptionService.updateOne(savedModel);
        EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple EvaluationCriteriaPhaseOption options (excludes soft-deleted).
     * @param evaluationCriteriaPhaseOptionDtoList - a List of updated EvaluationCriteriaPhaseOption option data
     * @return                                    - Response with list of updated EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Update multiple EvaluationCriteriaPhaseOption options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoList) {
        List<EvaluationCriteriaPhaseOptionModel> inputModelList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto : evaluationCriteriaPhaseOptionDtoList) {
            inputModelList.add(convertToModel(evaluationCriteriaPhaseOptionDto));
        }
        List<EvaluationCriteriaPhaseOptionModel> updatedModelList = evaluationCriteriaPhaseOptionService.updateMany(inputModelList);
        List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoArrayList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionModel model : updatedModelList) {
            evaluationCriteriaPhaseOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a EvaluationCriteriaPhaseOption option by ID, including soft-deleted.
     *
     * @param evaluationCriteriaPhaseOptionDto - Updated EvaluationCriteriaPhaseOption option data
     * @return                                - Response with updated EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Update a single EvaluationCriteriaPhaseOption option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto) {
        EvaluationCriteriaPhaseOptionModel evaluationCriteriaPhaseOptionModel = evaluationCriteriaPhaseOptionService.hardUpdate(convertToModel(evaluationCriteriaPhaseOptionDto));
        EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto1 = convertToDto(evaluationCriteriaPhaseOptionModel);
        return new ResponseEntity<>(evaluationCriteriaPhaseOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all EvaluationCriteriaPhaseOption options, including soft-deleted.
     * @param evaluationCriteriaPhaseOptionDtoList - List of updated EvaluationCriteriaPhaseOption option data
     * @return                                    - Response with list of updated EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Update all EvaluationCriteriaPhaseOption options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<EvaluationCriteriaPhaseOptionDto> evaluationCriteriaPhaseOptionDtoList) {
        List<EvaluationCriteriaPhaseOptionModel> inputModelList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionDto evaluationCriteriaPhaseOptionDto : evaluationCriteriaPhaseOptionDtoList) {
            inputModelList.add(convertToModel(evaluationCriteriaPhaseOptionDto));
        }
        List<EvaluationCriteriaPhaseOptionModel> updatedModelList = evaluationCriteriaPhaseOptionService.hardUpdateAll(inputModelList);
        List<EvaluationCriteriaPhaseOptionDto> updatedEvaluationCriteriaPhaseOptionDtoList = new ArrayList<>();
        for (EvaluationCriteriaPhaseOptionModel model : updatedModelList) {
            updatedEvaluationCriteriaPhaseOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedEvaluationCriteriaPhaseOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a EvaluationCriteriaPhaseOption option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single EvaluationCriteriaPhaseOption option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        EvaluationCriteriaPhaseOptionModel deleteEvaluationCriteriaPhaseOptionModel = evaluationCriteriaPhaseOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Evaluation criteria phase option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a EvaluationCriteriaPhaseOption option by ID.
     * @param id       - EvaluationCriteriaPhaseOption option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single EvaluationCriteriaPhaseOption option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        evaluationCriteriaPhaseOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Evaluation criteria phase option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple EvaluationCriteriaPhaseOption options by ID.
     * @param idList    - List of EvaluationCriteriaPhaseOption option IDList
     * @return          - Response with list of soft-deleted EvaluationCriteriaPhaseOption option data
     */
    @Operation(summary = "Soft delete multiple EvaluationCriteriaPhaseOption options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<EvaluationCriteriaPhaseOptionModel> deletedEvaluationCriteriaPhaseOptionModelList = evaluationCriteriaPhaseOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Evaluation criteria phase options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple EvaluationCriteriaPhaseOption options by ID.
     * @param idList   - List of EvaluationCriteriaPhaseOption option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple EvaluationCriteriaPhaseOption options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        evaluationCriteriaPhaseOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Evaluation criteria phase options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all EvaluationCriteriaPhaseOption options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all EvaluationCriteriaPhaseOption options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        evaluationCriteriaPhaseOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Evaluation criteria phase options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}