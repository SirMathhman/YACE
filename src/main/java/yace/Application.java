package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source, Path target) {
    Optional<Path> run() throws IOException {
        if (Files.exists(source())) {
            Files.createFile(target());
            return Optional.of(target());
        }
        return Optional.empty();
    }
}