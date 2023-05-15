package application.Menu;

public class CloseMenuException extends RuntimeException{
    public CloseMenuException(String errorMessage) {
        super(errorMessage);
    }
    public CloseMenuException() {
        super((String) null);
    }
}
