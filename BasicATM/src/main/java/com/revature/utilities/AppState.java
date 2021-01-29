package com.revature.utilities;

import com.revature.screens.HomeScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class AppState {

    private BufferedReader console;
    private ScreenRouter router;
    private boolean appRunning;

    //Console colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public AppState() {
        System.out.println(ANSI_RED + "[LOG] - Initializing application..." + ANSI_RESET);

        this.appRunning = true;
        this.console = new BufferedReader(new InputStreamReader(System.in));

        router = new ScreenRouter();
        router.addScreen(new HomeScreen());

        System.out.println(ANSI_GREEN + "[LOG] - Application initialized" + ANSI_RESET);
    }

    public BufferedReader getConsole() {
        return console;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }
}
