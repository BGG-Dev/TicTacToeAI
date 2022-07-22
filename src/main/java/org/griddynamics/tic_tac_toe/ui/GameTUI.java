package org.griddynamics.tic_tac_toe.ui;

import org.griddynamics.tic_tac_toe.Game;
import org.griddynamics.tic_tac_toe.player.Player;

import java.util.Scanner;

public class GameTUI {

    // Private static stdin scanner
    private static final Scanner scanIn = new Scanner(System.in);

    // Command request
    private static final String COMMAND_REQUEST = "Input command: ";

    // Bad parameters warning
    private static final String BAD_PARAMETERS_WARNING = "Bad parameters!";

    // User sign
    public static final String PLAYER_USER_COMMAND = "user";

    // Easy bot sign
    public static final String PLAYER_EASY_COMMAND = "easy";

    // Medium bot sign
    public static final String PLAYER_MEDIUM_COMMAND = "medium";

    // Hard bot sign
    public static final String PLAYER_HARD_COMMAND = "hard";

    // Player type enum
    public enum PlayerType {
        USER,
        EASY,
        MEDIUM,
        HARD
    }

    // Start command regex
    private static final String START_REGEX = "start (user|easy|medium|hard) (user|easy|medium|hard)";

    // Exit command regex
    private static final String EXIT_REGEX = "exit";

    /*
     * Returns win end string
     */
    public static void printWinEnd(Player winner) {
        System.out.println(winner.getSign() + " wins");
    }

    /*
     * Returns draw end string
     */
    public static void printDrawEnd() {
        System.out.println("Draw");
    }

    /*
     * Prints bad parameters warning
     */
    public static void printBadParametersWarning() {
        System.out.println(BAD_PARAMETERS_WARNING);
    }

    /*
     * Runs game UI cycle
     */
    public static void cycleGameUI() {
        String command;
        String[] playerTypeCommands;
        Game game;
        while (true) {
            // Printing command request
            System.out.print(COMMAND_REQUEST);

            // Reading
            command = scanIn.nextLine();

            // Making decision
            if (command.matches(EXIT_REGEX)) {
                System.exit(0);
            } else if (command.matches(START_REGEX)) {
                playerTypeCommands = command.split(" ");
                game = callCreate2User3x3(playerTypeCommands);
                game.run();
                System.out.println();
                continue;
            } else {
                printBadParametersWarning();
            }
        }
    }

    /*
     * Converts string input to enum input
     * and calls create2User3x3 from Game class
     * with converted params,
     * returns result of create2User3x3 call
     */
    private static Game callCreate2User3x3(String[] playerTypeCommands) {
        // Getting players types as enum
        PlayerType[] playerTypes = new PlayerType[playerTypeCommands.length - 1];
        for (int i = 1; i < playerTypeCommands.length; i++) {
            switch (playerTypeCommands[i]) {
                case GameTUI.PLAYER_USER_COMMAND:
                    playerTypes[i - 1] = PlayerType.USER;
                    break;
                case GameTUI.PLAYER_EASY_COMMAND:
                    playerTypes[i - 1] = PlayerType.EASY;
                    break;
                case GameTUI.PLAYER_MEDIUM_COMMAND:
                    playerTypes[i - 1] = PlayerType.MEDIUM;
                    break;
                case GameTUI.PLAYER_HARD_COMMAND:
                    playerTypes[i - 1] = PlayerType.HARD;
                    break;
            }
        }

        // Returning result of create2User3x3 call
        return Game.create2User3x3(playerTypes[0], playerTypes[1]);
    }
}
