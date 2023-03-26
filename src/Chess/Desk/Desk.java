package Chess.Desk;

import Chess.Figures.*;

import java.awt.*;
import java.util.ArrayList;

/*
класс для хранения данных осостоянии дооски и их преобразования.
 */
public class Desk {
    //  (x,y;cell-color;figure)
    //  EXAMPLE 1,1;default;;
    //  cell-color default means colors determine by cell even
    public final Cell[][] cells;
    //ширина доски в клетках
    public int xLength(){
        return cells.length;
    }
    private int _yLength = -1;
    //длина доски
    public int yLength(){
        if (_yLength != -1)
            return _yLength;
        var max = 0;
        for (int i = 0; i < xLength(); i++){
            max = Math.max(max, cells[i].length);
        }
        _yLength = max;
        return max;
    }

    private Cell[] whites;
    //клетки с белыми фигурами
    public Cell[] getWhites(){
        ArrayList<Cell> _whites = new ArrayList<>();
        for (var i:cells){
            for (var cell:i){
                if (cell.getFigure() != null && cell.getFigure().color == Color.WHITE)
                    _whites.add(cell);
            }
        }
        whites = _whites.toArray(new Cell[0]);
        return whites;
    }

    private Cell[] blacks;
    //клеки с чрными фигурами
    public Cell[] getBlacks(){
        ArrayList<Cell> _blacks = new ArrayList<>();
        for (var i:cells){
            for (var cell:i){
                if (cell.getFigure() != null && cell.getFigure().color == Color.BLACK)
                    _blacks.add(cell);
            }
        }
        blacks = _blacks.toArray(new Cell[0]);
        return blacks;
    }
    public final MoveChecker moveChecker;
    public Desk(Cell[][] cells){
        this.cells = cells;
        this.moveChecker = new MoveChecker(this);
    }

    public Desk copy(){
        Cell[][] _cells = new Cell[xLength()][yLength()];
        for (var i = 0; i < xLength(); i++){
            for (var j = 0; j < yLength(); j++){
                _cells[i][j] = cells[i][j].copy();
            }
        }
        return new Desk(_cells);
    }

    public void print(){
        for (int i = 7; i >= 0; i--){
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
            System.out.print("|");
            for (int j = 0; j < 8; j++){
                System.out.printf("  %s  |",cells[j][i]);
            }
            System.out.printf(" %s%n", i+1);
        }
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        System.out.println("   a     b     c     d     e     f     g     h");

    }

    static public Cell[][] setDefault(){
        Cell[][] cells = new Cell[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                var currentCell = new Cell(j+1, i+1);
                cells[j][i] = currentCell;
            }
        }
        int i = 1;
        for (int j = 0; j < 8; j++){
            cells[j][i].setFigure(new Pawn(Color.WHITE));
        }
        i = 6;
        for (int j = 0; j < 8; j++){
            cells[j][i].setFigure(new Pawn(Color.BLACK));
        }
        cells[0][7].setFigure(new Rook(Color.BLACK));
        cells[1][7].setFigure(new Knight(Color.BLACK));
        cells[2][7].setFigure(new Bishop(Color.BLACK));
        cells[3][7].setFigure(new Queen(Color.BLACK));
        cells[4][7].setFigure(new King(Color.BLACK));

        cells[0][0].setFigure(new Rook(Color.WHITE));
        cells[1][0].setFigure(new Knight(Color.WHITE));
        cells[2][0].setFigure(new Bishop(Color.WHITE));
        cells[3][0].setFigure(new Queen(Color.WHITE));
        cells[4][0].setFigure(new King(Color.WHITE));
        return cells;
    }
}
