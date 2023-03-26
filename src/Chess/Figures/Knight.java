package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.Vector;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class Knight extends Figure{
    public Knight(Color color) {
        super("knight", color);

    }
    public final Vector[] legalCells = new Vector[] {
        new Vector(+2, +1), new Vector(+2, -1),
        new Vector(-2, +1), new Vector(-2, -1),
        new Vector(+1, +2), new Vector(+1, -2),
        new Vector(-1, +2), new Vector(-1, -2) };

    private final Set<Vector> Cells = Set.of(
            MoveChecker.Cells.get("knightUpLeft"),
            MoveChecker.Cells.get("knightUpRight"),
            MoveChecker.Cells.get("knightDownLeft"),
            MoveChecker.Cells.get("knightDownRight"),
            MoveChecker.Cells.get("knightLeftUp"),
            MoveChecker.Cells.get("knightRightUp"),
            MoveChecker.Cells.get("knightLeftDown"),
            MoveChecker.Cells.get("knightRightDown")
    );
    private final Set<Vector> Directions = Set.of();

    @Override
    public Figure copy() {
        return new Knight(this.color);
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
        Vector vector = cellFrom.getVector(cellTo);

        if (Arrays.stream(legalCells).anyMatch(c -> c.equals(vector)) &&
                (cellTo.getFigure() == null || cellTo.getFigure().color != color)){
            cellFrom.moveFigure(cellTo);
        }
    }

    @Override
    public void move(Cell[] cells) {
        if (cells.length == 0)
            return;
        cells[0].moveFigure(cells[1]);
    }

    @Override
    public Cell[] pathTo(Cell cellFrom, Cell cellTo, Cell[][] board) {
        cellFrom = board[cellFrom.x - 1][cellFrom.y - 1];
        cellTo = board[cellTo.x - 1][cellTo.y - 1];
        Vector vector = cellFrom.getVector(cellTo);
        if (Arrays.stream(legalCells).anyMatch(c -> c.equals(vector)) &&
                (cellTo.getFigure() == null || cellTo.getFigure().color != color)) {
            return new Cell[]{cellFrom, cellTo};
        }
        return new Cell[0];
    }

    @Override
    public String toString() {
        return color == Color.BLACK ?
                String.valueOf(name.charAt(1)).toUpperCase() : String.valueOf(name.charAt(1)).toLowerCase();
    }
}
