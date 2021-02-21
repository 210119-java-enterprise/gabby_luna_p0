package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.service.BlackBox;
import com.revature.utilities.Session;

import java.net.ConnectException;
import java.sql.SQLException;

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
            throws IllegalArgumentException, AuthenticationException{
        try {
            //Initialize box connection
            box.setCurrentConnection(box.getConnection());

            //Authenticate user
            AppUser authUser = box.authenticateLogin(AppUser.class, username, password);

            if (authUser == null)
                throw new AuthenticationException();

            //Start new session
            app().setCurrentSession(new Session(authUser, box));

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
    public boolean register(String firstName, String lastName, String username, String password) throws InvalidRequestException {
        try{
            authenticate(username, password);
            throw new InvalidRequestException(ANSI_RED + "Username and password combo unavailable!" + ANSI_RESET);
        }catch (IllegalArgumentException e){
            throw new InvalidRequestException(ANSI_RED + "Invalid credentials, only alphanumeric and _ permitted. All values must begin with a letter or _" + ANSI_RESET);
        }catch (AuthenticationException e){
            System.out.println("create new user");
        }

        try{
            AppUser newUser = new AppUser(firstName, lastName, username, password);
            if (box.insert(newUser))
                System.out.println("Registration successful!");
            return true;
        }catch (IllegalArgumentException e){
            throw new AuthenticationException(ANSI_RED + "Invalid credentials, only alphanumeric and _ permitted. all values must begin with a letter or _" + ANSI_RESET);
        }
    }
}
