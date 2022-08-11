package yace;

/**
 * Indicates that an exception occurred during compilation (duh).
 */
public class CompileException extends ApplicationException {
    public CompileException() {
    }

    public CompileException(String message) {
        super(message);
    }
}
