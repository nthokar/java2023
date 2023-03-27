package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.MoveTemplate;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class Pawn extends Figure {
    public Pawn(Color color) {
        super("pawn", color);
        if (color == Color.BLACK) {
            legalCells = new MoveTemplate[]{ new MoveTemplate(0,-1)};
            possibleCells = new MoveTemplate[]{ new MoveTemplate(0, -2)};
            capture = new MoveTemplate[]{ new MoveTemplate(+1, -1), new MoveTemplate(-1, -1)};
            Cells = Set.of(
                    MoveChecker.Cells.get("downLeftCell"),
                    MoveChecker.Cells.get("downRightCell"),
                    MoveChecker.Cells.get("downCell"));
            Directions = Set.of(
                    MoveChecker.Directions.get("downVertical")
            );
        }
        else {
            legalCells = new MoveTemplate[]{ new MoveTemplate(0,+1)};
            possibleCells = new MoveTemplate[]{new MoveTemplate(0, +2)};
            capture = new MoveTemplate[]{ new MoveTemplate(-1, +1), new MoveTemplate(+1, +1)};
            Cells = Set.of(
                    MoveChecker.Cells.get("upperLeftCell"),
                    MoveChecker.Cells.get("upperRightCell"),
                    MoveChecker.Cells.get("upperCell"));
            Directions = Set.of(
                    MoveChecker.Directions.get("upperVertical")
            );
        }
    }
    private final Set<MoveTemplate> Cells;
    private final Set<MoveTemplate> Directions;

    @Override
    public Figure copy() {
        var c = new Pawn(this.color);
        c.isMoved = this.isMoved;
        return c;
    }

    public Set<MoveTemplate> getCells() {
        return Cells;
    }
    public Set<MoveTemplate> getDirections() {
        return Directions;
    }
    private boolean isMoved = false;
    public boolean isMoved() {
        return isMoved;
    }
    public final MoveTemplate[] legalCells;
    public final MoveTemplate[] possibleCells;
    public final MoveTemplate[] capture;
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
}
