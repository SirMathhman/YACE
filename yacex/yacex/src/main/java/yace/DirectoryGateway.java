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

    @Override
    public Stream<Path> streamSources() throws IOException {
        return Files.list(root);
    }
}
