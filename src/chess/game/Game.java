package chess.game;

import chess.bot.Bot;
import chess.desk.Cell;
import chess.desk.Desk;
import chess.desk.Move;
import chess.desk.MoveChecker;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

//главный класс партии. Отвечает за инициализацию пвртии, и ее проведение.
public class Game {
    @Getter
    APlayer p1;
    @Getter
    APlayer p2;
    @Getter
    Desk desk;
    @Getter
    MoveChecker moveChecker;
    Color whichTurn;
    @Getter
    Stack<Move> moveHistory = new Stack<>();


    public void changeTurn(){
        whichTurn = whichTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
    }


    public void turn(Move move){
        if (Objects.isNull(move)){
            throw new RuntimeException("Illegal move");
        }
        move = desk.projectMove(move);
        var cell = move.from;
        var cellTo = move.to;
        if (cell.getFigure() == null || cell.getFigure().color != whichTurn){
            throw new RuntimeException("choose possible move!");
        }
        var moveCopy = move.copy();
        if (!moveChecker.validateMove(move))
            throw new RuntimeException("Illegal move");
        var path = moveChecker.pathTo(cell, cellTo);
        cell.getFigure().move(path);
        moveHistory.push(moveCopy);
        changeTurn();
        //updateGame();
    }


    public void undo(){
        var lastmove = moveHistory.pop();
        desk.getCell(lastmove.from.x, lastmove.from.y).setFigure(lastmove.from.getFigure());
        desk.getCell(lastmove.to.x, lastmove.to.y).setFigure(lastmove.to.getFigure());
        changeTurn();
    }


    public void startGame(){
        desk.print();
        for(;;){
            System.out.println("White turn");
            if (whichTurn == Color.WHITE){
                try{
                    turn(p1.getMove());
                    print();
                }
                catch (Exception e){
                }
            }
            System.out.println("Black turn");
            if (whichTurn == Color.BLACK){
                try{
                    turn(p2.getMove());
                    print();
                }
                catch (Exception e){

                }
            }
            if (whichTurn == null){
                break;
            }
        }
    }


    public void print() {
        desk.print(moveHistory.peek());
    }


    public APlayer getEnemy(APlayer player){
        if (player == p1)
            return p2;
        if (player == p2)
            return p1;
        throw new RuntimeException("this player doesnt constraint in game");
    }


    public boolean isKingUnderAttack(Color color) {
        return moveChecker.isUnderAttack(desk.getKingCell(color));
    }


    public int evaluateMaterial(){
        return desk.evaluateMaterial();
    }


    public List<Cell> getPlayerFigures(APlayer player){
        return desk.getFiguresByColor(player.getColor());
    }


    public Game(Desk desk, Stack<Move> moveHistory, APlayer p1, APlayer p2) {
        this.p1= p1;
        this.p2 = p2;
        updateGame();
        this.desk = desk;
        this.moveChecker = new MoveChecker(this, desk);
        if (Objects.nonNull(moveHistory) && !moveHistory.isEmpty()){
                this.moveHistory = moveHistory;
                var lastMove = moveHistory.peek();
                whichTurn = lastMove.whichMove == p1.getColor() ? p2.getColor() : p1.getColor();
        }
        else {
            whichTurn = p1.getColor();
        }
    }


    private void updateGame() {
        if (p1 instanceof Bot){
            ((Bot)p1).setEvaluateGame(this);
        }
        if (p2 instanceof Bot){
            ((Bot)p2).setEvaluateGame(this);
        }
    }
}
