package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.services.UserService;

import java.util.Locale;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

public class LoginScreen extends Screen{

    private UserService userService;

    public LoginScreen(UserService userService) {
        super("LoginScreen", "/login");
        this.userService = userService;
    }

    @Override
    public void render() {
        //Login instructions:
        System.out.println(BORDER);
        String message = "Welcome to the BasicATM!";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        FinishLine("", 0);
        message = "Please enter your user credentials to continue...";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        message = "(../ to go back)";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");

        try{
            System.out.print(">" + main_color + " Username:  " + ANSI_RESET);
            String username = app().getConsole().readLine();

            //Pause fom collecting input to allow for returning to main
            if(username.toLowerCase(Locale.ROOT).equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to home screen...\n" + ANSI_RESET);
                app().getRouter().navigate("/home");
            }

            System.out.print("> " + main_color + "Password:  " + ANSI_RESET);
            String password = app().getConsole().readLine();

            //Authenticate User's Credentials
            userService.authenticate(username, password);
            if (app().isSessionValid()){
                System.out.print(CLEAR_SCREEN);
                System.out.println("Logged in");
            }

            System.out.println(main_color + "Proceeding to Dashboard...\n" + ANSI_RESET);
            app().getRouter().navigate("/dashboard");

        }catch (InvalidRequestException | AuthenticationException e) {
            System.err.println("[INFO] - Invalid login credentials provided!");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "[SEVERE] - An unexpected exception occurred");
            System.err.println("[FATAL] - Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

}
