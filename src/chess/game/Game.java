package chess.game;

import chess.desk.Castle;
import chess.desk.Desk;
import chess.desk.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
//главный класс партии. Отвечает за инициализацию пвртии, и ее проведение.
public class Game {
    Desk desk;
    IPlayer p1;
    IPlayer p2;
    Color whichTurn = Color.WHITE;
    List<Move> moveHistory = new ArrayList<>();
    public void changeTurn(){
        whichTurn = whichTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    public void turn(Move move){
        try {
            if (move == null){
            }
            if (move instanceof Castle){
                desk.castle((Castle) move);
                desk.print();
                changeTurn();
                return;
            }
            move = desk.projectMove(move);
            var cell = move.from;
            var cellTo = move.to;
            if (cell.getFigure() == null || cell.getFigure().color != whichTurn){
                System.out.println("choose possible move!");
                throw new RuntimeException();
            }
            var deskValidation = desk.copy();
            var path = deskValidation.moveChecker.pathTo(cell, cellTo);
            cell = deskValidation.cells[cell.x - 1][cell.y - 1];
            cell.getFigure().move(path);
            if (deskValidation.moveChecker.isUnderAttack(deskValidation.getKingCell(whichTurn))){
                throw new RuntimeException();
            }
            cell = move.from;
            path = desk.moveChecker.pathTo(cell, cellTo);
            cell.getFigure().move(path);
            desk.print();
            changeTurn();
        }
        catch (Exception e){
            System.out.println("failed while reading move o_O");
        }
    }
    public static Game newGame(IPlayer p1, IPlayer p2){
        return new Game(p1, p2);
    }
    public void startGame(){
        desk.print();
        for(;;){
            if (whichTurn == Color.WHITE){
                System.out.println("White turn");
                turn(p1.getMove());
            }
            if (whichTurn == Color.BLACK){
                System.out.println("Black turn");
                turn(p2.getMove());
            }
            if (whichTurn == null){
                break;
            }
        }
    }
    private Game(IPlayer p1, IPlayer p2){
        this.p1 = p1;
        this.p2 = p2;
        desk = (new Desk.Builder()).setDefault().build();
    }
}
