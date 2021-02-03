package com.revature.screens;

import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.models.TransactionType;
import com.revature.services.AccountService;
import com.revature.services.TransactionService;
import com.revature.services.UserService;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;

import java.sql.Connection;
import java.sql.SQLException;

import static com.revature.ATM.app;
import static com.revature.utilities.ConsoleDecoration.*;

public class TransactionScreen extends Screen{
    private UserService userService;
    private AccountService accountService;
    private TransactionService transService;

    public TransactionScreen (UserService userService, AccountService accountService, TransactionService transService){
        super("TransactionScreen", "/transaction");
        this.userService = userService;
        this.accountService = accountService;
        this.transService = transService;
    }

    @Override
    public void render() {
        AppUser user = app().getCurrentSession().getSessionUser();
        LinkedList<Integer> accountIds = user.getAccounts();

        //Dashboard Print out:
        System.out.println(BORDER);
        String message = "Make a Transaction";
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
        message = "To make a transaction follow the prompts below...";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            //Request account ID to determine account for current transaction
            System.out.println(ANSI_BLUE + " Pick an account number for the transaction (ex: 1000) : " + ANSI_RESET);
            System.out.print("> ");
            int accountId = Integer.parseInt(app().getConsole().readLine());
            //validate account number
            accountIds = user.getAccounts();
            if (!accountIds.contains(accountId)){
                System.out.println(CLEAR_SCREEN);
                System.out.println(ANSI_RED + "Invalid account number..." + ANSI_RESET);
                app().getRouter().navigate("/transaction");
            }

            //Request Transaction type
            System.out.println(ANSI_BLUE + " Is this a withdrawal or deposit? : " + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 1.)Withdrawal " + ANSI_RESET);
            System.out.println(ANSI_BLUE + " 2.)Deposit " + ANSI_RESET);
            System.out.print("> ");
            int t_type = Integer.parseInt(app().getConsole().readLine()) - 1;
            //validate transaction type
            if (t_type >= TransactionType.values().length){
                System.out.println(CLEAR_SCREEN);
                System.out.println(ANSI_RED + "Invalid transaction type..." + ANSI_RESET);
                app().getRouter().navigate("/transaction");
            }

            //Request Transaction amount
            System.out.println(ANSI_BLUE + " Amount : " + ANSI_RESET);
            System.out.print("> $");
            int amount = Integer.parseInt(app().getConsole().readLine());
            //Validate transaction
            BankAccount transactionAccount = user.getBankAccount(accountId);
            if (TransactionType.values()[t_type] == TransactionType.DEBIT){
                if (transactionAccount.getBalance() - amount < 0){
                    System.out.println(CLEAR_SCREEN);
                    System.out.println(ANSI_RED + "Insufficient Funds..." + ANSI_RESET);
                    app().getRouter().navigate("/transaction");
                }else
                    amount *= -1;
            }

            //Create transaction
            transService.addNewTransaction(TransactionType.values()[t_type], amount, accountId);
            double newBalance = transService.getTransactionSum(accountId);
            transactionAccount.setBalance(accountService.updateBalance(accountId, newBalance));

            System.out.print(CLEAR_SCREEN);
            System.out.println(main_color + "New Transaction successfully processed, returning to dashboard...\n" + ANSI_RESET);
            app().getRouter().navigate("/dashboard");

        }catch (SQLException e){
            e.printStackTrace();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
