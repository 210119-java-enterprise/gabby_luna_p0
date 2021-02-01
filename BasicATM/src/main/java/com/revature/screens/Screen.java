package com.revature.screens;

public abstract class Screen {

    protected String name;
    protected String route;
    protected String main_color;
    protected String main_color_bold;

    public Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    public String getName() { return name; }
    public String getRoute() { return route; }

    public abstract void render();
}
