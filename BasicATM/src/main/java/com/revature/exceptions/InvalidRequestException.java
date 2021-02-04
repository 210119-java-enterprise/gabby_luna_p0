package com.revature.exceptions;

import static com.revature.utilities.ConsoleDecoration.CLEAR_SCREEN;

/**
 * Class for InvalidRequestExceptions intended to respond to user choices that
 * do not align with available choices. Includes:
 * <ul>
 * <li> user input that is an Empty string
 * <p>
 * @author Wezley Singleton
 * @author Gabrielle Luna
 */
public class InvalidRequestException extends RuntimeException{
    /**
     * Clears the screen before printing a user-facing message informing them
     * of their input error.
     * <p>
     * @param message   includes a color coded message for the user
     */
    public InvalidRequestException(String message){
        System.out.print(CLEAR_SCREEN);
        System.out.println(message);
    }
}
