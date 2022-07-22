package org.griddynamics.tic_tac_toe.player;

import org.griddynamics.tic_tac_toe.Cell;
import org.griddynamics.tic_tac_toe.Grid;
import org.griddynamics.tic_tac_toe.ui.BotTUI;

import java.util.Random;

/*
 * Easy bot player implementation
 */
public final class EasyBotPlayer extends Player {

    /*
     * Private static Random instance to make moves
     */
    private static final Random random = new Random();

    /*
     * Default constructor
     */
    public EasyBotPlayer(Grid grid, char sign) {
        super(grid, sign);
    }

    /*
     * selectFreeCell override
     */
    @Override
    protected Cell selectCell() {
        // Showing message
        BotTUI.printEasyBotMakeMoveMessage();

        // Check if grid is full
        if (this.getGrid().isFull()) {
            // No free -> return null
            return null;
        }

        // Endless cycle
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
