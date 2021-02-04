package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.repositories.UserRepository;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.Session;
import org.postgresql.util.PSQLException;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.ANSI_RED;
import static com.revature.utilities.ConsoleDecoration.ANSI_RESET;

/*

 */
public class UserService {
    //Copy of Repo ----------------------------------------
    private UserRepository userRepo;

    //Constructors ----------------------------------------
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    //Verify user credentials and start new database session
    public void authenticate(String username, String password) throws AuthenticationException{
        //Reject for empty strings
        if (username == null || username.trim().equals("") ||
            password == null || password.trim().equals("")) {
            throw new InvalidRequestException(ANSI_RED + "Invalid login credentials, missing input for username or password..." + ANSI_RESET);
        }

        //Check if Username password combo exists
        AppUser authUser = userRepo.findUserByUsernameAndPassword(username, password);
        if (authUser == null){
            throw new AuthenticationException(ANSI_RED + "Invalid login credentials..." + ANSI_RESET);
        }

        //Start new session
        app().setCurrentSession(new Session(authUser, ConnectionFactory.getInstance().getConnection()));
    }

    public boolean register(String firstName, String lastName, String username, String password) throws AuthenticationException {
        //Reject if there are any empty strings
        if (    firstName == null || firstName.trim().equals("") ||
                lastName == null || lastName.trim().equals("") ||
                username == null || username.trim().equals("") ||
                password == null || password.trim().equals("")) {
            throw new InvalidRequestException(ANSI_RED + "Invalid credentials, no empty fields permitted!" + ANSI_RESET);
        }

        //Check if Username password combo exists
        AppUser authUser = userRepo.findUserByUsernameAndPassword(username, password);
        if (authUser == null){
            //Username password combo is available
            userRepo.createNewUser(firstName, lastName, username, password);
        }else{
            throw new AuthenticationException(ANSI_RED + "Username and password combo unavailable!" + ANSI_RESET);
        }

        System.out.println("Registration successful!");
        return true;
}
}
