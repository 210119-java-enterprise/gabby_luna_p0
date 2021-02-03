package com.revature.screens;

import com.revature.services.UserService;

import java.util.Locale;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;
import static com.revature.utilities.ConsoleDecoration.ANSI_RESET;

public class RegisterScreen extends Screen{
    private UserService userService;
    public RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
        this.userService = userService;
    }

    @Override
    public void render() {

        //Register instructions:
        System.out.println(BORDER);
        String message = "Welcome to the BasicATM!";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        FinishLine("", 0);
        message = "To register a new Account, please enter your information below...";
        FinishLine((main_color+ message + ANSI_RESET), message.length());
        FinishLine("", 0);
        message = "(../ to go back)";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
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

            //Register User's Credentials
            if (userService.register(firstName, lastName, username, password)) {
                //Successfully created new user
                System.out.println(main_color + "Proceeding to login screen...\n" + ANSI_RESET);
                app().getRouter().navigate("/login");
            }else
                app().getRouter().navigate("/register");

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }
}
