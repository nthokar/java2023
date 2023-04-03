package chess.game;

import chess.desk.Move;
import chess.parser.ConsoleMoveReader;

import java.awt.*;
import java.io.InputStream;

public class Player implements IPlayer {
    InputStream inputStream;
    ConsoleMoveReader reader;
    Color color;

    public Move getMove(){
            return reader.readMove();
    }
    public  static Player consolePlayer(Color color){
        return new Player(System.in, color);
    }
    protected Player(InputStream inputStream, Color color){
        this.inputStream = inputStream;
        this.color = color;
        reader = new ConsoleMoveReader(inputStream, color);
    }
}
