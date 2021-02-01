package com.revature.repositories;

import com.revature.models.AccountType;
import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;
import sun.awt.image.ImageWatched;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository implements CrudRepository{

    //Standard SQL statement -------------------------------------------
    private final String base = "SELECT * " +
                                "FROM accounts ";

    //Overrides --------------------------------------------------------
    @Override
    public void save(Object newObj) {
        System.err.println("Not implemented");
    }

    @Override
    public LinkedList findAll() {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public Object findById(int id) {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public boolean update(Object updatedObj) {
        System.err.println("Not implemented");
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        System.err.println("Not implemented");
        return false;
    }

    //Other Database Calls --------------------------------------------------
    public LinkedList <BankAccount> findAccountsByUserId(int userId){
        LinkedList <BankAccount> accounts = new LinkedList<>();

        AppUser user = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = base + "WHERE userId = ?";
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

    //AccountsRepo Utilities -----------------------------------------------------------
    //Returns a list of BankAccounts that get returned by the SQL query
    private LinkedList<BankAccount> mapResultSet (ResultSet rs) throws SQLException {

        LinkedList<BankAccount> accounts = new LinkedList<>();

        while (rs.next()){
            BankAccount account = new BankAccount();
            account.setAccountId(rs.getInt("accountId"));
            account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
            account.setUserId(rs.getInt("userId"));
            account.setBalance(rs.getDouble("balance"));
            accounts.insert(account);
        }
        return accounts;
    }
}
