package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.services.UserService;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Class responsible for generating the Login Screen View. Extends Screen
 * Responsible for printing out Login Screen and collecting user responses to prompts.
 * Main functionality is to give the user a way to log in to the application.
 * utilities.ConsoleDecoration class is used heavily for formatting assistance.
 * A UserService object is used for input validation.
 * <p>
 * @author WezleySingleton
 * @author Gabrielle Luna
 */
public class LoginScreen extends Screen{

    //Register Screen Attributes --------------------------------
    private final UserService userService;

    //Constructor -----------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. the route will be used for navigation later.
     * <p>
     * @param userService   allows screen to validate registration information
     */
    public LoginScreen(UserService userService) {
        super("LoginScreen", "/login");
        this.userService = userService;
    }

    //Overrides -------------------------------------------------
    /**
     * Render is responsible for displaying the Login Screen to the user. It prints an initial
     * message to the user that includes directions for how to proceed. It then prompts the user
     * to give their username and password authenticating after both are received.
     * If username and password combination is valid the user is redirected to the dashboard and
     * a database connection is initiated.
     */
    @Override
    public void render() {
        //Login instructions
        renderLoginPrintOut();

        //Login form
        try{
            //Collect user information
            System.out.print(">" + main_color + " Username:  " + ANSI_RESET);
            String username = app().getConsole().readLine();

            //Pause fom collecting input to allow for returning to main
            if(username.equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to home screen...\n" + ANSI_RESET);
                app().getRouter().navigate("/home");
            }

            System.out.print("> " + main_color + "Password:  " + ANSI_RESET);
            String password = app().getConsole().readLine();

            //Authenticate User's Credentials
            userService.authenticate(username, password);
            if (app().isSessionValid()){
                //Successful login
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Logged in, proceeding to Dashboard...\n" + ANSI_RESET);
                app().getRouter().navigate("/dashboard");
            }

        }catch (AuthenticationException|InvalidRequestException e) {
            //User has inputted invalid login credentials
            //Refresh page
            app().getRouter().navigate("/login");
        } catch (Exception e) {
            //Should be impossible to reach, check internet connection
            e.printStackTrace();
            System.err.println(ANSI_RED + "[FATAL] Failure to connect, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

    //render Helpers ---------------------------------------------------------
    /**
     * Login message print out. Uses ConsoleDecoration for formatting.
     */
    private void renderLoginPrintOut() {
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
    }

}
