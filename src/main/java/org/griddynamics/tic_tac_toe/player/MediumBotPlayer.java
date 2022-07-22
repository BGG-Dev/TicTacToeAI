package org.griddynamics.tic_tac_toe.player;

import org.griddynamics.tic_tac_toe.Cell;
import org.griddynamics.tic_tac_toe.Game;
import org.griddynamics.tic_tac_toe.Grid;
import org.griddynamics.tic_tac_toe.ui.BotTUI;

import java.util.Random;
import java.util.LinkedList;
import java.util.Arrays;

/*
 * Medium bot implementation
 */
public final class MediumBotPlayer extends Player {

    /*
     * Private static Random instance to make moves
     */
    private static final Random random = new Random();

    /*
     * Game reference
     */
    private final Game game;

    /*
     * Default constructor
     */
    public MediumBotPlayer(Grid grid, char sign, Game game) {
        super(grid, sign);
        this.game = game;
    }

    /*
     * selectFreeCell override
     */
    @Override
    protected Cell selectCell() {
        // Printing message
        BotTUI.printMediumBotMakeMoveMessage();

        // Placing this player on first position in queue
        LinkedList<Player> playersWithThisAsFirst = new LinkedList<>(Arrays.asList(game.getPlayers()));
        playersWithThisAsFirst.remove(this);
        playersWithThisAsFirst.addFirst(this);

        // Iterating though queue
        Cell[] winResults;
        for (Player player : playersWithThisAsFirst) {
            winResults = selectCellBasedOnWinType(isWinnerWithoutOne(player));
            for (Cell cell : winResults) {
                if (cell != null) {
                    // Cell was selected, return
                    return cell;
                }
            }
        }

        // No need for "deny other's player win" -> make random
        return this.selectRandomCell();
    }

    /*
     * Private static method
     * Checks if given player is winner without 1 cell
     * Returns array of 4 integers
     * 1st: -1 or n | is row win (if yes - row index)
     * 2nd: -1 or n | is column win (if yes - column index)
     * 3rd: -1 or 1 | main diagonal win
     * 4th: -1 or 1 | additional diagonal win
     */
    private static int[] isWinnerWithoutOne(Player player) {
        // Creating array to store result
        int[] result = { -1, -1, -1, -1 };

        // Variable to store temporary calls result
        int temp;

        // Check for row win
        temp = player.checkForRowWinWithout(1);
        if (temp != -1) {
            result[0] = temp;
        }

        // Check for column win
        temp = player.checkForColumnWinWithout(1);
        if (temp != -1) {
            result[1] = temp;
        }

        // Check for main diagonal win
        if (player.checkForMainDiagonalWinWithout(1) != -1) {
            result[2] = 1;
        }

        // Check for additional win
        if (player.checkForAdditionalDiagonalWinWithout(1) != -1) {
            result[3] = 1;
        }

        // Return result
        return result;
    }

    /*
     * Private method to select cell based on type of win
     * Returns cell to occupy or null
     */
    private Cell[] selectCellBasedOnWinType(int[] winStates) {
        Cell[] result = new Cell[4];

        if (winStates[0] != -1) {
            result[0] = this.selectCellFromGiven(this.getGrid().getRow(winStates[0]));
        }

        if (winStates[1] != -1) {
            result[1] = this.selectCellFromGiven(this.getGrid().getColumn(winStates[1]));
        }

        if (winStates[2] != -1) {
            result[2] = this.selectCellFromGiven(this.getGrid().getMainDiagonal());
        }

        if (winStates[3] != -1) {
            result[3] = this.selectCellFromGiven(this.getGrid().getAdditionalDiagonal());
        }

        return result;
    }

    /*
     * Private method to select cell from given cell array
     * Returns cell to occupy or null, if no free in given array
     */
    private Cell selectCellFromGiven(Cell[] cells) {
        // Iterating though cells
        for (Cell current : cells) {
            // Checking if free
            if (current.getOccupantPlayer() == null) {
                return current;
            }
        }

        // No free -> null
        return null;
    }

    /*
     * Returns free cell, selected randomly, or null
     */
    private Cell selectRandomCell() {
        // Check if grid is full
        if (this.getGrid().isFull()) {
            // No free -> return null
            return null;
        }

        // There are free, select random
        int[] coords = new int[2];
        while(true) {
            // Generating coords
            coords[0] = random.nextInt(this.getGrid().getDimension());
            coords[1] = random.nextInt(this.getGrid().getDimension());

            // Check if cell is occupied
            if (this.getGrid().getCell(coords[0], coords[1]).getOccupantPlayer() == null) {
                return this.getGrid().getCell(coords[0], coords[1]);
            }
        }
    }
}
