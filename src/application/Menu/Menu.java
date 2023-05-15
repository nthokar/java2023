package application.Menu;

import java.util.*;

public class Menu extends AbstractMenu implements Runnable {
    HashMap<Integer, Section> sections;
    public Menu(List<Section> sections){
        if (Objects.isNull(sections)){
            return;
        }
        this.sections = new HashMap<>();
        var lastIndex = sections.size() + 1;
        var exitSection = new Section("exit", lastIndex, x -> {
            throw new CloseMenuException();
        });
        sections.add(exitSection);
        sections.stream().forEach(x -> this.sections.put(x.index, x));
    }
    public void run(){
        while (true){
            print();
            var response = getAnswer();
            if (sections.containsKey(response)){
                var section = sections.get(response);
                try {
                    section.run();
                }
                catch (CloseMenuException e){
                    return;
                }
            }
        }
    }
    public int getAnswer(){
        var scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public void print(){
        for (var sectionIndex:sections.keySet()) {
            System.out.println( sections.get(sectionIndex).index + " " + sections.get(sectionIndex).title);
        }
    }
}
