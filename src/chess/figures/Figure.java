package chess.figures;

import chess.desk.Cell;
import chess.desk.MoveTemplate;

import java.awt.*;
import java.util.Set;

public abstract class Figure {
    protected boolean isMoved = false;
    public final String name;
    public final Color color;
    public final int material;


    Figure(String name, Color color){
        this.name = name;
        this.color = color;
        this.material = getMaterial();
    }


    public abstract Set<MoveTemplate> getCells();


    public abstract Set<MoveTemplate> getDirections();


    public boolean isMoved() {
        return isMoved;
    }


    public abstract void move(Cell[] cells);


    public abstract boolean canMove(Cell[] cells);


    @Override
    public String toString() {
        return color == Color.BLACK ?
                String.valueOf(name.charAt(0)).toUpperCase() : String.valueOf(name.charAt(0)).toLowerCase();
    }


    public abstract Figure copy();


    private int getMaterial(){
        switch (name){
            case "pawn":
                return 1;
            case "knight":
                return 3;
            case "bishop":
                return 3;
            case "rook":
                return 5;
            case "queen":
                return 9;
        }
        return 0;
    }
}
