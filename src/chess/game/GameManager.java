package chess.game;

import chess.bot.Bot;
import chess.desk.Cell;
import chess.desk.Desk;
import chess.desk.Move;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    public void startGame(Game game) {
        game.startGame();
    }

    public static void main(String[] args) {
        var gm = new GameManager();
        var desk = new Desk.Builder().setDefault().build();
        desk.loadByHistory(new ArrayList<>(){{add(new Move(new Cell(5,2), new Cell(5, 4), Color.WHITE));}});
        var game = Game.newGame(Player.consolePlayer(Color.WHITE), Player.consolePlayer(Color.BLACK), desk);
        game.p1 = new Bot(3, game.desk, Color.WHITE);
        //game.p2 = new Bot(3, game.desk, Color.BLACK);
        gm.startGame(game);
    }
}
