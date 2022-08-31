package yace;

import java.io.IOException;
import java.nio.file.Files;

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
        var sources = gateway.collectSources();

        for (var source : sources) {
            var fileName = source.getFileName().toString();
            var extensionSeparator = fileName.indexOf('.');
            var fileNameWithoutExtension = fileName.substring(0, extensionSeparator);
            Files.createFile(source.resolveSibling(fileNameWithoutExtension + ".mgs"));
        }
    }

}
