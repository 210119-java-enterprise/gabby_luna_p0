package com.revature.repositories;

import com.revature.models.AppUser;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;

import java.sql.*;

/**
 * Provides access to the database for User objects. Implements the CrudRepository
 * to allow for a set of standardized database calls, non of which are currently used.
 * Crud Repository will remain as a potential tool for future expansion
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public class UserRepository implements CrudRepository<AppUser>{

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

    //Queries ------------------------------------------------------

    /**
     * Calls database with a query looking for all accounts matching the provided username and
     * password. Utilizes the mapResultSet method to convert the results into a returnable AppUser
     * object. Breaks if users are allowed to have a username password combo that matches another
     * Users as it anticipates only one user to be returned from the mapResultSet method
     * <p>
     * @param username      string holding user's inputted username (must previously validate as not null and not empty)
     * @param password      string holding user's inputted password (must previously validate as not null and not empty)
     * @return AppUser      may return null or validated AppUser
     */
    public AppUser findUserByUsernameAndPassword(String username, String password){
        AppUser user = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql ="SELECT * " +
                        "FROM users " +
                        "WHERE username = ? AND user_password = ?";
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

    //Updates ------------------------------------------------------
    /**
     * Calls database with an Update to insert a new User. Requires the new User's first and last name,
     * username and password. the username password selection chosen should have been validated as unique
     * before calling this method
     * <p>
     * @param firstName     string holding Users First Name
     * @param lastName      string holding Users Last Name
     * @param username      string holding the Users chosen username
     * @param password      string holding the Users chosen password
     * @return AppUser      via the findUserByUsernameAndPassword method.
     */
    public AppUser createNewUser(String firstName, String lastName, String username, String password){
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql =    "insert into Users " +
                            "(firstname, lastname, username, user_password) " +
                            "values " +
                            "(?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setString(3, username);
            pStmt.setString(4, password);

            pStmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return findUserByUsernameAndPassword(username, password);
    }

    //UserRepo Utilities -----------------------------------------------------------

    /**
     * Used to convert between a Result Set and a LinkedList holding Users,
     * throws a SQLException if rs iterating throws one.
     * <p>
     * @param rs                Result set holding desired User
     * @return Linked List      holding desired User
     * @throws SQLException     thrown by result set navigation
     */
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
