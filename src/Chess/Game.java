package Chess;

import Chess.Desk.Desk;
import Chess.Desk.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    Desk desk;
    Player p1;
    Player p2;
    Color whichTurn = Color.WHITE;
    List<Move> moveHistory = new ArrayList<>();
    public void changeTurn(){
        whichTurn = whichTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    public static Game newGame(Player p1, Player p2){
        return new Game(p1, p2);
    }
    public static void startGame(){
        throw new RuntimeException();
    }
    private Game(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }
}
