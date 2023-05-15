package application;

import application.Menu.MainMenu;
import chess.game.GameManager;
import lombok.Getter;

public class ApplicationContext {
    @Getter
    User user = new User(System.in);
    @Getter
    GameManager gameManager = new GameManager(user);


    public static void main(String[] args) {
        var appContext = new ApplicationContext();
        var mainMenu = MainMenu.getMenu(appContext);
        mainMenu.run();
    }
}
