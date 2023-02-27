import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class streamReader extends InputStreamReader{
    public streamReader(InputStream stream){
        super(stream);
    }
    // Я знаю что этот класс - велосипед, просто хотелось освежить память про стримы,
    // давно их не трогал
    public String readUserName(){
        /*
        returns line from inputStream
         */
        try (BufferedReader br = new BufferedReader(this)){
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
