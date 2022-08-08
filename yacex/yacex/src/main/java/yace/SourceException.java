package yace;

import java.nio.file.Path;

public class SourceException extends ApplicationException {
    public SourceException(Path value) {
        super(value.toString());
    }
}
