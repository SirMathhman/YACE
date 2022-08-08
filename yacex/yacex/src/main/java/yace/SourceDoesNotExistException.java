package yace;

import java.nio.file.Path;

public class SourceDoesNotExistException extends SourceException {
    public SourceDoesNotExistException(Path value) {
        super(value);
    }
}
