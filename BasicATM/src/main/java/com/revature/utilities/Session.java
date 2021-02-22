package com.revature.utilities;

import com.revature.models.AppUser;
import com.revature.Boxed.service.BlackBox;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public class Session {

    //Session attributes ------------------------------------
    private AppUser sessionUser;
    private Connection connection;

    //Constructors -------------------------------------------
    /**
     * Stores the current session user and the connection
     * @param sessionUser       User who initiated current session
     * @param box        working ORM
     */
    public Session(AppUser sessionUser, BlackBox box){
        try {
            //TODO: ELIMINATE
            connection = box.getConnection();
            box.setCurrentConnection(connection);

        }catch (SQLException | ConnectException e){
            e.printStackTrace();
        }

        if (sessionUser == null || connection == null){
            throw new ExceptionInInitializerError("Cannot establish user session, null values provided!");
        }

        this.sessionUser = sessionUser;
    }

    //Getters and Setters -------------------------------------
    public AppUser getSessionUser(){
        return sessionUser;
    }

    public void setSessionUser(AppUser sessionUser){
        this.sessionUser = sessionUser;
    }

    //TODO: ADD ADMIN PERMISSIONS?

}
