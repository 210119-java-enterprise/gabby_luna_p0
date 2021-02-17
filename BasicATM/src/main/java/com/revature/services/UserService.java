package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.repositories.UserRepository;
import com.revature.service.BlackBox;
import com.revature.service.QueryBuilder;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.Session;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.ANSI_RED;
import static com.revature.utilities.ConsoleDecoration.ANSI_RESET;

/**
 * Class responsible for communicating between the Screens and the UserRepository
 * Also contains its own UserRepository
 * <p>
 * @author Gabrielle Luna
 */
public class UserService {
    //Copy of Repo ----------------------------------------
    private final BlackBox box;
    QueryBuilder builder = new QueryBuilder();

    //private final UserRepository userRepo;

    //Constructors ----------------------------------------
    /**
     * Only necessary constructor. Saves a copy of the application's shared copy of the ORM
     *
     * @param box  application copy of ORM
     */
    public UserService(BlackBox box){
        this.box = box;
    }

    //Database Accesses -----------------------------------
    /**
     * Used to authenticate a users username and password input, rejecting empty or null strings
     * @param username      used for user login once authenticated
     * @param password      used for user login once authenticated
     * @throws AuthenticationException  thrown if username password combo is not found in database
     */
    public void authenticate(String username, String password)
            throws AuthenticationException{
        try {
            //Initialize box connection
            box.setCurrentConnection(box.getConnection());

            //write SQL
            String query = builder.craftNewTransaction()
                    .returnFields()
                    .ofClassType("users")
                    .addCondition_Operator("username", "=", username, true)
                    .addCondition_Operator("user_password", "=", password, true)
                    .getQuery();

            //execute Query
            box.runQuery(query);

            //retrieve User
            List<AppUser> matches = box.getResultInClass(AppUser.class);
            if (matches.size() == 0)
                throw new AuthenticationException(ANSI_RED + "Invalid login credentials..." + ANSI_RESET);
            AppUser authUser = matches.get(0);

            //Start new session
            app().setCurrentSession(new Session(authUser, box));
        }catch (IllegalArgumentException e){
            throw new AuthenticationException(ANSI_RED + "Invalid login credentials..." + ANSI_RESET);
        }catch (SQLException | ConnectException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user to the database. Requires a first and last name along with a username and pass
     * @param firstName     Users first name
     * @param lastName      Users last name
     * @param username      Users chosen username
     * @param password      Users chosen password
     * @return boolean      true is successful
     * @throws AuthenticationException  thrown if username and password combo are unavailable
     */
    public boolean register(String firstName, String lastName, String username, String password) throws AuthenticationException {
        //Reject if there are any empty strings
        if (    firstName == null || firstName.trim().equals("") ||
                lastName == null || lastName.trim().equals("") ||
                username == null || username.trim().equals("") ||
                password == null || password.trim().equals("")) {
            throw new InvalidRequestException(ANSI_RED + "Invalid credentials, no empty fields permitted!" + ANSI_RESET);
        }

        //Check if Username password combo exists
//        AppUser authUser = userRepo.findUserByUsernameAndPassword(username, password);
//        if (authUser == null){
//            //Username password combo is available
//            userRepo.createNewUser(firstName, lastName, username, password);
//        }else{
//            throw new AuthenticationException(ANSI_RED + "Username and password combo unavailable!" + ANSI_RESET);
//        }

        System.out.println("Registration successful!");
        return true;
}
}
