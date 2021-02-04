package com.revature.repositories;

import com.revature.models.*;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;
import com.revature.utilities.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides access to the database for Transaction objects. Implements the CrudRepository
 * to allow for a set of standardized database calls, non of which are currently used.
 * Crud Repository will remain as a potential tool for future expansion
 * <p>
 * @author Gabrielle Luna
 */
public class TransactionRepository implements CrudRepository <Transaction>{

    //Overrides --------------------------------------------------------
    @Override
    public void save(Transaction newTransaction) {
        System.err.println("Not implemented");
    }

    @Override
    public LinkedList<Transaction> findAll() {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public Transaction findById(int id) {
        System.err.println("Not implemented");
        return null;
    }

    @Override
    public boolean update(Transaction updatedTransaction) {
        System.err.println("Not implemented");
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        System.err.println("Not implemented");
        return false;
    }

    //Queries -----------------------------------------------------
    /**
     * Calls database with a Query looking for all transactions matching the provided accountId. Utilizes
     * the mapResultSet method to convert the results into a returnable Map of transaction ids
     * and their corresponding Transactions
     * <p>
     * @param accountId     used to find relevant transactions
     * @return Map          containing transaction Ids and their corresponding accounts, may be empty
     */
    public Map<Integer, Transaction> findTransactionsByAccountId(int accountId){
        Map <Integer, Transaction> transactions = new Map<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql ="SELECT * " +
                        "FROM transactions " +
                        "WHERE accountId = ?";
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

    /**
     * Calls database with a Query looking for all transactions with an accountId matching the parameter
     * and then summing their values. As only one value will be returned so the ResultSet is parsed within
     * the method.
     * <p>
     * @param accountId     Used to decide what accounts transactions to sum
     * @return double       containing the sum calculated
     */
    public double SumTransactions(int accountId){
        double sum = 0;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql ="SELECT SUM(Amount) " +
                        "FROM (SELECT Amount, accountId " +
                                "FROM transactions t) as b " +
                        "WHERE b.accountId = ?";
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

    /**
     * Calls the database with an Update to insert a new transaction. Requires the Transaction type,
     * relevant accountId, and amount for that transaction. The transactions id and date will be set by
     * the database. Date will be set to current date.
     * <p>
     * @param type          The transaction type, used for color coding and value sign adjustment
     * @param accountId     Transaction needs a reference to the relevant account
     * @param amount        All transactions need a value
     */
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

    /**
     * Used to convert between a Result Set and a Map holding transaction ids and corresponding transactions
     * throws a SQLException if rs iterating throws one.
     * <p>
     * @param rs                holds the database query result
     * @return                  holding the transactionId and corresponding Transactions
     * @throws SQLException     thrown by result set navigation
     */
    private Map<Integer, Transaction> mapResultSet (ResultSet rs) throws SQLException {

        Map<Integer, Transaction> transactions = new Map<>();

        while (rs.next()){
            Transaction transaction = new Transaction();
            transaction.setTransactionId(rs.getInt("transactionId"));
            transaction.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));
            transaction.setAccountId(rs.getInt("accountId"));
            transaction.setAmount(rs.getDouble("amount"));
            transaction.setDate(rs.getString("transaction_date"));
            transactions.put(transaction.getTransactionId(), transaction);
        }
        return transactions;
    }
}
