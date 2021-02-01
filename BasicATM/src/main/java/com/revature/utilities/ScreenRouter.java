package com.revature.utilities;

import com.revature.screens.Screen;
import static com.revature.utilities.ConsoleDecoration.*;

public class ScreenRouter {

    private Set<Screen> screens = new Set<>();

    public Set<Screen> getScreens() {
        return screens;
    }

    //Load screens into screen set to ensure only one instance.
    public ScreenRouter addScreen(Screen screen){
        System.out.println(ANSI_RED + "[LOG] - Loading " + screen.getName() + " into router" + ANSI_RESET);
        screens.add(screen);
        return this;
    }

    public void navigate(String route) {
        //Todo: look into
        for (Screen screen : screens.toArray(Screen.class)) {
            //Look for route description matching requested route.
            if (screen.getRoute().equals(route)) {
                screen.render();
            }
        }
    }
}
