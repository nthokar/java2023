package chess.game;

import chess.desk.Move;
import chess.parser.ConsoleMoveReader;

import java.awt.*;
import java.io.InputStream;

public class Player extends APlayer {
    private final ConsoleMoveReader reader;
    public Move getMove(){
            return reader.readMove();
    }
    public static Player consolePlayer(Color color){
        return new Player(System.in, color);
    }
    public Player(InputStream inputStream, Color color){
        super(color);
        reader = new ConsoleMoveReader(inputStream, color);
    }
}
