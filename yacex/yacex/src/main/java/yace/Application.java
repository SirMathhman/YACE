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

    private static void createTarget(Module source, String name) {
        try {
            var target = source.resolveSibling(name + ".mgs");
            Files.createFile(target.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actually runs the application.
     *
     * @throws IOException If an error happened.
     */
    void run() throws IOException {
        gateway.streamSources().forEach(module -> {
            var fileNameWithoutExtension = module.computeName();
            createTarget(module, fileNameWithoutExtension);
        });
    }
}
