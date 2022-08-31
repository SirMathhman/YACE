package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The main class for the application.
 */
public class Application {
    private final Gateway gateway;

    /**
     * Constructs a new Application using the given gateway.
     *
     * @param gateway The gateway.
     */
    public Application(Gateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Actually runs the application.
     *
     * @throws IOException If an error happened.
     */
    void run() throws IOException {
        gateway.streamSources().forEach(source -> {
            var fileName = source.getFileName().toString();
            var extensionSeparator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, extensionSeparator);
            createTarget(source, fileNameWithoutExtension);
        });
    }

    private static void createTarget(Path source, String fileNameWithoutExtension) {
        try {
            Files.createFile(source.resolveSibling(fileNameWithoutExtension + ".mgs"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
