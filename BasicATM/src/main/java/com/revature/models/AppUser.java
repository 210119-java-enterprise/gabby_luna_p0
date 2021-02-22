package com.revature.models;

import com.revature.Boxed.annotations.*;
import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import java.util.Objects;

import static com.revature.Boxed.model.ColumnType.DEFAULT;
import static com.revature.Boxed.model.ColumnType.PK;

/**
 * Describes an object representing a user, their personal information,
 * login information and a map of their accounts.
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
@Entity(tableName = "users")
@CredentialsClass
public class AppUser {

    //User info ------------------------------------
    @Column(type = PK, columnName = "userid")
    @Generated
    private int id;

    @Column(type = DEFAULT, columnName = "firstname")
    private String firstName;
    @Column(type = DEFAULT, columnName = "lastname")
    private String lastName;

    @Column(type = DEFAULT, columnName = "username")
    @Credential
    private String username;
    @Column(type = DEFAULT, columnName = "user_password")
    @Credential
    private String password;

    private Map<Integer, BankAccount> accounts;
    private int numAccounts;

    //Constructors ---------------------------------

    /**
     * Creates an empty user
     */
    public AppUser() {
        super();
    }

    /**
     * Creates a user with the minimum information required. UserId and the list of
     * accounts are uninitialized, numAccounts is set to zero.
     * <p>
     * @param firstName     User's first name
     * @param lastName      User's last name
     * @param username      User's chosen username
     * @param password      User's chosen password
     */
    public AppUser(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.numAccounts = 0;
    }

    // Getters and Setters -------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Used to get a list of the users active account ids for quick searches and validation
     * <p>
     * @return LinkedList of account Id integers
     */
    public LinkedList<Integer> getAccounts() {
        return accounts.keyList();
    }

    /**
     * Used to retrieve an account via accountId. Calls the Map method get() on the list of
     * accounts using the account id.
     * <p>
     * @param accountId     for desired account
     * @return BankAccount  object containing all details regarding desired account
     */
    public BankAccount getBankAccount(int accountId){
        if (accounts == null) return null;
        return accounts.get(accountId);
    }

    /**
     * Used to update the Users internal list of accounts, with simple validation before
     * updating the users number of accounts
     * <p>
     * @param accounts  Map containing account ids and corresponding accounts
     */
    public void setAccounts(Map<Integer, BankAccount> accounts) {
        this.accounts = accounts;
        if (accounts != null)
            this.numAccounts = accounts.getSize();
        else
            numAccounts = 0;
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    //Overrides----------------------------------------

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password);
    }

    //Need to override .equals to avoid == default
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        //Must be safe to cast to AppUser Object
        AppUser appUser = (AppUser) obj;
        return  id == appUser.id &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
