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
        main_color = ANSI_BLUE;
        main_color_bold = BLUE_BOLD_BRIGHT;
    }

    @Override
    public void render() {
        System.out.println("/************************************************************************/");
        System.out.println("/*                      " + main_color_bold + "Welcome to the BasicATM!" + ANSI_RESET
                            + "                        */");
        System.out.println("/*                                                                      */");
        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "Please enter your user credentials to continue..."
                            + ANSI_RESET + "                    */");
        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "(../ to go back)" + ANSI_RESET
                            +"                                                     */");
        System.out.println("/************************************************************************/");
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
