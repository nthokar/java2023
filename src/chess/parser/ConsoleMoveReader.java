package chess.parser;

import chess.desk.Castle;
import chess.desk.Cell;
import chess.desk.Move;

import java.awt.*;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleMoveReader {
    private final Scanner scanner;
    private final Color playerColor;
    public ConsoleMoveReader(InputStream stream, Color playerColor){
        scanner = new Scanner(stream);
        this.playerColor = playerColor;
    }
    public Move readMove(){
        try {
            return readMove(scanner.nextLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Move readMove(String str){
        try {
            var from = readCell(str.substring(0,2));
            var to = readCell(str.substring(2));
            return new Move(from, to, playerColor);
        }
        catch (Exception e1)
        {
            try {
                return readCastle(str);
            }
            catch (Exception e2)
            {
                throw new RuntimeException("wrong input");
            }
        }
    }

    private   Cell readCell(String str) throws IllegalAccessException {
        if (str.length() != 2)
            throw new IllegalAccessException();
        return new Cell((int)str.charAt(0) - (int)'a' + 1, Character.getNumericValue(str.charAt(1)));
    }

    private Castle readCastle(String str) throws IllegalAccessException {
        if (str.equals("O-O-O")) {
            return new Castle(
                    new Cell(5, 0),
                    new Cell(3, 0),
                    playerColor
            );
        }
        if (str.equals("O-O")) {
            return new Castle(
                    new Cell(5, 0),
                    new Cell(7, 0),
                    playerColor
            );
        }
        throw new IllegalAccessException();
    }
}