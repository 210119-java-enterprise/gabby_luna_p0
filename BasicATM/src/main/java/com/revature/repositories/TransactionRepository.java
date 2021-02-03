package com.revature.repositories;

import com.revature.models.*;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRepository implements CrudRepository{

    //Standard SQL statement -------------------------------------------
    private final String base = "SELECT * " +
                                "FROM transactions ";

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

    //Queries -----------------------------------------------------
    public LinkedList <Transaction> findTransactionsByAccountId(int accountId){
        LinkedList <Transaction> transactions = new LinkedList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = base + "WHERE accountId = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, accountId);

            //Anticipating one or multiple matches
            ResultSet rs = pStmt.executeQuery();
            transactions = mapResultSet(rs);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }

    public double SumTransactions(int accountId){
        double sum = 0;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql ="SELECT SUM(Amount) " +
                        "FROM (SELECT Amount, accountId " +
                                "FROM transactions t " +
                                "GROUP BY transactionid) as b " +
                        "WHERE b.accountid = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, accountId);

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()){
                sum = rs.getDouble(1);
                System.out.println("New balance : " + sum);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return sum;
    }

    //Updates -----------------------------------------------------
    public void insertNewTransaction(TransactionType type, int accountId, double amount){
        System.out.println("New Transaction type: " + type.toString() + ", account: " + accountId + ", amount: " + amount);
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql =    "INSERT INTO Transactions (Transaction_Type, AccountId, Amount)"+
                    "VALUES (?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, type.toString());
            pStmt.setInt(2, accountId);
            pStmt.setDouble(3, amount);

            pStmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    //AccountsRepo Utilities -----------------------------------------------------------
    //Returns a list of BankAccounts that get returned by the SQL query
    private LinkedList<Transaction> mapResultSet (ResultSet rs) throws SQLException {

        LinkedList<Transaction> transactions = new LinkedList<>();

        while (rs.next()){
            Transaction transaction = new Transaction();
            transaction.setTransactionId(rs.getInt("transactionId"));
            transaction.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));
            transaction.setAccountId(rs.getInt("accountId"));
            transaction.setAmount(rs.getDouble("amount"));
            transaction.setDate(rs.getString("transaction_date"));
            transactions.insert(transaction);
        }
        return transactions;
    }
}
