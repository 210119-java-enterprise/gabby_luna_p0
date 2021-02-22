package com.revature.services;

import com.revature.Boxed.service.BlackBox;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import com.revature.utilities.Map;

import java.util.List;

/**
 * Class responsible for communicating between the Screens and the TransactionRepository
 * Also contains its own Transaction Repository
 * <p>
 * @author Gabrielle Luna
 */
public class TransactionService {

    //Copy of Repo ----------------------------------------
    private final BlackBox box;

    //Constructors ----------------------------------------
    /**
     * Only necessary constructor. Saves a copy of the TransactionRepository for
     * future use.
     */
    public TransactionService(BlackBox box){
        this.box = box;
    }

    //Database Accesses -----------------------------------
    /**
     * Used to generate a list of transactions belonging to an account
     * @param accountId    used to find relevant transactions
     */
    public Map<Integer, Transaction> getTransactions(int accountId){
        Map<Integer, Transaction> transactionMap = new Map<>();
        List<Transaction> result = null;

        result = box.getObjsMatchingId("accountId", Integer.toString(accountId),
                false, new Transaction());

        if (result != null){
            for (Transaction transaction: result){
                transactionMap.put(transaction.getTransactionId(), transaction);
            }
        }
        return transactionMap;
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
        Transaction newTransaction = new Transaction(type, accountId, amount);
        box.insert(newTransaction);
    }

    /**
     * Used to sum up the transactions belonging to one account
     * @param accountId     used to find appropriate transactions
     * @return              returns the account balance according to all transactions for that account
     */
    public double getTransactionSum(int accountId){
        double sum = 0;
        List<Transaction> result = null;
        result = box.getObjsMatchingId("accountId", Integer.toString(accountId),
                false, new Transaction());

        if (result != null){
            for (Transaction transaction : result){
                if (transaction.getTransactionType().equals(TransactionType.DEBIT))
                    sum -= transaction.getAmount();
                else
                    sum += transaction.getAmount();
                sum = Math.round(sum * 100.0)/100.0;
            }
        }
        return sum;
    }

    public void deleteTransactions(int accountId){
        box.deleteEntry(new Transaction(), "accountId", Integer.toString(accountId),
                false);
    }
}
