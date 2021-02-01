package com.revature.services;

import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.repositories.UserRepository;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.Session;

import javax.naming.AuthenticationException;

import static com.revature.ATM.app;

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
    public void authenticate(String username, String password) throws AuthenticationException {
        //Reject for empty strings
        if (username == null || username.trim().equals("") ||
            password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credentials (null or empty strings)!");
        }

        //Check if Username password combo exists
        AppUser authUser = userRepo.findUserByUsernameAndPassword(username, password);
        if (authUser == null){
            throw new AuthenticationException();
        }

        //Start new session
        app().setCurrentSession(new Session(authUser, ConnectionFactory.getInstance().getConnection()));
    }
}
