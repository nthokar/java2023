package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.Vector;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Bishop extends Figure{
    public Bishop(Color color) {
        super("bishop", color);
    }
    public Bishop copy(){
        return new Bishop(this.color);
    }
    private final Set<Vector> Cells = Set.of();
    private final Set<Vector> Directions = Set.of(
            MoveChecker.Directions.get("upperLeftDiagonal"),
            MoveChecker.Directions.get("upperRightDiagonal"),
            MoveChecker.Directions.get("downLeftDiagonal"),
            MoveChecker.Directions.get("downRightDiagonal"));
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
    public Vector[] legalDirections = new Vector[]{
            new Vector(+1, -1), new Vector(-1, +1),
            new Vector(+1, +1), new Vector(-1, -1),

    };

    @Override
    public void move(Cell cellFrom, Cell cellTo) {

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
        if (cells[cells.length - 1].getFigure() != null && cells[cells.length - 1].getFigure().color == color){
            return;
        }
        cells[0].moveFigure(cells[cells.length - 1]);
    }

    @Override
    public Cell[] pathTo(Cell cellFrom, Cell cellTo, Cell[][] board){
        Vector vector = cellFrom.getVector(cellTo);
        double maxAbs = Math.max(Math.abs(vector.x), Math.abs(vector.y));
        Vector unitVector =  new Vector(vector.x/ maxAbs, vector.y/maxAbs);
        if (Arrays.stream(legalDirections).anyMatch(v -> v.equals(unitVector))){
            Cell currentCell = cellFrom;
            ArrayList<Cell> path = new ArrayList<>();
            path.add(currentCell);
            while (currentCell != cellTo){
                currentCell = board[currentCell.x + (int) unitVector.x - 1][currentCell.y + (int) unitVector.y - 1];
                path.add(currentCell);
            }
            return path.toArray(Cell[]::new);
        }
        return new Cell[0];
    }
}
