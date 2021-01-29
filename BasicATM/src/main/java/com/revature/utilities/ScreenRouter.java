package com.revature.utilities;

import com.revature.screens.Screen;

public class ScreenRouter {

    private Set<Screen> screens = new Set<>();

    public Set<Screen> getScreens() {
        return screens;
    }

    public ScreenRouter addScreen(Screen screen){
        System.out.println( "[LOG] - Loading " + screen.getName() + " into router");
        screens.add(screen);
        return this;
    }

}
