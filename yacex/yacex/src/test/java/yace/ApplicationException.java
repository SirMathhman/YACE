package yace;

/**
 * Generic top-level class for exceptions within the application.
 */
public class ApplicationException extends Exception {
    public ApplicationException() {
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
