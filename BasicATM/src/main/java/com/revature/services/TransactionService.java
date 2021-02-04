package com.revature.services;

import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import com.revature.repositories.TransactionRepository;
import com.revature.utilities.Map;

/**
 * Class responsible for communicating between the Screens and the TransactionRepository
 * Also contains its own Transaction Repository
 * <p>
 * @author Gabrielle Luna
 */
public class TransactionService {

    //Copy of Repo ----------------------------------------
    private final TransactionRepository transRepo;

    //Constructors ----------------------------------------
    /**
     * Only necessary constructor. Saves a copy of the TransactionRepository for
     * future use.
     * @param transRepo  stores a private instance of the repo
     */
    public TransactionService(TransactionRepository transRepo){
        this.transRepo = transRepo;
    }

    //Database Accesses -----------------------------------
    /**
     * Used to generate a list of transactions belonging to an account
     * @param accountId    used to find relevant transactions
     */
    public Map<Integer, Transaction> getTransactions(int accountId){
        return transRepo.findTransactionsByAccountId(accountId);
    }

    /**
     * Used to save a new account to the database. Account type and
     * UserId are the only values without a default
     * <p>
     * @param type          records the type of transaction being added
     * @param amount        records the amount being exchanged
     * @param accountId     records which account is used in the transaction
     */
    public void addNewTransaction(TransactionType type, double amount, int accountId){
        transRepo.insertNewTransaction(type, accountId, amount);
    }

    /**
     * Used to sum up the transactions belonging to one account
     * @param accountId     used to find appropriate transactions
     * @return              returns the account balance according to all transactions for that account
     */
    public double getTransactionSum(int accountId){
        return transRepo.SumTransactions(accountId);
    }
}
