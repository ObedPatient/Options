/**
 * REST API controller for managing DonorType options.
 * Handles CRUD operations for DonorType option data with soft and hard delete capabilities.
 */
package rw.evolve.eprocurement.donor_type.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.evolve.eprocurement.donor_type.dto.ResponseMessageDto;
import rw.evolve.eprocurement.donor_type.dto.DonorTypeOptionDto;
import rw.evolve.eprocurement.donor_type.model.DonorTypeOptionModel;
import rw.evolve.eprocurement.donor_type.service.DonorTypeOptionService;
import rw.evolve.eprocurement.donor_type.utils.DonorTypeOptionIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/donor_type_option")
@Tag(name = "Donor Type Option API")
public class DonorTypeOptionController {

    @Autowired
    private DonorTypeOptionService donorTypeOptionService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts DonorTypeOptionModel to DonorTypeOptionDto.
     * @param model - DonorTypeOptionModel to convert
     * @return      - Converted DonorTypeOptionDto
     */
    private DonorTypeOptionDto convertToDto(DonorTypeOptionModel model) {
        return modelMapper.map(model, DonorTypeOptionDto.class);
    }

    /**
     * Converts DonorTypeOptionDto to DonorTypeOptionModel.
     * @param donorTypeOptionDto - DonorTypeOptionDto to convert
     * @return                   - Converted DonorTypeOptionModel
     */
    private DonorTypeOptionModel convertToModel(DonorTypeOptionDto donorTypeOptionDto) {
        return modelMapper.map(donorTypeOptionDto, DonorTypeOptionModel.class);
    }

