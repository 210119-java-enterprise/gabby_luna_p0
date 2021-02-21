package com.revature.models;
/**
 * Account types that can be used, no functionality difference thus far
 * Potential interest options available for the future
 * <p>
 * @author Gabrielle Luna
 */
public enum AccountType {
    /**
     *Checking account
     */
    CHK,
    /**
     * Savings account
     */
    SVG,
    /**
     * Money Market Account
     */
    MMA,
    /**
     * Certificates of Deposit
     */
    CD,
    /**
     * Individual Retirement Account
     */
    IRA;

    public java.lang.String toLongName() {
        switch(this){
            case CHK: return "Checking Account";
            case SVG: return "Saving Account";
            case MMA: return "Money Market Account";
            case CD: return "Certificates of Deposit";
            case IRA: return "Individual Retirement Account";
            default: throw new IllegalArgumentException();
        }
    }
}
