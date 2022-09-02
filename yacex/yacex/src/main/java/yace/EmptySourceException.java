package yace;

/**
 * Indicates that an error occurred during compilation.
 */
public class EmptySourceException extends RuntimeException {
    public EmptySourceException(String message) {
        super(message);
    }
}
