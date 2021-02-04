package com.revature.models;

import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Describes an object representing a Bank Account, the related account information,
 * and a map of its transactions.
 * <p>
 * @author Gabrielle Luna
 */
public class BankAccount {

    //Account info ------------------------------------
    private int accountId;
    private AccountType accountType;
    private int userId;
    private double balance;
    private Map<Integer, Transaction> transactions;
    private int numTransactions;

    public final double ACCOUNT_MAX = 1000000000000.00;

    //Constructors ---------------------------------
    /**
     * Creates an empty Account
     */
    public BankAccount() {
    }

    /**
     * Creates an account with the minimum amount of required information. Account id and the list of
     * transactions is uninitialized, numTransactions and balance are set to zero.
     * <p>
     * @param accountType       requires an input from the AccountType enum
     * @param userId            required to tie the account to a specific user
     *
     */
    public BankAccount(AccountType accountType, int userId){
        this.accountType = accountType;
        this.userId = userId;
        this.balance = 0;
    }

    // Getters and Setters -------------------------
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

    /**
     * Used to get a list of the accounts transaction ids for quick searches and validation
     * <p>
     * @return LinkedList of transaction Id integers
     */
    public LinkedList<Integer> getAccountTransactions() {
        return transactions.keyList();
    }

    /**
     * Used to retrieve a transaction via transactionId. Calls the Map method get() on the list of
     * transactions using the transaction id.
     * <p>
     * @param transactionId     for desired transaction
     * @return Transaction  object containing all details regarding desired transaction
     */
    public Transaction getTransaction(int transactionId) {
        if (transactions == null) return null;

        return transactions.get(transactionId);
    }

    /**
     * Used to update the Accounts internal list of transactions, with simple validation, before
     * updating the accounts number of transactions
     * <p>
     * @param transactions  Map containing transaction ids and corresponding transactions
     */
    public void setTransactions(Map<Integer, Transaction> transactions) {
        this.transactions = transactions;
        if (transactions != null)
            numTransactions = transactions.getSize();
        else
            numTransactions = 0;
    }

    public int getNumTransactions() {
        return numTransactions;
    }

    public double getAccountMax() {
        return ACCOUNT_MAX;
    }

    //Bank Account utilities --------------------------------------

    /**
     * Used to print out a bank account summary using the console decoration class.
     * An account is printed on two lines within left and right boundaries. The first line
     * has the account id and account type. The second line has the account balance formatted to
     * match US currency standards.
     *
     * @param color     Used to color code all account information aside from balance to match screen color
     */
    public void printAccount(String color) {
        String message = " Account : " + accountId + "    Type: " + accountType.toString();
        FinishLine((color + message + ANSI_RESET), message.length());

        message = "     Balance : $";
        String bal = String.format("%.2f", balance);
        FinishLine((color + message + ANSI_GREEN + bal + ANSI_RESET), message.length() + bal.length());
    }
}
