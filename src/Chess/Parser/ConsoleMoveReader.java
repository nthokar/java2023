package Chess.Parser;

import Chess.Desk.Cell;
import Chess.Desk.Move;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleMoveReader {
    public ConsoleMoveReader(InputStream stream){
        scanner = new Scanner(stream);
    }
    private Scanner scanner;
    public Move readMove(){
        try {
            return readMove(scanner.nextLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Move readMove(String str){
        try {
            return new Move(readCell(str.substring(0,2)), readCell(str.substring(2)));
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("wrong input");
        }
    }

    public  Cell readCell(String str) throws IllegalAccessException {
        if (str.length() != 2)
            throw new IllegalAccessException();
        return new Cell((int)str.charAt(0) - (int)'a', Character.getNumericValue(str.charAt(1)));
    }

    public static void main(String[] args) {
    }
}