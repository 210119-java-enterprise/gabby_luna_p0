package com.revature.screens;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.services.UserService;

/**
 * Class responsible for generating the Register Screen View. Extends Screen
 * Responsible for printing out Register Screen and collecting user responses to prompts.
 * Main functionality is to give the user a way to register for the application.
 * utilities.ConsoleDecoration class is used heavily for formatting assistance.
 * A UserService object is used for input validation.
 * <p>
 * @author WezleySingleton
 * @author Gabrielle Luna
 */
public class RegisterScreen extends Screen{

    //Register Screen Attributes --------------------------------
    private final UserService userService;

    //Constructor -----------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. the route will be used for navigation later.
     * <p>
     * @param userService   allows screen to validate registration information
     */
    public RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
        this.userService = userService;
    }

    //Overrides -------------------------------------------------
    /**
     * Render is responsible for displaying the Register Screen to the user. It uses a helper
     * function to print an initial message to the user that includes directions for how to proceed.
     * It then prompts the user to give the necessary information for registration, validating after
     * form has been completed. If username and password combination is available the user is
     * redirected to login screen to continue.
     */
    @Override
    public void render() {
        //Register Instructions
        renderRegisterPrintOut();

        //Registration form
        try{
            //Collect user information
            System.out.print("> " + main_color + "First Name : " + ANSI_RESET);
            String firstName = app().getConsole().readLine();

            //Pause fom collecting input to allow for returning to main
            if(firstName.equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to home screen...\n" + ANSI_RESET);
                app().getRouter().navigate("/home");
                return;
            }

            //Resume collecting input
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
                throw new Exception();

        }catch (InvalidRequestException | AuthenticationException e) {
            //User has inputted invalid or unavailable registration credentials
            //Refresh page
            app().getRouter().navigate("/register");
        }catch (Exception e) {
            //Should be impossible to reach, user has done wacky stuff
            e.printStackTrace();
            System.err.println(ANSI_RED + "[FATAL] Failure, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

    //render Helpers ---------------------------------------------------------
    /**
     * Register message print out. Uses ConsoleDecoration for formatting.
     */
    private void renderRegisterPrintOut() {
        //Register instructions:
        System.out.println(BORDER);
        String message = "Welcome to the BasicATM, now backed by Boxed!";
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
    }
}
