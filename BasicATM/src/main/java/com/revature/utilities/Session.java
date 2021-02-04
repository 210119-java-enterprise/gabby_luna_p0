package com.revature.utilities;

import com.revature.models.AppUser;

import java.sql.Connection;

public class Session {

    //Session attributes ------------------------------------
    private AppUser sessionUser;
    private Connection connection;

    //Constructors -------------------------------------------
    /**
     * Stores the current session user and the connection
     * @param sessionUser       User who initiated current session
     * @param connection        working database conenctino
     */
    public Session(AppUser sessionUser, Connection connection){

        if (sessionUser == null || connection == null){
            throw new ExceptionInInitializerError("Cannot establish user session, null values provided!");
        }

        this.sessionUser = sessionUser;
        this.connection = connection;
    }

    //Getters and Setters -------------------------------------
    public AppUser getSessionUser(){
        return sessionUser;
    }

    public void setSessionUser(AppUser sessionUser){
        this.sessionUser = sessionUser;
    }

    public Connection getConnection (){
        return connection;
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    //TODO: ADD ADMIN PERMISSIONS?

}
