package yace.gateway;

import yace.module.Module;
import yace.module.PathModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Implements a Gateway under a given directory.
 * All files present in that directory will be returned as sources.
 */
public class DirectoryGateway extends PathGateway {
    private final Path root;

    /**
     * Constructs a new DirectoryGateway with the given root directory.
     *
     * @param root The root directory, which should exist.
     */
    public DirectoryGateway(Path root) {
        this.root = root;
    }

    private Stream<Path> streamPaths() throws IOException {
        return Files.list(root);
    }

    @Override
    protected Path computeRoot() {
        return root;
    }

    @Override
    public Stream<Module> stream() throws IOException {
        return streamPaths().map(path -> PathModule.createFromAbsolute(root, path));
    }
}
