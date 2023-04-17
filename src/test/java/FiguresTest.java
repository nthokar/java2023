package test.java;

import chess.desk.Cell;
import chess.desk.Desk;
import chess.desk.MoveChecker;
import chess.figures.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiguresTest {
    @Test
    public void canMoveBishop(){
        var desk = new Desk.Builder()
             .setFigure(new Bishop(Color.WHITE), new Cell(1,1))
             .setFigure(new Pawn(Color.WHITE), new Cell(4,4))
             .setFigure(new Pawn(Color.WHITE), new Cell(5,5))
             .setFigure(new Bishop(Color.BLACK), new Cell(8,8))
             .build();
        var moveChecker = new MoveChecker(null, desk);
        /*
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |     |     |     |     |  B  | 8
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |     |     |     |     |     | 7
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |     |     |     |     |     | 6
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |     |  p  |     |     |     | 5
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |  p  |     |     |     |     | 4
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |     |     |     |     |     | 3
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |     |     |     |     |     |     |     |     | 2
            +-----+-----+-----+-----+-----+-----+-----+-----+
            |  b  |     |     |     |     |     |     |     | 1
            +-----+-----+-----+-----+-----+-----+-----+-----+
               a     b     c     d     e     f     g     h
         */
        var bishopWhite = desk.getCell(1,1);
        var bishopBlack = desk.getCell(8,8);
        var pawnWhite = desk.getCell(4,4);
        var pawnWhite2 = desk.getCell(5,5);
        assertFalse(bishopWhite.getFigure().canMove(moveChecker.pathTo(bishopWhite, pawnWhite)));
        assertFalse(bishopWhite.getFigure().canMove(moveChecker.pathTo(bishopWhite, pawnWhite2)));
        assertFalse(bishopBlack.getFigure().canMove(moveChecker.pathTo(bishopBlack, pawnWhite)));
        assertTrue(bishopBlack.getFigure().canMove(moveChecker.pathTo(bishopBlack, pawnWhite2)));
    }

    @Test
    public void canMoveKnight() {
        var desk = new Desk.Builder()
                .setFigure(new Knight(Color.WHITE), new Cell(1, 1))
                .setFigure(new Pawn(Color.WHITE), new Cell(2, 3))
                .setFigure(new Pawn(Color.WHITE), new Cell(3, 2))
                .setFigure(new Knight(Color.BLACK), new Cell(4, 4))
                .build();
        var moveChecker = new MoveChecker(null, desk);
        /*
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |     |     |     |     |     |     |     | 8
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |     |     |     |     |     |     |     | 7
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |     |     |     |     |     |     |     | 6
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |     |     |     |     |     |     |     | 5
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |     |     |  N  |     |     |     |     | 4
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |  p  |     |     |     |     |     |     | 3
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |     |     |  p  |     |     |     |     |     | 2
        +-----+-----+-----+-----+-----+-----+-----+-----+
        |  n  |     |     |     |     |     |     |     | 1
        +-----+-----+-----+-----+-----+-----+-----+-----+
           a     b     c     d     e     f     g     h
         */
        var knightWhite = desk.getCell(1,1);
        var knightBlack = desk.getCell(4,4);
        var pawnWhite = desk.getCell(2,3);
        var pawnWhite2 = desk.getCell(3,2);
        assertFalse(knightWhite.getFigure().canMove(moveChecker.pathTo(knightWhite, pawnWhite)));
        assertFalse(knightWhite.getFigure().canMove(moveChecker.pathTo(knightWhite, pawnWhite2)));
        assertTrue(knightBlack.getFigure().canMove(moveChecker.pathTo(knightBlack, pawnWhite)));
        assertTrue(knightBlack.getFigure().canMove(moveChecker.pathTo(knightBlack, pawnWhite2)));

    }

    @Test
    public void canMoveKing(){
        var desk = new Desk.Builder()
                .setFigure(new Queen(Color.WHITE), new Cell(1, 1))
                .setFigure(new King(Color.BLACK), new Cell(4, 4))
                .build();
        var moveChecker = new MoveChecker(null, desk);

        var queenWhite = desk.getCell(1,1);
        var kingBlack = desk.getCell(4,4);
        desk.print();

        assertTrue(kingBlack.getFigure().canMove(moveChecker.pathTo(kingBlack, new Cell(5,5))));
        assertTrue(kingBlack.getFigure().canMove(moveChecker.pathTo(kingBlack, new Cell(4,5))));
        assertFalse(kingBlack.getFigure().canMove(moveChecker.pathTo(kingBlack, new Cell(4,6))));
    }
}