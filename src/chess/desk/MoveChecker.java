package chess.desk;

import chess.game.Game;

import java.util.*;
import java.util.stream.Collectors;

public class MoveChecker {
    public final Game game;
    public final Desk desk;
    public MoveChecker(Game game, Desk desk){
        this.desk = desk;
        this.game = game;
    }


    public List<Cell> cellsInDirection(Cell cell, MoveTemplate moveTemplate){
        int x = Math.round((float) (cell.x + moveTemplate.x));
        int y = Math.round((float) (cell.y +  moveTemplate.y));
        if (x <= desk.xLength() && 0 < x && y <= desk.yLength() && 0 < y){
            try {
                Cell newCell = desk.getCell(x, y);
                if (newCell.getFigure() == null) {
                    var raisedList = new ArrayList<Cell>();
                    raisedList.add(newCell);
                    raisedList.addAll(cellsInDirection(newCell, moveTemplate));
                    return raisedList;
                }
                else {
                    return new ArrayList<Cell>(){{add(newCell);}};
                }
            }
            catch (Exception e) {
                //pass
            }
        }
        return Collections.emptyList();
    }


    public Cell[] pathTo(Cell cellFrom, Cell cellTo) {
        cellFrom = desk.projectCell(cellFrom);
        cellTo = desk.projectCell(cellTo);
        var vector = cellFrom.getVector(cellTo);
        double maxAbs = Math.max(Math.abs(vector.x), Math.abs(vector.y));
        MoveTemplate unitMoveTemplate =  new MoveTemplate(vector.x/ maxAbs, vector.y/maxAbs);

        var cells = cellFrom.getFigure().getCells();
        if (cells != null && cells.contains(vector)
                && (cellTo.getFigure() == null || cellFrom.getFigure().color != cellTo.getFigure().color)) {
            return new Cell[]{cellFrom, cellTo};
        }

        var directions = cellFrom.getFigure().getDirections();
        if (directions != null && directions.contains(unitMoveTemplate)) {
            Cell currentCell = cellFrom;
            ArrayList<Cell> path = new ArrayList<>();
            path.add(currentCell);
            while (currentCell != cellTo){
                currentCell = desk.projectCell(new Cell(currentCell.x + (int) unitMoveTemplate.x, currentCell.y + (int) unitMoveTemplate.y));
                path.add(currentCell);
            }
            return path.toArray(Cell[]::new);
        }
        return new Cell[0];
    }


    public Set<Move> possibleMoves(Cell cellFrom){
        HashSet<Move> moves = new HashSet<>();
        var figure = cellFrom.getFigure();
        if (Objects.isNull(figure)) {
            return moves;
        }
        for(var direction: figure.getDirections()){
            var cellsInDirection = cellsInDirection(cellFrom, direction);
            moves.addAll(cellsInDirection.stream()
                    .map(x -> new Move(cellFrom, x, figure.color))
                    .filter(x -> validateMove(desk.projectMove(x)))
                    .collect(Collectors.toList()));
        }
        for (var cell:figure.getCells()) {
            try {
                var move = new Move(
                        cellFrom,
                        desk.getCell(cellFrom.x + (int)cell.x, cellFrom.y + (int)cell.y),
                        figure.color);
                if (validateMove(desk.projectMove(move)))
                    moves.add(move);
            }
            catch (Exception e){
            }
        }
        return moves;
    }


    public boolean isUnderAttack(Cell cell){
        cell = desk.projectCell(cell);
        var enemies = desk.getEnemiesFigures(cell.getFigure().color);

        for (var enemy:enemies){
            enemy = desk.projectCell(enemy);
            if (enemy.getFigure().canMove(pathTo(enemy, cell)))
                return true;
        }
        return false;
    }


    public boolean validateMove(Move move){
        if (Objects.isNull(move)){
            return false;
        }
        try {
            move = desk.projectMove(move);
            if (move.from.getFigure() == null || (move.to.getFigure() != null &&
                    move.from.getFigure().color.equals(move.to.getFigure().color))){
                return false;
            }
            var path = pathTo(move.from, move.to);
            if (!move.from.getFigure().canMove(path))
                return false;
            var moveCopy = move.copy();
            desk.moveFigureForce(move);
            var result = !(isUnderAttack(desk.getKingCell(move.whichMove)));
            desk.getCell(moveCopy.from.x, moveCopy.from.y).setFigure(moveCopy.from.getFigure());
            desk.getCell(moveCopy.to.x, moveCopy.to.y).setFigure(moveCopy.to.getFigure());
            return result;
        }
        catch (Exception e){
            return false;
        }
    }


    public static final HashMap<String, MoveTemplate> Directions = new HashMap<String, MoveTemplate>(){{
        put("upperLeftDiagonal", new MoveTemplate(-1,+1));
        put("upperRightDiagonal", new MoveTemplate(+1,+1));
        put("downLeftDiagonal", new MoveTemplate(-1,-1));
        put("downRightDiagonal", new MoveTemplate(+1,-1));
        put("rightHorizontal", new MoveTemplate(+1,0));
        put("leftHorizontal", new MoveTemplate(-1,0));
        put("upperVertical", new MoveTemplate(0,+1));
        put("downVertical", new MoveTemplate(0,-1));
    }};


    public static final HashMap<String, MoveTemplate> Cells = new HashMap<String, MoveTemplate>(){{
        put("upperLeftCell", new MoveTemplate(-1,+1));
        put("upperRightCell", new MoveTemplate(+1,+1));
        put("downLeftCell", new MoveTemplate(-1,-1));
        put("downRightCell", new MoveTemplate(+1,-1));
        put("rightCell", new MoveTemplate(+1,0));
        put("leftCell", new MoveTemplate(-1,0));
        put("upperCell", new MoveTemplate(0,+1));
        put("upperUpperCell", new MoveTemplate(0,+2));
        put("downCell", new MoveTemplate(0,-1));
        put("downDownCell", new MoveTemplate(0,-2));

        put("knightUpLeft", new MoveTemplate(-1,+2));
        put("knightUpRight", new MoveTemplate(+1,+2));
        put("knightDownLeft", new MoveTemplate(-1,-2));
        put("knightDownRight", new MoveTemplate(+1,-2));
        put("knightLeftUp", new MoveTemplate(-2,+1));
        put("knightRightUp", new MoveTemplate(+2,+1));
        put("knightLeftDown", new MoveTemplate(-2,-1));
        put("knightRightDown", new MoveTemplate(+2,-1));

        put("longCastle", new MoveTemplate(-1, 0));
        put("shortCastle", new MoveTemplate(+1, 0));
    }};
}
