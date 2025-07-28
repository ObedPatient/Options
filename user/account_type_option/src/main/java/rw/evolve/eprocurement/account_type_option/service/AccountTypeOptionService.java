package rw.evolve.eprocurement.account_type_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.account_type_option.exception.AccountTypeExistException;
import rw.evolve.eprocurement.account_type_option.exception.AccountTypeOptionNotFoundException;
import rw.evolve.eprocurement.account_type_option.model.AccountTypeOptionModel;
import rw.evolve.eprocurement.account_type_option.repository.AccountTypeOptionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing Account type option entities.
 * Provides functionality to create, read, update, and delete Account type option data, supporting both
 * soft and hard deletion operations through the corresponding repository.
 */
@Service
public class AccountTypeOptionService {

    @Autowired
    private AccountTypeOptionRepository accountTypeOptionRepository;

    /**
     * Creates a single Account type option model with a generated ID.
     *
     * @param accountTypeOptionModel       - the AccountTypeOptionModel to be created
     * @return                             - the saved AccountTypeOption model
     * @throws AccountTypeExistException   - if an AccountTypeOption with the same name exists
     */
    @Transactional
    public AccountTypeOptionModel save(AccountTypeOptionModel accountTypeOptionModel) {
        if (accountTypeOptionModel == null) {
            throw new NullPointerException("Account type option cannot be null");
        }
        if (accountTypeOptionRepository.existsByName(accountTypeOptionModel.getName())) {
            throw new AccountTypeExistException("Account type option already exists: " + accountTypeOptionModel.getName());
        }
        return accountTypeOptionRepository.save(accountTypeOptionModel);
    }

    /**
     * Creates multiple Account type option models, each with a unique generated ID.
     *
     * @param accountTypeOptionModelList   - the list of Account type option models to be created
     * @return                             - a list of saved Account type option models
     * @throws IllegalArgumentException    - if the input list is null or empty
     */
    @Transactional
    public List<AccountTypeOptionModel> saveMany(List<AccountTypeOptionModel> accountTypeOptionModelList) {
        if (accountTypeOptionModelList == null || accountTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Account type option model list cannot be null or empty");
        }
        for (AccountTypeOptionModel accountTypeOptionModel : accountTypeOptionModelList) {
            if (accountTypeOptionRepository.existsByName(accountTypeOptionModel.getName())) {
                throw new AccountTypeExistException("Account type option already exists: " + accountTypeOptionModel.getName());
            }
        }
        return accountTypeOptionRepository.saveAll(accountTypeOptionModelList);
    }

    /**
     * Retrieves a single Account type option model by its ID.
     * Throws an AccountTypeOptionNotFoundException if the Account type option is not found or has been deleted.
     *
     * @param id                                  - the ID of the Account type option to retrieve
     * @return                                    - the Account type option model if found and not deleted
     * @throws AccountTypeOptionNotFoundException - if the Account type option is not found
     * @throws NullPointerException               - if Account type option ID is null
     */
    @Transactional
    public AccountTypeOptionModel readOne(String id) {
        if (id == null) {
            throw new NullPointerException("Account type option ID cannot be null");
        }
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                .orElseThrow(() -> new AccountTypeOptionNotFoundException("Account type option not found with ID: " + id));
        if (accountTypeOptionModel.getDeletedAt() != null) {
            throw new AccountTypeOptionNotFoundException("Account type option not found with ID: " + id);
        }
        return accountTypeOptionModel;
    }

    /**
     * Retrieves a list of AccountTypeOption models based on the provided AccountTypeOption IDs.
     *
     * @param accountTypeOptionIdList      - A list of AccountTypeOption IDs to retrieve
     * @return                             - A list of AccountTypeOptionModel objects that are not marked as deleted
     * @throws NullPointerException        - if AccountTypeOption ID list is null
     */
    @Transactional
    public List<AccountTypeOptionModel> readMany(List<String> accountTypeOptionIdList) {
        if (accountTypeOptionIdList == null || accountTypeOptionIdList.isEmpty()) {
            throw new NullPointerException("Account type option ID list cannot be null");
        }
        List<AccountTypeOptionModel> modelList = new ArrayList<>();
        for (String id : accountTypeOptionIdList) {
            if (id == null) {
                throw new NullPointerException("Account type option ID cannot be null");
            }
            AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                    .orElse(null);
            if (accountTypeOptionModel == null)
                continue;
            if (accountTypeOptionModel.getDeletedAt() == null) {
                modelList.add(accountTypeOptionModel);
            }
        }
        return modelList;
    }

