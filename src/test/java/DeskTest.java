package test.java;

import chess.desk.Cell;
import chess.desk.Desk;
import chess.figures.King;
import chess.figures.Pawn;
import chess.figures.Queen;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(desk.getCell(1,1), kingCell);
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
        var whites = desk.getFiguresByColor(Color.WHITE).toArray();
        var expected = new Cell[4];
        expected[0] = desk.getCell(0,0);
        expected[1] = desk.getCell(1,0);
        expected[2] = desk.getCell(2,0);
        expected[3] = desk.getCell(3,0);
        assertArrayEquals(expected, whites);
    }

    @Test
    void moveFigureForce() {

    }

    @Test
    void projectMove() {

    }

    @Test
    void projectCell() {

    }

    @Test
    void evaluateMaterial() {
        Desk desk = new Desk.Builder()
                .setFigure(new Queen(Color.WHITE), new Cell(1,1))
                .build();
        assertEquals(9, desk.evaluateMaterial());


        desk = new Desk.Builder()
                .setFigure(new Queen(Color.WHITE), new Cell(1,1))
                .setFigure(new Queen(Color.BLACK), new Cell(2,2))
                .build();
        assertEquals(0, desk.evaluateMaterial());


        desk = new Desk.Builder()
                .setFigure(new Queen(Color.WHITE), new Cell(1,1))
                .setFigure(new Queen(Color.BLACK), new Cell(2,2))
                .setFigure(new Queen(Color.BLACK), new Cell(2,3))
                .build();
        assertEquals(-9, desk.evaluateMaterial());
    }
}