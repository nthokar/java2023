package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveChecker;
import Chess.Desk.MoveTemplate;
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
    private final Set<MoveTemplate> Cells = Set.of(
            MoveChecker.Cells.get("upperLeftCell"),
            MoveChecker.Cells.get("upperRightCell"),
            MoveChecker.Cells.get("downLeftCell"),
            MoveChecker.Cells.get("downRightCell"),
            MoveChecker.Cells.get("rightCell"),
            MoveChecker.Cells.get("leftCell"),
            MoveChecker.Cells.get("upperCell"),
            MoveChecker.Cells.get("downCell")
    );
    private final Set<MoveTemplate> Directions = Set.of();

    @Override
    public Figure copy() {
        King king = new King(this.color);
        king.isMoved = this.isMoved;
        return king;
    }

    public Set<MoveTemplate> getCells() {
        return Cells;
    }
    public Set<MoveTemplate> getDirections() {
        return Directions;
    }

    @Override
    public void move(Cell[] cells) {
        if (cells.length == 0)
            return;
        cells[0].moveFigure(cells[1]);
    }
}
