package com.revature.models;

import static com.revature.utilities.ConsoleDecoration.*;

public class Transaction {
    private int transactionId;
    private TransactionType transactionType;
    private int accountId;
    private double amount;
    private String date;


    public void Transaction(int transactionId, TransactionType transactionType, double amount){
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = 0;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void printTransaction(String color) {
        String message = "Transaction : " + transactionId + "    Date : " + date + "    Amount : $" ;
        String t_amount = String.format("%.2f", amount);
        String type = (transactionType == TransactionType.CREDIT)? ANSI_GREEN : ANSI_RED;

        FinishLine((color + message + type + amount + ANSI_RESET), message.length() + t_amount.length());
    }
}
