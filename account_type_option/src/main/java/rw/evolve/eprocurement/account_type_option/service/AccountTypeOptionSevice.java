/**
 * Service for managing Account type option entities.
 * Provides functionality to create, read, update, and delete Account type option data.
 */
package rw.evolve.eprocurement.account_type_option.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.evolve.eprocurement.account_type_option.exception.AccountTypeExistException;
import rw.evolve.eprocurement.account_type_option.exception.AccountTypeOptionNotFoundException;
import rw.evolve.eprocurement.account_type_option.model.AccountTypeOptionModel;
import rw.evolve.eprocurement.account_type_option.repository.AccountTypeOptionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountTypeOptionSevice {

    @Autowired
    private AccountTypeOptionRepository accountTypeOptionRepository;

    /**
     * Creates a single Account type option entity with a generated fiscal year ID.
     *
     * @param @AccountTypeOptionModel the fiscal year model to be created
     * @return the saved AccountTypeOption model
     */
    @Transactional
    public AccountTypeOptionModel createAccountType(AccountTypeOptionModel accountTypeOptionModel){
        if (accountTypeOptionRepository.existsByName(accountTypeOptionModel.getName())){
            throw new AccountTypeExistException("Account type Already exists: " + accountTypeOptionModel.getName());
        }
        return accountTypeOptionRepository.save(accountTypeOptionModel);
    }

    /**
     * Creates multiple Account type entities, each with a unique ID.
     * Iterates through the provided list of Account type models
     *
     * @param accountTypeOptionModelList the list of Account type option models to be created
     * @return a list of saved Account type Option models.
     */
    @Transactional
    public List<AccountTypeOptionModel> createAccountTypes(List<AccountTypeOptionModel> accountTypeOptionModelList){
        if (accountTypeOptionModelList == null){
            throw new IllegalArgumentException("Account type option model cannot be null");
        }
        List<AccountTypeOptionModel> savedAccountTypeModels = new ArrayList<>();
        for (AccountTypeOptionModel accountTypeOptionModel: accountTypeOptionModelList){
            AccountTypeOptionModel savedAccountTypeModel = accountTypeOptionRepository.save(accountTypeOptionModel);
            savedAccountTypeModels.add(savedAccountTypeModel);
        }
        return savedAccountTypeModels;
    }

    /**
     * Retrieves a single Account type option entity by its ID.
     * Throws a AccounttypeoptionNotFoundException if the Account type option is not found or has been deleted.
     *
     * @param @AccountTypeOption id the ID of the Account type option to retrieve
     * @return the Account type option model if found and not deleted
     * @throws AccountTypeOptionNotFoundException if the Account type option is not found.
     */
    @Transactional
    public AccountTypeOptionModel readOne(Long id){
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account Type option not found with id:" + id));
        if (accountTypeOptionModel.getDeletedAt() != null){
            throw new AccountTypeOptionNotFoundException("Account Type option not found with id:" + id);
        }
        return accountTypeOptionModel;
    }

    /**
     * Retrieves a list of AccountTypeOption objects based on the provided AccountTypeOption IDs.
     *
     * @param accountTypeOptionIds A list of AccountTypeOption IDs to retrieve
     * @return A list of AccountTypeOptionModel objects that are not marked as deleted
     * @throws AccountTypeOptionNotFoundException if a AccountTypeOption with the given ID is not found
     */
    @Transactional
    public List<AccountTypeOptionModel> readMany(List<Long> accountTypeOptionIds){
        if (accountTypeOptionIds == null){
            throw new IllegalArgumentException("Account type option id cannot be null or empty");
        }
        List<AccountTypeOptionModel> models = new ArrayList<>();
        for (Long accountTypeOptionid: accountTypeOptionIds){
            if (accountTypeOptionid == null){
                throw new IllegalArgumentException("Account type option id cannot be null or emty");
            }
            AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(accountTypeOptionid)
                    .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account type Not found with id:" + accountTypeOptionid));
            if (accountTypeOptionModel.getDeletedAt() == null){
                models.add(accountTypeOptionModel);
            }
        }
        return models;
    }

    /**
     * Retrieve all AccountType OPtion that are not marked as deleted
     * @return a List of Account type option object where deleted in null
     * @throws AccountTypeOptionNotFoundException if no Account type option found
     */
    @Transactional
    public List<AccountTypeOptionModel> readall(){
        List<AccountTypeOptionModel> accountTypeOptionModels = accountTypeOptionRepository.findByDeletedAtIsNull();
        if (accountTypeOptionModels.isEmpty()){
            throw new AccountTypeOptionNotFoundException("No account type found");
        }
        return accountTypeOptionModels;
    }
    /**
     * Retrieves all AccountTypeOptionModels, including those marked as deleted.
     *
     * @return A list of all AccountTypeOptionModel objects
     * @throws AccountTypeOptionNotFoundException if no AccountTypoption are found
     */
    @Transactional
    public List<AccountTypeOptionModel> hardReadAll(){
        List<AccountTypeOptionModel> accountTypeOptionModels = accountTypeOptionRepository.findAll();
        if (accountTypeOptionModels.isEmpty()){
            throw new AccountTypeOptionNotFoundException("No Account type option found");
        }
        return accountTypeOptionModels;
    }

    /**
     * Updates a single AccountTypeOptionModel model identified by the provided ID.
     *
     * @param id    The ID of the AccountTypeOption to update
     * @param model The AccountTypeOptionModel containing updated data
     * @return The updated AccountTypeOptionModel
     * @throws AccountTypeOptionNotFoundException if the AccountTypeOptionModel is not found or is marked as deleted
     */
    @Transactional
    public AccountTypeOptionModel updateOne(Long id, AccountTypeOptionModel model){
        if (id == null){
            throw new IllegalArgumentException("Account Type option id cannot be null");
        }
        if (model == null){
            throw new IllegalArgumentException("Account type Option cannot be null");
        }
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account type option not found with id:" + id));
        if (accountTypeOptionModel.getDeletedAt() != null){
            throw new AccountTypeExistException("Account type option is marked as deleted with id:" + id);
        }
        accountTypeOptionModel.setUpdatedAt(LocalDateTime.now());
        return accountTypeOptionRepository.save(accountTypeOptionModel);
    }
    /**
     * Updates multiple AccountTypeOption models in a transactional manner.
     *
     * @param models List of AccountTypeOptionModel objects containing updated data
     * @return List of updated AccountTypeOptionModel objects
     * @throws IllegalArgumentException if any AccountTypeOptionModel is null
     * @throws AccountTypeOptionNotFoundException if a AccountTypeOptionModel is not found or marked as deleted
     */
    @Transactional
    public List<AccountTypeOptionModel> updateMany(List<AccountTypeOptionModel> models){
        if (models == null || models.isEmpty()){
            throw new IllegalArgumentException("Account Type option model List cannot be null or empty");
        }
        List<AccountTypeOptionModel> updatedModel = new ArrayList<>();
        for (AccountTypeOptionModel model: models){
            if (model.getId() == null){
                throw new IllegalArgumentException("Account type option id cannot be null");
            }
            AccountTypeOptionModel existingModel = accountTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account type option not found with id:" + model.getId()));
            if (existingModel.getDeletedAt() !=null ){
                throw new AccountTypeOptionNotFoundException("Account type option with id:" + model.getId() + "marked as deleted");
            }
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(accountTypeOptionRepository.save(existingModel));
        }
        return updatedModel;
    }
    /**
     * Updates multiple AccountTypeOptionModel models by their IDs
     *
     * @param accountTypeOptionModels List of AccountTypeOptionModel objects containing updated data
     * @return List of updated AccountTypeOptionModel objects
     * @throws IllegalArgumentException if any Account Type Option ID is null
     * @throws AccountTypeOptionNotFoundException if any AccountTypeOption is not found
     */
    @Transactional
    public List<AccountTypeOptionModel> UpdateAll(List<AccountTypeOptionModel> accountTypeOptionModels){
        if (accountTypeOptionModels == null || accountTypeOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Account type model list cannot be null or empty");
        }
        List<AccountTypeOptionModel> updatedModels = new ArrayList<>();
        for (AccountTypeOptionModel model: accountTypeOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("AccountTypeOptionid cannot be null on Hard update all");
            }
            AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account type option not found with id:" + model.getId()));
            if (accountTypeOptionModel.getDeletedAt() != null){
                throw new AccountTypeOptionNotFoundException("Account type option with id:" + model.getId() + "marked as deleted");
            }
            accountTypeOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(accountTypeOptionRepository.save(accountTypeOptionModel));
        }
        return updatedModels;
    }





}
