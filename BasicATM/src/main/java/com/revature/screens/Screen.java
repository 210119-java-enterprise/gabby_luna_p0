package com.revature.screens;

import static com.revature.utilities.ConsoleDecoration.ANSI_BLUE;
import static com.revature.utilities.ConsoleDecoration.BLUE_BOLD_BRIGHT;

/**
 * Abstract class Screen is parent to all screens. Sets the default color for
 * all screen and stores the screen name and route. Has one abstract class called
 * render, which is overwritten by each class.
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public abstract class Screen {

    //Screen attributes -----------------------------
    protected String name;
    protected String route;
    protected String main_color = ANSI_BLUE;
    protected String main_color_bold = BLUE_BOLD_BRIGHT;

    //Constructor -------------------------------------
    /**
     * Screen constructor stores the screen name and route
     * @param name      Screen name
     * @param route     Screen route
     */
    public Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    //Getters and Setters --------------------------------
    public String getName() { return name; }
    public String getRoute() { return route; }

    //Abstract Classes ------------------------------------
    /**
     * holds a screens console print out and processes user input
     */
    public abstract void render();
}
