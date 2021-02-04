package com.revature.exceptions;

import static com.revature.utilities.ConsoleDecoration.CLEAR_SCREEN;
/**
 * Class for Authentication Exceptions intended to catch invalid credentials
 * input by the user. Includes:
 * <ul>
 * <li> invalid login credentials
 * <li> unavailable username and password combo
 * <li> invalid account number references
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public class AuthenticationException extends RuntimeException{
    /**
     * Clears the screen before printing a user-facing message informing them
     * of their input error.
     * <p>
     * @param message   includes a color coded message for the user
     */
    public AuthenticationException(String message){
        System.out.print(CLEAR_SCREEN);
        System.out.println(message);
    }
}
