package Chess.Figures;

import Chess.Desk.Cell;
import Chess.Desk.MoveTemplate;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
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
    public abstract Set<MoveTemplate> getCells();
    public abstract Set<MoveTemplate> getDirections();
    public abstract void move(Cell[] cells);

    @Override
    public String toString() {
        return color == Color.BLACK ?
                String.valueOf(name.charAt(0)).toUpperCase() : String.valueOf(name.charAt(0)).toLowerCase();
    }
}
