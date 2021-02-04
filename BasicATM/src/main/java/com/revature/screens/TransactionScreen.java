package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.models.BankAccount;
import com.revature.models.TransactionType;
import com.revature.services.AccountService;
import com.revature.services.TransactionService;
import com.revature.utilities.ConnectionFactory;
import com.revature.utilities.LinkedList;

import java.sql.Connection;
import java.sql.SQLException;

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
public class TransactionScreen extends Screen{

    //NewAccount Attributes ---------------------------------------
    private final AccountService accountService;
    private final TransactionService transService;

    //Constructor -------------------------------------------------
    /**
     * Only necessary Constructor. Sends its screen name and screen route to
     * its parent class to be saved. the route will be used for navigation later.
     * <p>
     * @param accountService    allows access to active accounts
     * @param transService      allows access to past transactions
     */
    public TransactionScreen (AccountService accountService, TransactionService transService){
        super("TransactionScreen", "/transaction");
        this.accountService = accountService;
        this.transService = transService;
    }

    //Overrides --------------------------------------------------
    /**
     * Render is responsible for displaying the Transaction Screen to the user.
     * It uses a helper function to print out screen and internally validates user selections
     * and handles exceptions thrown y helper functions
     */
    @Override
    public void render() {
        //Retrieve active user
        AppUser user = app().getCurrentSession().getSessionUser();
        //Pull a list of account ids for reference
        LinkedList<Integer> accountIds = user.getAccounts();

        //Transaction instructions print out
        renderTransactionPrintOut(user, accountIds);

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            //Request an account for transaction
            System.out.println(ANSI_BLUE + " Pick an account number for the transaction (ex: 1000) : " + ANSI_RESET);
            System.out.print("> ");

            //Retrieve user selection
            String choice = app().getConsole().readLine();
            choice = choice.trim();
            int accountId = Integer.parseInt(choice);

            //validate account number
            accountIds = user.getAccounts();
            if (!accountIds.contains(accountId))
                throw new AuthenticationException(ANSI_RED + "Invalid account number..." + ANSI_RESET);


            //Request Transaction type
            renderRequestTransactionType();

            //Retrieve and trim user selection
            choice = app().getConsole().readLine();
            choice = choice.trim();
            int t_type = Integer.parseInt(choice) - 1;

            //validate transaction type
            if (t_type >= TransactionType.values().length)
                throw new InvalidRequestException(ANSI_RED + "Invalid transaction type..." + ANSI_RESET);

            //Request Transaction amount
            System.out.println(ANSI_BLUE + " Amount : " + ANSI_RESET);
            System.out.print("> $");

            //Retrieve and trim user selection
            choice = app().getConsole().readLine();
            choice = choice.trim();
            double amount = Double.parseDouble(choice);
            amount = Math.abs(amount);

            //Validate transaction
            BankAccount transactionAccount = user.getBankAccount(accountId);
            if (TransactionType.values()[t_type] == TransactionType.DEBIT){
                if (transactionAccount.getBalance() - amount < 0)
                    throw new InvalidRequestException(ANSI_RED + "Insufficient Funds..." + ANSI_RESET);
                else
                    amount *= -1;
            }
            else {
                if (transactionAccount.getBalance() + amount > transactionAccount.ACCOUNT_MAX)
                    throw new InvalidRequestException(ANSI_RED + "Account balances over" +
                            transactionAccount.ACCOUNT_MAX + " unsupported..." + ANSI_RESET);
            }

            //Create transaction and update account records
            transService.addNewTransaction(TransactionType.values()[t_type], amount, accountId);
            double newBalance = transService.getTransactionSum(accountId);
            transactionAccount.setBalance(accountService.updateBalance(accountId, newBalance));

            System.out.print(CLEAR_SCREEN);
            System.out.println(main_color + "New Transaction successfully processed, returning to dashboard...\n" + ANSI_RESET);
            app().getRouter().navigate("/dashboard");

        }catch (NumberFormatException e){
            //Selection was not a number
            System.out.print(CLEAR_SCREEN);
            System.out.println(ANSI_RED + "Invalid Selection! Please input a valid number...\n" + ANSI_RESET);
            app().getRouter().navigate("/transaction");
        }catch (AuthenticationException | InvalidRequestException e){
            //Invalid account number provided
            app().getRouter().navigate("/transaction");
        }
        catch (SQLException e){
            e.printStackTrace();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(ANSI_RED + " Invalid input ... " + ANSI_RESET);
            app().getRouter().navigate("/dashboard");
        }

    }

    //Render Helpers ----------------------------------

    /**
     * Transaction message print out. Uses ConsoleDecoration for formatting.
     * @param user          grants access to active user
     * @param accountIds    allows active accounts to be printed.
     */
    private void renderTransactionPrintOut(AppUser user, LinkedList<Integer> accountIds) {
        //Transaction instruction Print out:
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
            FinishLine("", 0);
        }
        FinishLine("", 0);
        message = "To make a transaction follow the prompts below...";
        FinishLine((main_color + message + ANSI_RESET), message.length());
        FinishLine("", 0);
        System.out.println(BORDER);
        System.out.println("");
    }

    /**
     * Print out to list transaction types.
     */
    private void renderRequestTransactionType() {
        System.out.println(ANSI_BLUE + " Is this a withdrawal or deposit? : " + ANSI_RESET);
        System.out.println(ANSI_BLUE + " 1.)Withdrawal " + ANSI_RESET);
        System.out.println(ANSI_BLUE + " 2.)Deposit " + ANSI_RESET);
        System.out.print("> ");
    }
}
