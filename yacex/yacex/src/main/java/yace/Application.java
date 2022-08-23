package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    public Application() {
    }

    AnalysisResult analyze(Path source) throws IOException {
        var input = Files.readString(source);
        if (input.isBlank()) {
            return new EmptySourceError(source);
        } else {
            return new ClassStructureError(input.strip());
        }
    }

    void format(Path source) throws IOException {
        var input = Files.readString(source);
        if (input.length() != 0) {
            Files.writeString(source, input.strip());
        }
    }
}