package org.griddynamics.tic_tac_toe.ui;

/*
 * TUI for bots
 */
public final class BotTUI {

    // easy bot move message
    private static final String BOT_MSG = "Making move level \"%s\"\n";

    /*
     * Prints easy bot make move message
     */
    public static void printEasyBotMakeMoveMessage() {
        System.out.printf(BOT_MSG, "easy");
    }

    public static void printMediumBotMakeMoveMessage() {
        System.out.printf(BOT_MSG, "medium");
    }

    public static void printHardBotMakeMoveMessage() {
        System.out.printf(BOT_MSG, "hard");
    }
}
