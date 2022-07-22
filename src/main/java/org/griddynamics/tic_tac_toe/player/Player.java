package org.griddynamics.tic_tac_toe.player;

import org.griddynamics.tic_tac_toe.Cell;
import org.griddynamics.tic_tac_toe.Grid;

import java.util.*;

/*
 * Abstract player class
 * All players are inherited from this class
 */
public abstract class Player {

    // Char to represent on cell/grid
    private final char sign;

    // Occupied cells
    private final List<Cell> occupied;

    // X coords set
    private final Set<Integer> xCoordSet;

    // Y coords set
    private final Set<Integer> yCoordSet;

    // Game grid
    private final Grid grid;

    /*
     * Default constructor
     */
    Player(Grid grid, char sign) {
        // Initialize by provided data
        this.grid = grid;
        this.sign = sign;

        // Initialize by default
        this.occupied = new LinkedList<>();
        this.xCoordSet = new LinkedHashSet<>(); // will be iterated many times
        this.yCoordSet = new LinkedHashSet<>(); // will be iterated many times
    }

    /*
     * Sign getter
     */
    public char getSign() {
        return sign;
    }

    /*
     * Grid getter
     */
    public Grid getGrid() {
        return this.grid;
    }

    /*
     * Occupied cells getter
     */
    List<Cell> getOccupied() {
        return this.occupied;
    }

    /*
     * Make move by this player
     * Cell is selected by SelectCell method
     */
    public void makeMove() {
        // Selecting cell to make move with
        Cell selected = this.selectCell();

        // Checking for null
        if (selected == null) {
            return;
        }

        // Occupying
        selected.occupyByPlayer(this);

        // Adding
        this.addCellToOccupied(selected);
    }

    /*
     * Make move by this player
     * Cell is selected from player's grid
     * by given coords
     */
    public void makeMove(int x, int y) {
        Cell selected = this.grid.getCell(x, y);
        if (selected.occupyByPlayer(this)) {
            this.addCellToOccupied(selected);
        }
    }

    /*
     * Abstract cell selector
     * has to be implemented in class children
     */
    abstract Cell selectCell();

    /*
     * Checks if player has won
     */
    public boolean isWinner() {
        // Check for enough occupied cells
        if (occupied.size() < grid.getDimension()) {
            return false;
        }

        // Checking
        return ((checkForRowWinWithout(0) != -1)          ||
                (checkForColumnWinWithout(0) != -1)       ||
                (checkForMainDiagonalWinWithout(0) != -1) ||
                (checkForAdditionalDiagonalWinWithout(0) != -1));
    }

    /*
     * Package-private checker for row win
     * Returns index of winning row,
     * if no row, returns -1
     */
    final int checkForRowWinWithout(int countToIgnore) {
        // Check for row win
        for (int current: xCoordSet) {
            if (occupied.stream().
                    filter(e -> e.getX() == current).
                    count() == grid.getDimension() - countToIgnore) {
                return current;
            }
        }

        // No success -> -1
        return -1;
    }

    /*
     * Package-private checker for column win
     * Returns index of winning column,
     * if no column, returns -1
     */
    final int checkForColumnWinWithout(int countToIgnore) {
        // Check for column win
        for (int current: yCoordSet) {
            if (occupied.stream().
                    filter(e -> e.getY() == current).
                    count() == grid.getDimension() - countToIgnore) {
                return current;
            }
        }

        // No success -> -1
        return -1;
    }

    /*
     * Package-private checker for main diagonal win
     * Returns 1, if win
     * Else -1
     */
    final int checkForMainDiagonalWinWithout(int countToIgnore) {
        // Check for main diagonal win
        if (occupied.stream().
                     filter(e -> e.getX() == e.getY()).
                     count() == grid.getDimension() - countToIgnore) {
            return 1;
        } else {
            return -1;
        }
    }

    /*
     * Package-private checker for
     * main diagonal win
     */
    final int checkForAdditionalDiagonalWinWithout(int countToIgnore) {
        // Check for additional diagonal win
        if (occupied.stream().
                filter(e -> e.getX() == (grid.getDimension() - 1) - e.getY()).
                count() == grid.getDimension() - countToIgnore) {
            return 1;
        } else {
            return -1;
        }
    }

    /*
     * Adds cell to list of occupied,
     * cell's coords to xCoordSet and yCoordSet
     */
    private void addCellToOccupied(Cell cell) {
        // Adding cell to occupy
        this.occupied.add(cell);

        // Adding cell coords to sets
        this.xCoordSet.add(cell.getX());
        this.yCoordSet.add(cell.getY());
    }
}
