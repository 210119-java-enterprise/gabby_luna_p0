package com.revature.services;

import com.revature.models.AccountType;
import com.revature.models.BankAccount;
import com.revature.repositories.AccountRepository;
import com.revature.utilities.LinkedList;

public class AccountService {
    //Copy of Repo ----------------------------------------
    private AccountRepository acctRepo;


    //Constructors ----------------------------------------
    public AccountService(AccountRepository acctRepo){
        this.acctRepo = acctRepo;
    }

    public LinkedList<BankAccount> getAccounts(int userId){
        return acctRepo.findAccountsByUserId(userId);
    }

    public void addNewAccount(AccountType type, int userId){
        acctRepo.insertNewAccount(type, userId);

    }
}
