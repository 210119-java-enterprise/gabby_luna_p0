package com.revature.models;

import com.revature.Boxed.annotations.Column;
import com.revature.Boxed.annotations.Entity;
import com.revature.Boxed.annotations.Generated;

import java.text.DecimalFormat;

import static com.revature.Boxed.model.ColumnType.*;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Describes an object representing a Transaction, the related transaction information.
 * <p>
 * @author Gabrielle Luna
 */
@Entity(tableName = "transactions")
public class Transaction {

    //Transaction info ------------------------------------
    @Column(type = PK, columnName = "transactionId")
    @Generated
    private int transactionId;
    @Column(type = DEFAULT, columnName = "transaction_type")
    private String transactionType;
    @Column(type = FK, columnName = "accountId")
    private int accountId;
    @Column(type = DEFAULT, columnName = "amount")
    private double amount;
    @Column(type = DEFAULT, columnName = "transaction_date")
    @Generated
    private String date;

    //Constructors ---------------------------------

    /**
     * Creates an empty Transaction
     */
    public Transaction() {
    }

    /**
     * Creates a Transaction with the minimum amount of required information. Transaction id and
     * the transaction date is uninitialized. Amount is made negative or positive based on transaction
     * type.
     * <p>
     * @param transactionType   required to determine whether the amount is negative or positive
     * @param accountId         required to tie the transaction to an account
     * @param amount            required to create a transaction, expected to be positive regardless of type
     */
    public Transaction(TransactionType transactionType, int accountId, double amount){
        this.transactionType = transactionType.toString();
        this.accountId = accountId;
        this.amount = (transactionType == TransactionType.CREDIT)? amount : amount * -1;
    }

    // Getters and Setters -------------------------
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return TransactionType.valueOf(transactionType);
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType.toString();
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

    //Bank Account utilities --------------------------------------

    /**
     * Used to print out a transaction summary using the console decoration class.
     * A transaction is printed on one line within left and right boundaries. The line
     * has the transaction id, transaction type, and transaction amount formatted to
     * match US currency standards and color coded to indicate transaction type.
     * <p>
     * @param color
     */
    public void printTransaction(String color) {
        String message = " Date : " + date + "    Amount : $" ;
        DecimalFormat format = new DecimalFormat("#,###.00");
        String t_amount = format.format(amount);
        String type = (TransactionType.valueOf(transactionType) == TransactionType.CREDIT)? GREEN_BOLD_BRIGHT : RED_BOLD_BRIGHT;

        FinishLine((color + message + type + t_amount + ANSI_RESET), message.length() + t_amount.length());
    }
}
