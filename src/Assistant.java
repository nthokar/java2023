import java.io.InputStream;
import java.text.DecimalFormat;

public class Assistant {
    private String username;
    public final streamReader consoleReader;
    public Assistant(InputStream stream) {
        this.consoleReader = new streamReader(stream);
    }

    public void askUsername(){
        System.out.println("How should i call you?");
        username = consoleReader.readUserName();
    }

    public void printSynTable(){
        //Лень пока делать универсальный метод, может потом :)
        var df = new DecimalFormat("0.0000");

        System.out.println("x° -> syn(x°)");
        for (int i = 0; i <= 45; i+=5){
            System.out.println("+-------------+");
            System.out.printf("|%-2s -> %7s|%n", i, df.format(java.lang.Math.sin(i)));
        }
        System.out.println("+-------------+");
    }

    public void greetingsUser() {
        System.out.printf("Hello, %s!%n", username);
    }
}
