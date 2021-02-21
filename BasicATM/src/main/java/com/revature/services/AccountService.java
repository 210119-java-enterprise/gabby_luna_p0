package com.revature.services;

import com.revature.models.AccountType;
import com.revature.models.BankAccount;
import com.revature.repositories.AccountRepository;
import com.revature.service.BlackBox;
import com.revature.utilities.Map;

import java.util.List;

/**
 * Class responsible for communicating between the Screens and the AccountRepository
 * Also contains its own AccountRepository object
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public class AccountService {

    //Copy of Repo ----------------------------------------
    private final BlackBox box;
    private final AccountRepository acctRepo;

    //Constructors ----------------------------------------
    /**
     * Only necessary constructor. Saves a copy of the AccountRepository for
     * future use.
     * @param acctRepo  stores a private instance of the repo
     */
    public AccountService(AccountRepository acctRepo, BlackBox box){
        this.box = box;
        this.acctRepo = acctRepo;
    }

    //Database Accesses -----------------------------------
    /**
     * Used to generate a list of accounts belonging to a user
     * @param userId    used to find relevant accounts
     */
    public Map<Integer, BankAccount> getAccounts(int userId){
        Map<Integer, BankAccount> bankAccountMap = new Map<>();
        List<BankAccount> result = null;

        result = box.getObjsMatchingId("userId", Integer.toString(userId),
                   false, new BankAccount());


        if (result != null ){
            for (BankAccount account : result)
                bankAccountMap.put(account.getAccountId(), account);
        }
        return bankAccountMap;
    }

    /**
     * Used to save a new account to the database. Account type and
     * UserId are the only values without a default
     * <p>
     * @param type      records the type of account being added
     * @param userId    records which user owns the account
     */
    public void addNewAccount(AccountType type, int userId){
        BankAccount newAccount = new BankAccount(type, userId);
        box.insert(newAccount);
    }

    /**
     * Used to update the balance value of an account
     * @param accountId     used to find appropriate account
     * @param newBalance    updated balance to be saved
     * @return              returns updated balance so it can be recorded locally
     */
    public double updateBalance(int accountId, double newBalance){
        return acctRepo.updateAccountBalance(accountId, newBalance);
    }
}
