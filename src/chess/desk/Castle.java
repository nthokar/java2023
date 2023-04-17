package chess.desk;

import java.awt.*;
//FIXME
public class Castle extends Move {
    public Castle(Cell from, Cell to, Color color) {
        super(from, to, color);
        var moveTemplate = new MoveTemplate(
                Math.abs(from.x - to.x),
                Math.abs(from.y - to.y));
        if (moveTemplate.x != 2 && moveTemplate.y != 0)
            throw new RuntimeException();
    }
}
