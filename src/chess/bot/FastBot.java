package chess.bot;

import chess.game.Game;

import java.awt.*;

public class FastBot extends Bot {
    public FastBot(int deep, Game game, Color color) {
        super(deep, game, color);
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
//    @Override
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
}
