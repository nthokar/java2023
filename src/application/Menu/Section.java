package application.Menu;

import lombok.Getter;

import java.util.Objects;
import java.util.function.Function;

public class Section implements Runnable {
    @Getter
    String title;
    @Getter
    Integer index;
    @Getter
    Runnable runnable;
    @Getter
    Function function;
    @Getter
    boolean closable;
    public Section(String title, Integer index, Runnable runnable){
        this.title = title;
        this.index = index;
        this.runnable = runnable;
    }
    public Section(String title, Integer index, Function function){
        this.title = title;
        this.index = index;
        this.function = function;
    }

    public void run(){
        if (Objects.nonNull(runnable)){
            runnable.run();
        }
        if (Objects.nonNull(function)){
            function.apply(null);
        }
    }
}
