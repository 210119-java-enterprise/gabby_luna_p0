package com.revature.screens;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Class responsible for generating the Home Screen View. Extends Screen.
 * Responsible for printing out Home Screen and collecting user responses to prompts.
 * Main functionality is to give the user a way to log in to application.
 * utilities.ConsoleDecoration class is used heavily for formatting assistance.
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public class HomeScreen extends Screen{

    //Constructor --------------------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. The route will be used for navigation later.
     */
    public HomeScreen(){
        super("HomeScreen", "/home");
    }

    //Overrides ----------------------------------------------------------
    /**
     * Render is responsible for displaying the HomeScreen to the user. It uses a helper function to
     * print a welcome message to the user that includes options about how to proceed. The users choice
     * is taken in and validated before they are directed to the appropriate screen.
     */
    @Override
    public void render() {
        //Welcome message:
        renderDashboardPrintOut();

        //User selection processing
        try{
            //Retrieve and trim user selection
            System.out.print("> ");
            String choice = app().getConsole().readLine();
            choice = choice.trim();

            //Reset screen and navigate to selected screen. Invalid selection will return to current screen
            System.out.print(CLEAR_SCREEN);
            switch (choice) {
                case "1": //Login
                    System.out.println(main_color + "Navigating to login screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/login");
                    break;
                case "2": //Register
                    System.out.println(main_color + "Navigating to register screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/register");
                    break;
                case "3": //Exit
                    System.out.println(ANSI_RED + "Exiting application..." + GREEN_BOLD_BRIGHT + "Goodbye\n" + ANSI_RESET);
                    app().setAppRunning(false);
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid selection!" + ANSI_RESET);
            }

        }catch (Exception e) {
            //Should be impossible to reach, user has done wacky stuff
            e.printStackTrace();
            System.err.println(ANSI_RED + "[FATAL] Failure, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

    //render Helpers ---------------------------------------------------------
    /**
     * Welcome message print out. Uses ConsoleDecoration for formatting.
     */
    private void renderDashboardPrintOut() {
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
    }
}
