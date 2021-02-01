package com.revature.screens;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

public class HomeScreen extends Screen{

    public HomeScreen(){
        super("HomeScreen", "/home");
        main_color = ANSI_BLUE;
        main_color_bold = BLUE_BOLD_BRIGHT;
    }

    @Override
    public void render() {
        //Welcome message:
        System.out.println("/************************************************************************/");
        System.out.println("/*                      " + main_color_bold + "Welcome to the BasicATM!" + ANSI_RESET
                            + "                        */");
        System.out.println("/*                                                                      */");
        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "1.) Login" + ANSI_RESET
                            + "                                                            */");
        System.out.println("/* " + main_color + "2.) Register" + ANSI_RESET
                            +"                                                         */");
        System.out.println("/* " + main_color + "3.) Exit" + ANSI_RESET
                            +"                                                             */");
        System.out.println("/*                                                                      */");
        System.out.println("/************************************************************************/");
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
