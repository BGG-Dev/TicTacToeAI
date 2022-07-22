package org.griddynamics.tic_tac_toe;

import org.griddynamics.tic_tac_toe.player.*;
import org.griddynamics.tic_tac_toe.ui.GameTUI;
import org.griddynamics.tic_tac_toe.ui.GridTUI;

/*
 * Game class
 * Represents TicTacToe game
 */
public final class Game {

    // Game grid
    private final Grid grid;

    // Player array
    private final Player[] players;

    /*
     * Default constructor
     */
    private Game(Grid grid, Player[] players) {
        this.grid = grid;
        this.players = players;
    }

    /*
     * Game state
     */
    private enum GameState {
        WIN,
        DRAW,
        CONTINUE
    }

    /*
     * Static method
     * to create 3x3 game with 2 outside-specified
     * players. Players are defined by string names
     */
    public static Game create2User3x3(GameTUI.PlayerType player1Type,
                                      GameTUI.PlayerType player2Type) {
        // Creating new grid
        Grid grid = new Grid(3);

        // Creating players
        Player[] players = new Player[2];

        // Creating game instance
        Game game = new Game(grid, players);

        // Initializing players
        GameTUI.PlayerType[] playerTypes = { player1Type, player2Type };
        char[] playerSigns = { 'X', 'O' };
        for (int i = 0; i < players.length; i++) {
            if (playerTypes[i] == GameTUI.PlayerType.USER) {
                players[i] = new HumanPlayer(grid, playerSigns[i]);
            } else if (playerTypes[i] == GameTUI.PlayerType.EASY) {
                players[i] = new EasyBotPlayer(grid, playerSigns[i]);
            } else if (playerTypes[i] == GameTUI.PlayerType.MEDIUM) {
                players[i] = new MediumBotPlayer(grid, playerSigns[i], game);
            } else if (playerTypes[i] == GameTUI.PlayerType.HARD) {
                players[i] = new HardBotPlayer(grid, playerSigns[i], game, i);
            }
        }

        // Returning game
        return game;
    }

    /*
     * players getter
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /*
     * Runs game instance
     */
    public void run() {
        // Game cycle
        GameState state = GameState.CONTINUE;
        while(state == GameState.CONTINUE) {
            // Cycling though players
            for (Player current : this.players) {
                // Printing grid
                GridTUI.printGrid(grid);

                // Current player makes move
                current.makeMove();

                // Checking game state
                state = this.calcGameState(current);

                switch (state) {
                    case WIN:
                        GridTUI.printGrid(grid);
                        GameTUI.printWinEnd(current);
                        return;
                    case DRAW:
                        GridTUI.printGrid(grid);
                        GameTUI.printDrawEnd();
                        return;
                }
            }
        }
    }

    /*
     * Calculates game
     */
    private GameState calcGameState(Player last) {
        // Checking if win
        if (last.isWinner()) {
            return GameState.WIN;
        }

        // Is there any free cells?
        if (this.grid.isFull()) {
            // No winner and no free -> draw
            return GameState.DRAW;
        }

        // Free left -> continue
        return GameState.CONTINUE;
    }
}
