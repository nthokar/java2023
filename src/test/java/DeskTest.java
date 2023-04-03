package test.java;

import chess.desk.Cell;
import chess.desk.Desk;
import chess.desk.Move;
import chess.figures.King;
import chess.figures.Pawn;
import chess.figures.Queen;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DeskTest {

    @Test
    void getKingCell() {
        var desk = new Desk
                .Builder()
                .setFigure(
                        new King(Color.WHITE),
                        new Cell(1,1))
                .build();
        var kingCell = desk.getKingCell(Color.WHITE);
        assertEquals(desk.cells[0][0], kingCell);
    }

    @Test
    void getWhites() {
        var desk = new Desk
                .Builder()
                .setFigure(
                        new Pawn(Color.WHITE),
                        new Cell(1, 1))
                .setFigure(
                        new Pawn(Color.WHITE),
                        new Cell(2, 1))
                .setFigure(
                        new Queen(Color.WHITE),
                        new Cell(3, 1))
                .setFigure(
                        new King(Color.WHITE),
                        new Cell(4, 1))
                .build();
        var whites = desk.getWhites();
        var expected = new Cell[4];
        expected[0] = desk.cells[0][0];
        expected[1] = desk.cells[1][0];
        expected[2] = desk.cells[2][0];
        expected[3] = desk.cells[3][0];
        assertArrayEquals(expected, whites);
    }

    @Test
    void copy() {
    }

    @Test
    void tryMove() {
        var desk = new Desk
                .Builder()
                .setFigure(
                        new Pawn(Color.WHITE),
                        new Cell(1, 1))
                .setFigure(
                        new Pawn(Color.WHITE),
                        new Cell(2, 1))
                .setFigure(
                        new Queen(Color.WHITE),
                        new Cell(3, 1))
                .setFigure(
                        new King(Color.WHITE),
                        new Cell(4, 1))
                .build();
        var isMoved1 = desk.tryMove(
                new Move(
                        new Cell(1,1),
                        new Cell(1,3),
                        Color.WHITE));
        desk.print();
        var isMoved2 = desk.tryMove(
                new Move(
                        new Cell(2,1),
                        new Cell(2,4),
                        Color.WHITE));
        desk.print();
        var isMoved3 = desk.tryMove(
                new Move(
                        new Cell(3,1),
                        new Cell(4,1),
                        Color.WHITE));
        assertTrue(isMoved1);
        assertFalse(isMoved2);
        assertFalse(isMoved3);
    }

    @Test
    void evaluateMaterial() {
    }

    @Test
    void castle() {
    }
}