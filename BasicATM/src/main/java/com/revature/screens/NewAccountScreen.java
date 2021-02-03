package com.revature.screens;

import com.revature.models.AccountType;
import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.repositories.UserRepository;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utilities.LinkedList;

import static com.revature.ATM.app;
import static com.revature.ATM.main;
import static com.revature.utilities.ConsoleDecoration.*;

public class NewAccountScreen extends Screen{

    private UserService userService;
    private AccountService accountService;

    public NewAccountScreen(UserService userService, AccountService accountService) {
        super("NewAccountScreen", "/newAccount");
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void render() {
        AppUser user = app().getCurrentSession().getSessionUser();
        LinkedList<Integer> accountIds = user.getAccounts();


        //Register instructions:
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
        message = " (../ to go back)";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");

        try{
            //Print Account type options
            System.out.println(" What type of account would you like to open : ");
            for (int i = 0; i < AccountType.values().length; i++){
                System.out.println((i + 1) + ".) " + AccountType.values()[i].toString());
            }
            System.out.print("> ");
            int acctTypeNum = Integer.parseInt(app().getConsole().readLine());
            AccountType acctType = AccountType.values()[acctTypeNum - 1];

            accountService.addNewAccount(acctType, user.getId());

            System.out.print(CLEAR_SCREEN);
            System.out.println(main_color + "New Account successfully created, returning to dashboard ...\n" + ANSI_RESET);
            app().getRouter().navigate("/dashboard");

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
    }
}
