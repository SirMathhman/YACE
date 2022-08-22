package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    public Application() {
    }

    EmptySourceError analyze(Path source) {
        return new EmptySourceError(source);
    }

    void format(Path source) throws IOException {
        var input = Files.readString(source);
        if (input.length() != 0) {
            Files.writeString(source, input.strip());
        }
    }
}