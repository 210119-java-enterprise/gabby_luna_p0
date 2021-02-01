package com.revature.repositories;

import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;

import java.sql.*;
import static com.revature.ATM.app;

public class UserRepository implements CrudRepository<AppUser>{

    //Standard SQL statement -------------------------------------------
    private final String base = "SELECT * " +
                                "FROM users ";

    //Overrides --------------------------------------------------------
    @Override
    public void save(AppUser newObj) {
        System.err.println("Not implemented");
    }

    @Override
    public LinkedList<AppUser> findAll() {

        System.err.println("Not implemented");
        return null;
    }

    @Override
    public AppUser findById(int id) {

        System.err.println("Not implemented");
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {

        System.err.println("Not implemented");
        return false;
    }

    @Override
    public boolean deleteById(int id) {

        System.err.println("Not implemented");
        return false;
    }

    //Other Database Calls --------------------------------------------------
    public AppUser findUserByUsernameAndPassword(String username, String password){
        AppUser user = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = base + "WHERE username = ? AND user_password = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            pStmt.setString(2, password);

            //Anticipating only one matching return for Result Set
            ResultSet rs = pStmt.executeQuery();
            user = mapResultSet(rs).pop();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    //UserRepo Utilities -----------------------------------------------------------
    //Returns a list of App Users that get returned by the SQL query
    private LinkedList<AppUser> mapResultSet (ResultSet rs) throws SQLException {

        LinkedList<AppUser> users = new LinkedList<>();

        while (rs.next()){
            AppUser user = new AppUser();
            user.setId(rs.getInt("userId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("User_Password"));
            users.insert(user);
        }
        return users;
    }
}
