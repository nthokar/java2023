package chess.desk;

import java.awt.*;

public class Move {
    public final Cell from;
    public final Cell to;
    public final Color whichMove;
    public Move(Cell from, Cell to, Color color) {
        this.from = from;
        this.to = to;
        whichMove = color;
    }
    public Move reverse(){
        return new Move(to, from, whichMove);
    }
    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
