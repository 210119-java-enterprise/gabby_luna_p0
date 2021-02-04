package com.revature.utilities;

/**
 * Class used for formatting console output. Has color code and spacing methods
 * @author Gabrielle Luna
 */
public class ConsoleDecoration {
    private static final int LINE_LENGTH = 70;

    //Console colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";

    //Bold High Intensity
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
//    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
//    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
//    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
//    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
//    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    public static final String CLEAR_SCREEN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    public static final String BORDER = "/************************************************************************/";

    /**
     * Used to print a line with a left and right boundary at a set width
     * @param s     string to print
     * @param x     length of string (string may include formatting that does not impact
     *              print out length
     */
    public static void FinishLine(String s, int x){
        System.out.print("/*" + s);
        for (int i = x; i < LINE_LENGTH; i++)
            System.out.print(" ");
        System.out.println("*/");
    }

    /**
     * Used to print a line with a left and right boundary at a set width with the text
     * centered on that line
     * @param s     string to print
     * @param x     length of string string may include formatting that does not impact
     *              print out length
     */
    public static void CenterLine(String s, int x){
        System.out.print("/*");
        int space = (LINE_LENGTH - x)/2;
        for (int i = 0; i < space; i++){
            System.out.print(" ");
        }
        System.out.print(s);
        for (int i = space + x; i < LINE_LENGTH; i++){
            System.out.print(" ");
        }
        System.out.print("*/\n");
    }
}
