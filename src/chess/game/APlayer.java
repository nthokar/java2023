package chess.game;

import chess.desk.Move;
import lombok.Getter;

import java.awt.*;

public abstract class APlayer {
    @Getter
    private final Color color;
    public abstract Move getMove();
    protected APlayer(Color color) {
        this.color = color;
    }
}
