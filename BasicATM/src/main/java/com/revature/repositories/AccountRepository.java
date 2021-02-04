package com.revature.repositories;

import com.revature.models.AccountType;
import com.revature.models.BankAccount;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides access to the database for Account objects. Implements the CrudRepository
 * to allow for a set of standardized database calls, none of which are currently used. CrudRepository
 * will remain as a potential tool for future expansion.
 * <p>
 * @author Gabrielle Luna
 */
public class AccountRepository implements CrudRepository <BankAccount>{

    //Overrides --------------------------------------------------------
    @Override
    public void save(BankAccount newAccount) {
        System.err.println("Not implemented");
    }

    @Override
    public LinkedList<BankAccount> findAll() {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public BankAccount findById(int id) {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public boolean update(BankAccount updatedAccount) {
        System.err.println("Not implemented");
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        System.err.println("Not implemented");
        return false;
    }

    //Queries  -----------------------------------------------------
    /**
     * Calls database with a query looking for all accounts matching the provided userId. Utilizes
     * the mapResultSet method to convert the results into a returnable Map of account ids
     * and their corresponding BankAccounts.
     * <p>
     * @param userId    used to find relevant accounts
     * @return Map      containing account Ids and their corresponding accounts, may be empty
     */
    public Map <Integer, BankAccount> findAccountsByUserId(int userId){
        Map <Integer, BankAccount> accounts = new Map<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql ="SELECT * " +
                        "FROM accounts " +
                        "WHERE userId = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, userId);

            //Anticipating one or multiple matches
            ResultSet rs = pStmt.executeQuery();
            accounts = mapResultSet(rs);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    //Updates  -----------------------------------------------------
    /**
     * Calls database with an Update to insert a new account. Requires only the account type and
     * account's owner id. The balance is initialized to 0, date and account Id are set by the database
     * <p>
     * @param type      saved as string in database
     * @param userId    Required for lookups later
     */
    public void insertNewAccount(AccountType type, int userId){
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql =    "INSERT INTO Accounts (Account_Type, UserId, Balance)"+
                            "VALUES (?, ?, 0.00)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, type.toString());
            pStmt.setInt(2, userId);

            pStmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Calls database with an Update to update the account's balance. Balance must be calculated in
     * transactionRepository and sent in as a parameter.
     * <p>
     * @param accountId     used to identify account getting updated
     * @param newBalance    a double holding new account balance value
     * @return              double holding the updated account balance, value allows you to update Account
     *                      object with return value.
     */
    public double updateAccountBalance(int accountId, double newBalance){
        System.out.println("Updating account : " + accountId + " to balance : " + newBalance);

        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql =    "UPDATE Accounts " +
                            "SET Balance = ? " +
                            "WHERE AccountId = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setDouble(1, newBalance);
            pStmt.setInt(2, accountId);

            pStmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return newBalance;
    }

    //AccountsRepo Utilities -----------------------------------------------------------

    /**
     * Used to convert between a Result Set and a Map holding account Ids and corresponding BankAccounts
     * throws a SQLException if rs iterating throws one.
     * <p>
     * @param rs                holds the database query result
     * @return Map              holding the accountIds and corresponding BankAccounts
     * @throws SQLException     thrown by result set navigation.
     */
    private Map<Integer, BankAccount> mapResultSet (ResultSet rs) throws SQLException {

        Map<Integer, BankAccount> accounts = new Map<>();

        while (rs.next()){
            BankAccount account = new BankAccount();
            account.setAccountId(rs.getInt("accountId"));
            account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
            account.setUserId(rs.getInt("userId"));
            account.setBalance(rs.getDouble("balance"));
            accounts.put(account.getAccountId(), account);
        }
        return accounts;
    }
}
