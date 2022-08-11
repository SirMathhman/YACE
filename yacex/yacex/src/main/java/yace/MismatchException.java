package yace;

public class MismatchException extends CompileException {
    private final String expected;
    private final String actual;
    private final String reason;

    public MismatchException(String expected, String actual, String reason) {
        this.expected = expected;
        this.actual = actual;
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        var format = "Expected to have a value of '%s' instead of actual value '%s': %s";
        return String.format(format, expected, actual, reason);
    }
}
