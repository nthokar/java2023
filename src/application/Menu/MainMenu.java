package application.Menu;

import application.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Menu {
    ApplicationContext applicationContext;
    public static MainMenu getMenu(ApplicationContext applicationContext) {
        var sections = new ArrayList<Section>();
        sections.add(new Section( "start game with bot", 1, applicationContext.getGameManager().getGameWithBot()));
        sections.add(new Section("game menu", 2, GameMenu.getMenu(applicationContext)));
        return new MainMenu(sections, applicationContext);
    }
    private MainMenu(List<Section> sections, ApplicationContext applicationContext) {
        super(sections);
        this.applicationContext = applicationContext;
    }
}
