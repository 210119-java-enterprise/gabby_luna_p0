package com.revature.screens;

import com.revature.models.AccountType;
import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.repositories.UserRepository;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utilities.LinkedList;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

public class NewAccountScreen extends Screen{

    private UserService userService;
    private AccountService accountService;

    public NewAccountScreen(UserService userService, AccountService accountService) {
        super("NewAccountScreen", "/newAccount");
        this.userService = userService;
        this.accountService = accountService;
        main_color = ANSI_BLUE;
        main_color_bold = BLUE_BOLD_BRIGHT;
    }

    @Override
    public void render() {
        AppUser user = app().getCurrentSession().getSessionUser();
        LinkedList<BankAccount> accounts = user.getAccounts();

        System.out.println("/************************************************************************/");
        System.out.println("/*                      " + main_color_bold + "Open a New Account" + ANSI_RESET
                + "                             */");
        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "Active Accounts: " + user.getNumAccounts() +ANSI_RESET
                + "                                                   */");
        System.out.println("/*                                                                      */");
        //Print Active Accounts
        for (LinkedList.Node <BankAccount> cur = accounts.head; cur != null; cur = cur.nextNode) {
            cur.data.printAccount(main_color);
        }
        System.out.println("/*                                                                      */");
        System.out.println("/************************************************************************/");

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

            System.out.println(main_color + "New Account successfully created, navigating to register screen...\n" + ANSI_RESET);
            app().getRouter().navigate("/dashboard");

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(ANSI_RED + "Shutting down application" + ANSI_RESET);
            app().setAppRunning(false);
        }
        System.out.print(CLEAR_SCREEN);
    }
}