    /**
     * Retrieve all Account type options that are not marked as deleted.
     *
     * @return         - a List of Account type option models where deletedAt is null
     */
    @Transactional
    public List<AccountTypeOptionModel> readAll() {
        return accountTypeOptionRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all AccountTypeOption models, including those marked as deleted.
     *
     * @return         - A list of all AccountTypeOptionModel objects
     */
    @Transactional
    public List<AccountTypeOptionModel> hardReadAll() {
        return accountTypeOptionRepository.findAll();
    }

    /**
     * Updates a single Account type option model identified by the provided ID.
     *
     * @param model                               - The AccountTypeOptionModel containing updated data
     * @return                                    - The updated AccountTypeOptionModel
     * @throws AccountTypeOptionNotFoundException - if Account type option is not found or is marked as deleted
     */
    @Transactional
    public AccountTypeOptionModel updateOne(AccountTypeOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("Account type option or ID cannot be null");
        }
        AccountTypeOptionModel existing = accountTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new AccountTypeOptionNotFoundException("Account type option not found with ID: " + model.getId()));
        if (existing.getDeletedAt() != null) {
            throw new AccountTypeOptionNotFoundException("Account type option with ID: " + model.getId() + " is not found");
        }
        return accountTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple Account type option models in a transactional manner.
     *
     * @param modelList                           - List of AccountTypeOptionModel objects containing updated data
     * @return                                    - List of updated AccountTypeOptionModel objects
     * @throws AccountTypeOptionNotFoundException - if Account type option is not found or is marked as deleted
     */
    @Transactional
    public List<AccountTypeOptionModel> updateMany(List<AccountTypeOptionModel> modelList) {
        if (modelList == null || modelList.isEmpty()) {
            throw new IllegalArgumentException("Account type option model list cannot be null or empty");
        }
        for (AccountTypeOptionModel model : modelList) {
            if (model.getId() == null) {
                throw new NullPointerException("Account type option ID cannot be null");
            }
            AccountTypeOptionModel existing = accountTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new AccountTypeOptionNotFoundException("Account type option not found with ID: " + model.getId()));
            if (existing.getDeletedAt() != null) {
                throw new AccountTypeOptionNotFoundException("Account type option with ID: " + model.getId() + " is not found");
            }
        }
        return accountTypeOptionRepository.saveAll(modelList);
    }

    /**
     * Updates a single Account type option model by ID, including deleted ones.
     *
     * @param model                               - The AccountTypeOptionModel containing updated data
     * @return                                    - The updated AccountTypeOptionModel
     * @throws AccountTypeOptionNotFoundException - if Account type option is not found
     */
    @Transactional
    public AccountTypeOptionModel hardUpdate(AccountTypeOptionModel model) {
        if (model == null || model.getId() == null) {
            throw new NullPointerException("Account type option or ID cannot be null");
        }
        AccountTypeOptionModel existing = accountTypeOptionRepository.findById(model.getId())
                .orElseThrow(() -> new AccountTypeOptionNotFoundException("Account type option not found with ID: " + model.getId()));
        return accountTypeOptionRepository.save(model);
    }

    /**
     * Updates multiple Account type option models by their IDs, including deleted ones.
     *
     * @param accountTypeOptionModelList      - List of AccountTypeOptionModel objects containing updated data
     * @return                                - List of updated AccountTypeOptionModel objects
     * @throws IllegalArgumentException       - if the input list is null or empty
     */
    @Transactional
    public List<AccountTypeOptionModel> hardUpdateAll(List<AccountTypeOptionModel> accountTypeOptionModelList) {
        if (accountTypeOptionModelList == null || accountTypeOptionModelList.isEmpty()) {
            throw new IllegalArgumentException("Account type option model list cannot be null or empty");
        }
        for (AccountTypeOptionModel model : accountTypeOptionModelList) {
            if (model.getId() == null) {
                throw new NullPointerException("Account type option ID cannot be null");
            }
            accountTypeOptionRepository.findById(model.getId())
                    .orElseThrow(() -> new AccountTypeOptionNotFoundException("Account type option not found with ID: " + model.getId()));
        }
        return accountTypeOptionRepository.saveAll(accountTypeOptionModelList);
    }

    /**
     * Soft deletes an Account type option by ID.
     *
     * @param id                                  - ID of the Account type option to soft delete
     * @return                                    - The soft-deleted AccountTypeOptionModel
     * @throws AccountTypeOptionNotFoundException - if Account type option is not found
     * @throws IllegalStateException              - if the Account type option is already deleted
     */
    @Transactional
    public AccountTypeOptionModel softDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Account type option ID cannot be null");
        }
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                .orElseThrow(() -> new AccountTypeOptionNotFoundException("Account type option not found with ID: " + id));
        if (accountTypeOptionModel.getDeletedAt() != null) {
            throw new IllegalStateException("Account type option with ID: " + id + " is already deleted");
        }
        accountTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return accountTypeOptionRepository.save(accountTypeOptionModel);
    }

    /**
     * Hard deletes an Account type option by ID.
     *
     * @param id                                  - ID of the Account type option to hard delete
     * @throws NullPointerException               - if the Account type option ID is null
     * @throws AccountTypeOptionNotFoundException - if the Account type option is not found
     */
    @Transactional
    public void hardDelete(String id) {
        if (id == null) {
            throw new NullPointerException("Account type option ID cannot be null");
        }
        if (!accountTypeOptionRepository.existsById(id)) {
            throw new AccountTypeOptionNotFoundException("Account type option not found with ID: " + id);
        }
        accountTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple Account type options by their IDs.
     *
     * @param idList                              - List of Account type option IDs to be soft deleted
     * @return                                    - List of soft-deleted AccountTypeOption objects
     * @throws AccountTypeOptionNotFoundException - if any Account type option IDs are not found
     */
    @Transactional
    public List<AccountTypeOptionModel> softDeleteMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new IllegalArgumentException("Account type option ID list cannot be null or empty");
        }
        List<AccountTypeOptionModel> accountTypeOptionModelList = accountTypeOptionRepository.findAllById(idList);
        if (accountTypeOptionModelList.isEmpty()) {
            throw new AccountTypeOptionNotFoundException("No account type options found with provided ID list: " + idList);
        }
        for (AccountTypeOptionModel model : accountTypeOptionModelList) {
            if (model.getDeletedAt() != null) {
                throw new IllegalStateException("Account type option with ID: " + model.getId() + " is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        return accountTypeOptionRepository.saveAll(accountTypeOptionModelList);
    }

    /**
     * Hard deletes multiple Account type options by IDs.
     *
     * @param idList                              - List of Account type option IDs to hard delete
     * @throws IllegalArgumentException           - if the ID list is null or empty
     * @throws AccountTypeOptionNotFoundException - if any Account type option IDs are not found
     */
    @Transactional
    public void hardDeleteMany(List<String> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new IllegalArgumentException("Account type option ID list cannot be null or empty");
        }
        List<AccountTypeOptionModel> accountTypeOptionModelList = accountTypeOptionRepository.findAllById(idList);
        if (accountTypeOptionModelList.isEmpty()) {
            throw new AccountTypeOptionNotFoundException("No account type options found with provided ID list: " + idList);
        }
        accountTypeOptionRepository.deleteAllById(idList);
    }

    /**
     * Hard deletes all Account type options, including soft-deleted ones.
     */
    @Transactional
    public void hardDeleteAll() {
        accountTypeOptionRepository.deleteAll();
    }
}