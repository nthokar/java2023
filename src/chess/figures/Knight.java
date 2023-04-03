package chess.figures;

import chess.desk.Cell;
import chess.game.MoveChecker;
import chess.desk.MoveTemplate;

import java.awt.*;
import java.util.Set;

public class Knight extends Figure{
    public Knight(Color color) {
        super("knight", color);

    }
    public final MoveTemplate[] legalCells = new MoveTemplate[] {
        new MoveTemplate(+2, +1), new MoveTemplate(+2, -1),
        new MoveTemplate(-2, +1), new MoveTemplate(-2, -1),
        new MoveTemplate(+1, +2), new MoveTemplate(+1, -2),
        new MoveTemplate(-1, +2), new MoveTemplate(-1, -2) };

    private final Set<MoveTemplate> Cells = Set.of(
            MoveChecker.Cells.get("knightUpLeft"),
            MoveChecker.Cells.get("knightUpRight"),
            MoveChecker.Cells.get("knightDownLeft"),
            MoveChecker.Cells.get("knightDownRight"),
            MoveChecker.Cells.get("knightLeftUp"),
            MoveChecker.Cells.get("knightRightUp"),
            MoveChecker.Cells.get("knightLeftDown"),
            MoveChecker.Cells.get("knightRightDown")
    );
    private final Set<MoveTemplate> Directions = Set.of();

    @Override
    public Figure copy() {
        return new Knight(this.color);
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
        cells[0].moveFigure(cells[1]);
    }
    @Override
    public String toString() {
        return color == Color.BLACK ?
                String.valueOf(name.charAt(1)).toUpperCase() : String.valueOf(name.charAt(1)).toLowerCase();
    }
}
