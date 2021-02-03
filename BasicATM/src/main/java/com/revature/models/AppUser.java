package com.revature.models;

import com.revature.repositories.UserRepository;

import com.revature.services.AccountService;
import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import java.util.Objects;

/*
    Holds and object representing the user and their personal information
    from the User table to avoid multiple calls to database.
 */
public class AppUser {

    //User info ------------------------------------
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Map<Integer, BankAccount> accounts;
    private int numAccounts;

    //Constructors ---------------------------------
    public AppUser() {
        super();
    }

    //Constructor with all but an id
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

    public LinkedList<Integer> getAccounts() {
        return accounts.keyList();
    }

    public BankAccount getBankAccount(int accountId){
        if (accounts == null) return null;

        return accounts.get(accountId);
    }

    public void setAccounts(Map<Integer, BankAccount> accounts) {
        this.accounts = accounts;
        this.numAccounts = accounts.getSize();
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
