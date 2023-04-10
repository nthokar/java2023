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
        return (double) desk.evaluateMaterial();
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
                        }
                        else {
                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
                            evaluate.put(move, newBot.complexEvaluate());
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
        return color == Color.WHITE ? Collections.max(evaluate.values()) : Collections.min(evaluate.values());
    }
    private class Tupple{
        Double x;
        Double y;

        Tupple(Double x, Double y){
            this.x = x;
            this.y = y;
        }
    }

//    public Tupple complexEvaluate(Double a, Double b){
//        var botFigures = color == Color.WHITE ? desk.getWhites() : desk.getBlacks();
//        for (var cell:botFigures) {
//            var possibleMoves = desk.moveChecker.possibleMoves(cell);
//            for (var move : possibleMoves) {
//                var newDesk = desk.copy();
//                try {
//                    if (newDesk.tryMove(move)) {
//                        if (deep <= 0) {
//                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
//                            a = Math.max(a, evaluate());
//                            b = Math.min(b, evaluate());
//                        }
//                        else {
//                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
//                            var result = newBot.complexEvaluate(a, b);
//                            if (color == Color.WHITE) {
//                                a = Math.max(a, result.y);
//                                if (b + 2 <= a )
//                                    return new Tupple(a, b);
//                            }
//                            else {
//                                b = Math.min(b, result.x);
//                                if (b  >= a + 2)
//                                    return new Tupple(a, b);
//                            }
//                        }
//                    }
//                }
//                catch(Exception e) {
//
//                }
//            }
//        }
//        return new Tupple(a, b);
//    }
//    public Move getMove(){
//        HashMap<Move, Double> evaluate = new HashMap<>();
//        var botFigures = color == Color.WHITE ? desk.getWhites() : desk.getBlacks();
//        var a = Double.NEGATIVE_INFINITY;
//        var b = Double.POSITIVE_INFINITY;
//        Move topMove = null;
//        for (var cell:botFigures) {
//            var possibleMoves = desk.moveChecker.possibleMoves(cell);
//            for (var move : possibleMoves) {
//                var newDesk = desk.copy();
//                try {
//                    if (newDesk.tryMove(move)) {
//                        if (deep <= 0) {
//                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
//                            if (color == Color.WHITE) {
//                                //a = Math.max(a, evaluate());
//                                if (a >= evaluate()){
//                                    topMove = move;
//                                }
//                            }
//                            else {
//                                //b = Math.min(b, evaluate());
//                                if (b <= evaluate()){
//                                    topMove = move;
//                                }
//                            }
//                        }
//                        else {
//                            var newBot = new Bot(deep - 1, newDesk, color == Color.WHITE ? Color.BLACK : Color.WHITE);
//                            var result = newBot.complexEvaluate(a, b);
//                            if (color == Color.WHITE) {
//                                if (a < result.y){
//                                    a = result.y;
//                                    evaluate.put(move, a);
//                                }
//                                //a = Math.max(a, result.y);
////                                if (b <= a)
////                                    return move;
//                            }
//                            else {
//                                if (b > result.x){
//                                    b = result.x;
//                                    evaluate.put(move, b);
//                                }
//                                //b = Math.min(b, result.x);
////                                if (b >= a)
////                                    return move;
//                            }
//                        }
//                    }
//                }
//                catch(Exception e) {
//
//                }
//            }
//        }
//        if (color == Color.WHITE){
//            return Collections.max(evaluate.entrySet(), Map.Entry.comparingByValue()).getKey();
//        }
//        else {
//            return Collections.min(evaluate.entrySet(), Map.Entry.comparingByValue()).getKey();
//        }
//    }

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
                            evaluate.put(move, newBot.complexEvaluate());
                        }
                        newDesk = desk.copy();
                    }
                }
                catch (Exception e) {

                }
            }
        }
        if (evaluate.size() == 0){
            return null;
        }
        var maxValueInMap = color == Color.white ? Collections.max(evaluate.entrySet(), Map.Entry.comparingByValue()) :
                Collections.min(evaluate.entrySet(), Map.Entry.comparingByValue());  // This will return max value in the HashMap
        System.out.println(maxValueInMap.getKey());
        return maxValueInMap.getKey();
    }
}
