package com.revature.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class in charge of establishing a connection to the database
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public class ConnectionFactory {
    //static copy of Connection Factory
    private static ConnectionFactory connFactory = new ConnectionFactory();

    //Variable to hold database login credentials
    private Properties props = new Properties();

    //Look for library
    static{
        try {
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //Constructors --------------------------
    /**
     * Private constructor loads the file containing login credentials into the Properties variable
     */
    private ConnectionFactory(){
        try{
            props.load(new FileReader("src/main/resources/application.properties"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Getters and Setters --------------------
    public static ConnectionFactory getInstance(){
        return connFactory;
    }

    /**
     * Uses properties to initialize connection and returns that connection
     * @return database connection
     */
    public Connection getConnection() {

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("admin-usr"),
                    props.getProperty("admin-pw")
            );
            conn.setSchema("BasicATM");

        }catch (SQLException e){
            e.printStackTrace();
        }

        return conn;
    }

}
