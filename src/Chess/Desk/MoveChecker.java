package Chess.Desk;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoveChecker {
    public MoveChecker(Desk desk){
        this.desk = desk;
        this.board = desk.cells;
    }
    public final Desk desk;
    public final Cell[][] board;
    public Cell[] CellsInDirection(Cell cell, Vector vector){
        int x = Math.round((float) (cell.x + vector.x)) - 1;
        int y = Math.round((float) (cell.y +  vector.y)) - 1;
        if (x < desk.xLength() && 0 <= x && y < desk.yLength() && 0 <= y){
            Cell newCell = board[x][y];
            if (newCell.getFigure() == null){
                var raisedArray = new ArrayList<Cell>(){{add(newCell);}};
                raisedArray.addAll(List.of(CellsInDirection(newCell, vector)));
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
        Vector unitVector =  new Vector(vector.x/ maxAbs, vector.y/maxAbs);

        var cells = cellFrom.getFigure().getCells();
        if (cells != null && cells.contains(vector)
                && (cellTo.getFigure() == null || cellFrom.getFigure().color != cellTo.getFigure().color)) {
            return new Cell[]{cellFrom, cellTo};
        }

        var directions = cellFrom.getFigure().getDirections();
        if (directions != null && directions.contains(unitVector)) {
            Cell currentCell = cellFrom;
            ArrayList<Cell> path = new ArrayList<>();
            path.add(currentCell);
            while (currentCell != cellTo){
                currentCell = board[currentCell.x + (int) unitVector.x - 1][currentCell.y + (int) unitVector.y - 1];
                path.add(currentCell);
            }
            return path.toArray(Cell[]::new);
        }
        return new Cell[0];
    }

//    public Cell[] attacksFrom(Cell cell){
//        var result = new ArrayList<Cell>();
//        for (String key: Directions.keySet()){
//            var direction = Directions.get(key);
//            var cellsTo = CellsInDirection(cell, direction);
//            if (cellsTo.length > 0){
//                var figure = cellsTo[cellsTo.length - 1].getFigure();
//                if (figure != null && figure.color != cell.getFigure().color
//                        && figure.getDirections().contains(direction)) {
//                    result.add(cellsTo[cellsTo.length - 1]);
//                }
//            }
//        }
//        return result.toArray(new Cell[0]);
//    }

    public boolean isUnderAttack(Cell cell){
        Cell[] enemies;
        if (cell.getFigure() == null){
            var whites = desk.getWhites();
            var blacks = desk.getBlacks();
            enemies = new Cell[blacks.length + whites.length];
            System.arraycopy(whites, 0, enemies, 0, whites.length);
            System.arraycopy(blacks, whites.length, enemies, whites.length, whites.length + blacks.length - whites.length);
        }
        enemies = cell.getFigure().color == Color.WHITE ? desk.getBlacks() : desk.getWhites();
        var _desk = desk.copy();
        cell = _desk.cells[cell.x - 1][cell.y - 1];
        for (var enemy:enemies){
            enemy = _desk.cells[enemy.x - 1][enemy.y - 1];
            enemy.getFigure().move(_desk.moveChecker.pathTo(enemy, cell));
            if (enemy.getFigure() == null)
                return true;
        }
        return false;
    }
    public static final HashMap<String, Vector> Directions = new HashMap<String, Vector>(){{
        put("upperLeftDiagonal", new Vector(-1,+1));
        put("upperRightDiagonal", new Vector(+1,+1));
        put("downLeftDiagonal", new Vector(-1,-1));
        put("downRightDiagonal", new Vector(+1,-1));
        put("rightHorizontal", new Vector(+1,0));
        put("leftHorizontal", new Vector(-1,0));
        put("upperVertical", new Vector(0,+1));
        put("downVertical", new Vector(0,-1));
    }};
    public static final HashMap<String, Vector> Cells = new HashMap<String, Vector>(){{
        put("upperLeftCell", new Vector(-1,+1));
        put("upperRightCell", new Vector(+1,+1));
        put("downLeftCell", new Vector(-1,-1));
        put("downRightCell", new Vector(+1,-1));
        put("rightCell", new Vector(+1,0));
        put("leftCell", new Vector(-1,0));
        put("upperCell", new Vector(0,+1));
        put("downCell", new Vector(0,-1));

        put("knightUpLeft", new Vector(-1,+2));
        put("knightUpRight", new Vector(+1,+2));
        put("knightDownLeft", new Vector(-1,-2));
        put("knightDownRight", new Vector(+1,-2));
        put("knightLeftUp", new Vector(-2,+1));
        put("knightRightUp", new Vector(+2,+1));
        put("knightLeftDown", new Vector(-2,-1));
        put("knightRightDown", new Vector(+2,-1));
    }};
}
