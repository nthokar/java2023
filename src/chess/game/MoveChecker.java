package chess.game;

import chess.desk.Cell;
import chess.desk.Desk;
import chess.desk.Move;
import chess.desk.MoveTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MoveChecker {
    public MoveChecker(Desk desk){
        this.desk = desk;
        this.board = desk.cells;
    }
    public final Desk desk;
    public final Cell[][] board;
    public Cell[] CellsInDirection(Cell cell, MoveTemplate moveTemplate){
        int x = Math.round((float) (cell.x + moveTemplate.x)) - 1;
        int y = Math.round((float) (cell.y +  moveTemplate.y)) - 1;
        if (x < desk.xLength() && 0 <= x && y < desk.yLength() && 0 <= y){
            Cell newCell = desk.cells[x][y];
            if (newCell.getFigure() == null){
                var raisedArray = new ArrayList<Cell>(){{add(newCell);}};
                raisedArray.addAll(List.of(CellsInDirection(newCell, moveTemplate)));
                return raisedArray.toArray(new Cell[raisedArray.size()]);
            }
            else
                return new Cell[]{newCell};
        }
        return new Cell[0];
    }
    public Cell[] pathTo(Cell cellFrom, Cell cellTo) {
        cellFrom = board[cellFrom.x - 1][cellFrom.y - 1];
        cellTo = board[cellTo.x - 1][cellTo.y -1];
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
                currentCell = board[currentCell.x + (int) unitMoveTemplate.x - 1][currentCell.y + (int) unitMoveTemplate.y - 1];
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
            var cellsInDirection = CellsInDirection(cellFrom, direction);
            moves.addAll(Arrays.stream(cellsInDirection)
                    .map(x -> new Move(cellFrom, x, figure.color))
                    .collect(Collectors.toList()));
        }
        for (var cell:figure.getCells()) {
            try {
                moves.add(new Move(
                        cellFrom, desk.cells
                        [cellFrom.x + (int)cell.x - 1]
                        [cellFrom.y + (int)cell.y - 1], figure.color));
            }
            catch (Exception e){

            }
        }
        return moves;
    }
    public boolean isUnderAttack(Cell cell){
        Cell[] enemies;
        if (cell.getFigure() == null){
            var whites = desk.getWhites();
            var blacks = desk.getBlacks(); // !
            enemies = new Cell[blacks.length + whites.length];
            System.arraycopy(whites, 0, enemies, 0, whites.length);
            System.arraycopy(blacks, whites.length, enemies, whites.length, whites.length + blacks.length - whites.length);
        }
        enemies = cell.getFigure().color == Color.WHITE ? desk.getBlacks() : desk.getWhites();
        var _desk = desk.copy();
        cell = _desk.cells[cell.x - 1][cell.y - 1];
        for (var enemy:enemies){
            enemy = _desk.cells[enemy.x - 1][enemy.y - 1];
            try {
                enemy.getFigure().move(_desk.moveChecker.pathTo(enemy, cell));
                if (enemy.getFigure() == null)
                    return true;
            }
            catch (Exception e){ }
        }
        return false;
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
