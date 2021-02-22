package com.revature.models;

import com.revature.Boxed.annotations.Column;
import com.revature.Boxed.annotations.Entity;
import com.revature.Boxed.annotations.Generated;
import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import java.text.DecimalFormat;

import static com.revature.Boxed.model.ColumnType.*;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Describes an object representing a Bank Account, the related account information,
 * and a map of its transactions.
 * <p>
 * @author Gabrielle Luna
 */

@Entity(tableName = "accounts")
public class BankAccount {

    //Account info ------------------------------------

    @Column(type = PK, columnName = "accountid")
    @Generated
    private int accountId;
    @Column(type = DEFAULT, columnName = "account_type")
    private String accountType;
    @Column(type = FK, columnName = "userid")
    private int userId;
    @Column(type = DEFAULT, columnName = "balance")
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
        this.accountType = accountType.toString();
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
        return AccountType.valueOf(accountType);
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType.toString();
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
    public void printAccount(java.lang.String color) {
        java.lang.String message = " Account : ";
        java.lang.String message2 = "" + accountId;
        java.lang.String message3 = "    Type: " + accountType.toString();
        FinishLine((color + message + ANSI_RESET + message2 + color + message3 + ANSI_RESET),
                                message.length() + message2.length() + message3.length());

        message = "     Balance : $";
        //String bal = String.format("%.2f", balance);
        DecimalFormat format = new DecimalFormat("#,###.00");
        java.lang.String bal = format.format(balance);
        FinishLine((color + message + GREEN_BOLD_BRIGHT + bal + ANSI_RESET), message.length() + bal.length());
    }
}
