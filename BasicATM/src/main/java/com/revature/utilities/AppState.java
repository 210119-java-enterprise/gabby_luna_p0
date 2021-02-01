package com.revature.utilities;

import com.revature.repositories.AccountRepository;
import com.revature.repositories.UserRepository;
import com.revature.screens.*;
import com.revature.services.AccountService;
import com.revature.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;

import static com.revature.utilities.ConsoleDecoration.*;

public class AppState {

    //Instances shared by whole app:
    private BufferedReader console; //Read in from console
    private ScreenRouter router;    //Screen router (switch board)
    private Session currentSession; //Database connection for current user

    private boolean appRunning;

    public AppState() {
        System.out.println(ANSI_RED + "[LOG] - Initializing application..." + ANSI_RESET);

        this.appRunning = true;
        this.console = new BufferedReader(new InputStreamReader(System.in));

        final UserRepository userRepo = new UserRepository();
        final AccountRepository acctRepo = new AccountRepository();
        final UserService userService = new UserService(userRepo);
        final AccountService acctService = new AccountService(acctRepo);

        router = new ScreenRouter();
        router.addScreen(new HomeScreen());
        router.addScreen(new LoginScreen(userService));
        router.addScreen(new RegisterScreen(userService));
        router.addScreen(new DashboardScreen(userService, acctService));
        router.addScreen(new NewAccountScreen(userService, acctService));

        System.out.println(ANSI_GREEN + "[LOG] - Application initialized" + ANSI_RESET);
        System.out.println(CLEAR_SCREEN);
    }

    public BufferedReader getConsole() {
        return console;
    }

    //Return instance of router to ATM to start app
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
}
