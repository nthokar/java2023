package application.Menu;

import application.ApplicationContext;
import chess.game.Game;

import java.util.ArrayList;
import java.util.List;

public class GameMenu extends Menu {
    ApplicationContext applicationContext;
    Game.Builder game;

    public static GameMenu getMenu(ApplicationContext applicationContext){
        var sections = new ArrayList<Section>();
        var game = new Game.Builder()
                .whiteUser(applicationContext.getUser())
                .setDeskDefault();
        sections.add(new Section("start game!", 1, game::buildAndRun));
        sections.add(new Section("swap colors", 2, game::swapPLayersColors));
        return new GameMenu(sections, game);
    }
    public GameMenu(List<Section> sections, Game.Builder game) {
        super(sections);
        this.game = game;
    }
}
