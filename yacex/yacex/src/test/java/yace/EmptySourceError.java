package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EmptySourceError {
    private final Path source;

    public EmptySourceError(Path source) {
        this.source = source;
    }

    String computeMessage() {
        return String.format("Source file '%s' is empty, nothing to analyze.", source.toAbsolutePath());
    }

    void apply() throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = fileName.substring(0, separator);
        Files.writeString(source, String.format("class %s {\n}", fileNameWithoutExtension));
    }
}
