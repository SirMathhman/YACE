package yace;

import yace.gateway.Gateway;
import yace.module.Module;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The main class for the application.
 */
public class Application<T> {
    private final Gateway<T> source;
    private final Gateway<T> target;


    private Application(Gateway<T> source, Gateway<T> target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Constructs a new Application using the given gateway.
     * The gateway will be used as both the source and target gateway.
     *
     * @param gateway The gateway.
     */
    public static <T> Application<T> fromSingleGateway(Gateway<T> gateway) {
        return new Application<>(gateway, gateway);
    }

    private T createTarget(Module source) {
        try {
            var fileName = source.computeName() + ".mgs";
            var target = source.resolveSibling(fileName);
            return this.target.write(target, "mgs");
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
    Set<T> run() throws IOException {
        return source.read()
                .map(this::createTarget)
                .collect(Collectors.toSet());
    }
}
