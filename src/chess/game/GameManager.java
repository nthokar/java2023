package chess.game;

import application.User;
import chess.desk.Desk;

public class GameManager {
    User user;
    public GameManager(User user){
        this.user = user;
    }
    public void startGame(Game game) {
        game.run();
    }

    public Game getGameWithBot(){
        var desk = new Desk.Builder().setDefault().build();
        Game.Builder game = new Game.Builder().setDeskDefault().whiteUser(user);
        return game.build();
    }

    public static void main(String[] args) {
        var gm = new GameManager(new User(System.in));
        gm.getGameWithBot().run();
    }
}
