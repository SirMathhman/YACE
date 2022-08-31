package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The main class for the application.
 */
public class Application {
    private final Path target;
    private final Path source;

    /**
     * Constructs a new Application using the given paths.
     * @param source The source file.
     * @param target The target/destination file.
     */
    public Application(Path target, Path source) {
        this.target = target;
        this.source = source;
    }

    /**
     * Actually runs the application.
     * @throws IOException If an error happened.
     */
    void run() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
        }
    }
}
