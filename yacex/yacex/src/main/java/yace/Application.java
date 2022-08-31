package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The main class for the application.
 */
public class Application {
    private final Path source;

    /**
     * Constructs a new Application using the given paths.
     *
     * @param source The source file.
     */
    public Application(Path source) {
        this.source = source;
    }

    /**
     * Actually runs the application.
     *
     * @throws IOException If an error happened.
     */
    void run() throws IOException {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var extensionSeparator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, extensionSeparator);
            Files.createFile(source.resolveSibling(fileNameWithoutExtension + ".mgs"));
        }
    }
}
