package rw.evolve.eprocurement.country.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.country.exception.CountryOptionAlreadyExistException;
import rw.evolve.eprocurement.country.exception.CountryOptionNotFoundException;
import rw.evolve.eprocurement.country.model.CountryOptionModel;
import rw.evolve.eprocurement.country.repository.CountryOptionRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryOptionService {


    private CountryOptionRepository countryOptionRepository;

    private static final String EXCEL_FILE_PATH = "src/main/resources/static/country_options.xlsx";

    /**
     * Creates a single Country option model with a generated ID and updates the static Excel file.
     *
     * @param countryOptionModel                  - the CountryOptionModel to be created
     * @return                                    - the saved CountryOption model
     * @throws CountryOptionAlreadyExistException - if a CountryOption with the same name, dial code, or code exists
     */
    @Transactional
    public CountryOptionModel save(CountryOptionModel countryOptionModel) {
        if (countryOptionModel == null) {
            throw new NullPointerException("Country option cannot be null");
        }
        validateCountryOption(countryOptionModel);
        if (countryOptionRepository.existsByName(countryOptionModel.getName())) {
            throw new CountryOptionAlreadyExistException("Country option already exists: " + countryOptionModel.getName());
        }
        if (countryOptionRepository.existsByDialCode(countryOptionModel.getDialCode())) {
            throw new CountryOptionAlreadyExistException("Country dial code already exists: " + countryOptionModel.getDialCode());
        }
        if (countryOptionRepository.existsByCode(countryOptionModel.getCode())) {
            throw new CountryOptionAlreadyExistException("Country abbreviation already exists: " + countryOptionModel.getCode());
        }
        CountryOptionModel savedModel = countryOptionRepository.save(countryOptionModel);
        updateStaticExcelFile();
        return savedModel;
    }

    /**
     * Creates multiple Country Option models, each with a unique generated ID, and updates the static Excel file.
     *
     * @param countryOptionModelList    - the list of Country option models to be created
     * @return                          - a list of saved Country Option models
     * @throws IllegalArgumentException - if the input list is null or empty
     * @throws CountryOptionAlreadyExistException - if a CountryOption with the same name, dial code, or code exists
     */
    @Transactional
    public List<CountryOptionModel> saveMany(List<CountryOptionModel> countryOptionModelList) {
        if (countryOptionModelList == null || countryOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Country option model list cannot be null or empty");
        }
        for (CountryOptionModel countryOptionModel : countryOptionModelList) {
            validateCountryOption(countryOptionModel);
            if (countryOptionRepository.existsByName(countryOptionModel.getName())) {
                throw new CountryOptionAlreadyExistException("Country option already exists: " + countryOptionModel.getName());
            }
            if (countryOptionRepository.existsByDialCode(countryOptionModel.getDialCode())) {
                throw new CountryOptionAlreadyExistException("Country dial code already exists: " + countryOptionModel.getDialCode());
            }
            if (countryOptionRepository.existsByCode(countryOptionModel.getCode())) {
                throw new CountryOptionAlreadyExistException("Country abbreviation already exists: " + countryOptionModel.getCode());
            }
        }
        List<CountryOptionModel> savedModels = countryOptionRepository.saveAll(countryOptionModelList);
        updateStaticExcelFile();
        return savedModels;
    }

    /**
     * Validates the country option model for dial code and abbreviation.
     *
     * @param countryOptionModel - the CountryOptionModel to validate
     * @throws IllegalArgumentException - if dial code or abbreviation is invalid
     */
    private void validateCountryOption(CountryOptionModel countryOptionModel) {
        if (countryOptionModel.getDialCode() == null) {
            throw new IllegalArgumentException("Country dial code cannot be null");
        }
        if (!countryOptionModel.getDialCode().matches("\\+[0-9\\s]+")) {
            throw new IllegalArgumentException("Country dial code must start with '+' followed by digits and optional spaces");
        }
        if (countryOptionModel.getCode() == null || countryOptionModel.getCode().length() != 2) {
            throw new IllegalArgumentException("Country abbreviation must be exactly 2 characters");
        }
        if (!countryOptionModel.getCode().matches("[A-Z]{2}")) {
            throw new IllegalArgumentException("Country abbreviation must contain only uppercase letters");
        }
        if (countryOptionModel.getName() == null) {
            throw new IllegalArgumentException("Country name cannot be null");
        }
    }

    /**
     * Updates the static Excel file with all non-deleted CountryOptionModel entries.
     *
     * @throws RuntimeException - if there is an error writing to the file
     */
    private void updateStaticExcelFile() {
        try {
            List<CountryOptionModel> allModels = countryOptionRepository.findByDeletedAtIsNull();
            File file = new File(EXCEL_FILE_PATH);
            file.getParentFile().mkdirs(); // Create directories if they don't exist

            // Create a new workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Country Options");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Dial Code", "Code", "Description", "Created At", "Updated At", "Deleted At"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate data rows
            int rowNum = 1;
            for (CountryOptionModel model : allModels) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(model.getId());
                row.createCell(1).setCellValue(model.getName());
                row.createCell(2).setCellValue(model.getDialCode());
                row.createCell(3).setCellValue(model.getCode());
                row.createCell(4).setCellValue(model.getDescription());
                row.createCell(5).setCellValue(model.getCreatedAt() != null ? model.getCreatedAt().toString() : "");
                row.createCell(6).setCellValue(model.getUpdatedAt() != null ? model.getUpdatedAt().toString() : "");
                row.createCell(7).setCellValue(model.getDeletedAt() != null ? model.getDeletedAt().toString() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }

            // Close the workbook
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to static Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a single Country option model by its ID.
     * Throws a CountryOptionNotFoundException if the Country option is not found or has been deleted.
     *
     * @param id                              - the ID of the Country option to retrieve
     * @return                                - the Country option model if found and not deleted
     * @throws CountryOptionNotFoundException - if the Country option is not found
     * @throws NullPointerException           - if Country option ID is null
     */
    @Transactional
    public CountryOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Country option ID cannot be null");
        }
        CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                .orElseThrow(() -> new CountryOptionNotFoundException("Country option not found with ID: " + id));
        if (countryOptionModel.getDeletedAt() != null) {
            throw new CountryOptionNotFoundException("Country option not found with ID: " + id);
        }
        return countryOptionModel;
    }

    /**
     * Retrieves a list of CountryOption models based on the provided CountryOption IDs.
     *
     * @param countryOptionIdList      - A list of CountryOption IDs to retrieve
     * @return                         - A list of CountryOptionModel objects that are not marked as deleted
     * @throws NullPointerException    - if CountryOption ID list is null
     */
    @Transactional
    public List<CountryOptionModel> readMany(List<String> countryOptionIdList) {
        if (countryOptionIdList == null || countryOptionIdList.isEmpty()) {
            throw new NullPointerException("Country option ID list cannot be null");
        }
        List<CountryOptionModel> modelList = new ArrayList<>();
        for (String id : countryOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Country option ID cannot be null");
            }
            CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                    .orElse(null);
            if (countryOptionModel == null)
                continue;
            if (countryOptionModel.getDeletedAt() == null) {
                modelList.add(countryOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Country options that are not marked as deleted
     *
     * @return         - a List of Country option models where deletedAt is null
     */
    @Transactional
    public List<CountryOptionModel> readAll() {
        return countryOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all Country Option models, including those marked as deleted.
     *
     * @return         - A list of all CountryOptionModel objects
     */
    @Transactional
    public List<CountryOptionModel> hardReadAll() {
        return countryOptionRepository.findAll();
    }

    /**
     * Updates a single Country Option model identified by the provided ID.
     *
     * @param model                           - The CountryOptionModel containing updated data
     * @return                                - The updated CountryOptionModel
     * @throws CountryOptionNotFoundException - if Country option is not found or is marked as deleted
     */
    @Transactional
    public CountryOptionModel updateOne(CountryOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("Country option or ID cannot be null");
        }
        validateCountryOption(model);
        CountryOptionModel existing = countryOptionRepository.findById(model.getId())
                .orElseThrow(() -> new CountryOptionNotFoundException("Country option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new CountryOptionNotFoundException("Country option with ID: " + model.getId() + " is not found");
        }
        return countryOptionRepository.save(model);
    }

    /**
     * Updates multiple Country option models in a transactional manner.
     *
     * @param modelList                       - List of CountryOptionModel objects containing updated data
     * @return                                - List of updated CountryOptionModel objects
     * @throws CountryOptionNotFoundException - if Country option is not found or is marked as deleted
     */
    @Transactional
    public List<CountryOptionModel> updateMany(List<CountryOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Country option model list cannot be null or empty");
        }
        for (CountryOptionModel model : modelList) {
            if (model.getId() == null) {
                throw new NullPointerException("Country option ID cannot be null");
            }
            validateCountryOption(model);
            CountryOptionModel existing = countryOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new CountryOptionNotFoundException("Country option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new CountryOptionNotFoundException("Country option with ID: " + model.getId() + " is not found");
            }
        }
        return countryOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Country option model by ID, including deleted ones.
     *
     * @param model                           - The CountryOptionModel containing updated data
     * @return                                - The updated CountryOptionModel
     * @throws CountryOptionNotFoundException - if Country option is not found
     */
    @Transactional
    public CountryOptionModel hardUpdate(CountryOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("Country option or ID cannot be null");
        }
        validateCountryOption(model);
        return countryOptionRepository.save(model);
    }

    /**
     * Updates multiple CountryOption models by their IDs, including deleted ones.
     *
     * @param countryOptionModelList - List of CountryOptionModel objects containing updated data
     * @return                       - List of updated CountryOptionModel objects
     */
    @Transactional
    public List<CountryOptionModel> hardUpdateAll(List<CountryOptionModel> countryOptionModelList) {
        if (countryOptionModelList == null || countryOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Country option model list cannot be null or empty");
        }
        for (CountryOptionModel model : countryOptionModelList) {
            validateCountryOption(model);
        }
        return countryOptionRepository.saveAll(countryOptionModelList);
    }

    /**
     * Soft deletes a Country option by ID.
     *
     * @param id                              - ID of the Country option to soft delete
     * @return                                - The soft-deleted CountryOptionModel
     * @throws CountryOptionNotFoundException - if Country option id is not found
     */
    @Transactional
    public CountryOptionModel softDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Country option ID cannot be null");
        }
        CountryOptionModel countryOptionModel = countryOptionRepository.findById(id)
                .orElseThrow(() -> new CountryOptionNotFoundException("Country option not found with id: " + id));
        countryOptionModel.setDeletedAt(LocalDateTime.now());
        return countryOptionRepository.save(countryOptionModel);
    }

    /**
     * Hard deletes a Country option by ID.
     *
     * @param id                              - ID of the Country option to hard delete
     * @throws NullPointerException           - if the Country option ID is null
     * @throws CountryOptionNotFoundException - if the Country option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Country option ID cannot be null");
        }
        if (!countryOptionRepository.existsById(id)) {
            throw new CountryOptionNotFoundException("Country option not found with id: " + id);
        }
        countryOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Country options by their IDs.
     *
     * @param idList                          - List of Country option IDs to be soft deleted
     * @return                                - List of soft-deleted CountryOption objects
     * @throws CountryOptionNotFoundException - if any Country option IDs are not found
     */
    @Transactional
    public List<CountryOptionModel> softDeleteMany(List<String> idList) {
        List<CountryOptionModel> countryOptionModelList = countryOptionRepository.findAllById(idList);
        if (countryOptionModelList.isEmpty()) {
            throw new CountryOptionNotFoundException("No Country options found with provided IDList: " + idList);
        }
        for (CountryOptionModel model : countryOptionModelList) {
            model.setDeletedAt(LocalDateTime.now());
        }
        return countryOptionRepository.saveAll(countryOptionModelList);
    }

    /**
     * Hard deletes multiple Country options by IDs.
     *
     * @param idList     - List of Country option IDs to hard delete
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        countryOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Country options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        countryOptionRepository.deleteAll();
    }
}