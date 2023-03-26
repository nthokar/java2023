package Chess;

import Chess.Desk.*;

public class Main {
    public static void main(String[] args) {
//        Desk desk = new Desk(Desk.setDefault());
//        desk.print();
//        Scanner myObj = new Scanner(System.in)

        GameManager gm = new GameManager(System.in);
        gm.startGame();

    }
}