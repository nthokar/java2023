package chess.desk;

import chess.figures.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

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
        public Builder setCastle(){
            int i = 1;
            for (int j = 0; j < 8; j++){
                cells[j][i].setFigure(new Pawn(Color.WHITE));
            }
            i = 6;
            for (int j = 0; j < 8; j++){
                cells[j][i].setFigure(new Pawn(Color.BLACK));
            }
            cells[0][7].setFigure(new Rook(Color.BLACK));
            cells[7][7].setFigure(new Rook(Color.BLACK));
            cells[4][7].setFigure(new King(Color.BLACK));

            cells[0][0].setFigure(new Rook(Color.WHITE));
            cells[7][0].setFigure(new Rook(Color.WHITE));
            cells[4][0].setFigure(new King(Color.WHITE));
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
    private int yLength = -1;
    //длина доски
    public final Cell[][] cells;
    public final MoveChecker moveChecker;
    public Stack<Move> history = new Stack<>();
    public Cell getKingCell(Color color){
        if (color == Color.WHITE){
            var whites = getWhites();
            for (var cell:whites){
                if (cell.getFigure() instanceof King)
                    return cell;
            }
        }
        else if(color == Color.BLACK){
            var blacks = getBlacks();
            for (var cell:blacks){
                if (cell.getFigure() instanceof King)
                    return cell;
            }
        }
        throw new RuntimeException("there is no king");
    }
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
    public int xLength(){
        return cells.length;
    }
    //клетки с белыми фигурами
    public Cell[] getWhites(){
        ArrayList<Cell> whites = new ArrayList<>();
        for (var i:cells){
            for (var cell:i){
                if (cell.getFigure() != null && cell.getFigure().color == Color.WHITE)
                    whites.add(cell);
            }
        }
        return whites.toArray(new Cell[0]);
    }
    //клеки с чрными фигурами
    public Cell[] getBlacks(){
        ArrayList<Cell> blacks = new ArrayList<>();
        for (var i:cells){
            for (var cell:i){
                if (cell.getFigure() != null && cell.getFigure().color == Color.BLACK)
                    blacks.add(cell);
            }
        }
        return blacks.toArray(new Cell[0]);
    }
    private Desk(Cell[][] cells){
        this.cells = cells;
        this.moveChecker = new MoveChecker(this);
    }
    //метод возвращает ответ на вопрос возможен ли ход, и если да, то совершает его
    public boolean tryMove(Move move){
        try {
            move = projectMove(move);
            if (move.from.getFigure() == null){
                return false;
            }

//            var deskValidation = this.copy();
//            deskValidation.moveFigure(move);
//
//            if (deskValidation.moveChecker.isUnderAttack(deskValidation.getKingCell(move.whichMove))){
//                return false;
//            }

            moveFigure(move);
            if (isKingUnderAttack(move.whichMove))
            {
                undoMove();
                return false;
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    private void moveFigureForce(Move move){
        move = projectMove(move);
        move.from.moveFigure(move.to);
        history.push(move);
    }
    private void moveFigure(Move move){
        move = projectMove(move);
        if (move.from.getFigure() == null){
            throw new RuntimeException();
        }
        var path = moveChecker.pathTo(move.from, move.to);
        move.from.getFigure().move(path);
        history.push(move);
    }
    public void undoMove(){
        var move = history.pop();
        moveFigure(move.reverse());
    }
    public void loadByHistory(ArrayList<Move> moves){
        for (var move:moves) {
            tryMove(move);
        }
    }
    public Move projectMove(Move move){
        var cellFrom = this.cells[move.from.x - 1][move.from .y - 1];
        var cellTo =    this.cells[move.to.x - 1][move.to.y - 1];
        return new Move(cellFrom, cellTo, move.whichMove);
//        return new Move(
//                this.cells[move.from.x - 1][move.from .y - 1],
//                this.cells[move.to.x - 1][move.to.y - 1],
//                move.whichMove);
    }
    private boolean isKingUnderAttack(Color color){
        return moveChecker.isUnderAttack(getKingCell(color));
    }
    public int evaluateMaterial(){
        var whitesMaterial = Arrays.stream(getWhites())
                .map(x -> x.getFigure().material)
                .reduce(0, Integer::sum);
        var blacksMaterial = Arrays.stream(getBlacks())
                .map(x -> x.getFigure().material)
                .reduce(0, Integer::sum);
        return whitesMaterial - blacksMaterial;
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
    public void castle(Castle castle){
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
}
