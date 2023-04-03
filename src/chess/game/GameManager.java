package chess.game;

import chess.bot.Bot;

import java.awt.*;

public class GameManager {
    public void startGame(Game game) {
        game.startGame();
    }

    public static void main(String[] args) {
        var gm = new GameManager();
        var game = Game.newGame(Player.consolePlayer(Color.WHITE), Player.consolePlayer(Color.BLACK));
        game.p1 = new Bot(3, game.desk, Color.WHITE);
        gm.startGame(game);
    }
}
