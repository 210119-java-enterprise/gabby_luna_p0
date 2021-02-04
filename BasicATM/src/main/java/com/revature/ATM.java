package com.revature;

import com.revature.utilities.AppState;

public class ATM {

    private static AppState app = new AppState();

    public static void main(String[] args) {
       while (app.isAppRunning()){
            app.getRouter().navigate("/home");
       }

    }

    public static AppState app(){
        return app;
    }
}
