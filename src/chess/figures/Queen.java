package chess.figures;

import chess.desk.Cell;
import chess.game.MoveChecker;
import chess.desk.MoveTemplate;

import java.awt.*;
import java.util.Set;

public class Queen extends Figure{
    public Queen(Color color) {
        super("queen", color);
    }
    public MoveTemplate[] legalDirections = new MoveTemplate[]{
            new MoveTemplate(0, +1), new MoveTemplate(0, -1),
            new MoveTemplate(+1, 0), new MoveTemplate(-1, 0),
            new MoveTemplate(+1, -1), new MoveTemplate(-1, +1),
            new MoveTemplate(+1, +1), new MoveTemplate(-1, -1),
    };
    private final Set<MoveTemplate> Cells = Set.of();
    private final Set<MoveTemplate> Directions = Set.of(
            MoveChecker.Directions.get("upperLeftDiagonal"),
            MoveChecker.Directions.get("upperRightDiagonal"),
            MoveChecker.Directions.get("downLeftDiagonal"),
            MoveChecker.Directions.get("downRightDiagonal"),
            MoveChecker.Directions.get("rightHorizontal"),
            MoveChecker.Directions.get("leftHorizontal"),
            MoveChecker.Directions.get("upperVertical"),
            MoveChecker.Directions.get("downVertical"));
    @Override
    public Figure copy() {
        return new Queen(this.color);
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
            throw new IllegalArgumentException("IllegalMove");
        for (var i = 1; i < cells.length - 1; i++){
            var cell = cells[i];
            if (cell.getFigure() != null)
                throw new IllegalArgumentException("IllegalMove");

        }
        if (cells[cells.length - 1].getFigure() != null && cells[cells.length - 1].getFigure().color == color){
            throw new IllegalArgumentException("IllegalMove");
        }
        cells[0].moveFigure(cells[cells.length - 1]);
    }
}
