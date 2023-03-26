package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.Vector;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.Set;

public class King extends Figure{
    public King(Color color) {
        super("king", color);
    }
    private boolean isMoved = false;
    public boolean isMoved() {
        return isMoved;
    }
    public Cell[] legalCells = new Cell[]{
            new Cell(0, +1), new Cell(0, -1),
            new Cell(+1, +1), new Cell(+1, 0), new Cell(+1, -1),
            new Cell(-1, +1), new Cell(-1, 0), new Cell(-1, -1),
    };
    private final Set<Vector> Cells = Set.of(
            MoveChecker.Cells.get("upperLeftCell"),
            MoveChecker.Cells.get("upperRightCell"),
            MoveChecker.Cells.get("downLeftCell"),
            MoveChecker.Cells.get("downRightCell"),
            MoveChecker.Cells.get("rightCell"),
            MoveChecker.Cells.get("leftCell"),
            MoveChecker.Cells.get("upperCell"),
            MoveChecker.Cells.get("downCell")
    );
    private final Set<Vector> Directions = Set.of();

    @Override
    public Figure copy() {
        King king = new King(this.color);
        king.isMoved = this.isMoved;
        return king;
    }

    public Set<Vector> getCells() {
        return Cells;
    }
    public Set<Vector> getDirections() {
        return Directions;
    }

    @Override
    public Cell[] availableCells() throws ExecutionControl.NotImplementedException {
        return new Cell[0];
    }

    @Override
    public void move(Cell cellFrom, Cell cellTo) {
    }

    @Override
    public void move(Cell[] cells) {
        if (cells.length == 0)
            return;
        cells[0].moveFigure(cells[1]);
    }

    @Override
    public Cell[] pathTo(Cell cellFrom, Cell cellTo, Cell[][] board) {
        return new Cell[0];
    }
}
