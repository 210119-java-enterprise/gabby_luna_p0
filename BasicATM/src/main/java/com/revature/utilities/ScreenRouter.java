package com.revature.utilities;

import com.revature.screens.Screen;
import static com.revature.utilities.ConsoleDecoration.*;

/**
 * Class is used to navigate between different screen, using a private set of
 * screens. Main functino is to avoid multiple instances of a screen existing in memory
 * <p>
 * @author Wezley Singleton
 */
public class ScreenRouter {
    //ScreenRouter attributes -------------------------------
    private final Set<Screen> screens = new Set<>();

    //Getters and Setters -----------------------------------
    public Set<Screen> getScreens() {
        return screens;
    }

    /**
     * Used to load screens into screen set, helps ensure only one instance
     * of a screen exists in memory at a time
     * @param screen    screen being added to set of screens
     * @return          returns a copy of this
     */
    public ScreenRouter addScreen(Screen screen){
        System.out.println(ANSI_RED + "[LOG] - Loading " + screen.getName() + " into router" + ANSI_RESET);
        screens.add(screen);
        return this;
    }

    /**
     * method looks up given route in its list of screens and then calls its render method
     * to display chosen screen
     * @param route     used to find desired screen in set of screens
     */
    public void navigate(String route) {
        for (Screen screen : screens.toArray(Screen.class)) {
            //Look for route description matching requested route.
            if (screen.getRoute().equals(route)) {
                screen.render();
            }
        }
    }
}
