package yace.app;

import yace.CompileException;
import yace.io.File;
import yace.io.Path;

import java.io.IOException;
import java.util.Optional;

public abstract class Application {
    protected final Path source;
    protected final Path target;

    public Application(Path source, Path target) {
        this.source = source;
        this.target = target;
    }

    protected static Input readInput(File source) throws IOException {
        var input = source.readAsString();
        if (input.isEmpty()) {
            throw new CompileException("Input may not be empty for Java source files.");
        }

        var slice = input.substring("class ".length());
        var actualName = slice.substring(0, slice.indexOf('{')).strip();
        return new Input(source, input, actualName);
    }

    /**
     * Actually executes the application.
     *
     * @return The preview of the output.
     */
    public Optional<String> run() {
        try {
            return source.existingAsFile().flatMap(this::runInSource);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private Optional<String> runInSource(File source) {
        try {
            var input = readInput(source);
            return handle(input);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    protected abstract Optional<String> handle(Input input) throws IOException;
}
