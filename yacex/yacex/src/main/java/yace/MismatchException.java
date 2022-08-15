package yace;

import yace.app.Renamer;

public class MismatchException extends CompileException {
    private final String expected;
    private final String actual;
    private final String reason;
    public final Renamer renamer;

    public MismatchException(String expected, String actual, String reason, Renamer renamer) {
        this.expected = expected;
        this.actual = actual;
        this.reason = reason;
        this.renamer = renamer;
    }

    @Override
    public String getMessage() {
        var format = "Expected to have a value of '%s' instead of actual value '%s': %s";
        return String.format(format, expected, actual, reason);
    }
}
