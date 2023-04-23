package test.java;

import chess.bot.Bot;
import chess.desk.Desk;
import chess.game.Game;
import chess.game.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveCheckerTest {

    @Test
    void possibleMoves() {
        var desk = new Desk.Builder().setDefault().build();
        Game game = null;
        game = new Game(desk, null, new Bot(4, game, Color.WHITE), Player.consolePlayer(Color.BLACK));
        var pawn = game.getDesk().getCell(1,2);
        assertEquals(2, game.getMoveChecker().possibleMoves(pawn).size());
    }
}