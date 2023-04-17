package chess.game;

import chess.bot.Bot;
import chess.desk.Desk;

import java.awt.*;

public class GameManager {
    public void startGame(Game game) {
        game.startGame();
    }

    public static void main(String[] args) {
        var gm = new GameManager();
        var desk = new Desk.Builder().setDefault().build();
        Game game = null;
        game = new Game(desk, null, new Bot(3, game, Color.WHITE), Player.consolePlayer(Color.BLACK));
        //game.p2 = new Bot(3, game.desk, Color.BLACK);
        gm.startGame(game);
    }
}
