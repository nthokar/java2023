package Chess.Desk;

import Chess.Parser.ConsoleMoveReader;

import java.awt.*;
import java.io.InputStream;

//главный класс партии. Отвечает за инициализацию пвртии, и ее проведение.
public class GameManager {
    public GameManager(InputStream stream){
        cmr = new ConsoleMoveReader(stream);
    }
    public final Desk desk = new Desk(Desk.setDefault());
    private Color whichTurn = Color.WHITE;
    private ConsoleMoveReader cmr;
    public void changeTurn(){
        whichTurn = whichTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    public void startGame(){
        desk.print();
        for(;;){
            turn(cmr.readMove());
        }
    }
    public void turn(Move move){
        try {
            var cell = move.from;
            var cellTo = move.to;
            cell = desk.cells[cell.x][cell.y - 1];
            cellTo = desk.cells[cellTo.x][cellTo.y - 1];
            if (cell.getFigure() == null){
                System.out.println("choose possible move!");
                throw new RuntimeException();
            }
            System.out.println(desk.moveChecker.isUnderAttack(cell));
            var path = desk.moveChecker.pathTo(cell, cellTo);
            cell.getFigure().move(path);
            desk.print();
        }
        catch (Exception e){
            System.out.println("failed while reading move o_O");
        }
        changeTurn();
    }

}
