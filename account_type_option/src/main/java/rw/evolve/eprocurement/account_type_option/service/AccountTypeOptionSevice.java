/**
 * Service for managing Account type option entities.
 * Provides functionality to create, read, update, and delete Account type option data.
 */
package rw.evolve.eprocurement.account_type_option.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper = new ModelMapper();
    /**
     * Creates a single Account type option entity with a generated Account type ID.
     *
     * @param @AccountTypeOptionModel the Account type model to be created
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
    public List<AccountTypeOptionModel> readAll(){
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
        modelMapper.map(model, accountTypeOptionModel);
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
            modelMapper.map(model, existingModel);
            existingModel.setUpdatedAt(LocalDateTime.now());
            updatedModel.add(accountTypeOptionRepository.save(existingModel));
        }
        return updatedModel;
    }

    /**
     * Updates a single Account type option model by ID.
     * @param id The ID of the Account type option to update
     * @param model The AccountTypeOptionModel containing updated data
     * @return The updated AccountTypeOptionModel
     * @throws IllegalArgumentException if the Account type option ID is null
     * @throws AccountTypeOptionNotFoundException if the Procurement type option is not found
     */
    @Transactional
    public AccountTypeOptionModel hardUpdateOne(Long id, AccountTypeOptionModel model){
        if (id == null) {
            throw new IllegalArgumentException("Account type Option ID cannot be null or empty");
        }
        if (model == null) {
            throw new IllegalArgumentException("Account type Option model cannot be null");
        }
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account Type Option not found with Id:" + id));

        modelMapper.map(model, accountTypeOptionModel);
        accountTypeOptionModel.setUpdatedAt(LocalDateTime.now());
        return accountTypeOptionRepository.save(accountTypeOptionModel);
    }


    /**
     * Updates multiple AccountTypeOptionModel models by their IDs
     * @param accountTypeOptionModels List of AccountTypeOptionModel objects containing updated data
     * @return List of updated AccountTypeOptionModel objects
     * @throws IllegalArgumentException if any Account Type Option ID is null
     * @throws AccountTypeOptionNotFoundException if any AccountTypeOption is not found
     */
    @Transactional
    public List<AccountTypeOptionModel> hardUpdateAll(List<AccountTypeOptionModel> accountTypeOptionModels){
        if (accountTypeOptionModels == null || accountTypeOptionModels.isEmpty()) {
            throw new IllegalArgumentException("Account type model list cannot be null or empty");
        }
        List<AccountTypeOptionModel> updatedModels = new ArrayList<>();
        for (AccountTypeOptionModel model: accountTypeOptionModels){
            if (model.getId() == null){
                throw new IllegalArgumentException("AccountTypeOption id cannot be null on Hard update all");
            }
            AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(model.getId())
                    .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account type option not found with id:" + model.getId()));

            modelMapper.map(model, accountTypeOptionModel);
            accountTypeOptionModel.setUpdatedAt(LocalDateTime.now());
            updatedModels.add(accountTypeOptionRepository.save(accountTypeOptionModel));
        }
        return updatedModels;
    }

    /**
     * Soft deletes a account type option by ID in a transactional manner.
     *
     * @param id The ID of the account type option to soft delete
     * @return The soft-deleted AccountTypeOptionModel
     * @throws IllegalArgumentException if the account type option ID is null
     * @throws AccountTypeOptionNotFoundException if the account type option is not found
     * @throws IllegalStateException if the account type option is already deleted
     */
    @Transactional
    public AccountTypeOptionModel softDeleteAccountTypeOption(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Account type Option ID cannot be null or empty");
        }
        AccountTypeOptionModel accountTypeOptionModel = accountTypeOptionRepository.findById(id)
                .orElseThrow(()-> new AccountTypeOptionNotFoundException("Account type option not found with id:" + id));
        if (accountTypeOptionModel.getDeletedAt() != null){
            throw new IllegalStateException("Account type option with id:" + id + "is already deleted");
        }
        accountTypeOptionModel.setDeletedAt(LocalDateTime.now());
        return accountTypeOptionRepository.save(accountTypeOptionModel);
    }
    /**
     * Hard deletes a Account type option by ID
     * @param id ID of the Account type option to hard delete
     */
    @Transactional
    public void hardDeleteAccountTypeOption(Long id){
        if (!accountTypeOptionRepository.existsById(id)){
            throw new AccountTypeOptionNotFoundException("Account Type option not found with id:" + id);
        }
        accountTypeOptionRepository.deleteById(id);
    }

    /**
     * Soft deletes multiple account type option by their IDs.
     *
     * @param ids List of account type option IDs to be soft deleted
     * @return List of soft deleted AccountTypeOption objects
     * @throws AccountTypeOptionNotFoundException if any account type option IDs are not found
     * @throws IllegalStateException if any account type option is already deleted
     */
    @Transactional
    public List<AccountTypeOptionModel> softDeleteAccountTypeOptions(List<Long> ids){
        if (ids == null) {
            throw new IllegalArgumentException("Account type option IDs list cannot be null or empty");
        }
        List<AccountTypeOptionModel> accountTypeOptionModels = accountTypeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (AccountTypeOptionModel model: accountTypeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if(!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new AccountTypeOptionNotFoundException("Account Type option not Found with ids:" + missingIds);
        }
        for (AccountTypeOptionModel model: accountTypeOptionModels){
            if (model.getDeletedAt() != null){
                throw new IllegalArgumentException("Account Type option with id:" + model.getId() + "is already deleted");
            }
            model.setDeletedAt(LocalDateTime.now());
        }
        accountTypeOptionRepository.saveAll(accountTypeOptionModels);
        return accountTypeOptionModels;
    }

    /**
     * Hard deletes multiple account type options by IDs
     * @param ids List of Account type option IDs to hard delete
     */
    @Transactional
    public void hardDeleteAccountTypeOptions(List<Long> ids){
        List<AccountTypeOptionModel> accountTypeOptionModels = accountTypeOptionRepository.findAllById(ids);

        List<Long> foundIds = new ArrayList<>();
        for (AccountTypeOptionModel model: accountTypeOptionModels){
            foundIds.add(model.getId());
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long id: ids){
            if (!foundIds.contains(id)){
                missingIds.add(id);
            }
        }
        if (!missingIds.isEmpty()){
            throw new AccountTypeOptionNotFoundException("Procurement type option not found with ids:" +missingIds);
        }
        accountTypeOptionRepository.deleteAllById(ids);
    }





}
