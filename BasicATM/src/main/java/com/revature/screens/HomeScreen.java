package com.revature.screens;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

public class HomeScreen extends Screen{

    public HomeScreen(){
        super("HomeScreen", "/home");
    }

    @Override
    public void render() {
        //Welcome message:
        System.out.println(BORDER);
        String message = "Welcome to the BasicATM!";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        FinishLine("", 0);
        message = "1.) Login";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = "2.) Register";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = "3.) Exit";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");

        try{
            System.out.print("> ");
            String choice = app().getConsole().readLine();
            System.out.print(CLEAR_SCREEN);
            switch (choice) {
                case "1":
                    System.out.println(main_color + "Navigating to login screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/login");
                    break;
                case "2":
                    System.out.println(main_color + "Navigating to register screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/register");
                    break;
                case "3":
                    System.out.println(ANSI_RED + "Exiting application...\n" + ANSI_RESET);
                    app().setAppRunning(false);
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid selection!" + ANSI_RESET);

            }

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }
}
