package org.griddynamics.tic_tac_toe.player;

import org.griddynamics.tic_tac_toe.Cell;
import org.griddynamics.tic_tac_toe.Game;
import org.griddynamics.tic_tac_toe.Grid;
import org.griddynamics.tic_tac_toe.ui.BotTUI;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * Hard bot implementation
 */
public final class HardBotPlayer extends Player {

    private final class FakePlayer extends Player {

        /*
         * Real player copy constructor
         */
        FakePlayer(Grid grid, Player origin) {
            // Calling parent constructor
            super(grid, origin.getSign());

            // Making moves
            for (Cell current : origin.getOccupied()) {
                this.makeMove(current.getX(), current.getY());
            }
        }

        /*
         * Dummy selectCell implementation
         */
        @Override
        protected Cell selectCell() {
            return null;
        }
    }

    /*
     * Game reference
     */
    private final Game game;

    /*
     * Index of this player in game's original array
     */
    private final int originalIndex;

    /*
     * Index of next free cell to select for move
     */
    private int nextCellToMoveIndex;

    /*
     * Default constructor
     */
    public HardBotPlayer(Grid grid, char sign, Game game, int originalIndex) {
        super(grid, sign);
        this.game = game;
        this.originalIndex = originalIndex;
    }

    /*
     * selectFreeCell override
     */
    @Override
    protected Cell selectCell() {
        // Printing message
        BotTUI.printHardBotMakeMoveMessage();

        // Going though the tree
        int temp = func(this.getGrid(),
             this.game.getPlayers(),
              this.originalIndex - 1);

        // Returning cell
        return this.getGrid().getFreeCells().get(nextCellToMoveIndex);
    }

    private int func(Grid lastGrid,
                     Player[] players,
                     int lastMoveMakerIndex) {

        // Evaluate current state
        if (players[originalIndex].isWinner()) {
            return 2;
        } else if (players[(players.length + lastMoveMakerIndex) % players.length].isWinner()) {
            return -1;
        } else if (lastGrid.isFull()) {
            return 1;
        }

        // Moving next
        int currentMoveMakerIndex = (lastMoveMakerIndex + 1) % players.length;
        Grid nextGrid;
        Cell nextMove;
        FakePlayer[] nextPlayers = new FakePlayer[players.length];
        List<Integer> values = new LinkedList<>();
        for (int i = 0; i < lastGrid.getFreeCells().size(); i++) {
            // Creating new grid
            nextGrid = new Grid(lastGrid.getDimension());

            // Creating players copies and placing them on the grid
            for (int j = 0; j < nextPlayers.length; j++) {
                nextPlayers[j] = new FakePlayer(nextGrid, players[j]);
            }

            // Making new move
            nextMove = nextGrid.getFreeCells().get(i);
            nextPlayers[currentMoveMakerIndex].makeMove(nextMove.getX(), nextMove.getY());

            // Moving down the tree
            values.add(func(nextGrid,
                     nextPlayers,
                     currentMoveMakerIndex));
        }

        // Returning
        int result;
        if (currentMoveMakerIndex == originalIndex) {
            result = Collections.max(values);
        } else {
            result = Collections.min(values);
        }
        this.nextCellToMoveIndex = values.indexOf(result);
        return result;
    }
}
