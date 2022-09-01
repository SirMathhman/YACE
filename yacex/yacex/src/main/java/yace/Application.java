package yace;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The main class for the application.
 */
public class Application {
    private final Gateway<Path> gateway;

    /**
     * Constructs a new Application using the given gateway.
     *
     * @param gateway The gateway.
     */
    public Application(Gateway<Path> gateway) {
        this.gateway = gateway;
    }

    private void createTarget(Module source, String name) {
        try {
            var target = source.resolveSibling(name + ".mgs");
            gateway.write(target, "mgs");
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
        gateway.read().forEach(module -> {
            var fileNameWithoutExtension = module.computeName();
            createTarget(module, fileNameWithoutExtension);
        });
    }
}
