package org.griddynamics.tic_tac_toe;

import org.griddynamics.tic_tac_toe.player.EasyBotPlayer;
import org.griddynamics.tic_tac_toe.player.HumanPlayer;
import org.griddynamics.tic_tac_toe.player.Player;
import org.griddynamics.tic_tac_toe.ui.GridCellGameTUI;

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
     * to create game with 2 human players
     */
    public static Game create2Human3x3() {
        // Creating new grid
        Grid grid = new Grid(3);

        // Creating players
        Player[] p = new Player[2];
        p[0] = new HumanPlayer(grid, 'X');
        p[1] = new HumanPlayer(grid, 'O');

        // Returning game instance
        return new Game(grid, p);
    }

    /*
     * Static method
     * to create game with 1 human and 1 easy bot
     * on 3x3
     */
    public static Game createHumanEasy3x3() {
        // Creating new grid
        Grid grid = new Grid(3);

        // Creating players
        Player[] p = new Player[2];
        p[0] = new HumanPlayer(grid, 'X');
        p[1] = new EasyBotPlayer(grid, 'O');

        // Returning game instance
        return new Game(grid, p);
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
                GridCellGameTUI.printGrid(grid);

                // Current player makes move
                current.makeMove();

                // Checking game state
                state = this.calcGameState(current);

                switch (state) {
                    case WIN -> {
                        GridCellGameTUI.printGrid(grid);
                        GridCellGameTUI.printWinEnd(current);
                        return;
                    }
                    case DRAW -> {
                        GridCellGameTUI.printGrid(grid);
                        GridCellGameTUI.printDrawEnd();
                        return;
                    }
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
