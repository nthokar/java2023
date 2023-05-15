package chess.bot;

import chess.desk.Move;
import chess.game.Game;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FastBot extends Bot {
    public FastBot(int deep, Color color) {
        super(deep, color);
    }

    private class Tupple{
        Double x;
        Double y;

        Tupple(Double x, Double y){
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public Move getMove(){
        initEvaluateGame();
        var evaluate = buildEvaluateTree(deep, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        if (evaluate.size() == 0){
            return null;
        }
        var maxValueInMap = getColor() == Color.white ?
                evaluate.entrySet().stream()
                    .max(Comparator.comparing(tupple -> tupple.getValue().y))  :
                evaluate.entrySet().stream()
                        .min(Comparator.comparing(tupple -> tupple.getValue().x));  // This will return max value in the HashMap
        return maxValueInMap.get().getKey();
    }

    public Tupple complexEvaluate(int deep, Double alpha, Double beta){
        initEvaluateGame();
        Map<Move, Tupple> evaluate = buildEvaluateTree(deep, alpha, beta);
        if (evaluate.size() == 0) {
            if (evaluateGame.isKingUnderAttack(getColor()))
                return getColor() == Color.WHITE ? new Tupple(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY):
                        new Tupple(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
            return new Tupple(0d, 0d);
        }
        return getColor() == Color.WHITE ?
                evaluate.entrySet().stream()
                    .max(Comparator.comparing(t -> t.getValue().y))
                    .get()
                    .getValue():
                evaluate.entrySet().stream()
                        .min(Comparator.comparing(t -> t.getValue().x))
                        .get()
                        .getValue();
    }

    public Map<Move, Tupple> buildEvaluateTree(int deep, Double alpha, Double beta){
        initEvaluateGame();
        HashMap<Move, Tupple> evaluate = new HashMap<>();
        var botFigures = evaluateGame.getPlayerFigures(this);
        for (var cell:botFigures) {
            var possibleMoves = evaluateGame.getMoveChecker().possibleMoves(cell);
            for (var move:possibleMoves) {
                var moveCopy = move.copy();
                try {
                    evaluateGame.turn(move);
                    evaluateGame.undo();
                }
                catch (Exception e){
                    continue;
                }
                if (deep <= 0) {
                    evaluateGame.turn(move);
                    var positionMaterialEvaluate = evaluate();
                    var alphaBeta = new Tupple(positionMaterialEvaluate, positionMaterialEvaluate);
                    evaluate.put(moveCopy, alphaBeta);
                    evaluateGame.undo();

                }
                else {
                    evaluateGame.turn(move);
                    var bot = ((FastBot) evaluateGame.getEnemy(this));
                    var botResponse = bot.complexEvaluate(deep - 1, alpha, beta);
                    evaluate.put(moveCopy, botResponse);
                    evaluateGame.undo();
                    if (getColor() == Color.WHITE){
                        alpha = Math.max(alpha, Math.max(botResponse.x, botResponse.y));
                    }
                    else {
                        beta = Math.min(beta, Math.min(botResponse.x, botResponse.y));
                    }
                    if (alpha >= beta) {
                        return evaluate;
                    }
                }
            }
        }
        return evaluate;
    }

    @Override
    protected void initEvaluateGame(){
        if (isInitialized)
            return;
        var newBot = new FastBot(
                deep - 1,
                evaluateGame.getEnemy(this).getColor());
        this.evaluateGame = new Game(
                evaluateGame.getDesk(),
                (Stack<Move>) evaluateGame.getMoveHistory().clone(),
                this,
                newBot);
        newBot.evaluateGame = evaluateGame;
        isInitialized = true;
        newBot.isInitialized = true;
    }
}
