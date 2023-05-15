package application;

import lombok.Getter;

import java.io.InputStream;

public class User {
    @Getter
    InputStream inputStream;
    public User(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
