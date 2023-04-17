package chess.figures;

import chess.desk.Cell;
import chess.desk.MoveTemplate;
import chess.desk.MoveChecker;

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
    private final Set<MoveTemplate> Directions = Set.of(
            MoveChecker.Cells.get("longCastle"),
            MoveChecker.Cells.get("shortCastle")
    );

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
        if (canMove(cells)){
            cells[0].moveFigure(cells[1]);
            isMoved = false;
        }
    }

    @Override
    public boolean canMove(Cell[] cells) {
        if (cells.length == 0) {
            return false;
        }
        if (cells.length >= 3){
            //рокировка
            if (isMoved){
                return false;
            }
            var rookCell = cells[cells.length - 1];

            if (rookCell.getFigure() instanceof Rook &&
                    !(((Rook) rookCell.getFigure()).isMoved())){
                return true;
            }
        }
        else{
            if (cells[1].getFigure() != null && cells[1].getFigure().color == color) {
                return false;
            }
            return true;
        }
        return false;
    }
}
