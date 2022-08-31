package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Implements a Gateway under a given directory.
 * All files present in that directory will be returned as sources.
 */
public class DirectoryGateway implements Gateway {
    private final Path root;

    /**
     * Constructs a new DirectoryGateway with the given root directory.
     * @param root The root directory, which should exist.
     */
    public DirectoryGateway(Path root) {
        this.root = root;
    }

    private Stream<Path> streamPaths() throws IOException {
        return Files.list(root);
    }

    @Override
    public void write(Module module) throws IOException {
        Files.createFile(module.getPath());
    }

    @Override
    public Stream<Module> read() throws IOException {
        return streamPaths().map(Module::new);
    }
}
