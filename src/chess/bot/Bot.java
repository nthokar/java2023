package chess.bot;

import chess.desk.Desk;
import chess.desk.Move;
import chess.game.IPlayer;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Bot implements IPlayer {
    public Bot(int deep, Desk desk, Color color){
        this.deep = deep;
        this.desk = desk;
        this.color = color;
    }
    Desk desk;
    Color color;
    public final int deep;
    public Double evaluate() {
        return (double) ((color == Color.WHITE ? 1 : -1 ) * desk.evaluateMaterial());
    }
    public Double complexEvaluate(){
        HashMap<Move, Double> evaluate = new HashMap<>();
        var botFigures = color == Color.WHITE ? desk.getWhites() : desk.getBlacks();
        for (var cell:botFigures) {
            var possibleMoves = desk.moveChecker.possibleMoves(cell);
            for (var move:possibleMoves) {
                var newDesk = desk.copy();
                try {
                    if (newDesk.tryMove(move)) {
                        if (deep <= 0) {
                            evaluate.put(move, evaluate());
                        } else {
                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
                            evaluate.put(move, (-1)*newBot.complexEvaluate());
                        }
                    }
                }
                catch(Exception e) {

                }
            }
        }
        if (evaluate.size() == 0){
            return Double.NEGATIVE_INFINITY;
        }
        return (Collections.max(evaluate.values()));  // This will return max value in the HashMap
    }
    public Move getMove(){
        HashMap<Move, Double> evaluate = new HashMap<>();
        var botFigures = color == Color.WHITE ? desk.getWhites() : desk.getBlacks();
        var newDesk = desk.copy();
        for (var cell:botFigures) {
            var possibleMoves = desk.moveChecker.possibleMoves(cell);
            for (var move:possibleMoves) {
                try {
                    if (newDesk.tryMove(move)) {
                        if (deep <= 0) {
                            evaluate.put(move, evaluate());
                        }
                        else {
                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
                            evaluate.put(move, (-1) * newBot.complexEvaluate());
                        }
                        newDesk = desk.copy();
                    }
                }
                catch (Exception e){

                }
            }
        }
        if (evaluate.size() == 0){
            return null;
        }
        var maxValueInMap = Collections.max(evaluate.entrySet(), Map.Entry.comparingByValue());  // This will return max value in the HashMap
        System.out.println(maxValueInMap.getKey());
        return maxValueInMap.getKey();
    }
}
