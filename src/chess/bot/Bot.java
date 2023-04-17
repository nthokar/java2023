package chess.bot;

import chess.desk.Move;
import chess.game.APlayer;
import chess.game.Game;
import lombok.Setter;

import java.awt.*;
import java.util.*;

public class Bot extends APlayer {
    @Setter
    private Game evaluateGame;
    private boolean isInitialized;
    private final int deep;



    public Bot(int deep, Game game, Color color){
        super(color);
        this.deep = deep;
        evaluateGame = game;
    }

    @Override
    public Move getMove(){
        initEvaluateGame();
        var evaluate = buildEvaluateTree(deep);
        if (evaluate.size() == 0){
            return null;
        }
        var maxValueInMap = getColor() == Color.white ?
                Collections.max(evaluate.entrySet(), Map.Entry.comparingByValue())  :
                Collections.min(evaluate.entrySet(), Map.Entry.comparingByValue());  // This will return max value in the HashMap
        return maxValueInMap.getKey();
    }

    public Double complexEvaluate(int deep){
        initEvaluateGame();
        Map<Move, Double> evaluate = buildEvaluateTree(deep);
        if (evaluate.size() == 0) {
            if (evaluateGame.isKingUnderAttack(getColor()))
                return Double.NEGATIVE_INFINITY;
            return 0d;
        }
        return getColor() == Color.WHITE ? Collections.max(evaluate.values()) : Collections.min(evaluate.values());
    }

    public Map<Move, Double> buildEvaluateTree(int deep){
        initEvaluateGame();
        HashMap<Move, Double> evaluate = new HashMap<>();
        var botFigures = evaluateGame.getPlayerFigures(this);
        for (var cell:botFigures) {
            var possibleMoves = evaluateGame.getMoveChecker().possibleMoves(cell);
            for (var move:possibleMoves) {
                var moveCopy = move.copy();
                try {
                    evaluateGame.turn(move);
                    evaluateGame.undo();
                }
                catch (Exception ignored) {
                    //pass
                }
                if (deep <= 0) {
                    evaluate.put(moveCopy, evaluate());
                }
                else {
                    evaluate.put(moveCopy, ((Bot) evaluateGame.getEnemy(this)).complexEvaluate(deep - 1));
                }
            }
        }
        return evaluate;
    }

    public Double evaluate() {
        return (double) evaluateGame.evaluateMaterial();
    }



    private Bot(int deep, Color color){
        super(color);
        this.deep = deep;
    }
    private void initEvaluateGame(){
        if (isInitialized)
            return;
        var newBot = new Bot(
                deep - 1,
                evaluateGame.getEnemy(this).getColor());
        this.evaluateGame = new Game(
                evaluateGame.getDesk().copy(),
                (Stack<Move>) evaluateGame.getMoveHistory().clone(),
                this,
                newBot);
        newBot.evaluateGame = evaluateGame;
        isInitialized = true;
        newBot.isInitialized = true;
    }
}
