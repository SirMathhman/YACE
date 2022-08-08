package yace;

import java.nio.file.Path;

public class SourceDoesNotExistException extends ApplicationException {
    public SourceDoesNotExistException(Path value) {
        super(value.toString());
    }
}
