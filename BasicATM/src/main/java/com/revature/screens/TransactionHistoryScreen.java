package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.services.TransactionService;
import com.revature.utilities.LinkedList;

import java.io.IOException;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Class responsible for generating the TransactionHistory Screen View. Extends Screen
 * Responsible for printing out the users past transactions.
 * utilities.ConsoleDecoration class is used heavily for formatting assistance.
 * A TransactionService object is used for retrieving past transactions.
 * <p>
 * @author Gabrielle Luna
 */
public class TransactionHistoryScreen extends Screen{

    //NewAccount Attributes ---------------------------------------
    private final TransactionService transactionService;

    //Constructor -------------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. the route will be used for navigation later.
     * <p>
     * @param transactionService   allows access to past transactions
     */
    public TransactionHistoryScreen(TransactionService transactionService) {
        super("TransactionHistory", "/transactionHistory");
        this.transactionService = transactionService;
    }

    //Overrides --------------------------------------------------
    /**
     * Render is responsible for displaying the Transaction History Screen to the user.
     * It uses two helper functions to print out screen and internally validates user selections
     * and handles exceptions thrown y helper functions
     */
    @Override
    public void render() {
        //Retrieve active user
        AppUser user = app().getCurrentSession().getSessionUser();
        //Pull a list of account ids for reference
        LinkedList <Integer> accountIds = user.getAccounts();
        //Create a holder for transaction ids and chosen account id
        LinkedList <Integer> transactionIds;
        int transactionAccountId;

        //Account selection form and past transaction print out:
        try{
            //Print out Account summaries and retrieve user account selection
            String choice = renderAccountSelection(user, accountIds);

            //Allow for returning to main
            if(choice.equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to dashboard...\n" + ANSI_RESET);
                app().getRouter().navigate("/dashboard");
            }

            //Convert selection to Account id and validate
            transactionAccountId = Integer.parseInt(choice);
            accountIds = user.getAccounts();
            if (!accountIds.contains(transactionAccountId))
                throw new AuthenticationException(ANSI_RED + "Invalid account number..." + ANSI_RESET);

            //Get copy of Chosen account and update transaction list
            BankAccount transactionAccount = user.getBankAccount(transactionAccountId);
            transactionAccount.setTransactions(transactionService.getTransactions(transactionAccountId));
            //Get list of transaction keys
            transactionIds = transactionAccount.getAccountTransactions();

            //Print out transaction history and wait for user input to return
            renderTransactionHistory(transactionIds, transactionAccount);
            app().getRouter().navigate("/dashboard");

        }catch(NumberFormatException e){
            //Selection was not a number
            System.out.print(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "Invalid Selection! Please input an account number...\n" + ANSI_RESET);
            app().getRouter().navigate("/transactionHistory");
        }catch(AuthenticationException e){
            //Invalid account number provided
            app().getRouter().navigate("/transactionHistory");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "[FATAL] Failure, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }

    //Render Helpers ----------------------------------
    /**
     * Displays a summary of users active accounts and requests the user select
     * an account for transaction history.
     * <p>
     * @param user          active user
     * @param accountIds    list of ids for account summary print out
     * @return              user account selection in string form
     * @throws IOException  thrown by readLine() method when getting user input
     */
    private String renderAccountSelection(AppUser user, LinkedList<Integer> accountIds) throws IOException {
        String choice = "";

        //Transaction History Account print out:
        System.out.println(BORDER);
        String message = "Transaction History";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        message = "Active Accounts: " + user.getNumAccounts();
        FinishLine((main_color+ message + ANSI_RESET), message.length());
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

        //Request user select an account to view
        System.out.println(ANSI_BLUE + " Pick an account number for the transaction (ex: 1000) : " + ANSI_RESET);

        //Retrieve and trim user selection
        System.out.print("> ");
        choice = app().getConsole().readLine();
        choice = choice.trim();

        return choice;
    }

    /**
     * Displays the accounts transaction history once an account has been chosen.
     * <p>
     * @param transactionIds        a list of transaction ids for reference while printing transactions
     * @param transactionAccount    Bank account whose transactions are being listed
     * @throws IOException          thrown by readLine() function while retrieving user input
     */
    private void renderTransactionHistory(LinkedList<Integer> transactionIds, BankAccount transactionAccount) throws IOException {
        //Clear screen and print chosen accounts past transactions
        System.out.println(CLEAR_SCREEN);
        System.out.println(BORDER);
        String message = "Transaction History";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        if (transactionAccount.getNumTransactions() == 0){
            message = " No transactions completed yet...";
            CenterLine((main_color + message + ANSI_RESET), message.length());
        }else{
            //Print Transaction History
            for (Integer cur = transactionIds.pop(); cur != null; cur = transactionIds.pop()) {
                transactionAccount.getTransaction(cur).printTransaction(main_color);
            }}
        FinishLine("", 0);
        //print navigation options
        message = " press enter to go back";
        CenterLine((main_color + message + ANSI_RESET), message.length());
        System.out.println(BORDER);
        System.out.println("");

        //Await user input, any input returns user to dashboard
        System.out.print("> ");
        app().getConsole().readLine();
        System.out.print(CLEAR_SCREEN);
        System.out.println(main_color + "Returning to dashboard...\n" + ANSI_RESET);
    }

}
