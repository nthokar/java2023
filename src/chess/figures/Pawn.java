package chess.figures;

import chess.desk.Cell;
import chess.desk.MoveChecker;
import chess.desk.MoveTemplate;

import java.awt.*;
import java.util.Objects;
import java.util.Set;

public class Pawn extends Figure {
    public Pawn(Color color) {
        super("pawn", color);
        if (color == Color.BLACK) {
            Cells = Set.of(
                    MoveChecker.Cells.get("downLeftCell"),
                    MoveChecker.Cells.get("downRightCell"),
                    MoveChecker.Cells.get("downCell")
                    //MoveChecker.Cells.get("downDownCell")
            );
            Directions = Set.of(
                    MoveChecker.Directions.get("downVertical")
            );
        }
        else {
            Cells = Set.of(
                    MoveChecker.Cells.get("upperLeftCell"),
                    MoveChecker.Cells.get("upperRightCell"),
                    MoveChecker.Cells.get("upperCell")
                    //MoveChecker.Cells.get("upperUpperCell")
                    );
            Directions = Set.of(
                    MoveChecker.Directions.get("upperVertical")
            );
        }
    }
    private final Set<MoveTemplate> Cells;
    private final Set<MoveTemplate> Directions;

    @Override
    public Figure copy() {
        var pawn = new Pawn(this.color);
        pawn.isMoved = this.isMoved;
        return pawn;
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
            cells[0].moveFigure(cells[cells.length - 1]);
            isMoved = true;
        }
    }

    @Override
    public boolean canMove(Cell[] cells) {
        if (cells.length == 0 || cells.length > 3)
            return false;
        if (cells[0].x != cells[1].x){
            if (cells[1].getFigure() != null && cells[1].getFigure().color != color)
                return true;
            else
                return false;
        }
        if (cells.length == 3){
            if (isMoved) {
                return false;
            }
            for (var i = 1; i < cells.length; i++){
                var cell = cells[i];
                if (cell.getFigure() != null) {
                    return false;
                }
            }
            if (Objects.nonNull(cells[cells.length - 1].getFigure())) {
                return false;
            }
            return true;
        }
        if (Objects.nonNull(cells[cells.length - 1].getFigure())) {
            return false;
        }
        return true;
    }
}
