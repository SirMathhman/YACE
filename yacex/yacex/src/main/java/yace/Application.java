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
    private final Gateway<T> sourceGateway;
    private final Gateway<T> target;
    private boolean isJava;


    private Application(Gateway<T> sourceGateway, Gateway<T> target, boolean isJava) {
        this.sourceGateway = sourceGateway;
        this.target = target;
        this.isJava = isJava;
    }

    /**
     * Constructs a new Application using the given gateway.
     * The gateway will be used as both the source and target gateway.
     *
     * @param gateway The gateway.
     * @param isJava If this application has Java sources.
     */
    public static <T> Application<T> fromSingleGateway(Gateway<T> gateway, boolean isJava) {
        return new Application<>(gateway, gateway, isJava);
    }

    private T compile(Module source) {
        try {
            var input = source.load();
            if (isJava) {
                if (input.isEmpty()) {
                    throw new EmptySourceException("Java source must have class.");
                }
            }

            var stored = source.detach().store("class Index {}");
            return this.target.write(stored, "mgs");
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
        return sourceGateway.stream()
                .map(this::compile)
                .collect(Collectors.toSet());
    }
}
