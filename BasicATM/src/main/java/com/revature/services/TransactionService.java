package com.revature.services;

import com.revature.models.AccountType;
import com.revature.models.BankAccount;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.utilities.LinkedList;

public class TransactionService {

    //Copy of Repo ----------------------------------------
    private TransactionRepository transRepo;

    //Constructors ----------------------------------------
    public TransactionService(TransactionRepository transRepo){
        this.transRepo = transRepo;
    }

    public LinkedList<Transaction> getTransactions(int accountId){
        return transRepo.findTransactionsByAccountId(accountId);
    }

    public void addNewTransaction(TransactionType type, double amount, int accountId){
        transRepo.insertNewTransaction(type, accountId, amount);
    }

    public double getTransactionSum(int accountId){
        double sum = transRepo.SumTransactions(accountId);
        System.out.println("getTransactionSum.sum = " + sum);
        return sum;
    }
}
