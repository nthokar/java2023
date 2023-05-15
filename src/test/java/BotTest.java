package test.java;

import chess.bot.Bot;
import chess.desk.Cell;
import chess.desk.Desk;
import chess.desk.Move;
import chess.figures.*;
import chess.game.Game;
import chess.game.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BotTest {

    @Test
    void buildEvaluateTree() {
        var desk = new Desk.Builder().setDefault().build();
        Game game = null;
        game = new Game(desk, null, new Bot(4, game, Color.WHITE), Player.consolePlayer(Color.BLACK));
        var bot = (Bot) game.getP1();
        var result = bot.buildEvaluateTree(1);
        game.getDesk().print();
        assertEquals(20, result.size());
        game.turn(new Move(new Cell(5,2), new Cell(5,4), Color.WHITE));
        game.getDesk().print();
        result = bot.buildEvaluateTree(1);
        assertEquals(30, result.size());
    }

    @Test
    void debug(){
        var desk = new Desk.Builder()
                .setDefault()
                .setFigure(null, new Cell(1,8))
                .setFigure(null, new Cell(2,8))
                .setFigure(null, new Cell(2,7))
                .setFigure(null, new Cell(5,7))
                .setFigure(null, new Cell(3,8))
                .setFigure(new Bishop(Color.BLACK), new Cell(4,7))
                .setFigure(new Pawn(Color.BLACK), new Cell(5,5))
                .setFigure(new Pawn(Color.BLACK), new Cell(4,6))
                .setFigure(new Knight(Color.BLACK), new Cell(3,6))
                .setFigure(new Rook(Color.BLACK), new Cell(2,6))

                .setFigure(new Queen(Color.WHITE), new Cell(1, 6))
                .setFigure(null, new Cell(4,1))
                .setFigure(null, new Cell(4,2))
                .setFigure(null, new Cell(3,2))
                .setFigure(new Pawn(Color.WHITE), new Cell(4,3))
                .setFigure(new Pawn(Color.WHITE), new Cell(3,3))
                .setFigure(new Queen(Color.WHITE), new Cell(1, 6))
                .build();
        Game game = null;
        game = new Game(desk, null, Player.consolePlayer(Color.WHITE), Player.consolePlayer(Color.BLACK));
        desk.print();
        game.turn(new Move(new Cell(1, 6), new Cell(1, 4), Color.WHITE));
        desk.print();
    }
    @Test
    void debug2(){
        var desk = new Desk.Builder()
                .setFigure(new Queen(Color.BLACK), new Cell(1,8))
                .setFigure(new King(Color.BLACK), new Cell(8,8))
                .setFigure(new King(Color.WHITE), new Cell(1,1))
                .build();
        Game game = null;
        game = new Game(desk, null, new Bot(3, game, Color.WHITE), Player.consolePlayer(Color.BLACK));
        desk.print();
        //game.turn(new Move(new Cell(1, 6), new Cell(1, 4), Color.WHITE));
        game.run();
        desk.print();
    }
}