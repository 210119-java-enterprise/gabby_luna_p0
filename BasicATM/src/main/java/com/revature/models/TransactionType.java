package com.revature.models;
/**
 * Transaction types that can be used, determines wether an amount
 * is added or subtracted from account balance.
 * <p>
 * @author Gabrielle Luna
 */
public enum TransactionType {
    /**
     * Account withdrawal made
     */
    DEBIT,
    /**
     * Account deposit made
     */
    CREDIT
}
