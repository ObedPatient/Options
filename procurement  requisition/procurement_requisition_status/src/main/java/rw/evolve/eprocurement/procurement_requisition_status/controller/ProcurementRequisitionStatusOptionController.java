/**
 * REST API controller for managing ProcurementRequisitionStatus options.
 * Handles CRUD operations for ProcurementRequisitionStatus option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.procurement_requisition_status.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.procurement_requisition_status.dto.ResponseMessageDto;
import rw.evolve.eprocurement.procurement_requisition_status.dto.ProcurementRequisitionStatusOptionDto;
import rw.evolve.eprocurement.procurement_requisition_status.model.ProcurementRequisitionStatusOptionModel;
import rw.evolve.eprocurement.procurement_requisition_status.service.ProcurementRequisitionStatusOptionService;
import rw.evolve.eprocurement.procurement_requisition_status.utils.ProcurementRequisitionStatusOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/procurement_requisition_status_option")
@Tag(name = "Procurement Requisition Status Option API")
public class ProcurementRequisitionStatusOptionController {

    @Autowired
    private ProcurementRequisitionStatusOptionService procurementRequisitionStatusOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts ProcurementRequisitionStatusOptionModel to ProcurementRequisitionStatusOptionDto.
     * @param model - ProcurementRequisitionStatusOptionModel to convert
     * @return      - Converted ProcurementRequisitionStatusOptionDto
     */
    private ProcurementRequisitionStatusOptionDto convertToDto(ProcurementRequisitionStatusOptionModel model) {
        return modelMapper.map(model, ProcurementRequisitionStatusOptionDto.class);
    }

    /**
     * Converts ProcurementRequisitionStatusOptionDto to ProcurementRequisitionStatusOptionModel.
     * @param procurementRequisitionStatusOptionDto - ProcurementRequisitionStatusOptionDto to convert
     * @return                                      - Converted ProcurementRequisitionStatusOptionModel
     */
    private ProcurementRequisitionStatusOptionModel convertToModel(ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto) {
        return modelMapper.map(procurementRequisitionStatusOptionDto, ProcurementRequisitionStatusOptionModel.class);
    }

    /**
     * Creates a single ProcurementRequisitionStatus option with a generated ID.
     * @param procurementRequisitionStatusOptionDto - ProcurementRequisitionStatus option data
     * @return                                      - Response with success message
     */
    @Operation(summary = "Create a single ProcurementRequisitionStatus option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto) {
        ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel = convertToModel(procurementRequisitionStatusOptionDto);
        procurementRequisitionStatusOptionModel.setId(ProcurementRequisitionStatusOptionIdGenerator.generateId());
        procurementRequisitionStatusOptionService.save(procurementRequisitionStatusOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement requisition status option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple ProcurementRequisitionStatus options with generated IDList.
     * @param procurementRequisitionStatusOptionDtoList - List of ProcurementRequisitionStatus option data
     * @return                                          - Response with success message
     */
    @Operation(summary = "Create multiple ProcurementRequisitionStatus options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoList) {
        List<ProcurementRequisitionStatusOptionModel> procurementRequisitionStatusOptionModelList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto : procurementRequisitionStatusOptionDtoList) {
            ProcurementRequisitionStatusOptionModel model = convertToModel(procurementRequisitionStatusOptionDto);
            model.setId(ProcurementRequisitionStatusOptionIdGenerator.generateId());
            procurementRequisitionStatusOptionModelList.add(model);
        }
        procurementRequisitionStatusOptionService.saveMany(procurementRequisitionStatusOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement requisition status options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a ProcurementRequisitionStatus option by ID (excludes soft-deleted).
     * @param id - ProcurementRequisitionStatus option ID
     * @return   - Response with ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Get a single ProcurementRequisitionStatus option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        ProcurementRequisitionStatusOptionModel model = procurementRequisitionStatusOptionService.readOne(id);
        ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto = convertToDto(model);
        return new ResponseEntity<>(procurementRequisitionStatusOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted ProcurementRequisitionStatus options.
     * @return  - Response with list of ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Get all available ProcurementRequisitionStatus options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<ProcurementRequisitionStatusOptionModel> procurementRequisitionStatusOptionModelList = procurementRequisitionStatusOptionService.readAll();
        List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel : procurementRequisitionStatusOptionModelList) {
            procurementRequisitionStatusOptionDtoList.add(convertToDto(procurementRequisitionStatusOptionModel));
        }
        return new ResponseEntity<>(procurementRequisitionStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all ProcurementRequisitionStatus options, including soft-deleted.
     * @return        - Response with list of all ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Get all ProcurementRequisitionStatus options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<ProcurementRequisitionStatusOptionModel> modelList = procurementRequisitionStatusOptionService.hardReadAll();
        List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionModel model : modelList) {
            procurementRequisitionStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementRequisitionStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple ProcurementRequisitionStatus options by ID (excludes soft-deleted).
     * @param idList - List of ProcurementRequisitionStatus option ID
     * @return       - Response with list of ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Get multiple ProcurementRequisitionStatus options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<ProcurementRequisitionStatusOptionModel> procurementRequisitionStatusOptionModelList = procurementRequisitionStatusOptionService.readMany(idList);
        List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionModel model : procurementRequisitionStatusOptionModelList) {
            procurementRequisitionStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementRequisitionStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a ProcurementRequisitionStatus option by ID (excludes soft-deleted).
     *
     * @param procurementRequisitionStatusOptionDto - Updated ProcurementRequisitionStatus option data
     * @return                                      - Response with updated ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Update a single ProcurementRequisitionStatus option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto){
        String modelId = procurementRequisitionStatusOptionDto.getId();
        ProcurementRequisitionStatusOptionModel savedModel = procurementRequisitionStatusOptionService.readOne(modelId);
        savedModel.setName(procurementRequisitionStatusOptionDto.getName());
        savedModel.setDescription(procurementRequisitionStatusOptionDto.getDescription());
        procurementRequisitionStatusOptionService.updateOne(savedModel);
        ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(procurementRequisitionStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple ProcurementRequisitionStatus options (excludes soft-deleted).
     * @param procurementRequisitionStatusOptionDtoList - a List of updated ProcurementRequisitionStatus option data
     * @return                                          - Response with list of updated ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Update multiple ProcurementRequisitionStatus options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoList) {
        List<ProcurementRequisitionStatusOptionModel> inputModelList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto : procurementRequisitionStatusOptionDtoList) {
            inputModelList.add(convertToModel(procurementRequisitionStatusOptionDto));
        }
        List<ProcurementRequisitionStatusOptionModel> updatedModelList = procurementRequisitionStatusOptionService.updateMany(inputModelList);
        List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoArrayList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionModel model : updatedModelList) {
            procurementRequisitionStatusOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(procurementRequisitionStatusOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a ProcurementRequisitionStatus option by ID, including soft-deleted.
     *
     * @param procurementRequisitionStatusOptionDto - Updated ProcurementRequisitionStatus option data
     * @return                                      - Response with updated ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Update a single ProcurementRequisitionStatus option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto) {
        ProcurementRequisitionStatusOptionModel procurementRequisitionStatusOptionModel = procurementRequisitionStatusOptionService.hardUpdate(convertToModel(procurementRequisitionStatusOptionDto));
        ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto1 = convertToDto(procurementRequisitionStatusOptionModel);
        return new ResponseEntity<>(procurementRequisitionStatusOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all ProcurementRequisitionStatus options, including soft-deleted.
     * @param procurementRequisitionStatusOptionDtoList - List of updated ProcurementRequisitionStatus option data
     * @return                                          - Response with list of updated ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Update all ProcurementRequisitionStatus options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<ProcurementRequisitionStatusOptionDto> procurementRequisitionStatusOptionDtoList) {
        List<ProcurementRequisitionStatusOptionModel> inputModelList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionDto procurementRequisitionStatusOptionDto : procurementRequisitionStatusOptionDtoList) {
            inputModelList.add(convertToModel(procurementRequisitionStatusOptionDto));
        }
        List<ProcurementRequisitionStatusOptionModel> updatedModelList = procurementRequisitionStatusOptionService.hardUpdateAll(inputModelList);
        List<ProcurementRequisitionStatusOptionDto> updatedProcurementRequisitionStatusOptionDtoList = new ArrayList<>();
        for (ProcurementRequisitionStatusOptionModel model : updatedModelList) {
            updatedProcurementRequisitionStatusOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedProcurementRequisitionStatusOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a ProcurementRequisitionStatus option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single ProcurementRequisitionStatus option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        ProcurementRequisitionStatusOptionModel deleteProcurementRequisitionStatusOptionModel = procurementRequisitionStatusOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement requisition status option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a ProcurementRequisitionStatus option by ID.
     * @param id       - ProcurementRequisitionStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single ProcurementRequisitionStatus option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        procurementRequisitionStatusOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement requisition status option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple ProcurementRequisitionStatus options by ID.
     * @param idList    - List of ProcurementRequisitionStatus option IDList
     * @return          - Response with list of soft-deleted ProcurementRequisitionStatus option data
     */
    @Operation(summary = "Soft delete multiple ProcurementRequisitionStatus options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<ProcurementRequisitionStatusOptionModel> deletedProcurementRequisitionStatusOptionModelList = procurementRequisitionStatusOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Procurement requisition status options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple ProcurementRequisitionStatus options by ID.
     * @param idList   - List of ProcurementRequisitionStatus option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple ProcurementRequisitionStatus options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        procurementRequisitionStatusOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement requisition status options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all ProcurementRequisitionStatus options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all ProcurementRequisitionStatus options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        procurementRequisitionStatusOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Procurement requisition status options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}