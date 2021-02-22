package com.revature.screens;

import com.revature.models.AppUser;
import com.revature.services.AccountService;
import com.revature.utilities.LinkedList;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Class responsible for generating the Dashboard Screen View. Extends Screen
 * Responsible for printing out the users active accounts, printing navigation options and
 * collecting user responses to prompts. Main functionality is to give the user an account summary.
 * utilities.ConsoleDecoration class is used heavily for formatting assistance.
 * An AccountService object is used for retrieving active accounts.
 * <p>
 * @author Gabrielle Luna
 */
public class DashboardScreen extends Screen{

    //Dashboard Attributes ---------------------------------------
    private final AccountService accountService;

    //Constructor ------------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. the route will be used for navigation later.
     * <p>
     * @param accountService   allows access to active accounts
     */
    public DashboardScreen(AccountService accountService) {
        super("DashboardScreen", "/dashboard");
        this.accountService = accountService;
    }

    //Overrides -------------------------------------------------
    /**
     * Render is responsible for displaying the Dashboard Screen to the user. It prints active
     * accounts for the user and includes options for how to proceed. From here the user can navigate
     * to transaction history, to make a new transaction etc. All future screen return to dashboard.
     * User choice validation is done locally.
     */
    @Override
    public void render() {
        //Retrieve active user
        AppUser user = app().getCurrentSession().getSessionUser();
        //Update Accounts for user and pull a list of ids for reference
        user.setAccounts(accountService.getAccounts(user.getId()));
        LinkedList <Integer> accountIds = user.getAccounts();

        //Dashboard display
        renderDashboardPrintOut(user, accountIds);

        //Get navigation choice from user
        try{
            //Retrieve and trim user selection
            System.out.print("> ");
            String choice = app().getConsole().readLine();
            choice = choice.trim();

            //Reset screen and navigate to selected screen. Invalid selection will return to current screen.
            System.out.print(CLEAR_SCREEN);
            switch (choice) {
                case "1":   //Create a new account
                    System.out.println(main_color + "Navigating to new account screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/newAccount");
                    break;
                case "2":   //View transaction history
                    if (user.getNumAccounts() == 0){
                        System.out.println(main_color + "No account histories to view, open account first...\n" + ANSI_RESET);
                        app().getRouter().navigate("/dashboard");
                    }else {
                        System.out.println(main_color + "Navigating to transaction history screen...\n" + ANSI_RESET);
                        app().getRouter().navigate("/transactionHistory");
                    }
                    break;
                case "3":   //Make a new transaction
                    System.out.println(main_color + "Navigating to transaction screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/transaction");
                    break;
                case "4":   //Transfer funds between accounts
                    System.out.println(main_color + "Navigating to close account screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/closeAccount");
                    break;
                case "5":   //Exit application (terminate connection)
                    System.out.println(ANSI_RED + "Exiting application...\n" + ANSI_RESET);
                    app().invalidateCurrentSession();
                    app().getRouter().navigate("/home");
                    break;
                default:    //Invalid selection
                    System.out.println(ANSI_RED + "Invalid selection!" + ANSI_RESET);
                    app().getRouter().navigate("/dashboard");
            }

        }catch (Exception e) {
            //Should be impossible to reach, user had done wacky stuff
            e.printStackTrace();
            System.err.println(ANSI_RED + "[FATAL] Failure, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

    //render Helpers ---------------------------------------------------------
    /**
     * Dashboard display print out. Uses ConsoleDecoration for formatting.
     */
    private void renderDashboardPrintOut(AppUser user, LinkedList<Integer> accountIds) {
        //Dashboard Print out:
        System.out.println(BORDER);
        String message = "Welcome back " + user.getFirstName() + " " + user.getLastName() + "!";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        message = " Active Accounts: " + user.getNumAccounts();
        FinishLine((main_color+ message + ANSI_RESET), message.length());
        FinishLine("", 0);
        //Print Active Accounts
        for (Integer cur = accountIds.pop(); cur != null; cur = accountIds.pop()) {
            user.getBankAccount(cur).printAccount(main_color);
            FinishLine("", 0);
        }
        FinishLine("", 0);
        //Print navigation options
        message = " 1.) Open New Account";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 2.) View Transaction History";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 3.) Make Deposit/Withdrawal";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 4.) Delete Account";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 5.) Logout";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");
    }
}
