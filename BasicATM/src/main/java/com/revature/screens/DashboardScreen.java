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
        main_color = ANSI_BLUE;
        main_color_bold = BLUE_BOLD_BRIGHT;
    }

    @Override
    public void render() {
        AppUser user = app().getCurrentSession().getSessionUser();
        //Update Accounts for user
        user.setAccounts(accountService.getAccounts(user.getId()));
        LinkedList <BankAccount> accounts = user.getAccounts();

        String name = user.getFirstName() + " " + user.getLastName();
        System.out.println("/************************************************************************/");
        System.out.println("/*                      " + main_color_bold + "Welcome back " + name + "!" + ANSI_RESET
                + "                        */");
        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "Active Accounts: " + user.getNumAccounts() +ANSI_RESET
                + "                                                   */");
        System.out.println("/*                                                                      */");

        //Print Active Accounts
        for (LinkedList.Node <BankAccount> cur = accounts.head; cur != null; cur = cur.nextNode) {
            cur.data.printAccount(main_color);
        }

        System.out.println("/*                                                                      */");
        System.out.println("/* " + main_color + "1.) Open New Account" + ANSI_RESET
                +"                                                 */");
        System.out.println("/* " + main_color + "2.) View Transaction History" + ANSI_RESET
                +"                                         */");
        System.out.println("/* " + main_color + "3.) Make Deposit/Withdrawal" + ANSI_RESET
                +"                                          */");
        System.out.println("/* " + main_color + "4.) Transfer Funds" + ANSI_RESET
                +"                                                   */");
        System.out.println("/* " + main_color + "5.) Logout" + ANSI_RESET
                +"                                                           */");
        System.out.println("/*                                                                      */");
        System.out.println("/************************************************************************/");
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
                    //app().getRouter().navigate("/register");
                    break;
                case "4":
                    System.out.println(main_color + "Navigating to transfer funds screen...\n" + ANSI_RESET);
                    //app().getRouter().navigate("/register");
                    break;
                case "5":
                    System.out.println(ANSI_RED + "Exiting application...\n" + ANSI_RESET);
                    //app().setAppRunning(false);
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
