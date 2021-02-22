package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.models.AppUser;
import com.revature.services.AccountService;
import com.revature.services.TransactionService;
import com.revature.utilities.LinkedList;


import java.io.IOException;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

public class CloseAccountScreen extends Screen{

    //Attributes ----------------------------------------------------
    private final TransactionService transactionService;
    private final AccountService accountService;

    //Constructor ---------------------------------------------------
    /**
     *
     */
    public CloseAccountScreen(TransactionService transactionService, AccountService accountService){
        super("CloseAccount", "/closeAccount");
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public void render(){
        //Retrieve active user
        AppUser user = app().getCurrentSession().getSessionUser();
        LinkedList<Integer> accountIds = user.getAccounts();
        int deleteAccountId;

        try{
            String choice = renderAccountSelection(user, accountIds);

            //Allow for returning to main
            if(choice.equals("../")) {
                System.out.print(CLEAR_SCREEN);
                System.out.println(main_color + "Returning to dashboard...\n" + ANSI_RESET);
                app().getRouter().navigate("/dashboard");
            }

            //Convert selection to Account id and validate
            deleteAccountId = Integer.parseInt(choice);
            accountIds = user.getAccounts();
            if (!accountIds.contains(deleteAccountId))
                throw new AuthenticationException(ANSI_RED + "Invalid account number..." + ANSI_RESET);


            transactionService.deleteTransactions(deleteAccountId);
            accountService.deleteAccount(deleteAccountId);

            app().getRouter().navigate("/dashboard");

        }catch(NumberFormatException e){
            //Selection was not a number
            System.out.print(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "Invalid Selection! Please input an account number...\n" + ANSI_RESET);
            app().getRouter().navigate("/closeAccount");
        }catch(AuthenticationException e){
            //Invalid account number provided
            app().getRouter().navigate("/closeAccount");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "[FATAL] Failure, shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }

    }

    private String renderAccountSelection(AppUser user, LinkedList<Integer> accountIds) throws IOException {
        String choice = "";

        //Transaction History Account print out:
        System.out.println(BORDER);
        String message = "Choose an Account to Close";
        CenterLine((main_color_bold + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        message = "Active Accounts: " + user.getNumAccounts();
        FinishLine((main_color+ message + ANSI_RESET), message.length());
        FinishLine("", 0);
        //Print Active Accounts
        for (Integer cur = accountIds.pop(); cur != null; cur = accountIds.pop()) {
            user.getBankAccount(cur).printAccount(main_color);
            FinishLine("", 0);
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
}
