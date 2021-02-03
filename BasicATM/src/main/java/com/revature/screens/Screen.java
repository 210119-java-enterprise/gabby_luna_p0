package com.revature.screens;

import static com.revature.utilities.ConsoleDecoration.ANSI_BLUE;
import static com.revature.utilities.ConsoleDecoration.BLUE_BOLD_BRIGHT;

public abstract class Screen {

    protected String name;
    protected String route;
    protected String main_color = ANSI_BLUE;
    protected String main_color_bold = BLUE_BOLD_BRIGHT;

    public Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    public String getName() { return name; }
    public String getRoute() { return route; }

    public abstract void render();
}
