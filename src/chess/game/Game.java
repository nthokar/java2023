package chess.game;

import application.User;
import chess.bot.Bot;
import chess.bot.FastBot;
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
public class Game implements Runnable {

    public static class Builder {
        @Getter
        APlayer p1;
        @Getter
        APlayer p2;
        @Getter
        Desk desk;
        @Getter
        MoveChecker moveChecker;
        @Getter
        Color whichTurn = Color.WHITE;
        @Getter
        Stack<Move> moveHistory = new Stack<>();

        @Getter
        User whiteUser;
        @Getter
        User blackUser;

        public Builder p1(APlayer player){
            p1 = player;
            return this;
        }
        public Builder p2(APlayer player){
            p2 = player;
            return this;
        }

        public Builder whiteUser(User user){
            whiteUser = user;
            return this;
        }

        public Builder blackUser(User user){
            blackUser = user;
            return this;
        }

        public Builder setPlayerWithBot(APlayer player){
            p1(player).p2(new Bot(3, null, player.getColor() == Color.BLACK ? Color.WHITE : Color.BLACK));
            return this;
        }

        public Builder swapPLayersColors(){
            var blackUser = getBlackUser();
            blackUser(getWhiteUser()).whiteUser(blackUser);
            return this;
        }

        public Builder desk(Desk desk){
            this.desk = desk;
            return this;
        }
        public Builder setDeskDefault(){
            desk = new Desk.Builder().setDefault().build();
            return this;
        }

        public Builder moveChecker(MoveChecker moveChecker){
            this.moveChecker = moveChecker;
            return this;
        }
        public Builder whichTurn(Color whichTurn){
            this.whichTurn = whichTurn;
            return this;
        }
        public Builder moveHistory(Stack<Move> moveHistory){
            this.moveHistory = moveHistory;
            return this;
        }
        public Builder moveHistoryAdd(Move move){
            this.moveHistory.add(move);
            return this;
        }
        public Game build(){
            return new Game(this);
        }
        public void buildAndRun(){
            new Game(this).run();
        }
    }

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


    public void run(){
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
                System.out.println(System.nanoTime());
                try{
                    turn(p2.getMove());
                    print();
                }
                catch (Exception e){

                }
                System.out.println(System.nanoTime());
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
        if (p1.getColor() == p2.getColor())
            throw new RuntimeException("players have same color");
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
    private Game(Builder builder) {
        if (Objects.nonNull(builder.whiteUser)){
            p1 = new Player(builder.whiteUser.getInputStream(), Color.WHITE);
        }
        else {
            p1 = new FastBot(4,Color.WHITE);
        }
        if (Objects.nonNull(builder.blackUser)){
            p2 = new Player(builder.blackUser.getInputStream(), Color.BLACK);
        }
        else {
            p2 = new FastBot(4,Color.BLACK);
        }

        updateGame();

        this.desk = builder.getDesk();
        this.moveHistory = builder.moveHistory;
        this.whichTurn = builder.whichTurn;
        this.moveChecker = new MoveChecker(this, desk);
    }
}
