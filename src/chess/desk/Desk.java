package chess.desk;

import chess.figures.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/*
класс для хранения данных осостоянии дооски и их преобразования.
 */
public class Desk {
    public static class Builder {
        public Builder() {
            cells = new Cell[8][8];
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    var currentCell = new Cell(j+1, i+1);
                    cells[j][i] = currentCell;
                }
            }
        }
        private final Cell[][] cells;
        public Builder setDefault(){
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
            cells[5][7].setFigure(new Bishop(Color.BLACK));
            cells[6][7].setFigure(new Knight(Color.BLACK));
            cells[7][7].setFigure(new Rook(Color.BLACK));

            cells[0][0].setFigure(new Rook(Color.WHITE));
            cells[1][0].setFigure(new Knight(Color.WHITE));
            cells[2][0].setFigure(new Bishop(Color.WHITE));
            cells[3][0].setFigure(new Queen(Color.WHITE));
            cells[4][0].setFigure(new King(Color.WHITE));
            cells[5][0].setFigure(new Bishop(Color.WHITE));
            cells[6][0].setFigure(new Knight(Color.WHITE));
            cells[7][0].setFigure(new Rook(Color.WHITE));
            return this;
        }
        public Builder setFigure(Figure figure, Cell cell){
            cell = cells[cell.x - 1][cell.y - 1];
            cell.setFigure(figure);
            return this;
        }
        public Desk build(){
            return new Desk(cells);
        }
    }
    //  длина доски
    private final Cell[][] cells;
    private int yLength = -1;


    //  вычисляет длину по Y
    public int yLength(){
        if (yLength != -1)
            return yLength;
        var max = 0;
        for (int i = 0; i < xLength(); i++){
            max = Math.max(max, cells[i].length);
        }
        yLength = max;
        return max;
    }


    //  вычисляет длину по X
    public int xLength(){
        return cells.length;
    }



    //  возвращает клетку короля указанного цвета
    public Cell getKingCell(Color color) {
        var figures = getFiguresByColor(color);
        for (var cell : figures) {
            if (cell.getFigure() instanceof King)
                return cell;
        }
        throw new RuntimeException("there is no king");
    }


    //  возвращает все фигуры на доске указанного цвета
    public List<Cell> getFiguresByColor(Color color){
        ArrayList<Cell> figures = new ArrayList<>();
        for (var i:cells){
            for (var cell:i){
                if (cell.getFigure() != null)
                    figures.add(cell);
            }
        }
        if (Objects.isNull(color)){
            return figures;
        }
        return figures.stream()
                .filter(cell -> cell.getFigure().color == color)
                .collect(Collectors.toList());
    }


    //  возвращает все фигуры отличного цвета
    public List<Cell> getEnemiesFigures(Color color){
        return getFiguresByColor(null).stream()
                .filter(cell -> cell.getFigure().color != color)
                .collect(Collectors.toList());
    }


    //  передвигает фигуру из начальной в конечную клетку
    public void moveFigureForce(Move move){
        move = projectMove(move);
        move.from.moveFigure(move.to);
    }


    //  проецирует ход на доску(возвращает ход основанный на клетках текущей доски,
    //  по кординатам поступивших клеток)
    public Move projectMove(Move move){
        if (Objects.isNull(move))
            throw new RuntimeException();
        var cellFrom = projectCell(move.from);
        var cellTo = projectCell(move.to);
        return new Move(cellFrom, cellTo, move.whichMove);
    }


    //  возвращает клетку по ее кординатам
    public Cell getCell(int x, int y){
        if ((x - 1 < 0 || xLength() < x ) || (y - 1 < 0 || yLength() < y ))
            throw new IllegalArgumentException("not valid cell");
        return cells[x - 1][y - 1];
    }


    //  проецирует клетку(возвращает клетку доски выбранную по кординатам поступивщей клетки)
    public Cell projectCell(Cell cell){
        if (Objects.isNull(cell) ||
                (cell.x - 1 < 0 || cell.x > xLength() ) ||
                (cell.y - 1 < 0 || cell.y > xLength() ))
            throw new IllegalArgumentException("not valid cell");
        return getCell(cell.x, cell.y);
    }


    //  оценка материала на доске(сравнение силы суммарной ценности всех фигур)
    public int evaluateMaterial(){
        var whitesMaterial = getFiguresByColor(Color.WHITE).stream()
                .map(x -> x.getFigure().material)
                .reduce(0, Integer::sum);
        var blacksMaterial = getFiguresByColor(Color.BLACK).stream()
                .map(x -> x.getFigure().material)
                .reduce(0, Integer::sum);
        return whitesMaterial - blacksMaterial;
    }


    //  полное копирование доски
    public Desk copy(){
        Cell[][] _cells = new Cell[xLength()][yLength()];
        for (var i = 0; i < xLength(); i++){
            for (var j = 0; j < yLength(); j++){
                _cells[i][j] = cells[i][j].copy();
            }
        }
        return new Desk(_cells);
    }

    //  вывод положения фигур на доске
    public void print2(){
        for (int i = 7; i >= 0; i--){
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
            System.out.print("|");
            for (int j = 0; j < 8; j++){
                System.out.printf(" " + ((i + j) % 2 == 0 ? ConsoleColors.WHITE_BACKGROUND : "")
                        + " %s " +
                        ConsoleColors.RESET + " |",cells[j][i]);
            }
            System.out.printf(" %s%n", i+1);
        }
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        System.out.println("   a     b     c     d     e     f     g     h");

    }

    public void print(){
        for (int i = 7; i >= 0; i--){
            for (int j = 0; j < 8; j++){
                System.out.print(cells[j][i].prettytoString());
            }
            System.out.printf("%s%n", ConsoleColors.RESET + " " + (i+1));
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
    public void print(Move move){
        for (int i = 7; i >= 0; i--){
            for (int j = 0; j < 8; j++) {
                String isHighlighted = "";
                String isHighlightedBright = "";
                if (move.from.equals(cells[j][i]))
                {
                    isHighlighted = ConsoleColors.YELLOW_BACKGROUND;
                }
                if (move.to.equals(cells[j][i]))
                {
                    isHighlighted = ConsoleColors.YELLOW_BACKGROUND_BRIGHT;
                }
                System.out.print(cells[j][i].prettytoString(isHighlighted));
            }
            System.out.printf("%s%n", ConsoleColors.RESET + " " + (i+1));
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }

    //FIXME
    private void castle(Castle castle){
        var kingCell = getKingCell(castle.whichMove);
        Cell rookCell = cells
            [castle.to.equals(new Cell(3, 0)) ? 0 : 7]
            [castle.whichMove.equals(Color.WHITE)   ? 0 : 7];
        if (Objects.isNull(rookCell.getFigure()) ||
                rookCell.getFigure().isMoved()  ||
                kingCell.getFigure().isMoved()){
            throw new RuntimeException();
        }
        kingCell.moveFigure(cells
                [castle.to.x - 1]
                [castle.whichMove.equals(Color.WHITE)   ? 0 : 7]);
        rookCell.moveFigure(cells
                [castle.to.equals(new Cell(3, 0)) ? castle.to.x : castle.to.x - 2 ]
                [castle.whichMove.equals(Color.WHITE)   ? 0 : 7]);
    }
    private Desk(Cell[][] cells){
        this.cells = cells;
    }
}
