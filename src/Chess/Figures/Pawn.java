package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.Vector;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Pawn extends Figure {
    public Pawn(Color color) {
        super("pawn", color);
        if (color == Color.BLACK) {
            legalCells = new Vector[]{ new Vector(0,-1)};
            possibleCells = new Vector[]{ new Vector(0, -2)};
            capture = new Vector[]{ new Vector(+1, -1), new Vector(-1, -1)};
            Cells = Set.of(
                    MoveChecker.Cells.get("downLeftCell"),
                    MoveChecker.Cells.get("downRightCell"),
                    MoveChecker.Cells.get("downCell"));
            Directions = Set.of(
                    MoveChecker.Directions.get("downVertical")
            );
        }
        else {
            legalCells = new Vector[]{ new Vector(0,+1)};
            possibleCells = new Vector[]{new Vector(0, +2)};
            capture = new Vector[]{ new Vector(-1, +1), new Vector(+1, +1)};
            Cells = Set.of(
                    MoveChecker.Cells.get("upperLeftCell"),
                    MoveChecker.Cells.get("upperRightCell"),
                    MoveChecker.Cells.get("upperCell"));
            Directions = Set.of(
                    MoveChecker.Directions.get("upperVertical")
            );
        }
    }
    private final Set<Vector> Cells;
    private final Set<Vector> Directions;

    @Override
    public Figure copy() {
        var c = new Pawn(this.color);
        c.isMoved = this.isMoved;
        return c;
    }

    public Set<Vector> getCells() {
        return Cells;
    }
    public Set<Vector> getDirections() {
        return Directions;
    }
    private boolean isMoved = false;
    public boolean isMoved() {
        return isMoved;
    }
    public final Vector[] legalCells;
    public final Vector[] possibleCells;
    public final Vector[] capture;

    @Override
    public Cell[] availableCells() throws ExecutionControl.NotImplementedException {
        return new Cell[0];
    }

    @Override
    public void move(Cell cellFrom, Cell cellTo) {
        var vector = cellFrom.getVector(cellTo);
        if (Arrays.stream(legalCells).anyMatch(c -> c.equals(vector)) && cellTo.getFigure() == null){
            cellFrom.moveFigure(cellTo);
            isMoved = true;
        }
        else if (Arrays.stream(capture).anyMatch(c -> c.equals(vector)) && cellTo.getFigure().color != color){
            cellFrom.moveFigure(cellTo);
            isMoved = true;
        }
        else {
                if (Arrays.stream(possibleCells).anyMatch(c -> c.equals(vector)) && !isMoved && cellTo.getFigure() == null){
                cellFrom.moveFigure(cellTo);
                isMoved = true;
            }
        }
    }

    @Override
    public void move(Cell[] cells) {
        if (cells.length == 0)
            return;
        for (var i = 1; i < cells.length - 1; i++){
            var cell = cells[i];
            if (cell.getFigure() != null)
                return;
        }
        if (cells[cells.length - 1].getFigure() != null && cells[0].x == cells[1].x){
            return;
        }
        if (cells.length == 3){
            if (isMoved)
                return;
            cells[0].moveFigure(cells[cells.length - 1]);
            isMoved = true;
            return;
        }
        cells[0].moveFigure(cells[cells.length - 1]);
        isMoved = true;
    }

    @Override
    public Cell[] pathTo(Cell cellFrom, Cell cellTo, Cell[][] board) {
        return new Cell[0];
    }
}
