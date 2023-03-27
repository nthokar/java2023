package Chess;

import Chess.Desk.Move;

import java.awt.*;
import java.io.InputStream;

public class Player {
    InputStream inputStream;
    Color color;

    public Move getMove(){
        throw new RuntimeException();
    }
    public  static Player consolePlayer(Color color){
        return new Player(System.in, color);
    }
    private Player(InputStream inputStream, Color color){
        this.inputStream = inputStream;
        this.color = color;
    }
}
