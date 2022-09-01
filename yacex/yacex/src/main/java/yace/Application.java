package yace;

import yace.gateway.Gateway;
import yace.module.Module;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Path createTarget(Module source, String name) {
        try {
            var target = source.resolveSibling(name + ".mgs");
            return gateway.write(target, "mgs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actually runs the application.
     *
     * @return The target files created.
     * @throws IOException If an error happened.
     */
    Set<Path> run() throws IOException {
        return gateway.read().map(module -> {
            var fileNameWithoutExtension = module.computeName();
            return createTarget(module, fileNameWithoutExtension);
        }).collect(Collectors.toSet());
    }
}
