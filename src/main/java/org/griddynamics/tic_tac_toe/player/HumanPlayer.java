package org.griddynamics.tic_tac_toe.player;

import org.griddynamics.tic_tac_toe.Cell;
import org.griddynamics.tic_tac_toe.Grid;
import org.griddynamics.tic_tac_toe.ui.HumanPlayerTUI;

/*
 * Represents human player
 */
public final class HumanPlayer extends Player {

    /*
     * Default constructor
     */
    public HumanPlayer(Grid grid, char sign) {
        super(grid, sign);
    }

    /*
     * Implementing selectCellToOccupy for HumanPlayer
     */
    @Override
    protected Cell selectCell() {
        // While user does not select free cell
        int[] coords;
        while (true) {
            // Getting coords from user
            coords = HumanPlayerTUI.getCoordsFromUser();

            // Processing coords
            if (!this.areCoordsInBounds(coords)) {
                // Coords are not in bounds
                HumanPlayerTUI.printCoordOutOfBoundError(this.getGrid().getDimension());
            } else if (this.getGrid().getCell(coords[0], coords[1]).getOccupantPlayer() == null) {
                // Cell is free, can return
                return this.getGrid().getCell(coords[0], coords[1]);
            } else {
                // Cell is already occupied -> show warning
                HumanPlayerTUI.printOccupiedCellCaptureWarning();
            }
        }
    }

    /*
     * Checks if coords are in bounds
     */
    private boolean areCoordsInBounds(int[] coords) {
        for (int coord : coords) {
            if (coord < 0 || coord >= this.getGrid().getDimension()) {
                return false;
            }
        }
        return true;
    }
}
