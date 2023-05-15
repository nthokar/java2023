package application;

import chess.game.Game;

import java.util.List;
import java.util.UUID;

public class Lobby {
    public final String id;
    Game game;
    User host;
    List<User> users;
    public Lobby(User host) {
        this.id = UUID.randomUUID().toString();
        this.host = host;
    }
}
