package yace;

import java.nio.file.Path;

public class SourceEmptyException extends SourceException {
    public SourceEmptyException(Path value) {
        super(value);
    }
}
