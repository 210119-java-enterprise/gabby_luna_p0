package com.revature.models;

import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import java.util.Currency;

import static com.revature.utilities.ConsoleDecoration.*;

public class BankAccount {
    private int accountId;
    private AccountType accountType;
    private int userId;
    private double balance;
    private Map<Integer, Transaction> transactions;
    private int numTransactions;

    public void BankAccount(int accountId, AccountType accountType){
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = 0;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void updateBalance() {
    }

    public Map<Integer, Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<Integer, Transaction> transactions) {
        this.transactions = transactions;
        if (transactions != null)
            numTransactions = transactions.getSize();
        else
            numTransactions = 0;
    }

    public void addTransaction(Transaction transaction){
        transactions.put(transaction.getTransactionId(), transaction);
    }

    public int getNumTransactions() {
        return numTransactions;
    }

    public void printAccount(String color) {
        String message = " Account : " + accountId + "    Type: " + accountType.toString();
        FinishLine((ANSI_BLUE + message + ANSI_RESET), message.length());

        message = "     Balance : $";
        String bal = String.format("%.2f", balance);
        FinishLine((ANSI_BLUE + message + ANSI_GREEN + bal + ANSI_RESET), message.length() + bal.length());
    }
}
