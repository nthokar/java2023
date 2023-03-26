package Chess.Desk;

public class Vector{
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    public final double x;
    public final double y;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector)
            return this.x == ((Vector) o).x && this.y == ((Vector) o).y;
        if (o instanceof Cell)
            return this.x == ((Cell) o).x && this.y == ((Cell) o).y;
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (x * 11 * 11 + y * 11);
    }

    public boolean equals(Cell cell) {
        return this.x == (double) cell.x && this.y == (double) cell.y;
    }
    public Vector getUnitVector(){
        double maxAbs = Math.max(Math.abs(this.x), Math.abs(this.y));
        return new Vector(this.x/ maxAbs, this.y/maxAbs);
    }

}