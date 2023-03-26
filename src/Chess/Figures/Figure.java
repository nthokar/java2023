package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.Vector;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public abstract class Figure {
    public Figure(String name, Color color){
        this.name = name;
        this.color = color;
    }
    public abstract Figure copy();
    public final String name;
    public final Color color;
    public Set<String> Directions;
    public abstract Set<Vector> getCells();
    public abstract Set<Vector> getDirections();
    public abstract Cell[] availableCells() throws ExecutionControl.NotImplementedException;
    public abstract void move(Cell cellFrom, Cell cellTo);
    public abstract void move(Cell[] cells);
    public abstract Cell[] pathTo(Cell cellFrom, Cell cellTo, Cell[][] board);


    @Override
    public String toString() {
        return color == Color.BLACK ?
                String.valueOf(name.charAt(0)).toUpperCase() : String.valueOf(name.charAt(0)).toLowerCase();
    }
}
