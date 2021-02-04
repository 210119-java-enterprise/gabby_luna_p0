package com.revature.screens;

import com.revature.models.AccountType;
import com.revature.models.AppUser;
import com.revature.services.AccountService;
import com.revature.utilities.LinkedList;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Class responsible for generating the New Account Screen View. Extends Screen
 * Responsible for printing out the users active accounts, and providing the ability to create a new
 * account via user responses to prompts. Main functionality is to create a new account.
 * utilities.ConsoleDecoration class is used heavily for formatting assistance.
 * An AccountService object is used for retrieving active accounts.
 * <p>
 * @author Gabrielle Luna
 */
public class NewAccountScreen extends Screen{

    //NewAccount Attributes ---------------------------------------
    private final AccountService accountService;

    //Constructor ------------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. the route will be used for navigation later.
     * <p>
     * @param accountService   allows access to active accounts
     */
    public NewAccountScreen(AccountService accountService) {
        super("NewAccountScreen", "/newAccount");
        this.accountService = accountService;
    }

    //Overrides -------------------------------------------------
    /**
     * Render is responsible for displaying the New Account Screen to the user. It prints active
     * accounts for the user and includes options for making a new account. From here the user gets
     * returned to the dashboard once the new account has been created.
     * User choice validation is done locally.
     */
    @Override
    public void render() {
        //retrieve active user
        AppUser user = app().getCurrentSession().getSessionUser();
        //pull a list of account ids for reference
        LinkedList<Integer> accountIds = user.getAccounts();

        //NewAccount Instructions
        renderNewAccountPrintOut(user, accountIds);

        //NewAccount form:
        try {
            //Print Account type options
            System.out.println(" What type of account would you like to open : ");
            for (int i = 0; i < AccountType.values().length; i++) {
                System.out.println((i + 1) + ".) " + AccountType.values()[i].toLongName());
            }

            //Retrieve and trim user selection
            System.out.print("> ");
            String choice = app().getConsole().readLine();
            choice = choice.trim();

            //Allow for returning to main
            if(choice.equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to dashboard...\n" + ANSI_RESET);
                app().getRouter().navigate("/dashboard");
            }

            //Convert selection to AccountType
            int acctTypeNum = Integer.parseInt(choice);
            AccountType acctType = AccountType.values()[acctTypeNum - 1];

            //Save new account to database
            accountService.addNewAccount(acctType, user.getId());

            //Return to dashboard
            System.out.print(CLEAR_SCREEN);
            System.out.println(main_color + "New Account successfully created, returning to dashboard ...\n" + ANSI_RESET);
            app().getRouter().navigate("/dashboard");

        }catch(ArrayIndexOutOfBoundsException e){
            //Number selection did not map to an account type
            System.out.print(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "Invalid Selection!\n" + ANSI_RESET);
            app().getRouter().navigate("/newAccount");
        }catch(NumberFormatException e){
            //Selection was not a number
            System.out.print(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "Invalid Selection! Please input a number...\n" + ANSI_RESET);
            app().getRouter().navigate("/newAccount");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "[FATAL] Failure to connect, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

    //render Helpers ---------------------------------------------------------
    /**
     * NewAccount message print out. Uses ConsoleDecoration for formatting.
     */
    private void renderNewAccountPrintOut(AppUser user, LinkedList<Integer> accountIds) {
        //NewAccount print out:
        System.out.println(BORDER);
        String message = "Open a New Account";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        FinishLine("", 0);
        message = " Active Accounts: ";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        //Print Active Accounts
        for (Integer cur = accountIds.pop(); cur != null; cur = accountIds.pop()) {
            user.getBankAccount(cur).printAccount(main_color);
        }
        FinishLine("", 0);
        //Print navigation options
        message = " (../ to go back)";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");
    }
}
