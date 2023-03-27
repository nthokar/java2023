package Chess.Desk;

import Chess.Figures.Figure;
import lombok.Getter;
import lombok.Setter;


/*
Класс клетки поля, отвечает за хранение данных клетки(ее местоположение на пооле, и фигура которая на ней стоит)

 */
public class Cell {
    @Setter
    @Getter
    private Figure figure;

    public final int x;
    public final int y;
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
    //метод копирования клетки
    public Cell copy(){
        var c = new Cell(this.x, this.y);
        if (getFigure() != null)
            c.setFigure(getFigure().copy());
        return c;
    }
    //вычисляет вектор до клетки
    public MoveTemplate getVector(Cell cellTo){
        return new MoveTemplate(cellTo.x - this.x, cellTo.y - this.y);
    }
    //переставляет фигуру в клетку
    public void moveFigure(Cell cellTo){
        if (cellTo.equals(this))
            return;
        cellTo.figure = figure;
        this.figure = null;
    }
    @Override
    public String toString() {
        return figure == null ? " " : figure.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof MoveTemplate){
            MoveTemplate moveTemplate = (MoveTemplate) o;
            return this.x == moveTemplate.x && this.y == moveTemplate.y;
        }
        if (o instanceof Cell){
            Cell cell = (Cell) o;
            return this.x == cell.x && this.y == cell.y;
        }
        return false;
    }
}
