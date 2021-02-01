package com.revature.models;

import java.util.Currency;

import static com.revature.utilities.ConsoleDecoration.ANSI_RESET;

public class BankAccount {
    private int accountId;
    private AccountType accountType;
    private int userId;
    private double balance;


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

    public void printAccount(String color) {
        System.out.println("/* " + color + "Account : " + accountId + "\tType: " + accountType.toString()
                + ANSI_RESET +"                                           */");
        System.out.println("/* " + color + "Balance : $" + String.format("%.2f", balance) + ANSI_RESET
                +"                                                      */");
    }
}
