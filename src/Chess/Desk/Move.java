package Chess.Desk;

public class Move {
    public final Cell from;
    public final Cell to;

    public Move(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }
}
