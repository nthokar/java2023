package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.MoveTemplate;
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
    private final Set<MoveTemplate> Cells = Set.of();
    private final Set<MoveTemplate> Directions = Set.of(
            MoveChecker.Directions.get("upperLeftDiagonal"),
            MoveChecker.Directions.get("upperRightDiagonal"),
            MoveChecker.Directions.get("downLeftDiagonal"),
            MoveChecker.Directions.get("downRightDiagonal"));
    public Set<MoveTemplate> getCells() {
        return Cells;
    }
    public Set<MoveTemplate> getDirections() {
        return Directions;
    }
    public MoveTemplate[] legalDirections = new MoveTemplate[]{
            new MoveTemplate(+1, -1), new MoveTemplate(-1, +1),
            new MoveTemplate(+1, +1), new MoveTemplate(-1, -1),
    };

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
}
