package com.revature.screens;

import com.revature.services.UserService;

import java.util.Locale;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;
import static com.revature.utilities.ConsoleDecoration.ANSI_RESET;

public class RegisterScreen extends Screen{

    public RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
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
        System.out.println("/* " + main_color + "Please enter your information below..."
                + ANSI_RESET + "                               */");
        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "(../ to go back)" + ANSI_RESET
                +"                                                     */");
        System.out.println("/*                                                                      */");
        System.out.println("/************************************************************************/");
        System.out.println("");

        try{
            System.out.print("> " + main_color + "First Name : " + ANSI_RESET);
            String firstName = app().getConsole().readLine();

            //Pause fom collecting input to allow for returning to main
            if(firstName.toLowerCase(Locale.ROOT).equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to home screen...\n" + ANSI_RESET);
                app().getRouter().navigate("/home");
            }

            System.out.print("> " + main_color + "Last Name  : "  + ANSI_RESET);
            String lastName = app().getConsole().readLine();
            System.out.print("> " + main_color + "Username   : "  + ANSI_RESET);
            String username = app().getConsole().readLine();
            System.out.print("> " + main_color + "Password   : "  + ANSI_RESET);
            String password = app().getConsole().readLine();
            System.out.print(CLEAR_SCREEN);

            //TODO: REGISTER USER

            System.out.println(main_color + "Proceeding to login screen...\n" + ANSI_RESET);
            app().getRouter().navigate("/login");

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }
}
