package com.revature.screens;

import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utilities.LinkedList;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;
import static com.revature.utilities.ConsoleDecoration.ANSI_RESET;

public class DashboardScreen extends Screen{

    private UserService userService;
    private AccountService accountService;

    public DashboardScreen(UserService userService, AccountService accountService) {
        super("DashboardScreen", "/dashboard");
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() {
        AppUser user = app().getCurrentSession().getSessionUser();
        //Update Accounts for user
        user.setAccounts(accountService.getAccounts(user.getId()));
        LinkedList <Integer> accountIds = user.getAccounts();

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
        }
        FinishLine("", 0);
        message = " 1.) Open New Account";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 2.) View Transaction History";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 3.) Make Deposit/Withdrawal";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 4.) Transfer Funds";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        message = " 5.) Logout";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");

        try{
            System.out.print("> ");
            String choice = app().getConsole().readLine();
            System.out.print(CLEAR_SCREEN);
            switch (choice) {
                case "1":
                    System.out.println(main_color + "Navigating to new account screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/newAccount");
                    break;
                case "2":
                    System.out.println(main_color + "Navigating to transaction history screen...\n" + ANSI_RESET);
                    //app().getRouter().navigate("/register");
                    break;
                case "3":
                    System.out.println(main_color + "Navigating to transaction screen...\n" + ANSI_RESET);
                    app().getRouter().navigate("/transaction");
                    break;
                case "4":
                    System.out.println(main_color + "Navigating to transfer funds screen...\n" + ANSI_RESET);
                    //app().getRouter().navigate("/register");
                    break;
                case "5":
                    System.out.println(ANSI_RED + "Exiting application...\n" + ANSI_RESET);
                    app().setAppRunning(false);
                    app().getRouter().navigate("/home");
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid selection!" + ANSI_RESET);

            }

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }
}
