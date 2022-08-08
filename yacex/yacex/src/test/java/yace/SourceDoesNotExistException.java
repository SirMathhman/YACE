package yace;

import java.nio.file.Path;

public class SourceDoesNotExistException extends Exception {
    public SourceDoesNotExistException(Path value) {
        super(value.toString());
    }
}
