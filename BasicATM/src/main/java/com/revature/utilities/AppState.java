package com.revature.utilities;

import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.screens.*;
import com.revature.service.BlackBox;
import com.revature.services.AccountService;
import com.revature.services.TransactionService;
import com.revature.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.revature.utilities.ConsoleDecoration.*;

/**
 * class manages application state, including storing application wide variables and
 * a boolean for whether the app is still in a running state.
 */
public class AppState {

    //Instances shared by whole app:
    private final BlackBox box;
    private final BufferedReader console; //Read in from console
    private final ScreenRouter router;    //Screen router (switch board)
    private Session currentSession; //Database connection for current user
    private boolean appRunning;

    //Constructor ---------------------------------------------
    /**
     * The AppState constructor initializes all of the application wide variables
     * including loading the Screen router with all of the screen in the application.
     */
    public AppState() {
        System.out.println(ANSI_RED + "[LOG] - Initializing application..." + ANSI_RESET);

        //Set appRunning to true and open a Buffered Reader
        this.appRunning = true;
        this.console = new BufferedReader(new InputStreamReader(System.in));
        this.box = new BlackBox("src/main/resources/application.properties");

        //Initialize all of the Services and Repositories
        final AccountRepository acctRepo = new AccountRepository();
        final TransactionRepository transRepo = new TransactionRepository();
        final UserService userService = new UserService(box);
        final AccountService acctService = new AccountService(acctRepo, box);
        final TransactionService transService = new TransactionService(transRepo);

        //Load the Screen Router
        router = new ScreenRouter();
        router.addScreen(new HomeScreen());
        router.addScreen(new LoginScreen(userService));
        router.addScreen(new RegisterScreen(userService));
        router.addScreen(new DashboardScreen(acctService));
        router.addScreen(new NewAccountScreen(acctService));
        router.addScreen(new TransactionScreen(acctService, transService));
        router.addScreen(new TransactionHistoryScreen(transService));

        System.out.println(ANSI_GREEN + "[LOG] - Application initialized" + ANSI_RESET);
        System.out.println(CLEAR_SCREEN);
    }

    public BufferedReader getConsole() {
        return console;
    }

    public ScreenRouter getRouter(){
        return router;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public void invalidateCurrentSession(){
        this.currentSession = null;
    }

    public boolean isSessionValid(){
        return (this.currentSession != null);
    }

    public BlackBox getBox() { return box; }
}
