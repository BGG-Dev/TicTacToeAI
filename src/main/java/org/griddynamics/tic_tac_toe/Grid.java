package org.griddynamics.tic_tac_toe;

import java.util.ArrayList;
import java.util.List;

public final class Grid {

    // Cell matrix
    private final Cell[][] cells;

    // Free cell list
    private final List<Cell> freeCells;

    /*
     * Dimension constructor
     */
    public Grid(int dimension) {
        // Initializing matrix
        cells = new Cell[dimension][dimension];

        // Initializing lists
        freeCells = new ArrayList<>();

        // Initializing cells, adding to freeCells list
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cells[i][j] = new Cell(this, i, j);
                freeCells.add(cells[i][j]);
            }
        }
    }

    /*
     * Dimension getter
     */
    public int getDimension() {
        return this.cells[0].length;
    }

    /*
     * Cell getter
     */
    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }

    /*
     * Row getter
     */
    public Cell[] getRow(int rowIndex) {
        Cell[] result = new Cell[this.getDimension()];
        for (int i = 0; i < result.length; i++) {
            result[i] = cells[rowIndex][i];
        }
        return result;
    }

    /*
     * Column getter
     */
    public Cell[] getColumn(int columnIndex) {
        Cell[] result = new Cell[this.getDimension()];
        for (int i = 0; i < result.length; i++) {
            result[i] = cells[i][columnIndex];
        }
        return result;
    }

    /*
     * Main diagonal getter
     */
    public Cell[] getMainDiagonal() {
        Cell[] result = new Cell[this.getDimension()];
        for (int i = 0; i < result.length; i++) {
            result[i] = cells[i][i];
        }
        return result;
    }

    /*
     * Additional diagonal getter
     */
    public Cell[] getAdditionalDiagonal() {
        Cell[] result = new Cell[this.getDimension()];
        for (int i = 0; i < result.length; i++) {
            result[i] = cells[this.getDimension() - 1 - i][i];
        }
        return result;
    }

    /*
     * Returns list of free cells
     */
    public List<Cell> getFreeCells() {
        return this.freeCells;
    }

    /*
     * Removes given cell from list of free cells
     */
    void removeCellFromFree(Cell cell) {
        this.freeCells.remove(cell);
    }

    /*
     * Returns true, if there are free
     * (not occupied) cells
     * Otherwise - false
     */
    public boolean isFull() {
        return this.freeCells.size() == 0;
    }
}