    /**
     * Creates a single DonorType option with a generated ID.
     * @param donorTypeOptionDto - DonorType option data
     * @return                   - Response with success message
     */
    @Operation(summary = "Create a single DonorType option")
    @PostMapping("/create/one")
    public ResponseEntity<Object> save(@Valid @RequestBody DonorTypeOptionDto donorTypeOptionDto) {
        DonorTypeOptionModel donorTypeOptionModel = convertToModel(donorTypeOptionDto);
        donorTypeOptionModel.setId(DonorTypeOptionIdGenerator.generateId());
        donorTypeOptionService.save(donorTypeOptionModel);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Donor type option created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Creates multiple DonorType options with generated IDList.
     * @param donorTypeOptionDtoList - List of DonorType option data
     * @return                       - Response with success message
     */
    @Operation(summary = "Create multiple DonorType options")
    @PostMapping("/create/many")
    public ResponseEntity<Object> saveMany(@Valid @RequestBody List<DonorTypeOptionDto> donorTypeOptionDtoList) {
        List<DonorTypeOptionModel> donorTypeOptionModelList = new ArrayList<>();
        for (DonorTypeOptionDto donorTypeOptionDto : donorTypeOptionDtoList) {
            DonorTypeOptionModel model = convertToModel(donorTypeOptionDto);
            model.setId(DonorTypeOptionIdGenerator.generateId());
            donorTypeOptionModelList.add(model);
        }
        donorTypeOptionService.saveMany(donorTypeOptionModelList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Donor type options created successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Retrieves a DonorType option by ID (excludes soft-deleted).
     * @param id - DonorType option ID
     * @return   - Response with DonorType option data
     */
    @Operation(summary = "Get a single DonorType option by ID")
    @GetMapping("/read/one")
    public ResponseEntity<Object> readOne(@RequestParam("id") String id) {
        DonorTypeOptionModel model = donorTypeOptionService.readOne(id);
        DonorTypeOptionDto donorTypeOptionDto = convertToDto(model);
        return new ResponseEntity<>(donorTypeOptionDto, HttpStatus.OK);
    }

    /**
     * Retrieves all non-deleted DonorType options.
     * @return  - Response with list of DonorType option data
     */
    @Operation(summary = "Get all available DonorType options")
    @GetMapping("/read/all")
    public ResponseEntity<Object> readAll() {
        List<DonorTypeOptionModel> donorTypeOptionModelList = donorTypeOptionService.readAll();
        List<DonorTypeOptionDto> donorTypeOptionDtoList = new ArrayList<>();
        for (DonorTypeOptionModel donorTypeOptionModel : donorTypeOptionModelList) {
            donorTypeOptionDtoList.add(convertToDto(donorTypeOptionModel));
        }
        return new ResponseEntity<>(donorTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves all DonorType options, including soft-deleted.
     * @return        - Response with list of all DonorType option data
     */
    @Operation(summary = "Get all DonorType options, including soft-deleted")
    @GetMapping("/read/hard/all")
    public ResponseEntity<Object> hardReadAll() {
        List<DonorTypeOptionModel> modelList = donorTypeOptionService.hardReadAll();
        List<DonorTypeOptionDto> donorTypeOptionDtoList = new ArrayList<>();
        for (DonorTypeOptionModel model : modelList) {
            donorTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(donorTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Retrieves multiple DonorType options by ID (excludes soft-deleted).
     * @param idList - List of DonorType option ID
     * @return       - Response with list of DonorType option data
     */
    @Operation(summary = "Get multiple DonorType options by ID")
    @PostMapping("/read/many")
    public ResponseEntity<Object> readMany(@Valid @RequestParam("id_list") List<String> idList) {
        List<DonorTypeOptionModel> donorTypeOptionModelList = donorTypeOptionService.readMany(idList);
        List<DonorTypeOptionDto> donorTypeOptionDtoList = new ArrayList<>();
        for (DonorTypeOptionModel model : donorTypeOptionModelList) {
            donorTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(donorTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Updates a DonorType option by ID (excludes soft-deleted).
     *
     * @param donorTypeOptionDto - Updated DonorType option data
     * @return                   - Response with updated DonorType option data
     */
    @Operation(summary = "Update a single DonorType option by ID")
    @PutMapping("/update/one")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody DonorTypeOptionDto donorTypeOptionDto){
        String modelId = donorTypeOptionDto.getId();
        DonorTypeOptionModel savedModel = donorTypeOptionService.readOne(modelId);
        savedModel.setName(donorTypeOptionDto.getName());
        savedModel.setDescription(donorTypeOptionDto.getDescription());
        donorTypeOptionService.updateOne(savedModel);
        DonorTypeOptionDto donorTypeOptionDto1 = convertToDto(savedModel);
        return new ResponseEntity<>(donorTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates multiple DonorType options (excludes soft-deleted).
     * @param donorTypeOptionDtoList - a List of updated DonorType option data
     * @return                       - Response with list of updated DonorType option data
     */
    @Operation(summary = "Update multiple DonorType options")
    @PutMapping("/update/many")
    public ResponseEntity<Object> updateMany(@Valid @RequestBody List<DonorTypeOptionDto> donorTypeOptionDtoList) {
        List<DonorTypeOptionModel> inputModelList = new ArrayList<>();
        for (DonorTypeOptionDto donorTypeOptionDto : donorTypeOptionDtoList) {
            inputModelList.add(convertToModel(donorTypeOptionDto));
        }
        List<DonorTypeOptionModel> updatedModelList = donorTypeOptionService.updateMany(inputModelList);
        List<DonorTypeOptionDto> donorTypeOptionDtoArrayList = new ArrayList<>();
        for (DonorTypeOptionModel model : updatedModelList) {
            donorTypeOptionDtoArrayList.add(convertToDto(model));
        }
        return new ResponseEntity<>(donorTypeOptionDtoArrayList, HttpStatus.OK);
    }

    /**
     * Updates a DonorType option by ID, including soft-deleted.
     *
     * @param donorTypeOptionDto - Updated DonorType option data
     * @return                   - Response with updated DonorType option data
     */
    @Operation(summary = "Update a single DonorType option by ID, including soft-deleted")
    @PutMapping("/update/hard/one")
    public ResponseEntity<Object> hardUpdate(@Valid @RequestBody DonorTypeOptionDto donorTypeOptionDto) {
        DonorTypeOptionModel donorTypeOptionModel = donorTypeOptionService.hardUpdate(convertToModel(donorTypeOptionDto));
        DonorTypeOptionDto donorTypeOptionDto1 = convertToDto(donorTypeOptionModel);
        return new ResponseEntity<>(donorTypeOptionDto1, HttpStatus.OK);
    }

    /**
     * Updates all DonorType options, including soft-deleted.
     * @param donorTypeOptionDtoList - List of updated DonorType option data
     * @return                       - Response with list of updated DonorType option data
     */
    @Operation(summary = "Update all DonorType options, including soft-deleted")
    @PutMapping("/update/hard/all")
    public ResponseEntity<Object> hardUpdateAll(@Valid @RequestBody List<DonorTypeOptionDto> donorTypeOptionDtoList) {
        List<DonorTypeOptionModel> inputModelList = new ArrayList<>();
        for (DonorTypeOptionDto donorTypeOptionDto : donorTypeOptionDtoList) {
            inputModelList.add(convertToModel(donorTypeOptionDto));
        }
        List<DonorTypeOptionModel> updatedModelList = donorTypeOptionService.hardUpdateAll(inputModelList);
        List<DonorTypeOptionDto> updatedDonorTypeOptionDtoList = new ArrayList<>();
        for (DonorTypeOptionModel model : updatedModelList) {
            updatedDonorTypeOptionDtoList.add(convertToDto(model));
        }
        return new ResponseEntity<>(updatedDonorTypeOptionDtoList, HttpStatus.OK);
    }

    /**
     * Soft deletes a DonorType option by ID.
     * @return   - Response with success message
     */
    @Operation(summary = "Soft delete a single DonorType option by ID")
    @PutMapping("/soft/delete/one")
    public ResponseEntity<Object> softDelete(@RequestParam String id){
        DonorTypeOptionModel deleteDonorTypeOptionModel = donorTypeOptionService.softDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Donor type option soft deleted successfully",
                "OK",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes a DonorType option by ID.
     * @param id       - DonorType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete a single DonorType option by ID")
    @GetMapping("/hard/delete/{id}")
    public ResponseEntity<Object> hardDelete(@RequestParam String id) {
        donorTypeOptionService.hardDelete(id);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Donor type option hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Soft deletes multiple DonorType options by ID.
     * @param idList    - List of DonorType option IDList
     * @return          - Response with list of soft-deleted DonorType option data
     */
    @Operation(summary = "Soft delete multiple DonorType options by ID")
    @PutMapping("/soft/delete/many")
    public ResponseEntity<Object> softDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        List<DonorTypeOptionModel> deletedDonorTypeOptionModelList = donorTypeOptionService.softDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "Donor type options soft deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes multiple DonorType options by ID.
     * @param idList   - List of DonorType option ID
     * @return         - Response with success message
     */
    @Operation(summary = "Hard delete multiple DonorType options by ID")
    @GetMapping("/hard/delete/many")
    public ResponseEntity<Object> hardDeleteMany(@Valid @RequestParam("idList") List<String> idList) {
        donorTypeOptionService.hardDeleteMany(idList);
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Donor type options hard deleted successfully",
                HttpStatus.OK.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }

    /**
     * Hard deletes all DonorType options, including soft-deleted.
     * @return          - Response with success message
     */
    @Operation(summary = "Hard delete all DonorType options")
    @GetMapping("/hard/delete/all")
    public ResponseEntity<Object> hardDeleteAll() {
        donorTypeOptionService.hardDeleteAll();
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(
                "All Donor type options hard deleted successfully",
                HttpStatus.OK + "",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseMessageDto, HttpStatus.OK);
    }
}