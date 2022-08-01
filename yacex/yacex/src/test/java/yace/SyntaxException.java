package yace;

public class SyntaxException extends RuntimeException {
    public SyntaxException(String message, String input) {
        this(message + ": " + input);
    }

    public SyntaxException(String message) {
        super(message);
    }
}
